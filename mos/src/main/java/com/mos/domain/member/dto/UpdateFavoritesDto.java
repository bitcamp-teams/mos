package com.mos.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
public class UpdateFavoritesDto {

    private int memberNo;
    private int studyNo;
    private boolean favorites;

}
