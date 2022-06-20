package jpaone.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    protected Address(){}

    private String city;
    private String street;
    private String zipcode;
}