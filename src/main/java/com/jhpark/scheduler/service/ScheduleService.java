package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;


public interface ScheduleService {
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto, Long authorId);

    public Page<ScheduleResponseDto> findAllSchedules(Pageable pageable);

    public Page<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate, Pageable pageable);

    public Page<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate, Pageable pageable);

    public Page<ScheduleResponseDto> findSchedulesByAuthor(Long authorId, Pageable pageable);

    public ScheduleResponseDto findScheduleById(Long id);

    public ScheduleResponseDto patchScheduleById(Long id, String password, String title);

    public String deleteScheduleById(Long id, String password);
}
