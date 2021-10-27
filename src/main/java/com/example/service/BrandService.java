package com.example.service;

import java.util.List;

import com.example.entity.Brand;
import com.example.entity.BrandProjection;

public interface BrandService {

    //브랜드 추가
    public void insertBrand(Brand brand);
    
    //브랜드 찾기
    public Brand selectBrand(Long no);

    //브랜드 전체 조회
    public List<BrandProjection> selectBrandList();

}
