package com.Spring_Board.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO 
{
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private PageHandler ph;
	
	
	public PageDTO(PageHandler ph, int totalPage)
	{
		this.ph = ph;
		this.total = totalPage;
		
		this.endPage = (int)(Math.ceil(ph.getCurrentPage() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		
		int finalEndPage = (int)(Math.ceil((double)totalPage / ph.getPageRowSize()));
		
		
		if(finalEndPage < this.endPage)
		{
			this.endPage = finalEndPage;
		}
		
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < finalEndPage;
	}
}
