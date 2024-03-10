package org.example.hw5.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "BOOKS")
public class Book extends BaseEntity implements Serializable {
    private String bookName;
    private String authorName;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Category category;

    public Book() {
        this.category = new Category();
    }

    public static Book merge(Book oldBook, Book updateBook) {
        oldBook.setBookName(updateBook.getBookName());
        oldBook.setAuthorName(updateBook.getAuthorName());
        oldBook.getCategory().setCategoryName(updateBook.getCategory().getCategoryName());
        return oldBook;
    }
}
