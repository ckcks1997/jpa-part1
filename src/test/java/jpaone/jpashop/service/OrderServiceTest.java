package jpaone.jpashop.service;

import jpaone.jpashop.domain.Address;
import jpaone.jpashop.domain.Member;
import jpaone.jpashop.domain.Order;
import jpaone.jpashop.domain.OrderStatus;
import jpaone.jpashop.domain.item.Book;
import jpaone.jpashop.domain.item.Item;
import jpaone.jpashop.exception.NotEnoughStockException;
import jpaone.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    public void 상품주문() throws Exception{
        Member member = createMember();
        Item book = createBook("사골",10000,10);

        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("가격오류", 10000*2, getOrder.getTotalPrice());
    }

    @Test
    public void 주문취소() throws Exception{

        Member member = createMember();
        Item item = createBook("aa", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);
        Assert.assertEquals("캔슬이 아님", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("주문이 취소된 수량 복구", 10, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고초과() throws Exception{
        Member member = createMember();
        Item book = createBook("사골", 1000,10);
        int orderCount = 11;

        orderService.order(member.getId(), book.getId(), orderCount);

        Assert.fail("예외가 터져야됨");

    }

    private Item createBook(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123123"));
        em.persist(member);
        return member;
    }
}