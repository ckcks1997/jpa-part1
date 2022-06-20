package jpaone.jpashop;

import jpaone.jpashop.domain.Delivery;
import jpaone.jpashop.domain.Member;
import jpaone.jpashop.domain.Order;
import jpaone.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberRepositoryTest {


    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception{

        Member member = new Member();
        member.setName("memberA");

        Delivery d = new Delivery();
        Order o = new Order();
        o.setDelivery(d);

        Long savedId = memberRepository.save(member);
        Long savedId2 = orderRepository.save(o);
        Member findMember = memberRepository.findOne(savedId);

        Order order2 = orderRepository.find(o.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

    }
}