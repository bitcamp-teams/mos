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

  // jstree에서 각 노드를 구별하는 키를 id라고 한다.
  @JsonProperty(value = "id")
  private int wikiNo;

  // jstree에서 부모 노드의 키를 parent라고 한다.
  // 최상위 노드의 아이디는 '#' 으로 정의되어 있다.
  // 이를 만족시키기 위해 따로 시리얼라이저를 정의했다.
  @JsonProperty(value = "parent")
  @JsonSerialize(using = ParentWikiNoSerializer.class)
  private int parentWikiNo;

  // jstree에서 node에 표시되는 각 텍스트는 text라는 이름을 갖는다.
  @JsonProperty(value = "text")
  private String title;

  //jstree에서 순서를 position이라는 이름을 쓴다.
  @JsonProperty(value = "position")
  private int ordr;

  private int memberNo;
  private int studyNo;

}
