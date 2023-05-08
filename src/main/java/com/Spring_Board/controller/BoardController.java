package com.Spring_Board.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Spring_Board.domain.BoardAttachVO;
import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageDTO;
import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
@Controller
public class BoardController 
{
	
	private BoardService boardService;

	
	// 등록
	@PreAuthorize("isAuthenticated()") // 로그인 성공한 사용자만이 해당 기능 사용 가능
	@GetMapping("/register")
	public void register()
	{
		
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr)
	{
		log.info("등록!" + board);
		
		
		if(board.getAttachList() != null)
		{
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		log.info("=============");
		
		
		boardService.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	


	// 조회
	@GetMapping("/list")
	public void list(PageHandler ph, Model model)
	{
		// 전체 게시글 수(페이징 처리)
		int total = boardService.getTotal(ph);
		
		log.info("페이징 전체 목록" + ph);
		log.info("total: " + total);
		
		model.addAttribute("list", boardService.getList(ph));
		model.addAttribute("pageMaker", new PageDTO(ph, total));
	}
	

	
	@ResponseBody // JSON 데이터 반환
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE) //HTTP 응답 헤더로 "Content-Type: application/json;charset=UTF-8" 반환
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno)
	{
		log.info("첨부 파일 목록 : " + bno);
		
		return new ResponseEntity<>(boardService.getAttachList(bno), HttpStatus.OK);
	}
	
	
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("ph") PageHandler ph, Model model)
	{
		log.info("조회 또는 수정");
		
		model.addAttribute("board", boardService.get(bno));
	}
	
	
	// 수정
	@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, PageHandler ph, RedirectAttributes rttr)
	{
		log.info("수정" + board);
		
		if(boardService.modify(board))
		{
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + ph.getListLink();
	}
	
	
	
	// 삭제
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, PageHandler ph, RedirectAttributes rttr, String writer)
	{
		log.info("삭제" + bno);
		
		List<BoardAttachVO> attachList = boardService.getAttachList(bno);
		
		if(boardService.remove(bno))
		{
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + ph.getListLink();
	}
	

	
	private void deleteFiles(List<BoardAttachVO> attachList)
	{
		if(attachList == null || attachList.size() == 0)
		{
			return;
		}
		
		
		log.info("첨부 파일 삭제");
		log.info(attachList);
		
		
		attachList.forEach(attach -> 
		{
			try
			{
				Path file = Paths.get("D:\\Programming\\STS\\tmp\\Spring_Board\\" + attach.getUploadPath() + 
						"\\" + attach.getUuid() + "_" + attach.getFileName());
				
				Files.deleteIfExists(file); // 파일이 존재하는 경우에 파일 삭제, 파일 미존재 시 false 리턴
				
				
				if(Files.probeContentType(file).startsWith("image"))
				{
					Path thumbNail = Paths.get("D:\\Programming\\STS\\tmp\\Spring_Board\\" + attach.getUploadPath() + 
							"\\s_" + attach.getUuid() + "_" + attach.getFileName());
					
					Files.delete(thumbNail);
				}
			}
			catch(Exception e)
			{
				log.error("파일 삭제 에러" + e.getMessage());
			}
		});
	}
	
}
