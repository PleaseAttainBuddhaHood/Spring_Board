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

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
public class ReplyServiceImpl implements ReplyService 
{

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	
	// 등록
	@Transactional
	@Override
	public int register(ReplyVO vo) 
	{
		log.info("댓글 등록" + vo);
		
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		
		return replyMapper.insert(vo);
	}

	
	// 조회
	@Override
	public ReplyVO get(Long rno) 
	{
		log.info("특정 댓글 조회" + rno);
		return replyMapper.read(rno);
	}

	
	@Override
	public List<ReplyVO> getList(PageHandler ph, Long bno) 
	{
		log.info("전체 댓글 조회" + bno);
		return replyMapper.getListWithPaging(ph, bno);
	}


	@Override
	public ReplyPageDTO getListPage(PageHandler ph, Long bno) 
	{
		return new ReplyPageDTO(replyMapper.getCountByBno(bno), replyMapper.getListWithPaging(ph, bno));
	}
	
	
	// 수정
	@Override
	public int modify(ReplyVO vo) 
	{
		log.info("댓글 수정" + vo);
		return replyMapper.update(vo);
	}

	
	// 삭제
	@Transactional
	@Override
	public int remove(Long rno) 
	{
		log.info("댓글 삭제" + rno);
		
		ReplyVO vo = replyMapper.read(rno);
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		
		return replyMapper.delete(rno);
	}

	@Override
	public int removeAll(Long bno) 
	{
		log.info("댓글 전체 삭제" + bno);
		return replyMapper.deleteAll(bno);
	}


}
