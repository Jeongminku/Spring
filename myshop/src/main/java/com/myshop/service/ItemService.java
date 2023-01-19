package com.myshop.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.entity.Item;
import com.myshop.entity.ItemImg;
import com.myshop.repository.ItemImgRepository;
import com.myshop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepository;
	
	//상품 등록
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		
		//상품등록
		Item item = itemFormDto.createItem();
		itemRepository.save(item);
		
		//이미지 등록                    //우리가 넣을 수 있는 이미지는 최대 5개.  3개를 입력했다고 하면 for문이 3번 돕니다.
		for(int i=0; i<itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg(); //itemImg 객체를 생성하여 
			itemImg.setItem(item);			 //item을 등록합니다.
			
			//첫번째 이미지 일때 대표 상품이미지 여부를 지정합니다. 첫번째 이미지만 대표상품이미지로 지정되고 나머지는 N으로 가서 대표아니게됩니다.
			if(i==0) {
				itemImg.setRepimgYn("Y");
			} else {
				itemImg.setRepimgYn("N");
			}
			
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		return item.getId();
		
	}
}
