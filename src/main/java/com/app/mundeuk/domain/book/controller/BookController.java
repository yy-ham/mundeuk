package com.app.mundeuk.domain.book.controller;

import com.app.mundeuk.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    final BookService bookService;

    // 도서 검색
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String searchBooks(@RequestParam("keyword") String keyword,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return bookService.searchBooks(keyword, page, size);
    }
}
