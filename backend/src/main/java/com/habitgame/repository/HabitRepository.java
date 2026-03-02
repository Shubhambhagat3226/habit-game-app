package com.habitgame.repository;

import com.habitgame.entity.Habit;
import com.habitgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, UUID> {
    List<Habit> findByUser(User user);
}
