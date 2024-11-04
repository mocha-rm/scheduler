package com.jhpark.scheduler.controller;

import com.jhpark.scheduler.dto.ScheduleRequestDto;
import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import com.jhpark.scheduler.service.ScheduleService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    * 전체 일정 조회 (parameter 로 작성자를 넣는경우 해당 작성자가 작성한 전체 목록 조회)
    * */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String author) {

        if (author != null && !author.isEmpty()) {
            return new ResponseEntity<>(scheduleService.findSchedulesByAuthor(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
        }
    }

    /*
     * 최종 수정일로 전체 일정 조회
     * */
    @GetMapping("/{mod_date}")
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByDate(@PathVariable LocalDate mod_date) {
        return new ResponseEntity<>(scheduleService.findSchedulesByDate(mod_date), HttpStatus.OK);
    }
}
