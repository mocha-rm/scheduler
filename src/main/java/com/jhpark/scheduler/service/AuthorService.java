package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.AuthorRequestDto;
import com.jhpark.scheduler.dto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto saveAuthor(AuthorRequestDto dto);
    List<AuthorResponseDto> findAllAuthors();
}
