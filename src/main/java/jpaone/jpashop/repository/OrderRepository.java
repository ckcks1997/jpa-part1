package jpaone.jpashop.repository;

import jpaone.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    //검색
    public List<Order> findAll(OrderSearch orderSearch){
        List<Order> resultList = em.createQuery("select o from Order o join o.member m" +
                        " where o.status = : status "
                        + " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();

        return resultList;
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            jpql += " where o.status = :status";
            isFirstCondition = false;
        }
        //회원 이름 검색
        if (orderSearch.getMemberName() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if ( orderSearch.getMemberName() != null) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery("select o from Order o " +
                "join fetch o.member m " +
                "join fetch o.delivery d", Order.class).getResultList() ;
    }

    public List<Order> findAllWithMemberDelivery(int off, int limit) {
        return em.createQuery("select o from Order o " +
                "join fetch o.member m " +
                "join fetch o.delivery d", Order.class)
                .setFirstResult(off)
                .setMaxResults(limit)
                .getResultList() ;
    }
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery("select new jpaone.jpashop.repository.OrderSimpleQueryDto(" +
                        "o.id, m.name, o.orderDate, o.status, d.address" +
                        ") from Order o " +
                "join o.member m " +
                "join o.delivery d  ", OrderSimpleQueryDto.class)
                .getResultList();

    }

    public List<Order> findAllWithItem(OrderSearch orderSearch) { //distince를 하지 않으면 결과가 뻥튀기됨
        return em.createQuery("select distinct o from Order o " +
                "join fetch o.member m " +
                "join fetch o.delivery d " +
                "join fetch o.orderItems oi " +
                "join fetch oi.item i", Order.class)
                //하지만, 일대다의 경우(페치조인의 경우) 페이징이 불가하다.
                //입력해도 쿼리에는 안먹고 메모리에서 페이징처리가 되어버림..
                .setFirstResult(1)
                .setMaxResults(100)
                .getResultList();

    }
}
