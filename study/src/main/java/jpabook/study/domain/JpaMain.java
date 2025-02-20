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

//            Member member = new Member();
//            member.setName("hello");
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            //프록시 객체를 가져옴
//            Member findMember = em.getReference(Member.class, member.getId());
//            System.out.println("findMember = " + findMember.getClass());
//            // 값이 실제 사용되는 시점에 쿼리가 나간다.
//            // 찾을 때, ID값을 알고 있으니 조회하지 않음
//            System.out.println("findMember.getId() = " + findMember.getId());
//            // Name의 값은 알 수 없으므로 영속성 컨텍스트에 실제 객체를 요청
//            System.out.println("findMember.getName() = " + findMember.getName());
//            System.out.println("findMember.getName() = " + findMember.getName());
//            Member member1 = new Member();
//            member1.setName("member1");
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setName("member2");
//            em.persist(member2);
//
//            em.flush();
//            em.clear();
//
//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());
//
//            Member m3 = em.find(Member.class, member2.getId());
//            System.out.println(m3.getClass().getName());
//            System.out.println(m2.getClass() == m3.getClass());

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

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
