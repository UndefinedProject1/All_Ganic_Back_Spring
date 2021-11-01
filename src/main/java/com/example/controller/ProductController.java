package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BrandProjection;
import com.example.entity.CategoryProjection;
import com.example.entity.Product;
import com.example.entity.ProductProjection;
import com.example.entity.SubImage;
import com.example.entity.SubImageProjection;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberServiece;
import com.example.service.ProductService;
import com.example.service.SubImageService;

import org.springframework.beans.factory.annotation.Autowired;
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
        @RequestParam("product") long product
        ) throws Exception {
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

    // 브랜드 전체 조회
    // 127.0.0.1:8080/REST/api/select_brand
    @RequestMapping(value = "/select_brand", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    // 127.0.0.1:8080/REST/api/select_bproduct?code=
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
    // 127.0.0.1:8080/REST/api/select_bproduct2?code=
    @RequestMapping(value = "/select_bproduct2", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectBProduct2GET( Model model,
    @RequestParam("code") long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectBProductLsit2(code);
            //System.out.println(list.get(0).getImage());
            model.addAttribute("list", list);
            // map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
    
    //카테고리 코드 별 제품 조회(jpa)
    // 127.0.0.1:8080/REST/api/select_cproduct2?code=
    @RequestMapping(value = "/select_cproduct2", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProduct2GET( Model model,
    @RequestParam(name = "code")long code) {
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
    // 127.0.0.1:8080/REST/api/select_cproduct?code=
    @RequestMapping(value = "/select_cproduct", method = {
        RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectCProductGET( Model model,
    @RequestParam("code") long code) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductProjection> list = pService.selectCProductLsit(code);
            model.addAttribute("list", list);
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    } 

    //물품 이미지 찾고 변환하기
    // 127.0.0.1:8080/REST/api/select_productimage?no=번호
    // <img src="/admin/select_image?no=12" />
    // no = 서브이미지에서 찾은 subcode
    @RequestMapping(value = "/select_productimage", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectProductimageFindGET(@RequestParam("no") long no) throws Exception {
        try {
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
}
