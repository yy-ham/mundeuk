package com.app.mundeuk.domain.member.service;

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
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
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

}
