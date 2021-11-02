package com.example.repository;

import java.util.List;

import com.example.entity.Product;
import com.example.entity.ProductProjection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    
    @Query(value = "SELECT PRODUCTNAME, PRODUCTPRICE FROM PRODUCT", nativeQuery = true)
    public List<ProductProjection> queryListProduct();

    //물품 전체 조회
    List<ProductProjection> findAllByOrderByProductcodeAsc();

    // 브랜드 코드 별 제품 조회(jpa)
    List<ProductProjection> findByBrand_Brandcode(Long brandcode);

    //브랜드 코드 별 제품 이름 순 조회(jpa)
    List<ProductProjection>  findByBrand_BrandcodeOrderByProductnameAsc(Long brandcode, Pageable pageable);

    // 브랜드 코드 별 제품 조회(sql)
    @Query(value = "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, PRODUCTDATE FROM PRODUCT WHERE BRAND =:code", nativeQuery = true)
    public List<ProductProjection> queryListBProduct(@Param("code") Long code);

    //카테고리 코드 별 제품 조회(sql)
    @Query(value = "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, PRODUCTDATE FROM PRODUCT WHERE CATEGORY LIKE :code || '%'", nativeQuery = true)
    public List<ProductProjection> queryListCProduct(@Param("code") long code);

    //카테고리 코드 별 제품 조회(jpa)
    List<ProductProjection>  findByCategory_CategorycodeStartingWith(String categorycode);
    
    //카테고리 코드 별 제품 이름 순 조회(jpa)
    List<ProductProjection>  findByCategory_CategorycodeStartingWithOrderByProductnameAsc(String categorycode, Pageable pageable);

    //제품 1개 조회 (상세 페이지)
    ProductProjection findByProductcode(long code);

    //제품 전체 목록(이름에 단어가 포함하는 + 제품이름 오름차순 정렬 + 페이지 네이션)
    List<ProductProjection> findByProductnameIgnoreCaseContainingOrderByProductnameAsc(String productname, Pageable pageable);
}
