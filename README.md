# 전 세계 공휴일 관리
- nager.date 참조하여 공휴일 관리(https://date.nager.at)

## 문서화
- 아래 주소로 swagger 문서 페이지로 접근 가능
```text
http://localhost:{PORT}/swagger-ui/index.html
```

## API
### 연도별 국가별 조회
- Request
    - url: GET /api/holidays/{year}/{countryCode}
        - year: 연도(yyyy)
        - countryCode: 국가 코드(ISO 3166-1 alpha-2)
    - query parameters
        - page: 페이지 번호(필수 아님, 1부터 시작, 기본값 1)
        - size: 페이지별 항목 수(필수 아님, 기본값 10)
        - from: 월 시작(값 포함. 필수 아님)
        - to: 월 종료(값 포함. 필수 아님)
        - types: 공휴일 유형(string list). 필수 아님. 존재 시 OR 조건으로 적용됨
            - Public: 법정 공휴일
            - Bank: 은행 휴업일
            - School: 학교 휴일
            - Authorities: 관공서 공휴일
            - Optional: 선택적 휴일
            - Observance: 기념일
    - body: (없음)
    - example
        ```
        GET /api/holidays/2025/KR?page=1&size=3&from=10&types=Public&types=Optional
        ```
- Response
    - 성공
        - http status code: 200 ok
        - body(object)
            - page: 검색된 페이지 번호(1부터 시작)
            - size: 페이지별 항목 수
            - totalPages: 전체 페이지 수
            - totalElements: 전체 항목 수
            - isFirst: 첫 페이지인지 여부
            - isLast: 마지막 페이지인지 여부
            - content: 조회된 데이터 목록(object list). date 오름차순 정렬됨
                - countryCode: 국가 코드(ISO 3166-1 alpha-2)
                - date: 날짜(yyyy-MM-dd)
                - localName: 공휴일 이름(해당 국가 언어 표기)
                - name: 공휴일 이름(영문 표기)
                - global: 국가 전체 휴일 여부(boolean)
                - launchYear: 최초 적용 연도(yyyy 또는 null)
                - counties: 적용되는 state code list(ISO 3166-2 list. 빈 배열일 경우 국가 전체에 적용됨)
                - types: 공휴일 유형(string list)
                    - Public: 법정 공휴일
                    - Bank: 은행 휴업일
                    - School: 학교 휴일
                    - Authorities: 관공서 공휴일
                    - Optional: 선택적 휴일
                    - Observance: 기념일
        - example
            ```json
            {
              "page": 1,
              "size": 3,
              "totalPages": 2,
              "totalElements": 6,
              "isFirst": true,
              "isLast": false,
              "content": [{
                "countryCode": "KR",
                "date": "2025-10-03",
                "localName": "개천절",
                "name": "National Foundation Day",
                "global": true,
                "launchYear": null,
                "counties": [],
                "types": ["Public"]
              }, {
                "countryCode": "KR",
                "date": "2025-10-06",
                "localName": "추석",
                "name": "Chuseok",
                "global": true,
                "launchYear": null,
                "counties": [],
                "types": ["Public"]
              }, {
                "countryCode": "KR",
                "date": "2025-10-07",
                "localName": "추석",
                "name": "Chuseok",
                "global": true,
                "launchYear": null,
                "counties": [],
                "types": ["Public"]
              }]
            }
            ```
    - 실패 http status code
        - 400 bad request: 전달된 정보가 유효하지 않음
        - 500 internal server error: 서버 오류

### 재동기화
- Request
    - url: POST /api/holidays/{year}/{countryCode}/refresh
        - year: 연도(yyyy)
        - countryCode: 국가 코드(ISO 3166-1 alpha-2)
    - query parameters: (없음)
    - body: (없음)
    - example
        ```
        POST /api/holidays/2025/KR/refresh
        ```
- Response
    - 성공
        - http status code: 200 ok
        - body: (없음)
    - 실패 http status code
        - 400 bad request: 전달된 정보가 유효하지 않음
        - 500 internal server error: 서버 오류

### 삭제
- Request
    - url: DELETE /api/holidays/{year}/{countryCode}
        - year: 연도(yyyy)
        - countryCode: 국가 코드(ISO 3166-1 alpha-2)
    - query parameters: (없음)
    - body: (없음)
    - example
        ```
        DELETE /api/holidays/2025/KR
        ```
- Response
    - 성공
        - http status code: 200 ok
        - body: (없음)
    - 실패 http status code
        - 400 bad request: 전달된 정보가 유효하지 않음
        - 404 not found: 해당하는 데이터가 없음
        - 500 internal server error: 서버 오류

## Batch
### 최근 5년의 공휴일 일괄 저장
- 애플리케이션 최초 실행 시 작동

### 매년 자동 동기화
- 매년 1월 2일 01:00 KST에 전년도, 금년도 데이터를 자동 동기화


## DB
### 설계 참고
- 국가 코드(ISO 3166-1 alpha-2) 및 국가 하위 지역 코드(ISO 3166-2)는 별도 코드 테이블을 사용하지 않음
    - 해당 코드들은 국제 표준 정보로서, 변경되거나 저장 시 유효성 오류가 발생할 가능성이 희박함
    - 서비스하는 데이터 저장 시 국가 코드 목록 조회 -> 특정 연도 특정 코드의 데이터 조회 -> 저장의 프로세스를 가지는데, 코드 목록 조회를 DB의 코드 테이블을 사용할 경우 신규 국가 코드가 추가됐는데 DB에는 반영되지 않았다면 데이터 조회 시 누락이 발생할 수 있음
    - 이를 막으려면 국가 코드를 조회하거나 재동기화를 할 때마다 코드를 최신화해야 하는데, 이미 nager.date API에서 최신화된 코드를 조회할 수 있기도 하고, 본 프로젝트 규모에서 코드 정보를 별도로 관리하는 것은 과도하다고 판단됨
    - 따라서 nager.date에서 제공하는 정보를 그대로 사용하되, 차후 국가의 부가 정보들을 직접 다루는 등 코드 이외의 정보를 관리해야 할 필요가 생기면 개선하는 것이 타당함
- 공휴일 유형은 별도 코드 테이블을 사용하지 않음
    - 공휴일 유형은 nager.date API의 분류로서 변경 가능성이 상대적으로 높음. 다만 애플리케이션에서 해당 API 데이터를 조회한 후 enum으로 변환하는 과정에서 변경 여부를 확인할 수 있으므로 별도 코드 테이블은 불필요함
- id는 DBMS마다 특화된 유형이 다른데 본 프로젝트는 h2를 사용하므로 공통 적용할 수 있는 varchar를 사용(UUID 값을 string으로 사용)
- 도메인 레벨에서 공휴일 유형, 국가 하위 지역 코드는 아이디를 사용하지 않음
  - 도메인에서는 단지 값만 필요한데 DB와의 연계를 위해 아이디를 부여해서 jpa entity와 맞추는 것은 과도함
  - 아이디가 존재하지 않으므로 DB에서 조회한 데이터에서 일부 공휴일 유형, 국가 하위 지역 코드에 변경 사항이 발생할 경우 전체 목록을 삭제하고 다시 만들어야 함. 다만 현재 특정 공휴일에 대한 공휴일 유형 추가, 국가 하위 지역 코드 추가 등 세부 조정 기능은 존재하지 않으므로 성능상 불리점은 없음
  - 차후 세부 조정이 필요할 경우 도메인 레벨에도 아이디를 부여하는 등 DB와의 연계 정보 추가 필요
  - 단, 특정 공휴일 인스턴스는 삭제할 수 있어야 하므로 holiday 도메인 모델에는 아이디를 사용

### ERD
```mermaid
erDiagram
    holiday {
        varchar id PK
        varchar country_code "KR, ...(ISO 3166-1 alpha-2)"
        date date "2025-10-07"
        varchar local_name "추석"
        varchar name "Chuseok"
        boolean global "true"
        int launch_year "null"
    }
    
    holiday_county {
        varchar id PK
        varchar holiday_id FK
        varchar code "KR-11, ...(ISO 3166-2)"
    }

    holiday_type {
        varchar id PK
        varchar holiday_id FK
        varchar code "Public, Bank, School, Authorities, Optional, Observance"
    }

    holiday ||..o{ holiday_county: ""
    holiday ||..|{ holiday_type: ""
```