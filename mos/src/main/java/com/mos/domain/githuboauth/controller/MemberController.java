//package com.mos.domain.githuboauth.controller;
//
//import com.mos.service.GithubOAuthService;
//import com.mos.service.MBMemberService;
//import com.mos.service.MemberService;
//import com.mos.vo.MemberJoinDto;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.Mapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/member")
//public class MemberController {
//
//    private final MBMemberService memberService;
//
//    @Autowired
//    public MemberController(MBMemberService memberService) {
//        this.memberService = memberService;
//    }
//
//
//    @PostMapping("add")
//    public String add(MemberJoinDto joinDto) {
//        memberService.add(joinDto);
//        return "redirect:/";
//    }
//}
