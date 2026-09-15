package com.app.mundeuk.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
//@Setter
@Builder
@AllArgsConstructor // Builder 사용 시 필수
@ToString
@NoArgsConstructor
@DynamicInsert
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    @SequenceGenerator(name = "seq_member", sequenceName = "seq_member", allocationSize = 1)
    private Long id;

    private String email;
    private String password;
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "active_yn")
    private String activeYN;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

//    public static Member toEntity(MemberDto memberDto, PasswordEncoder passwordEncoder) {
//        Member member = new Member();
//        member.setId(memberDto.getId());
//        String password = passwordEncoder.encode(memberDto.getPassword());
//        member.setPassword(password);
//        member.setName(memberDto.getName());
//        member.setJoinDate(LocalDate.now());
//        member.setRole(Role.ROLE_USER);
//        return member;
//    }

}
