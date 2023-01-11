package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	/* 1월 10일 퀴즈1-1 ~ 1-5
	//Quiz1-1 itemNm이 “테스트 상품1” 이고 ItemSellStatus가 Sell인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemsellStatus);

	//Quiz1-2 price가 10004~ 10008 사이인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByPriceBetween(Integer price1, Integer price2);

	//Quiz1-3 regTime이 2023-1-1 12:12:44 이후의 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	List<Item> findByRegTimeAfter(LocalDateTime regTime);

	//Quiz1-4 itemSellStatus가 null이 아닌 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	List<Item> findByItemSellStatusIsNotNull();

	//Quiz1-5 itemDetail이 설명1로 끝나는 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByItemDetailLike(String itemDetail);
	List<Item> findByItemDetailEndingWith(String itemDetail);
	 */
	
	
	
	
	//@Query("select 별칭 from 엔티티이름 별칭 where ")
	//String itemDetail로 받은 값을 "itemDetail"이라는 이름의 @Param으로 만들겠다.
	
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	//첫번째 ?1 에 매개변수값을 넣겠다.
	//@Query("select i from Item i where i.itemDetail like %?1% order by i.price desc")
	//List<Item> findByItemDetail(String itemDetail);

	@Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	//1월 11일 퀴즈2-1 price가 10005 이상인 레코드를 구하는 @Query 어노테이션을 작성하시오.  
	@Query("select i from Item i where i.price > :price")
	List<Item> findByPrice(@Param("price") Integer price);
	
	//1월 11일 퀴즈2-2 itemNm이 “테스트 상품1” 이고 ItemSellStatus가 Sell인 레코드를 구하는 @Query 어노테이션을 작성하시오.
	
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :itemsellStatus")
	List<Item> findByItemNmAndSell(@Param("itemNm") String itemNm , @Param("itemsellStatus")  ItemSellStatus itemsellStatus);
	
	@Query("select i from Item i where i.itemNm = ?1 and i.itemSellStatus = ?2")
	List<Item> findByItemNmAndSell2(String itemNm , ItemSellStatus itemsellStatus);
	
	
	//네이티브쿼리 2-2
	@Query(value = "select * from Item i where i.item_nm = :itemNm and i.item_sell_status = :#{#sell.name()}", nativeQuery = true)
	List<Item> getIemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("sell") ItemSellStatus sell);
	
		
}


