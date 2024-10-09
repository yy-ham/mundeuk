package com.dev.mundeuk.domain.member.service;

import com.dev.mundeuk.domain.member.entity.Member;
import com.dev.mundeuk.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Member join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 중복 회원 검증
    public void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                   throw new IllegalStateException("이미 가입된 이메일입니다.");
                });
    }

}
