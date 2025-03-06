package jpashop.study.api.Member;

import jpashop.study.domain.Address;
import lombok.Data;

@Data
public class CreateMemberResponse {
    private Long id;
    private String name;
    private Address address;

    public CreateMemberResponse(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
