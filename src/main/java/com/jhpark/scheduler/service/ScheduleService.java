package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;


import java.time.LocalDate;
import java.util.List;


public interface ScheduleService {
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto, Long authorId);

    public List<ScheduleResponseDto> findAllSchedules();

    public List<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate);

    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate);

    public List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId);

    public ScheduleResponseDto findScheduleById(Long id);

    public ScheduleResponseDto patchScheduleById(Long id, String password, String title);

    public String deleteScheduleById(Long id, String password);
}
