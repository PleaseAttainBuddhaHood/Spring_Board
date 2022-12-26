package com.Spring_Board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.domain.ReplyPageDTO;
import com.Spring_Board.domain.ReplyVO;
import com.Spring_Board.mapper.BoardMapper;
import com.Spring_Board.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;


@Log4j
@Service
public class ReplyServiceImpl implements ReplyService 
{

	@Autowired
	private ReplyMapper mapper;
	
	@Autowired
	private BoardMapper boardMapper;
	

	@Transactional
	@Override
	public int register(ReplyVO vo) 
	{
		log.info("댓글 등록" + vo);
		
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		
		return mapper.insert(vo);
	}

	
	@Override
	public ReplyVO get(Long rno) 
	{
		log.info("특정 댓글 조회" + rno);
		
		return mapper.read(rno);
	}

	
	@Override
	public List<ReplyVO> getList(PageHandler ph, Long bno) 
	{
		log.info("전체 댓글 조회" + bno);
		
		return mapper.getListWithPaging(ph, bno);
	}

	
	@Override
	public int modify(ReplyVO vo) 
	{
		log.info("댓글 수정" + vo);
		
		return mapper.update(vo);
	}

	
	@Transactional
	@Override
	public int remove(Long rno) 
	{
		log.info("댓글 삭제" + rno);
		
		ReplyVO vo = mapper.read(rno);
		
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		
		return mapper.delete(rno);
	}


	@Override
	public ReplyPageDTO getListPage(PageHandler ph, Long bno) 
	{
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(ph, bno));
	}

}
