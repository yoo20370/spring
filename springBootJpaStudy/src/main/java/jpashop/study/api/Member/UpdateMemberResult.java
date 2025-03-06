package jpashop.study.api.Member;

import lombok.Data;

@Data
public class UpdateMemberResult {
    private String message;
    private UpdateMemberResponse updateMemberResponse;

    public UpdateMemberResult(String message, UpdateMemberResponse updateMemberResponse) {
        this.message = message;
        this.updateMemberResponse = updateMemberResponse;
    }
}
