package com.example.librarymanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AuthorResponseDTO {
    // This is the data we send back to the user.
    // It INCLUDES the ID so the user knows the database identifier.
    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
}