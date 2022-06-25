package jpaone.jpashop.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpaone.jpashop.domain.Address;
import jpaone.jpashop.domain.Order;
import jpaone.jpashop.domain.OrderItem;
import jpaone.jpashop.domain.OrderStatus;
import jpaone.jpashop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order o) {
        orderId = o.getId();
        name = o.getMember().getName();
        orderDate = o.getOrderDate();
        orderStatus = o.getStatus();
        address = o.getDelivery().getAddress();
        orderItems = o.getOrderItems().stream()
                .map( i -> new OrderItemDto(i.getItem().getName(), i.getOrderPrice(), i.getCount()))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    private class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

    }
}
