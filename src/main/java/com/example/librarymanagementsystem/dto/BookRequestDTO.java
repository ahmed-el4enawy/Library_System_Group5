package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class BookRequestDTO {

    private String title;

    private String isbn;

    private String genre;

    private Integer publishedYear;

    private Long authorId;
}