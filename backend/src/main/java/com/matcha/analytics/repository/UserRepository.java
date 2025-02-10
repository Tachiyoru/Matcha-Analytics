package com.matcha.analytics.repository;

import com.matcha.analytics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
} 