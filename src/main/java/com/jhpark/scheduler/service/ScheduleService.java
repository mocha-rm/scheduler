package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;


import java.time.LocalDate;
import java.util.List;


public interface ScheduleService {
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    public List<ScheduleResponseDto> findAllSchedules();

    public List<ScheduleResponseDto> findSchedulesByAuthorAndDate(String author, LocalDate mod_date);

    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate mod_date);

    public List<ScheduleResponseDto> findSchedulesByAuthor(String author);

    public ScheduleResponseDto findScheduleById(Long id);

    public ScheduleResponseDto patchScheduleById(Long id, String password, String title, String author);

    public String deleteScheduleById(Long id, String password);
}
