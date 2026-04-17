package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.MemberRequestDTO;
import com.example.librarymanagementsystem.dto.MemberResponseDTO;
import com.example.librarymanagementsystem.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Page<MemberResponseDTO>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MemberResponseDTO>> searchByName(
            @RequestParam String name, Pageable pageable) {
        return ResponseEntity.ok(memberService.searchByName(name, pageable));
    }

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(
            @Valid @RequestBody MemberRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.createMember(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(
            @PathVariable Long id, @Valid @RequestBody MemberRequestDTO dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}