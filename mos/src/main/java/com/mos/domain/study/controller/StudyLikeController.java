package com.mos.domain.study.controller;


import com.mos.domain.study.dto.StudyLikeStatDto;
import com.mos.domain.study.service.StudyLikeService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/study/like")
public class StudyLikeController {

    private final StudyLikeService studyLikeService;
    private final Log log = LogFactory.getLog(StudyLikeController.class);

    @PostMapping("toggleLike")
    public ResponseEntity<Map<String, Object>> toggleLike(@RequestBody StudyLikeStatDto studyLikeStatDto) {

        Map<String, Object> response = new HashMap<>();

        int checked = studyLikeService.checked(studyLikeStatDto);
        response.put("checked", checked);

        if (checked != 1) {
            studyLikeService.addLike(studyLikeStatDto);
        } else {
            studyLikeService.deleteLike(studyLikeStatDto);
        }

        // 클라이언트에게 성공적인 요청을 알리는 상태 코드와 메시지를 반환
        response.put("message", "Success");
        return ResponseEntity.ok(response);
    }
}
