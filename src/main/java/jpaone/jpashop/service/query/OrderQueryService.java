package jpaone.jpashop.service.query;


import jpaone.jpashop.api.OrderDto;
import jpaone.jpashop.domain.Order;
import jpaone.jpashop.domain.OrderItem;
import jpaone.jpashop.repository.OrderRepository;
import jpaone.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem(new OrderSearch());
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(toList());

        return collect;
    }
}
