package com.Spring_Board.service;

import java.util.List;

import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageHandler;

public interface BoardService 
{
	// 등록
	public void register(BoardVO board);
	
	
	// 조회
	public BoardVO get(Long bno); 	// 특정 게시물
	public List<BoardVO> getList(PageHandler ph); // 전체 게시물
	public int getTotal(PageHandler ph);
	
	
	// 수정
	public boolean modify(BoardVO board);
	
	
	// 삭제
	public boolean remove(Long bno);
}
