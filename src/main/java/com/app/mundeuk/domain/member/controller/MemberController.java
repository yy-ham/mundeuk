package com.app.mundeuk.domain.member.controller;

import com.app.mundeuk.domain.member.dto.MemberRequestDto;
import com.app.mundeuk.domain.member.dto.MemberResponseDto;
import com.app.mundeuk.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody MemberRequestDto requestDto, HttpSession session) {
        MemberResponseDto responseDto = memberService.signIn(requestDto);

        if (responseDto != null) {
            // 세션에 로그인 유저 정보 저장
            session.setAttribute("loginUser", responseDto);
            return ResponseEntity.ok("로그인에 성공했습니다.");
        }

        return ResponseEntity.status(401).body("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
    
    // 로그아웃
    @PostMapping("/signout")
    public ResponseEntity<String> signOut(HttpSession session, HttpServletResponse response) {
        System.out.println("로그아웃!!");

        // 1. 서버 세션 파기
        if (session != null) {
            session.invalidate();
        }

        // 2. 브라우저 쿠키(JSESSIONID) 강제 만료 처리
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
