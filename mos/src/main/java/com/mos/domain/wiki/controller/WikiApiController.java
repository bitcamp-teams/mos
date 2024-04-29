package com.mos.domain.wiki.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.global.auth.LoginUser;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  //조회 (모든 유저가 다 가능함)
  @GetMapping("")
  public List<JstreeWikiDto> listTitle(@RequestParam int studyNo) {
    log.debug("api/wiki: list");
    // 제목 트리를 adjacency list 형태로 전부 가져옴 (JSON)
    return wikiApiService.getWikiTitleTree(studyNo);
  }

  // TODO: 권한 검증
  //추가
  @PostMapping("")
  public JstreeWikiDto createWiki(@LoginUser MemberDto loginUser, @RequestBody JstreeWikiDto jstreeWikiDto) {
    // jstreeWikiDto를 파싱하여 addWiki 서비스를 수행함
    jstreeWikiDto.setMemberNo(loginUser.getMemberNo());
    log.debug("api/wiki: post");
    log.debug(jstreeWikiDto);
    //클라에서 바로 노드를 만드는 것이 아니라, DB에 잘 추가 됐으면 그것을 클라가 로드함.
    //추가하고 정상이면 id(wiki_no) 반환한다.
    int wikiNo = wikiApiService.addNode(jstreeWikiDto);
    //id, parent, title, position 리턴해준다.
    return wikiApiService.getNodeByWikiNo(wikiNo);
  }

  //일부 항목 업데이트(수정)
  @PatchMapping("")
  public String updateWiki() {
    log.debug("api/wiki: patch");
    return "updateWiki Called";
  }

  @DeleteMapping("")
  public String deleteWiki() {
    log.debug("api/wiki: delete");
    return "deleteWiki Called";
  }

}
