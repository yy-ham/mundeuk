package com.app.mundeuk.domain.review.dto;

import com.app.mundeuk.domain.review.entity.ContentType;
import com.app.mundeuk.domain.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewRequestDto {

    private Long memberId;
    private ContentType contentType;
    private Long contentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double rating;
    private String content;

    // Dto -> Entity 변환
    public Review toEntity() {
        return Review.builder()
                .memberId(this.memberId)
                .contentType(this.contentType)
                .contentId(this.contentId)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .rating(this.rating)
                .content(this.content)
                .build();
    }

}
