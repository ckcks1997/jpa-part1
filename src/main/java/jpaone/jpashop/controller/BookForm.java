package jpaone.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BookForm {

    private Long id;
    //private Long id;
    @NotEmpty(message = "아이템 이름은 필수입니다.")
    private String name;

    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
