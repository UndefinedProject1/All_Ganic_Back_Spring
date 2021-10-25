package com.example.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.repository.BrandRepository;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberServiece;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BrandService bService;

    @Autowired
    BrandRepository bRepository;

    @Autowired
    CategoryService cService;

    @Autowired
    MemberServiece mServiece;

    @Autowired
    ProductService pService;

    // 브랜드 추가
    // 127.0.0.1:8080/REST/admin/brand_insert
    // {"brandname":"오틀리", "brandimage":"오틀리.PNG"}
    @RequestMapping(value = "/brand_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> brandInsertPOST(@RequestBody Brand brand, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            brand.setBrandimage("classpath:/static/brand/" + brand.getBrandimage());
            bService.insertBrand(brand);
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 브랜드 이미지 찾기
    // 127.0.0.1:8080/REST/admin/select_image?no=번호
    // <img src="/admin/select_image?no=12" />
    @RequestMapping(value = "/select_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("no") long no) throws Exception {
        try {
            Brand brand = bService.selectBrand(no);
            // System.out.println(brand.toString());
            if (brand.getBrandimage() != null) {
                HttpHeaders headers = new HttpHeaders();
                InputStream is = resourceLoader.getResource(brand.getBrandimage()).getInputStream();

                headers.setContentType(MediaType.IMAGE_PNG);
                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 카테고리 추가
    // 127.0.0.1:8080/REST/admin/category_insert
    // {"categorycode":400425, "categoryname":"핸드워시"}
    @RequestMapping(value = "/category_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> brandInsertPOST(@RequestBody Category category, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            cService.insertCategory(category);
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 물품 추가
    // 127.0.0.1:8080/REST/admin/product_insert
    // {"productname":"a", "productprice":123, "productcontent":"내용",
    // "productimage":"이미지.PNG",
    // "brand":{"brandcode":1}, "category":{"categorycode":100101}}
    @RequestMapping(value = "/product_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@RequestBody Product product, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 브랜드, 카테고리 id 확인
            // System.out.println(product.getBrand().getBrandcode());
            // System.out.println(product.getCategory().getCategorycode());
            product.setProductimage("classpath:/static/product/" + product.getProductimage());
            pService.insertProduct(product);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 물품 이미지 찾기
    // 127.0.0.1:8080/REST/admin/select_product?no=번호
    // <img src="/admin/select_image?no=12" />
    @RequestMapping(value = "/select_product", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectProductImage(@RequestParam("no") long no) throws Exception {
        try {
            Product product = pService.selectProduct(no);
            // Brand brand = bService.selectBrand(no);
            // System.out.println(brand.toString());
            if (product.getProductimage() != null) {
                HttpHeaders headers = new HttpHeaders();
                InputStream is = resourceLoader.getResource(product.getProductimage()).getInputStream();

                headers.setContentType(MediaType.IMAGE_PNG);
                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 물품 삭제
    // 127.0.0.1:8080/REST/admin/product_delete
    // {"productcode":1}
    @RequestMapping(value = "/product_delete", method = {
            RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productDelete(@RequestBody Product product, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            pService.deleteProduct(product.getProductcode());
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 물품 수정
    // 127.0.0.1:8080/REST/admin/product_update
    // {"productcode":2, "productname":"수정", "productprice":1000,
    // "productcontent":"수정", "productimage":"Nukak.PNG"}
    @RequestMapping(value = "/product_update", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productUpdate(@RequestBody Map<String, Object> mapobj,
            @RequestHeader("token") String token) {

        Map<String, Object> map = new HashMap<>();
        try {
            // @RequestBody Map<>으로 데이터 받는부분
            Long productcode = Long.parseLong(String.valueOf(mapobj.get("productcode"))); // 코드
            String productname = (String) mapobj.get("productname"); // 이름
            String productcontent = (String) mapobj.get("productcontent"); // 내용
            Long productprice = Long.parseLong(String.valueOf(mapobj.get("productprice"))); // 가격
            String productimage = (String) mapobj.get("productimage"); // 이미지

            // 물품 코드를 이용해 기존 정보 가져오기
            Product product = pService.selectProduct(productcode);
            product.setProductname(productname);
            product.setProductcontent(productcontent);
            product.setProductprice(productprice);
            product.setProductimage("classpath:/static/product/" + productimage);
            pService.updteProduct(product);
            map.put("result", 1L);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

}
