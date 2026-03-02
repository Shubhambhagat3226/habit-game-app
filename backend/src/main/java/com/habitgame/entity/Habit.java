package com.habitgame.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    private LocalDateTime lastCompleted;

    public enum Difficulty {
        EASY(10), MEDIUM(25), HARD(50);

        private final int xpReward;

        Difficulty(int xpReward) {
            this.xpReward = xpReward;
        }

        public int getXpReward() {
            return xpReward;
        }
    }

    public enum Frequency {
        DAILY, WEEKLY
    }

    public Habit() {
    }

    public Habit(User user, String title, String description, Difficulty difficulty, Frequency frequency) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.frequency = frequency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getLastCompleted() {
        return lastCompleted;
    }

    public void setLastCompleted(LocalDateTime lastCompleted) {
        this.lastCompleted = lastCompleted;
    }
}
