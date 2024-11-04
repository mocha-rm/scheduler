package com.jhpark.scheduler.dto;


import com.jhpark.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String author;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.author = schedule.getAuthor();
        this.password = schedule.getPassword();
        this.createdDate = schedule.getCreatedDate();
        this.modDate = schedule.getModDate();
    }
}
