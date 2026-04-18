package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.BorrowResponseDTO;
import com.example.librarymanagementsystem.entity.BorrowRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BorrowRecordMapper {
    // Converts the Entity into the Response DTO (including the nested Book and Member summaries)
    BorrowResponseDTO toDto(BorrowRecord entity);
}