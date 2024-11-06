package com.jhpark.scheduler.dto;


import com.jhpark.scheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private Long authorId;
    private String title;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.authorId = schedule.getAuthorId();
        this.title = schedule.getTitle();
        this.password = schedule.getPassword();
        this.createdDate = schedule.getCreatedDate();
        this.modDate = schedule.getModDate();
    }
}
