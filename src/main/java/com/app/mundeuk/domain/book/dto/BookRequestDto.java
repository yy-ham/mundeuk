package com.app.mundeuk.domain.book.dto;

import com.app.mundeuk.domain.book.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookRequestDto {

    private String title;
    private String author;
    private String publisher;
    private String description;
    private LocalDate publishDate;
    private String isbn10;
    private String isbn13;
    private String image;

    // Dto -> Entity 변환
    public Book toEntity() {
        return Book.builder()
                .title(this.title)
                .author(this.author)
                .publisher(this.publisher)
                .description(this.description)
                .publishDate(this.publishDate)
                .isbn10(this.isbn10)
                .isbn13(this.isbn13)
                .image(this.image)
                .build();
    }

}
