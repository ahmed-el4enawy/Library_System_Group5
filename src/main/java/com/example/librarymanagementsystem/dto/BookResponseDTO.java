package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class BookResponseDTO {

    private Long id;
    private String title;
    private String isbn;
    private String genre;
    private Integer publishedYear;

    // Nested author info — never expose the raw entity
    private AuthorSummaryDTO author;

    // Lightweight nested DTO for the author inside a book response
    @Data
    public static class AuthorSummaryDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String nationality;
    }
}