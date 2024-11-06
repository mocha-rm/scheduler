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
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto, Long authorId) {
        Schedule schedule = new Schedule(authorId, dto.getTitle(), dto.getPassword());
        return repository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return repository.findAllSchedules();
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate) {
        return repository.findSchedulesByAuthorAndDate(authorId, modDate);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate) {
        return repository.findSchedulesByDate(modDate);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId) {
        return repository.findSchedulesByAuthor(authorId);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = repository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto patchScheduleById(Long id, String password, String title, String author) {
        if (password.equals(repository.findScheduleById(id).getPassword())) { //비밀번호 일치 확인
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
        if (password.equals(repository.findScheduleById(id).getPassword())) { //비밀번호 일치 확인
            int result = repository.deleteScheduleById(id);
            if (result == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return "Delete Success";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match.");
        }
    }
}
