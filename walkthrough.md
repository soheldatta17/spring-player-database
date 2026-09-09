# Walkthrough - Spring Security & JWT Authentication Implementation

We have implemented complete stateless JWT Authentication & Authorization in your Spring Boot application using Spring Security and Java JWT (JJWT).

## Summary of Changes

1. **Dependencies & Configuration**:
   - Added `spring-boot-starter-security` and `io.jsonwebtoken:jjwt-*` (0.12.6) in [pom.xml](file:///e:/GitHub/spring-tutorial-course/pom.xml).
   - Configured `jwt.secret` and `jwt.expiration` in [application.properties](file:///e:/GitHub/spring-tutorial-course/src/main/resources/application.properties).

2. **DTOs**:
   - Created [AuthRequest.java](file:///e:/GitHub/spring-tutorial-course/src/main/java/com/sohel/demoproj/dto/AuthRequest.java) to accept `username` and `password`.
   - Created [AuthResponse.java](file:///e:/GitHub/spring-tutorial-course/src/main/java/com/sohel/demoproj/dto/AuthResponse.java) to return JWT `token`.

3. **JWT Service Component**:
   - Created [JwtUtil.java](file:///e:/GitHub/spring-tutorial-course/src/main/java/com/sohel/demoproj/security/JwtUtil.java) to generate 256-bit signed tokens, parse claims, check expiration, and validate tokens.

4. **Authentication Filter & Security Config**:
   - Created [JwtAuthenticationFilter.java](file:///e:/GitHub/spring-tutorial-course/src/main/java/com/sohel/demoproj/security/JwtAuthenticationFilter.java) extending `OncePerRequestFilter` to intercept request headers, extract `Authorization: Bearer <token>`, and set Spring's `SecurityContext`.
   - Created [SecurityConfig.java](file:///e:/GitHub/spring-tutorial-course/src/main/java/com/sohel/demoproj/config/SecurityConfig.java) establishing a stateless `SecurityFilterChain` where `/identity`, `/`, `/swagger-ui/**`, `/v3/api-docs/**`, and `/h2-console/**` are public, while all other endpoints require authentication.

5. **Authentication Endpoint**:
   - Created [IdentityController.java](file:///e:/GitHub/spring-tutorial-course/src/main/java/com/sohel/demoproj/controller/IdentityController.java) handling `POST /identity`.
   - Validates hardcoded credentials (`sohel` / `sohel123`), issuing a JWT token on success or returning `401 Unauthorized` on failure.

---

## Verification Results

### Automated Tests
- `mvn test` executed with **`BUILD SUCCESS`**.

### End-to-End Runtime Verification

```text
--- Step 1: POST /identity with invalid credentials ---
Status Code: 401 Unauthorized

--- Step 2: POST /identity with valid credentials (sohel / sohel123) ---
Status: 200 OK
Token: eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzb2hlbCI...

--- Step 3: GET /team/players WITHOUT token ---
Status Code: 403 Forbidden

--- Step 4: GET /team/players WITH `Authorization: Bearer <token>` ---
Status: 200 OK
Body: [ { "name": "Rahul", "gender": "Male", "age": 24 } ]
```

---

## How to Test in Postman or cURL

### 1. Generate JWT Token

**Request**: `POST http://localhost:8080/identity`
```json
{
  "username": "sohel",
  "password": "sohel123"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzb2hlbCI..."
}
```

### 2. Call Protected API Endpoint

**Request**: `GET http://localhost:8080/team/players`
**Header**:
```text
Authorization: Bearer <your_jwt_token_here>
```
