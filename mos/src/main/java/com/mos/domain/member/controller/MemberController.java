package com.mos.domain.member.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.domain.member.service.impl.MBMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
      throw new Exception("해당 계정이 존재하지 않습니다.");
    }
    memberService.get(member.getEmail());
  }


  private final MBMemberService mbMemberService;


  @PostMapping("add")
  public String add(MemberJoinDto joinDto) {
    mbMemberService.add(joinDto);
    return "redirect:/";
  }
}
