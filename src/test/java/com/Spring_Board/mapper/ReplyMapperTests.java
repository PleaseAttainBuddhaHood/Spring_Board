package com.Spring_Board.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.domain.ReplyVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ReplyMapperTests 
{
	@Autowired
	private ReplyMapper mapper;
	
	private Long[] bnoArr = {300L, 299L, 298L, 297L, 296L};
	
	
	
	// 등록
	@Test
	public void testCreate()
	{
		IntStream.rangeClosed(1, 10).forEach(i -> 
		{
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트" + i);
			vo.setReplier("댓글러" + i);
			
			mapper.insert(vo);
		});
	}
	
	
	@Test
	public void testMapper()
	{
		log.info(mapper);
	}
	
	
	// 조회
	@Test
	public void testRead()
	{
		Long targetRno = 15L;
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}
	

	@Test
	public void testList()
	{
		PageHandler ph = new PageHandler();
		List<ReplyVO> replies = mapper.getListWithPaging(ph,  bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	
	@Test 
	public void testList2()
	{
		PageHandler ph = new PageHandler(2, 10);
		List<ReplyVO> replies = mapper.getListWithPaging(ph, 300L);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	
	
	// 수정
	@Test
	public void testUpdate()
	{
		Long targetRno = 14L;
		ReplyVO vo = mapper.read(targetRno);

		vo.setReply("댓글 수정");
		
		int count = mapper.update(vo);
		
		log.info("수정 댓글 개수 : " + count);
	}
	
	


	// 삭제
	@Test
	public void testDelete()
	{
		Long targetRno = 15L;
		mapper.delete(targetRno);
	}
}
