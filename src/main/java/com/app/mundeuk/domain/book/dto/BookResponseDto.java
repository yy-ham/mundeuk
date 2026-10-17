package com.app.mundeuk.domain.book.dto;

import com.app.mundeuk.domain.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private LocalDate publishDate;
    private String isbn10;
    private String isbn13;
    private String image;

    // Entity -> Dto 변환
    public static BookResponseDto fromEntity(Book book) {
        return new BookResponseDto(
          book.getId(),
          book.getTitle(),
          book.getAuthor(),
          book.getPublisher(),
          book.getDescription(),
          book.getPublishDate(),
          book.getIsbn10(),
          book.getIsbn13(),
          book.getImage()
        );
    }

}
