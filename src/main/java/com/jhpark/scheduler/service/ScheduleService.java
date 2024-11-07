package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.Optional;


public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto, Long authorId);

    Page<ScheduleResponseDto> findSchedules(Optional<Long> authorId, Optional<LocalDate> modDate, Pageable pageable);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto patchScheduleById(Long id, String password, String title);

    String deleteScheduleById(Long id, String password);
}
