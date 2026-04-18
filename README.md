# Library Management System

## Project Description
This is a RESTful API for a Library Management System built with Spring Boot. The system allows librarians to manage books, authors, and members, and to track book-borrowing records. All data is persisted in an in-memory H2 relational database, utilizing a clean layered architecture. Mapping between entities and DTOs is handled via MapStruct.

## Prerequisites
- **Java Version:** Java 17
- **Maven:** Supported via the included Maven Wrapper (`mvnw`)

## How to Build and Run the Application
Open a terminal in the root of the repository and execute the following command:

**On Linux / macOS:**
```bash
./mvnw spring-boot:run
```

**On Windows:**
```cmd
mvnw.cmd spring-boot:run
```

## How to Access the H2 Console
Once the application is running, you can inspect the H2 embedded database:
1. Open a web browser and navigate to: `http://localhost:8080/h2-console`
2. Connect using the following settings defined in the project:
   - **JDBC URL:** `jdbc:h2:mem:librarydb`
   - **User Name:** `sa`
   - **Password:** *(leave blank)*

## N+1 Analysis
By default, the mapping relationship from a `Book` to an `Author` utilizes `@ManyToOne(fetch = FetchType.LAZY)` to avoid fetching unneeded data. However, the `GET /api/books` (getAllBooks) endpoint would natively suffer from the N+1 select problem by issuing a primary query to load all books, followed by additional individual select queries for each author. We effectively resolved this in the `BookRepository` by using the `JOIN FETCH` directive in our custom JPQL queries (e.g., `SELECT b FROM Book b JOIN FETCH b.author`). This command forces Hibernate to load both the Book and its Author concurrently in a single, optimized SQL query, fully resolving the N+1 issue.