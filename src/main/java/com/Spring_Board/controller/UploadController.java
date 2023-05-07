package com.Spring_Board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Spring_Board.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;


@Log4j
@Controller
public class UploadController 
{
	
	
	// 날짜별 폴더 처리를 위한, 오늘 날짜의 경로를 문자열로 생성
	private String getFolder()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	
	private boolean checkImageType(File file)
	{
		try
		{
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile)
	{
		List<AttachFileDTO> list = new ArrayList<>();
		
		String uploadFolder = "D:\\Programming\\STS\\tmp\\Spring_Board";
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		
		if(uploadPath.exists() == false)
		{
			uploadPath.mkdirs();
		}
		
		
		for(MultipartFile multipartFile : uploadFile)
		{
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			UUID uuid = UUID.randomUUID();

			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("--------------");
			log.info("업로드 파일 이름 : " + multipartFile.getOriginalFilename());
			log.info("업로드 파일 크기 : " + multipartFile.getSize());
			log.info("파일 이름 : " + uploadFileName);
			
			attachDTO.setFileName(uploadFileName);
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			
			try
			{
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile); // 파일 저장
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				if(checkImageType(saveFile))
				{
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				
				list.add(attachDTO);
			}
			catch(Exception e)
			{
				log.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	// 특정한 파일 이름을 받아 이미지 데이터 전송
	@ResponseBody
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName)
	{
		log.info("파일 이름 : " + fileName);
		
		File file = new File("D:\\Programming\\STS\\tmp\\Spring_Board\\" + fileName);
		
		log.info("파일 : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try
		{
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK); // 파일 복사하여 Byte 배열로 반환
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	// 브라우저별 다운로드 처리
	@ResponseBody
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) // MIME 타입을 다운로드 타입으로 지정(8비트 단위)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName)
	{
		
		Resource resource = new FileSystemResource("D:\\Programming\\STS\\tmp\\Spring_Board\\" + fileName);
		String resourceName = resource.getFilename();
		
		// UUID 제거
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();
		
		
		if(resource.exists() == false)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		try
		{
			String downloadName = null;
			
			
			if(userAgent.contains("Trident")) // IE 브라우저 엔진 이름 - IE11
			{
				log.info("IE 브라우저");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			}
			else if(userAgent.contains("Edge"))
			{
				log.info("Edge 브라우저");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			}
			else
			{
				log.info("크롬 브라우저");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			
			log.info("다운로드 파일 이름 : " + downloadName);
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName, String type)
	{
		log.info("파일 삭제 : " + fileName);
		
		File file;
		
		try
		{
			file = new File("D:\\Programming\\STS\\tmp\\Spring_Board\\" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			
			if(type.contentEquals("image"))
			{
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				file = new File(largeFileName);
				
				file.delete();
			}
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
}
