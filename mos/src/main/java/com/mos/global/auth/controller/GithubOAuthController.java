package com.mos.global.auth.controller;

import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.global.auth.service.GithubOAuthService;
import com.mos.global.auth.service.GoogleOAuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GithubOAuthController {

  @Autowired
  private GithubOAuthService githubOAuthService;
  @Autowired
  private GoogleOAuthService googleOAuthService;
  @Autowired
  private DefaultMemberService memberService;

  @GetMapping("callback")
  public String githubLogin(@RequestParam String code, MemberJoinDto joinDto, Model model) {
    Optional<String> emailOpt = githubOAuthService.getAccessToken(code);

    if (emailOpt.isPresent()) {
      joinDto.setEmail(emailOpt.get());
      if (memberService.existsByEmail(joinDto.getEmail())) {
        return "redirect:/";
      }
      model.addAttribute("joinDto", joinDto);
    } else {
      model.addAttribute("error", "github 로그인 실패");
    }
    return "auth/form";
  }

  @GetMapping("login/oauth2/code/google")
  public String googleOAuth(@RequestParam String code, MemberJoinDto joinDto, Model model) {
    Optional<String> emailOpt = googleOAuthService.getAccessToken(code);

    if (emailOpt.isPresent()) {
      joinDto.setEmail(emailOpt.get());
      if (memberService.existsByEmail(joinDto.getEmail())) {
        return "redirect:/";
      }
      model.addAttribute("joinDto", joinDto);
    } else {
      model.addAttribute("error", "github 로그인 실패");
    }
    return "auth/form";
  }
}
