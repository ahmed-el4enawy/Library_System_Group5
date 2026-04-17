package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.AuthorRequestDTO;
import com.example.librarymanagementsystem.dto.AuthorResponseDTO;
import com.example.librarymanagementsystem.entity.Author;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.mapper.AuthorMapper;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO requestDTO) {
        Author author = authorMapper.toEntity(requestDTO);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDto(savedAuthor);
    }

    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = getAuthorEntityById(id);
        return authorMapper.toDto(author);
    }

    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO requestDTO) {
        Author author = getAuthorEntityById(id);
        authorMapper.updateEntityFromDto(requestDTO, author);
        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.toDto(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = getAuthorEntityById(id);
        authorRepository.delete(author);
    }

    protected Author getAuthorEntityById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }
}