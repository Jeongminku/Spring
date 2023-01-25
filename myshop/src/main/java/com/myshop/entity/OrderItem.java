package com.myshop.entity;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 				//이것을 Entity클래스로 사용할 것이라고 어노테이션합니다.
@Table(name="order_item") 	//테이블명을 설정합니다.
@Getter 				//Getter,Setter,ToString 롬복을 사용합니다.
@Setter
@ToString
public class OrderItem extends BaseEntity{
	@Id
	@Column(name="order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	
	private int orderPrice; //주문 가격
	
	
	private int count; //주문 수량
	
	//주문할 상품과 주문 수량을 통해 orderItem객체를 만듦.
	public static OrderItem createOrderItem(Item item, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderPrice(item.getPrice());
		
		item.removeStock(count);//count가 주문수량이기때문에 매개변수로 바로 주면 됩니다.
		
		return orderItem;
		
	}
	
	public int getTotalPrice() {
		return orderPrice*count;
	}
}
