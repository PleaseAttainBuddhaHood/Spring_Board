package com.Spring_Board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Spring_Board.domain.BoardAttachVO;
import com.Spring_Board.domain.BoardVO;
import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.mapper.BoardAttachMapper;
import com.Spring_Board.mapper.BoardMapper;
import com.Spring_Board.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService 
{
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	
	// 등록
	@Transactional
	@Override
	public void register(BoardVO board)
	{
		log.info("등록" + board);
		
		mapper.insertSelectKey(board);
		
		
		if(board.getAttachList() == null || board.getAttachList().size() <= 0)
		{
			return;
		}
		
		
		board.getAttachList().forEach(attach -> 
		{
			attach.setBno(board.getBno());
			
			attachMapper.insert(attach);
		});
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
	@Transactional
	@Override
	public boolean modify(BoardVO board) 
	{
		log.info("수정" + board);
		
		boolean modifyResult = mapper.update(board) == 1;

		attachMapper.deleteAll(board.getBno()); // 전부 삭제 후 다시 추가
		
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0)
		{
			board.getAttachList().forEach(attach -> 
			{
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

		return modifyResult;
	}

	
	
	// 삭제
	@Transactional
	@Override
	public boolean remove(Long bno) 
	{
		log.info("삭제" + bno);
		
		attachMapper.deleteAll(bno);
		replyMapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1;
	}



	@Override
	public List<BoardAttachVO> getAttachList(Long bno) 
	{
		log.info("첨부파일 목록의 게시글 번호 : " + bno);
		return attachMapper.findByBno(bno);
	}



}
