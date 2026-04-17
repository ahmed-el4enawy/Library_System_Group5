package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.MemberRequestDTO;
import com.example.librarymanagementsystem.dto.MemberResponseDTO;
import com.example.librarymanagementsystem.entity.Member;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.mapper.MemberMapper;
import com.example.librarymanagementsystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(memberMapper::toResponseDTO);
    }

    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return memberMapper.toResponseDTO(member);
    }

    public Page<MemberResponseDTO> searchByName(String name, Pageable pageable) {
        return memberRepository.searchByName(name, pageable)
                .map(memberMapper::toResponseDTO);
    }

    public MemberResponseDTO createMember(MemberRequestDTO dto) {
        try {
            Member member = memberMapper.toEntity(dto);
            return memberMapper.toResponseDTO(memberRepository.save(member));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email already exists: " + dto.getEmail());
        }
    }

    public MemberResponseDTO updateMember(Long id, MemberRequestDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        try {
            memberMapper.updateEntityFromDTO(dto, member);
            return memberMapper.toResponseDTO(memberRepository.save(member));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email already exists: " + dto.getEmail());
        }
    }

    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }
}