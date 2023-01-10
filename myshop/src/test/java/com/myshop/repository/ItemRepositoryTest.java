package com.myshop.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties") //테스트 properties 로 연결하기
class ItemRepositoryTest {

	@Autowired
	ItemRepository itemRepository;

	@Test							//테스트할 메소드에 @Test를 적어주기.
	@DisplayName("상품 저장 테스트") 	//@Test할 것의 이름지정.
	public void createItemTest() {
		Item item = new Item();		//엔티티 Item 임포트하기.
		item.setItemNm("테스트 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL); //열거형은 열거형에서 가져올것.
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now()); //현재날짜와 시간을 저장하는법 : LocalDateTime.Now()
		item.setUpdateTime(LocalDateTime.now());
		
		Item savedItem = itemRepository.save(item); //데이터 insert
		
		System.out.println(savedItem.toString()); //객체 정보 찍어보기.
		
	}
	
}
