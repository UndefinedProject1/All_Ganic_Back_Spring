package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.entity.ProductProjection;
import com.example.jwt.JwtUtil;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MemberServiece;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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

    // 물품 삭제
    // 127.0.0.1:8080/REST/admin/product_delete
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

    // 물품 수정
    // 127.0.0.1:8080/REST/admin/product_update
    @RequestMapping(value = "/admin/product_update", method = {
            RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productUpdate(@ModelAttribute Product product, @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            Product product2 = pService.getProductOne(product.getProductcode());
            product2.setProductname(product.getProductname());
            product2.setProductprice(product.getProductprice());
            product2.setImage(file.getBytes());
            product2.setImagename(file.getOriginalFilename());
            product2.setImagetype(file.getContentType());

            pService.updteProduct(product2);
            map.put("result", 1);
        } catch (Exception e) {
            map.put("result", e.hashCode());
        }
        return map;
    }

    // // 127.0.0.1:8080/ROOT/board/select => title=
    // // 127.0.0.1:8080/ROOT/board/select?title=dkdjd&page=1
    // @RequestMapping(value = "/select", method = RequestMethod.GET)
    // public String select(Model model, @RequestParam(name = "title", required =
    // false, defaultValue = "") String title,
    // @RequestParam(name = "page", required = false, defaultValue = "0") int page)
    // {

    // if (page == 0) {
    // // 강제로 페이지 전환
    // return "redirect:select?page=1";
    // }
    // // 글번호 최신순으로
    // // List<Board> list1 = bRepository.findAll(Sort.by(Sort.Direction.DESC,
    // "no"));

    // // 페이지숫자(0부터), 개수
    // PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);
    // List<Board> list1 = bRepository.findByTitleContainingOrderByNoDesc(title,
    // pageRequest);

    // long cnt = bRepository.countByTitleContaining(title);
    // // 11개 1 2
    // // 23개 1 2 3
    // model.addAttribute("cnt", (cnt - 1) / PAGECNT + 1);

    // model.addAttribute("list", list1);
    // return "board_select";
    // }

    // @RequestMapping(value = "/list/product", method = {
    // RequestMethod.GET }, consumes = MediaType.ALL_VALUE, produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> ListProductGET() {
    // Map<String, Object> map = new HashMap<String, Object>();
    // try {
    // List<ProductProjection> list = pService.getListProduct();
    // map.put("list", list);
    // map.put("result", 1);
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("result", e.hashCode());
    // }
    // return map;
    // }

}
