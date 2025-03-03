package jpashop.study.repository;

import jpashop.study.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {

    Long save(Member member);

    Member findOne(Long id);

    List<Member> findAll();

    List<Member> findByName(String username);
}
