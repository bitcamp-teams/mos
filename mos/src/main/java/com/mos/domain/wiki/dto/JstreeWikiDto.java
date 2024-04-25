package com.mos.domain.wiki.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JstreeWikiDto {
  @JsonProperty(value="id")
  int wikiNo;

  @JsonProperty(value="parent")
  int parentWikiNo;

  @JsonProperty(value="text")
  String title;

}
