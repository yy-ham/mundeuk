package com.dev.mundeuk.domain.member.service;

import com.dev.mundeuk.domain.member.dto.MemberDto;
import com.dev.mundeuk.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setId("test00@test.com");
        memberDto.setPassword("1234");
        memberDto.setName("test00");
        return Member.toEntity(memberDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void join() {
        Member member = createMember();
        Member joinMember = memberService.join(member);

        assertEquals(member.getId(), joinMember.getId());
        assertEquals(member.getPassword(), joinMember.getPassword());
        assertEquals(member.getName(), joinMember.getName());
        assertEquals(member.getJoinDate(), joinMember.getJoinDate());
        assertEquals(member.getRole(), joinMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 검증 테스트")
    public void validateDuplicateMember() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.join(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        assertEquals("이미 가입된 이메일입니다.", e.getMessage());
    }

}