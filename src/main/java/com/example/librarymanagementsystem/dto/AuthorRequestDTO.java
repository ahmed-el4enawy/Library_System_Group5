package com.example.librarymanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;

@Data // Generates getters and setters
public class AuthorRequestDTO {
    // This is the data we expect the user to send when creating or updating an Author.
    // Notice it does NOT have an ID, because the database generates the ID.
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
}