package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.constant.ItemSellStatus;
import com.myshop.entity.Item;

//JpaRepository : 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어있습니다.
						//JpaRepository<사용할 엔티티 클래스, 해당엔티티의 기본속성타입>
public interface ItemRepository extends JpaRepository<Item, Long>{
	//select * from item where item_nm = ? 와 같은 표현입니다. ?의 값을 매개변수로 받을 것입니다.
	List<Item> findByItemNm(String itemNm);
	
	//select * from item where item_nm = ? or item_detail = ? 와 같은 표현입니다. ?가 두개이므로 매개변수도 2개를 받습니다.
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	//select * from item where price < ?
	List<Item> findByPriceLessThan(Integer price);
	
	//select * from item where price < ? order by price desc;
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	//Quiz1 itemNm이 “테스트 상품1” 이고 ItemSellStatus가 Sell인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus SELL);
	
	//Quiz2 price가 10004~ 10008 사이인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByPriceBetween(Integer price1, Integer price2);
	
	//Quiz3 regTime이 2023-1-1 12:12:44 이후의 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	List<Item> findByRegTimeAfter(LocalDateTime regTime);
	
	//Quiz4 itemSellStatus가 null이 아닌 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	List<Item> findByItemSellStatusIsNotNull();
	
	//Quiz5 itemDetail이 설명1로 끝나는 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByItemDetailLike(String itemDetail);
	List<Item> findByItemDetailEndingWith(String itemDetail);
}
