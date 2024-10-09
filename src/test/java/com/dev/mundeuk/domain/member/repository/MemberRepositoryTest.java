package com.dev.mundeuk.domain.member.repository;

import com.dev.mundeuk.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void save() {
        Member member = new Member();
        member.setId("test@test.com");
        member.setPassword("test1234");
        member.setName("test");
        member.setJoinDate(LocalDate.now());

        Member savedMember = memberRepository.save(member);
        assertEquals(member.getId(), savedMember.getId());
    }

    @Test
    @DisplayName("로그인 테스트")
    public void findByIdAndPassword() {
        // 아이디 일치, 비밀번호 일치
        Optional<Member> savedMember = memberRepository.findByIdAndPassword("test@test.com", "test1234");
        assertTrue(savedMember.isPresent());

        // 아이디 일치, 비빌번호 불일치
        Optional<Member> notSavedMember1 = memberRepository.findByIdAndPassword("test@test.com", "1234");
        assertFalse(notSavedMember1.isPresent());

        // 아이디 불일치
        Optional<Member> notSavedMember2 = memberRepository.findByIdAndPassword("test1@test.com", "test1234");
        assertFalse(notSavedMember2.isPresent());
    }

    @Test
    @DisplayName("아이디 중복 확인 테스트")
    public void findById() {
        // 아이디 중복 O -> 이미 가입된 아이디
        Optional<Member> savedMember = memberRepository.findById("test@test.com");
        assertTrue(savedMember.isPresent());

        // 아이디 중복 X -> 회원가입 가능
        Optional<Member> notSavedMember = memberRepository.findById("test2@test.com");
        assertFalse(notSavedMember.isPresent());
    }

}
