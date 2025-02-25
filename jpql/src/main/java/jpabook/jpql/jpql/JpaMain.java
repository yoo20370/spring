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

            Team teamA = new Team();
            teamA.setName("팀A");

            Team teamB = new Team();
            teamB.setName("팀B");

            Team teamC = new Team();
            teamC.setName("팀C");

            em.persist(teamA);
            em.persist(teamB);
            em.persist(teamC);


            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);

            Member member4 = new Member();
            member4.setUsername("회원4");

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            em.flush();
            em.clear();

            String jpqlQuery = "SELECT m FROM Member m INNER JOIN FETCH m.team";

            List<Member> members = em.createQuery(jpqlQuery, Member.class).getResultList();
            for (Member m : members) {
                System.out.print("m.getUsername() = " + m.getUsername() + " ");
                System.out.println("m.getUsername() = " + m.getTeam().getName());
            }

            String jpqlQuery2 = "SELECT DISTINCT t FROM Team t INNER JOIN FETCH t.members";

            List<Team> teams = em.createQuery(jpqlQuery2, Team.class).getResultList();
            for (Team team : teams) {
                System.out.println("team.getName() = " + team.getName() +" " + team.getMembers());
            }

            String jpqlQuery3 = "Select m FROM Member m WHERE m.id = :member";

            Member member = em.createQuery(jpqlQuery3, Member.class)
                    .setParameter("member", member1.getId())
                    .getSingleResult();

            System.out.println("member = " + member);
            
            String jpqlQuery4 = "select m from Member m where m.team = :team";
            Member member10 = em.createQuery(jpqlQuery4, Member.class)
                    .setParameter("team", teamB)
                    .getSingleResult();

            System.out.println("member10 = " + member10);


            List<Member> resultList = em.createNamedQuery("Member.findByUserName")
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member member5 : resultList) {
                System.out.println("member5 = " + member5);
            }

            int resultCount = em.createQuery("update Member m set m.age = 20").executeUpdate();
            System.out.println(resultCount);
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