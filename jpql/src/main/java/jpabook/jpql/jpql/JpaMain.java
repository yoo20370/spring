package jpabook.jpql.jpql;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {
            Team team1 = new Team();
            team1.setName("teamA");

            Team team2 = new Team();
            team2.setName("teamA");

            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(19);
            member.setTeam(team1);
            member.setAddress(new Address("city", "street", "10000"));
            team1.getMembers().add(member);

            Member member2 = new Member();
            member2.setUsername("memberB");
            member2.setAge(20);
            member2.setTeam(team2);
            team1.getMembers().add(member2);

            em.persist(team1);
            em.persist(team2);
            em.persist(member);
            em.persist(member2);

            for (int i = 0; i < 100; i++) {
                Member newMember = new Member();
                newMember.setUsername("member"+i);
                em.persist(newMember);
            }

            em.flush();
            em.clear();

//            // 단순히 JPQL 사용
//            Query query = em.createQuery("select m from Member m");
//            List<Object> members = query.getResultList();
//
//            for (Object m : members) {
//                System.out.println("m.getUsername() = " + ((Member)m).getUsername());
//            }
//            // 파리미터 사용
//            TypedQuery<Object[]> query2 = em.createQuery("select m.username, m.age from Member m", Object[].class);
//
//            List<Object[]> datas = query2.getResultList();
//            for (Object[] date : datas) {
//                System.out.println("date[0] = " + date[0]);
//                System.out.println("date[1] = " + date[1]);
//            }
//
//            // 연관된 엔티티의 데이터가 필요 없는 경우 일반 조인 사용
//            Query query3 = em.createQuery("select m.username from Member m inner join m.team t where t.name =: teamName");
//            List<String> usernames = query3.setParameter("teamName", team2.getName())
//                    .getResultList();
//            for (String name : usernames) {
//                System.out.println("name = " + name);
//            }

            // 연관관계 엔티티 명시적 조인 - fetch join
            // 지연로딩이든 즉시로딩이든
//            TypedQuery<Member> query4 = em.createQuery("select m from Member m join fetch m.team", Member.class);
//
//            List<Member> memberList  = query4.getResultList();
//            for (Member m : memberList) {
//                System.out.println("m.getTeam().getName() = " + m.getTeam().getName());
//            }
//

//            // 여러 값을 조회하는 경우 DTO를 만들어서 가져올 수 있다.
//            Query query5 = em.createQuery("select new jpabook.jpql.jpql.MemberDto(m.username, m.team.name, m.team, true) from Member m join m.team");
//
//            List<MemberDto> memberDtos = query5.getResultList();
//            for (MemberDto md : memberDtos) {
//                System.out.println("md = " + md);
//            }

            // 페이징 API를 사용하여 for문으로 등록한 인원만 가져오기 ( 기존에 2명은 제외하고 가져오기 )
//            TypedQuery query6 = em.createQuery("select m from Member m", Member.class);
//
//            List<Member> memberList = query6.setFirstResult(2).setMaxResults(102).getResultList();
//            for (Member m : memberList) {
//                System.out.println("m = " + m);
//            }
            // size() 함수 사용 - 컬렉션의 길이를 반환
//            TypedQuery<Integer> query7 = em.createQuery("select size(t.members) from Team t", Integer.class);
//
//            List<Integer> teamSize = query7.getResultList();
//            for (Integer i : teamSize) {
//                System.out.println("i = " + i);
//            }

            //// 명시적 조인을 사용해야 조인 대상에 대하여 별칭을 부여할 수 있다. 즉, 컬렉션 값 연관필드를 위한 별칭
//            Query query8 = em.createQuery("select m.username from Team t inner join t.members m");
//
//            List<String> names = query8.getResultList();
//            for (String name : names) {
//                System.out.println("name = " + name);
//            }

            // 명시적 조인을 사용해야, 쿼리 튜닝하기 쉽다.( JPA가 묵시적으로 조인할 경우 예측하기 어렵기 떄문에 쿼리 튜닝 어려움 )
//            Query query9 = em.createQuery("select t from Member m inner join m.team t");

            // 일반 조인을 사용할 경우 LazyLoading으로 인해, 일반 조인은 연관된 N+1 문제가 발생할 수 있다. -> fetch join으로 이를 해결
            // 연관된 엔티티가 모두 프록시 객체로 선언되어 있음, 그러므로 순차적으로 접근하는 경우 N번의 select 쿼리를 날리게 됨
//            Query query10 = em.createQuery("select m from Member m join m.team");
//            List<Member> members = query10.getResultList();
//            for (Member member1 : members) {
//                System.out.println("member1.getTeam().getName() = " + member1.getTeam().getName());
//            }

            // fetch join을 통해, N+1 문제 해결
            // 한 번에 연관된 team을 모두 가져와서 한 번의 쿼리로 해결, 지연로딩보다 fetch join이 우선권을 가지므로 프록시 객체가 아닌 실제 엔티티 값을 가지고 있음
//            Query query11 = em.createQuery("select m from Member m join fetch m.team ");
//
//            List<Member> members = query11.getResultList();
//            for (Member member1 : members) {
//                System.out.println("member1 = " + member1);
//            }


            // 컬렉션(일대다) 페치 조인시, 데이터 뻥튀기 발생
            // JPA는 조인 테이블 로우 수 만큼 컬렉션에 저장하기 때문에 동일한 데이터가 중복 저장될 수 있다.


//            Query query12 = em.createQuery("select DISTINCT t from Team t join fetch t.members");
//
//            List<Team> teams = query12.getResultList();
//            for (Team team : teams) {
//                System.out.println("team.getName()  = " + team.getName() + " " + team.getMembers().size());
//            }

            // named query를 사용할 수 있다. 이는 애플리케이션 실행 시점에 쿼리를 검증하므로 매우 좋다.
//            Query query13 = em.createNamedQuery("Member.getTeam", Member.class);
//            List<Member> members = query13.getResultList();
//            for (Member member1 : members) {
//                System.out.println("member1 = " + member1);
//            }

            Query query14 = em.createQuery("update Member m set m.age = m.age + 1");

            int resultCount = query14.executeUpdate();
            System.out.println("resultCount = " + resultCount);


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