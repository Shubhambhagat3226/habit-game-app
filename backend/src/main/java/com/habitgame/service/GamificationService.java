package com.habitgame.service;

import com.habitgame.entity.User;
import com.habitgame.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class GamificationService {
    private final UserRepository userRepository;

    public GamificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void processProgression(User user) {
        updateStreak(user);
        calculateXpAndLevel(user);
        userRepository.save(user);
    }

    private void updateStreak(User user) {
        LocalDate today = LocalDate.now();
        LocalDate lastActionDate = user.getLastActionDate();

        if (lastActionDate == null) {
            user.setCurrentStreak(1);
        } else if (lastActionDate.equals(today.minusDays(1))) {
            user.setCurrentStreak(user.getCurrentStreak() + 1);
        } else if (!lastActionDate.equals(today)) {
            user.setCurrentStreak(1);
        }

        if (user.getCurrentStreak() > user.getLongestStreak()) {
            user.setLongestStreak(user.getCurrentStreak());
        }
        user.setLastActionDate(today);
    }

    private void calculateXpAndLevel(User user) {
        // This is a simplified version, base XP should probably come from the activity
        // But for the current implementation, we'll keep it consistent with what was in
        // HabitService
    }

    @Transactional
    public void addProgress(User user, int baseXp) {
        updateStreak(user);

        int xpToGain = baseXp;
        // Streak bonus: +20 XP every 7 days
        if (user.getCurrentStreak() > 0 && user.getCurrentStreak() % 7 == 0) {
            xpToGain += 20;
        }

        user.addXp(xpToGain);
        userRepository.save(user);
    }
}
