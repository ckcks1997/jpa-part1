package jpaone.jpashop.controller;

import jpaone.jpashop.domain.Address;
import jpaone.jpashop.domain.Member;
import jpaone.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm()); //validation을 위해 빈 껍데기를 보냄냄        return "members/createMemberForm";
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") //valid시 해당객체의 validation 적용됨, 오류시 바인딩리졸트에 담겨 리턴
    public String create( @Valid MemberForm form, BindingResult result){

        //참고로 memberform은 @ModelAtrtribute의 속성을 가져 별도의 리턴없어도 그대로 값이 리턴된다.
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";

    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
