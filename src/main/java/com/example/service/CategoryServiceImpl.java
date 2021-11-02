package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.Category;
import com.example.entity.CategoryProjection;
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

    //카테고리 찾기
    @Override
    public Category selectCategory(long cno) {
        Optional<Category> category = cRepository.findById(cno);
        return category.orElse(null);
    }

    //카테고리 전체 조회
    @Override
    public List<CategoryProjection> selectCategoryList() {
        return cRepository.findAllByOrderByCategorycodeAsc();
    }

    //카테고리 코드 별 카테고리 조회(jpa)
    @Override
    public List<CategoryProjection> selectCategoryNum(String categorycode) {
        return  cRepository.findByCategorycodeStartingWithOrderByCategorycodeAsc(categorycode);
    }

    //카테고리 중복 체크
    @Override
    public int checkCateCode(String categorycode) {
        return cRepository.queryCheckCatecode(categorycode);
    }
}
