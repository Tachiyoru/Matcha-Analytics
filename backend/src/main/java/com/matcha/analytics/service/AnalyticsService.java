package com.matcha.analytics.service;

import com.matcha.analytics.model.UserInteraction;
import com.matcha.analytics.repository.UserInteractionRepository;
import com.matcha.analytics.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInteractionRepository interactionRepository;

    @Data
    public static class UserStats {
        private long totalUsers;
        private long activeUsers;
        private Map<String, Long> interactionStats;
        private List<Object[]> topInteractedUsers;
    }

    public UserStats getUserStats() {
        UserStats stats = new UserStats();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = now.minus(30, ChronoUnit.DAYS);

        stats.setTotalUsers(userRepository.count());
        
        Map<String, Long> interactionStats = new HashMap<>();
        for (UserInteraction.InteractionType type : UserInteraction.InteractionType.values()) {
            Long count = interactionRepository.countInteractionsByTypeAndDateRange(
                type, thirtyDaysAgo, now);
            interactionStats.put(type.toString(), count);
        }
        stats.setInteractionStats(interactionStats);

        // Get most interacted users (profile views)
        stats.setTopInteractedUsers(
            interactionRepository.findMostInteractedUsers(UserInteraction.InteractionType.PROFILE_VIEW)
        );

        return stats;
    }

    public Map<String, Long> getUserInteractionStats(Long userId) {
        Map<String, Long> stats = new HashMap<>();
        for (UserInteraction.InteractionType type : UserInteraction.InteractionType.values()) {
            Long count = interactionRepository.countUserInteractionsByType(userId, type);
            stats.put(type.toString(), count);
        }
        return stats;
    }
} 