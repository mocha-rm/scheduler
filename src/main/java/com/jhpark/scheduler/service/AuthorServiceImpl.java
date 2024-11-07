package com.jhpark.scheduler.service;

import com.jhpark.scheduler.dto.AuthorRequestDto;
import com.jhpark.scheduler.dto.AuthorResponseDto;
import com.jhpark.scheduler.entity.Author;
import com.jhpark.scheduler.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }


    @Override
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
        Author author = new Author(dto.getName(), dto.getEmail());
        return repository.saveAuthor(author);
    }

    @Override
    public List<AuthorResponseDto> findAllAuthors() {
        return repository.findAllAuthors();
    }
}
