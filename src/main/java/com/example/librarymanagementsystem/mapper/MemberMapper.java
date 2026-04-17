package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.MemberRequestDTO;
import com.example.librarymanagementsystem.dto.MemberResponseDTO;
import com.example.librarymanagementsystem.entity.Member;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberResponseDTO toResponseDTO(Member member);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "membershipDate", ignore = true)
    Member toEntity(MemberRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "membershipDate", ignore = true)
    void updateEntityFromDTO(MemberRequestDTO dto, @MappingTarget Member member);
}