package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import com.jhpark.scheduler.repository.ScheduleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


@Service
public class ScheduleServiceImpl implements ScheduleService {

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
    public List<ScheduleResponseDto> findSchedulesByAuthorAndDate(String author, LocalDate mod_date) {
        return repository.findSchedulesByAuthorAndDate(author, mod_date);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate mod_date) {
        return repository.findSchedulesByDate(mod_date);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthor(String author) {
        return repository.findSchedulesByAuthor(author);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = repository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto patchScheduleById(Long id, String password, String title, String author) {
        if (password.equals(repository.findScheduleById(id).getPassword())) {
            int updatedRow = repository.patchSchedule(id, title, author);
            if (updatedRow == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            Schedule schedule = repository.findScheduleById(id);
            return new ScheduleResponseDto(schedule);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match.");
        }
    }

    @Override
    public String deleteScheduleById(Long id, String password) {
        if (password.equals(repository.findScheduleById(id).getPassword())) {
            int result = repository.deleteScheduleById(id);
            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return "Delete Success";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
