package jpashop.study.api.Member;

import jpashop.study.domain.Address;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private Address address;

    public MemberDto(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
