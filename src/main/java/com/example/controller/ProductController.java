package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dto.ProductDto;
import com.example.dto.ProductListDto;
import com.example.entity.BrandCountProjection;
import com.example.entity.BrandProjection;
import com.example.entity.CategoryProjection;
import com.example.entity.Product;
import com.example.entity.ProductListProjection;
import com.example.entity.ProductProjection;
import com.example.entity.SubImage;
import com.example.entity.SubImageProjection;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberServiece;
import com.example.service.ProductService;
import com.example.service.SubImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    MemberServiece mServiece;

    @Autowired
    ProductService pService;

    @Autowired
    SubImageService sImageService;

    //물품 코드에 따른 서브 이미지들 코드 찾기
    //127.0.0.1:8080/REST/api/select_subimage?product=1
    @RequestMapping(value = "/select_subimage", method = {
        RequestMethod.GET}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectSubImageGET(Model model,
        @RequestParam("product") long product) throws Exception {
            Map<String, Object> map = new HashMap<>();
        try {
            List<SubImageProjection> list = sImageService.selectSubcode(product);
            model.addAttribute("list", list);
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
    @RequestMapping(value = "/select_subimage/find", method = RequestMethod.GET)
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

    //물품 전체 조회
    //127.0.0.1:8080/REST/api/select_product
    @RequestMapping(value = "/select_product", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductGET( Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectProductList();
            model.addAttribute("list", list);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
    //물품 목록 페이지
    //127.0.0.1:8080/REST/api/select_product2?page=1&name=
    @RequestMapping(value = "/select_product2", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectProductListGET(
        @RequestParam(value = "page", defaultValue = "1")int page,
        @RequestParam(value = "name", defaultValue = "")String productname) {
        //페이지 네이션 처리
        PageRequest pageable = PageRequest.of(page-1, 16);
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectProductList2(productname, pageable);
            map.put("list", list);
            map.put("result", 1);
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
    @RequestMapping(value = "/select_productimage", method = RequestMethod.GET)
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
    @RequestMapping(value = "/product_one", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productOneGET( Model model,
    @RequestParam("code") long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductProjection product = pService.selectProductOne(code);
            model.addAttribute("product", product);
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
    @RequestMapping(value = "/select_cate", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCateGET( Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CategoryProjection> list = cService.selectCategoryList();
            model.addAttribute("list", list);
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
    @RequestMapping(value = "/select_catenum", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCateNumGET( Model model,
    @RequestParam(name = "code")String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<CategoryProjection> list = cService.selectCategoryNum(code+ "");
            model.addAttribute("list", list);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
        
    //카테고리 코드 별 제품 조회(jpa)
    // 127.0.0.1:8080/REST/api/select_cproduct2?code= 카테고리 코드
    @RequestMapping(value = "/select_cproduct2", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProduct2GET( Model model,
    @RequestParam(name = "code")String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectCProductLsit2(code+"");
            model.addAttribute("list", list);
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
    @RequestMapping(value = "/select_cproduct", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProductGET( Model model,
    @RequestParam("code") String code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductListProjection> list = pService.selectCProductLsit(code);
            model.addAttribute("list", list);
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
    @RequestMapping(value = "/select_cproduct3", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProduct2GET(
    @RequestParam(value = "page", defaultValue = "1")int page,
    @RequestParam("code") String code) {
        //페이지 네이션 처리
        PageRequest pageable = PageRequest.of(page-1, 16);
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectCProductLsit3(code, pageable);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    } 
 
    // 브랜드 전체 조회
    // 127.0.0.1:8080/REST/api/select_brand
    @RequestMapping(value = "/select_brand", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBrandGET( Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<BrandProjection> list = bService.selectBrandList();
            model.addAttribute("list", list);
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
    @RequestMapping(value = "/select_bproduct", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBProductGET( Model model,
    @RequestParam(name = "code")long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectBProductList(code);
            model.addAttribute("list", list);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
    //브랜드 코드 별 제품 조회(sql)
    // 127.0.0.1:8080/REST/api/select_bproduct2?code= 브랜드 코드
    @RequestMapping(value = "/select_bproduct2", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBProduct2GET( Model model,
    @RequestParam("code") long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectBProductLsit2(code);
            //System.out.println(list.get(0).getImage());
            model.addAttribute("list", list);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
            
    //브랜드 코드 별 제품 이름 순 조회(jpa)
    // 127.0.0.1:8080/REST/api/select_bproduct3?page=1&code=
    @RequestMapping(value = "/select_bproduct3", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBProduct3GET(
    @RequestParam(value = "page", defaultValue = "1")int page,
    @RequestParam("code") Long code) {
        //페이지 네이션 처리
        PageRequest pageable = PageRequest.of(page-1, 16);
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectBProductLsit3(code, pageable);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 브랜드별 물품 개수(list에 brand, 개수 리턴)
    // 127.0.0.1:8080/REST/api/select/brand/count
    @RequestMapping(value = "/select/brand/count", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBrandCountGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<BrandCountProjection> list = pService.selectBrandCount();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
} 
