package com.mos.domain.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {
    private String email;
    private String name;
    private String belong;
    private String career;
    private String jobGroup;
}
 