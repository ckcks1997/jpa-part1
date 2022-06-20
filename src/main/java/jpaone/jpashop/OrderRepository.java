package jpaone.jpashop;

import jpaone.jpashop.domain.Member;
import jpaone.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    @PersistenceContext
    private EntityManager em; //스프링 부트가 yml파일을 보고 EM을 생성해준다.

    public Long save(Order order){
        em.persist(order);
        return order.getId();
    }
    public Order find(Long id){
        return em.find(Order.class, id);
    }
}
