package com.jhpark.scheduler.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthorRequestDto {
    @NotBlank(message = "이름을 입력하세요")
    private String name;

    @Email
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
}
