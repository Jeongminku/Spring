package com.myshop.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
import com.myshop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ItemService itemService; 
	
	@GetMapping(value = "/")
	public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
		//현재페이지가 있으면 그걸 보여주고 그게 아니라면 0(첫번째페이지)를 보여주세요.
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6); //of는 (int page, int size) 
		Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
		
		model.addAttribute("items", items); //items는 Page(페이지) 객체
		model.addAttribute("itemSearchDto",itemSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "main";
	}
}