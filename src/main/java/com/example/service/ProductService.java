package com.example.service;

import java.util.List;

import com.example.entity.BrandCountProjection;
import com.example.entity.Product;
import com.example.entity.ProductProjection;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    //제품 추가
    public void insertProduct(Product product);

    //제품 삭제
    public void deleteProduct(Long product);
    
    //제품 수정
    public void updteProduct(Product product);

    //제품정보 가져오기, 물품 이미지 찾고 변환하기
    public Product selectProduct(long code);

    //제품 전체 조회
    public List<ProductProjection> selectProductList();

    //브랜드 코드 별 제품 조회(jpa)
    public List<ProductProjection> selectBProductList(long code);

    //브랜드 코드 별 제품 이름순 조회(jpa)
    public List<ProductProjection> selectBProductLsit3(long code, Pageable pageable);

    //브랜드 코드 별 제품 조회(sql)
    public List<ProductProjection> selectBProductLsit2(Long code);

    //카테고리 코드 별 제품 조회(sql)
    public List<ProductProjection> selectCProductLsit(String code);

    //카테고리 코드 별 제품 조회(jpa)
    public List<ProductProjection> selectCProductLsit2(String code);

    //카테고리 코드 별 제품 이름순 조회(jpa)
    public List<ProductProjection> selectCProductLsit3(String code, Pageable pageable);

    //제품 1개 조회 (상세 페이지)
    public ProductProjection selectProductOne(long code);

    //제품 전체 목록(이름에 단어가 포함하는 + 제품이름 오름차순 정렬 + 페이지 네이션)
    public List<ProductProjection> selectProductList2(String productname, Pageable pageable);

    // 브랜드별 물품 개수
    public List<BrandCountProjection> selectBrandCount();
}
