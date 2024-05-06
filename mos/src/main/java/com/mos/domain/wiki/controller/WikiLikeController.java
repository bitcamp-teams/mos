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

        int checked = wikiLikeService.checked(wikiLikeStatDto);
        response.put("checked", checked);

        if (checked != 1) {
            wikiLikeService.addLike(wikiLikeStatDto);
        } else {
            wikiLikeService.deleteLike(wikiLikeStatDto);
        }

        // 클라이언트에게 성공적인 요청을 알리는 상태 코드와 메시지를 반환
        response.put("message", "Success");
        return ResponseEntity.ok(response);

        }

    }
