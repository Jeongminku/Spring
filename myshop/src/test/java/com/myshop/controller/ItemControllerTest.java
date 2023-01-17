package com.myshop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc //실제 객체와 비슷하지만 테스트에 필요한 기능만 제공하는 가짜 객체생성
					  //-> 웹브라우저에서 요청을 하는 것처럼 작성이 가능합니다.
@TestPropertySource(locations="classpath:application-test.properties")
class ItemControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@DisplayName("상품 등록 페이지 권한 테스트")
	@WithMockUser(username = "admin", roles = "ADMIN") //유저가 로그인된 상태로 테스트 할 수 있게 해준다.
	public void itemFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //이 페이지로 get방식으로 request합니다.
        .andDo(print()) //request(요청)과 그에대한 응답 메시지를 확인(출력)할 수 있습니다.
        .andExpect(status().isOk()); //응답 상태 코드가 정상인지 확인 후 정상이면 테스트를 통과합니다.
		
	}

	@Test
	@DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
	@WithMockUser(username = "user", roles = "User") //유저가 로그인된 상태로 테스트 할 수 있게 해준다.
	public void itemFormNotAdminTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //이 페이지로 get방식으로 request합니다.
		.andDo(print()) //request(요청)과 그에대한 응답 메시지를 확인(출력)할 수 있습니다.
		.andExpect(status().isForbidden()); //응답 상태 코드가 Forbidden 예외가 발생하면 테스트를 통과합니다.
		
	}
}
