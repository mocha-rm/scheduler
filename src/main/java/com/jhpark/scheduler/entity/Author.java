package com.jhpark.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modDate;

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdDate = LocalDateTime.now();
        this.modDate = LocalDateTime.now();
    }
}
