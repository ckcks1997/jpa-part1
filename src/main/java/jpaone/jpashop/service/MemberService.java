package jpaone.jpashop.service;

import jpaone.jpashop.domain.Member;
import jpaone.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional//jpa에서 일어나는 일들은 모두 transactional 안에서 일어나야 한다.
@Transactional(readOnly = true) //읽기에는 이렇게하면 더 성능 최적화됨
@RequiredArgsConstructor // final 생성자 만들어줌
public class MemberService {
    private final MemberRepository memberRepository;

    //requiredArgConstructor
/*    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    //회원가입
    @Transactional//쓰기
    public  Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }
    //전체조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
