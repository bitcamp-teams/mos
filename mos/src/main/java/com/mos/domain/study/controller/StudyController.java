package com.mos.domain.study.controller;

import com.mos.domain.comment.dto.StudyCommentDto;
import com.mos.domain.comment.service.CommentService;
import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.member.dto.MemberStudyDto;
import com.mos.domain.study.dto.*;
import com.mos.domain.study.service.StudyLikeService;
import com.mos.domain.study.service.StudyService;
import com.mos.domain.wiki.service.WikiService;
import com.mos.global.auth.LoginUser;

import com.mos.global.storage.service.StorageService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study")
@Slf4j
public class StudyController implements InitializingBean {

  // 현재 스레드의 클래스를 파라미터로 받아서 로그 객체를 만든다.
  private final StudyService studyService;
  // 스터디에 파일저장 / 이미지 옵티마이징 따로 없으므로 변수 추가 없음
  private final CommentService commentService;
  private final WikiService wikiService;
  private final StudyLikeService studyLikeService;
  private final StorageService storageService;
  private String uploadDir;

  @Value("${ncp.ss.bucketname}")
  private String bucketName;


  @Override
  public void afterPropertiesSet() throws Exception {
    this.uploadDir = "study/";

    log.debug("uploadDir ={}", this.uploadDir);
    log.debug("bucketname ={}", this.bucketName);
  }


  @GetMapping("form")
  public String form(Model model) throws Exception {

    List<TagDto> tagList = studyService.getAllTags();

    model.addAttribute("study", StudyDto.builder().build());
    model.addAttribute("tagList", tagList);
    return "study/form";
  }

  @PostMapping("add")
  public String add(
      @LoginUser MemberDto loginUser,
      @Validated @ModelAttribute("study") StudyAddDto studyAddDto,
      BindingResult bindingResult,
      @RequestParam(value = "tags", required = false) List<Integer> tagNums,
      Model model,
      SessionStatus sessionStatus
  ) throws Exception {

    if (tagNums == null) {
      bindingResult.reject("tags", null, "태그를 선택해주세요.");
    }

    if (bindingResult.hasErrors()) {
      log.error("bindingResult={}", bindingResult);
      model.addAttribute("study", studyAddDto);
      model.addAttribute("tagList", studyService.getAllTags());
      return "/study/form";
    }

    List<TagDto> tagList = new ArrayList<>();
    for (int no : tagNums) {
      TagDto tag = TagDto.builder().tagNo(no).build();
      tagList.add(tag);
    }
    StudyDto studyDto = StudyDto.builder().memberNo(loginUser.getMemberNo()).method(studyAddDto.getMethod())
        .studyNo(studyAddDto.getStudyNo()).title(studyAddDto.getTitle()).introduction(studyAddDto.getIntroduction())
        .startDate(studyAddDto.getStartDate()).endDate(studyAddDto.getEndDate()).intake(studyAddDto.getIntake())
        .recruitmentDeadline(studyAddDto.getRecruitmentDeadline()).tagList(tagList).build();

    // MemberStudyDto 객체 생성 및 값 설정
    MemberStudyDto memberStudyDto = new MemberStudyDto();
    memberStudyDto.setMemberDto(loginUser);
    memberStudyDto.setStudyDto(studyDto);
    memberStudyDto.setStatus("S03-101");//스터디장

    studyService.add(studyDto);
    studyService.applyStudy(memberStudyDto);

    // 게시글을 등록하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

    return "redirect:list";
  }

  @GetMapping("view")
  public void view(@LoginUser MemberDto loginUser, @RequestParam int studyNo, Model model) throws Exception {

    try {
      model.addAttribute("loginUser", loginUser);
    } catch (Exception e) {
      //slightly quit
    }
    if (loginUser != null) {
      model.addAttribute("memberNo", loginUser.getMemberNo());
      StudyLikeStatDto studyLikeStatDto = new StudyLikeStatDto();
      studyLikeStatDto.setStudyNo(studyNo);
      studyLikeStatDto.setMemberNo(loginUser.getMemberNo());
      model.addAttribute("isLiked", studyLikeService.checked(studyLikeStatDto));
    } else {

      model.addAttribute("isLiked", null);
    }

    StudyDto studyDto = studyService.getByStudyNo(studyNo);
    studyService.updateHitCount(studyNo);
    if (studyDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("study", studyDto);

    List<StudyCommentDto> studyCommentDtoList = commentService.getStudyComments(studyNo);

    model.addAttribute("studyComments", studyCommentDtoList);

    //첫번째 wikiNo도 모델에 담아준다.
    try {

      model.addAttribute("firstWikiNo", wikiService.getFirstWikiNo(studyNo));

    } catch (Exception e) {
      //아직 위키가 없는 상태임
    }

  }

  @GetMapping("edit")
  public void edit(int studyNo, Model model) throws Exception {
    StudyDto studyDto = studyService.getByStudyNo(studyNo);
    if (studyDto == null) {
      throw new Exception("해당 스터디 번호가 존재하지 않습니다.");
    }
    model.addAttribute("study", studyDto);
  }

  @PostMapping("update")
  // 히든필드로 POST에 studyNo를 받는다!
  public String update(
          @Validated @ModelAttribute("study") StudyUpdateDto studyUpdateDtoDto,
          BindingResult bindingResult,
          Model model,
          HttpSession session,
          SessionStatus sessionStatus
  ) throws Exception {

    if (bindingResult.hasErrors()) {
      model.addAttribute("study", studyUpdateDtoDto);
      return "/study/edit?studyNo=" + studyUpdateDtoDto.getStudyNo();
    }

    StudyDto studyDto = StudyDto.builder()
            .studyNo(studyUpdateDtoDto.getStudyNo())
            .title(studyUpdateDtoDto.getTitle())
            .method(studyUpdateDtoDto.getMethod())
            .intake(studyUpdateDtoDto.getIntake())
            .recruitmentDeadline(studyUpdateDtoDto.getRecruitmentDeadline())
            .introduction(studyUpdateDtoDto.getIntroduction())
            .build();

    studyService.update(studyDto);
    StudyDto result = studyService.getByStudyNo(studyDto.getStudyNo());

    model.addAttribute("study", result);

    // 게시글을 변경하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

    // return "view?studyNo=" + studyDto.getStudyNo();
    return "redirect:view?studyNo=" + studyDto.getStudyNo();
  }

  @GetMapping("delete")
  public String delete(HttpSession session, int studyNo) throws Exception {
    // TODO 스터디장만 삭제 권한 있고,
    //  연결된 다른 참여회원이 존재할 경우 삭제 불가하며
    //  타인이 작성된 위키가 있는 경우는 삭제 불가
    studyService.deleteStudy(studyNo);

    List<AttachedFileDto> files = studyService.getAttachedFiles(studyNo);

    // TODO 연결된 위키도 전부 삭제함
    // wikiService.deleteAllByStudyNo(studyNo);

    for (AttachedFileDto file : files) {
      storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());
    }

    return "redirect:list";
  }

  @PostMapping("/applyStudy")
  @ResponseBody
  public Object applyStudy(
      @LoginUser MemberDto loginUser,
      @RequestParam int studyNo,
      @RequestParam String applyMsg,
      RedirectAttributes redirectAttributes
  ) {

    if (loginUser == null) {
      return "auth/login";
    }

    // 스터디 정보 가져오기
    StudyDto studyDto = studyService.getByStudyNo(studyNo);

    // 스터디가 존재하지 않는 경우 처리
    if (studyDto == null) {
      redirectAttributes.addFlashAttribute("message", "해당 스터디를 찾을 수 없습니다.");
      return "study/list";
    }

    // 스터디 상태 확인
    String stat = studyDto.getStat();
    if (stat == null || stat.equals("S01-103") || stat.equals("S01-104")) {
      redirectAttributes.addFlashAttribute("message", "이미 모집이 완료되었거나 종료된 스터디입니다.");
      return "study/list";
    }

    // MemberStudyDto 객체 생성 및 값 설정
    MemberStudyDto memberStudyDto = new MemberStudyDto();
    memberStudyDto.setMemberDto(loginUser);
    memberStudyDto.setStudyDto(studyDto);
    memberStudyDto.setStatus("S03-104");
    memberStudyDto.setApplyMsg(applyMsg); // applyMsg 설정

    try {
      studyService.applyStudy(memberStudyDto);
      redirectAttributes.addFlashAttribute("message", "스터디 신청이 완료되었습니다.");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", "스터디 신청에 실패했습니다.");
      // 에러 로깅 또는 처리
    }

    return "success";
  }


  @GetMapping("api/v1/list")
  public ResponseEntity<?> list(@PageableDefault(size = 20) Pageable pageable) {
    Page<StudyDto> studyList = studyService.list(pageable);
    return ResponseEntity.ok().body(studyList);
  }

  @GetMapping("list")
  public String list() {
    return "study/list";
  }

  @GetMapping("card")
  public String card() {
    return "study/card";
  }

  //  @GetMapping("main")
  //  public void main(Model model) {
  //    model.addAttribute("studyList", studyService.list());
  //  }

  @GetMapping("test")
  @ResponseBody
  public String test() {
    return "This is a test";
  }

  @GetMapping("/search")
  public String search(
      Model model,
      @RequestParam(value = "type", required = false, defaultValue = "") String type,
      @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page
  ) {
    try {
      int pageSize = 20; // 한 페이지당 보여줄 게시글 수

      Page<StudyDto> resultPage;

      if (type.isEmpty() && keyword.isEmpty()) {
        // 검색 조건이 없는 경우 전체 게시글 조회
        Pageable pageable = PageRequest.of(page - 1, pageSize); // 페이지 인덱스는 0부터 시작하므로 1을 빼줍니다.
        resultPage = studyService.list(pageable);
      } else {
        // 검색 조건에 따라 게시글 검색
        resultPage = studyService.searchByTypeAndKeyword(type, keyword, PageRequest.of(page - 1, pageSize));
      }

      List<StudyDto> studyList = resultPage.getContent(); // 현재 페이지의 게시글 목록
      int totalPages = resultPage.getTotalPages(); // 전체 페이지 수

      model.addAttribute("studyList", studyList);
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", totalPages);
      model.addAttribute("type", type);
      model.addAttribute("keyword", keyword);

      return "study/search";
    } catch (Exception e) {
      //      log.error("Error occurred during study search", e);
      model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
      return "error-page";
    }
  }


  @GetMapping("file/delete")
  public String fileDelete(@LoginUser MemberDto loginUser, int fileNo) throws Exception {

    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    AttachedFileDto file = studyService.getAttachedFile(fileNo);
    if (file == null) {
      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
    }

    int writerNo = studyService.getByStudyNo(file.getStudyNo()).getMemberNo();
    if (writerNo != loginUser.getMemberNo()) {
      throw new Exception("권한이 없습니다.");
    }

    studyService.deleteAttachedFile(fileNo);

    storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());

    return "redirect:/study/view?no=" + file.getStudyNo();
  }


  @PostMapping("file/upload")
  @ResponseBody
  public Object fileUpload(@LoginUser MemberDto loginUser, MultipartFile[] files, Model model) throws Exception {
    // NCP Object Storage에 저장한 파일의 이미지 이름을 보관할 컬렉션을 준비한다.
    ArrayList<AttachedFileDto> attachedFiles = new ArrayList<>();

    if (loginUser == null) {
      // 로그인 하지 않았으면 빈 목록을 보낸다.
      return attachedFiles;
    }

    // 클라이언트가 보낸 멀티파트 파일을 NCP Object Storage에 업로드한다.
    for (MultipartFile file : files) {
      if (file.getSize() == 0) {
        continue;
      }
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      attachedFiles.add(AttachedFileDto.builder().filePath(filename).build());
    }

    // 업로드한 파일 목록을 세션에 보관한다.
    model.addAttribute("attachedFiles", attachedFiles);

    // 클라이언트에서 이미지 이름을 가지고 <img> 태그를 생성할 수 있도록
    // 업로드한 파일의 이미지 정보를 보낸다.
    return attachedFiles;
  }


}
