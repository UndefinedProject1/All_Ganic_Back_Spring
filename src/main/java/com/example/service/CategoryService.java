package com.example.service;

import com.example.entity.Category;

public interface CategoryService {

    //카테고리 추가
    public void insertCategory(Category category);

    //카테고리 찾기
    public Category selectCategory(long cno);
    
}
