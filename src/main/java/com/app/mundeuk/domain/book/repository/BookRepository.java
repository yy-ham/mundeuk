package com.app.mundeuk.domain.book.repository;

import com.app.mundeuk.domain.book.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    // 책 저장
    public void save(Book book) {
        em.persist(book);
    }

    public Optional<Book> findByIsbn10OrIsbn13(String isbn10, String isbn13) {
        List<Book> books = em.createQuery("select b from Book b where b.isbn10 = :isbn10 or b.isbn13 = :isbn13", Book.class)
                .setParameter("isbn10", isbn10)
                .setParameter("isbn13", isbn13)
                .getResultList();
        return books.stream().findFirst();
    }

    // isbn10 중복 확인
    public boolean existsByIsbn10(String isbn10) {
        List<Long> results = em.createQuery("select b.id from Book b where b.isbn10 = :isbn10", Long.class)
                .setParameter("isbn10", isbn10)
                .setMaxResults(1)
                .getResultList();
        return !results.isEmpty();
    }

    // isbn13 중복 확인
    public boolean existsByIsbn13(String isbn13) {
        List<Long> results = em.createQuery("select b.id from Book b where b.isbn13 = :isbn13", Long.class)
                .setParameter("isbn13", isbn13)
                .setMaxResults(1)
                .getResultList();
        return !results.isEmpty();
    }

}
