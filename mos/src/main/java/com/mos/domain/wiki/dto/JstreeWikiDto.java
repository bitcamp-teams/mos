package com.mos.domain.wiki.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mos.domain.wiki.serializer.ParentWikiNoSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JstreeWikiDto {
  @JsonProperty(value="id")
  int wikiNo;

  @JsonProperty(value="parent")
      @JsonSerialize(using = ParentWikiNoSerializer.class)
  int parentWikiNo;

  @JsonProperty(value="text")
  String title;

}
