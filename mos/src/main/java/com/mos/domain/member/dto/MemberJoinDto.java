package com.mos.domain.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberJoinDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    private String belong;
    private String career;
    private String jobGroup;
    private String platform;

    @Override
    public String toString() {
        return "MemberJoinDto{" +
            "email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", belong='" + belong + '\'' +
            ", career='" + career + '\'' +
            ", jobGroup='" + jobGroup + '\'' +
            ", platform='" + platform + '\'' +
            '}';
    }
}
