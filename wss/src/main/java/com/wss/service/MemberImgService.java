package com.wss.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wss.entity.Member;
import com.wss.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImgService {
	@Value("${itemImgLocation}")
	private String imgLocation;
	
	private final FileService fileService;
	private final MemberRepository memberRepository;
	
	
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
	
	public void updateMemberImg(Member member, MultipartFile imgfile) throws Exception {
		
		if(!imgfile.isEmpty()) { //itemImgFile이 비어있지 않으면 = itemImgFile안에 파일이 있으면
			Member savedMemberImg = memberRepository.findByImgName(member.getImgName());
			//기존 이미지 파일 삭제 =  (기존 이미지 삭제수정을 하면 기존 이미지파일이 삭제되게 합니다.)
			if(!StringUtils.isEmpty(savedMemberImg.getImgName())) { //ImgName이 비어있지 않으면.
				fileService.deleteFile(imgLocation + "/" + savedMemberImg.getImgName());
			}
			
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
	
}
