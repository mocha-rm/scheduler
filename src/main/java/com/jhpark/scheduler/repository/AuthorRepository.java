package com.jhpark.scheduler.repository;

import com.jhpark.scheduler.dto.AuthorResponseDto;
import com.jhpark.scheduler.entity.Author;

import java.util.List;


public interface AuthorRepository {
    AuthorResponseDto saveAuthor(Author author);
    List<AuthorResponseDto> findAllAuthors();
}
