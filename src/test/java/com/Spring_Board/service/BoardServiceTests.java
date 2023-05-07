package com.Spring_Board.service;

import static org.junit.Assert.assertNotNull;

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
public class BoardServiceTests 
{
	@Autowired
	private BoardService service;
	
	

	// 등록
	@Test
	public void testRegister()
	{
		BoardVO board = new BoardVO();
		board.setTitle("새 글");
		board.setContent("새 내용");
		board.setWriter("뉴비");
		
		service.register(board);
		
		log.info("생성된 게시물 번호 : " + board.getBno());
	}
	
	
	
	// 조회
	@Test
	public void testExist()
	{
		log.info("1111111111" + service);
		assertNotNull(service);
	}
	
	
	@Test
	public void testGetList()
	{
		service.getList(new PageHandler(2, 10))
			   .forEach(board -> log.info(board));
	}
	

	@Test
	public void testGet()
	{
		log.info(service.get(1L));
	}
	
	
	
	// 수정
	@Test
	public void testUpdate()
	{
		BoardVO board = service.get(1L);
		
		if(board == null)
		{
			return;
		}
		
		board.setTitle("제목 수정");
		
		log.info("수정 결과 : " + service.modify(board));
	}
	

	// 삭제
	@Test
	public void testDelete()
	{
		log.info("삭제 결과 : " + service.remove(2L));
	}
	
}
