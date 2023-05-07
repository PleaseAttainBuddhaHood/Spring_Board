package com.Spring_Board.service;

import java.util.List;

import com.Spring_Board.domain.BoardAttachVO;
import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageHandler;

public interface BoardService 
{
	// 등록
	public void register(BoardVO board);
	
	
	// 조회
	public BoardVO get(Long bno); 	
	public List<BoardVO> getList(PageHandler ph); 
	public int getTotal(PageHandler ph);
	public List<BoardAttachVO> getAttachList(Long bno);
	
	
	// 수정
	public boolean modify(BoardVO board);
	
	
	// 삭제
	public boolean remove(Long bno);
}
