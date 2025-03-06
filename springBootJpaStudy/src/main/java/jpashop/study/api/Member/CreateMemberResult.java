package jpashop.study.api.Member;

import lombok.Data;

@Data
public class CreateMemberResult {
    private String message;
    private CreateMemberResponse createMemberResponse;

    public CreateMemberResult(String message, CreateMemberResponse createMemberResponse) {
        this.message = message;
        this.createMemberResponse = createMemberResponse;
    }
}
