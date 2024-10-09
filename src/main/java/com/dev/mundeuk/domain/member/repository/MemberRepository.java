package com.dev.mundeuk.domain.member.repository;

import com.dev.mundeuk.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByIdAndPassword(String id, String password);

}
