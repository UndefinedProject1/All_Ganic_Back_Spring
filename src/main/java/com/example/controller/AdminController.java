package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dto.MailDto;
import com.example.entity.Brand;
import com.example.entity.Category;
import com.example.entity.Product;
import com.example.entity.Question;
import com.example.entity.SubImage;
import com.example.jwt.JwtUtil;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.MainService;
import com.example.service.MemberService;
import com.example.service.ProductService;
import com.example.service.QuestionService;
import com.example.service.SubImageService;
import com.example.service.sendEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
    MainService mainService;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService pService;

    @Autowired
    SubImageService sImageService;

    @Autowired
    QuestionService qService;   
    
    @Autowired
    sendEmailService sendEmailService;

    // 브랜드 추가
    // 127.0.0.1:8080/REST/api/admin/brand_insert
    @PostMapping(value = "/admin/brand_insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/admin/checkbrand", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value = "/select_image")
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
    @PostMapping(value = "/admin/category_insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    // 카테고리 중복 체크
    // {"categorycode":"100101"} 있으면 1 리턴, 없으면 0 리턴
    // 127.0.0.1:8080/REST/api/admin/checkcate
    @PostMapping(value = "/admin/checkcate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/admin/product_insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
            mainService.deleteProductTransaction(no);
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
    @PostMapping(value = "/admin/product_update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/admin/subimg_insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> subimgInsertPOST(@RequestParam("file") MultipartFile[] files, @RequestParam(name = "product")long no,
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
    @PostMapping(value = "/admin/subimg_update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> subimgUpdate(@RequestParam("file") MultipartFile[] files, @RequestParam(name = "product")long no,
        @RequestParam(name = "subcode")long[] subcode, @RequestHeader("token") String token) { 
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
    @DeleteMapping(value = "/admin/subimg_delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/admin/question/reply/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    // 회원관리(각 회원의 구매, 신고, 위조금액 적발 횟수)
    // 127.0.0.1:8080/REST/api/admin/member/list
    @GetMapping(value = "admin/member/list")
    public Map<String, Object> memberListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> list = memberService.adminMemberList();
            map.put("list", list);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", e.hashCode());
        }
        return map;
    }

    // 위조금액 적발 3회인 회원 수
    // 127.0.0.1:8080/REST/api/admin/forge/member
    @GetMapping(value = "/admin/forge/member")
    public int forgeMemberGET(@RequestHeader("token") String token) {
        int result = 0;
        try {
            int count = memberService.forgeMoneyThree();
            result = count;
        } catch (Exception e) {
            e.printStackTrace();
            result = e.hashCode();
        }
        return result;
    }

    // 관리자가 회원을 삭제 시킬 때(이메일 먼저 전송되고 나서 발동)
    // 127.0.0.1:8080/REST/api/admin/delete/member
    @DeleteMapping(value = "/admin/delete/member", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int deleteMember(@RequestHeader("token") String token, @RequestBody Map<String, Object> body) {
        int result = 0;
        String userEmail = (String) body.get("useremail");
        try {
            if(userEmail != null){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                Date date = df.parse(df.format(now.getTime()));
                int state = memberService.updateLeaveMember(date, userEmail);
                if(state == 1){
                    mainService.deleteMemberTransaction(userEmail, date);
                    result = 1;
                }
                else{
                    result = 300;
                }
            }
            else{
                result = 400;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.hashCode();
        }
        return result;
    }

    // 위조금액 3번 적발 시 삭제하면서 발송되는 메일
    // 127.0.0.1:8080/REST/api/admin/delete/sendEmail
    @PostMapping(value = "/admin/delete/sendEmail", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int deleteSendEmail(@RequestHeader("token") String token, @RequestBody Map<String, Object> body, @RequestParam("kind") long kind){
        int result = 0;
        try{
            String userEmail = (String) body.get("useremail");
            String userName = (String) body.get("username");
            if(kind == 1){
                MailDto dto = sendEmailService.createMailAndMalignityMember(userEmail, userName);
                sendEmailService.imgMailSend(dto);
                result = 1;
            }
            else if(kind == 2){
                MailDto dto = sendEmailService.createMailAndCounterfeitMember(userEmail, userName);
                sendEmailService.imgMailSend(dto);
                result = 1;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = e.hashCode();
        }
        return result;
    }
}
