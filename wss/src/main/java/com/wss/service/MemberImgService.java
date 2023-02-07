package com.wss.service;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wss.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImgService {
	@Value("${itemImgLocation}")
	private String imgLocation;
	
	private final FileService fileService;
	
	public void savememberImg(Member member, MultipartFile imgfile) throws Exception {
		
		String imgOri = imgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

			
		if(!StringUtils.isEmpty(imgOri)) {
			imgName = fileService.uploadFile(imgLocation, imgOri, imgfile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		member.updateImg(imgName, imgUrl, imgOri);
		
	}
}
