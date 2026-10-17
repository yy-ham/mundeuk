package com.app.mundeuk.domain.review.dto;

import com.app.mundeuk.domain.book.dto.BookRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSaveRequestDto {

    private ReviewRequestDto reviewRequestDto;
    private BookRequestDto bookRequestDto;

}
