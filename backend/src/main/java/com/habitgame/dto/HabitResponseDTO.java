package com.habitgame.dto;

import com.habitgame.entity.Habit;
import java.time.LocalDateTime;
import java.util.UUID;

public class HabitResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Habit.Difficulty difficulty;
    private Habit.Frequency frequency;
    private LocalDateTime lastCompleted;

    public HabitResponseDTO() {
    }

    public HabitResponseDTO(Habit habit) {
        this.id = habit.getId();
        this.title = habit.getTitle();
        this.description = habit.getDescription();
        this.difficulty = habit.getDifficulty();
        this.frequency = habit.getFrequency();
        this.lastCompleted = habit.getLastCompleted();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Habit.Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Habit.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Habit.Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Habit.Frequency frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getLastCompleted() {
        return lastCompleted;
    }

    public void setLastCompleted(LocalDateTime lastCompleted) {
        this.lastCompleted = lastCompleted;
    }
}
