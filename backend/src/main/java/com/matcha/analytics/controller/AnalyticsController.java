package com.matcha.analytics.controller;

import com.matcha.analytics.model.User;
import com.matcha.analytics.model.UserInteraction;
import com.matcha.analytics.repository.UserRepository;
import com.matcha.analytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:3002")
public class AnalyticsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Analytics Service is running!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/stats/users")
    public AnalyticsService.UserStats getUserStats() {
        return analyticsService.getUserStats();
    }

    @GetMapping("/stats/users/{userId}/interactions")
    public Map<String, Long> getUserInteractionStats(@PathVariable Long userId) {
        return analyticsService.getUserInteractionStats(userId);
    }

    @PostMapping("/track/interaction")
    public UserInteraction trackInteraction(@RequestBody UserInteraction interaction) {
        return userRepository.findById(interaction.getUserId())
                .map(user -> {
                    user.setLastLogin(interaction.getCreatedAt());
                    userRepository.save(user);
                    return interaction;
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
} 