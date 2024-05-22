package com.mos.global.common;

import lombok.Getter;

import java.text.MessageFormat;

@Getter
public enum ErrorCode {
  // server error
  ERROR("-10", "Internal Server Error"),

  BAD_REQUEST("-20", "Bad Request"),
  NOT_FOUND("-21", "Not Found"),

  UPDATE_ERROR("-30", "수정 실패"),
  DELETE_ERROR("-31", "삭제 실패"),
  INSERT_ERROR("-32", "저장 실패"),
  INSERT_ERROR2("-33", "이미 저장되어있습니다."),

  // message
  MESSAGE_SEND_ERROR("-60", "전송에 실패하였습니다."),
  MESSAGE_CODE_NOT_AVAILABLE("-61", "유효하지 않은 메시지코드입니다."),
  MESSAGE_TYPE_NOT_SAME("-62", "메시지 타입이 일치하지 않습니다."),

  // file error
  FILE_UPLOAD_ERROR("-70", "파일업로드에 실패하였습니다."),
  FILE_DOWNLOAD_ERROR("-71", "파일다운로드에 실패하였습니다."),
  FILE_NOT_FOUND_ERROR("-72", "파일을 찾을 수 없습니다."),
  FILE_NAME_ERROR("-73", "파일명에 부적합 문자가 포함되어 있습니다."),

  // validation error
  VALIDATION_ERROR("-80", "필수값이 없습니다."),

  // token error
  TOKEN_NONE_ERROR("-90", "토큰 정보가 없습니다.!"),
  TOKEN_VALID_ERROR("-91", "유효하지 않은 토큰입니다."),
  TOKEN_EXPIRE_ERROR("-92", "기간이 만료 된 토큰입니다."),

  DB_ERROR("-99", "무결성 제약 조건 위반"),

  EXIST_DATA("-100", "데이터가 존재합니다."),
  NOT_EXIST_DATA("-101", "데이터를 찾을수 없습니다."),
  DATA_IN_USE("-102", "사용중인 데이터 입니다."),
  REGISTERED_DATA("-103", "등록된 데이터 입니다."),

  USER_NOT_FOUND_DATA("-200", "이용자 정보가 없습니다.");

  private String code = "-10";
  private String message = "Internal Server Error";

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage(Object... args) {
    return MessageFormat.format(this.message, args);
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
