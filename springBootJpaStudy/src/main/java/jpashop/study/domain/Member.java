package jpashop.study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jmx.export.assembler.MethodNameBasedMBeanInfoAssembler;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();


    private Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // 멤버 생성자 메서드
    public static Member createMember(String name, Address address) {
        return new Member(name, address);
    }
}
