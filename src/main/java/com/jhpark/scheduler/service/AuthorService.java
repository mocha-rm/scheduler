package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.AuthorRequestDto;
import com.jhpark.scheduler.dto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto);
    public List<AuthorResponseDto> findAllAuthors();
}
