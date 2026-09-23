# Spring Security Basics

A simple Spring Boot project demonstrating the fundamentals of **Spring Security**, including authentication, authorization, password encoding, roles, and securing REST APIs.

## 🛠️ Technologies

* Java 17+
* Spring Boot
* Spring Security
* Spring Web
* Maven
* REST API

## 📚 What This Project Covers

### 1. Authentication

Authentication verifies **who the user is**.

Example:

```text
Username: admin
Password: admin123
```

Spring Security authenticates the user before allowing access to protected resources.

### 2. Authorization

Authorization determines **what an authenticated user is allowed to access**.

Example:

```text
USER  → /user/**
ADMIN → /admin/**
```

An authenticated user does not automatically have permission to access every API.

---

## 🔐 Spring Security Request Flow

```text
Client
   |
   | HTTP Request
   v
Security Filter Chain
   |
   v
Authentication
   |
   +---- Invalid ----> 401 Unauthorized
   |
   v
Authorization
   |
   +---- No Permission ----> 403 Forbidden
   |
   v
Controller
   |
   v
Service
   |
   v
Response
```

---

## 🔑 Important Concepts

### SecurityFilterChain

`SecurityFilterChain` defines how HTTP requests should be secured.

Example:

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/public/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults());

    return http.build();
}
```

### PasswordEncoder

Passwords should never be stored as plain text.

Use BCrypt:

```java
@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Example:

```java
String encodedPassword =
        passwordEncoder.encode("admin123");
```

The database should contain the encoded password rather than:

```text
admin123
```

---

## 👤 In-Memory Authentication

For learning purposes, users can be configured in memory.

```java
@Bean
UserDetailsService users(PasswordEncoder encoder) {

    UserDetails user = User
            .withUsername("user")
            .password(encoder.encode("user123"))
            .roles("USER")
            .build();

    UserDetails admin = User
            .withUsername("admin")
            .password(encoder.encode("admin123"))
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user, admin);
}
```

---

## 🛡️ Role-Based Authorization

Example:

```java
.requestMatchers("/admin/**").hasRole("ADMIN")
.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
```

Controller:

```java
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard";
    }
}
```

Only users with the `ADMIN` role can access:

```text
GET /admin/dashboard
```

---

## 🌐 Public vs Protected APIs

Example configuration:

```java
.authorizeHttpRequests(auth -> auth

    .requestMatchers("/public/**").permitAll()

    .requestMatchers("/admin/**")
    .hasRole("ADMIN")

    .requestMatchers("/user/**")
    .hasAnyRole("USER", "ADMIN")

    .anyRequest()
    .authenticated()
)
```

| API          | Access              |
| ------------ | ------------------- |
| `/public/**` | Everyone            |
| `/user/**`   | USER, ADMIN         |
| `/admin/**`  | ADMIN               |
| Other APIs   | Authenticated users |

---

## 🚦 HTTP Status Codes

### 401 Unauthorized

The request does not contain valid authentication.

```text
401 Unauthorized
```

Typical scenario:

```text
Client → Protected API
       → No valid authentication
       → 401
```

### 403 Forbidden

The user is authenticated but does not have sufficient permissions.

```text
403 Forbidden
```

Example:

```text
USER → /admin/dashboard
     → Authenticated
     → Not ADMIN
     → 403
```

### Important Difference

```text
401 → Authentication problem
403 → Authorization problem
```

---

## 🔒 CSRF

CSRF stands for:

**Cross-Site Request Forgery**

Spring Security enables CSRF protection by default for applications where it is relevant.

For a stateless REST API, it is common to disable CSRF when authentication is handled through mechanisms such as JWT:

```java
http.csrf(csrf -> csrf.disable());
```

Do not disable CSRF blindly. The correct configuration depends on how the application authenticates users and handles browser requests.

---

## 🔐 Basic Authentication

For learning purposes:

```java
http
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(auth -> auth
        .anyRequest()
        .authenticated()
    );
```

The client sends credentials using the HTTP `Authorization` header:

```text
Authorization: Basic <base64(username:password)>
```

Basic Authentication should normally be used over **HTTPS**.

---

## 🎫 JWT Authentication

For modern microservices and REST APIs, JWT-based authentication is commonly used.

Typical flow:

```text
Login
  |
  v
Authentication Server
  |
  v
JWT Token
  |
  v
Client
  |
  | Authorization: Bearer <JWT>
  v
Spring Security
  |
  v
JWT Validation
  |
  v
Controller
```

Example:

```http
Authorization: Bearer eyJhbGciOi...
```

Spring Security validates the JWT before allowing access to protected resources.

---

## 🧩 Important Spring Security Classes

| Class / Interface       | Purpose                       |
| ----------------------- | ----------------------------- |
| `SecurityFilterChain`   | Defines security rules        |
| `HttpSecurity`          | Configures HTTP security      |
| `UserDetails`           | Represents authenticated user |
| `UserDetailsService`    | Loads user information        |
| `PasswordEncoder`       | Encrypts/hashes passwords     |
| `Authentication`        | Represents authentication     |
| `AuthenticationManager` | Performs authentication       |
| `GrantedAuthority`      | Represents permissions/roles  |
| `BCryptPasswordEncoder` | BCrypt password hashing       |

---

## 📁 Suggested Project Structure

```text
src/main/java
└── com.example.security
    ├── config
    │   └── SecurityConfig.java
    │
    ├── controller
    │   ├── PublicController.java
    │   ├── UserController.java
    │   └── AdminController.java
    │
    └── SecurityApplication.java
```

---

## 🧪 Testing

### Public API

```http
GET /public/hello
```

Expected:

```text
200 OK
```

### Protected API Without Authentication

```http
GET /user/profile
```

Expected:

```text
401 Unauthorized
```

### USER Accessing USER API

```text
USER
 |
 +--> /user/profile
       |
       +--> 200 OK
```

### USER Accessing ADMIN API

```text
USER
 |
 +--> /admin/dashboard
       |
       +--> 403 Forbidden
```

### ADMIN Accessing ADMIN API

```text
ADMIN
 |
 +--> /admin/dashboard
       |
       +--> 200 OK
```

---

## 🎯 Interview Questions

### What is Spring Security?

Spring Security is a framework for implementing authentication and authorization in Spring applications.

### Authentication vs Authorization?

```text
Authentication → Who are you?

Authorization → What are you allowed to do?
```

### Why use PasswordEncoder?

To avoid storing passwords in plain text and securely hash passwords before storing them.

### What is SecurityFilterChain?

It defines the security filters and authorization rules applied to incoming HTTP requests.

### 401 vs 403?

```text
401 → User is not properly authenticated.

403 → User is authenticated but does not have permission.
```

### What is BCrypt?

BCrypt is a password-hashing algorithm designed to securely store passwords and includes a salt to make password cracking more difficult.

### What is JWT?

JWT is a signed token format commonly used to represent claims about an authenticated user and support stateless API authentication.

---

## 🚀 Learning Path

Recommended progression:

```text
1. Spring Security basics
        ↓
2. Authentication
        ↓
3. Authorization
        ↓
4. PasswordEncoder / BCrypt
        ↓
5. Database Authentication
        ↓
6. Role-Based Authorization
        ↓
7. REST API Security
        ↓
8. Basic Authentication
        ↓
9. JWT Authentication
        ↓
10. OAuth2 / OpenID Connect
        ↓
11. Microservices Security
```

## 💡 Production Architecture

For a banking/payment microservices application:

```text
                    ┌──────────────┐
                    │    Client    │
                    └──────┬───────┘
                           │
                           │ JWT
                           ▼
                    ┌──────────────┐
                    │ API Gateway  │
                    └──────┬───────┘
                           │
                 ┌─────────┴─────────┐
                 ▼                   ▼
          ┌─────────────┐     ┌─────────────┐
          │ Payment MS  │     │ Account MS  │
          └─────────────┘     └─────────────┘
                 │                   │
                 └─────────┬─────────┘
                           ▼
                    Authorization
                    + Authentication
```

This pattern is particularly useful when securing **Spring Boot microservices, payment APIs, and enterprise REST services**.


Debug Mode  Spring app: mvn spring-boot:run  "-Dspring-boot.run.arguments=--debug"