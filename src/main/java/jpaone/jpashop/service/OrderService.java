package jpaone.jpashop.service;

import jpaone.jpashop.domain.Delivery;
import jpaone.jpashop.domain.Member;
import jpaone.jpashop.domain.Order;
import jpaone.jpashop.domain.OrderItem;
import jpaone.jpashop.domain.item.Item;
import jpaone.jpashop.repository.ItemRepository;
import jpaone.jpashop.repository.MemberRepository;
import jpaone.jpashop.repository.OrderRepository;
import jpaone.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem  = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장
        orderRepository.save(order);
        return order.getId();
    }


    //주문취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }

    //public List<Order> findOrders(OrderSearch orderSearch){}

}
