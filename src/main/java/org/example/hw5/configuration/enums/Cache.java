package org.example.hw5.configuration.enums;

import lombok.Getter;

import java.time.Duration;

@Getter
public enum Cache {
    FIND_BOOK_BY_ID(Name.FIND_BOOK_BY_ID, Duration.ofMinutes(10)),
    FIND_ALL_BOOK(Name.FIND_ALL_BOOK, Duration.ofMinutes(10)),
    FIND_BOOK_BY_AUTHOR_NAME_AND_BOOK_NAME(Name.FIND_BOOK_BY_AUTHOR_NAME_AND_BOOK_NAME, Duration.ofMinutes(10)),
    FIND_BOOK_BY_CATEGORY_NAME(Name.FIND_BOOK_BY_CATEGORY_NAME, Duration.ofMinutes(10));
    private final String val;
    private final Duration expire;
    Cache(String val, Duration expire) {
        this.val = val;
        this.expire = expire;
    }

    public static class Name {
        public static final String FIND_BOOK_BY_ID = "findBookById";

        public static final String FIND_ALL_BOOK = "findBook";
        public static final String FIND_BOOK_BY_AUTHOR_NAME_AND_BOOK_NAME = "findByBookNameAndAuthorName";
        public static final String FIND_BOOK_BY_CATEGORY_NAME = "findByCategoryName";
    }
}
