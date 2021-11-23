package com.example.repository;

import java.util.List;

import com.example.entity.Review;
import com.example.entity.ReviewProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
