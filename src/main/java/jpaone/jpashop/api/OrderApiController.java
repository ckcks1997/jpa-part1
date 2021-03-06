package jpaone.jpashop.api;

import jpaone.jpashop.domain.Order;
import jpaone.jpashop.domain.OrderItem;
import jpaone.jpashop.repository.OrderRepository;
import jpaone.jpashop.repository.OrderSearch;
import jpaone.jpashop.repository.order.query.OrderFlatDto;
import jpaone.jpashop.repository.order.query.OrderItemQueryDto;
import jpaone.jpashop.repository.order.query.OrderQueryDto;
import jpaone.jpashop.repository.order.query.OrderQueryRepository;
import jpaone.jpashop.service.query.OrderQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryService orderQueryService;
    @GetMapping("/api/v1/orders") //엔티티 노출,x
    public List<Order> ordersV1(){

        return orderQueryService.ordersV1();
    }

    @GetMapping("/api/v2/orders") //DTO 변환
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(toList());

        return collect;
    }
    @GetMapping("/api/v2.1/orders")
    public Result ordersV21() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(toList());

        return new Result(collect.size(), collect);
    }


    @GetMapping("/api/v3/orders") // jpql join fetch
    public List<OrderDto> ordersV3() {
        return orderQueryService.ordersV3();
    }
    @GetMapping("/api/v3.1/orders") //xToOne관계까지만 join fetch로 가져온 후 나머지는 lazy + 하이버네이트 설정
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue ="0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(toList());

        return collect;
    }

    @GetMapping("/api/v4/orders") //DTO사용
    public List<OrderQueryDto> ordersV4(@RequestParam(value = "offset", defaultValue ="0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {

        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllByDto_optimizaion();
    }
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6_flat(){ //비추..
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }
}
