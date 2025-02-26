package jpabook.jpashop.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Album")
@Getter @Setter
public class Album extends Item{

    private String artist;
    private String etc;

    public Album() {

    }
}
