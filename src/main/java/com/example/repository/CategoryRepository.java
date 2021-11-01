package com.example.repository;

import java.util.List;

import com.example.entity.Category;
import com.example.entity.CategoryProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    //카테고리 목록
    // @Query(value = "SELECT * FROM CATEGORY", nativeQuery = true)
    // public List<Category> querySelectcate();

    //카테고리 전체 목록
    List<CategoryProjection> findAllByOrderByCategorycodeAsc();

    //카테고리 코드 별 카테고리 조회(jpa)
    List<CategoryProjection>  findByCategorycodeStartingWithOrderByCategorycodeAsc(String categorycode);

    //카테고리 중복 체크
    @Query(value = "SELECT COUNT(CATEGORYCODE) FROM CATEGORY WHERE CATEGORYCODE = :categorycode", nativeQuery = true)
    public int queryCheckCatecode(long categorycode);
}
