package jpaone.jpashop.service;


import jpaone.jpashop.domain.*;
import jpaone.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = getMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 book", 10000,100);
            Book book2 = createBook("JPA2 book", 20000,200);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }



        public void dbInit2(){
            Member member = getMember("userB", "진주", "2", "222");
            em.persist(member);

            Book book1 = createBook("spring1 book", 20000,100);
            Book book2 = createBook("spring2 book", 40000,200);

            em.persist(book1);
            em.persist(book2);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Book createBook(String JPA1_book, int price, int quantity) {
            Book book1 = new Book();
            book1.setName(JPA1_book);
            book1.setPrice(price);
            book1.setStockQuantity(quantity);
            return book1;
        }

        private Member getMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }

}


