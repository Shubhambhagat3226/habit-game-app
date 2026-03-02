# Architecture Design: Gamified Habit Tracker

## 1. Overview
A gamified habit tracker built with Spring Boot and PostgreSQL. Users earn XP and level up by completing daily habits.

## 2. Folder Structure
```text
backend/
├── src/main/java/com/habitgame/
│   ├── config/             # Spring configuration (Security, CORS, etc.)
│   ├── controller/         # REST API Controllers
│   ├── dto/                # Data Transfer Objects
│   ├── entity/             # JPA Entities (PostgreSQL implementation)
│   ├── repository/         # Spring Data JPA Repositories
│   ├── service/            # Business Logic (XP calculations, habit logic)
│   └── HabitGameApplication.java
└── src/main/resources/
    ├── application.yml     # Configuration
    └── db/migration/       # Liquibase or Flyway (optional for now)
```

## 3. Database Schema (PostgreSQL)

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | UUID | Primary Key |
| username | VARCHAR | Unique |
| email | VARCHAR | Unique |
| password_hash | VARCHAR | Min 8 chars |
| xp | INTEGER | Total XP |
| level | INTEGER | Current Level |

### Habits Table
| Column | Type | Description |
|--------|------|-------------|
| id | UUID | Primary Key |
| user_id | UUID | Foreign Key -> Users.id |
| title | VARCHAR | Habit Name |
| description | TEXT | |
| difficulty | ENUM | EASY, MEDIUM, HARD |
| frequency | ENUM | DAILY, WEEKLY |
| last_completed | TIMESTAMP | |

## 4. API Endpoints

### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`

### Habits
- `GET /api/habits` - List current user's habits
- `POST /api/habits` - Create new habit
- `PUT /api/habits/{id}/complete` - Mark complete and trigger XP gain

### Profile
- `GET /api/profile` - Get user stats (XP, Level)

## 5. XP Calculation Logic
- **Easy**: 10 XP
- **Medium**: 25 XP
- **Hard**: 50 XP
- **Level Up**: `level * 100` XP required for next level.
