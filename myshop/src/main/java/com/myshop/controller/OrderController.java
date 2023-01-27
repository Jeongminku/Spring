package com.myshop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myshop.dto.OrderDto;
import com.myshop.dto.OrderHistDto;
import com.myshop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	
	/* 비동기
	 * Order랑 그런데 저장하는것을 만들어뒀으니 컨트롤러에서는 서비스를 호출해야합니다.
	 *비동기 ajax를 보기위해서 잠깐 ItemDtl.html 보겠습니다. 
	 *96번째줄(?)부터 주문하기 버튼눌렀을때 비동기 처리하는것부터 작성하겠습니다.
	 * */
	@PostMapping(value = "/order")
						/*ResponseEntity: HTTP요청(리퀘스트) 또는 응답(리스폰스)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스.
						 				앞에 값을 보내줘야할때 자기에게(?)가(?) 값을 담는다(?) & 앞단에 값을 보내준다*/
						//Principal: 현재 로그인한 사용자의 정보를 얻을 수 있는 객체 입니다.
	public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {  
		if(bindingResult.hasErrors()) {
			//에러 메시지를 따로 만들어서 responseEntity에 넣어서 보내주어야 합니다.
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); //생성자를 작성합니다.  앞에는 ResponseEntity<body의 데이터타입>(앞 body, 뒤 HttpStatus Status)가 옵니다
		}	//에러가 발생하면 ItemDtl.html의 function이 도는데 error:~~ 이 작동하게됩니다. (Validation 에러처리 끝!)
		
		//그다음에는 서비스를 호출하면 됩니다.
		String email = principal.getName(); //사용자의 이름을 가져옵니다. 우리는 memberService에서 usernameParameter를 Email로 지정했기때문에 Email을 가져올 것입니다.
		Long orderId;
		
		try { //서비스를호출하겠습니다.
			orderId = orderService.order(orderDto, email);
			
		} catch(Exception e) { //에러가 났을때 처리하겠습니다.
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		
		//에러없이 성공적으로 끝났다면
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
		
	}
	
	@GetMapping(value = {"/orders", "/orders/{page}"})
	public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		
		Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);
		
		model.addAttribute("orders",orderHistDtoList);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "order/orderHist";
	}
	
	//비동기
	@PostMapping(value = "/order/{orderId}/cancel") //PathVariable: Path에 있는 orderId를 가져와서 Long orderId에 넣어주겠다.
	public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
		//현재 로그인한 사용자와 주문데이터를 생성한 사용자가 같은지 검사를 먼저하고 주문데이터 취소를 할 수 있게 할 것입니다.
		
		if(!orderService.validateOrder(orderId, principal.getName())) {
			return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		orderService.cancelOrder(orderId);
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	}
	
	@DeleteMapping("/order/{orderId}/delete")
	public @ResponseBody ResponseEntity deleteOrder(@PathVariable("orderId") Long orderId, Principal principal) {
		if(!orderService.validateOrder(orderId, principal.getName())) {
			return new ResponseEntity<String>("주문 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		orderService.deleteOrder(orderId);
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	}
}
