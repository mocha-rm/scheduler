package com.jhpark.scheduler.controller;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /*
     * 일정 생성
     * */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    /*
     * 전체 일정 조회
     * */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam Optional<String> author,
            @RequestParam Optional<LocalDate> mod_date
    ) {
        if (author.isPresent() && mod_date.isPresent()) {
            //작성자와 날짜 둘 다 조건으로 넣어서 전체조회
            return new ResponseEntity<>(scheduleService.findSchedulesByAuthorAndDate(author.get(), mod_date.get()), HttpStatus.OK);
        } else if (author.isPresent()) {
            // 작성자를 조건으로 넣어서 전체조회
            return new ResponseEntity<>(scheduleService.findSchedulesByAuthor(author.get()), HttpStatus.OK);
        } else if (mod_date.isPresent()) {
            // 날짜를 조건으로 넣어서 전체조회
            return new ResponseEntity<>(scheduleService.findSchedulesByDate(mod_date.get()), HttpStatus.OK);
        } else {
            // 필터링 없이 전체조회
            return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
        }

    }
}
