package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.AuthorResponseDto;
import com.jhpark.scheduler.entity.Author;

import java.util.List;


public interface AuthorRepository {
    public AuthorResponseDto saveAuthor(Author author);
    public List<AuthorResponseDto> findAllAuthors();
}
