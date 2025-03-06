package jpashop.study.api.Member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @NotEmpty(message = "이름을 넣어주세요.")
    private String name;
}
