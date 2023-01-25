package com.example.dto;

import com.example.constant.ItemSellStatus;

import lombok.*;

@Getter
@Setter
public class ItemDto {
	private Long id;
	
	private String itemNm;
	
	private int price;
	
	private int stockNumber;
	
	private String itemDetail;
	
	private ItemSellStatus itemSellStatus;
}
