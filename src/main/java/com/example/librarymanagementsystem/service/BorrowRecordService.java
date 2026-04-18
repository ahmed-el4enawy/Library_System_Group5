package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.BorrowRequestDTO;
import com.example.librarymanagementsystem.dto.BorrowResponseDTO;
import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.BorrowRecord;
import com.example.librarymanagementsystem.entity.Member;
import com.example.librarymanagementsystem.exception.DuplicateResourceException;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.mapper.BorrowRecordMapper;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.BorrowRecordRepository;
import com.example.librarymanagementsystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordMapper borrowRecordMapper;

    // Process a new borrowing request
    public BorrowResponseDTO borrowBook(BorrowRequestDTO dto) {
        // 1. Check if the book is already borrowed (Return HTTP 409 Conflict logic)
        if (borrowRecordRepository.existsByBookIdAndReturnDateIsNull(dto.getBookId())) {
            throw new DuplicateResourceException("This book is currently borrowed and has not been returned.");
        }

        // 2. Fetch Book and Member, throw 404 if not found
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getBookId()));

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + dto.getMemberId()));

        // 3. Create and save the record
        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        // borrowDate is set automatically by @PrePersist in the Entity

        BorrowRecord savedRecord = borrowRecordRepository.save(record);
        return borrowRecordMapper.toDto(savedRecord);
    }

    // Process a book return
    public BorrowResponseDTO returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findByIdWithDetails(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found with id: " + recordId));

        // Check if it's already returned to avoid overwriting the original return date
        if (record.getReturnDate() != null) {
            throw new DuplicateResourceException("This book has already been returned.");
        }

        // Update the return date and save
        record.setReturnDate(LocalDate.now());
        BorrowRecord updatedRecord = borrowRecordRepository.save(record);

        return borrowRecordMapper.toDto(updatedRecord);
    }

    // Get all borrow records with Pagination
    public Page<BorrowResponseDTO> getAllRecords(Pageable pageable) {
        return borrowRecordRepository.findAllWithDetails(pageable)
                .map(borrowRecordMapper::toDto);
    }
}