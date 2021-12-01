package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.mappers.ProductMapper;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    //브랜드 코드 별 제품 조회(jpa)
    @Override
    public List<ProductDto> selectBProductList(long code) {
        return pMapper.selectBrandProductList(code);
    }

    // 브랜드별 제품 개수
    @Override
    public int selectBrandProductCount(Long code) {
        return pMapper.selectBrandProduct(code);
    }
    

    //카테고리 코드 별 제품 개수
    @Override
    public int selectCateProductCount(String code) {
        return pMapper.selectCateProduct(code);
    }

    //카테고리 코드 별 제품 조회(sql)
    @Override
    public List<ProductDto> selectCProductLsit(String code) {
        return pMapper.queryListCProduct(code);
    }

    //제품 1개 조회 (상세 페이지)
    @Override
    public ProductDto selectProductOne(long code) {
        return pMapper.querySelectProduct(code);
    }

    // 검색을 위한 페이지네이션 개수
    @Override
    public int serchProductCount(String name) {
        return pMapper.serchProductCount(name);
    }

    //제품 전체 목록(이름에 단어가 포함하는 + 제품이름 오름차순 정렬 + 페이지 네이션)
    @Override
    public List<ProductDto> selectProductList(String name, long start, long end) {
        return pMapper.serchProductList(name, start, end);
    }

    //카테고리 코드 별 제품 이름 순 조회(jpa)
    @Override
    public List<ProductDto> selectCProductLsit3(String code, long start, long end) {
        return pMapper.selectCateProductList(code, start, end);
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

    // 해당 회원의 최근 구매 물품코드 리턴
    @Override
    public Long latestOrder(String email) {
        return pMapper.latestOrder(email);
    }

    // 랜덤 출력
    @Override
    public Long randomProduct(Long code) {
        return pMapper.randomProduct(code);
    }

    // 판매가능한 상품인지 확인
    @Override
    public int checkUnsalableProduct(Long code) {
        return 0;
    }

}
