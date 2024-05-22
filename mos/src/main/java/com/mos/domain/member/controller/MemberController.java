package com.mos.domain.member.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.dto.WikiCommentDto;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberEditDto;
import com.mos.domain.member.dto.MemberJoinDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.member.dto.MyStudiesDto;
import com.mos.domain.member.dto.MyStudiesUpdateDto;
import com.mos.domain.member.dto.UpdateFavoritesDto;
import com.mos.domain.member.service.impl.DefaultMemberService;
import com.mos.domain.study.dto.StudyDto;
import com.mos.domain.study.service.impl.DefaultStudyService;
import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.auth.LoginUser;
import com.mos.global.storage.service.StorageService;

import java.lang.reflect.Member;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController implements InitializingBean {

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

// 참여한 스터디목록 페이지
@GetMapping("mystudy")
public String getMyStudy(@LoginUser MemberDto loginUser, Model model, int page) {
    int memberNo = loginUser.getMemberNo();
    Pageable pageable = PageRequest.of(page - 1, 5);

    Page<MemberStudyDto> myStudies = memberService.findMyStudies(memberNo, pageable);
    System.out.println("myStudies = " + myStudies);

    model.addAttribute("memberStudyList", myStudies);
    return "member/mystudy";
}

@GetMapping("likedStudies")
public String findLikedStudies(@LoginUser MemberDto loginUser, Model model, int page) {
    Pageable pageable = PageRequest.of(page - 1, 5);
    int memberNo = loginUser.getMemberNo();
    Page<StudyDto> likedStudies = memberService.findLikedStudiseByNo(memberNo, pageable);
    model.addAttribute(likedStudies);
    return "/member/likedstudies";
}

  /**
   * 스터디장일 경우 가입 신청, 멤버 관리를 위한 컨트롤러
   * @param studyNo 스터디 번호
   * @param loginUser 로그인 유저 정보
   * @return 해당 스터디 멤버 리스트
   */
  @GetMapping("studyManagement")
  public ResponseEntity<Page<MyStudiesDto>> studyManagement(
      int studyNo,
      @LoginUser MemberDto loginUser,
      @PageableDefault(size = 5) Pageable page) {
    int memberNo = loginUser.getMemberNo();
    return ResponseEntity.ok().body(memberService.findListByStudyNo(studyNo, memberNo, page));
  }

  @PatchMapping("updateStatus")
  public ResponseEntity<?> updateStatus(@RequestBody MyStudiesUpdateDto updateDto) {
    log.debug("updateDto = {}", updateDto);
    memberService.updateStatus(updateDto);
    return ResponseEntity.ok().build();
  }


  // 회원 정보 조회 페이지
  @GetMapping("edit")
  public String editMemberForm(Model model, @LoginUser MemberDto loginUser) {

    if (loginUser == null) {
      return "auth/login";
    }

    MemberDto member = memberService.getNo(loginUser.getMemberNo());
    model.addAttribute("memberDto", member);
    return "member/editProfile";
  }


  // 회원 정보 수정
  @PostMapping("update")
  public String updateMember(@Validated @ModelAttribute("memberDto") MemberEditDto memberDto,
      BindingResult bindingResult,
      MultipartFile memberPhoto,
      Model model,
      @LoginUser MemberDto loginUser) throws Exception {

    if (loginUser == null) {
      return "auth/login";
    }

    System.out.println("memberDto = " + memberDto);
    System.out.println("bindingResult = " + bindingResult);

    String newUserName = memberDto.getUserName();
    String originalUserName = loginUser.getUserName();

    // 새로운 유저네임이 비어있으면 기존 유저네임으로 설정
    if (newUserName == null || newUserName.trim().isEmpty()) {
      memberDto.setUserName(originalUserName);
    }

    // 새로운 유저네임이 기존에 존재하는 유저네임인지 확인
    if (!originalUserName.equals(newUserName) && memberService.existsByUserName(newUserName)) {
      bindingResult.rejectValue("userName", "duplicate", "이미 존재하는 유저네임입니다.");
    }

    // 유효성 검사 실패 시 editForm 뷰로 이동
    if (bindingResult.hasErrors()) {
      memberDto.setPhoto(loginUser.getPhoto());
      model.addAttribute("memberDto", memberDto);
      return "member/editProfile";
    }

    if (memberPhoto.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, memberPhoto);
      memberDto.setPhoto(filename); // 파일 이름 또는 경로를 저장
      storageService.delete(this.bucketName, this.uploadDir, loginUser.getPhoto());
    } else {
      memberDto.setPhoto(loginUser.getPhoto());
    }

    memberDto.setMemberNo(loginUser.getMemberNo());

    MemberDto updateMember = MemberDto.builder()
        .memberNo(memberDto.getMemberNo())
        .email(memberDto.getEmail())
        .photo(memberDto.getPhoto())
        .biography(memberDto.getBiography())
        .belong(memberDto.getBelong())
        .userName(memberDto.getUserName())
        .career(memberDto.getCareer())
        .socialLink(memberDto.getSocialLink())
        .build();

    memberService.update(updateMember);
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

  @GetMapping("myWikiList")
  public String getMyWiki(@LoginUser MemberDto loginUser, Model model,
                          @RequestParam(name = "page", defaultValue = "1") int page) {
    int memberNo = loginUser.getMemberNo();

    Pageable pageable = PageRequest.of(page - 1, 10);

    Page<WikiDto> myWiki = memberService.findMyWiki(memberNo, pageable);

    if (myWiki != null) {
      model.addAttribute("memberWikiList", myWiki);
      model.addAttribute("totalPages", myWiki.getTotalPages());
    } else {
      model.addAttribute("errorMessage", "댓글 목록을 불러올 수 없습니다.");
    }

    return "member/myWikiList";
  }

  @GetMapping("myStudyCommentList")
  public String getMyStudyComment(@LoginUser MemberDto loginUser, Model model,
                                  @RequestParam(name = "page", defaultValue = "1") int page) {
    int memberNo = loginUser.getMemberNo();

    // 페이지 요청 처리를 위한 Pageable 객체 생성
    Pageable pageable = PageRequest.of(page - 1, 10);

    // 회원 번호를 이용하여 회원의 스터디 댓글 목록 조회
    Page<StudyCommentDto> myStudyComment = memberService.findMyStudyComment(memberNo, pageable);

    // 조회된 결과가 null이 아니면 모델에 추가
    if (myStudyComment != null) {
      model.addAttribute("memberStudyCommentList", myStudyComment);
      model.addAttribute("totalPages", myStudyComment.getTotalPages());
    } else {
      // 조회된 댓글이 없는 경우 적절한 처리 (예: 에러 메시지 설정)
      model.addAttribute("errorMessage", "댓글 목록을 불러올 수 없습니다.");
    }

    return "member/myStudyCommentList";
  }


  @GetMapping("myWikiCommentList")
  public String getMyWikiComment(@LoginUser MemberDto loginUser, Model model,
                                 @RequestParam(name = "page", defaultValue = "1") int page) {
    int memberNo = loginUser.getMemberNo();

    Pageable pageable = PageRequest.of(page - 1, 10);

    // 회원 번호를 이용하여 회원의 위키 댓글 목록을 조회
    Page<WikiCommentDto> myWikiComment = memberService.findMyWikiComment(memberNo, pageable);
    System.out.println("myWikiComment = " + myWikiComment);
    if (myWikiComment != null) {
      model.addAttribute("memberWikiCommentList", myWikiComment);
      model.addAttribute("totalPages", myWikiComment.getTotalPages());
    } else {
      model.addAttribute("errorMessage", "댓글 목록을 불러올 수 없습니다.");
    }

    return "member/myWikiCommentList";
  }

  @PostMapping("/addFavorites")
  public ResponseEntity<?> addFavorites(@RequestBody UpdateFavoritesDto updateFavoritesDto) {
     memberService.addFavorites(updateFavoritesDto);
     return ResponseEntity.ok().build();
  }

}
