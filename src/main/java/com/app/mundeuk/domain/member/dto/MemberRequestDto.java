package com.app.mundeuk.domain.member.dto;

import com.app.mundeuk.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {

    private Long id; // 토큰 인증 방식으로 변환 후 필드 제거
    private String email;
    private String password;
    private String nickname;
    private String profileImage;

    // Dto -> Entity 변환
    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .profileImage(this.profileImage)
                .build();
    }

}
