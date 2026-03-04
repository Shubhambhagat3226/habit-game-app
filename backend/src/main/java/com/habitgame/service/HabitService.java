package com.habitgame.service;

import com.habitgame.entity.Habit;
import com.habitgame.entity.User;
import com.habitgame.repository.HabitRepository;
import com.habitgame.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class HabitService {
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    private final GamificationService gamificationService;

    public HabitService(HabitRepository habitRepository, UserRepository userRepository,
            GamificationService gamificationService) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
        this.gamificationService = gamificationService;
    }

    public List<Habit> getHabitsByUser(User user) {
        return habitRepository.findByUser(user);
    }

    public Habit createHabit(Habit habit) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        habit.setUser(user);
        return habitRepository.save(habit);
    }

    @Transactional
    public Habit completeHabit(UUID habitId) {
        String authenticatedUsername = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        // Ownership check
        User user = habit.getUser();
        System.out.println("DEBUG: Auth User=" + authenticatedUsername + ", Habit Owner=" + user.getUsername());
        if (!user.getUsername().equals(authenticatedUsername)) {
            throw new RuntimeException("Access denied: you do not own this habit");
        }

        // Daily completion guard
        if (habit.getLastCompleted() != null) {
            LocalDate lastDate = habit.getLastCompleted().toLocalDate();
            if (lastDate.equals(LocalDate.now())) {
                throw new RuntimeException("Habit already completed today");
            }
        }

        habit.setLastCompleted(LocalDateTime.now());

        // Delegate gamification logic
        gamificationService.addProgress(user, habit.getDifficulty().getXpReward());

        return habitRepository.save(habit);
    }
}
