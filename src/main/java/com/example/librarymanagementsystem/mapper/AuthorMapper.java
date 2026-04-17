package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.AuthorRequestDTO;
import com.example.librarymanagementsystem.dto.AuthorResponseDTO;
import com.example.librarymanagementsystem.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

// componentModel = "spring" allows us to inject this mapper using @Autowired or Constructor Injection
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    // Converts incoming DTO to a database Entity
    Author toEntity(AuthorRequestDTO dto);

    // Converts database Entity to an outgoing DTO
    AuthorResponseDTO toDto(Author entity);

    // Updates an existing database Entity with new data from the Request DTO (used for PUT requests)
    void updateEntityFromDto(AuthorRequestDTO dto, @MappingTarget Author entity);
}