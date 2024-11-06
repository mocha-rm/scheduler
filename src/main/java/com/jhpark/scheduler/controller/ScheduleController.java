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
    @PostMapping("/{authorId}")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@PathVariable Long authorId, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto, authorId), HttpStatus.CREATED);
    }

    /*
     * 전체 일정 조회
     * */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam Optional<Long> authorId,
            @RequestParam Optional<LocalDate> modDate
    ) {
        if (authorId.isPresent() && modDate.isPresent()) {
            //작성자와 날짜 둘 다 조건으로 넣어서 전체조회
            return new ResponseEntity<>(scheduleService.findSchedulesByAuthorAndDate(authorId.get(), modDate.get()), HttpStatus.OK);
        } else if (authorId.isPresent()) {
            // 작성자를 조건으로 넣어서 전체조회
            return new ResponseEntity<>(scheduleService.findSchedulesByAuthor(authorId.get()), HttpStatus.OK);
        } else if (modDate.isPresent()) {
            // 날짜를 조건으로 넣어서 전체조회
            return new ResponseEntity<>(scheduleService.findSchedulesByDate(modDate.get()), HttpStatus.OK);
        } else {
            // 필터링 없이 전체조회
            return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
        }
    }

    /*
     * 선택 일정 조회
     * */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /*
     * 일정 수정
     * */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> patchScheduleById(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.patchScheduleById(id, dto.getPassword(), dto.getTitle()), HttpStatus.OK);
    }

    /*
     * 일정 삭제
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScheduleById(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.deleteScheduleById(id, dto.getPassword()), HttpStatus.OK);
    }
}
