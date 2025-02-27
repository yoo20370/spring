package jpabook.jpashop.entity.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
// 컴포넌트 스캔 대상이 되어 스프링 컨테이너 자동으로 빈 등록 되어 관리됨
// DB 예외를 스프링 예외로 바꿔주는 기능 제공
// 스프링 컨테이너가 Repository라는 것을 인식
@RequiredArgsConstructor
@Repository
public class MemberRepository {

//    @PersistenceContext // 스프링이 엔티티 매니저를 만들어서 주입해준다.
    private final EntityManager em;

    // 스프링 부트의 스프링 Data JPA를 사용하면 @PersistenceContext 대신 Autowired 사용 가능하도록 지원
    // -> @RequiredArgsConstructor 사용 가능
//    @Autowired
//    private EntityManager em;

    // 엔티티 매니저 팩토리를 주입하고 싶다면, @PersistenceUnits 사용
//    @PersistenceUnits
//    private EntityManagerFactory emf;

    public Long save(Member member) {
        // 영속화를 시키면 영속성 컨텍스트에 등록되고 관리됨 ( 1차 캐시에 id, 엔티티 객체, 스냅샷 저장), SQL 쓰기 지연 저장소에 쿼리 저장
        // 트랜잭션 시점에 flush()가 호출되어 DB에 변경된 데이터가 반영된다. (SQL 쓰기 지연 저장소 쿼리 한 번에 전달 - 개수 지정 가능)
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        // DB에서 해당하는 id를 찾아서 영속성 컨텍스트 1차 캐시에 저장 후 반환
        // em.find()는 단 건 조회시 사용
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // 단 건 조회가 아닌 모든 데이터 조회 시 JPQL 사용 - JPQL은 엔티티 객체를 대상으로 쿼리 -> SQL로 번역되어 실행됨
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String userName) {
        return em.createQuery("select m from Member m where m.name = :username", Member.class)
                .setParameter("username", userName)
                .getResultList();
    }
}
