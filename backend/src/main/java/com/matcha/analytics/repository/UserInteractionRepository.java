package com.matcha.analytics.repository;

import com.matcha.analytics.model.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {
    
    @Query("SELECT COUNT(ui) FROM UserInteraction ui WHERE ui.type = :type AND ui.createdAt BETWEEN :startDate AND :endDate")
    Long countInteractionsByTypeAndDateRange(
        @Param("type") UserInteraction.InteractionType type,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT COUNT(ui) FROM UserInteraction ui WHERE ui.userId = :userId AND ui.type = :type")
    Long countUserInteractionsByType(
        @Param("userId") Long userId,
        @Param("type") UserInteraction.InteractionType type
    );

    @Query("SELECT ui.targetUserId, COUNT(ui) as count FROM UserInteraction ui " +
           "WHERE ui.type = :type GROUP BY ui.targetUserId ORDER BY COUNT(ui) DESC")
    List<Object[]> findMostInteractedUsers(@Param("type") UserInteraction.InteractionType type);
} 