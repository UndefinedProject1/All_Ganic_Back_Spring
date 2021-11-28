package com.example.repository;

import java.util.Optional;

import com.example.entity.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long>{

    // 추천물품이 있는지 확인
    Optional<Recommend> findByProduct_Productcode(Long productcode);
}
