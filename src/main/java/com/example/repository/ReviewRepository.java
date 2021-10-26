package com.example.repository;

import com.example.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // @Query(value = "SELECT COUNT(USEREMAIL) FROM MEMBER WHERE USEREMAIL =
    // :email", nativeQuery = true)
    // public int queryCheckEmail(String email);
}
