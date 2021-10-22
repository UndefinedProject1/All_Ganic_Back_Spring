package com.example.service;

import com.example.entity.Brand;

public interface BrandService {

    //브랜드 추가
    public void insertBrand(Brand brand);
    
    //브랜드 찾기
    public Brand selectBrand(Long no);


}
