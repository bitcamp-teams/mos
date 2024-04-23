package com.mos.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class MemberJoinDto implements Serializable {
    @Email @NotBlank
    private String email;
    @NotBlank
    @Size(min = 3)
    @JsonAlias({"userName", "name"})
    private String name;
    private String belong;
    private String career;
    private String jobGroup;
    private String platform;

}
