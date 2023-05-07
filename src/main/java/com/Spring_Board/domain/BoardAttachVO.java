package com.Spring_Board.domain;

import lombok.Data;

@Data
public class BoardAttachVO 
{
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean ImageType;
	private Long bno;
}
