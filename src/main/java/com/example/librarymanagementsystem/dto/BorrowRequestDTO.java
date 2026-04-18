package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class BorrowRequestDTO {
    // Only IDs are needed from the user to create a borrow record
    private Long bookId;
    private Long memberId;
}