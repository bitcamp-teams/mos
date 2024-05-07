package com.mos.domain.wiki.controller;

import com.mos.domain.member.dto.MemberDto;
import com.mos.domain.study.dto.StudyLikeStatDto;
import com.mos.domain.wiki.dto.WikiLikeStatDto;
import com.mos.domain.wiki.service.WikiLikeService;
import com.mos.global.auth.LoginUser;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wiki/like")
public class WikiLikeController {

    private static final Logger log = LoggerFactory.getLogger(Thread.currentThread().getClass());
    private final WikiLikeService wikiLikeService;


    @PostMapping("toggleLike")
    public ResponseEntity<Map<String, Object>> toggleLike(@LoginUser MemberDto loginUser, @RequestBody WikiLikeStatDto wikiLikeStatDto) {

        if (loginUser == null) {
            return ResponseEntity.badRequest().build();
        }
        wikiLikeStatDto.setMemberNo(loginUser.getMemberNo());

        Map<String, Object> response = new HashMap<>();

        // checked 메서드 대신 직접 조회
        int isLiked = wikiLikeService.checked(wikiLikeStatDto);
        response.put("checked", isLiked);

        if (isLiked == 0) {
            // 좋아요를 누르지 않은 경우
            wikiLikeService.addLike(wikiLikeStatDto);
        } else {
            // 이미 좋아요를 누른 경우
            wikiLikeService.deleteLike(wikiLikeStatDto);
        }
        // 좋아요 수 업데이트
        int updatedLikesCount = wikiLikeService.countLikesByWikiNo(wikiLikeStatDto.getWikiNo());
        response.put("likesCount", updatedLikesCount);

        // 클라이언트에게 성공적인 요청을 알리는 상태 코드와 메시지를 반환
        response.put("message", "Success");
        return ResponseEntity.ok(response);

    }

}
