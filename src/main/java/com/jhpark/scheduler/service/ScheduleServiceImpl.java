package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import com.jhpark.scheduler.repository.ScheduleRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.plaf.basic.BasicOptionPaneUI;
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
    public Page<ScheduleResponseDto> findAllSchedules(Pageable pageable) {
        List<ScheduleResponseDto> schedules = repository.findAllSchedules(pageable);
        int total = repository.countAll();

        return new PageImpl<>(schedules, pageable, total);
    }

    @Override
    public Page<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate, Pageable pageable) {
        List<ScheduleResponseDto> schedules = repository.findSchedulesByAuthorAndDate(authorId, modDate, pageable);
        int total = repository.countByAuthorAndDate(authorId, modDate);

        return new PageImpl<>(schedules, pageable, total);
    }

    @Override
    public Page<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate, Pageable pageable) {
        List<ScheduleResponseDto> schedules = repository.findSchedulesByDate(modDate, pageable);
        int total = repository.countByDate(modDate);

        return new PageImpl<>(schedules, pageable, total);
    }

    @Override
    public Page<ScheduleResponseDto> findSchedulesByAuthor(Long authorId, Pageable pageable) {
        List<ScheduleResponseDto> schedules = repository.findSchedulesByAuthor(authorId, pageable);
        int total = repository.countByAuthor(authorId);

        return new PageImpl<>(schedules, pageable, total);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = repository.findScheduleById(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto patchScheduleById(Long id, String password, String title) {
        if (password.equals(repository.findScheduleById(id).getPassword())) { //비밀번호 일치 확인
            int updatedRow = repository.patchSchedule(id, title);
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
