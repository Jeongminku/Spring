package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.myshop.constant.ItemSellStatus;
import com.myshop.dto.ItemFormDto;
import com.myshop.exception.OutOfStockException;

import lombok.*;

@Entity 				//이것을 Entity클래스로 사용할 것이라고 어노테이션합니다.
@Table(name="item") 	//테이블명을 설정합니다.
@Getter 				//Getter,Setter,ToString 롬복을 사용합니다.
@Setter
@ToString
public class Item extends BaseEntity {
	@Id 											//PK줌.
	@Column(name="item_id") 						//컬럼명 설정
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						//상품코드
	
	
	@Column(nullable = false, length = 50)			//notNull 에 길이는 50으로 지정.
	private String itemNm;					//상품명
	
	
	@Column(nullable = false, name = "price")		//notNull 에 컬럼이름은 "price"로 지정.
	private int price;						//가격
	
	
	@Column(nullable = false)						//(nullable = false) = notNull
	private int stockNumber;				//재고수량
	
	
	@Lob
	@Column(nullable = false)
	private String itemDetail;				//상품 상세 설명
	
	
	@Enumerated(EnumType.STRING)					//열거형 타입을 지정하면 SELL아니면 SOLD_OUT만 저장이 가능하다. (그렇게 enum에 지정해놨기 때문.)
	private ItemSellStatus itemSellStatus;	//상품 판매상태
	
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
	}
	
	
	//private LocalDateTime regTime;			//등록 시간

	
	//private LocalDateTime updateTime;		//수정 시간
	
	//상품의 재고 감소
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber - stockNumber; //주문 후 남은 재고수량
		
		if(restStock < 0) {
			throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량:" + this.stockNumber + ")" );
		}
		this.stockNumber = restStock; //주문 후 남은 재고 수량을 상품의 현재 재고 값으로 할당합니다.
	}
	
	
	//상품의 재고 증가 (주문이 취소된 경우 재고가 증가함)
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
		
	}
	
}
