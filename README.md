# 게시판 프로젝트



책, 코드로 배우는 스프링 웹 프로젝트 https://product.kyobobook.co.kr/detail/S000001923741 의 모방작입니다.     
(원본 소스 https://cafe.naver.com/gugucoding/7819 에서 파트7_2 폴더 참조)


***
기본적으로 책을 처음부터 끝까지 그대로 따라가며(공부하며) 게시판을 만들었으며,


기능적으로 개선할 부분은 개선하고자 하였습니다.


변경사항은 제일 하단에 적어놓았습니다.

* * *

### 프로젝트 접속 URL :    
 id: admin90   
 pw: pw90   

* * *
작지만 이 게시판을 만들며(수정, 개선하며) 중점을 두었던 것은


1. 가독성 / 일관성 향상


2. 각종 책에서 배웠던 리팩터링 / 클린 코드의 적용


이었으며, 변경된 기능/화면(삭제 버튼 등)은 네이버 카페를 기준 삼았습니다.




* * *
### 개발환경
- **JDK**: 11
- **IDE**: STS 3.9.18
- **Framework**: Spring 5.0.7
- **DB**: Oracle 11g
- **ORM**: Mybatis
- **Language**: java, javascript, html, css
- **Library**: jquery, bootstrap



* * *
### 주요기능   
 - 게시글   
 : 등록/조회/수정/삭제/페이징/검색   
 
 - 댓글(Ajax)   
 : 등록/조회/수정/삭제/페이징   
      
 - 로그인/로그아웃(Spring Security)    
 
 - 자동 로그인   
  
 - 파일(image vs non image)   
 : 등록/조회/다운로드/삭제   
 : 체크 스케줄러   




* * *
### 아래는 변경사항입니다.


1. 다른 프로젝트와의 개발환경 통일을 위해 JDK 버전을 11로 변경, 그에 맞게 STS 버전도 3.9.18로 변경

2. 모든 코드 내용을 가능한 '등록/조회/수정/삭제'(CRUD) 또는 '입력-처리-출력(IPO)' 순으로 변경 

3. BSD 형식 적용(중괄호 위치 조정)
![image](https://user-images.githubusercontent.com/83068670/236707697-8dc0d311-eab6-4cbb-ac6c-26ee4c5b6e56.png)
↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓   
![image](https://user-images.githubusercontent.com/83068670/236707715-69045b85-77fe-4bc9-af6f-90685dbf1347.png)

* BSD 형식은 홀로 진행하는 이 프로젝트의 특성이 반영된 본 개발자의 선호일 뿐이며,   
* 회사 업무시 팀 컨벤션에 따라 K&R 형식으로 업무 가능

   
      
         
         
5. 중요 부분 주석 추가

6. 각 애너테이션의 위치를 유지보수 편하게 조정(변경가능성이 높은 애너테이션이 상단으로 가게끔 변경)
![image](https://user-images.githubusercontent.com/83068670/236707354-89f8fe0b-bb01-4299-9f9c-25cf6aff533c.png)   
↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓   
![image](https://user-images.githubusercontent.com/83068670/236707392-89091589-7fab-4a79-9dd9-171d58bb0e0a.png)


5. 댓글 삭제 시, 삭제 버튼 누르면 바로 삭제되는 것이 아니라 confirm 창으로 한 번 더 확인하게끔 개선
![image](https://user-images.githubusercontent.com/83068670/236759559-ce65f181-29d6-4032-a1dd-ec0f30de6f1d.png)   

   

8. 책에서는 댓글 있는 게시글은 삭제 불가능하게 했으나, 댓글 있는 게시글도 삭제 가능하게 처리(게시글 삭제 처리 전, 전체 댓글 삭제 처리 추가)   
![image](https://user-images.githubusercontent.com/83068670/236708570-6bd878d8-191b-4e94-ab87-be3afc049acf.png)


9. '글쓰기' 버튼 위치 및 색상 변경


10. 게시물 등록창 게시물 제목과 내용을 빈 칸으로 놓지 않게 안내 메시지(alert) 추가


11. 자신이 작성하지 않은 댓글은 '저장', '삭제' 버튼이 보이지 않게하고, '댓글 내용' 탭도 readonly로 변경


12. 게시글 조회화면에서의 자바스크립트에서, 로그인한 사용자의 아이디를 가리키는 변수명인 replier를 loginId로 변경


1. 글 삭제를 '조회' 화면에서 처리할 수 있도록 변경
 


### 클래스명
1. domain 패키지 Criteria -> PageHandler : 페이징 처리(+검색조건 처리)라는 의미가 직관적으로 와닿게 변경(강의 스프링의 정석을 참고하였음)
 
   
### 변수명
1. PageDTO total -> totalPage
2. PageDTO realEnd -> finalEndPage

3. PageHandler pageNum -> currentPage
4. PageHandler amount -> pageRowSize
5. PageHandler type -> searchType
6. PageHandler keyword -> searchKeyword

7. BoardAttachVO boolean fileType -> ImageType

8. ReplyVO replyer -> replier (replyer라는 영단어는 없음)


### 변수 선언
1. Javascript var -> let 
