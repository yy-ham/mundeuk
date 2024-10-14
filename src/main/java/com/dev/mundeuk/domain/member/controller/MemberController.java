package com.dev.mundeuk.domain.member.controller;

import com.dev.mundeuk.domain.member.dto.MemberDto;
import com.dev.mundeuk.domain.member.entity.Member;
import com.dev.mundeuk.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "member/join";
        }

        try {
            Member member = Member.toEntity(memberDto, passwordEncoder);
            memberService.join(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }

        return "redirect:/";
    }

}
