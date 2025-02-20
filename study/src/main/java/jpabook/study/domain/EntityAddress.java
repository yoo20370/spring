package jpabook.study.domain;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityAddress {

    private String city;
    private String street;
    private String zipcode;

    public EntityAddress() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
