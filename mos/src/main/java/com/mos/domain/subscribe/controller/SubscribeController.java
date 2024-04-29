package com.mos.domain.subscribe.controller;

import com.mos.domain.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

  private final SubscribeService subscribeService;

  @GetMapping(value = "/subscribe/{memberNo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter subscribe(@PathVariable int memberNo,
      @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
      String lastEventId) {
    return subscribeService.subscribe(memberNo, lastEventId);
  }

}
