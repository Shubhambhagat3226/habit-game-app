package com.habitgame.controller;

import com.habitgame.dto.HabitResponseDTO;
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

    @PostMapping
    public ResponseEntity<HabitResponseDTO> createHabit(@RequestBody Habit habit) {
        return ResponseEntity.ok(new HabitResponseDTO(habitService.createHabit(habit)));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<HabitResponseDTO> completeHabit(@PathVariable UUID id) {
        return ResponseEntity.ok(new HabitResponseDTO(habitService.completeHabit(id)));
    }
}
