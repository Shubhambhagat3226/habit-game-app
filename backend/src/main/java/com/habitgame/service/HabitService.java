package com.habitgame.service;

import com.habitgame.entity.Habit;
import com.habitgame.entity.User;
import com.habitgame.repository.HabitRepository;
import com.habitgame.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        habit.setLastCompleted(LocalDateTime.now());

        User user = habit.getUser();
        user.addXp(habit.getDifficulty().getXpReward());

        userRepository.save(user);
        return habitRepository.save(habit);
    }
}
