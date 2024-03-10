package org.example.hw5.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.hw5.criteria.BookSearchCriteria;
import org.example.hw5.repository.model.Book;
import org.example.hw5.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping
    public String mainPage(BookSearchCriteria criteria, Model model) {
        List<Book> result;
        if (StringUtils.isEmpty(criteria.getBookName()) &&
                StringUtils.isEmpty(criteria.getAuthorName()) &&
                StringUtils.isEmpty(criteria.getCategoryName())) {
            result = service.findAll();
        } else if (StringUtils.isNotEmpty(criteria.getBookName()) &&
                StringUtils.isNotEmpty(criteria.getAuthorName())) {
            result = service.findByBookNameAndAuthorName(criteria.getBookName(), criteria.getAuthorName());
        } else if (StringUtils.isNotEmpty(criteria.getCategoryName())) {
            result = service.findByCategoryName(criteria.getCategoryName());
        } else {
            result = Collections.emptyList();
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.sort(Comparator.comparing(Book::getId).reversed());
        }

        model.addAttribute("bookList", result);
        return "main-page";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("book", new Book());
        return "edit-book";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("book", service.findById(id));
        return "edit-book";
    }

    @PostMapping
    public String saveBook(@ModelAttribute Book book) {
        service.saveOrUpdate(null, book);
        return "redirect:/";
    }

    @PostMapping("/{id}")
    public String editBook(@ModelAttribute Book book,
                           @PathVariable Long id) {
        service.saveOrUpdate(id, book);
        return "redirect:/";
    }

    @PostMapping("/remove/{id}")
    public String removeContact(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
