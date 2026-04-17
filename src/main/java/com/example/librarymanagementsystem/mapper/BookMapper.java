package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.BookRequestDTO;
import com.example.librarymanagementsystem.dto.BookResponseDTO;
import com.example.librarymanagementsystem.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // Map Book entity -> BookResponseDTO
    // author.id   -> author.id
    // author.firstName -> author.firstName  (MapStruct resolves nested fields automatically)
    @Mapping(source = "author.id",          target = "author.id")
    @Mapping(source = "author.firstName",   target = "author.firstName")
    @Mapping(source = "author.lastName",    target = "author.lastName")
    @Mapping(source = "author.nationality", target = "author.nationality")
    BookResponseDTO toDto(Book book);

    // Map BookRequestDTO -> Book entity
    // We ignore the author field here; the service sets it manually after
    // looking up the Author entity from the database.
    @Mapping(target = "id",     ignore = true)
    @Mapping(target = "author", ignore = true)
    Book toEntity(BookRequestDTO dto);

    // Used for PUT /api/books/{id} — updates all scalar fields in place
    @Mapping(target = "id",     ignore = true)
    @Mapping(target = "author", ignore = true)
    void updateEntityFromDto(BookRequestDTO dto, @MappingTarget Book book);
}