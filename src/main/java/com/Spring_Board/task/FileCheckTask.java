package com.Spring_Board.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Spring_Board.domain.BoardAttachVO;
import com.Spring_Board.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask  // 데이터베이스(tbl_attach)에 등록된 파일인가 확인하고, 잘못된 파일은 삭제하는 작업
{
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	
	private String getFolderYesterDay()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	
	// 매일 새벽 2시
	@Scheduled(cron="0 0 2 * * *") // 초 분 시 일 월 주 (년)
	public void checkFiles() throws Exception
	{
		log.warn("파일 체크 태스크 실행");
		log.warn(new Date());
		
		// 데이터베이스 내 파일 목록(어제 날짜)
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		
		
		// fileList는 BoardAttachVO 타입 객체이므로, 나중 비교를 위해 java.nio.Paths 목록으로 변환
		List<Path> fileListPaths = fileList
				.stream()
				.map(vo -> Paths.get("D:\\Programming\\STS\\tmp\\Spring_Board", 
									 vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		
		fileList.stream()
			.filter(vo -> vo.isImageType() == true)
			.map(vo -> Paths.get("D:\\Programming\\STS\\tmp\\Spring_Board", 
					vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
			.forEach(p -> fileListPaths.add(p));
		
		
		log.warn("==============");
		
		
		fileListPaths.forEach(p -> log.warn(p));
		
		
		// 실제 폴더에 있는 파일들의 목록에서 데이터베이스에 없는 파일들을 찾아 삭제 작업
		File targetDir = Paths.get("D:\\Programming\\STS\\tmp\\Spring_Board", getFolderYesterDay()).toFile();
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		
		log.warn("-------------------");
		
		
		for(File file : removeFiles)
		{
			log.warn(file.getAbsolutePath());
			file.delete();
		}
		
		
	}
}
