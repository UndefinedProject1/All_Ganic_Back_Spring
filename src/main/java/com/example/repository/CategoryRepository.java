package com.example.repository;

import java.util.List;

import com.example.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    //카테고리 목록
    // @Query(value = "SELECT * FROM CATEGORY", nativeQuery = true)
    // public List<Category> querySelectcate();
}
