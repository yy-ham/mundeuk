package com.app.mundeuk.domain.review.repository;

import com.app.mundeuk.domain.review.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository {

    @PersistenceContext
    private EntityManager em;

    // 리뷰 저장
    public void save(Review review) {
        em.persist(review);
    }

}
