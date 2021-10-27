package com.example.repository;

import java.util.List;

import com.example.entity.Product;
import com.example.entity.ProductProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    
    // 브랜드코드 별 제품 조회
    List<ProductProjection> findByBrand_Brandcode(Long brandcode);

    @Query(value = "SELECT PRODUCTNAME, PRODUCTPRICE FROM PRODUCT", nativeQuery = true)
    public List<ProductProjection> queryListProduct();

    //물품 전체 조회
    List<ProductProjection> findAllByOrderByProductcodeAsc();

    //카테고리 코드 별 제품 조회
    @Query(value = "SELECT PRODUCTNAME, PRODUCTPRICE FROM PRODUCT WHERE CATEGORY LIKE :code || '%'", nativeQuery = true)
    public List<ProductProjection> queryListCProduct(@Param("code") Long code);

}
