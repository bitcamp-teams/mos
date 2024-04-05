package com.mos.domain.member.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.mos.domain.member.service.MemberService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

  private final DefaultMemberService memberService;

  @GetMapping("findByEmail")
  public void findByEmail(String email) throws Exception{
    MemberDto member = memberService.get(email);
    if (member == null) {
      throw new Exception("해당 이메일이 존재하지 않습니다.");
    }
    memberService.get(member.getEmail());
  }

  @GetMapping("findByUsername")
  public String findByUsername(String username) throws Exception {
    MemberDto member = memberService.getName(username);
    if (memberService.getName(username) != null) {
      System.out.println("중복된 닉네임입니다.");
      return "auth/signup";
    }
    return "index";
  }

  @PostMapping("add")
  public String add(MemberJoinDto joinDto) {
    memberService.join(joinDto);
    System.out.println("사용가능한 닉네임입니다.");
    return "index";
  }


    @GetMapping("view")
    public void view(int no, Model model) throws Exception {
        MemberDto member = memberService.getNo(no);
        if (member == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }
        model.addAttribute("member", member);
    }
}
