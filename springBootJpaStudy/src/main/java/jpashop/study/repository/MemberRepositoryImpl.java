package jpashop.study.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpashop.study.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Member member) {
        // 쓰기와 조회를 분리해야 한다. 그렇지 않으면
        em.persist(member);
        return member.getId();
    }

    @Override
    public Member findOne(Long id) {
        return em.find(Member.class, id);

    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.name =: username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
}
