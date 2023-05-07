package com.Spring_Board.mapper;

import java.util.List;

import com.Spring_Board.domain.BoardAttachVO;

public interface BoardAttachMapper 
{
	// 등록
	public void insert(BoardAttachVO vo);

	// 조회
	public List<BoardAttachVO> findByBno(Long bno);
	public List<BoardAttachVO> getOldFiles(); // '어제' 등록된 모든 첨부 파일 목록 조회
	
	// 삭제
	public void delete(String uuid);
	public void deleteAll(Long bno);
	
}
