package org.example.hw5.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.example.hw5.repository.BookRepository;
import org.example.hw5.repository.model.Book;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.hw5.configuration.enums.Cache.Name.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "cacheManager")
public class BookService {

    private final BookRepository repository;

    @Cacheable(value = FIND_BOOK_BY_ID, key = "#id")
    public Book findById(Long id) {
        return repository.findById(id).get();
    }

    @Cacheable(FIND_ALL_BOOK)
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Cacheable(FIND_BOOK_BY_AUTHOR_NAME_AND_BOOK_NAME)
    public List<Book> findByBookNameAndAuthorName(String bookName, String authorName) {
        return repository.findByBookNameContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(bookName, authorName);
    }

    @Cacheable(FIND_BOOK_BY_CATEGORY_NAME)
    public List<Book> findByCategoryName(String categoryName) {
        return repository.findByCategory_CategoryNameContainingIgnoreCase(categoryName);
    }

    @Caching(evict = {
            @CacheEvict(value = FIND_BOOK_BY_ID, key = "#id", beforeInvocation = true),
            @CacheEvict(value = FIND_ALL_BOOK, allEntries = true, beforeInvocation = true),
            @CacheEvict(value = FIND_BOOK_BY_AUTHOR_NAME_AND_BOOK_NAME, allEntries = true, beforeInvocation = true),
            @CacheEvict(value = FIND_BOOK_BY_CATEGORY_NAME, allEntries = true, beforeInvocation = true)
    })
    public Book saveOrUpdate(@Nullable Long id, Book book) {
        if (Objects.isNull(id)) {
            return repository.save(book);
        } else {
            Optional<Book> oldBook = repository.findById(id);
            return oldBook
                    .map(value -> repository.save(Book.merge(value, book)))
                    .orElseGet(() -> repository.save(book));
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
