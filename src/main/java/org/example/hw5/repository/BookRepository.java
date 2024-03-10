package org.example.hw5.repository;

import org.example.hw5.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookNameContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(String bookName, String authorName);

    List<Book> findByCategory_CategoryNameContainingIgnoreCase(String categoryName);

}
