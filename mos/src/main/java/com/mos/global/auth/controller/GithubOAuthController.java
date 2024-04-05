package com.mos.global.auth.controller;

import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.global.auth.dto.OAuthDto;
import com.mos.global.auth.service.GithubOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GithubOAuthController {

  @Autowired
  private GithubOAuthService githubService;
  @Autowired
  private DefaultMemberService memberService;

  @GetMapping("callback")
  public String githubLogin(@RequestParam String code, Model model) throws Exception {
    OAuthDto oauthDto = githubService.getAccessToken(code);

      if (memberService.existsByEmail(oauthDto.getEmail())) {
        return "redirect:/";
      }
      model.addAttribute("oauthDto", oauthDto);
    return "auth/form";
  }
}
