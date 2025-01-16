package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 문제 때문에 ConcurrentHashMap 클래스를 사용
    // 이 클래스는 다수의 스레드가 동시에 데이터를 읽거나 수정해야 하는 상황에서 안전하고 효율적으로 동작하도록 설계됨
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
