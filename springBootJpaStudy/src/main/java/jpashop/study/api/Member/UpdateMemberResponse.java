package jpashop.study.api.Member;

import jpashop.study.domain.Address;
import lombok.Data;

@Data
public class UpdateMemberResponse {
    private Long id;
    private String name;
    private Address address;

    public UpdateMemberResponse(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
