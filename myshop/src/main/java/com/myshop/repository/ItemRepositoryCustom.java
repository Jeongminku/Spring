package com.myshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
import com.myshop.entity.Item;

//1.사용자 정의 인터페이스를 작성해주세요 (QueryDsl을 사용하기 위함입니다)
public interface ItemRepositoryCustom {
	//상품관리 페이지 아이템
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
	//메인 화면에 뿌리는 아이템
	Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
