package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    private Long id;
    private String name;

    // JPA는 기본적으로 내부적으로 리플렉션 등을 사용하므로
    // 동적으로 객체를 생성해야 함, 그래서 기본 생성자가 있어야 한다.
    public Member(){}

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
