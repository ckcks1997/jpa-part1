package jpaone.jpashop.repository;

import jpaone.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderSearch {

    private String memberName; //회원 이름
    private OrderStatus orderStatus;//주문상태
}
