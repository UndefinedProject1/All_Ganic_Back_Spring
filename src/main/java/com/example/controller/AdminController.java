package com.example.controller;

import java.io.InputStream;
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
    CategoryService cService;

    @Autowired
    MemberServiece mServiece;

    @Autowired
    ProductService pService;

    // 브랜드 추가
    // 127.0.0.1:8080/REST/admin/brand_insert
    @RequestMapping(value = "/brand_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> brandInsertPOST(@RequestBody Brand brand, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            brand.setBrandimage("classpath:/static/brand/"+brand.getBrandimage());
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
            System.out.println(brand.toString());
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

    // 제품 추가
    // 127.0.0.1:8080/REST/admin/product_insert
    @RequestMapping(value = "/product_insert", method = {
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
    //127.0.0.1:8080/REST/admin/product_delete
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


    //물품 수정
    //127.0.0.1:8080/REST/admin/product_update
    @RequestMapping(value = "/product_update", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Map<String, Object> productUpdate(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token) { 
            Map<String, Object> map = new HashMap<>();
            try{ 
                Product product2 = pService.getProductOne(product.getProductcode());
                product2.setProductname(product.getProductname());
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

    

}
