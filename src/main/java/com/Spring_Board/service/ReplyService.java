package com.Spring_Board.service;

import java.util.List;

import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.domain.ReplyPageDTO;
import com.Spring_Board.domain.ReplyVO;

public interface ReplyService 
{
	
	// 등록
	public int register(ReplyVO vo);
	
	
	// 조회
	public ReplyVO get(Long rno);
	public List<ReplyVO> getList(PageHandler ph, Long bno);
	public ReplyPageDTO getListPage(PageHandler ph, Long bno);
	
	
	// 수정
	public int modify(ReplyVO vo);
	
	
	// 삭제
	public int remove(Long rno);
	public int removeAll(Long bno);
}
