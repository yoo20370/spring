package jpabook.jpashoop;

import jakarta.persistence.*;
import jpabook.jpashoop.domain.Member;
import jpabook.jpashoop.domain.Movie;

import java.time.LocalDateTime;


public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 데이터베이스 커넥션 하나 받았다고 생각하면 편하다.
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction ts = em.getTransaction();

        ts.begin();

        try {

            Member member = new Member();
            member.setName("user1");
            member.setCreateBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

            ts.commit();
        } catch (Exception e) {
            ts.rollback();
        } finally {
            // 애플리케이션 종료시 종료시켜줘야 함
            em.close();
        }
        emf.close();
    }
}
