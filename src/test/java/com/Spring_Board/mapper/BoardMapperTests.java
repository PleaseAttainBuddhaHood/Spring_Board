package com.Spring_Board.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageHandler;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTests 
{
	@Autowired
	private BoardMapper mapper;
	

	// 등록
	@Test
	public void testInsert()
	{
		BoardVO board = new BoardVO();
		board.setTitle("새 글");
		board.setContent("새 내용");
		board.setWriter("뉴비");
		
		mapper.insert(board);
		
		log.info(board);
	}
	
	
	@Test
	public void testInsertSelectKey()
	{
		BoardVO board = new BoardVO();
		board.setTitle("셀렉트키 제목");
		board.setContent("셀렉트키 내용");
		board.setWriter("뉴비");
		
		mapper.insertSelectKey(board);
		
		log.info(board);
	}
	
	
	
	// 조회
	@Test
	public void testGetList()
	{
		mapper.getList().forEach(board -> log.info(board));
	}
	
	
	@Test
	public void testRead()
	{
		BoardVO board = mapper.read(5L);
		log.info("1 : " + board);
	}
	
	
	@Test
	public void testPaging()
	{
		PageHandler ph = new PageHandler();
		
		ph.setCurrentPage(3);
		ph.setPageRowSize(10);
		
		List<BoardVO> list = mapper.getListWithPaging(ph);
		
		list.forEach(board -> log.info(board));
	}
	
	
	@Test
	public void testSearch()
	{
		PageHandler ph = new PageHandler();
		
		ph.setSearchType("TC");
		ph.setSearchKeyword("수정된");
		
		List<BoardVO> list = mapper.getListWithPaging(ph);
		
		list.forEach(board -> log.info(board));
	}
	
	
	
	// 수정
	@Test
	public void testUpdate()
	{
		BoardVO board = new BoardVO();
		board.setBno(5L);
		board.setTitle("수정된 제목");
		board.setContent("수정된 내용");
		board.setWriter("수정된 작성자");
		
		log.info("수정된 개수 : " + mapper.update(board));
	}

	
	// 삭제
	@Test
	public void testDelete()
	{
		log.info("삭제 개수 : " + mapper.delete(6L));
	}
}
