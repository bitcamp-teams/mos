package com.mos.domain.wiki.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.study.dto.StudyLikeStatDto;
import com.mos.domain.wiki.dto.WikiDto;
import com.mos.domain.wiki.dto.WikiLikeStatDto;
import com.mos.domain.wiki.service.WikiLikeService;
import com.mos.global.auth.LoginUser;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mos.domain.wiki.dto.JstreeWikiDto;
import com.mos.domain.wiki.service.WikiApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
public class WikiApiController {

  private static final Log log = LogFactory.getLog(new Object() {
  }.getClass().getEnclosingClass());
  private final WikiApiService wikiApiService;
  private final WikiLikeService wikiLikeService;

  //조회 (모든 유저가 다 가능함)
  @GetMapping("")
  public List<JstreeWikiDto> listTitle(@RequestParam int studyNo) {
    log.debug("api/wiki: list");
    // 제목 트리를 adjacency list 형태로 전부 가져옴 (JSON)
    return wikiApiService.getWikiTitleTree(studyNo);
  }

  @GetMapping("/{wiki_no}")
  public WikiDto getWiki(@PathVariable("wiki_no") int wikiNo) {
    return wikiApiService.getWikiContent(wikiNo);
  }

  // TODO: 권한 검증
  //추가
  @PostMapping("")
  public JstreeWikiDto createWiki(@LoginUser MemberDto loginUser, @RequestBody JstreeWikiDto jstreeWikiDto) {
    // jstreeWikiDto를 파싱하여 addWiki 서비스를 수행함
    jstreeWikiDto.setMemberNo(loginUser.getMemberNo());
    //클라에서 바로 노드를 만드는 것이 아니라, DB에 잘 추가 됐으면 그것을 클라가 로드함.
    //추가하고 정상이면 id(wiki_no) 반환한다.
    int wikiNo = wikiApiService.addNode(jstreeWikiDto);
    //id, parent, title, position 리턴해준다.
    return wikiApiService.getNodeByWikiNo(wikiNo);
  }

  //TODO: 권한 검증
  //일부 항목 업데이트(수정)
  @PatchMapping("")
  public void updateWiki(@LoginUser MemberDto loginUser, @RequestBody JstreeWikiDto jstreeWikiDto) {
    //한개의 위키 데이터를 가져와서, wiki_no 기준으로 맞는 것을 patch 한다. (parent_wiki_no, title, ordr만 변경됨)
    wikiApiService.patchWikiByWikiNo(jstreeWikiDto);
  }

  //TODO: 권한 검증
  //DANGER! 항목 삭제 (Recursive)
  @DeleteMapping("")
  public void deleteWiki(@LoginUser MemberDto loginUser, @RequestBody JstreeWikiDto jstreeWikiDto) {
    log.debug("api/wiki: delete");
    wikiApiService.deleteWikiByWikiNo(jstreeWikiDto.getWikiNo());
  }


  @GetMapping("/isLiked")
  @ResponseBody
  public int isLiked(@LoginUser MemberDto user, @RequestParam int wikiNo) {
    if (user != null) {
      WikiLikeStatDto wikiLikeStatDto = new WikiLikeStatDto();
      wikiLikeStatDto.setWikiNo(wikiNo);
      wikiLikeStatDto.setMemberNo(user.getMemberNo());
      return wikiLikeService.checked(wikiLikeStatDto);
    }
    return 0; // 좋아요 하지 않은 상태
  }


}
