package com.Spring_Board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.domain.ReplyPageDTO;
import com.Spring_Board.domain.ReplyVO;
import com.Spring_Board.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
@RequestMapping("/replies/")
@RestController
public class ReplyController 
{
	private ReplyService service;
	
	
	// 댓글 등록 
	// 브라우저: JSON 댓글 데이터 전송, 서버: 문자열로 결과 반환
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/new", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> create(@RequestBody ReplyVO vo)
	{
		int insertCount = service.register(vo);
		
		log.info("ReplyVO : " + vo);
		log.info("댓글 등록 개수 : " +  insertCount);
		
		return insertCount == 1 ? 
				  new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@GetMapping(value = "/pages/{bno}/{page}",
				produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno)
	{
		PageHandler ph = new PageHandler(page, 10);
		
		log.info("특정 게시물의 댓글 전체 목록, 글번호: " + bno);
		log.info("페이지 번호: " + ph);
		
		return new ResponseEntity<>(service.getListPage(ph, bno), HttpStatus.OK);
	}
	
	
	
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno")Long rno)
	{
		log.info("특정 댓글 조회 : " + rno);
		
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	
	@PreAuthorize("principal.username == #vo.replier")
	@DeleteMapping(value = "/{rno}")
	public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno)
	{
		log.info("댓글 삭제 : " + rno);
		log.info("replier : " + vo.getReplier());
		
		return service.remove(rno) == 1 ?
				  new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	
	@PreAuthorize("principal.username == #vo.replier")
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, 
					value = "/{rno}", 
					consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno)
	{
		log.info("댓글 수정 : " + vo);
		log.info("댓글 번호 : " + rno);
		
		return service.modify(vo) == 1 ?
				  new ResponseEntity<>("Success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
