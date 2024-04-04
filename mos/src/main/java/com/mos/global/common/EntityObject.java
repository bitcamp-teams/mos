package com.mos.global.common;

import com.mos.global.common.paging.Paging;
import lombok.Getter;
import lombok.Setter;

/**
 * 	공통 응답 데이터 클래스
 * 		- 필요하면 상속받아 사용
 *
 */
@Getter
@Setter
public abstract class EntityObject /* implements Serializable */{
//	private static final long serialVersionUID = -6168799228959824230L;

	private String sortKey;
	private String sortType;
	private int pageNo = -1;
	private int pageSize = -1;
	private Paging paging;
}
