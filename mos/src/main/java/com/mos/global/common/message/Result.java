package com.mos.global.common.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mos.global.common.paging.Paging;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.text.MessageFormat;

@Getter
@NoArgsConstructor
public class Result<T> {
	private String resultCode = "success";
	private String errorCode;
	private String errorMessage;
//	@JsonIgnoreProperties({"startRow","endRow"})
	private Paging pageInfo;
//	@JsonIgnoreProperties({"sortKey","sortType","pageNo","pageSize","paging"})
	private T resultData;


	public Result(String resultCode) {
		this.resultCode = resultCode;
	}

	public Result(T resultData) {
		this.resultData = resultData;
	}

	public Result(T resultData, Paging pageInfo) {
		this.resultData = resultData;
		this.pageInfo = pageInfo;
	}

	public Result(String resultCode, T resultData) {
		this.resultCode = resultCode;
		this.resultData = resultData;
	}

	public Result(String resultCode, T resultData, Paging pageInfo) {
		this.resultCode = resultCode;
		this.resultData = resultData;
		this.pageInfo = pageInfo;
	}

	public Result<T> setResultCode(String resultCode) {
		this.resultCode = resultCode;
		return this;
	}

	public Result<T> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}

	public Result<T> setResultData(T resultData) {
		this.resultData = resultData;
		return this;
	}

	public Result<T> setPageInfo(Paging pageInfo) {
		this.pageInfo = pageInfo;
		return this;
	}

	@Override
	public String toString() {
		return new GsonBuilder().serializeNulls().create().toJson(this);
	}
}
