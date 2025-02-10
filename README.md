# Matcha Analytics API

## Project Overview
This project is a data analytics API built with Spring Boot to track and analyze user interactions in a dating application. It provides insights into user behavior, matching patterns, and overall application usage.

## Technical Stack
- Backend: Java 11 + Spring Boot 2.7.0
- Frontend: React + Axios
- Database: MariaDB
- Container: Docker

## Core Spring Boot Concepts Used

### 1. Spring Boot Annotations
- `@SpringBootApplication`: Main application annotation that combines:
  - `@Configuration`: Marks class as source of bean definitions
  - `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration
  - `@ComponentScan`: Scans for components in the application

- Controller Annotations:
  - `@RestController`: Creates a RESTful controller
  - `@RequestMapping`: Maps HTTP requests to handler methods
  - `@GetMapping`, `@PostMapping`: Shortcuts for specific HTTP methods
  - `@CrossOrigin`: Enables CORS for frontend communication

- Entity Annotations:
  - `@Entity`: Marks class as JPA entity
  - `@Table`: Specifies database table details
  - `@Column`: Defines column properties
  - `@Id`: Marks primary key field
  - `@GeneratedValue`: Configures key generation

### 2. Project Structure
```
backend/
├── src/main/java/com/matcha/analytics/
│   ├── controller/          # REST endpoints
│   ├── model/              # Data entities
│   ├── repository/         # Database interfaces
│   ├── service/           # Business logic
│   └── MatchaAnalyticsApplication.java
└── src/main/resources/
    └── application.properties  # Configuration
```

### 3. Key Components

#### Models (Entities)
- `User.java`: Represents user data
  ```java
  @Entity
  @Table(name = "users")
  public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      // Fields and getters/setters
  }
  ```

- `UserInteraction.java`: Tracks user interactions
  ```java
  @Entity
  @Table(name = "user_interactions")
  public class UserInteraction {
      public enum InteractionType {
          PROFILE_VIEW, LIKE, UNLIKE, MESSAGE, MATCH
      }
      // Fields and methods
  }
  ```

#### Repositories
- Extend `JpaRepository` for database operations
- Custom queries using `@Query` annotation
```java
public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
    @Query("SELECT COUNT(ui) FROM UserInteraction ui WHERE ui.type = :type")
    Long countInteractionsByType(@Param("type") InteractionType type);
}
```

#### Services
- Business logic layer
- Use `@Service` annotation
- Implement complex operations
```java
@Service
public class AnalyticsService {
    @Autowired
    private UserRepository userRepository;
    // Business methods
}
```

#### Controllers
- Handle HTTP requests
- Map to service methods
```java
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
```

### 4. API Endpoints

#### User Management
- `GET /api/analytics/users`: List all users
- `GET /api/analytics/users/{id}`: Get specific user

#### Analytics
- `GET /api/analytics/stats/users`: Get overall user statistics
- `GET /api/analytics/stats/users/{userId}/interactions`: Get user-specific stats
- `POST /api/analytics/track/interaction`: Track new interaction

### 5. Database Integration
- Uses Spring Data JPA
- Configured in application.properties:
```properties
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 6. Docker Integration
- Multi-container setup with docker-compose
- Separate containers for:
  - Spring Boot backend
  - React frontend
  - MariaDB database

## Best Practices Implemented

1. **Layered Architecture**
   - Controller → Service → Repository
   - Clear separation of concerns

2. **Data Validation**
   - Entity constraints
   - Input validation

3. **Error Handling**
   - Exception handling for not found cases
   - Proper HTTP status codes

4. **Code Organization**
   - Logical package structure
   - Clear naming conventions

5. **Security Considerations**
   - CORS configuration
   - Environment variables for sensitive data

## Learning Path for Spring Boot

1. Start with Core Concepts:
   - Spring IoC Container
   - Dependency Injection
   - Spring Boot Auto-configuration

2. Move to Web Development:
   - REST Controllers
   - Request Mapping
   - Response Handling

3. Learn Data Access:
   - Spring Data JPA
   - Repository Pattern
   - Database Configuration

4. Advanced Topics:
   - Security
   - Testing
   - Caching
   - Async Operations

## Useful Resources
- [Spring Boot Official Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Guide](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-boot)

## Future Enhancements to Consider

1. **Analytics Features**
   - Geographic distribution analysis
   - User retention metrics
   - Match success rate analysis
   - User behavior patterns

2. **Technical Improvements**
   - Caching for performance
   - Real-time analytics with WebSocket
   - Batch processing for large datasets
   - Advanced security features 