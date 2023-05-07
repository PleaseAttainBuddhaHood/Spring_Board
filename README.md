# Spring_Board
# 게시판 프로젝트(코드로 배우는 스프링 웹 프로젝트)



책, 코드로 배우는 스프링 웹 프로젝트 개정판   
https://product.kyobobook.co.kr/detail/S000001923741 의 모방작입니다.   
(원본 소스 https://cafe.naver.com/gugucoding/7819)



책을 그대로 따라가며 게시판을 만들었으며,


추가적으로 제가 필요하다고 생각한 부분을 수정하거나 덧붙였습니다.


변경사항은 제일 하단에 적어놓았습니다.

* * *

프로젝트 접속 URL : 


* * *
작지만 이 게시판을 만들며 중점을 두었던 것은


1. 가독성


2. 각종 책에서 배웠던 리팩터링 / 클린 코드의 적용


을 중점에 두려했고, 소소한 기능들은 네이버 카페를 기준 삼았습니다.




* * *
개발환경
- JDK: 11
- IDE: STS 3.9.18
- Spring: 5.0.7
- DB: Oracle 11g
- ORM: Mybatis
- Language: java, javascript, jquery, html, css



* * *
주요기능
 - 




* * *
아래는 변경사항입니다.



* * *
변경점

1. 본인 컴퓨터의 개발환경을 통일하기 위해 jdk 버전을 11로 변경하고, 그에 맞게 STS 버전도 3.9.18로 변경

1. 글 삭제를 '조회' 화면에서 처리할 수 있도록 변경


1. 모든 클래스 및 메서드의 내용을 가능한 [등록/조회/수정/삭제](crud) 순으로 구분 


2. 모든 클래스 및 메서드에 주석 추가


3. 검색 조건을 알려주는 클래스의 이름(Creteria)을 PageHandler로 변경


4. 애너테이션의 위치를 유지보수 편하게 조정(변경가능성이 높은 애너테이션이 위로 가게끔 변경)


5. 댓글 삭제 시, 삭제 버튼 누르면 바로 삭제되는 것이 아니라 confirm 창으로 한 번 더 확인하게끔 개선


6. 게시글 조회에서 첨부파일을 클릭하면 활성화되는 이미지를, 이미지가 아닌 파일은 공통 이미지를 표시하게 개선


7. fileType -> imageType


8. 책에서는 댓글 있는 게시글은 삭제 불가능하게 했으나, 댓글 있는 게시글도 삭제 가능하게 처리(게시글 삭제 처리 전, 전체 댓글 삭제 처리 추가)


9. '글쓰기' 버튼 위치 및 색상 변경


10. 게시물 등록창 게시물 제목과 내용을 빈 칸으로 놓지 않게 안내 메시지(alert) 추가


11. 자신이 작성하지 않은 댓글은 '저장', '삭제' 버튼이 보이지 않게하고, '댓글 내용' 탭도 readonly로 변경


12. 게시글 조회화면에서의 자바스크립트에서, 로그인한 사용자의 아이디를 가리키는 변수명인 replier를 의미있게 loginId로 변경


13. BSD 형식


14. IPO 관점에서 내용 위치 조정


15. JS var -> let 


클래스명 변경
 1. domain 패키지 Criteria -> PageHandler : 페이징 처리라는 의미가 직관적으로 와닿게 변경(+검색 조건, 스프링의 정석 참고하였음)
 2. 

변수명 변경(의미가 직관적으로 통하게 변경)
1. PageDTO total -> totalPage
2. PageDTO realEnd -> finalEndPage

3. PageHandler pageNum -> currentPage
4. PageHandler amount -> pageRowSize
5. PageHandler type -> searchType
6. PageHandler keyword -> searchKeyword

7. BoardAttachVO boolean fileType -> ImageType

8. ReplyVO replyer -> replier : replyer 라는 단어는 없음
