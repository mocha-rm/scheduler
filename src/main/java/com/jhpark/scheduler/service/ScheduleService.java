package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    public List<ScheduleResponseDto> findAllSchedules();
    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate mod_date);
}
