package com.app.mundeuk.domain.member.dto;

import com.app.mundeuk.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private String profileImage;
    private LocalDateTime createdAt; // 가입일자까지만 공개 yyyyy-mm-dd

    // Entity -> Dto 변환
    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImage(),
                member.getCreatedAt()
        );
    }

}
