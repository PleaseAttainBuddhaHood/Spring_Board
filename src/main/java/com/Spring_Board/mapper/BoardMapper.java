package com.Spring_Board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageHandler;

public interface BoardMapper 
{
	// 등록
	public void insert(BoardVO board);
	public void insertSelectKey(BoardVO board); // bno.nextval
	
	
	// 조회
	public List<BoardVO> getList();
	public List<BoardVO> getListWithPaging(PageHandler ph);
	public int getTotalCount(PageHandler ph);
	public BoardVO read(Long bno);
	
	
	// 수정
	public int update(BoardVO board);	
	public void updateReplyCnt(@Param("bno")Long bno, @Param("amount")int amount);
	
	
	// 삭제
	public int delete(Long bno);
}
