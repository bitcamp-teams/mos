package com.mos.global.auth.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.service.MemberService;
import com.mos.global.auth.LoginUser;
import com.mos.global.auth.handler.LoginApiManager;
import com.mos.global.auth.handler.response.LoginResponseHandler;

import javax.servlet.http.HttpSession;

import com.mos.global.notification.service.RedisMessageService;
import com.mos.global.notification.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.mos.global.auth.handler.LoginApiProvider.*;
import static com.mos.global.auth.handler.OAuthRequestParam.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping()
public class OAuthController {

  private final MemberService memberService;
  private final LoginApiManager loginApiManager;
  private final SseEmitterService emitterService;
  private final RedisMessageService redisMessageService;
  private final WebClient webClient;

  @GetMapping("/auth/login")
  public String login(Model model) {
    // 카카오
    model.addAttribute("kakaoUrl",
        KAKAO_AUTH_URI.getParam() + "/oauth/authorize?client_id=" + KAKAO_CLIENT_ID.getParam() + "&redirect_uri=" + KAKAO_REDIRECT_URL.getParam() + "&response_type=code");
    // 깃헙
    model.addAttribute("clientId", GITHUB_CLIENT_ID.getParam());
    // 구글
    model.addAttribute("googleUrl",
        "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + GOOGLE_CLIENT_ID.getParam() + "&redirect_uri=" + GOOGLE_REDIRECT_URI.getParam() + "&response_type=code&scope=email");

    // 네이버
    model.addAttribute("naverUrl",
        NAVER_AUTH_URI.getParam() + "?client_id=" + NAVER_CLIENT_ID.getParam() + "&redirect_uri=" + NAVER_REDIRECT_URI.getParam() + "&response_type=code&state=mos");


    return "auth/login";
  }

  @GetMapping("/auth/login-template")
  public String loginForm(Model model) {
    // 카카오
    model.addAttribute("kakaoUrl",
        KAKAO_AUTH_URI.getParam() + "/oauth/authorize?client_id=" + KAKAO_CLIENT_ID.getParam() + "&redirect_uri=" + KAKAO_REDIRECT_URL.getParam() + "&response_type=code");
    // 깃헙
    model.addAttribute("clientId", GITHUB_CLIENT_ID.getParam());
    // 구글
    model.addAttribute("googleUrl",
        "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + GOOGLE_CLIENT_ID.getParam() + "&redirect_uri=" + GOOGLE_REDIRECT_URI.getParam() + "&response_type=code&scope=email");

    // 네이버
    model.addAttribute("naverUrl",
        NAVER_AUTH_URI.getParam() + "?client_id=" + NAVER_CLIENT_ID.getParam() + "&redirect_uri=" + NAVER_REDIRECT_URI.getParam() + "&response_type=code&state=mos");


    return "auth/form";
  }

  // 카카오
  @GetMapping("/auth/kakao/callback")
  public String kakaoLogin(@RequestParam String code, Model model,
      RedirectAttributes redirectAttributes, HttpSession session) throws Exception {
    LoginResponseHandler kakaoInfo =
        loginApiManager.getProvider("KAKAO").getUserInfo(webClient, code);
    return url(session, redirectAttributes, kakaoInfo, KAKAO.name());
  }

  // 깃헙
  @GetMapping("login/oauth2/code/github")
  public String githubLogin(@RequestParam String code, Model model,
      RedirectAttributes redirectAttributes, HttpSession session) {
    LoginResponseHandler githubInfo =
        loginApiManager.getProvider("GITHUB").getUserInfo(webClient, code);
    return url(session, redirectAttributes, githubInfo, GITHUB.name());
  }

  // 구글
  @GetMapping("login/oauth2/code/google")
  public String googleLogin(@RequestParam String code, Model model,
      RedirectAttributes redirectAttributes, HttpSession session) {
    LoginResponseHandler googleInfo =
        loginApiManager.getProvider("GOOGLE").getUserInfo(webClient, code);
    return url(session, redirectAttributes, googleInfo, GOOGLE.name());
  }

  // 네이버
  @GetMapping("/auth/naver/callback")
  public String naverLogin(@RequestParam String code, Model model,
      RedirectAttributes redirectAttributes, HttpSession session) {
    LoginResponseHandler naverInfo =
        loginApiManager.getProvider("NAVER").getUserInfo(webClient, code);
    return url(session, redirectAttributes, naverInfo, NAVER.name());
  }


  @GetMapping("auth/logout")
  public String logout(HttpSession session, @LoginUser MemberDto loginUser) throws Exception {
    session.invalidate();
    String id = String.valueOf(loginUser.getMemberNo());
    // SseEmitter 삭제
    emitterService.deleteEmitter(id);
    // redis 구독해제
    redisMessageService.removeSubscribe(id);
    return "redirect:/";
  }

  @GetMapping("auth/form")
  public String form(@LoginUser MemberDto user, Model model) throws Exception {
    model.addAttribute("user", user);
    return "auth/form";
  }


  // 닉네임 중복확인
  @GetMapping("checkUsername")
  public ResponseEntity<String> checkUsername(@RequestParam String username) {
    MemberDto member = memberService.getName(username);

    if (member != null) {
      // 이미 존재하는 닉네임인 경우
      return ResponseEntity.ok("duplicate");
    } else {
      // 존재하지 않는 닉네임인 경우
      return ResponseEntity.ok("available");
    }
  }

  private String url(HttpSession session, RedirectAttributes redirectAttributes,
      LoginResponseHandler info, String platform) {
    session.setAttribute("platform", platform);
    if (memberService.existsByEmail(info.getEmail())) {
      session.setAttribute("accessToken", info.getToken());
      session.setAttribute("loginUser", memberService.get(info.getEmail()));
    } else {
      redirectAttributes.addFlashAttribute("loginEmail", info.getEmail());
      redirectAttributes.addFlashAttribute("showModal", true);
    }
    return "redirect:/";
  }
}
