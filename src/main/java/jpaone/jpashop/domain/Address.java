package jpaone.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {

    protected Address(){}

    private String city;
    private String street;
    private String zipcode;
}
