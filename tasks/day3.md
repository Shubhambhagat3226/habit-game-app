You are a senior backend engineer working on a production-grade Spring Boot SaaS application.

Your task is to implement Day 3: Gamification Engine Upgrade.

Read the repository first.
Follow existing architecture and coding style.
Do NOT refactor unrelated files.
Keep controllers thin.
Keep business logic inside service layer.
Do not add unnecessary features.

====================================
SYSTEM CONTEXT
====================================

The system already has:
- Spring Boot backend
- JWT authentication
- User entity
- Habit entity
- XP logic
- Anti-cheat (once per day completion)

Now we upgrade to a real gamified engine.

====================================
REQUIREMENTS
====================================

1️⃣ STREAK SYSTEM
- Add fields in User:
    currentStreak
    longestStreak
- If habit completed on consecutive days → increment streak
- If a day is missed → reset currentStreak
- Update longestStreak automatically
- Streak updates must happen inside HabitService during completion

2️⃣ XP SCALING
- Add Difficulty enum in Habit:
    EASY, MEDIUM, HARD
- XP reward:
    EASY = 10
    MEDIUM = 20
    HARD = 30
- Add streak bonus:
    Every 7 consecutive days → +20 bonus XP
- Prevent double XP reward

3️⃣ LEVEL PROGRESSION
- XP threshold formula:
    level * 100 XP required
- Auto level-up when threshold crossed
- Carry forward extra XP
- Provide:
    - XP remaining to next level
    - Level progress percentage

4️⃣ DTO IMPLEMENTATION
- Create:
    UserResponseDTO
    HabitResponseDTO
- Controllers must return DTOs
- Never expose password
- Never return entity directly

5️⃣ GLOBAL EXCEPTION HANDLING
- Implement @ControllerAdvice
- Return structured JSON errors:

{
  "timestamp": "",
  "status": "",
  "message": ""
}

- Handle:
    - Duplicate user
    - Invalid credentials
    - Habit already completed
    - Resource not found

====================================
CONSTRAINTS
====================================

- Follow layered architecture
- No logic inside entities
- No business logic inside controller
- Keep code clean and production-ready
- Avoid duplication
- Follow SOLID principles

====================================
COMMIT STRUCTURE (MANDATORY)
====================================

You must logically separate changes and suggest commits in this order:

1. Commit:
   "Day 3 - Add difficulty-based XP scaling system"

2. Commit:
   "Day 3 - Implement user streak tracking system"

3. Commit:
   "Day 3 - Implement automatic level progression logic"

4. Commit:
   "Day 3 - Refactor controllers to use DTO responses"

5. Commit:
   "Day 3 - Implement global exception handling"

Do NOT merge everything into one commit.

====================================
AFTER IMPLEMENTATION
====================================

Provide:

1. Files created
2. Files modified
3. Short explanation of:
   - Streak algorithm
   - XP calculation
   - Level progression logic
4. List of edge cases handled
5. Any assumptions made

Think like this is going to production.
Be strict.
Be clean.
Be scalable.