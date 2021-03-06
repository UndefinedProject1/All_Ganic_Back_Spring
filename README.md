# ๐ All_Ganic (Back End-Spring)

![header](https://capsule-render.vercel.app/api?type=soft&color=auto&height=300&section=header&text=ํจ์จ์ ์ด๊ณ %20์ผ๊ด์ฑ์๋%20์ฝ๋๋ก&desc=๊น๋ํ%20๋ฐ์ดํฐ%20์ ๋ฌ์%20์ง์ค&fontSize=50&descSize=20&descAlign=75)

## ๋ชฉ์ฐจ
1. [์์ฝ](#summary--์์ฝ)
2. [๊ฐ๋ฐํ๊ฒฝ](#tech--๊ฐ๋ฐํ๊ฒฝ)
3. [API](#Open-API--์คํ-api)
4. [์๋ฒ๊ตฌ์กฐ](#server-structure--์๋ฒ๊ตฌ์กฐ)
5. [ERD ์ค๊ณ](#erd-์ค๊ณ)
6. [๋ฌธ์ ํด๊ฒฐ](#Problems-and-Solutions--๋ฌธ์ -ํด๊ฒฐ)
7. [ํน์ง](#features--ํน์ง)

## Summary / ์์ฝ
- __ํ๋ก์ ํธ ๊ธฐ๊ฐ__ : 2021.10.04 - 2021.12.03
- __๋ฐฑ์๋ ๊ตฌ์ฑ__ : ์ ์งํฌ
- __๋ฉ์ธ ๊ฐ๋ฐ ํ๊ฒฝ__ : Spring Boot

## Tech / ๊ฐ๋ฐํ๊ฒฝ
- ![Java](https://img.shields.io/badge/-Java-007396?style=flat-square&logo=Java&logoColor=white) : ๋ฐฑ์๋ ๋ฉ์ธ ๊ฐ๋ฐ ํ๊ฒฝ
- ![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=Spring%20Boot&logoColor=white) : ํ๋ ์์ํฌ
- ![H2DB](https://img.shields.io/badge/-H2DB-22ADF6?style=flat-square&&logoColor=white) : DB
- ![MyBatis](https://img.shields.io/badge/-MyBatis-C9284D?style=flat-square&&logoColor=white) : DB์์ฒญ(SQL๋ฌธ)
- ![JPA](https://img.shields.io/badge/-JPA-D77310?style=flat-square&&logoColor=white) : DB์์ฒญ
- ![H2DB](https://img.shields.io/badge/-H2DB-22ADF6?style=flat-square&&logoColor=white) : DB

## Open API / ์คํ api
- [Iamport](https://github.com/iamport/iamport-rest-client-java-hc) : ๊ฒฐ์  api

## Server Structure / ์๋ฒ๊ตฌ์กฐ
![ํ๋ ์  ํ์ด์1](https://user-images.githubusercontent.com/85853167/147995935-a7cb34a9-0699-4763-ba5b-34d71b2becf6.png)
> Spring MVC๋ฅผ ๋ฐํ์ผ๋ก ์ ์์ด ๋์์ต๋๋ค. View์ธ Vew์์ Rest API๋ก ๋ฐฑ์ Controller์ ์์ฒญ์ด ์ค๋ฉด Service, ServiceImpl๋ฅผ ํตํด DB์ ์ ๊ทผํ๊ฒ ๋ฉ๋๋ค.
> ์ด ๋ Query๋ฌธ์ ์จ์ผํ๋ฉด Mybatis๋ก ์๋๋ฉด JPA๋ฅผ ์ด์ฉํ์ฌ ๋ฐ์ดํฐ์ ์ ๊ทผํฉ๋๋ค. ์ด ๋ ์ด๋ค ๋ฐฉ๋ฒ์ผ๋ก ์ ๊ทผํ๋์ง, ์ด๋ค ํํ์ ๋ฐ์ดํฐ๊ฐ ๋ฐ์์ค๋์ง์ ๋ฐ๋ผ Entity, DTO, Projection์ ๋ฐ์ดํฐ๋ฅผ ๋ด๊ฒ ๋ฉ๋๋ค.


## ERD ์ค๊ณ
![์บก์ฒ](https://user-images.githubusercontent.com/85853167/147996583-40b5b84d-f71e-420f-a659-2c9697015a9a.PNG)
์ด 15๊ฐ์ ํ์ด๋ธ์ด ์์ผ๋ฉฐ ADMIN, MEMBER๋ก ๊ตฌ๋ถํ์ฌ ์ค๊ณ๋ฅผ ํ์ต๋๋ค. ์ค๊ณ์ ๋ํ ์ ์ ์๊ฐ์ด๋ ๊ตฌ์กฐ๋ฅผ ๋ ์์ธํ ๋ณด๊ณ ์ถ์ผ์๋ค๋ฉด ๐๐ผ[ERD COLUD](https://www.erdcloud.com/d/X52ATW8iNCRWnrLGW)๋ฅผ ๋๋ฌ์ฃผ์ธ์.

## Problems and Solutions / ๋ฌธ์  ํด๊ฒฐ
### ์นด์นด์ค ์ ์ ์ ๊ธฐ์กด ํ์๊ณผ์ ํผ๋
#### 1. ๋ฌธ์ ์ ์
- ๊ธฐ์กดํ์๊ณผ ์นด์นด์คํก ํ์์ ์ด๋ฉ์ผ์ด ๊ฐ์ ์ ๊ตฌ๋ถ๋ถ๊ฐ 

#### 2. ์ฌ์ค์์ง
- ๊ธฐ์กด ํ์์ ์ด๋ฉ์ผ์ด ์นด์นด์คํก ๊ณ์ ์ ์ด๋ฉ์ผ๊ณผ ๋์ผ ์ ์นด์นด์คํก ๋ก๊ทธ์ธ๊ณผ ์ฌ์ดํธ ๋ด๋ถ ๋ก๊ทธ์ธ ๊ตฌ๋ถ์์ด ๋ก๊ทธ์ธ ์๋๊ฐ ๊ฐ๋ฅํด์ง
- ๋น๋ฐ๋ฒํธ ์ฐพ๊ธฐ์ ๊ฐ์ ๊ธฐ๋ฅ์ ๊ธฐ์กดํ์๋ง ๊ฐ๋ฅ(์นด์นด์คํก์ ์ ์  ๊ตฌ๋ณ์ ์ํด ์ํธ๊ฐ ๋ฐ๋ก ์ ํด์ ธ์์), ์นด์นด์คํก ๋ก๊ทธ์ธ์ ํ๋ ค๋ค ์ํธ๊ฐ ํ๋ ธ๋ค๋ ์๋์ ๋ณด๊ณ  ์คํดํ์ฌ ์ํธ๋ฅผ ๋ณ๊ฒฝํ  ์ ์์

#### 3. ์์ธ์ถ๋ก 
- ๋ก๊ทธ์ธ ์ sns์ผ๋ก ์๋ํ๋ ค๋ ๊ฒ์ธ์ง ๊ตฌ๋ถ ์์

#### 4. ์กฐ์ฌ๋ฐฉ๋ฒ๊ฒฐ์ 
- ์นด์นด์คํก ๋ก๊ทธ์ธ์ธ์ง ๊ตฌ๋ถํ๋ ๊ฐ์ Param์ผ๋ก ์ ๋ฌ๋ฐ๊ณ , ๋ด๋ถ security bcpe match๊ธฐ๋ฅ์ผ๋ก ์ง์ ๋ ์นด์นด์คํก ๋น๋ฐ๋ฒํธ์ ๋น๊ตํด ๋ง๋์ง๋ฅผ ๊ตฌ๋ถ

#### 5. ์กฐ์ฌ๋ฐฉ๋ฒ๊ตฌํ
``` javascript
// ๋ก๊ทธ์ธ
// POST 127.0.0.1:8080/REST/api/member/login?sns=true
@PostMapping(value = "/member/login", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> memberLoginPOST(@RequestBody Member member, @RequestParam(name = "sns") Boolean sns) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        int check = mServiece.leaveMemberCheck(member.getUseremail());
        if(check == 1){ // ํํดํ ํ์
            map.put("result", 4L);
            map.put("state", "์ด๋ฏธ ํํดํ์  ํ์์๋๋ค.");
        }
        else{ // ํํดํ์ง ์์ ํ์
            Member member1 = mServiece.getMemberOne(member.getUseremail());
            if(sns == true){ // ์นด์นด์ค๋ก ๋ก๊ทธ์ธ ์
                if (bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
                else{
                    map.put("result", 0L);
                    map.put("state", "์นด์นด์ค ์ ์ ๊ฐ ์๋๋๋ค. ์ฌ์ดํธ ๋ก๊ทธ์ธ์์ ๋ก๊ทธ์ธ์ ์๋ํ์ฌ ์ฃผ์ญ์์ค.");
                }
            }else{ // ๊ทธ๋ฅ ๋ก๊ทธ์ธ ์
                if(bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 0L);
                    map.put("state", "์นด์นด์ค ์ ์ ์๋๋ค. ์นด์นด์ค ๋ก๊ทธ์ธ์์ ๋ก๊ทธ์ธ์ ์๋ํ์ฌ ์ฃผ์ญ์์ค.");
                }
                else{
                    authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(member.getUseremail(), member.getUserpw()));
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}

// ๋น๋ฐ๋ฒํธ ๋ณ๊ฒฝ ์ ์นด์นด์ค์ ์ ์ธ์ง ํ์ธ
// GET 127.0.0.1:8080/REST/api/kakao/user/check
@GetMapping(value = "/kakao/user/check")
public int kakaoUserCheckGET(@RequestHeader("token") String token) {
    int result;
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token์ ํตํด ํ์์ ๋ณด(์ด๋ฉ์ผ) ์ฐพ๊ธฐ
        // ์์ด๋๋ฅผ ์ด์ฉํด ๊ธฐ์กด ์ ๋ณด ๊ฐ์ ธ์ค๊ธฐ
        Member member = mServiece.getMemberOne(useremail);
        // ํ ํฐ๊ณผ ์ฌ์ฉ์ ์์ด๋ ์ผ์น ์์ 
        if (bcpe.matches("kakao_login_pw", member.getUserpw())) {
            result = 1;   
        }
        else{
            result = 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
        result = e.hashCode();
    }
    return result;
}
```
- `if(bcpe.matches("kakao_login_pw", member1.getUserpw()))`

#### 6. ๋ฌธ์ ํด๊ฒฐ
- ์ํธํ๋ ๋น๋ฐ๋ฒํธ๋ฅผ ๋ด๋ถ ๊ธฐ๋ฅ์ธ bcpe๋ก ์ด๋ฉ์ผ๊ณผ ์นด์นด์คํก ๋น๋ฐ๋ฒํธ๋ฅผ ๋น๊ตํด ๋ง๋์ง๋ฅผ ๊ตฌ๋ถ

---
### ํ์ํํด, ๋ฌผํ์ญ์  ์ ์ฐ๊ด๋ ์ ๋ณด๋ค ์ผ๊ด ์ฒ๋ฆฌ
#### 1. ๋ฌธ์ ์ ์
- ํ์ํํด, ๋ฌผํ์ญ์  ๋ฑ ์ธ๋ํค๋ก ์กํ์๋ ์ ๋ณด๊ฐ ์์ ์ ์๋ฌ๋ฐ์

#### 2. ์ฌ์ค์์ง
- ํ์์ ๊ฒฝ์ฐ ์ธ๋ํค๋ก ์ง์ ๋ CartItem, Cart, CancleHistory, PayHistory, Report, Question, Review๊ฐ ์ ๋ฆฌ๋์ผ์ง ์ญ์  ๊ฐ๋ฅ
- ๋ฌผํ์ ๊ฒฝ์ฐ ์ธ๋ํค๋ก ์ง์ ๋ SubImage, Question, Review, CartItem์ด ์ ๋ฆฌ๋์ผ์ง ์ญ์  ๊ฐ๋ฅ

#### 3. ์กฐ์ฌ๋ฐฉ๋ฒ๊ฒฐ์ 
- EntityManager์ ๊ธฐ๋ฅ์ธ Transaction์ ์ฌ์ฉํ์ฌ ํ ๋ฒ์ ์ฒ๋ฆฌ

#### 4. ์กฐ์ฌ๋ฐฉ๋ฒ๊ตฌํ
``` javascript
// ๋ฌผํ ์ญ์  ์ ์ด๋ฃจ์ด์ง๋ ํธ๋์ญ์
@Override
public void deleteProductTransaction(Long no) {

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    String sql = "UPDATE PRODUCT SET PRODUCTIMAGE=NULL, IMAGENAME=NULL, IMAGETYPE=NULL WHERE PRODUCTCODE=:no";
    em.createNativeQuery(sql)
        .setParameter("no", no).executeUpdate();
    String sql1 = "DELETE FROM SUBIMAGE WHERE PRODUCT=:no";
    em.createNativeQuery(sql1)
        .setParameter("no", no).executeUpdate();
    String sql2 = "DELETE FROM QUESTION WHERE PRODUCT=:no";
    em.createNativeQuery(sql2)
        .setParameter("no", no).executeUpdate();
    String sql3 = "DELETE FROM REVIEW WHERE PRODUCT=:no";
    em.createNativeQuery(sql3)
        .setParameter("no", no).executeUpdate();
    String sql4 = "DELETE FROM CARTITEM WHERE PRODUCT=:no";
    em.createNativeQuery(sql4)
        .setParameter("no", no).executeUpdate();
    em.getTransaction().commit();

}

// ํ์  ์ ์ด๋ฃจ์ด์ง๋ ํธ๋์ญ์
@Override
public void deleteMemberTransaction(String email, Date date) {
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    String sql1 = "DELETE FROM CARTITEM WHERE CART=(SELECT CARTCODE FROM CART WHERE MEMBER=:email)";
    em.createNativeQuery(sql1)
        .setParameter("email", email).executeUpdate();
    String sql2 = "DELETE FROM CART WHERE MEMBER=:email";
    em.createNativeQuery(sql2)
        .setParameter("email", email).executeUpdate();
    String sql3 = "DELETE FROM CANCELHISTORY WHERE MEMBER=:email";
    em.createNativeQuery(sql3)
        .setParameter("email", email).executeUpdate();
    String sql4 = "UPDATE PAYHISTORY SET MEMBER='ghost' WHERE MEMBER=:email";
    em.createNativeQuery(sql4)
        .setParameter("email", email).executeUpdate();
    String sql5 = "DELETE FROM REPORT WHERE MEMBER=:email";
        em.createNativeQuery(sql5)
            .setParameter("email", email).executeUpdate();
    String sql6 = "DELETE FROM QUESTION WHERE MEMBER=:email";
    em.createNativeQuery(sql6)
        .setParameter("email", email).executeUpdate();
    String sql7 = "DELETE FROM REVIEW WHERE MEMBER=:email";
    em.createNativeQuery(sql7)
        .setParameter("email", email).executeUpdate();
    String sql = "DELETE FROM MEMBER WHERE LEAVECHECK=TRUE AND LEAVEDATE=:date AND USEREMAIL=:email";
    em.createNativeQuery(sql)
        .setParameter("email", email)
        .setParameter("date", date).executeUpdate();
    em.getTransaction().commit();

}
```

#### 5. ๋ฌธ์ ํด๊ฒฐ
- Transaction์ ์ฌ์ฉํ์ฌ ์ฐ๊ด ์ ๋ณด๋ค์ Query๋ฌธ์ ํตํด ์ผ๊ด ์ฒ๋ฆฌ, ํ์์ ๊ฒฝ์ฐ ๊ฒฐ์ ์ ๊ฐ์ ์ค์ํ ์ ๋ณด๋ฅผ ์ง๋๊ณ  ์๊ธฐ๋๋ฌธ์ ํํดํ๋ค๋ ๋ ๋ก๋ถํฐ 1๋ ๋ค๋ฅผ ์ ๋ณด์ญ์ ๋ ๋ก ์ง์  ํ ๋งค์ผ ์์  ์ค์ผ์ฅด๋ฌ๋ฅผ ์ด์ฉํด LeaveCheck๊ฐ ture์ด๋ฉด์ LeaveDate๊ฐ ๋น์ผ์ธ ๊ฒ๋ค์ ์ญ์ 
- ์ฐ๊ด์ ๋ณด ์ค ๋์ ํต๊ณ์ ํ์ํ ๊ฒฐ์ ์ ๋ณด์ ๊ฒฝ์ฐ ์ธ๋ํค์ธ Member๋ฅผ ghost๋ผ๋ ์์ ๊ณ์ ์ ์ฐธ์กฐํ๊ฒ ํ์ฌ ํ์์ ๋ณด๋ ์ฌ๋ผ์ง๊ณ  ํ์ํ ํต๊ณ์ ๋ณด๋ง ๋จ๊ธฐ๊ฒํจ
- ๋ฌผํ์ ๊ฒฝ์ฐ ๊ฒฐ์ ์ ๋ณด๋ฑ๊ณผ ์ฐ๊ด์ด ๋์ด ๋ฌผํ ๋ํ์ด๋ฏธ์ง๋ฅผ null๋ก ๋ณ๊ฒฝ ํ ์ฐ๊ด๋ ์ ๋ณด๋ค์ ์ญ์ ํ์ฌ ์ต์ํ์ ์ ๋ณด๋ง 

---
### ๊ฒฐ์  ์ ํ์ํ ์ ๋ณด ์ ๋ฌ
#### 1. ๋ฌธ์ ์ ์
- ํ๋ ํน์ ์ฌ๋ฌ๊ฐ์ ์ฅ๋ฐ๊ตฌ๋ ์์ดํ ์ ๋ณด๋ฅผ ํ๋์ฉ ์ฐพ์ ์ ๋ณด๋ฅผ ์ ๋ฌ

#### 2. ์ฌ์ค์์ง
- ๊ฒฐ์ ํ์ด์ง๋ก ๋๊ธธ ๋ ์ฒดํฌํ ๋ฌผํ์ ๋ณด์ ํ์์ ๋ณด๋ฅผ listํํ๋ก ๋ณํฉํ์ฌ ๋ณด๋ด์ผํจ.
- param์์ Listํํ๋ก ๋์ด์ค์ง๋ง ์ฌ๋ฌ๊ฐ๊ฐ ์๋ ํ๋์ ์ฝ๋๋ง ๋์ด์ฌ ์ ์์ผ๋ฉฐ ๊ฒฐ์ ๊ฐ ๋์์ ์ ๊ฒฐ์ ํ ์ฅ๋ฐ๊ตฌ๋์์ดํ์ ์ญ์ ์ฒ๋ฆฌ๊ฐ ๋์ผํ๋ฏ๋ก ์ฌ๋ฌํ์ด๋ธ์ ๋ฐ์ดํฐ๋ฅผ ๋๊ฒจ์ผํจ.

#### 3. ์กฐ์ฌ๋ฐฉ๋ฒ๊ฒฐ์ 
- VIEW๋ฅผ ์์ฑํ์ฌ ํ์ํ ์ ๋ณด๋ค์ ๋ชจ์ ๋ค์ ํด๋น VIEW์์ foreach๋ฌธ์ ํตํด 

#### 4. ์กฐ์ฌ๋ฐฉ๋ฒ๊ตฌํ
``` javascript
// ๊ฒฐ์ ์ ํ์ํ ์ ๋ณด์ธ PAYMENTLIST VIEW์์ฑ
CREATE VIEW PAYMENTLIST AS SELECT 
   CARTITEM.QUANTITY, CARTITEM.CARTITEMCODE, MEMBER.USEREMAIL, MEMBER.USERNAME, MEMBER.USERTEL, MEMBER.POST, MEMBER.ADDRESS, MEMBER.DETAILEADDRESS,
   PRODUCT.PRODUCTCODE, PRODUCT.PRODUCTPRICE, PRODUCT.PRODUCTNAME, BRAND.BRANDNAME
FROM 
   CARTITEM, CART, MEMBER, PRODUCT, BRAND
WHERE 
   CARTITEM.PRODUCT = PRODUCT.PRODUCTCODE AND
   CARTITEM.CART = CART.CARTCODE AND
   CART.MEMBER = MEMBER.USEREMAIL AND
   PRODUCT.BRAND = BRAND.BRANDCODE
   
// ๊ฒฐ์  ์ ๋ฉค๋ฒ, ๋ฌผํ ์ ๋ณด ๋๊ธฐ๊ธฐ
@Select({
    "<script>",
        "SELECT * FROM PAYMENTLIST ", 
        "WHERE CARTITEMCODE IN ",
        " <foreach collection='chks' item='list' open='(' close=')' separator=','> ",
        "#{list}",
        " </foreach>",
    "</script>"})
public List<Map<String, Object>> selectPaymentInfo(@Param("chks") List<Long> chks);
```

#### 5. ๋ฌธ์ ํด๊ฒฐ
- ์ธ๋ํค ์ ๋ณด๋ฅผ ํตํด ๊ฐ ํ์ด๋ธ์ INNER JOINํ ๋ค ํ์ํ ๋ฐ์ดํฐ๋ฅผ ๋ด์ VIEW๋ฅผ ์์ฑํ์ฌ ํด๋น VIEW์์ foreach๋ฅผ ํตํ์ฌ ์ ๋ณด๋ฅผ ์ฐพ์ ์ ๋ฌ

---
### ์ต๊ทผ 5์ผ๊ฐ์ ์ผ์ผํ๋งค๋ ํต๊ณ
#### 1. ๋ฌธ์ ์ ์
- ํ๋งค๊ฐ ์ด๋ฃจ์ด์ง์ง ์์ ๋ ์ ํต๊ณ์ ๋ํ๋์ง์์

#### 2. ์ฌ์ค์์ง
- PAYHISTORYํ์ด๋ธ์์ ์๋ฃ๋ฅผ ์์งํด์ค๋ฉด ํ๋งค๊ฐ ์๋ ๋ ์ ์๋ฃ ์์ง์ด ๋ถ๊ฐ
- ๋ฐ์ดํฐ๊ฐ ์๋ ๋ ์ ๋ ์ง ์์ฑ๊ณผ ๋๋ถ์ด ๊ฐ์ ๋ํ 0์ผ๋ก ๋ํ๋ด์ผํจ

#### 3. ์กฐ์ฌ๋ฐฉ๋ฒ๊ฒฐ์ 
- ์ต๊ทผ 5์ผ์ ๋ ์ง๋ฅผ ์ ์ฅํ  ํ์ด๋ธ์ ์์ฑ ๋ฐ NVL์ ์ด์ฉํด ์๋ ๋ฐ์ดํฐ ํ์

#### 4. ์กฐ์ฌ๋ฐฉ๋ฒ๊ตฌํ
``` javascript
// ํ๋งค๋์กฐํ๋ฅผ ์ํ ๋ ์ง ํ์ด๋ธ 
@Update({
    "UPDATE DUAL SET DUAL_DATE=#{date} WHERE DUAL_ID=#{no}"
})
public int InsertDate(@Param("no") long no, @Param("date") Date date);

// ํด๋น์ผ์ ์ต๊ทผ 5์ผ๊ฐ์ ํ๋งค๋ ์กฐํ
@Select({
    "SELECT DUAL.DUAL_DATE, (NVL(DATE1.CNT, 0)) AS CNT FROM DUAL ",
    "LEFT OUTER JOIN DATE1 ON DUAL.DUAL_DATE = DATE1.ORDERDATE ORDER BY DUAL.DUAL_DATE ASC"
})
public List<Map<String, Object>> selectSalesRate();

// 5์ผ๊ฐ์ ๋ ์ง์ payhistory๊ฐ์๋ฅผ ๋ฆฌ์คํธ๋ก ์ถ๋ ฅ
// GET 127.0.0.1:8080/REST/api/admin/payhistory/list
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
```
#### 5. ๋ฌธ์ ํด๊ฒฐ
- ๋ฐ๋ณต๋ฌธ์ ํตํด D-5์ ๋ ์ง๋ฅผ ํ์ด๋ธ์ ์ ์ฅํ ํ ํ๋งค๋์ ์กฐํ
- LEFT JOIN์ ํตํด ํ์ดํฐ๋ฅผ ํฉํ ํ ํ๋งค๊ฐ ์๋๋ ์ NVL(DATE1.CNT, 0)๋ฅผ ํตํด ํ๋งค๋์ 0์ผ๋ก ํ์ํ์ฌ ์ ๋ณด๋ฅผ ์ ๋ฌ

---
### ๋์ Query๋ฌธ์์์ Pagenation์ฒ๋ฆฌ์ ์ ๋ ฌ
#### 1. ๋ฌธ์ ์ ์
- JPA์ Pageable๊ณผ ๊ฐ์ ๊ฒ์ ๋์ Query์ ์์

#### 2. ์ฌ์ค์์ง
- ํ ํ์ด์ง๋น ๋ํ๋ด๋ ๊ฐ์์ ๋ง์ถ์ด ๊ฐ ํ์ด์ง์ ๋ํ๋๋ ๋ฐ์ดํฐ์ ์์๋ฒํธ์ ๋๋ฒํธ๋ฅผ ๋งค๊ฒจ ํ์ด์ง ์ฒ๋ฆฌ๋ฅผ ํด์ผํจ

#### 3. ์กฐ์ฌ๋ฐฉ๋ฒ๊ฒฐ์ 
- ํ์ด์ง๋น ๋ํ๋ผ ๋ฐ์ดํฐ์ ๊ฐ์์ ๋ฐ๋ผ ์์๋ฒํธ์ ๋๋ฒํธ๋ฅผ param์ผ๋ก ๋๊ฒจ ROWN BETWEEN์ ์ฌ์ฉํ์ฌ ํ์ด์ง ์ฒ๋ฆฌ

#### 4. ์กฐ์ฌ๋ฐฉ๋ฒ๊ตฌํ
``` javascript
// ๋ฌธ์๊ธ ๋ต๊ธ์ฌ๋ถ, ์ข๋ฅ๋ณ ์กฐํ(๋ ์ง ๊ธฐ์ค ์ ๋ ฌ)
// GET 127.0.0.1:8080/REST/api/question/all/selectlist?reply=false&kind=2&page=1
@GetMapping(value = "/question/all/selectlist", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> AllSelectListGET(@RequestParam(name = "reply") Boolean reply, 
@RequestParam(name = "kind", defaultValue = "0") long kind, @RequestParam(name = "page", defaultValue = "1") long page) {
    Map<String, Object> map = new HashMap<>();
    try {
        long start, end = 1;
        int count = qService.selectReplyKindCNT(reply, kind);
        if(page == 1){
            start = 1;
            end = 1*5;
            List<Map<String, Object>> list = qService.selectQuestionDTOList(reply, kind, start, end);
            map.put("list", list);
            map.put("result", 1);
        }
        else{
            start = (page-1)*5+1;
            end = page*5; 
            List<Map<String, Object>> list = qService.selectQuestionDTOList(reply, kind, start, end);
            map.put("list", list);
            map.put("result", 1);
        }
        map.put("count", count);
    } catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}

 // ๋ต๊ธ์ฌ๋ถ, ๋ฌธ์์ข๋ฅ์ ๋ฐ๋ฅธ ๋ฆฌ์คํธ ์ถ๋ ฅ(admin)
@Select({
    "<script>",
        "SELECT * FROM(",
        "SELECT QUESTIONCODE, QUESTIONTITLE, QUESTIONCONTENT, PRODUCTCODE, ",
        "to_char(QUESTIONDATE,'YYYY-MM-DD') AS QUESTIONDATE, ROW_NUMBER() OVER (ORDER BY QUESTIONDATE",
        " <if test='reply == true'> DESC  </if>",
        " <if test='reply == false'> ASC  </if>) ROWN ",
        "FROM QUESTIONLIST  WHERE QUESTIONREPLY=#{reply}",
        "<if test='kind != 0'> AND QUESTIONKIND=#{kind} </if>",
        ") QUESTION WHERE ROWN BETWEEN #{start} AND #{end}",
    "</script>"    
})
public List<Map<String, Object>> selectQuestionDTO(@Param("reply") Boolean reply, @Param("kind") Long kind, @Param("start") long start, @Param("end") long end);
```
#### 5. ๋ฌธ์ ํด๊ฒฐ
- ROWN์ ์ฌ์ฉํ์ฌ ๋ฒํธ๋ฅผ ๋งค๊ธฐ๊ณ  ์์๋ฒํธ์ ๋๋ฒํธ๋ฅผ BETWEEN์ ํตํด ๋ฐ์ดํฐ๋ฅผ ์์งํจ 
- ROWN ์ฌ์ฉ ์ order by๋ฅผ ์ด์ฉํ๋ฉด ์์๊ฐ ๋ค์ฃฝ๋ฐ์ฃฝ์ด ๋๊ธฐ๋๋ฌธ์ ROW_NUMBER() OVER๋ฅผ ์ฌ์ฉํ์ฌ ์ ๋ ฌ์ ํด์ค

---
### ๋ฆฌ๋ทฐ๋ฑ๋ก ๊ฐ๋ฅ ์ฌ๋ถ
#### 1. ๋ฌธ์ ์ ์
- ์์ฑํ๋ ค๋ ๋ฌผํ์ ๊ตฌ๋งคํ์ง์์ ์ฌ๋๋ ๋ฆฌ๋ทฐ์์ฑ์ด ๊ฐ๋ฅ

#### 2. ์ฌ์ค์์ง
- ๊ตฌ๋งคํ ๋ฌผํํ์์ ๋ฆฌ๋ทฐ ์์ฑ์ด ๊ฐ๋ฅํด์ผํ๋๋ฐ ๊ทธ๋ ์ง ์์
- ๊ฒฐ์ ๋ฅผ ํด์ ์ด๋ฏธ ๋ฆฌ๋ทฐ๋ฅผ ์์ฑํ ์ฌ๋ ์ค ๋ ํด๋น ๋ฌผํ์ ๊ตฌ๋งคํ์ฌ ๋ฆฌ๋ทฐ๋ฅผ ์ฐ๋ ค๋ ๊ฒฝ์ฐ ๋ฆฌ๋ทฐ๋ ํ ๋ฌผํ ๋น ํ๋๋ง ์์ฑ๊ฐ๋ฅํ๊ธฐ๋๋ฌธ์ ์ค๋ฅ ๋ฐ์

#### 3. ์กฐ์ฌ๋ฐฉ๋ฒ๊ฒฐ์ 
- ๊ฒฐ์ ๋ด์ญ ํ์ธ๊ณผ ์ด๋ฏธ ๋ฆฌ๋ทฐ๋ฅผ ์์ฑํ๋์ง ํ์ธ

#### 4. ์กฐ์ฌ๋ฐฉ๋ฒ๊ตฌํ
``` javascript
// ๊ฒฐ์ ๋ด์ญํ์ธ์ ํตํด ๋ฆฌ๋ทฐ์์ฑ๊ฐ๋ฅํ์ง ํ์ธ
// GET 127.0.0.1:8080/REST/api/payments/paylist/check?no=14
@GetMapping(value="/payments/paylist/check")
public int payhistoryCheckListGET(@RequestParam("no") Long no, @RequestHeader("token") String token) {
    int i;
    try{
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token์ ํตํด ํ์์ ๋ณด(์ด๋ฉ์ผ) ์ฐพ๊ธฐ
        Map<String, Object> check = phService.checkPayHistory(no, useremail);
        Long count = (Long)check.get("COUNT(MEMBER)");
        Boolean review = (Boolean)check.get("MAX(REVIEWCHECK)");
        if(count >= 1 && review == true){
            i = 2; // ์ด๋ฏธ ์์ฑํ ๋ฆฌ๋ทฐ๊ฐ ์์ต๋๋ค
        }
        else if(count >= 1 && review == false){
            i = 1; // ๋ฆฌ๋ทฐ ์์ฑ ๊ฐ๋ฅ
        }
        else{
            i = 0; // ๋ฆฌ๋ทฐ์์ฑ ๋ถ๊ฐ
        }

    }
    catch (Exception e) {
        e.printStackTrace();
        i = e.hashCode();
    }
    return i;
}

// ๊ฒฐ์ ๋ด์ญ ํ์ธ์ ํ์ํ PAYHISTORYLIST VIEW์์ฑ
CREATE VIEW PAYHISTORYLIST AS SELECT 
   PRODUCT.PRODUCTCODE, PRODUCT.PRODUCTNAME, PRODUCT.PRODUCTPRICE, BRAND.BRANDNAME, 
   PAYHISTORY.ORDERQUANTITY, PAYHISTORY.ORDERDATE, PAY.MERCHANT_UID, PAYHISTORY.MEMBER, PAYHISTORY.REVIEWCHECK
FROM 
   PAYHISTORY, PAY, PRODUCT, BRAND
WHERE 
   PAYHISTORY.PAY = PAY.IMP_UID AND
   PAYHISTORY.PRODUCT = PRODUCT.PRODUCTCODE AND
   PRODUCT.BRAND = BRAND.BRANDCODE

// ํ์๊ณผ ๋ฌผํ์ ๋ณด์ ๋ฐ๋ฅธ ๊ฒฐ์ ๋ด์ญ ์กฐํ(๋ฆฌ๋ทฐ์์ฑ๊ฐ๋ฅํ์ง)
@Select({
        "SELECT COUNT(MEMBER), max(REVIEWCHECK) FROM PAYHISTORYLIST  ", 
        "WHERE MEMBER=#{email} AND PRODUCTCODE=#{no}",
})
public Map<String, Object> selectPayHistoryCheck(@Param("no") Long no, @Param("email") String email);
```
#### 5. ๋ฌธ์ ํด๊ฒฐ
- review๋ฅผ ์์ฑํ๊ฒ ๋๋ฉด ํด๋น ์ฃผ๋ฌธ์ ๋ณด์ reviewCheck๋ถ๋ถ์ด true๋ก ๋ณ๊ฒฝ๋๋๋ฐ ํ ๋ฌผํ์ ์ฌ๋ฌ๋ฒ ๊ตฌ๋งคํ๋๋ผ๋ max(REVIEWCHECK)๋ฅผ ์ด์ฉํ์ฌ ๋ฆฌ๋ทฐ๋ฅผ ํ ๋ฒ๋ง ์์ฑ๊ฐ๋ฅํ๊ฒ ๊ตฌ๋ถ

---

## Features / ํน์ง
### ์ถ์ฒ๋ฌผํ
> ์ค์  ์ฌ์ดํธ์ ํ์๋ค์ด ๊ฒฐ์ ํ ๋ฐ์ดํฐ๋ค์ ์์ ์ถ์ฒ๋ฌผํ์ ๋ํ๋ด๊ณ  ์ถ์์ต๋๋ค. ์ฒ์์ ๋ฅ๋ฌ๋๊ณผ ๊ฐ์ ์ธ๊ณต์ง๋ฅ์ด ์๊ฐ์ด ๋ฌ์ง๋ง ์์ง ๋ฐฐ์๋ณด์ง๋ชปํ ๋ถ์ผ๋ผ ๋ค๋ฅธ ๋ฐฉ๋ฒ์ ์ฐพ์์ต๋๋ค. 
> 
> ์ธ๊ณต์ง๋ฅ์ ์๋ฏธ๋ฅผ ์๊ฐํด๋ณด๋ ์ฌ์ฉ์๋ค์ ๋ฐ์ดํฐ๋ค์ ์์ ์ค์ค๋ก ํ์ตํ๋ ๊ฒ์ด๋ผ๋ ์๊ฐ์ ์๋ก์ด ๋ฐฉ๋ฒ์ด ๋ ์ฌ๋์ต๋๋ค. 
> 
> ํ์๋ค์ด ๊ฒฐ์ ๋ฅผ ํ ๋ด์ญ์ ํตํด ์ถ์ฒ๋ฌผํ์ ๋ํ๋๊ฒ ํ๋ ๊ฒ์ด์์ต๋๋ค. ๊ด์ฌ์๋ ๋ฌผํ์ ๋ณด๋ฌ ๋ค์ด์ค๋ฉด ํด๋น ๋ฌผํ์ฝ๋์ ์์ธ ๋ฐ์ดํฐ๋ค ์ค ๊ตฌ๋งคํ์๊ฐ ๊ฐ์ฅ ๋ง์ ์ถ์ฒ๋ฌผํ ์ฝ๋๋ฅผ ๋ฝ์ ๋ํ๋๊ฒ ํ๋๋ฐฉ๋ฒ์ด์์ต๋๋ค.
> ์๋ฆฌ๋ ๋ค์๊ณผ ๊ฐ์ต๋๋ค. ๊ด์ฌ์๋ ๋ฌผํ์ฝ๋๊ฐ ์๊ณ  ๊ทธ ์์ ์ถ์ฒ๋ฌผํ ์ฝ๋์ ๊ตฌ๋งคํ์์ ๋ฐ์ดํฐ๋ฅผ ์๋ ๊ฒ์ด์์ต๋๋ค. ์ด๊ธฐ์ ์๊ฐ์ ๋ฌผํ์ฝ๋๋ผ๋ key๋ฅผ ์ฐพ๊ณ  ๊ทธ ์์ ์ถ์ฒ๋ฌผํ ์ฝ๋๋ผ๋ key์ ๊ตฌ๋งคํ์๋ผ๋ value๊ฐ ์์ผ๋ key: {key: value} ์ฆ, Hash Map์ value์์ Hash Map์ด ์๋ ๊ฒ์ด์์ต๋๋ค. 
> 
> ํ์ง๋ง ํ์ด๋ธ ์ปฌ๋ผ์ HashMap๊ณผ ๋ฐฐ์ด๋ก ๋์ง์์๊ณ  ์๋จธ๋ฆฌ๋ฅผ ์ฌ์ฉํด ๋ฐ๊ณผ ๊ฐ์ ๊ตฌ์กฐ๋ก ํ์์ต๋๋ค.
``` javascript
// Recommend ํ์ด๋ธ ์ค์ 
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "RECOMMEND")
@SequenceGenerator(name = "SEQ_RECOMMEND_NO", sequenceName = "SEQ_RECOMMEND_NO", initialValue = 1, allocationSize = 1)
public class Recommend {

    @Id
    @Column(name = "RECOMMENDCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECOMMEND_NO")
    private long recommendcode = 0L;

    @OneToOne // ๋ฌผํ์ ๋ณด
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @Column(name = "RECOMMENDKEY")
    private String recommendkey = null;

    @Column(name = "RECOMMENDVALUE")
    private String recommendvalue = null;
}
```

``` javascript
// ๋ฌผํ ๊ตฌ๋งค ์ ์ถ์ฒ๋ฌผํ์ ์ถ๊ฐ
// PUT 127.0.0.1:8080/REST/api/add/recommended
@PutMapping(value="/add/recommended")
public Map<String, Object> addRecommendProduct(@RequestHeader("token") String token, @RequestParam("no") List<Long> no) { // ๊ฒฐ์ ํ ๋ฌผํ์ฝ๋๋ฒํธ
    Map<String, Object> map = new HashMap<String, Object>();
    Map<Long, Long> re = new HashMap<Long, Long>();
    try{
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token์ ํตํด ํ์์ ๋ณด(์ด๋ฉ์ผ) ์ฐพ๊ธฐ
        Long product = pService.latestOrder(useremail); // ์ด์ ์ ๊ฒฐ์ ํ ๋ฌผํ๋ฒํธ
        String key = "";
        String count = "";
        String[] st1;
        String[] st2;
        if(product != null){
            Map<String, Object> list = rService.checkRecommend(product);
            Recommend recommend = rService.findRecommend(product);
            if(list != null){ // ์ถ์ฒ๋ฌผํ์ list๊ฐ ์๋ค๋ฉด
                st1 = recommend.getRecommendkey().split(",");
                st2 = recommend.getRecommendvalue().split(",");
                for(int i=0; i<st1.length; i++){
                    re.put(Long.parseLong(st1[i]), Long.parseLong(st2[i]));
                }
                for(int j=0; j<no.size(); j++){
                    if(re.containsKey(no.get(j)) && product != no.get(j)){ // list์์ ๊ตฌ๋งค ๋ฌผํ๋ฒํธ๊ฐ ์๋์ง
                        re.put(no.get(j), re.get(no.get(j)) + 1); // ํ์ธ ํ +1
                    }else if(!re.containsKey(no.get(j)) && product != no.get(j)){ // ๊ตฌ๋งคํ๋ ค๋ ๋ฌผํ๊ณผ ๊ฐ์ ์ฝ๋์ธ์ง ํ์ธ
                        re.put(no.get(j), 1L); // ๋ฌผํ๋ฒํธ๊ฐ ์๋ค๋ฉด ์ถ๊ฐ
                    }
                }
                for(Entry<Long, Long> elem : re.entrySet()){ 
                    key += elem.getKey() + ",";
                    count += elem.getValue() + ","; 
                }
                rService.updateKeyValue(key, count, product);
            }else{ // ์ถ์ฒ๋ฌผํ์ list๊ฐ ์๋ค๋ฉด ์๋ก ์ถ๊ฐ
                for(int j=0; j<no.size(); j++){
                    key += String.valueOf(no.get(j)) + ",";
                    count += String.valueOf(1) + ",";
                }
                rService.updateKeyValue(key, count, product);
            }
            map.put("result", 1);
            map.put("state", "์ถ์ฒ๋ฌผํ ์ถ๊ฐ์๋ฃ");
        }
        else if(product == null && no.contains(product)){
            map.put("result", 1);
            map.put("state", "์ด๋ ฅ ์์");
        }

    }
    catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}
```
- ์ฒ์์ ์๊ฐํ ๊ฒ๊ณผ ๊ฐ์ด Hash Map์์ Hash Map์ key์ value๋ค์ ์ปฌ๋ผ์ผ๋ก ์ ์ฅํ๋ ๊ฒ์๋๋ค.
- ๋ฌผํ์ฝ๋๋ OneToOne์ผ๋ก ๋ฑ๋ก๋์ด์๋ ๋ชจ๋  ๋ฌผํ๋ค์ ๋ฑ๋กํด๋์ต๋๋ค. ๊ทธ๋ฆฌ๊ณ  Recommend Key, Recommend Value๋ค์ ์ถ์ฒ๋ฌผํ์ฝ๋์ ๊ตฌ๋งคํ์๋ฅผ ','๋ฅผ ๊ธฐ์ค์ผ๋ก ๋ฃ์ด๋๊ณ  ์ด๋ฅผ String์ผ๋ก ๋ณํํด ์ ์ฅํ๋ ๊ฒ์๋๋ค. ์ด๋ ๊ฒ ๋๋ฉด ๋์ค์ spilt ๋ฉ์๋๋ฅผ ํตํด ๊ฐ ์ถ์ฒ๋ฌผํ์ฝ๋์ ๊ตฌ๋งคํ์๋ฅผ ์ถ๋ ฅํ  ์ ์๊ฒ๋ฉ๋๋ค.
- ํ์์ด ๊ฒฐ์ ๋ฅผ ํ๋ฉด ํด๋น ํ์์ด ์ด์ ์ ๊ฒฐ์ ํ ๋ด์ญ ์ค ์ต๊ทผ์ ๋ฌผํ ์ฝ๋์ ์์ฌ์๋ ๋ฐ์ดํฐ๊ฐ ์๋์ง ํ์ธํ๊ณ  ์๋ค๋ฉด ์ด๋ฅผ split์ ์ฌ์ฉํ์ฌ ๋ฐฐ์ด์ ๋ฃ๊ณ  ๋ฐ๋ณต๋ฌธ์ ํตํด Map์ ๋ฃ์ต๋๋ค. 
- Map์ ๊ธฐ๋ฅ์ธ containsKey์ get์ ์ฌ์ฉํ์ฌ ์ด๋ฏธ ์์ฌ์๋ ๋ฐ์ดํฐ์ ๊ฒฐ์ ํ ๋ฌผํ์ ๋ฐ์ดํฐ๊ฐ ์๋์ง ํ์ธํ๊ณ  ์๋ค๋ฉด 1๊ณผ ํจ๊ป ์ถ๊ฐํ๊ณ  ์๋ค๋ฉด ์์ฌ์๋ ๊ตฌ๋งคํ์์ +1์ ํด์ค๋๋ค. 

``` javascript
// ๋ฌผํ์์ธํ์ด์ง์ ์ถ์ฒ๋ฌผํ์ฝ๋ ์ถ๋ ฅ
// GET 127.0.0.1:8080/REST/api/recommend/product?code=
@GetMapping(value="/recommend/product")
public Map<String, Object> getRandom(@RequestParam Long code) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> list = rService.checkRecommend(code);
    try{
        if(list != null){
            Map<Long, Integer> re = new HashMap<Long, Integer>();
            Recommend recommend = rService.findRecommend(code);
            String[] st1 = recommend.getRecommendkey().split(",");
            String[] st2 = recommend.getRecommendvalue().split(",");
            for(int i=0; i<st1.length; i++){
                re.put(Long.parseLong(st1[i]), Integer.parseInt(st2[i]));
            }
            List<Entry<Long, Integer>> list_entries = new ArrayList<Entry<Long, Integer>>(re.entrySet());
            // ๋น๊ตํจ์ Comparator๋ฅผ ์ฌ์ฉํ์ฌ ์ค๋ฆ์ฐจ์์ผ๋ก ์ ๋ ฌ
            Collections.sort(list_entries, new Comparator<Entry<Long, Integer>>() {
                // compare๋ก ๊ฐ์ ๋น๊ต
                public int compare(Entry<Long, Integer> obj1, Entry<Long, Integer> obj2) {
                    // ์ค๋ฆ ์ฐจ์ ์ ๋ ฌ
                    return obj2.getValue().compareTo(obj1.getValue());
                }
            });
            ProductDto product = pService.selectProductOne(list_entries.get(0).getKey());
            map.put("result", 1);
            map.put("recommend", product);
        }else{
            Long ret = pService.randomProduct(code);
            ProductDto product = pService.selectProductOne(ret);
            map.put("result", 2);
            map.put("recommend", product);
        }
    }catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}
```
- ๊ด์ฌ์๋ ๋ฌผํ์ ๋๋ฌ ์์ธํ์ด์ง๋ก ์ด๋ํ์์ ๋ ํด๋น ๋ฌผํ์ฝ๋์ ์ ์ฅ๋์ด์๋ ์ถ์ฒ๋ฌผํ ๋ฐ์ดํฐ๋ค์ ์ฐพ์ต๋๋ค.
- ๋ค์ split์ ์ฌ์ฉํด ๋ฐฐ์ด์ ๋ฃ๊ณ  ์ด๋ฅผ ๋ฐ๋ณต๋ฌธ์ ํตํด map์ ๋ฃ์ด์ค ๋ค์ compare ๊ธฐ๋ฅ์ผ๋ก ๊ตฌ๋งคํ์๋ฅผ ๊ธฐ์ค์ผ๋ก ์ค๋ฆ์ฐจ์ ์ ๋ ฌ์ ํด์ค๋๋ค. ์ ๋ ฌ๋ list_entries์ 0๋ฒ์งธ key ์ฆ, ์ถ์ฒ๋ฌผํ์ฝ๋๋ฅผ ๋ฆฌํดํ๋ฉด ๋ฉ๋๋ค. 
- ๋ง์ฝ ์ ์ฅ๋ ์ถ์ฒ๋ฌผํ ๋ฐ์ดํฐ๊ฐ ์๋ค๋ฉด ๋๋ค์ผ๋ก ๋ฌผํ์ ์ถ๋ ฅํ๊ฒ ๋๋๋ฐ ISBN์ ์ฌ์ฉํ ์นดํ๊ณ ๋ฆฌ์ฝ๋์ 6์๋ฆฌ ์ค ์ ์ธ์๋ฆฌ๊ฐ ๊ฐ์ ์นดํ๊ณ ๋ฆฌ ๋ด์์ ๋ฌผํ์ ๋๋ค์ผ๋ก ์ถ์ฒํ๊ฒ ํ์์ต๋๋ค.
---

### ์ ๊ณ ๊ธฐ๋ฅ
> ๋ค๋ฅธ ์ฌ์ดํธ๋ค์ ์ฌ์ฉํ๋ค๋ณด๋ฉด ๋ฆฌ๋ทฐ๋ ๋ฌธ์๋ฅผ ํตํด ์์์ ์ธ ํ์๋ฅผ ํ๋ ๊ฒ์ ๋ณธ์ ์ด ์์ต๋๋ค. 
> 
> ๊ทธ๋์ ์ ํฌ ์ฌ์ดํธ์์ ์ด๋ฌํ ํ์๋ฅผ ๋ค๋ฅธ ํ์๋ค์ด ํ๋จํ์ฌ ์ ๊ณ ๋ฅผ ํ๋ฉด ๊ด๋ฆฌ์๊ฐ ํ๋จํ์ฌ ๊ฒฝ๊ณ ๋ฅผ ํ๋ ๋ฑ์ ํ์๋ฅผ ํ  ์ ์๋๋ก ์ ๊ณ ๊ธฐ๋ฅ์ ๋ฃ์์ต๋๋ค.
> ๋ฌธ์๋ ๊ด๋ฆฌ์๋ง ๋ณผ ์ ์๊ฒ ํด๋์๊ธฐ ๋๋ฌธ์ ๋ฆฌ๋ทฐ๋ฅผ ํ์๋ค์ด ์ ๊ณ ํ  ์ ์๊ฒ ํ์๊ณ  ๊ด๋ฆฌ์ํ์ด์ง์์ ๋ฌธ์๋ฅผ ๋ณด๊ณ  ๊ด๋ฆฌ์๊ฐ ํ๋จํ์ฌ ์ ๊ณ ํ  ์ ์๊ฒ ํด์ฃผ์์ต๋๋ค.

``` javascript
// ๋ฌผํ์์ธํ์ด์ง์ ์ถ์ฒ๋ฌผํ์ฝ๋ ์ถ๋ ฅ
 @Id
@Column(name = "REPORTCODE")
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPORT_NO")
private long reportcode = 0L;

@OneToOne // ํ์์ ๋ณด
@JoinColumn(updatable = false, name = "MEMBER")
private Member member;

@Column(name = "REPORTDATE")
private String reprotdate = null;

@Column(name = "REPORTCOUNT")
private Long reportcount = 0L;
```
-ํ ํ์๋น ํ๋์ ์ ๊ณ ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ง๊ฒ OneToOne์ผ๋ก ํด์ฃผ์๊ณ  ๊ด๋ฆฌ์๊ฐ ํด๋น ํ์์ด ์ธ์  ์ ๊ณ ๋นํ๋์ง ์๊ธฐ์ํด ๋ง์ฐฌ๊ฐ์ง๋ก date๋ฅผ ','๋ฅผ ๊ธฐ์ค์ผ๋ก String์ผ๋ก ์ ์ฅํ์ต๋๋ค.
``` javascript
// ์ ๊ณ ํ๊ธฐ(๋ฆฌ๋ทฐ, ๋ฌธ์)
// POST 127.0.0.1:8080/REST/api/member/report
@PostMapping(value = "member/report", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> reportPOST(@RequestHeader("token") String token, @RequestBody Map<String, Object> mapobj){
    Map<String, Object> map = new HashMap<String, Object>();
    try{
        String useremail = (String) mapobj.get("useremail");
        Report report = rServiece.findReport(useremail);
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(report != null){
            rServiece.updateDate(report.getReprotdate()+formatDate+",", report.getReportcode());
        }
        else{
            Report report1 = new Report();
            Member member = mServiece.getMemberOne(useremail);
            report1.setMember(member);
            report1.setReprotdate(formatDate+",");
            rServiece.insertReport(report1);
        }
        map.put("result", 1);
        map.put("state", "์ ๊ณ ์ ์๊ฐ ์๋ฃ๋์์ต๋๋ค.");
    }
    catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}
```
-๊ด๋ฆฌ์ ํ์ด์ง์์ ์ ๊ณ ๋ ํ์๋ค์ ๋ฐ๋ก ๋ณผ ์ ์๋ ํ์ด์ง๋ฅผ ๋ฐ๋ก ๊ตฌ์ฑํ์๊ณ  ํด๋น ํ์ด์ง์์ ์ ๊ณ  ํ์์ ๋ ์ง๋ฅผ ๋ณผ ์ ์์ต๋๋ค.

---

### ๋ฉ์ผ๊ธฐ๋ฅ
> ์ฌ์ดํธ์์ ํ์ํ ๊ธฐ๋ฅ ์ค ๋น๋ฐ๋ฒํธ ์ฐพ๊ธฐ์ ํ์ํํด๋ฅผ ๋ฉ์ผ๋ก ๊ตฌํํด๋ณด๊ณ  ์ถ์์ต๋๋ค.
```javascript
// ๋น๋ฐ๋ฒํธ ์ฐพ๊ธฐ ์ ์์๋น๋ฐ๋ฒํธ ๋ฐ์ก 
public MailDto createMailAndChangePassword(String userEmail, String userName){
    String str = getTempPassword();
    MailDto dto = new MailDto();
    dto.setAddress(userEmail);
    dto.setTitle(userName+"๋์ ALL_GANIC ์์๋น๋ฐ๋ฒํธ ์๋ด ์ด๋ฉ์ผ ์๋๋ค.");
    dto.setMessage("์๋ํ์ธ์. ALL_GANIC ์์๋น๋ฐ๋ฒํธ ์๋ด ๊ด๋ จ ์ด๋ฉ์ผ ์๋๋ค." + "[" + userName + "]" +"๋์ ์์ ๋น๋ฐ๋ฒํธ๋ "
    + str + " ์๋๋ค.");
    updatePassword(str,userEmail);
    return dto;
}

public void updatePassword(String str,String userEmail){
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    Member member = mService.getMemberOne(userEmail);
    member.setUserpw(bcpe.encode(str));
    // ์์ด๋, ์ํธ๋ฅผ ์๋ก์ด ๊ธฐ๋ณธ๊ฐ์ผ๋ก ๋์ฒด
    mService.updatePassword(member);

}


public String getTempPassword(){
    char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    String str = "";

    int idx = 0;
    for (int i = 0; i < 10; i++) {
        idx = (int) (charSet.length * Math.random());
        str += charSet[idx];
    }
    return str;
}

public void mailSend(MailDto mailDto){
    System.out.println("์ด๋ฉ ์ ์ก ์๋ฃ!");
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailDto.getAddress());
    message.setFrom(sendEmailService.FROM_ADDRESS);
    message.setSubject(mailDto.getTitle());
    message.setText(mailDto.getMessage());

    mailSender.send(message);
}

```
-์์๋น๋ฐ๋ฒํธ ์๋ด๊ฐ ๋ฐ๊ณผ ๊ฐ์ด ๋์ฐฉํฉ๋๋ค.
![2](https://user-images.githubusercontent.com/85853167/150677059-48e9ab07-cffe-4937-a524-c80a016a2864.PNG)

```javascript
public MailDto createMailAndMalignityMember(String userEmail, String userName){
    String str = getTempPassword();
    MailDto dto = new MailDto();
    dto.setAddress(userEmail);
    dto.setTitle("์์ฑ ๋ฆฌ๋ทฐ ๋ฐ ๋ฌธ์๋ก ์ธํ ALL_GANIC ํํด ์ฒ๋ฆฌ ์๋ด ์ด๋ฉ์ผ์๋๋ค.");
    dto.setMessage(userEmail + "๋์ ์์์ ์ธ ๋ฆฌ๋ทฐ ๋ฐ ๋ฌธ์๋ฅผ ์ฌ๋ฌ๋ฒ ์ฌ๋ ค ์ฌ์ดํธ ๋ด๋ถ ์ง์นจ์ ๋ฐ๋ผ ํํด์ฒ๋ฆฌํจ์ ์๋ ค๋๋ฆฝ๋๋ค."  
    + "<img src=\"https://media.istockphoto.com/vectors/astronaut-in-outer-space-concept-vector-illustration-in-flat-style-vector-id862745000?b=1&k=20&m=862745000&s=170667a&w=0&h=aCl9IvQsbFDqRpvibEeHIABBucC23769h4kuYM-4ae0=\">"
    );
    updatePassword(str,userEmail);
    return dto;
}

public MailDto createMailAndCounterfeitMember(String userEmail, String userName){
    String str = getTempPassword();
    MailDto dto = new MailDto();
    dto.setAddress(userEmail);
    dto.setTitle("์์กฐ๊ธ์ก 3ํ ์ ๋ฐ๋ก ์ธํ ALL_GANIC ํํด ์ฒ๋ฆฌ ์๋ด ์ด๋ฉ์ผ์๋๋ค.");
    dto.setMessage(userEmail + "๋์ ๊ฒฐ์  ์ ๊ธ์ก์ ์์กฐํ๋ ค๋ ํ์๊ฐ ์ด 3๋ฒ ์ ๋ฐ๋์์ต๋๋ค." + " ์ด์ ์ฌ์ดํธ ๋ด๋ถ ์ง์นจ์ ๋ฐ๋ผ ํํด์ฒ๋ฆฌํจ์ ์๋ ค๋๋ฆฝ๋๋ค." 
    + "<img src=\"https://media.istockphoto.com/vectors/astronaut-in-outer-space-concept-vector-illustration-in-flat-style-vector-id862745000?     b=1&k=20&m=862745000&s=170667a&w=0&h=aCl9IvQsbFDqRpvibEeHIABBucC23769h4kuYM-4ae0=\">"
    );
    updatePassword(str,userEmail);
    return dto;
}

public void imgMailSend(MailDto mailDto){
    try {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

        mailHelper.setFrom(sendEmailService.FROM_ADDRESS);
        mailHelper.setTo(mailDto.getAddress());
        mailHelper.setSubject(mailDto.getTitle());
        mailHelper.setText(mailDto.getMessage(), true);           

        mailSender.send(mail);
    } catch(Exception e) {
        e.printStackTrace();
    }
}

```
-ํํด ์๋ด๊ฐ ๋ฐ๊ณผ ๊ฐ์ด ๋์ฐฉํฉ๋๋ค.
![์บก์ฒ](https://user-images.githubusercontent.com/85853167/150677086-1706e2ea-cd64-4732-8c0a-429ca36dd616.PNG)
---

