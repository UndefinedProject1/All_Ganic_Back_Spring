package com.example.service;

import java.util.List;

import com.example.entity.Category;

import org.springframework.data.jpa.repository.Query;

public interface CategoryService {

    //카테고리 추가
    public void insertCategory(Category category);

    //카테고리 찾기
    public Category selectCategory(long cno);

    //카테고리 목록
    @Query(value = "SELECT * FROM CATEGORY", nativeQuery = true)
    public List<Category> querySelectcate();

}
