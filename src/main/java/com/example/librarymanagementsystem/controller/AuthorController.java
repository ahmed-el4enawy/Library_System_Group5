package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.AuthorRequestDTO;
import com.example.librarymanagementsystem.dto.AuthorResponseDTO;
import com.example.librarymanagementsystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST API controller
@RequestMapping("/api/authors") // Base URL for all endpoints in this class
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // POST /api/authors -> Creates a new author
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody AuthorRequestDTO requestDTO) {
        return new ResponseEntity<>(authorService.createAuthor(requestDTO), HttpStatus.CREATED); // Returns 201 Created
    }

    // GET /api/authors -> Returns a list of all authors
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors()); // Returns 200 OK
    }

    // GET /api/authors/{id} -> Returns a specific author by ID
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    // PUT /api/authors/{id} -> Updates an author completely
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, requestDTO));
    }

    // DELETE /api/authors/{id} -> Deletes an author
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content upon successful deletion
    }
}