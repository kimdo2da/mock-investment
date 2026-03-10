# Mock Investment

JSP/Servlet 기반의 모의 주식 투자 웹 프로젝트입니다.  
사용자는 회원가입 후 로그인하여 주식 정보를 조회하고, 매수/매도 및 포트폴리오 관리를 할 수 있습니다.  
또한 경제 뉴스 기능을 통해 주요 시장 이슈를 함께 확인할 수 있도록 구성했습니다.

## 주요 기능

- 회원가입 / 로그인 / 로그아웃
- 회원가입 시 계좌 자동 생성(100만원 들어가있음)
- 마이페이지에서 사용자 정보 및 자산 확인
- 주식 목록 조회
- 실시간 주가 조회
- 주식 매수 / 매도
- 보유 주식 및 포트폴리오 조회(언제 사고 언제 팔았는지)
- 경제 뉴스 조회
- 관리자 페이지(수익 랭킹)

## 사용 기술

- Java
- JSP / Servlet
- Apache Tomcat
- MySQL
- JDBC
- HTML / CSS / JavaScript
- JSON / Gson
- 외부 API 연동
  - 뉴스 API(딥서치 뉴스)
  - 주가 조회 API(한국투자증권)
  - 주식 그래프 API(야후 파이낸스)

## 프로젝트 구조

- `src/main/java/project`
  - DAO, DTO, Servlet, API 연동 클래스
- `src/main/webapp`
  - JSP 페이지, CSS, WEB-INF 설정 파일
- `src/main/webapp/WEB-INF/lib`
  - 프로젝트 실행에 필요한 라이브러리

## 대표 페이지

- `index.jsp` : 메인 페이지
- `login.jsp` : 로그인
- `signup.jsp` : 회원가입
- `mypage.jsp` : 마이페이지
- `stock.jsp` : 주식 조회 및 거래
- `admin.jsp` : 관리자 페이지

## 핵심 구현 내용

이 프로젝트는 단순 화면 구현이 아니라,  
DAO/DTO 패턴을 활용해 사용자 정보, 계좌, 거래 내역, 포트폴리오, 뉴스 데이터를 분리하여 관리하도록 설계했습니다.

특히 뉴스 API와 주가 조회 API를 연동하여  
사용자가 시장 뉴스와 주식 정보를 함께 확인하면서 모의 투자 흐름을 경험할 수 있도록 구현했습니다.

## 실행 환경

- JDK 21
- Apache Tomcat 10.1
- MySQL
- Eclipse EE

## 느낀 점

JSP/Servlet 기반으로 사용자 인증, 데이터베이스 연동, 외부 API 처리,  
주식 매매 로직까지 직접 구현하면서 웹 백엔드 구조를 전체적으로 익힐 수 있었습니다.  
특히 DAO/DTO 설계와 세션 처리, JDBC 연결, API 응답 데이터 파싱에 대한 이해를 높일 수 있었습니다.
