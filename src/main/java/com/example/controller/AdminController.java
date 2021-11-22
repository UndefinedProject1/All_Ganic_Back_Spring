package com.example.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.entity.Question;
import com.example.entity.SubImage;
import com.example.jwt.JwtUtil;
import com.example.service.BrandService;
import com.example.service.CartItemService;
import com.example.service.CategoryService;
import com.example.service.MainService;
import com.example.service.MemberService;
import com.example.service.ProductService;
import com.example.service.QuestionService;
import com.example.service.SubImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    MainService mService;

    @Autowired
    MemberService mServiece;

    @Autowired
    ProductService pService;

    @Autowired
    SubImageService sImageService;

    @Autowired
    QuestionService qService;    

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
    // 127.0.0.1:8080/REST/api/select_image?no=번호
    // <img src="/select_image?no=12" />
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
    //127.0.0.1:8080/REST/api/admin/product_delete?no=167
    // {"productcode":33}
    @DeleteMapping(value = "/admin/product_delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productDelete(@RequestParam("no") long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            mService.deleteProductTransaction(no);
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
        @RequestParam(name = "subcode")long[] subcode,
        @RequestHeader("token") String token) { 
        Map<String, Object> map = new HashMap<>();
        
        try{
            //물품코드 가져오기
            Product product = pService.selectProduct(no);
            List<SubImage> list = new ArrayList<>();
            for(int i=0; i<files.length; i++){
                SubImage subImage2 = new SubImage();
                subImage2.setProduct(product);
                subImage2.setSubcode(subcode[i]);
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

    // 문의글 답글 작성
    // 127.0.0.1:8080/REST/api/admin/question/reply/insert?code=
    // 여기서 code는 문의코드
    @RequestMapping(value = "/admin/question/reply/insert", method = {
        RequestMethod.POST }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> replyInsertPOST(@RequestBody Question question, @RequestParam("code") long code,
    @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Question question1 = qService.selectQuestion(code);
            question1.setAnswercontent(question.getAnswercontent());

            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

            question1.setAnswerdate(date.format(today));

            question1.setQuestionreply(true);
            qService.updateQuestion(question1);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 매일 그날과 이전 5일간의 날짜와 payhistory개수를 리스트로 출력
    // 127.0.0.1:8080/REST/api/admin/payhistory/list
    @GetMapping(value = "/admin/payhistory/list")
    public Map<String, Object> payhistoryListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date now1 = new Date();

            for(int i=0; i<5; i++){
                Calendar cal = Calendar.getInstance(); 
                cal.setTime(now1);
                cal.add(Calendar.DATE, -i);
                Date date = df.parse(df.format(cal.getTime()));
                pService.updateDate(i, date);
            }

            List<Map<String, Object>> list = pService.selectSaleRate();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 브랜드별 점유율
    // 127.0.0.1:8080/REST/api/admin/brand/share
    @GetMapping(value = "/admin/brand/share")
    public Map<String, Object> brandShareGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> list = pService.selectBrandShare();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 브랜드별 판매량
    // 127.0.0.1:8080/REST/api/admin/brand/sell
    @GetMapping(value = "/admin/brand/sell")
    public Map<String, Object> brandSellGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> list = pService.selectBrandSell();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 카테고리별 판매량
    // 127.0.0.1:8080/REST/api/admin/cate/sell
    @GetMapping(value = "/admin/cate/sell")
    public Map<String, Object> cateSellGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> list = pService.selectCateSell();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }
}
