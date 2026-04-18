package com.example.librarymanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BorrowResponseDTO {

    private Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    // Nested summaries to show clear details without exposing full entities
    private BookSummaryDTO book;
    private MemberSummaryDTO member;

    @Data
    public static class BookSummaryDTO {
        private Long id;
        private String title;
        private String isbn;
    }

    @Data
    public static class MemberSummaryDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
    }
}