package com.jhpark.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id; // auto_increment
    private Long authorId; // 작성자 아이디 (USERS 테이블의 FK)
    private String title; // 할일 제목
    private String password; // 비밀번호
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modDate; // 수정일

    public Schedule(Long authorId, String title, String password) {
        this.authorId = authorId;
        this.title = title;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.modDate = LocalDateTime.now();
    }
}
