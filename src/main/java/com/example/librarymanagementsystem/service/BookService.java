package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.BookRequestDTO;
import com.example.librarymanagementsystem.dto.BookResponseDTO;
import com.example.librarymanagementsystem.entity.Author;
import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    // POST /api/books
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + dto.getAuthorId()));
        try {
            Book book = bookMapper.toEntity(dto);
            book.setAuthor(author);
            return bookMapper.toDto(bookRepository.save(book));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "A book with ISBN '" + dto.getIsbn() + "' already exists.");
        }
    }

    // GET /api/books  (paginated)
    // Uses JOIN FETCH query to avoid N+1 — see BookRepository.findAllWithAuthor(Pageable)
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAllWithAuthor(pageable)
                .map(bookMapper::toDto);
    }

    // GET /api/books/{id}
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    // PUT /api/books/{id}
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + id));

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + dto.getAuthorId()));
        try {
            bookMapper.updateEntityFromDto(dto, book);
            book.setAuthor(author);
            return bookMapper.toDto(bookRepository.save(book));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "A book with ISBN '" + dto.getIsbn() + "' already exists.");
        }
    }

    // DELETE /api/books/{id}
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // GET /api/books/search?title=...&genre=...&publishedYear=...
    public List<BookResponseDTO> searchBooks(String title, String genre, Integer publishedYear) {
        return bookRepository.searchBooks(title, genre, publishedYear)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    // Used internally by AuthorService to support GET /api/authors/{id}/books
    public List<BookResponseDTO> getBooksByAuthorId(Long authorId) {
        // Verify the author exists first so we return 404 instead of an empty list
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }
        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}