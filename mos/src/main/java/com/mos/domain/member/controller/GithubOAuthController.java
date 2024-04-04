package com.mos.domain.member.controller;

import com.mos.domain.member.service.impl.GithubOAuthService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GithubOAuthController {

  @Autowired
  private GithubOAuthService loginService;

  @GetMapping("callback")
  public String githubLogin(@RequestParam String code, Model model) {
    Optional<String> emailOpt = loginService.getAccessToken(code);

    if (emailOpt.isPresent()) {
      model.addAttribute("email", emailOpt.get());
    } else {
      model.addAttribute("error", "github 로그인 실패");
    }
    return "auth/form";
  }
}
