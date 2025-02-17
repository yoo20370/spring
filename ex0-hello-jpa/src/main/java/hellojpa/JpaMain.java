package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 데이터베이스 커넥션 하나 받았다고 생각하면 편하다.
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction ts = em.getTransaction();

        ts.begin();

        try {

            Member member = em.find(Member.class, 150L);
            member.setName("AAAAAA");

            em.clear();
            // JPA에서 member를 관리하지 않음
            // 데이터베이스 커밋이 일어날 때, 해당 객체에 대해서는 아무 일도 일어나지 않는다.
            // JPA에서 관리하지 않기 때문
            // 여기서는 update문이 날아가지 않음

            System.out.println("========================");

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
