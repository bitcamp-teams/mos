package com.mos.domain.githuboauth.vo;

import lombok.Getter;

@Getter
public class Member {
    private String name;
    private String email;
    private String platform;
    private String photo;
    private String createdDate;
    private String updatedDate;
    private String status;
    private int score;
    private String biograph;
    private String belong;
    private String career;
    private String jobGroup;
    private String socialLink;

    public String getEmail(){
        return this.email;
    }

}
