package com.example.service;

import java.util.List;

import com.example.entity.Product;
import com.example.entity.ProductProjection;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    //제품 추가
    public void insertProduct(Product product);

    //제품 삭제
    public void deleteProduct(Long product);
    
    //제품 수정
    public void updteProduct(Product product);

    //제품정보 가져오기
    public Product selectProduct(long code);

    //해당 브랜드 제품들 가져오기
    public List<Product> getBrandProduct(long code);

    //해당 브랜드 제품들 가져오기
    public List<ProductProjection> getListProduct();

}
