package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import com.jhpark.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getTitle(), dto.getAuthor(), dto.getPassword()); //여기서 now()로 넣어주면 되는거 아닌지 , Schedule 생성자에서 하고 있는게 맞는건지
        return repository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return repository.findAllSchedules();
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate mod_date) {
        return repository.findSchedulesByDate(mod_date);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthor(String author) {
        return repository.findSchedulesByAuthor(author);
    }
}
