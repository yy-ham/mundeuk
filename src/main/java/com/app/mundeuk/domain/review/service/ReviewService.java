package com.app.mundeuk.domain.review.service;

import com.app.mundeuk.domain.book.dto.BookResponseDto;
import com.app.mundeuk.domain.book.service.BookService;
import com.app.mundeuk.domain.review.dto.ReviewRequestDto;
import com.app.mundeuk.domain.review.dto.ReviewSaveRequestDto;
import com.app.mundeuk.domain.review.entity.ContentType;
import com.app.mundeuk.domain.review.entity.Review;
import com.app.mundeuk.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;

    public ReviewService(ReviewRepository reviewRepository,
                         BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
    }

    @Transactional
    public Long createReview(ReviewSaveRequestDto requestDto) {
        ContentType contentType = requestDto.getReviewRequestDto().getContentType();
        ReviewRequestDto reviewRequestDto = requestDto.getReviewRequestDto();

        Long savedReviewId = -1L;

        // 책 리뷰 저장
        if (contentType == ContentType.BOOK) {
            // 1. 책 저장
            BookResponseDto responseDto = bookService.createBook(requestDto.getBookRequestDto());

            // 2. 리뷰 저장
            Long bookId = responseDto.getId();
            reviewRequestDto.setContentId(bookId);

            Review review = reviewRequestDto.toEntity();
            reviewRepository.save(review);

            savedReviewId = review.getId();
        }

        return savedReviewId;
    }

}
