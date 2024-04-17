package com.mos.domain.member.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.service.impl.DefaultStudyService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final DefaultMemberService memberService;
  private final DefaultStudyService studyService;

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

    @GetMapping("mystudy")
    public String getMyStudy(Model model) throws Exception {


        MemberDto member = memberService.getNo(3);
        if (member == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }
        model.addAttribute("member", member);

        // 회원 번호를 이용하여 회원의 스터디 목록을 조회
        List<MemberStudyDto> myStudy = memberService.findMyStudies(3);
        if (myStudy == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }

        model.addAttribute("memberStudyList", myStudy);
        return "member/mystudy";
    }

    @GetMapping("mystudy-view")
    public void viewMyStudy(int studyNo, Model model) throws Exception {
        MemberDto member = memberService.getNo(3);
        if (member == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }
        model.addAttribute("member", member);

        StudyDto studyDto = studyService.getByStudyNo(studyNo);
        studyNo = studyDto.getStudyNo();

        model.addAttribute("study", studyDto);

        // 스터디 번호를 이용하여 회원의 스터디 목록을 조회
        List<MemberStudyDto> myStudy = memberService.viewMyStudies(studyNo);
        if (myStudy == null) {
            throw new Exception("스터디 번호가 유효하지 않습니다.");
        }

        model.addAttribute("memberStudyView", myStudy);
    }

}
