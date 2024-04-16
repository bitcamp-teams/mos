package com.mos.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
  private int studyNo;
  private int tagNo;
  private String name;
  private String path;
}
