package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Review;
import com.example.entity.ReviewProjection;
import com.example.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository rRepository;

    @Override
    public void insertReview(Review review) {
        rRepository.save(review);
    }

    @Override
    public Review getReviewOne(long no) {
        Optional<Review> review = rRepository.findById(no);
        return review.orElse(null);
    }

    @Override
    public void deleteReview(Long no) {
        rRepository.deleteById(no);
    }

    @Override
    public void updteReview(Review review) {
        rRepository.save(review);
    }

    @Override
    public List<ReviewProjection> selectProductList(long code) {
        return rRepository.findByProduct_Productcode(code);
    }

    @Override
    public List<ReviewProjection> selectMemberList(String email) {
        return rRepository.findByMember_Useremail(email);
    }

}
