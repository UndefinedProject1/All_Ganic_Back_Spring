package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dto.ProductDto;
import com.example.entity.BrandProjection;
import com.example.entity.CategoryProjection;
import com.example.entity.Product;
import com.example.entity.SubImage;
import com.example.entity.SubImageProjection;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberService;
import com.example.service.ProductService;
import com.example.service.SubImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProductController {
    
    @Autowired
    BrandService bService;

    @Autowired
    CategoryService cService;

    @Autowired
    MemberService mServiece;

    @Autowired
    ProductService pService;

    @Autowired
    SubImageService sImageService;

    //물품 코드에 따른 서브 이미지들 코드 찾기
    //127.0.0.1:8080/REST/api/select_subimage?product=1
    @GetMapping(value = "/select_subimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectSubImageGET(@RequestParam("product") long product) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            List<SubImageProjection> list = sImageService.selectSubcode(product);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) { 
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 서브 이미지 찾고 변환하기
    // 127.0.0.1:8080/REST/api/select_subimage/find?no=번호
    // <img src="/admin/select_image?no=12" />
    // no = 서브이미지에서 찾은 subcode
    @GetMapping(value = "/select_subimage/find")
    public ResponseEntity<byte[]> selectSubImageFindGET(@RequestParam("no") long no) throws Exception {
        try {
            //서브이미지 코드 찾기
            SubImage subImage = sImageService.selectSubimg(no);
            if (subImage.getImage() != null) {
                HttpHeaders headers = new HttpHeaders();
                if (subImage.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (subImage.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (subImage.getImagetype().equals("image/git")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(subImage.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) { 
            e.printStackTrace();
            return null;
        }
    }

    //물품 목록 페이지
    //127.0.0.1:8080/REST/api/select_product?page=1&name=
    @GetMapping(value = "/select_product", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductListGET(@RequestParam(value = "page", defaultValue = "1")int page,
        @RequestParam(value = "name", defaultValue = "")String productname) {
        Map<String, Object> map = new HashMap<>();
        try {
            long start, end = 1;
            int count = pService.serchProductCount(productname);
            if(page == 1){
                start = 1;
                end = 1*16;
                List<ProductDto> list = pService.selectProductList(productname, start, end);
                map.put("list", list);
                map.put("result", 1);
            }
            else{
                start = (page-1)*16+1;
                end = page*16; 
                List<ProductDto> list = pService.selectProductList(productname, start, end);
                map.put("list", list);
                map.put("result", 1);
            }map.put("count", count);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //제품 이미지 찾고 변환하기
    // 127.0.0.1:8080/REST/api/select_productimage?no=제품코드
    // <img src="/select_productimage?no=12" />
    // no = Product에서 찾은 productcode
    @GetMapping(value = "/select_productimage")
    public ResponseEntity<byte[]> selectProductimageFindGET(@RequestParam("no") long no) throws Exception {
        try {
            //제품 코드 찾기
            Product product = pService.selectProduct(no);
            if (product.getImage() != null) {
                HttpHeaders headers = new HttpHeaders();
                if (product.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (product.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (product.getImagetype().equals("image/git")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(product.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) { 
            e.printStackTrace();
            return null;
        }
    }

    //제품 1개 조회 (상세 페이지)
    //127.0.0.1:8080/REST/api/product_one?code=
    @GetMapping(value = "/product_one", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productOneGET(@RequestParam("code") long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductDto product = pService.selectProductOne(code);
            map.put("product", product);
            map.put("imgurl", "/REST/api/select_productimage?no=" + code);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //카테고리 전체 조회
    //127.0.0.1:8080/REST/api/select_cate
    @GetMapping(value = "/select_cate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCateGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CategoryProjection> list = cService.selectCategoryList();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //카테고리 코드 별 카테고리 조회(jpa)
    // 127.0.0.1:8080/REST/api/select_catenum?code= 카테고리 코드
    @GetMapping(value = "/select_catenum", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCateNumGET(@RequestParam(name = "code")String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CategoryProjection> list = cService.selectCategoryNum(code+ "");
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //카테고리 코드 별 제품 조회(sql)
    // 127.0.0.1:8080/REST/api/select_cproduct?code= 카테고리 코드
    @GetMapping(value = "/select_cproduct", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProductGET(@RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductDto> list = pService.selectCProductLsit(code);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
        
    //카테고리 코드 별 제품 이름 순 조회(jpa)
    // 127.0.0.1:8080/REST/api/select_cproduct3?page=1&code=
    @GetMapping(value = "/select_cproduct3", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProduct2GET(@RequestParam(value = "page", defaultValue = "1")int page, @RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            long start, end = 1;
            int count = pService.selectCateProductCount(code);
            if(page == 1){
                start = 1;
                end = 1*16;
                List<ProductDto> list = pService.selectCProductLsit3(code, start, end);
                map.put("list", list);
                map.put("result", 1);
            }
            else{
                start = (page-1)*16+1;
                end = page*16; 
                List<ProductDto> list = pService.selectCProductLsit3(code, start, end);
                map.put("list", list);
                map.put("result", 1);
            }map.put("count", count);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    } 

    // 브랜드 전체 조회
    // 127.0.0.1:8080/REST/api/select_brand
    @GetMapping(value = "/select_brand", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBrandGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<BrandProjection> list = bService.selectBrandList();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //브랜드 코드 별 제품 조회(jpa)
    // 127.0.0.1:8080/REST/api/select_bproduct?code= 브랜드 코드
    @GetMapping(value = "/select_bproduct", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBProductGET(@RequestParam(name = "code")long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductDto> list = pService.selectBProductList(code);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

} 
