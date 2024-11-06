package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;

import java.time.LocalDate;
import java.util.List;


public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    List<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate);

    List<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate);

    List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId);

    Schedule findScheduleById(Long id);

    int patchSchedule(Long id, String title, String author);

    int deleteScheduleById(Long id);
}
