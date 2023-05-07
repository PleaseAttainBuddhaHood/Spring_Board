package com.Spring_Board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.Spring_Board.domain.PageHandler;
import com.Spring_Board.domain.ReplyVO;

public interface ReplyMapper 
{
	// 등록
	public int insert(ReplyVO vo);
	
	
	// 읽기
	public ReplyVO read(Long rno);
	public List<ReplyVO> getListWithPaging(@Param("ph") PageHandler ph, @Param("bno") Long bno); // @Param : DB에 2개 이상의 변수 전달 시 사용
	public int getCountByBno(Long bno); // 해당 게시물의 전체 댓글 숫자
	
	
	// 수정
	public int update(ReplyVO reply);
	
	
	// 삭제
	public int delete(Long rno);
	public int deleteAll(Long bno);
}
