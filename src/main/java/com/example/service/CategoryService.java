package com.example.service;

import java.util.List;

import com.example.entity.Category;
import com.example.entity.CategoryProjection;

public interface CategoryService {

    //카테고리 추가
    public void insertCategory(Category category);

    //카테고리 찾기
    public Category selectCategory(long cno);

    //카테고리 전체 조회
    public List<CategoryProjection> selectCategoryList();

}
