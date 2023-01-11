package com.myshop.repository;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties") //테스트 properties 로 연결하기

class ItemRepositoryTest {

	@Autowired
	ItemRepository itemRepository;

	@PersistenceContext //영속성 컨텍스트를 사용하기 위해 선언합니다.
	EntityManager em;   //엔티티 매니저를 가져옵니다.
	
	
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
	public void createItemTest2() {
		for (int i=1; i<= 5; i++) {
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
		
		for (int i=6; i<= 10; i++) {
			Item item = new Item();		//엔티티 Item 임포트하기.
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT); //열거형은 열거형에서 가져올것.
			item.setStockNumber(0);
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
	//@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("@native Query를 이용한 상품 조회 테스트")
	public void findByItemDetailByNative() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("querydsl 조회 테스트")
	public void queryDslTest() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 생성하기 위한 객체입니다.
							   //JPAQueryFactory(엔티티 매니저);
		QItem qItem = QItem.item; //타겟/제네레이티드-소스/자바 안에 QItem 생성이 자동으로 되었기 때문에 Import 해주면 됩니다.
		
		//qf.selectFrom(qItem)은 select * from item 과 같습니다.
		//select * from item where itemSellStatus = 'SELL'이라는 뜻입니다. 
		//체이닝으로 연결해서 사용도 가능합니다 .where(qitem.itemSellStatus.eq(ItemSellStatus.SELL))~~~
		JPAQuery<Item> query = qf.selectFrom(qItem)
				.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
				.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
				.orderBy(qItem.price.desc()); 
		
		List<Item> itemList = query.fetch(); //조회 결과 리스트를 반환합니다.
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//@Test
	//@DisplayName("querydsl 조회 테스트 2")
	public void queryDslTest2() {
		this.createItemTest2();

		JPAQueryFactory qf = new JPAQueryFactory(em);
		QItem qItem = QItem.item;
		Pageable page = PageRequest.of(1, 2); //of(조회할 페이지의 번호, 한 페이지당 조회할 데이터의 개수)
		
		//gt(매개변수) : 매개변수보다 큰.
		JPAQuery<Item> query = qf.selectFrom(qItem)
								.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
								.where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
								.where(qItem.price.gt(10003))
								.offset(page.getOffset())
								.limit(page.getPageSize());
		
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	 
	
	
	
	
	
}
	


/* 23년 1월 10~11일 퀴즈1-1 ~ 1-5
		//@Test
		//@DisplayName("퀴즈1-1 ItemNm And ItemSellStatus")
		public void findByItemNmAndItemSellStatus() {
			this.createItemTest();
			List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
			for (Item item : itemList) {
				System.out.println(item.toString());
			}
		}
		
		//@Test
		//@DisplayName("퀴즈1-2 Price Between")
		public void findByPriceBetween() {
			this.createItemTest();
			List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
			for (Item item : itemList) {
				System.out.println(item.toString());
			}
		}
		
		//@Test
		//@DisplayName("퀴즈1-3 LocalDateTime After")
		public void findByRegTimeGreaterThan() {
			this.createItemTest();
			LocalDateTime Time = LocalDateTime.of(2023, 01, 01, 12, 12, 44);
			List<Item> itemList = itemRepository.findByRegTimeAfter(Time);
			for (Item item : itemList) {
				System.out.println(item.toString());
			}
		}
		
		//@Test
		//@DisplayName("퀴즈1-4 ItemSellStatus Is Not Null 로 null이 아닌 레코드 구하기")
		public void findByItemSellStatusIsNotNull() {
			this.createItemTest();
			List<Item> itemList = itemRepository.findByItemSellStatusIsNotNull();
			for (Item item : itemList) {
				System.out.println(item.toString());
			}
		}
		
		//@Test
		//@DisplayName("퀴즈1-5 ItemDetail like %설명1")
		public void findByItemDetailLike() {
			this.createItemTest();
			List<Item> itemList = itemRepository.findByItemDetailLike("%설명1");
			for (Item item : itemList) {
				System.out.println(item.toString());
			}
		}
		
		//@Test
		//@DisplayName("퀴즈1-5 ItemDetail EndingWith")
		public void findByItemDetailEndingWith() {
			this.createItemTest();
			List<Item> itemList = itemRepository.findByItemDetailEndingWith("설명1");
			for (Item item : itemList) {
				System.out.println(item.toString());
			}
		}
		
 */
/*23년 1월 11일 퀴즈 2-1, 2-2
	//@Test
	//@DisplayName("price가 10005 이상인 레코드를 구하는 @Query 어노테이션")
	public void findByPrice() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPrice(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}	
	
	@Test
	@DisplayName("itemNm이 테스트 상품1 이고 ItemSellStatus가 Sell인 레코드")
	public void findByItemNmAndSell(){
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndSell("테스트 상품1", ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
	}
 */


/*23년 1월 11일 퀴즈3-1 ~ 3-5 

//@Test
//@DisplayName("querydsl 퀴즈 3-1")
public void ItemNmAndSellStatus() {
	this.createItemTest();
	JPAQueryFactory qf = new JPAQueryFactory(em);
	QItem qItem = QItem.item;
	JPAQuery<Item> query = qf.selectFrom(qItem)
							.where(qItem.itemNm.eq("테스트 상품1"))
							.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
	List<Item> itemList = query.fetch(); //조회 결과 리스트를 반환합니다.
	
	for (Item item : itemList) {
		System.out.println(item.toString());
	}
}

//@Test
//@DisplayName("querydsl 퀴즈 3-2") 
public void queryDslBetween() {
	this.createItemTest();
	JPAQueryFactory qf = new JPAQueryFactory(em);
	QItem qItem = QItem.item;
	JPAQuery<Item> query = qf.selectFrom(qItem)
							.where(qItem.price.between(10004, 10008));
	List<Item> itemList = query.fetch();
	
	for (Item item : itemList) {
		System.out.println(item.toString());
	}
}

//@Test
//@DisplayName("querydsl 퀴즈 3-3")
public void queryDslAfter() {
	this.createItemTest();
	JPAQueryFactory qf = new JPAQueryFactory(em);
	QItem qItem = QItem.item;
	JPAQuery<Item> query = qf.selectFrom(qItem)
							.where(qItem.regTime.after(LocalDateTime.of(2023, 01, 01, 12, 12, 44)));
	List<Item> itemList = query.fetch();
	
	for (Item item : itemList) {
		System.out.println(item.toString());
	}
}

//@Test
//@DisplayName("querydsl 퀴즈 3-4")
public void queryDslNotNull() {
	this.createItemTest();
	JPAQueryFactory qf = new JPAQueryFactory(em);
	QItem qItem = QItem.item;
	JPAQuery<Item> query = qf.selectFrom(qItem)
							.where(qItem.itemSellStatus.isNotNull());
	List<Item> itemList = query.fetch();
			
	for (Item item : itemList) {
		System.out.println(item.toString());
	}
}

//@Test
//@DisplayName("querydsl 퀴즈 3-5")
public void queryDslLike() {
	this.createItemTest();
	JPAQueryFactory qf = new JPAQueryFactory(em);
	QItem qItem = QItem.item;
	JPAQuery<Item> query = qf .selectFrom(qItem)
							.where(qItem.itemDetail.like("%설명1"));
	List<Item> itemList = query.fetch();
	
	for (Item item : itemList) {
		System.out.println(item.toString());
	}
}
*/

