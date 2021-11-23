package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.Review;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    // 리뷰 추가
    public void insertReview(Review review);

    // 리뷰 찾기
    public Review getReviewOne(long no);

    // 리뷰 삭제
    public void deleteReview(Long no);

    // 리뷰 수정
    public void updteReview(Review review);

    // 물품별 리뷰 출력
    public List<Map<String, Object>> selectProductList(long code);

    // 회원별 리뷰 출력
    public List<Map<String, Object>> selectMemberList(String email);
}
