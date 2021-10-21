package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberServiece;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class AdminController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BrandService bService;

    @Autowired
    CategoryService cService;

    @Autowired
    MemberServiece mServiece;

    @Autowired
    ProductService pService;

    // 브랜드 추가
    // 127.0.0.1:8080/REST/api/admin/brand_insert
    @RequestMapping(value = "/admin/brand_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> brandInsertPOST(@ModelAttribute Brand brand, @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            brand.setImage(file.getBytes());
            brand.setImagename(file.getOriginalFilename());
            brand.setImagetype(file.getContentType());
            bService.insertBrand(brand);
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 카테고리 추가
    // 127.0.0.1:8080/REST/api/admin/category_insert
    @RequestMapping(value = "/admin/category_insert", method = {
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

    // 제품 추가
    // 127.0.0.1:8080/REST/api/admin/product_insert
    @RequestMapping(value = "/admin/product_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productInsertPOST(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            product.setImage(file.getBytes());
            product.setImagename(file.getOriginalFilename());
            product.setImagetype(file.getContentType());
            pService.insertProduct(product);

            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }


    //물품 삭제
    //127.0.0.1:8080/REST/api/admin/product_delete

    @RequestMapping(value = "/admin/product_delete", method = {
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


    //물품 수정
    //127.0.0.1:8080/REST/api/admin/product_update
    @RequestMapping(value = "/admin/product_update", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Map<String, Object> productUpdate(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token) { 
            Map<String, Object> map = new HashMap<>();
            try{ 
                Product product2 = pService.getProductOne(product.getProductcode());
                product2.setProductname(product.getProductname());
                product2.setProductcontent(product.getProductcontent());
                product2.setProductprice(product.getProductprice());
                product2.setImage(file.getBytes());
                product2.setImagename(file.getOriginalFilename());
                product2.setImagetype(file.getContentType());

                pService.updteProduct(product2);
                map.put("result",1);
            }
            catch(Exception e){
                map.put("result",e.hashCode());
            }
            return map;
        }

    //     @RequestMapping(value = "/admin/product_update", method = {
    //         RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //     public Map<String, Object> memberUpdate(@RequestBody Map<String, Object> mapobj,
    //         @RequestHeader("token") String token) {

    //     Map<String, Object> map = new HashMap<>();
    //     try {
    //         // @RequestBody Map<>으로 데이터 받는부분
    //         Long productcode =(long) mapobj.get("productcode"); // token을 통해 회원정보(이메일) 찾기
    //         String productname = (String) mapobj.get("productname"); // 이름
    //         String productcontent = (String) mapobj.get("productcontent"); // 내용
    //         Long productprice = Long.parseLong(String.valueOf(mapobj.get("post"))); // 가격

    //         // 아이디를 이용해 기존 정보 가져오기
    //         Product product = pService.getProductOne(productcode);
    //         product.setProductname(productname);
    //         product.setProductcontent(productcontent);
    //         product.setProductprice(productprice);
            
    //         pService.updteProduct(product);
    //         map.put("result", 1L);
            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         map.put("result", e.hashCode());
    //     }
    //     return map;
    // }

}
