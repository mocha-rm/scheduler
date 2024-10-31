# scheduler
일정관리 웹 어플리케이션 입니다.

---

## API
| 기능       | Method | URL               | request                                    | response             | 상태코드            |
| -------- | ------ | ----------------- | ------------------------------------------ | -------------------- | --------------- |
| 전체 일정 조회 | GET    | api/schedules     | pram : name(optional), datetime(optional)  | 복수 응답 | 200: 정상조회, 404: 조회실패 |
| 선택 일정 조회 | GET    | api/schedules/{id} | pram : schedule_id                                  | 단일 응답 | 200: 정상조회, 404: 조회실패 |
| 일정 생성    | POST   | api/schedules     | body : title, password, datetime                       | 등록 정보  | 200: 정상등록, 400: 생성실패 |
| 일정 수정    | PUT    | api/schedules/{id} | param:schedule_id<br>body : title, name, password | 수정 정보 | 200: 정상수정, 400: 수정실패 |
| 일정 삭제    | DELETE | api/schedules/{id} | param:schedule_id<br>body : password                                   | X       | 200: 정상삭제, 400: 삭제실패 |

---

<details>
<summary>전체 일정 조회</summary>
<div markdown="1">

### Request

최신 추가된 일정 순으로 정렬되어 보여집니다.

```
curl --location 'http://localhost:8080/schedules'
```

파라미터를 입력해서 필터링 할 수 있습니다.(작성자 이름, 날짜)

```
curl --location 'http://localhost:8080/schedules{name}'
```

```
curl --location 'http://localhost:8080/schedules{2024-10-31}'
```

### Response

Success HTTP Status : 200

error HTTP Status : 404

| **이름** | **타입** | **설명** | **필수여부** |
| --- | --- | --- | --- |
| schedule_id | INT | 일정 id | Y |
| title | VARCHAR | 일정 제목 | Y |
| name | VARCHAR | 작성자 이름 | Y |
| created_date | DATETIME | 일정 생성 일 | Y |
| mod_date | DATETIME | 일정 수정 일 | Y |
| user_id | INT | 유저 id | Y |

### Example Response

성공

``` json
{
    "Scheules" : [
        {
            "schedule_id": 1,
            "title": "테스트 스케줄",
            "name": "작성자",
            "created_date": "2024-10-30",
            "mod_date": "2024-10-30",
            "user_id": 1
        }
        {
            "schedule_id": 2,
            "title": "테스트 스케줄2",
            "name": "작성자",
            "created_date": "2024-10-30",
            "mod_date": "2024-11-01",
            "user_id": 2
        }
        {
            "schedule_id": 3,
            "title": "테스트 스케줄3",
            "name": "작성자",
            "created_date": "2024-10-30",
            "mod_date": "2024-10-30",
            "user_id": 3
        }
    ]
}

 ```

실패

``` json
{
    "Error": "데이터를 조회할 수 없습니다."
}

 ```

</div>
</details>

<details>
<summary>선택 일정 조회</summary>
<div markdown="1">

### Request

id로 일정을 조회합니다.

```
curl --location 'http://localhost:8080/schedules{id}'
```

### Response

단일 일정 목록

Success HTTP Status : 200

error HTTP Status : 404

| **이름** | **타입** | **설명** | **필수여부** |
| --- | --- | --- | --- |
| schedule_id | INT | 일정 id | Y |
| title | VARCHAR | 일정 제목 | Y |
| name | VARCHAR | 작성자 이름 | Y |
| created_date | DATETIME | 일정 생성 일 | Y |
| mod_date | DATETIME | 일정 수정 일 | Y |
| user_id | INT | 유저 id | Y |

### Example Response

성공

``` json
{
    "Scheules" : [
        {
            "schedule_id": 1,
            "title": "테스트 스케줄",
            "name": "작성자"
            "created_date": "2024-10-30",
            "mod_date": "2024-10-30",
            "user_id": 1
        }
    ]
}

 ```

실패

``` json
{
    "Error": "데이터를 조회할 수 없습니다."
}

 ```

</div>
</details>

<details>
<summary>일정 생성</summary>
<div markdown="1">

### Request

```
curl --location 'http://localhost:8080/schedules'
```

```json
{
  "title": "테스트 스케줄",
  "name": "작성자 이름",
  "password": "12345",
  "created_date": "2024-10-30"
}
```

### Response

Success HTTP Status : 200

error HTTP Status : 400

| **이름** | **타입** | **설명** | **필수여부** |
| --- | --- | --- | --- |
| schedule_id | INT | 일정 id | Y |
| password | VARCHAR | 비밀번호 | Y |
| title | VARCHAR | 일정 제목 | Y |
| name | VARCHAR | 작성자 이름 | Y |
| created_date | DATETIME | 일정 생성 일 | Y |
| mod_date | DATETIME | 일정 수정 일 | Y |
| user_id | INT | 유저 id | Y |

### Example Response

성공

``` json
{
    "Scheules" : [
        {
            "schedule_id": 1,
            "password": "12345",
            "title": "테스트 스케줄",
            "name": "작성자 이름"
            "created_date": "2024-10-30",
            "mod_date": "2024-10-30",
            "user_id": 1
        }
    ]
}

 ```

실패

``` json
{
    "Error": "데이터를 생성할 수 없습니다."
}

 ```

</div>
</details>

<details>
<summary>일정 수정</summary>
<div markdown="1">

### Request

```
curl --location 'http://localhost:8080/schedules{id}'
```

```json
{
  "title": "제목을 입력하세요",
  "password": "비밀번호를 입력하세요",
  "name": "이름을 입력하세요",
  "mod_date": "2024-10-30"
}
```

### Response

Success HTTP Status : 200

error HTTP Status : 400

| **이름** | **타입** | **설명** | **필수여부** |
| --- | --- | --- | --- |
| schedule_id | INT | 일정 id | Y |
| password | VARCHAR | 비밀번호 | Y |
| title | VARCHAR | 일정 제목 | Y |
| name | VARCHAR | 작성자 이름 | Y |
| created_date | DATETIME | 일정 생성 일 | Y |
| mod_date | DATETIME | 일정 수정 일 | Y |
| user_id | INT | 유저 id | Y |

### Example Response

성공

``` json
{
    "Scheules" : [
        {
            "schedule_id": 1,
            "title": "테스트 스케줄",
            "name": "작성자 이름"
            "created_date": "2024-10-30",
            "mod_date": "2024-10-31",
            "user_id": 1
        }
    ]
}

 ```

실패

``` json
{
    "Error": "데이터를 수정할 수 없습니다."
}
 ```

</div>
</details>

<details>
<summary>일정 삭제</summary>
<div markdown="1">

### Request

```
curl --location 'http://localhost:8080/schedules{id}'
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

``` json
{
    "result": "Success" or "Fail"
}
 ```

</div>
</details>


## ERD
![스크린샷 2024-10-31 오후 9 31 50](https://github.com/user-attachments/assets/90847c4b-84f6-4f99-a501-77333ae6e407)
