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

    public HabitService(HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public List<Habit> getHabitsByUser(User user) {
        return habitRepository.findByUser(user);
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    @Transactional
    public Habit completeHabit(UUID habitId) {
        String authenticatedUsername = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        // Ownership check: user can only complete their own habits
        User user = habit.getUser();
        if (!user.getUsername().equals(authenticatedUsername)) {
            throw new RuntimeException("Access denied: you do not own this habit");
        }

        // Daily completion guard: prevent completing same habit twice in one day
        if (habit.getLastCompleted() != null) {
            LocalDate lastDate = habit.getLastCompleted().toLocalDate();
            if (lastDate.equals(LocalDate.now())) {
                throw new RuntimeException("Habit already completed today");
            }
        }

        habit.setLastCompleted(LocalDateTime.now());

        user.addXp(habit.getDifficulty().getXpReward());

        userRepository.save(user);
        return habitRepository.save(habit);
    }
}
