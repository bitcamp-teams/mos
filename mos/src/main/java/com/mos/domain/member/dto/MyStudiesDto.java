package com.mos.domain.member.dto;

import lombok.Data;

@Data
public class MyStudiesDto {

  int id;
  String memberName;
  String memberPhoto;
  int memberNo;
  int studyNo;
  String status;
  String message;
}
