package com.example.repository;

import java.util.List;

import com.example.entity.Brand;
import com.example.entity.BrandProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long>{
    
    //브랜드 전체 목록
    List<BrandProjection> findAllByOrderByBrandcodeAsc();
}
