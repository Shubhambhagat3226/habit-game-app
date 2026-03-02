package com.habitgame.controller;

import com.habitgame.entity.Habit;
import com.habitgame.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Habit> completeHabit(@PathVariable UUID id) {
        return ResponseEntity.ok(habitService.completeHabit(id));
    }
}
