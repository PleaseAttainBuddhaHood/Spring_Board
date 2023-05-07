package com.Spring_Board.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class BoardControllerTests 
{
	
	@Autowired
	private WebApplicationContext ctx;
	
	
	// 가짜 mvc. 가짜로 URL과 파라미터 등을 브라우저에서 사용하는 것처럼 만들어 Controller 실행 가능
	private MockMvc mockMvc;
	
	
	// 모든 테스트가 실행되기 전에 매번 실행됨
	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	

	// 등록
	@Test
	public void testRegister() throws Exception
	{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "테스트 새 글 제목")
				.param("content", "테스트 새 글 내용")
				.param("writer", "유저00"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	
	// 조회
	@Test
	public void testList() throws Exception
	{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
	
	
	@Test
	public void testGet() throws Exception
	{
		log.info(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "1"))
				.andReturn()
				.getModelAndView().getModelMap());
	}
	
	
	@Test
	public void testListPaging() throws Exception
	{
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
				.param("currentPage", "2")
				.param("pageRowSize", "50"))
				.andReturn().getModelAndView().getModelMap());
	}
	
	
	
	// 수정
	@Test
	public void testModify() throws Exception
	{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno", "1")
				.param("title", "수정된 테스트 새 글 제목")
				.param("content", "수정된 테스트 새 글 내용")
				.param("writer", "유저00"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}	
	
	
	
	// 삭제
	@Test
	public void testRemove() throws Exception
	{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove").param("bno", "24"))
								   .andReturn()
								   .getModelAndView()
								   .getViewName();
		
		log.info(resultPage);
	}
	
}
