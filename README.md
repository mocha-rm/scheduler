# scheduler
일정관리 어플리케이션 입니다.

---

## API
| 기능       | Method | URL             | request                                               | response             | 상태코드                 |
| -------- | ------ |-----------------|-------------------------------------------------------| -------------------- |----------------------|
| 전체 일정 조회 | GET    | /schedules      | pram : name(optional), datetime(optional)             | 복수 응답 | 200: 정상조회, 404: 조회실패 |
| 선택 일정 조회 | GET    | /schedules/{id} | pathvariable : id                                     | 단일 응답 | 200: 정상조회, 404: 조회실패 |
| 일정 생성    | POST   | /schedules/{id} | pathvariable : id<br>body : title, password, datetime | 등록 정보  | 201: 정상등록, 400: 생성실패 |
| 일정 수정    | PUT    | /schedules/{id} | pathvariable : id<br>body : title, password           | 수정 정보 | 200: 정상수정, 400: 수정실패 |
| 일정 삭제    | DELETE | /schedules/{id} | pathvariable : id<br>body : password                  | 삭제 정보       | 200: 정상삭제, 400: 삭제실패 |

---

<details>
<summary>전체 일정 조회</summary>
<div markdown="1">


| **이름**      | **타입**               | **설명** | **필수여부** |
|-------------|----------------------| --- | --- |
| id          | INT (AUTO_INCREMENT) | 일정 id | Y |
| title       | VARCHAR(200)         | 일정 제목 | Y |
| name        | VARCHAR(10)          | 작성자 이름 | Y |
| createdDate | DATETIME             | 일정 생성 일 | Y |
| modDate     | DATETIME             | 일정 수정 일 | Y |
| authorId    | INT (FK)             | 유저 id | Y |

### Request

최신 추가된 일정 순으로 정렬되어 보여집니다.

```
curl --location 'http://localhost:8080/schedules?page=0'
```

파라미터를 입력해서 필터링 할 수 있습니다.(작성자 이름, 날짜)

```
curl --location 'http://localhost:8080/schedules?authorId=1&modDate=2024-11-07&page=1'
```

```
curl --location 'http://localhost:8080/schedules?authorId=1&page=1'
```

```
curl --location 'http://localhost:8080/schedules?modDate=2024-11-08&page=0'
```

### Response

Success HTTP Status : 200

error HTTP Status : 404


### Example Response

성공

``` json
{
    "content": [
        {
            "id": 84,
            "authorId": 3,
            "title": "",
            "password": "1234",
            "createdDate": "2024-11-08T11:40:56",
            "modDate": "2024-11-08T11:40:56"
        },
        {
            "id": 83,
            "authorId": 3,
            "title": "200자 이내",
            "password": "12111",
            "createdDate": "2024-11-08T11:35:13",
            "modDate": "2024-11-08T11:35:13"
        },
        {
            "id": 81,
            "authorId": 3,
            "title": "검증체크",
            "password": "0000",
            "createdDate": "2024-11-08T11:33:44",
            "modDate": "2024-11-08T11:33:44"
        },
        {
            "id": 76,
            "authorId": 7,
            "title": "Data29",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:50",
            "modDate": "2024-11-07T16:29:50"
        },
        {
            "id": 75,
            "authorId": 7,
            "title": "Data28",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:48",
            "modDate": "2024-11-07T16:29:48"
        },
        {
            "id": 74,
            "authorId": 7,
            "title": "Data27",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:46",
            "modDate": "2024-11-07T16:29:46"
        },
        {
            "id": 73,
            "authorId": 7,
            "title": "Data26",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:45",
            "modDate": "2024-11-07T16:29:45"
        },
        {
            "id": 72,
            "authorId": 7,
            "title": "Data24",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:43",
            "modDate": "2024-11-07T16:29:43"
        },
        {
            "id": 71,
            "authorId": 7,
            "title": "Data25",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:42",
            "modDate": "2024-11-07T16:29:42"
        },
        {
            "id": 70,
            "authorId": 7,
            "title": "Data23",
            "password": "0000",
            "createdDate": "2024-11-07T16:29:40",
            "modDate": "2024-11-07T16:29:40"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalPages": 8,
    "totalElements": 71,
    "first": true,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 10,
    "empty": false
}

```

데이터가 없는 경우 빈 배열을 리턴합니다.

``` json
{
    []
}

 ```

</div>
</details>

<details>
<summary>선택 일정 조회</summary>
<div markdown="1">

| **이름**      | **타입**               | **설명** | **필수여부** |
|-------------|----------------------| --- | --- |
| id          | INT (AUTO_INCREMENT) | 일정 id | Y |
| title       | VARCHAR(200)         | 일정 제목 | Y |
| name        | VARCHAR(10)          | 작성자 이름 | Y |
| createdDate | DATETIME             | 일정 생성 일 | Y |
| modDate     | DATETIME             | 일정 수정 일 | Y |
| authorId    | INT                  | 유저 id | Y |

### Request

id로 일정을 조회합니다.

```
curl --location 'http://localhost:8080/schedules/82'
```

### Response

단일 일정 목록

Success HTTP Status : 200

error HTTP Status : 404


### Example Response

성공

``` json
{
    "id": 83,
    "authorId": 3,
    "title": "200자 이내",
    "password": "12111",
    "createdDate": "2024-11-08T11:35:13",
    "modDate": "2024-11-08T11:35:13"
}

 ```

실패

``` json
{
    "httpStatus": "NOT_FOUND",
    "message": "삭제된 일정입니다"
}
 ```

</div>
</details>

<details>
<summary>일정 생성</summary>
<div markdown="1">

| **이름**      | **타입**               | **설명** | **필수여부** |
|-------------|----------------------| --- | --- |
| id          | INT (AUTO_INCREMENT) | 일정 id | Y |
| password    | VARCHAR(20)          | 비밀번호 | Y |
| title       | VARCHAR(200)         | 일정 제목 | Y |
| name        | VARCHAR(10)          | 작성자 이름 | Y |
| createdDate | DATETIME             | 일정 생성 일 | Y |
| modDate     | DATETIME             | 일정 수정 일 | Y |
| authorId    | INT                  | 유저 id | Y |

### Request

```
curl --location 'http://localhost:8080/schedules/{authorId}'
```
ex)
```
curl --location 'http://localhost:8080/schedules/3'
```

```json
{
  "title": "ddf",
  "password": "1234"
}
```

### Response

Success HTTP Status : 200

error HTTP Status : 400


### Example Response

성공

``` json
{
    "id": 85,
    "authorId": 3,
    "title": "ddf",
    "password": "1234",
    "createdDate": "2024-11-08T12:06:17.097429",
    "modDate": "2024-11-08T12:06:17.097449"
}
 ```

실패 (등록된 유저가 없을 시)

``` json
{
    "timestamp": "2024-11-08T03:07:30.468+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "message": "PreparedStatementCallback; Cannot add or update a child row: a foreign key constraint fails (`scheduler`.`schedules`, CONSTRAINT `fk_user_id` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`))",
    "path": "/schedules/22"
}
 ```

실패 (필수 값을 입력핮 않을 시)

``` json
{
    "timestamp": "2024-11-08T03:10:51.941+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Validation failed for object='scheduleRequestDto'. Error count: 1",
    "path": "/schedules/4"
}
 ```

</div>
</details>

<details>
<summary>일정 수정</summary>
<div markdown="1">

| **이름**      | **타입**               | **설명** | **필수여부** |
|-------------|----------------------| --- | --- |
| id          | INT (AUTO_INCREMENT) | 일정 id | Y |
| password    | VARCHAR(20)          | 비밀번호 | Y |
| title       | VARCHAR(200)         | 일정 제목 | Y |
| name        | VARCHAR(10)          | 작성자 이름 | Y |
| createdDate | DATETIME             | 일정 생성 일 | Y |
| modDate     | DATETIME             | 일정 수정 일 | Y |
| authorId    | INT                  | 유저 id | Y |

### Request

```
curl --location 'http://localhost:8080/schedules/{id}'
```

ex)
```
curl --location 'http://localhost:8080/schedules/82'
```

```json
{
  "password": "1234",
  "title": "최종테스트"
}
```

### Response

Success HTTP Status : 200

error HTTP Status : 400


### Example Response

성공

``` json
{
    "id": 85,
    "authorId": 3,
    "title": "최종테스트",
    "password": "1234",
    "createdDate": "2024-11-08T12:06:17",
    "modDate": "2024-11-08T12:13:52"
}
 ```

실패

``` json
{
    "httpStatus": "BAD_REQUEST",
    "message": "비밀번호가 일치하지 않습니다"
}
 ```

</div>
</details>

<details>
<summary>일정 삭제</summary>
<div markdown="1">

### Request

```
curl --location 'http://localhost:8080/schedules?id={id}'
```

ex)
```
curl --location 'http://localhost:8080/schedules/85'
```

``` json
{
    "password": "12345"
}
 ```

### Response

Success HTTP Status : 200

error HTTP Status : 400

### Example Response

성공

```
Delete Success
 ```

실패

``` json
{
    "httpStatus": "BAD_REQUEST",
    "message": "비밀번호가 일치하지 않습니다"
}
 ```

</div>
</details>


## ERD
![스크린샷 2024-10-31 오후 9 31 50](https://github.com/user-attachments/assets/90847c4b-84f6-4f99-a501-77333ae6e407)

---

## 트러블 슈팅
https://buly.kr/7bFjQf4
