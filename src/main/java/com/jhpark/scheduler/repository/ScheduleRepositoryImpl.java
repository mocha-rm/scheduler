package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("SCHEDULES").usingGeneratedKeyColumns("SCHEDULE_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("USER_ID", schedule.getAuthorId());
        params.put("TITLE", schedule.getTitle());
        params.put("PASSWORD", schedule.getPassword());
        params.put("CREATED_DATE", schedule.getCreatedDate());
        params.put("MOD_DATE", schedule.getModDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new ScheduleResponseDto(key.longValue(), schedule.getAuthorId(), schedule.getTitle(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getModDate());
    }

    @Override
    public List<ScheduleResponseDto> findSchedules(Optional<Long> authorId, Optional<LocalDate> modDate, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT * FROM SCHEDULES");
        List<Object> params = new ArrayList<>();

        // 조건에 따라 SQL 쿼리 생성
        if (authorId.isPresent() && modDate.isPresent()) {
            sql.append(" WHERE USER_ID = ? AND DATE(MOD_DATE) = ?");
            params.add(authorId.get());
            params.add(modDate.get());
        } else if (authorId.isPresent()) {
            sql.append(" WHERE USER_ID = ?");
            params.add(authorId.get());
        } else if (modDate.isPresent()) {
            sql.append(" WHERE DATE(MOD_DATE) = ?");
            params.add(modDate.get());
        }
        sql.append(" ORDER BY MOD_DATE DESC LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        return jdbcTemplate.query(sql.toString(), schedulesRowMapper(), params.toArray());
    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM SCHEDULES WHERE SCHEDULE_ID = ?"
                , scheduleRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public int patchSchedule(Long id, String title) {
        return jdbcTemplate.update("UPDATE SCHEDULES SET TITLE = ?, MOD_DATE = ? WHERE SCHEDULE_ID = ?", title, LocalDateTime.now(), id);
    }

    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("DELETE FROM SCHEDULES WHERE SCHEDULE_ID = ?", id);
    }


    private RowMapper<ScheduleResponseDto> schedulesRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("SCHEDULE_ID"),
                        rs.getLong("USER_ID"),
                        rs.getString("TITLE"),
                        rs.getString("PASSWORD"),
                        rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                        rs.getTimestamp("MOD_DATE").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("SCHEDULE_ID"),
                        rs.getLong("USER_ID"),
                        rs.getString("TITLE"),
                        rs.getString("PASSWORD"),
                        rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                        rs.getTimestamp("MOD_DATE").toLocalDateTime()
                );
            }
        };
    }

    @Override
    public int countSchedules(Optional<Long> authorId, Optional<LocalDate> modDate) {
        String sql = "SELECT COUNT(*) FROM SCHEDULES";
        List<Object> params = new ArrayList<>();

        if (authorId.isPresent() && modDate.isPresent()) {
            sql += " WHERE USER_ID = ? AND DATE(MOD_DATE) = ?";
            params.add(authorId.get());
            params.add(modDate.get());
        } else if (authorId.isPresent()) {
            sql += " WHERE USER_ID = ?";
            params.add(authorId.get());
        } else if (modDate.isPresent()) {
            sql += " WHERE DATE(MOD_DATE) = ?";
            params.add(modDate.get());
        }

        return jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
    }
}