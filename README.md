# 🌏 All_Ganic (Back End-Spring)

![header](https://capsule-render.vercel.app/api?type=soft&color=auto&height=300&section=header&text=효율적이고%20일관성있는%20코드로&desc=깔끔한%20데이터%20전달에%20집중&fontSize=50&descSize=20&descAlign=75)

## 목차
1. [요약](#summary--요약)
2. [서버구조](#server-structure--서버구조)
3. [ERD 설계](#erd-설계)
4. [문제해결](#Problems-and-Solutions--문제-해결)
5. [특징](#features--특징)
6. [개발환경](#tech--개발환경)
7. [API](#Open-API--오픈-api)

## Summary / 요약
- __프로젝트 기간__ : 2021.10.04 - 2021.12.03
- __백엔드 구성__ : 정지희
- __메인 개발 환경__ : Spring Boot


## Server Structure / 서버구조
![프레젠테이션1](https://user-images.githubusercontent.com/85853167/147995935-a7cb34a9-0699-4763-ba5b-34d71b2becf6.png)
> Spring MVC를 바탕으로 제작이 되었습니다. View인 Vew에서 Rest API로 백의 Controller에 요청이 오면 Service, ServiceImpl를 통해 DB에 접근하게 됩니다.
> 이 때 Query문을 써야하면 Mybatis로 아니면 JPA를 이용하여 데이터에 접근합니다. 이 때 어떤 방법으로 접근했는지, 어떤 형태의 데이터가 받아오는지에 따라 Entity, DTO, Projection에 데이터를 담게 됩니다.


## ERD 설계
![캡처](https://user-images.githubusercontent.com/85853167/147996583-40b5b84d-f71e-420f-a659-2c9697015a9a.PNG)
총 15개의 테이블이 있으며 ADMIN, MEMBER로 구분하여 설계를 했습니다. 설계에 대한 저의 생각이나 구조를 더 자세히 보고싶으시다면 👉🏼[ERD COLUD](https://www.erdcloud.com/d/X52ATW8iNCRWnrLGW)를 눌러주세요.

## Problems and Solutions / 문제 해결
### 카카오 유저와 기존 회원과의 혼동
#### 1. 문제정의
- 기존회원과 카카오톡 회원의 이메일이 같을 시 구분불가 

#### 2. 사실수집
- 기존 회원의 이메일이 카카오톡 계정의 이메일과 동일 시 카카오톡 로그인과 사이트 내부 로그인 구분없이 로그인 시도가 가능해짐
- 비밀번호 찾기와 같은 기능은 기존회원만 가능(카카오톡은 유저 구별을 위해 암호가 따로 정해져있음), 카카오톡 로그인을 하려다 암호가 틀렸다는 알람을 보고 오해하여 암호를 변경할 수 있음

#### 3. 원인추론
- 로그인 시 sns으로 시도하려는 것인지 구분 없음

#### 4. 조사방법결정
- 카카오톡 로그인인지 구분하는 값을 Param으로 전달받고, 내부 security bcpe match기능으로 지정된 카카오톡 비밀번호와 비교해 맞는지를 구분

#### 5. 조사방법구현
``` javascript
// 로그인
// POST 127.0.0.1:8080/REST/api/member/login?sns=true
@PostMapping(value = "/member/login", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> memberLoginPOST(@RequestBody Member member, @RequestParam(name = "sns") Boolean sns) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        int check = mServiece.leaveMemberCheck(member.getUseremail());
        if(check == 1){ // 탈퇴한 회원
            map.put("result", 4L);
            map.put("state", "이미 탈퇴하신 회원입니다.");
        }
        else{ // 탈퇴하지 않은 회원
            Member member1 = mServiece.getMemberOne(member.getUseremail());
            if(sns == true){ // 카카오로 로그인 시
                if (bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 1L);
                    map.put("token", jwtUtil.generateToken(member.getUseremail()));
                }
                else{
                    map.put("result", 0L);
                    map.put("state", "카카오 유저가 아닙니다. 사이트 로그인에서 로그인을 시도하여 주십시오.");
                }
            }else{ // 그냥 로그인 시
                if(bcpe.matches("kakao_login_pw", member1.getUserpw())) {
                    map.put("result", 0L);
                    map.put("state", "카카오 유저입니다. 카카오 로그인에서 로그인을 시도하여 주십시오.");
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

// 비밀번호 변경 시 카카오유저인지 확인
// GET 127.0.0.1:8080/REST/api/kakao/user/check
@GetMapping(value = "/kakao/user/check")
public int kakaoUserCheckGET(@RequestHeader("token") String token) {
    int result;
    try {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
        // 아이디를 이용해 기존 정보 가져오기
        Member member = mServiece.getMemberOne(useremail);
        // 토큰과 사용자 아이디 일치 시점
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

#### 6. 문제해결
- 암호화된 비밀번호를 내부 기능인 bcpe로 이메일과 카카오톡 비밀번호를 비교해 맞는지를 구분

---
### 회원탈퇴, 물품삭제 시 연관된 정보들 일괄 처리
#### 1. 문제정의
- 회원탈퇴, 물품삭제 등 외래키로 잡혀있는 정보가 있을 시 에러발생

#### 2. 사실수집
- 회원의 경우 외래키로 지정된 CartItem, Cart, CancleHistory, PayHistory, Report, Question, Review가 정리되야지 삭제 가능
- 물품의 경우 외래키로 지정된 SubImage, Question, Review, CartItem이 정리되야지 삭제 가능

#### 3. 조사방법결정
- EntityManager의 기능인 Transaction을 사용하여 한 번에 처리

#### 4. 조사방법구현
``` javascript
// 물품 삭제 시 이루어지는 트랜잭션
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

// 회원  시 이루어지는 트랜잭션
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

#### 5. 문제해결
- Transaction을 사용하여 연관 정보들을 Query문을 통해 일괄 처리, 회원의 경우 결제와 같은 중요한 정보를 지니고 있기때문에 탈퇴한다는 날로부터 1년 뒤를 정보삭제날로 지정 후 매일 자정 스케쥴러를 이용해 LeaveCheck가 ture이면서 LeaveDate가 당일인 것들을 삭제
- 연관정보 중 누적통계에 필요한 결제정보의 경우 외래키인 Member를 ghost라는 임시 계정을 참조하게 하여 회원정보는 사라지고 필요한 통계정보만 남기게함
- 물품의 경우 결제정보등과 연관이 되어 물품 대표이미지를 null로 변경 후 연관된 정보들은 삭제하여 최소한의 정보만 

---
### 결제 시 필요한 정보 전달
#### 1. 문제정의
- 하나 혹은 여러개의 장바구니 아이템 정보를 하나씩 찾아 정보를 전달

#### 2. 사실수집
- 결제페이지로 넘길 때 체크한 물품정보와 회원정보를 list형태로 병합하여 보내야함.
- param에서 List형태로 넘어오지만 여러개가 아닌 하나의 코드만 넘어올 수 있으며 결제가 되었을 시 결제한 장바구니아이템은 삭제처리가 되야하므로 여러테이블의 데이터를 넘겨야함.

#### 3. 조사방법결정
- VIEW를 생성하여 필요한 정보들을 모은 다음 해당 VIEW에서 foreach문을 통해 

#### 4. 조사방법구현
``` javascript
// 결제에 필요한 정보인 PAYMENTLIST VIEW생성
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
   
// 결제 시 멤버, 물품 정보 넘기기
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

#### 5. 문제해결
- 외래키 정보를 통해 각 테이블을 INNER JOIN한 뒤 필요한 데이터를 담은 VIEW를 생성하여 해당 VIEW에서 foreach를 통하여 정보를 찾아 전달

---
### 최근 5일간의 일일판매량 통계
#### 1. 문제정의
- 판매가 이루어지지 않은 날은 통계에 나타나지않음

#### 2. 사실수집
- PAYHISTORY테이블에서 자료를 수집해오면 판매가 없는 날은 자료 수집이 불가
- 데이터가 없는 날은 날짜 생성과 더불어 개수 또한 0으로 나타내야함

#### 3. 조사방법결정
- 최근 5일의 날짜를 저장할 테이블을 생성 및 NVL을 이용해 없는 데이터 표시

#### 4. 조사방법구현
``` javascript
// 판매량조회를 위한 날짜 테이블 
@Update({
    "UPDATE DUAL SET DUAL_DATE=#{date} WHERE DUAL_ID=#{no}"
})
public int InsertDate(@Param("no") long no, @Param("date") Date date);

// 해당일의 최근 5일간의 판매량 조회
@Select({
    "SELECT DUAL.DUAL_DATE, (NVL(DATE1.CNT, 0)) AS CNT FROM DUAL ",
    "LEFT OUTER JOIN DATE1 ON DUAL.DUAL_DATE = DATE1.ORDERDATE ORDER BY DUAL.DUAL_DATE ASC"
})
public List<Map<String, Object>> selectSalesRate();

// 5일간의 날짜와 payhistory개수를 리스트로 출력
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
#### 5. 문제해결
- 반복문을 통해 D-5의 날짜를 테이블에 저장한 후 판매량을 조회
- LEFT JOIN을 통해 테이터를 합한 후 판매가 없는날은 NVL(DATE1.CNT, 0)를 통해 판매량을 0으로 표시하여 정보를 전달

---
### 동적Query문에서의 Pagenation처리와 정렬
#### 1. 문제정의
- JPA의 Pageable과 같은 것은 동적Query에 없음

#### 2. 사실수집
- 한 페이지당 나타내는 개수에 맞추어 각 페이지에 나타나는 데이터의 시작번호와 끝번호를 매겨 페이징 처리를 해야함

#### 3. 조사방법결정
- 페이지당 나타낼 데이터의 개수에 따라 시작번호와 끝번호를 param으로 넘겨 ROWN BETWEEN을 사용하여 페이징 처리

#### 4. 조사방법구현
``` javascript
// 문의글 답글여부, 종류별 조회(날짜 기준 정렬)
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

 // 답글여부, 문의종류에 따른 리스트 출력(admin)
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
#### 5. 문제해결
- ROWN을 사용하여 번호를 매기고 시작번호와 끝번호를 BETWEEN을 통해 데이터를 수집함 
- ROWN 사용 시 order by를 이용하면 순서가 뒤죽박죽이 되기때문에 ROW_NUMBER() OVER를 사용하여 정렬을 해줌

---
### 리뷰등록 가능 여부
#### 1. 문제정의
- 작성하려는 물품을 구매하지않은 사람도 리뷰작성이 가능

#### 2. 사실수집
- 구매한 물품한에서 리뷰 작성이 가능해야하는데 그렇지 않음
- 결제를 해서 이미 리뷰를 작성한 사람 중 또 해당 물품을 구매하여 리뷰를 쓰려는 경우 리뷰는 한 물품 당 하나만 작성가능하기때문에 오류 발생

#### 3. 조사방법결정
- 결제내역 확인과 이미 리뷰를 작성했는지 확인

#### 4. 조사방법구현
``` javascript
// 결제내역확인을 통해 리뷰작성가능한지 확인
// GET 127.0.0.1:8080/REST/api/payments/paylist/check?no=14
@GetMapping(value="/payments/paylist/check")
public int payhistoryCheckListGET(@RequestParam("no") Long no, @RequestHeader("token") String token) {
    int i;
    try{
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
        Map<String, Object> check = phService.checkPayHistory(no, useremail);
        Long count = (Long)check.get("COUNT(MEMBER)");
        Boolean review = (Boolean)check.get("MAX(REVIEWCHECK)");
        if(count >= 1 && review == true){
            i = 2; // 이미 작성한 리뷰가 있습니다
        }
        else if(count >= 1 && review == false){
            i = 1; // 리뷰 작성 가능
        }
        else{
            i = 0; // 리뷰작성 불가
        }

    }
    catch (Exception e) {
        e.printStackTrace();
        i = e.hashCode();
    }
    return i;
}

// 결제내역 확인에 필요한 PAYHISTORYLIST VIEW생성
CREATE VIEW PAYHISTORYLIST AS SELECT 
   PRODUCT.PRODUCTCODE, PRODUCT.PRODUCTNAME, PRODUCT.PRODUCTPRICE, BRAND.BRANDNAME, 
   PAYHISTORY.ORDERQUANTITY, PAYHISTORY.ORDERDATE, PAY.MERCHANT_UID, PAYHISTORY.MEMBER, PAYHISTORY.REVIEWCHECK
FROM 
   PAYHISTORY, PAY, PRODUCT, BRAND
WHERE 
   PAYHISTORY.PAY = PAY.IMP_UID AND
   PAYHISTORY.PRODUCT = PRODUCT.PRODUCTCODE AND
   PRODUCT.BRAND = BRAND.BRANDCODE

// 회원과 물품정보에 따른 결제내역 조회(리뷰작성가능한지)
@Select({
        "SELECT COUNT(MEMBER), max(REVIEWCHECK) FROM PAYHISTORYLIST  ", 
        "WHERE MEMBER=#{email} AND PRODUCTCODE=#{no}",
})
public Map<String, Object> selectPayHistoryCheck(@Param("no") Long no, @Param("email") String email);
```
#### 5. 문제해결
- review를 작성하게 되면 해당 주문정보의 reviewCheck부분이 true로 변경되는데 한 물품을 여러번 구매했더라도 max(REVIEWCHECK)를 이용하여 리뷰를 한 번만 작성가능하게 구분

---

## Features / 특징
### 추천물품
> 실제 사이트의 회원들이 결제한 데이터들을 쌓아 추천물품을 나타내고 싶었습니다. 처음엔 딥러닝과 같은 인공지능이 생각이 났지만 아직 배워보지못한 분야라 다른 방법을 찾았습니다. 
> 
> 인공지능의 의미를 생각해보니 사용자들의 데이터들을 쌓아 스스로 학습하는 것이라는 생각에 새로운 방법이 떠올랐습니다. 
> 
> 회원들이 결제를 한 내역을 통해 추천물품을 나타나게 하는 것이었습니다. 관심있는 물품을 보러 들어오면 해당 물품코드에 쌓인 데이터들 중 구매회수가 가장 많은 추천물품 코드를 뽑아 나타나게 하는방법이었습니다.
> 원리는 다음과 같습니다. 관심있는 물품코드가 있고 그 안에 추천물품 코드와 구매회수의 데이터를 쌓는 것이었습니다. 초기의 생각은 물품코드라는 key를 찾고 그 안의 추천물품 코드라는 key와 구매회수라는 value가 있으니 key: {key: value} 즉, Hash Map의 value안에 Hash Map이 있는 것이었습니다. 
> 
> 하지만 테이블 컬럼에 HashMap과 배열로 되지않았고 잔머리를 사용해 밑과 같은 구조로 하였습니다.
``` javascript
// Recommend 테이블 설정
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

    @OneToOne // 물품정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @Column(name = "RECOMMENDKEY")
    private String recommendkey = null;

    @Column(name = "RECOMMENDVALUE")
    private String recommendvalue = null;
}
```

``` javascript
// 물품 구매 시 추천물품에 추가
// PUT 127.0.0.1:8080/REST/api/add/recommended
@PutMapping(value="/add/recommended")
public Map<String, Object> addRecommendProduct(@RequestHeader("token") String token, @RequestParam("no") List<Long> no) { // 결제한 물품코드번호
    Map<String, Object> map = new HashMap<String, Object>();
    Map<Long, Long> re = new HashMap<Long, Long>();
    try{
        String useremail = jwtUtil.extractUsername(token.substring(7)); // token을 통해 회원정보(이메일) 찾기
        Long product = pService.latestOrder(useremail); // 이전에 결제한 물품번호
        String key = "";
        String count = "";
        String[] st1;
        String[] st2;
        if(product != null){
            Map<String, Object> list = rService.checkRecommend(product);
            Recommend recommend = rService.findRecommend(product);
            if(list != null){ // 추천물품에 list가 있다면
                st1 = recommend.getRecommendkey().split(",");
                st2 = recommend.getRecommendvalue().split(",");
                for(int i=0; i<st1.length; i++){
                    re.put(Long.parseLong(st1[i]), Long.parseLong(st2[i]));
                }
                for(int j=0; j<no.size(); j++){
                    if(re.containsKey(no.get(j)) && product != no.get(j)){ // list안에 구매 물품번호가 있는지
                        re.put(no.get(j), re.get(no.get(j)) + 1); // 확인 후 +1
                    }else if(!re.containsKey(no.get(j)) && product != no.get(j)){ // 구매하려는 물품과 같은 코드인지 확인
                        re.put(no.get(j), 1L); // 물품번호가 없다면 추가
                    }
                }
                for(Entry<Long, Long> elem : re.entrySet()){ 
                    key += elem.getKey() + ",";
                    count += elem.getValue() + ","; 
                }
                rService.updateKeyValue(key, count, product);
            }else{ // 추천물품에 list가 없다면 새로 추가
                for(int j=0; j<no.size(); j++){
                    key += String.valueOf(no.get(j)) + ",";
                    count += String.valueOf(1) + ",";
                }
                rService.updateKeyValue(key, count, product);
            }
            map.put("result", 1);
            map.put("state", "추천물품 추가완료");
        }
        else if(product == null && no.contains(product)){
            map.put("result", 1);
            map.put("state", "이력 없음");
        }

    }
    catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}
```
- 처음에 생각한 것과 같이 Hash Map안의 Hash Map의 key와 value들을 컬럼으로 저장하는 것입니다.
- 물품코드는 OneToOne으로 등록되어있는 모든 물품들을 등록해놓습니다. 그리고 Recommend Key, Recommend Value들에 추천물품코드와 구매회수를 ','를 기준으로 넣어놓고 이를 String으로 변환해 저장하는 것입니다. 이렇게 되면 나중에 spilt 메서드를 통해 각 추천물품코드와 구매회수를 출력할 수 있게됩니다.
- 회원이 결제를 하면 해당 회원이 이전에 결제한 내역 중 최근의 물품 코드에 쌓여있는 데이터가 있는지 확인하고 있다면 이를 split을 사용하여 배열에 넣고 반복문을 통해 Map에 넣습니다. 
- Map의 기능인 containsKey와 get을 사용하여 이미 쌓여있는 데이터에 결제한 물품의 데이터가 있는지 확인하고 없다면 1과 함께 추가하고 있다면 쌓여있는 구매회수에 +1을 해줍니다. 

``` javascript
// 물품상세페이지에 추천물품코드 출력
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
            // 비교함수 Comparator를 사용하여 오름차순으로 정렬
            Collections.sort(list_entries, new Comparator<Entry<Long, Integer>>() {
                // compare로 값을 비교
                public int compare(Entry<Long, Integer> obj1, Entry<Long, Integer> obj2) {
                    // 오름 차순 정렬
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
- 관심있는 물품을 눌러 상세페이지로 이동하였을 때 해당 물품코드에 저장되어있는 추천물품 데이터들을 찾습니다.
- 다시 split을 사용해 배열에 넣고 이를 반복문을 통해 map에 넣어준 다음 compare 기능으로 구매회수를 기준으로 오름차순 정렬을 해줍니다. 정렬된 list_entries의 0번째 key 즉, 추천물품코드를 리턴하면 됩니다. 
- 만약 저장된 추천물품 데이터가 없다면 랜덤으로 물품을 출력하게 되는데 ISBN을 사용한 카테고리코드의 6자리 중 앞 세자리가 같은 카테고리 내에서 물품을 랜덤으로 추천하게 하였습니다.
---

### 신고기능
> 다른 사이트들을 사용하다보면 리뷰나 문의를 통해 악의적인 행위를 하는 것을 본적이 있습니다. 
> 
> 그래서 저희 사이트에서 이러한 행위를 다른 회원들이 판단하여 신고를 하면 관리자가 판단하여 경고를 하는 등의 행위를 할 수 있도록 신고기능을 넣었습니다.
> 문의는 관리자만 볼 수 있게 해놓았기 때문에 리뷰를 회원들이 신고할 수 있게 하였고 관리자페이지에서 문의를 보고 관리자가 판단하여 신고할 수 있게 해주었습니다.

``` javascript
// 물품상세페이지에 추천물품코드 출력
 @Id
@Column(name = "REPORTCODE")
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPORT_NO")
private long reportcode = 0L;

@OneToOne // 회원정보
@JoinColumn(updatable = false, name = "MEMBER")
private Member member;

@Column(name = "REPORTDATE")
private String reprotdate = null;

@Column(name = "REPORTCOUNT")
private Long reportcount = 0L;
```
-한 회원당 하나의 신고데이터를 가지게 OneToOne으로 해주었고 관리자가 해당 회원이 언제 신고당했는지 알기위해 마찬가지로 date를 ','를 기준으로 String으로 저장했습니다.
``` javascript
// 신고하기(리뷰, 문의)
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
        map.put("state", "신고접수가 완료되었습니다.");
    }
    catch (Exception e) {
        e.printStackTrace();
        map.put("result", e.hashCode());
    }
    return map;
}
```
-관리자 페이지에서 신고된 회원들을 따로 볼 수 있는 페이지를 따로 구성하였고 해당 페이지에서 신고 횟수와 날짜를 볼 수 있습니다.

---

### 메일기능
> 사이트에서 필요한 기능 중 비밀번호 찾기와 회원탈퇴를 메일로 구현해보고 싶었습니다.
```javascript
// 비밀번호 찾기 시 임시비밀번호 발송 
public MailDto createMailAndChangePassword(String userEmail, String userName){
    String str = getTempPassword();
    MailDto dto = new MailDto();
    dto.setAddress(userEmail);
    dto.setTitle(userName+"님의 ALL_GANIC 임시비밀번호 안내 이메일 입니다.");
    dto.setMessage("안녕하세요. ALL_GANIC 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" +"님의 임시 비밀번호는 "
    + str + " 입니다.");
    updatePassword(str,userEmail);
    return dto;
}

public void updatePassword(String str,String userEmail){
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    Member member = mService.getMemberOne(userEmail);
    member.setUserpw(bcpe.encode(str));
    // 아이디, 암호를 새로운 기본값으로 대체
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
    System.out.println("이멜 전송 완료!");
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailDto.getAddress());
    message.setFrom(sendEmailService.FROM_ADDRESS);
    message.setSubject(mailDto.getTitle());
    message.setText(mailDto.getMessage());

    mailSender.send(message);
}

```
-임시비밀번호 안내가 밑과 같이 도착합니다.

---

## Tech / 개발환경

## Open API / 오픈 api
![header](https://capsule-render.vercel.app/api?type=soft&height=300&text=Hello%20World!&desc=Hello%20capsule%20render)
