package jpabook.study.domain;

import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {

            Address address = new Address("city", "street", "100");

            Member member1 = new Member();
            member1.setName("member1");
            member1.setHomeAddress(address);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setName("member2");
            member2.setHomeAddress(copyAddress);

            em.persist(member1);
            em.persist(member2);

            // 업데이트 쿼리가 두 번 나감, 이런 버그는 잡기 어렵다.
            // 하나의 임베디드 타입을 공유하기 때문


            ts.commit();
        } catch (Exception e) {
            ts.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getName();

    }
}
