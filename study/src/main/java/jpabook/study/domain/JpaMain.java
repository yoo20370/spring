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

            Address address1 = new Address("city", "street", "zipcode");
            Address address2 = new Address("city", "street", "zipcode");

            System.out.println(address1.equals(address2));


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
