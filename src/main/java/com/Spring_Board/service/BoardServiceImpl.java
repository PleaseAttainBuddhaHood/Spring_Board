package com.Spring_Board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService 
{
	
	private BoardMapper mapper;
	
	
	// 등록
	@Override
	public void register(BoardVO board)
	{
		log.info("등록" + board);
		
		mapper.insertSelectKey(board);
	}

	
	
	//조회
	@Override
	public BoardVO get(Long bno) 
	{
		log.info("게시물 조회" + bno);

		return mapper.read(bno);
	}


	@Override
	public List<BoardVO> getList(PageHandler ph) 
	{
		log.info("페이징 전체 목록" + ph);

		return mapper.getListWithPaging(ph);
	}

	
	@Override
	public int getTotal(PageHandler ph) 
	{
		log.info("전체 게시글 수 조회");
		
		return mapper.getTotalCount(ph);
	}
	
	
	// 수정
	@Override
	public boolean modify(BoardVO board) 
	{
		log.info("수정" + board);

		return mapper.update(board) == 1;
	}

	
	
	// 삭제
	@Override
	public boolean remove(Long bno) 
	{
		log.info("삭제" + bno);

		return mapper.delete(bno) == 1;
	}



}
