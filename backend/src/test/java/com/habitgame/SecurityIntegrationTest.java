package com.habitgame;

import com.habitgame.config.JwtUtil;
import com.habitgame.domain.Habit;
import com.habitgame.domain.User;
import com.habitgame.repository.HabitRepository;
import com.habitgame.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HabitRepository habitRepository;

    @org.junit.jupiter.api.BeforeEach
    public void setup() {
        habitRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testJwtForgeryAndIdor() throws Exception {
        // 1. Create a victim user and their habit
        User victim = new User("victim", "victim@test.com", "password");
        userRepository.save(victim);

        Habit habit = new Habit(victim, "Victim Habit", "Secret", Habit.Difficulty.HARD, Habit.Frequency.DAILY);
        habit = habitRepository.save(habit);
        UUID habitId = habit.getId();

        // 2. Forge a token for the victim
        String forgedToken = jwtUtil.generateToken("victim");

        // 3. Attempt to complete victim's habit using forged token (Should succeed
        // because token is validly signed)
        mockMvc.perform(post("/api/habits/" + habitId + "/complete")
                .header("Authorization", "Bearer " + forgedToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 4. Create an attacker user
        User attacker = new User("attacker", "attacker@test.com", "password");
        userRepository.save(attacker);
        String attackerToken = jwtUtil.generateToken("attacker");

        // 5. Attempt IDOR: attacker tries to complete victim's habit (Should fail with
        // 401/403/BAD_REQUEST due to ownership check)
        mockMvc.perform(post("/api/habits/" + habitId + "/complete")
                .header("Authorization", "Bearer " + attackerToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDoubleCompletion() throws Exception {
        User user = new User("double", "double@test.com", "password");
        userRepository.saveAndFlush(user);
        String token = jwtUtil.generateToken("double");

        Habit habit = new Habit(user, "Double Habit", "Desc", Habit.Difficulty.EASY, Habit.Frequency.DAILY);
        habit = habitRepository.saveAndFlush(habit);
        UUID habitId = habit.getId();

        // First completion - Success
        mockMvc.perform(post("/api/habits/" + habitId + "/complete")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());

        // Second completion - Should fail with 409 Conflict
        mockMvc.perform(post("/api/habits/" + habitId + "/complete")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isConflict());
    }

    @Test
    public void testAuthenticationBypass() throws Exception {
        mockMvc.perform(post("/api/habits/" + UUID.randomUUID() + "/complete"))
                .andExpect(status().isForbidden());
    }
}
