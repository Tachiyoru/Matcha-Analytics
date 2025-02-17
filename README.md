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
  
   \\\\\\\\\\\\\\\\

   # SwipeCard Component Documentation

## Overview
The SwipeCard component is a complex UI element that provides an interactive user profile viewing experience with the following features:
- 3D card flip animation
- Photo gallery with modal view
- Swipe functionality for like/dislike
- Responsive design
- Smooth animations and transitions

## Component Structure

### 1. Main Card Structure
```javascript
<motion.div className="swipe-card">
  <div className={`card-inner ${isFlipped ? 'flipped' : ''}`}>
    <div className="card-front">
      {/* Front content - Main photo and basic info */}
    </div>
    <div className="card-back">
      {/* Back content - Detailed profile and photo gallery */}
    </div>
  </div>
</motion.div>
```
- Uses Framer Motion for animations and gestures
- Implements 3D flip effect using CSS transforms
- Handles swipe interactions for like/dislike functionality

### 2. State Management
```javascript
const [isFlipped, setIsFlipped] = useState(false);
const [selectedPhoto, setSelectedPhoto] = useState(null);
const x = useMotionValue(0);
const rotate = useTransform(x, [-200, 200], [-30, 30]);
const opacity = useTransform(x, [-200, -100, 0, 100, 200], [0, 1, 1, 1, 0]);
```
- Tracks card flip state
- Manages selected photo for modal view
- Handles motion values for swipe animations

### 3. Photo Management
```javascript
const photos = typeof user.photos === 'string' ? JSON.parse(user.photos) : user.photos;
const firstPhoto = photos && photos.length > 0 
  ? `/shared/uploads/${photos[0]}` 
  : '/default-avatar.png';
```
Features:
- Handles JSON parsing of photo arrays
- Provides fallback for missing photos
- Manages photo URL formatting

### 4. Content Organization
```javascript
const contentSections = [];
if (user.bio) {
  contentSections.push({
    key: 'bio',
    content: (
      <div className="profile-section">
        <h3>About Me</h3>
        <p>{user.bio}</p>
      </div>
    )
  });
}
// Similar structure for job, interests, and location
```
- Creates organized content sections
- Conditionally renders based on available data
- Maintains clean component structure

### 5. Photo-Content Interleaving
```javascript
contentSections.forEach((section, index) => {
  sections.push(section.content);
  if (remainingPhotos[index]) {
    sections.push(
      <div 
        key={`photo-${index}`}
        className="profile-photo"
        style={{ backgroundImage: `url(/shared/uploads/${remainingPhotos[index]})` }}
        onClick={() => handlePhotoClick(remainingPhotos[index])}
      />
    );
  }
});
```
Features:
- Alternates between content and photos
- Ensures even distribution of photos
- Maintains proper React key management

### 6. Photo Modal Implementation
```javascript
const handlePhotoClick = (photo) => {
  if (photo.startsWith('/shared/uploads/')) {
    setSelectedPhoto(photo);
  } else {
    setSelectedPhoto(`/shared/uploads/${photo}`);
  }
};

<PhotoModal 
  photo={selectedPhoto}
  isOpen={!!selectedPhoto}
  onClose={() => setSelectedPhoto(null)}
/>
```
- Handles photo click events
- Manages modal state
- Provides full-size photo viewing

### 7. CSS Features

#### Card Flip Animation
```css
.card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.6s;
  transform-style: preserve-3d;
}

.card-inner.flipped {
  transform: rotateY(-180deg);
}
```

#### Photo Styling
```css
.profile-photo {
  width: 100%;
  height: 200px;
  border-radius: 10px;
  background-size: cover;
  background-position: center;
  transition: all 0.3s ease;
  cursor: pointer;
}

.profile-photo:hover {
  transform: scale(1.02);
}
```

#### Modal Styling
```css
.photo-modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}
```

## Key Features

### 1. Interactive Elements
- Clickable photos with hover effects
- Smooth flip animation
- Swipe gestures for like/dislike
- Modal view for photos

### 2. Responsive Design
- Properly scaled photos
- Scrollable content on back
- Maintained aspect ratios
- Mobile-friendly interactions

### 3. Error Handling
- Fallbacks for missing photos
- Protected against undefined values
- Proper JSON parsing
- Graceful handling of missing data

### 4. Performance Considerations
- Optimized re-renders
- Efficient photo loading
- Smooth animations
- Proper event handling

## Usage Example
```javascript
<SwipeCard 
  user={userProfile}
  onSwipe={(direction, userId) => {
    // Handle like/dislike
    if (direction === 'right') {
      handleLike(userId);
    } else {
      handleDislike(userId);
    }
  }}
/>
```

## Dependencies
- React
- Framer Motion
- React Icons
- Custom CSS modules

## Best Practices Implemented
1. Component modularity
2. Proper state management
3. Efficient rendering
4. Clean code structure
5. Proper error handling
6. Responsive design
7. Accessibility considerations
8. Performance optimization

## Maintenance Notes
- Keep photo paths consistent
- Update fallback images as needed
- Monitor performance with large photo sets
- Test on various devices and browsers
- Keep dependencies updated 
