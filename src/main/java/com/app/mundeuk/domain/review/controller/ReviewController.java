package com.app.mundeuk.domain.review.controller;

import com.app.mundeuk.domain.member.dto.MemberResponseDto;
import com.app.mundeuk.domain.review.dto.ReviewSaveRequestDto;
import com.app.mundeuk.domain.review.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    final ReviewService reviewService;

    // 리뷰 저장
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewSaveRequestDto requestDto, HttpSession session) {
        MemberResponseDto member = (MemberResponseDto) session.getAttribute("loginUser");
        Long memberId = member.getId();

        if (memberId == null) {
            return ResponseEntity.badRequest().body("로그인이 필요한 서비스입니다.");
        }

        requestDto.getReviewRequestDto().setMemberId(memberId);
        Long savedReviewID = reviewService.createReview(requestDto);
        return ResponseEntity.ok(savedReviewID);
    }

}
