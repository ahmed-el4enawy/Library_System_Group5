package com.example.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity // Marks this class as a JPA database entity
@Table(name = "authors") // Specifies the table name in the H2 database
@Data // Lombok: Generates getters, setters, toString, equals, and hashcode automatically
@NoArgsConstructor // Lombok: Generates an empty constructor required by JPA
@AllArgsConstructor // Lombok: Generates a constructor with all arguments
public class Author {

    @Id // Marks this field as the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;

    @Column(nullable = false) // This field cannot be null in the database
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // Optional fields do not need the @Column(nullable = false) annotation
    private String nationality;

    private LocalDate birthDate;
}