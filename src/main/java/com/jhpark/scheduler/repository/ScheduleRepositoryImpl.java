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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public List<ScheduleResponseDto> findAllSchedules(Pageable pageable) {
        String sql = "SELECT * FROM SCHEDULES ORDER BY MOD_DATE DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, schedulesRowMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthorAndDate(Long authorId, LocalDate modDate, Pageable pageable) {
        String sql = "SELECT * FROM SCHEDULES WHERE USER_ID = ? AND DATE(MOD_DATE) = ? ORDER BY MOD_DATE DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,
                schedulesRowMapper(), authorId, modDate, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByDate(LocalDate modDate, Pageable pageable) {
        String sql = "SELECT * FROM SCHEDULES WHERE DATE(MOD_DATE) = ? ORDER BY MOD_DATE DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, schedulesRowMapper(), modDate, pageable.getPageSize(), pageable.getOffset());

    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByAuthor(Long authorId, Pageable pageable) {
        String sql = "SELECT * FROM SCHEDULES WHERE USER_ID = ? ORDER BY MOD_DATE DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, schedulesRowMapper(), authorId, pageable.getPageSize(), pageable.getOffset());

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
    public int countByAuthorAndDate(Long authorId, LocalDate modDate) {
        String sql = "SELECT COUNT(*) FROM SCHEDULES WHERE USER_ID = ? AND DATE(MOD_DATE) = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, authorId, modDate);
    }

    @Override
    public int countByAuthor(Long authorId) {
        String sql = "SELECT COUNT(USER_ID) FROM SCHEDULES WHERE USER_ID = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, authorId);
    }

    @Override
    public int countByDate(LocalDate modDate) {
        String sql = "SELECT COUNT(MOD_DATE) FROM SCHEDULES WHERE MOD_DATE = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, modDate);
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM SCHEDULES";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
