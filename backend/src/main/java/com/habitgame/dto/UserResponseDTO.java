package com.habitgame.dto;

import com.habitgame.entity.User;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private Integer xp;
    private Integer level;
    private Integer currentStreak;
    private Integer longestStreak;
    private Integer xpToNextLevel;
    private double progressPercentage;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.xp = user.getXp();
        this.level = user.getLevel();
        this.currentStreak = user.getCurrentStreak();
        this.longestStreak = user.getLongestStreak();

        int nextLevelThreshold = user.getLevel() * 100;
        this.xpToNextLevel = nextLevelThreshold - user.getXp();
        this.progressPercentage = (double) user.getXp() / nextLevelThreshold * 100;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Integer getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Integer getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(Integer xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
