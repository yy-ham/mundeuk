package com.app.mundeuk.domain.member.controller;

import com.app.mundeuk.domain.member.dto.MemberRequestDto;
import com.app.mundeuk.domain.member.dto.MemberResponseDto;
import com.app.mundeuk.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    final MemberService memberService;

    // 회원 전체 조회
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        List<MemberResponseDto> responseDtos = memberService.findMembers();
        return ResponseEntity.ok(responseDtos);
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


}
