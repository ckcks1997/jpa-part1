package jpaone.jpashop;

import jpaone.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; //스프링 부트가 yml파일을 보고 EM을 생성해준다.

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }


    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
