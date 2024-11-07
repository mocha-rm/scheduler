package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findSchedules(Optional<Long> authorId, Optional<LocalDate> modDate, Pageable pageable);

    Schedule findScheduleById(Long id);

    int patchSchedule(Long id, String title);

    int deleteScheduleById(Long id);

    int countSchedules(Optional<Long> authorId, Optional<LocalDate> modDate);
}
