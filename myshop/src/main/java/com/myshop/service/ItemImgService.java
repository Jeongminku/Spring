package com.myshop.service;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.entity.ItemImg;
import com.myshop.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
	@Value("${itemImgLocation}") //프로퍼티에 있는 아이템이미지로케이션을 가져올 것입니다.
	private String itemImgLocation; //C:/shop/item
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;
	
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename(); //파일 이름이 담겨있습니다.
		String imgName = "";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); //byte[] filedata: 파일 자체를 받는 것(?)
			imgUrl = "/images/item/" + imgName;
		} 
		
		//상품 이미지 정보 저장하기.
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg);
		
	}
}
