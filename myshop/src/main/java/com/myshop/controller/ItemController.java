package com.myshop.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemSearchDto;
import com.myshop.entity.Item;
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
	//@Valid ~ bindingResult -> 유효성 검사할때 필요로 했던 것.
	//@RequestParam("파라미터 이름 지정 -- input창에 등록한 name알아서 찾아와서 List타입으로 받아오는데 <MultipartFile>이라고 하는 타입으로 리스트를 받아옵니다. 
	//이 List에는 MultipartFile이라는 이름으로 이미지가 내가 등록한 수 만큼 저장됩니다."
	@PostMapping(value ="/admin/item/new")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, 
			Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
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
	
	//상품 수정 페이지 보기
	@GetMapping(value = "/admin/item/{itemId}")
	public String itemDtl(@PathVariable("itemId") Long itemId, Model model) { // @PathVariable : itemId가 {itemId} 안의 값으로 들어갑니다.
		try {
			ItemFormDto itemFormdto = itemService.getItemDtl(itemId);
			model.addAttribute(itemFormdto);
			
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
			model.addAttribute("itemFormDto", new ItemFormDto()); //에러가 발생한경우 빈 객체일 뿐이지만 itemFormDto를 넣어주긴 해야함(오브젝트 인식을위해서(?)) 
			return "item/itemForm";
		}
		return "item/itemForm";
	}
	
	//상품을 수정
	@PostMapping(value = "/admin/item/{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		//첫번째 이미지가 있는지 검사 (첫번째 이미지는 필수 입력값이기 때문에)
		if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "item/itemForm";
		}
		try {
			itemService.updateItem(itemFormDto, itemImgFileList);
		}catch (Exception e) {
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
			return "item/itemForm";
		}
		return "redirect:/";
	}	
	
	//상품관리 페이지 보기
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"}) //{주소A, 주소B} 어떤 주소로 들어와도 메소드를 실행함. 페이지 번호가 없는 경우와 있는 경우 2가지를 매핑.
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); 
										//of(조회할 페이지의 번호, 한페이지당 조회할 데이터의 개수)
										//page.isPresent()값이 존재하냐? 있으면 page.get() 을 띄워주고 그게 아니면 0을 띄움
		//url경로에 페이지가 있으면 해당 페이지를 조회하도록 하고, 페이지 번호가 없으면 0페이지를 조회하도록 합니다.
		
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		model.addAttribute("items", items);
		
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5);//상품 관리 메뉴 하단에 보여줄 최대 페이지 번호

		return "item/itemMng";
	}
		
		
		
}
