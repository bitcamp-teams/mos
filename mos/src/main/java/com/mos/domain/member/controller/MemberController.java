package com.mos.domain.member.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.service.impl.DefaultStudyService;
import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.auth.LoginUser;
import com.mos.global.storage.service.StorageService;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController implements InitializingBean {

  private final Log log = LogFactory.getLog(Thread.currentThread().getClass());
  private final DefaultMemberService memberService;
  private final DefaultStudyService studyService;
  private final StorageService storageService;
  private final WikiService wikiService;
  private String uploadDir;

  @Value("${ncp.ss.bucketname}")
  private String bucketName;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.uploadDir = "member/";

    log.debug(String.format("uploadDir: %s", this.uploadDir));
    log.debug(String.format("bucketname: %s", this.bucketName));
  }

  @GetMapping("findByEmail")
  public void findByEmail(String email) throws Exception {
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
    return "redirect:/";
  }

    @GetMapping("dashboard")
    public void viewDashboard(int no, Model model) throws Exception {
        MemberDto member = memberService.getNo(no);
        if (member == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }
        model.addAttribute("member", member);
    }

  @GetMapping("mystudy")
  public String getMyStudy  (@LoginUser MemberDto loginUser, Model model) throws Exception {

    // 로그인한 회원의 번호 가져오기
    int memberNo = loginUser.getMemberNo();

    MemberDto member = memberService.getNo(memberNo);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    model.addAttribute("member", member);

    // 회원 번호를 이용하여 회원의 스터디 목록을 조회
    List<MemberStudyDto> myStudy = memberService.findMyStudies(memberNo);
    if (myStudy == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    model.addAttribute("memberStudyList", myStudy);
    return "member/mystudy";
  }

  @GetMapping("viewMystudy")
  public void viewMyStudy(int studyNo, Model model, @LoginUser MemberDto loginUser) throws Exception {


    int memberNo = loginUser.getMemberNo();

    MemberDto member = memberService.getNo(memberNo);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    model.addAttribute("member", member);

    List<MemberStudyDto> myStudy = memberService.findMyStudies(memberNo);
    if (myStudy == null || myStudy.isEmpty()) {
      throw new Exception("회원이 참여하고 있는 스터디가 없습니다.");
    }

    StudyDto studyDto = studyService.getByStudyNo(studyNo);
    studyNo = studyDto.getStudyNo();

    model.addAttribute("study", studyDto);

    // 스터디 번호를 이용하여 회원의 스터디 상세보기 조회
    List<MemberStudyDto> editMyStudy = memberService.viewMyStudies(studyNo);
    if (myStudy == null) {
      throw new Exception("스터디 번호가 유효하지 않습니다.");
    }

    model.addAttribute("memberStudyView", editMyStudy);
  }

  // 회원 정보 조회 페이지
  @GetMapping("edit")
  public String editMemberForm(Model model, @LoginUser MemberDto loginUser) {

    if (loginUser == null) {
      return "auth/login";
    }

    MemberDto member = memberService.getNo(loginUser.getMemberNo());
    model.addAttribute("member", member);
    return "member/editProfile";
  }


  // 회원 정보 수정
  @PostMapping("update")
  public String updateMember(@Valid MemberDto member,
      MultipartFile memberPhoto,
      BindingResult bindingResult,
      @LoginUser MemberDto loginUser) throws Exception {

    if (loginUser == null) {
      return "auth/login";
    }

    String newUserName = member.getUserName();
    String originalUserName = loginUser.getUserName();

    // 새로운 유저네임이 비어있으면 기존 유저네임으로 설정
    if (newUserName == null || newUserName.trim().isEmpty()) {
      member.setUserName(originalUserName);
    }

    // 새로운 유저네임이 기존에 존재하는 유저네임인지 확인
    if (!originalUserName.equals(newUserName) && memberService.existsByUserName(newUserName)) {
      bindingResult.rejectValue("userName", "duplicate", "이미 존재하는 유저네임입니다.");
    }

    // 유효성 검사 실패 시 editForm 뷰로 이동
    if (bindingResult.hasErrors()) {
      return "member/editProfile";
    }

    if (memberPhoto.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, memberPhoto);
      member.setPhoto(filename); // 파일 이름 또는 경로를 저장
      storageService.delete(this.bucketName, this.uploadDir, loginUser.getPhoto());
    } else {
      member.setPhoto(loginUser.getPhoto());
    }

    member.setMemberNo(loginUser.getMemberNo());

    memberService.update(member);
    return "redirect:/member/edit";
  }


  @PostMapping("/withdraw")
  public String withdrawMember(HttpSession session) throws Exception {
    MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
    if (loginUser != null) {

      // 회원 탈퇴 처리
      memberService.withdraw(loginUser.getMemberNo());

      String filename = loginUser.getPhoto();

      if (filename != null) {
        storageService.delete(this.bucketName, this.uploadDir, loginUser.getPhoto());
      }
      // 세션 무효화 (로그아웃)
      session.invalidate();
    }
    return "redirect:/";
  }



  @GetMapping("myWriteCommentList")
  public String getMyWiki(@LoginUser MemberDto loginUser, Model model, HttpSession session) throws Exception {

//    user = (MemberDto) session.getAttribute("loginUser");

    int memberNo = loginUser.getMemberNo();

    MemberDto member = memberService.getNo(memberNo);
//    System.out.println("member = " + member);
    if (member == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    model.addAttribute("member", member);

    // 회원 번호를 이용하여 회원의 위키 목록을 조회
    List<WikiDto> myWiki = memberService.findMyWiki(memberNo);
//    System.out.println("myWiki = " + myWiki);
    if (myWiki == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    // 회원 번호를 이용하여 회원의 스터디 댓글 목록을 조회
    List<StudyCommentDto> myStudyComment = memberService.findMyStudyComment(memberNo);
    if (myStudyComment == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    // 회원 번호를 이용하여 회원의 스터디 댓글 목록을 조회
    List<WikiCommentDto> myWikiComment = memberService.findMyWikiComment(memberNo);
    if (myWikiComment == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    model.addAttribute("memberWikiList", myWiki);
    model.addAttribute("memberStudyCommentList", myStudyComment);
    model.addAttribute("memberWikiCommentList", myWikiComment);

    return "member/myWriteCommentList";
  }

}
