package com.app.mundeuk.domain.member.repository;

import com.app.mundeuk.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 가입 및 회원 정보 수정
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 전체 조회
    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m order by m.id desc", Member.class)
                .getResultList();
        return members;
    }

    // 회원 탈퇴
    public void delete(Member member) {
        em.remove(member);
    }
}
