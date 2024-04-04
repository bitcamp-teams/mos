package com.mos.domain.member.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @GetMapping("findByEmail")
  public void findByEmail(String email) throws Exception{
    MemberDto member = memberService.get(email);
    if (member == null) {
      throw new Exception("해당 이메일이 존재하지 않습니다.");
    }
    memberService.get(member.getEmail());
  }

  @GetMapping("findByUsername")
  public String findByUsername(String username) throws Exception{
    MemberDto member = memberService.getName(username);
    if (memberService.getName(username) != null) {
      System.out.println("중복된 닉네임입니다.");
      return "auth/signup";
    }

    System.out.println("사용가능한 닉네임입니다.");
    return "/index";
  }
}
