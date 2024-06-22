package com.example.patterns.repository;

import com.example.patterns.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u LEFT JOIN u.contactDetails c " +
            "WHERE u.username = :identifier " +
            "OR (c.type = 'email' AND c.value = :identifier) " +
            "OR (c.type = 'phone' AND c.value = :identifier)")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);


    @Query("SELECT u FROM User u where u.username = :userName and password = :encode")
    Optional<User> findByUserNameAndPassword(String userName, String encode);
}