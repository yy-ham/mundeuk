package com.app.mundeuk.domain.member.service;

import com.app.mundeuk.domain.member.dto.MemberRequestDto;
import com.app.mundeuk.domain.member.dto.MemberResponseDto;
import com.app.mundeuk.domain.member.entity.Member;
import com.app.mundeuk.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    @Transactional
    public MemberResponseDto signUp(MemberRequestDto requestDto) {
        // 1. 회원 중복 확인
        if (validateDuplicateMember(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 2. DTO -> Entity 변환
        Member member = requestDto.toEntity();

        // 3. 회원가입
        memberRepository.save(member);
        
        // 4. Entity -> DTO 변환
        MemberResponseDto responseDto = MemberResponseDto.fromEntity(member);
        return responseDto;
    }

    // 로그인
    public MemberResponseDto signIn(MemberRequestDto requestDto) {
        // 1. 이메일 확인
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 확인
        if (!member.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. Entity -> DTO 변환
        MemberResponseDto responseDto = MemberResponseDto.fromEntity(member);
        return responseDto;
    }

    // 전체 회원 조회
    public List<MemberResponseDto> findMembers() {
        // 1. 조회
        List<Member> members = memberRepository.findAll();

        //2. DTO 변환
        List<MemberResponseDto> responseDtos = new ArrayList<>();
        for (Member member : members) {
            responseDtos.add(MemberResponseDto.fromEntity(member));
        }
        return responseDtos;
    }

    // 회원 정보 수정
    @Transactional
    public Long updateMemberInfo(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 탈퇴
    public Long deleteMember(Member member) {
        memberRepository.delete(member);
        return member.getId();
    }

    // 회원 중복 확인
    public boolean validateDuplicateMember(String email) {
        return memberRepository.existsByEmail(email);
    }

}
