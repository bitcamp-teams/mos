package com.mos.domain.member.controller;

import com.mos.domain.member.service.impl.GithubOAuthService;
import java.io.IOException;
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
  public String githubLogin(@RequestParam String code, Model model) throws IOException{
    String email = loginService.getAccessToken(code);
    model.addAttribute("email", email);
    return "auth/form";
  }
}
