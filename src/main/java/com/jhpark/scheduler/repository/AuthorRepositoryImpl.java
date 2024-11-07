package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.AuthorResponseDto;
import com.jhpark.scheduler.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final JdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AuthorResponseDto saveAuthor(Author author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USERS").usingGeneratedKeyColumns("USER_ID");

        Map<String, Object> params = new HashMap<>();
        params.put("NAME", author.getName());
        params.put("EMAIL", author.getEmail());
        params.put("CREATED_DATE", author.getCreatedDate());
        params.put("MOD_DATE", author.getModDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new AuthorResponseDto(key.longValue(), author.getName(), author.getEmail(), author.getCreatedDate(), author.getModDate());
    }

    @Override
    public List<AuthorResponseDto> findAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM USERS", authorsRowMapper());
    }


    private RowMapper<AuthorResponseDto> authorsRowMapper() {
        return new RowMapper<AuthorResponseDto>() {
            @Override
            public AuthorResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new AuthorResponseDto(
                        rs.getLong("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getTimestamp("CREATED_DATE").toLocalDateTime(),
                        rs.getTimestamp("MOD_DATE").toLocalDateTime()
                );
            }
        };
    }
}
