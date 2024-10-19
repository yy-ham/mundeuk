package com.dev.mundeuk.domain.member.service;

import com.dev.mundeuk.domain.member.entity.Member;
import com.dev.mundeuk.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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

    //로그인
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id)));

        return User.builder()
                .username(member.get().getId())
                .password(member.get().getPassword())
                .roles("USER")
                .build();
    }
}
