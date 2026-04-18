package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.entity.BorrowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    // BUSINESS LOGIC CHECK: Checks if a specific book is currently borrowed and not returned yet
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    // N+1 FIX: Fetch the record along with its book and member in a single query
    @Query("SELECT br FROM BorrowRecord br JOIN FETCH br.book JOIN FETCH br.member WHERE br.id = :id")
    Optional<BorrowRecord> findByIdWithDetails(@Param("id") Long id);

    // N+1 FIX: Fetch all records with pagination, joining book and member
    @Query(value = "SELECT br FROM BorrowRecord br JOIN FETCH br.book JOIN FETCH br.member",
            countQuery = "SELECT count(br) FROM BorrowRecord br")
    Page<BorrowRecord> findAllWithDetails(Pageable pageable);

    // Get all borrow records for a specific member
    @Query("SELECT br FROM BorrowRecord br JOIN FETCH br.book JOIN FETCH br.member WHERE br.member.id = :memberId")
    List<BorrowRecord> findByMemberId(@Param("memberId") Long memberId);

    // Get all currently borrowed books
    @Query("SELECT br FROM BorrowRecord br JOIN FETCH br.book JOIN FETCH br.member WHERE br.returnDate IS NULL")
    List<BorrowRecord> findActiveBorrowRecords();
}