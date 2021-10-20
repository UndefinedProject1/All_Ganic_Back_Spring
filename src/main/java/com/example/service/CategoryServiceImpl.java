package com.example.service;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Category;
import com.example.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
    
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    CategoryRepository cRepository;

    //카테고리 추가
    public void insertCategory(Category category){
        cRepository.save(category);
    }
    
}
