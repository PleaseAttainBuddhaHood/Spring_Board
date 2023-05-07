package com.Spring_Board.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO 
{
	private Long rno;
	private Long bno;
	private String reply;
	private String replier;
	private Date replyDate;
	private Date replyUpdateDate;
}
