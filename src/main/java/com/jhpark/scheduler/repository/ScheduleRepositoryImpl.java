package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.ScheduleResponseDto;
import com.jhpark.scheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("SCHEDULES").usingGeneratedKeyColumns("SCHEDULE_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("TITLE", schedule.getTitle());
        params.put("AUTHOR", schedule.getAuthor());
        params.put("PASSWORD", schedule.getPassword());
        params.put("CREATED_DATE", schedule.getCreatedDate());
        params.put("MOD_DATE", schedule.getModDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new ScheduleResponseDto(key.longValue(), schedule.getTitle(), schedule.getAuthor(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getModDate());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("SELECT * FROM SCHEDULES", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate mod_date) {
        return jdbcTemplate.query("SELECT * FROM SCHEDULES WHERE DATE(MOD_DATE) = ?"
                , scheduleRowMapper(), mod_date);
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthor(String author) {
        return jdbcTemplate.query("SELECT * FROM SCHEDULES WHERE AUTHOR = ?"
                , scheduleRowMapper(), author);
    }


    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("SCHEDULE_ID"),
                        rs.getString("AUTHOR"),
                        rs.getString("TITLE"),
                        rs.getString("PASSWORD"),
                        rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                        rs.getTimestamp("MOD_DATE").toLocalDateTime()
                );
            }
        };
    }
}
