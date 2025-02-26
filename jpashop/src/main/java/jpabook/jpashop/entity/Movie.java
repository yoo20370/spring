package jpabook.jpashop.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Movie")
@Getter @Setter
public class Movie extends Item{
    private String director;
    private String actor;

    public Movie() {

    }
}
