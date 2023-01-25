package com.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemImgDto;
import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
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
	
	//상품 가져오기
	@Transactional(readOnly = true) //트랜잭션 읽기 전용.  (트랜잭션 임포트할때  org.springframework.transaction.annotation.Transactional 이걸로 해야.)
	public ItemFormDto getItemDtl(Long itemId) {
		//1. item_img테이블의 이미지를 가져옵니다.
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		
		//엔티티 객체를 -> Dto 객체로 변환시켜주는 작업입니다.
		for(ItemImg itemImg : itemImgList) {
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			itemImgDtoList.add(itemImgDto);
		}
		
		//2. item테이블에 있는 데이터를 가져옵니다.
		Item item = itemRepository.findById(itemId)
								  .orElseThrow(EntityNotFoundException::new);
		
		//엔티티 객체를 -> Dto 객체로 변환시켜주는 작업
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		//상품의 이미지 정보를 넣어줍니다.
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		return itemFormDto;
	}
	
	//상품 수정
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		Item item = itemRepository.findById(itemFormDto.getId())
								.orElseThrow(EntityNotFoundException::new);
		
		item.updateItem(itemFormDto);
		
		List<Long> itemImgIds = itemFormDto.getItemImgIds();
		
		for(int i=0; i<itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i)); //Id와 이미지파일 자체를 가져와서 updateItemImg를 실행합니다.
			
		}
		return item.getId();
		
	}
	//상품 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}

	//CustomImpl의 Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable)를 실행시키고 리턴함.
	@Transactional(readOnly = true)
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getMainItemPage(itemSearchDto, pageable);
	}
	
}
