# Day 2: Authentication System

## Objective
Add JWT-based authentication (register + login) to the Habit Game backend.

## Requirements
- POST /api/auth/register — Create new user with hashed password, return JWT
- POST /api/auth/login — Verify credentials, return JWT
- Protect all other endpoints with JWT Bearer token validation
- Use BCrypt for password hashing
- Use Spring Security with stateless session management

## Files Created
- `config/SecurityConfig.java` — Spring Security filter chain
- `config/JwtUtil.java` — JWT token generation and validation
- `config/JwtAuthFilter.java` — Request filter for Bearer tokens
- `dto/RegisterRequest.java` — Registration DTO with validation
- `dto/LoginRequest.java` — Login DTO with validation
- `dto/AuthResponse.java` — Response DTO with token
- `service/AuthService.java` — Auth business logic
- `controller/AuthController.java` — Auth REST endpoints

## Files Modified
- `pom.xml` — Added spring-boot-starter-security, jjwt dependencies
- `application.yml` — Added jwt.secret and jwt.expiration config

## Constraints
- Do NOT modify XP logic
- Do NOT refactor existing files
- Follow existing layered architecture pattern