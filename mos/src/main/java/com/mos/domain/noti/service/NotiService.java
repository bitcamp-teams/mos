package com.mos.domain.noti.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mos.domain.noti.dto.NotiAddDto;
import com.mos.domain.noti.dto.NotiDto;
import com.mos.domain.noti.repository.NotiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotiService {

  private final NotiRepository notiRepository;

  public void add(NotiAddDto notiDto) {
    System.out.println("notiDto = " + notiDto);
    notiRepository.add(notiDto);
  }

  public String list(int id) {
    List<NotiDto> list = notiRepository.findById(id);

    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create();

    return gson.toJson(list);
  }
}
