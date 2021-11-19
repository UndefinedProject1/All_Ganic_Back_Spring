package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.entity.BrandCountProjection;
import com.example.entity.Product;
import com.example.entity.ProductListProjection;
import com.example.entity.ProductProjection;
import com.example.mappers.ProductMapper;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    ProductMapper pMapper;

    //제품 추가
    public void insertProduct(Product product){
        pRepository.save(product);
    }

    //제품 삭제
    @Override
    public void deleteProduct(Long product) {
        pRepository.deleteById(product);
    }

    //제품 수정
    @Override
    public void updteProduct(Product product) {
        pRepository.save(product);
    }

    //제품정보 가져오기, 제품 이미지 찾고 변환하기
    @Override
    public Product selectProduct(long code) {
        Optional<Product> product = pRepository.findById(code);
        return product.orElse(null);
    }

    //제품 전체 조회
    @Override
    public List<ProductProjection> selectProductList() {
        return pRepository.findAllByOrderByProductcodeAsc();
    }

    //브랜드 코드 별 제품 조회(jpa)
    @Override
    public List<ProductProjection> selectBProductList(long code) {
        return pRepository.findByBrand_Brandcode(code);
    }

    //브랜드 코드 별 제품 조회(sql)
    @Override
    public List<ProductProjection> selectBProductLsit2(Long code) {
        return pRepository.queryListBProduct(code);
    }

    //카테고리 코드 별 제품 조회(sql)
    @Override
    public List<ProductListProjection> selectCProductLsit(String code) {
        return pRepository.queryListCProduct(code);
    }

    //카테고리 코드 별 제품 조회(jpa)
    @Override
    public List<ProductProjection> selectCProductLsit2(String code) {
        return pRepository.findByCategory_CategorycodeStartingWith(code);
    }

    //제품 1개 조회 (상세 페이지)
    @Override
    public ProductListProjection selectProductOne(long code) {
        return pRepository.querySelectProduct(code);
    }

    //제품 전체 목록(이름에 단어가 포함하는 + 제품이름 오름차순 정렬 + 페이지 네이션)
    @Override
    public List<ProductProjection> selectProductList2(String productname, Pageable pageable) {
        return pRepository.findByProductnameIgnoreCaseContainingOrderByProductnameAsc(productname, pageable);
    }

    //카테고리 코드 별 제품 이름 순 조회(jpa)
    @Override
    public List<ProductProjection> selectCProductLsit3(String code, Pageable pageable) {
        return pRepository.findByCategory_CategorycodeStartingWithOrderByProductnameAsc(code, pageable);
    }

    //브랜드 코드 별 제품 이름순 조회(jpa)
    @Override
    public List<ProductProjection> selectBProductLsit3(long code, Pageable pageable) {
        return pRepository.findByBrand_BrandcodeOrderByProductnameAsc(code, pageable);
    }

    @Override
    public List<BrandCountProjection> selectBrandCount() {
        return pRepository.findGroupByProductWithNativeQuery();
    }

    // 브랜드별 점유율
    @Override
    public List<Map<String, Object>> selectBrandShare() {
        return pMapper.selectBrandShare();
    }

    // 브랜드별 판매량
    @Override
    public List<Map<String, Object>> selectBrandSell() {
        return pMapper.selectBrandSell();
    }

    // 카테고리별 판매량
    @Override
    public List<Map<String, Object>> selectCateSell() {
        return pMapper.selectCateSell();
    }

    // 판매량 조회를 위한 날짜 업데이트
    @Override
    public int updateDate(long no, Date date) {
        return pMapper.InsertDate(no, date);        
    }

    // 최근 5일간의 판매량조회
    @Override
    public List<Map<String, Object>> selectSaleRate() {
        return pMapper.selectSalesRate();
    }

    // 물품 삭제 시 메인이미지 null로 변경
    @Override
    public int updateMainImg(long no) {
        return pMapper.updateMain(no);
    }

    @Override
    public int deleteSubImg(long no) {
        return pMapper.deleteSubImg(no);
    }

    
}
