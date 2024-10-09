package com.dev.mundeuk.domain.member.entity;

import com.dev.mundeuk.domain.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    private String id;

    private String password;
    private String name;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member toEntity(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setId(memberDto.getId());
        String password = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(password);
        member.setName(memberDto.getName());
        member.setJoinDate(LocalDate.now());
        member.setRole(Role.ROLE_USER);
        return member;
    }

}
