package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;

import java.time.LocalDate;
import java.util.List;


public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    List<ScheduleResponseDto> findSchedulesByAuthorAndDate(String author, LocalDate mod_date);

    List<ScheduleResponseDto> findSchedulesByDate(LocalDate mod_date);

    List<ScheduleResponseDto> findSchedulesByAuthor(String author);

    Schedule findScheduleById(Long id);

    int patchSchedule(Long id, String title, String author);

    int deleteScheduleById(Long id);
}
