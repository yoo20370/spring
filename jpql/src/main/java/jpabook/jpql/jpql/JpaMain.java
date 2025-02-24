package jpabook.jpql.jpql;

import jakarta.persistence.*;


import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {

            Team team = new Team();
            team.setName("A");
            em.persist(team);

            Member member = new Member();
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.ADMIN);
            member.setAddress(new Address("city", "street", "zipcode"));

            for (int i = 0; i < 100; i++) {
                Member m = new Member();
                m.setAge(i+10);
                m.setUsername(i + "");

                em.persist(m);
            }

            em.persist(member);

            em.flush();
            em.clear();

            // m.username은 상태 필드
            String query = "SELECT t.members FROM Team t";

            List<Object> resultList = em.createQuery(query)
                    .getResultList();
            for (Object object : resultList) {
                System.out.println("object = " + object);
            }

            ts.commit();
        } catch (Exception e) {
            ts.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}