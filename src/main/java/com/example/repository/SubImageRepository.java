package com.example.repository;

import java.util.List;

import com.example.entity.SubImage;
import com.example.entity.SubImageProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubImageRepository extends JpaRepository<SubImage, Long> {

    //제품코드 별 이미지 목록
    List<SubImageProjection> findByProduct_Productcode(long productcode);
}
