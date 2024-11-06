package com.jhpark.scheduler.dto;

import com.jhpark.scheduler.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modDate;

    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
        this.createdDate = author.getCreatedDate();
        this.modDate = getModDate();
    }
}
