package com.myshop.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myshop.dto.OrderDto;
import com.myshop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	
	/*Order랑 그런데 저장하는것을 만들어뒀으니 컨트롤러에서는 서비스를 호출해야합니다.
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
	
}
