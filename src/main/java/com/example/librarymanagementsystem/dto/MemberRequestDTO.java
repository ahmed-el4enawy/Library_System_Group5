package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class MemberRequestDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}