package com.Spring_Board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	private BoardService service;
	
	
	
	@GetMapping("/register")
	public void register()
	{
		
	}
	
	
	
	@GetMapping("/list")
	public void list(PageHandler ph, Model model)
	{
		int total = service.getTotal(ph);
		
		log.info("페이징 전체 목록" + ph);
		log.info("total: " + total);
		
		model.addAttribute("list", service.getList(ph));
		model.addAttribute("pageCreator", new PageDTO(ph, total));
	}
	

	
	
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr)
	{
		log.info("등록!" + board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	
	
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("ph") PageHandler ph, Model model)
	{
		log.info("조회 또는 수정");
		
		model.addAttribute("board", service.get(bno));
	}
	
	
	
	@PostMapping("/modify")
	public String modify(BoardVO board, PageHandler ph, RedirectAttributes rttr)
	{
		log.info("수정" + board);
		
		
		if(service.modify(board))
		{
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + ph.getListLink();
	}
	
	
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, PageHandler ph, RedirectAttributes rttr)
	{
		log.info("삭제" + bno);
		
		if(service.remove(bno))
		{
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + ph.getListLink();
	}
}
