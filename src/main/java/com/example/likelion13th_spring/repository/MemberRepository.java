package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.likelion13th_spring.domain.Member;

import java.util.List;
import java.util.Optional;

//@Repository // 스프링이 관리하는 빈이라는 것을 알려주는 어노테이션
//public class MemberRepository {
//
//    @PersistenceContext // EntityManager 주입을 위한 어노테이션
//    private EntityManager em; // DB 조작을 위한 객체
//
//    public void save(Member member) {
//        em.persist(member); // 저장
//    }
//
//    public Optional<Member> findById(Long id) {
//        Member member = em.find(Member.class, id);
//        return Optional.ofNullable(member);
//    }
//
//    public List<Member> findAll() {
//        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
//    }
//
//    public Optional<Member> findByEmail(String email) {
//        List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
//                .setParameter("email", email)
//                .getResultList();
//        return result.stream().findFirst();
//    }
//
//    public List<Member> findByName(String name) {
//        return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//    public void delete(Member member){
//        em.remove(member);
//    }

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable); //w18
    List<Member> findByNameStartingWith(String prefix);
}
