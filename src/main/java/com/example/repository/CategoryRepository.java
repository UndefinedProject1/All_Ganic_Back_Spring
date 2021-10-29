package com.example.repository;

import java.util.List;

import com.example.entity.Category;
import com.example.entity.CategoryProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    //카테고리 목록
    // @Query(value = "SELECT * FROM CATEGORY", nativeQuery = true)
    // public List<Category> querySelectcate();

    //카테고리 전체 목록
    List<CategoryProjection> findAllByOrderByCategorycodeAsc();
}
