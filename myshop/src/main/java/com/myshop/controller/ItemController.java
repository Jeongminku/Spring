package com.myshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	
	//상품등록 페이지를 보여줌
	@GetMapping(value = "/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "item/itemForm";
	}
	
	//상품을 등록함
	@PostMapping(value ="/admin/item/new")
	//@Valid ~ bindingResult -> 유효성 검사할때 필요로 했던 것.
	//@RequestParam("파라미터 이름 지정 -- input창에 등록한 name알아서 찾아와서 List타입으로 받아오는데 <MultipartFile>이라고 하는 타입으로 리스트를 받아옵니다. 
	//이 List에는 MultipartFile이라는 이름으로 이미지가 내가 등록한 수 만큼 저장됩니다."
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		} 
		
		//itemImgFileList.get(인덱스0)으로 첫번째 이미지가 있는지 검사합니다 (왜냐면, 첫번째 이미지는 필수 입력값 이기 때문입니다.) + Id값이 null이면
		if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) { 
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
			return "item/itemForm";
		}
		
		return "redirect:/";
		
	}
}
