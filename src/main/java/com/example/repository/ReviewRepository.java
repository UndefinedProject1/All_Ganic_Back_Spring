package com.example.repository;

import java.util.List;

import com.example.entity.Review;
import com.example.entity.ReviewProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // @Query(value = "SELECT COUNT(USEREMAIL) FROM REVIEW WHERE PRODUCT =
    // :email", nativeQuery = true)
    // public int queryCheckEmail(String email);

    // 물품별 리뷰 출력
    List<ReviewProjection> findByProduct_Productcode(long productcode);

    // 회원별 리뷰 출력
    List<ReviewProjection> findByMember_Useremail(String useremail);
}
