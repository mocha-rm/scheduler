package com.jhpark.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id; // auto_increment
    private String author;// 작성자
    private String title; // 할일 제목
    private String password;// 비밀번호
    private LocalDateTime createdDate;// 생성일
    private LocalDateTime modDate;
    //private int userId;

    public  Schedule(String title, String author, String password) {
        this.title = title;
        this.author = author;
        this.password = password;
        this.createdDate = LocalDateTime.now();
        this.modDate = LocalDateTime.now();
    }
}
