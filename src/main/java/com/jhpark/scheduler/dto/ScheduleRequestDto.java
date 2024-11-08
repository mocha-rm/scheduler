package com.jhpark.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @Size(max = 200)
    private String title;

    @NotBlank
    private String password;
    private Long authorId;
}
