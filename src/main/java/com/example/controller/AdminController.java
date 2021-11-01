package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.entity.SubImage;
import com.example.jwt.JwtUtil;
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

    @Autowired
    SubImageService sImageService;

    // // 상수
    // @Value("${board.page.count}")
    // private int PAGECNT;

    // 브랜드 추가
    // 127.0.0.1:8080/REST/api/admin/brand_insert
    @RequestMapping(value = "/admin/brand_insert", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> brandInsertPOST(@ModelAttribute Brand brand, @RequestParam("file") MultipartFile file,
    @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            brand.setBrandimage(file.getBytes());
            brand.setImagename(file.getOriginalFilename());
            brand.setImagetype(file.getContentType());
            bService.insertBrand(brand);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //브랜드 중복 체크
    // {"brandcode":1} 있으면 1 리턴, 없으면 0 리턴
    // 127.0.0.1:8080/REST/api/admin/checkbrand
    @RequestMapping(value = "/admin/checkbrand", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CheckBrandPOST(@RequestBody Brand brand) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int count = bService.checkBrandCode(brand.getBrandcode());
            //System.out.println(count);
            if (count == 0) {
                map.put("result", 0L);
            } else {
                map.put("result", 1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 브랜드 이미지 찾기
    // 127.0.0.1:8080/REST/api/admin/select_image?no=번호
    // <img src="/admin/select_image?no=12" />
    @RequestMapping(value = "/admin/select_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("no") long no) throws Exception {
        try {
            Brand brand = bService.selectBrand(no);
            if (brand.getBrandimage() != null) {
                HttpHeaders headers = new HttpHeaders();
                if (brand.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (brand.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (brand.getImagetype().equals("image/git")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(brand.getBrandimage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) { 
            e.printStackTrace();
            return null;
        }
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
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //카테고리 중복 체크
    // {"categorycode":"100101"} 있으면 1 리턴, 없으면 0 리턴
    // 127.0.0.1:8080/REST/api/admin/checkcate
    @RequestMapping(value = "/admin/checkcate", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> CheckCatePOST(@RequestBody Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int count = cService.checkCateCode(category.getCategorycode());
            //System.out.println(count);
            if (count == 0) {
                map.put("result", 0L);
            } else {
                map.put("result", 1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 제품 추가
    // 127.0.0.1:8080/REST/api/admin/product_insert
    // form-data => productname, productprice, file, brand, category
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
            map.put("code", product.getProductcode());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //물품 삭제
    //127.0.0.1:8080/REST/api/admin/product_delete
    // {"productcode":33}
    @RequestMapping(value = "/admin/product_delete", method = {
            RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productDelete(@RequestBody Product product, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            pService.deleteProduct(product.getProductcode());
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }

        return map;
    }

    //물품 수정
    //127.0.0.1:8080/REST/api/admin/product_update
    // formdata =>productcode, productname, productprice, file, brand, category
    @RequestMapping(value = "/admin/product_update", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productUpdate(@ModelAttribute Product product,
        @RequestParam("file") MultipartFile file,
        @RequestHeader("token") String token) { 
        Map<String, Object> map = new HashMap<>();
        try{ 
            Product product2 = pService.selectProduct(product.getProductcode());
            product2.setProductname(product.getProductname());
            product2.setProductprice(product.getProductprice());
            product2.setImage(file.getBytes());
            product2.setImagename(file.getOriginalFilename());
            product2.setImagetype(file.getContentType());
            product2.setBrand(product.getBrand());
            product2.setCategory(product.getCategory());
            pService.updteProduct(product2);
            map.put("result",1);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("result",e.hashCode());
        }
        return map;
    }

    // 서브이미지 등록
    //127.0.0.1:8080/REST/api/admin/subimg_insert?product=1
    //form-data => file
    @RequestMapping(value = "/admin/subimg_insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> subimgInsertPOST( 
    @RequestParam("file") MultipartFile[] files,
    @RequestParam(name = "product")long no,
    @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            //물품코드 가져오기
            Product product = pService.selectProduct(no);
            List<SubImage> list = new ArrayList<>();
            for(int i=0; i<files.length; i++){
                SubImage subImage2 = new SubImage();
                subImage2.setProduct(product);
                subImage2.setImage(files[i].getBytes());
                subImage2.setImagename(files[i].getOriginalFilename());
                subImage2.setImagetype(files[i].getContentType());
                list.add(subImage2);
            }
            sImageService.insertSubimg(list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    //서브이미지 수정하기
    //127.0.0.1:8080/REST/api/admin/subimg_update?product=1&subcode=34
    //form-data => file
    @RequestMapping(value = "/admin/subimg_update", method = {
        RequestMethod.POST}, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> subimgUpdate(
        @RequestParam("file") MultipartFile[] files,
        @RequestParam(name = "product")long no,
        @RequestParam(name = "subcode")long subcode,
        @RequestHeader("token") String token) { 
        Map<String, Object> map = new HashMap<>();
        
        try{
            //물품코드 가져오기
            Product product = pService.selectProduct(no);
            List<SubImage> list = new ArrayList<>();
            for(int i=0; i<files.length; i++){
                SubImage subImage2 = new SubImage();
                subImage2.setProduct(product);
                subImage2.setSubcode(subcode);
                subImage2.setImage(files[i].getBytes());
                subImage2.setImagename(files[i].getOriginalFilename());
                subImage2.setImagetype(files[i].getContentType());
                list.add(subImage2);
            }
            //일괄 수정
            sImageService.updateSubimg(list);
            map.put("result",1);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("result",e.hashCode());
        }
        return map;
    }

    //서브이미지 삭제하기
    //127.0.0.1:8080/REST/api/admin/subimg_delete
    // {"subcode":33}
    @RequestMapping(value = "/admin/subimg_delete", method = {
        RequestMethod.DELETE }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> subimgDelete(@RequestBody SubImage subImage, 
    @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            sImageService.deleteSubimg(subImage.getSubcode());
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
