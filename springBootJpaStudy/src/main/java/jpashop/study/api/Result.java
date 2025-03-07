package jpashop.study.api;

import lombok.Data;

@Data
public class Result<T> {
    private String message;
    private T data;

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
