package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.ItemDto;

@Controller
@RequestMapping(value="/thymeleaf")
public class ThymeleafExController {
	@GetMapping(value="/ex01")
	public String thymeleafExample01(Model model) {
		model.addAttribute("data", "타임리프 예제를 넣었습니다.");		
		return "thymeleafEx/thymeleafExample01";
	}
	
	@GetMapping(value="/ex02")
	public String thymeleafExample02(Model model) {
		ItemDto itemDto = new ItemDto();
		
		itemDto.setItemNm("테스트 상품");
		itemDto.setPrice(10000);
		itemDto.setItemDetail("상품상세내용");
		
		model.addAttribute("itemDto", itemDto);
		
		return "thymeleafEx/thymeleafExample02";
	}
}

