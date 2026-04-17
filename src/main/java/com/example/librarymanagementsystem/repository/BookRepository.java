package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // ── N+1 FIX ──────────────────────────────────────────────────────────────
    // GET /api/books — fetching all books naively would fire one extra SELECT
    // per book to load its author (because author is LAZY). JOIN FETCH loads
    // every book together with its author in a single SQL query.
    @Query("SELECT b FROM Book b JOIN FETCH b.author")
    List<Book> findAllWithAuthor();

    // Same fix for the paginated variant
    @Query(value = "SELECT b FROM Book b JOIN FETCH b.author",
            countQuery = "SELECT COUNT(b) FROM Book b")
    Page<Book> findAllWithAuthor(Pageable pageable);

    // Single book lookup also eagerly fetches the author in one query
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.id = :id")
    Optional<Book> findByIdWithAuthor(@Param("id") Long id);

    // GET /api/authors/{id}/books — all books for one author
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.author.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Long authorId);

    // GET /api/books/search — any combination of title, genre, publishedYear
    // All three parameters are optional (null means "skip that filter")
    @Query("""
            SELECT b FROM Book b JOIN FETCH b.author
            WHERE (:title       IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))
              AND (:genre       IS NULL OR LOWER(b.genre) LIKE LOWER(CONCAT('%', :genre, '%')))
              AND (:publishedYear IS NULL OR b.publishedYear = :publishedYear)
            """)
    List<Book> searchBooks(@Param("title")        String title,
                           @Param("genre")        String genre,
                           @Param("publishedYear") Integer publishedYear);
}