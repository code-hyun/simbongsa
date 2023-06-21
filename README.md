# simbongsa |<a href="http://www.simbongsa.site/main/"> JPA 프로젝트 : 봉사, 후원, 펀딩, 커뮤니티 종합 플랫폼 서비스(http://www.simbongsa.site/main/)</a>

## 🖥️ 프로젝트 소개
봉사활동에 참여하고자 하는 사람들에게 편리한 플랫폼을 제공하여, 다양한 봉사기회를 찾을 수 있도록 돕고 사회적 상호작용과 긍정적 영향을 증가시켜 사회적 가치 창출과 지역사회 발전에 도움을 줄 수 있다.

<br>

## 🕰️개발 기간
* 23.04.11 - 23.05.25

## 🧑‍🤝‍🧑 맴버구성 - 백엔드 업무
 - 팀장  : 황자현 - 메인페이지, 봉사페이지, 후원페이지
 - 부팀장 : 문은서 - 펀딩
 - 팀원1 : 김주연 - 로그인, 회원가입, PW찾기, 마이페이지
 - 팀원2 : 김서현 - 커뮤니티 게시판
 - 팀원3 : 정현진 - 관리자페이지, 복지센터 검색, 고객센터
 
 
 ## ✨ 프로젝트 목적 
  지역사회 발전을 위해 봉사활동의 중요성을 인식시키고, 다양한 연령층의 봉사활동 참여를 유도하기위한 목적
 
 ## ✨ 기획배경
 <img width="1209" alt="image" src="https://github.com/code-hyun/simbongsa/assets/122762287/6e3c40ae-9da3-41ee-a062-8560af985aec"><br>

 현대 사회에서 많은 사람들이 도시에 살며 작은 공간에서 살아가고 있음에 따라 집안에 물건을 보관하는 것도 쉽지 않은 문제가 되고 있다. 이러한 문제점을 해결하기 위해, 창고 제공자와 창고 사용자를 연결해주는 서비스를 만들어 창고 제공자는 방치된 빈 방을 경제적이고 활용성 있게 만들 수 있고, 창고 사용자는 집의 여유공간을 확보하여 더 편안한 생활을 할 수 있도록 기획하였습니다.

 ## ✨ 목적 및 기대 효과
 <img width="1208" alt="image" src="https://github.com/code-hyun/simbongsa/assets/122762287/b6cfd527-276f-44ad-a931-b18c37393ae2">

- 다양한 봉사 활동 체험 : 다양한 봉사 활동 체험을 통해 누구나 손쉽게 봉사활동에 참여 할 수 잇는 기회를 제공
- 자발적 참여 유도 :  봉사활동을 통해 적립한 포인트를 후원할 수 있는 방식으로 봉사자들에게 보상감을 제공, 
 후원자들은 포인트를 사용하여 후원을 할 수 있는 기회를 제공하여 봉사활동과 후원 모두에 대한 관심과 참여를 유도
- 청년층 참여 활성화 : 사회 구성원들 간의 연대와 협력을 촉진하여 사회적 유대감과 상호의존성을 강화
- 봉사활동 공유 문화 확산 : 후원과 펀딩을 통해 봉사활동을 지원하는 모든 사람들이 함께 사회적 책임과 가치를 공유하는 문화를 확산시키며, 지속 가능한 사회 발전에 기여

## 개발 환경
- java
- jQuery
- Thymeleaf
- QueryDSL
- JPA
- Spring Boot
- HTML, CSS, JS
- Oracle
- JDK 11.0.15
- YAML
- JSON
- REST:API
- Sourcetree
- DBeaver
- IntelliJ IDEA
- git, gitHub
- JUnit5
- POSTMAN
- Lombok
- Boot pay
- Naver OAuth
- Kakao OAuth
- Cool SMS api 

 ## 📌 프로젝트에서 맡은 역할 
- 프로젝트 팀장, 서비스 기획 및 전반적인 구성, 엔티티 설계
- 퍼블리싱 업무 : 펀딩페이지, 관리자페이지
- 백엔드 업무 : 메인페이지, 봉사페이지, 후원페이지
- AWS 서버 배포 <a href="http://www.simbongsa.site/main/">simbongsa</a>

### 퍼블리싱 작업(회원가입/로그인) - <a href="https://github.com/code-hyun/simbongsa/wiki/맡은기능-소개(퍼블리싱)" > 상세보기 - WIKI 이동</a>
 1. 관리자 페이지 <br>
 2. 펀딩 페이지 <br>

### 백엔드 작업(관리자 페이지) - <a href="https://github.com/code-hyun/simbongsa/wiki/맡은기능-소개(백엔드)" >상세보기 - WIKI 이동</a>
 1. 메인페이지<br>
    1) 봉사 목록 조회<br>
    2) 후원 목록 조회<br>
    3) 고객 랭킹 목록 조회<br>
 2. 후원 페이지 <br>
    1) 후원 요청하기(작성)<br>
    2) 후원 게시물 목록(목록, 카테고리, 지역별 조회)<br>
    3) 후원 상세페이지(상세조회, 후원하기, 참여내역조회)<br>


## ✨ ERD
<img width="1025" alt="심봉사 ERD" src="https://github.com/code-hyun/simbongsa/assets/122762287/2b084aae-fb2a-4550-85ad-08a89ddaf375">

## ✨ 프로젝트에서 느낀점
#### ✨어려웠던 부분 <br>
엔티티 설계를 진행하면서 연관관계에 대해서 어려움을 겪었던 경험이 있습니다. 처음에는 엔티티들 간의 관계를 명확하게 파악하기 어려워서 혼란스러웠습니다. 각 엔티티가 서로 어떻게 연결되어야 하는지 이해하기 위해 많은 시간을 투자했고, 여러번의 시도와 수정을 거쳤습니다. <br>
<br>
일대다(1:N) 관계에서 외래 키(Foreign Key)의 위치를 결정하는 것에 어려움을 겪었습니다. 어느 테이블에 외래 키를 추가해야 하는지 혼란스러웠고, 데이터의 일관성과 효율성을 고려하여 최적의 구조를 찾는 것이 어려웠습니다. 특히, 엔티티 간의 관계가 복잡하고 다양한 조건이 존재할 경우에는 더욱 어려움을 겪었습니다.<br>
<br>
하지만 이 경험을 통해 데이터베이스 설계에 대한 이해와 역량이 크게 향상되었습니다. 문제를 해결하기 위해 노력하고 고민하는 과정에서 엔티티 간의 관계를 명확히 이해하고, 데이터베이스 구조를 최적화하는 방법에 대해 배울 수 있었습니다. 이러한 경험은 저에게 소중한 성장 기회였으며, 앞으로의 프로젝트에서 더 나은 데이터 모델링과 관계 설계를 할 수 있게 되었습니다.

<!-- # <서버에 관한것도 써보자> -->

##### ✨문제를 해결했던 부분
후원 기능을 구현해야 했습니다. 그런데 기존 코드에 버그가 있어서 후원 버튼을 클릭해도 모달 창이 나타나지 않는 문제가 발생했습니다.

먼저, 코드를 확인하고 디버깅을 시작했습니다. 버튼 클릭 이벤트를 처리하는 부분에서 문제가 발생하는 것 같았습니다. 코드를 하나씩 분석해보니 click 이벤트가 정상적으로 연결되어 있었지만, 콜백 함수가 실행되지 않는 것을 발견했습니다.

이에 따라 click 이벤트의 콜백 함수를 확인하니, 로그인 상태와 후원 가능한 공양미를 확인하는 부분에서 오류가 있었습니다. 코드에서 memberDTO와 관련된 부분이 문제의 원인이었습니다.

문제를 해결하기 위해 memberDTO가 올바른 값을 가져오는지 확인하기 위해 콘솔 로그를 추가했습니다. 그리고 버튼 클릭 시 해당 로그를 확인해보았습니다.

로그 결과, memberDTO가 null로 설정되어 있어서 오류가 발생하는 것을 확인했습니다. 이는 로그인 상태를 확인하는데 사용되는 변수였습니다.

해결책으로, 로그인이 필요한 기능이므로 로그인을 한 상태에서만 후원 기능을 사용할 수 있도록 조건문을 추가했습니다. 만약 memberDTO가 null이거나 memberDTO.memberRice가 null인 경우, 오류 메시지가 있는 모달 창을 보여주고 후원 기능을 사용할 수 없도록 했습니다.

또한, 후원 기능이 정상적으로 동작하는 경우에는 후원 입력 폼이 있는 모달 창을 보여주도록 수정했습니다. 이 폼에서는 공양미를 입력할 수 있는 텍스트 필드와 확인 버튼이 있었습니다.

모든 수정 사항을 반영하고 코드를 다시 테스트한 결과, 버그가 해결되었고 후원 버튼을 클릭하면 정상적으로 모달 창이 나타나는 것을 확인할 수 있었습니다.

이렇게 문제를 발견하고 디버깅하여 코드를 수정하고, 마지막으로 정상 동작하는 것을 확인하는 과정을 거쳐 문제를 해결한 경험을 가지고 있습니다.

##### ✨협업의 중요성
우리는 6명의 팀원으로 구성된 프로젝트를 진행하기로 결정했습니다. 그러나 중간에 한 명의 팀원이 다른 사정으로 인해 팀에서 떠나야 했습니다. 이로 인해 팀 인원이 6명에서 5명으로 줄어들었습니다.

이 상황에서 프로젝트를 성공적으로 완료하기 위해 우리는 협업의 중요성을 더욱 강조해야 했습니다. 우리 팀은 실력이 출중한 편은 아니었지만, 소통과 문제 해결에 능숙한 리더십을 발휘할 수 있었습니다.

먼저, 팀 인원이 줄어들었기 때문에 모든 팀원들이 더욱 책임감을 가지고 역할을 분담해야 했습니다. 우리는 프로젝트의 각 분야를 팀원들 사이에 공정하게 나누었습니다. 각자의 역할과 책임을 명확하게 정의함으로써 협업의 효율성을 높였습니다.

프로젝트 진행 중에는 문제와 어려움이 발생했습니다. 그러나 팀원들은 서로의 의견을 존중하고 소통을 통해 문제를 해결하기 위해 노력했습니다. 우리는 주기적으로 회의를 열어 문제점을 공유하고 함께 해결책을 찾아나갔습니다. 이러한 소통과 협력은 프로젝트의 진행을 원활하게 만들어주었습니다.

또한, 리더십의 역할도 중요했습니다. 저는 팀의 리더로서 팀원들의 역할과 일정을 관리하고, 필요한 지원과 조언을 제공하는 역할을 수행했습니다. 또한, 팀원들의 의견을 경청하고 효과적인 의사 결정을 내리는 과정에서 리더십을 발휘했습니다. 이를 통해 팀원들이 자신의 역할을 수행하며 최선을 다할 수 있었습니다.

최종적으로, 우리는 5명의 팀원으로서 협업을 통해 프로젝트를 성공적으로 완료할 수 있었습니다

