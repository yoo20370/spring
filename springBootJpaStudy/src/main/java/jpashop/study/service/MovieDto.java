package jpashop.study.service;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieDto extends ItemDto {
    private String director;
    private String actor;
}
