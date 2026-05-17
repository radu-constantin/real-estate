package com.thedevs.real_estate.repository;

import com.thedevs.real_estate.model.User;
import org.springframework.data.jpa.repository.JpaRepository; // Changed this import
import java.util.Optional; // Added this import

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}