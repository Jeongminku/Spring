package com.myshop.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

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

	/*
	//@Test							//테스트할 메소드에 @Test를 적어주기.
	//@DisplayName("상품 저장 테스트") 	//@Test할 것의 이름지정.
	//테스트를 하지 않으려면 @Test 와 @DisplayName을 떼주셔야 합니다.
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
	*/
	public void createItemTest() {
		for (int i=1; i<= 10; i++) {
			Item item = new Item();		//엔티티 Item 임포트하기.
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL); //열거형은 열거형에서 가져올것.
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now()); //현재날짜와 시간을 저장하는법 : LocalDateTime.Now()
			item.setUpdateTime(LocalDateTime.now());
			
			Item savedItem = itemRepository.save(item); //데이터 insert			
		}
		
	}
	
	//@Test
	//@DisplayName("상품명 조회 테스트")
	public void findByItemNmTest() {
		this.createItemTest(); //아이템 테이블에 데이터 10개를 insert 시켜둡니다. (조회하기위해서)
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품1"); //의존성주입해둔 itemRepository에서 findByItemNm을 불러옵니다.
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	
	//@Test
	//@DisplayName("상품명 or 상품상세설명 조회 테스트")
	public void findByItemNmOrItemDetail() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("가격 LessThan 테스트")
	public void findByPriceLessThan() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceLessThan(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}

	//@Test
	//@DisplayName("가격 내림차순 조회 테스트 ---- LessThan + Order by desc")
	public void findByPriceLessThanOrderByPriceDesc() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("퀴즈1 ItemNm And ItemSellStatus")
	public void findByItemNmAndItemSellStatus() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("퀴즈2 Price Between")
	public void findByPriceBetween() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("퀴즈3 LocalDateTime After")
	public void findByRegTimeGreaterThan() {
		this.createItemTest();
		LocalDateTime Time = LocalDateTime.of(2023, 01, 01, 12, 12, 44);
		List<Item> itemList = itemRepository.findByRegTimeAfter(Time);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("퀴즈4 ItemSellStatus Is Not Null 로 null이 아닌 레코드 구하기")
	public void findByItemSellStatusIsNotNull() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemSellStatusIsNotNull();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	@DisplayName("퀴즈5 ItemDetail like %설명1")
	public void findByItemDetailLike() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailLike("%설명1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("퀴즈5 ItemDetail EndingWith")
	public void findByItemDetailEndingWith() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailEndingWith("설명1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
}
