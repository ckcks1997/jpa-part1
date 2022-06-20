package jpaone.jpashop.service;

import jpaone.jpashop.domain.Member;
import jpaone.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em; //되는데 왜 ide에서는 오류로 뜨지..?

    @Test
    @Rollback(value = false)// 기본속성은 rollback인데 이럴경우 아예 flush조차 하지 않음(db저장x)
    public void 회원가입() throws Exception{
        //given,when,then
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);
        em.flush();
        em.clear();

        assertEquals(member.getName(), memberRepository.findOne(savedId).getName());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        /*try {
            memberService.join(member2);
        }catch (IllegalStateException e){
            return;
        }*/
        memberService.join(member2);

        fail("예외가 발생해야 한다.");
    }
}