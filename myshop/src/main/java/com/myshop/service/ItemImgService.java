package com.myshop.service;

import javax.persistence.EntityNotFoundException;
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
	
	//이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
		if(!itemImgFile.isEmpty()) { //itemImgFile이 비어있지 않으면 = itemImgFile안에 파일이 있으면
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제 =  (기존 이미지 삭제수정을 하면 기존 이미지파일이 삭제되게 합니다.)
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) { //ImgName이 비어있지 않으면.
				//C:/shop/item/이미지 이름.jpg
				fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
			}
			
			//수정된 이미지 파일 업로드
			String oriImgName = itemImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			//★ savedItemImg가 영속상태에 있음(영속성 컨텍스트에 이 엔티티가 이미 있다는 뜻)
			//★ savedItemImg는 현재 영속상태이므로 데이터를 변경하는 것만으로도 변경감지 기능이 동작하여 트랜잭션이 끝날대 update쿼리가 실행됩니다.
			//따로 Repository.save를 굳이 안해줘도 된다는 뜻(?) insert할때는 save를 해야하지만 update할때는 save를 안해도 됩니다.
			//이렇게 하기 위해서는 엔티티가 반드시 영속상태여야 합니다.
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
		}
	}
}
