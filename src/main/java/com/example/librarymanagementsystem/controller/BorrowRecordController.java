package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BorrowRequestDTO;
import com.example.librarymanagementsystem.dto.BorrowResponseDTO;
import com.example.librarymanagementsystem.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow-records")
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    // POST /api/borrow -> Borrow a book
    @PostMapping
    public ResponseEntity<BorrowResponseDTO> borrowBook(@RequestBody BorrowRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowRecordService.borrowBook(dto));
    }

    // PUT /api/borrow/{id}/return -> Return a book
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowResponseDTO> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowRecordService.returnBook(id));
    }

    // GET /api/borrow-records -> Get all borrow records (paginated)
    @GetMapping
    public ResponseEntity<Page<BorrowResponseDTO>> getAllRecords(Pageable pageable) {
        return ResponseEntity.ok(borrowRecordService.getAllRecords(pageable));
    }

    // GET /api/borrow-records/member/{memberId} -> Get all borrow records for a specific member
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowResponseDTO>> getRecordsByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowRecordService.getRecordsByMemberId(memberId));
    }

    // GET /api/borrow-records/active -> Get all currently borrowed books
    @GetMapping("/active")
    public ResponseEntity<List<BorrowResponseDTO>> getActiveBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getActiveBorrowRecords());
    }
}