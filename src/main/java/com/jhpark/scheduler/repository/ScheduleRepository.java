package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(Pageable pageable);

    List<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate, Pageable pageable);

    List<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate, Pageable pageable);

    List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId, Pageable pageable);

    Schedule findScheduleById(Long id);

    int patchSchedule(Long id, String title);

    int deleteScheduleById(Long id);

    int countByAuthorAndDate(Long authorId, LocalDate modDate);

    int countByAuthor(Long authorId);

    int countByDate(LocalDate modDate);

    int countAll();
}
