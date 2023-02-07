package com.wss.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class FileService {
	//업로드 파일
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		System.out.println("파일이름을 확장자로 끊기 : " + extension);
		
		String savedFileName = uuid.toString() + extension;
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData);
		fos.close();
		
		return savedFileName;
	}
	
	//파일 삭제
	public void deleteFile(String filePath) throws Exception {
		File deleteFile = new File(filePath);
		
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제하였습니다.");
		} else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
	
}
