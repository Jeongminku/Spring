package com.myshop.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;


@Service
@Log //"lombok"에서 제공하는 어노테이션입니다. 로그를 남겨야될때 사용합니다.
public class FileService {
	//파일 업로드
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
		UUID uuid = UUID.randomUUID(); //이름이 중복되면 안되므로 랜덤한 값을 지정하기위해서 UUID를 사용하였습니다.
									   //중복되지않는 랜덤한 파일 이름을 생성합니다.
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자명을 따로 분리해주세요.
		String savedFileName = uuid.toString() + extension; //파일 이름을 랜덤값 + 확장자명 으로 파일이름 생성.
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		//생성자에 파일이 저장될 위치와 파일의 이름을 같이 넘겨 출력 스트림을 만듭니다.
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData); //출력 스트림에 파일 데이터를 입력합니다.
		fos.close(); //리소스(?)를 사용하였으므로 닫아주세요.
		
		return savedFileName;
	}
	
	//파일 삭제
	public void deleteFile(String filePath) throws Exception {
		File deleteFile = new File(filePath); //파일이 저장된 경로를 이용해서 파일 객체를 생성
		
		if(deleteFile.exists()) { //해당 경로에 파일이 있으면
			deleteFile.delete(); //파일을 삭제합니다.
			log.info("파일을 삭제하였습니다.");
		} else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
}
