package com.example.repository;

import java.util.List;

import com.example.entity.Brand;
import com.example.entity.BrandProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long>{
    
    //브랜드 전체 목록
    List<BrandProjection> findAllByOrderByBrandcodeAsc();

    //브랜드 중복 체크
    @Query(value = "SELECT COUNT(BRANDCODE) FROM BRAND WHERE BRANDCODE = :brandcode", nativeQuery = true)
    public int queryCheckBrandcode(Long brandcode);
}
