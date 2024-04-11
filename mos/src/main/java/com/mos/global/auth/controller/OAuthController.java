package com.mos.global.auth.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.service.MemberService;
import com.mos.global.auth.dto.KakaoDto;
import com.mos.global.auth.service.KakaoService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping("/auth")
public class OAuthController {

  private static final Log log = LogFactory.getLog(OAuthController.class);

  private final KakaoService kakaoService;

  @Autowired
  private final MemberService memberService;

  @Value("${github.clientId}")
  private String clientId;

  @GetMapping("login")
  public String login(Model model) {

    // 카카오 인증 URL 저장
    model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
    model.addAttribute("clientId", clientId);
    //log.debug(String.format("requestUrl : %s", model));

    return "auth/login";
  }

  @GetMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "redirect:/";
  }


  @GetMapping("/kakao/callback")
  public String callback(@RequestParam String code, MemberDto member, HttpServletResponse response,HttpSession session) throws Exception {
    KakaoDto kakaoInfo = kakaoService.getKakaoInfo(code);
    //System.out.println("이메일: " + kakaoInfo.getEmail());
    //System.out.println("닉네임: " + kakaoInfo.getNickname());
    member.setEmail(kakaoInfo.getEmail());
    System.out.println(member.getEmail());
    if (member != null) {
      System.out.println("로그인 성공!!!!!!!!");
      session.setAttribute("loginUser", member);
      return "redirect:/";
    }

    System.out.println("회원 정보가 없음!!!!!!!!");
    return "auth/signup";
  }

  @PostMapping("signup")
  public void signup(@RequestParam String username) {
    // 중복확인 완료한 후 가입을 진행하던 중
    // 그 시점에 같은 닉네임으로 가입을 완료한 사람이 있다면??

    /*MemberDto member = memberService.getName(username);

    if (memberService.getName(username) != null) {
      System.out.println("중복된 닉네임입니다.");
      return "auth/signup";
    }

    System.out.println("사용가능한 닉네임입니다!!");
    return "auth/signup";*/
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

  @PostMapping("signup2")
  public void signup2() {

  }
}
