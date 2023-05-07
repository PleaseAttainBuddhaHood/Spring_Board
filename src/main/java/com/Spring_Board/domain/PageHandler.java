package com.Spring_Board.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageHandler 
{
	private int currentPage;
	private int pageRowSize;
	private String searchType;
	private String searchKeyword;
	
	public PageHandler()
	{
		this(1, 10);
	}
	
	
	public PageHandler(int currentPage, int pageRowSize)
	{
		this.currentPage = currentPage;
		this.pageRowSize = pageRowSize;
	}
	
	
	public String[] getSearchTypeArr()
	{
		return searchType == null ? new String[] {} : searchType.split("");
	}
	
	
	// 링크 생성 기능
	public String getListLink()
	{
		// 여러 개의 파라미터를 연결하여 URL 형태로 생성
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("currentPage", this.currentPage)
				.queryParam("pageRowSize", this.getPageRowSize())
				.queryParam("searchType", this.getSearchType())
				.queryParam("searchKeyword", this.getSearchKeyword());
		
		return builder.toUriString();
	}
}
