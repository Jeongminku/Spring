package com.myshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//업로드한 파일을 읽어올 경로를 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${uploadPath}")//application.properties 프로퍼티의 업로드 경로(uploadPath) 값을 가져옵니다.
	String uploadPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**") //웹 브라우저에 입력하는 url /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어옵니다.
				.addResourceLocations(uploadPath); //images의 경로로 시작되는 모든 것들은 uploadPath에서 찾아오겠다는 뜻입니다.
	}
	
}
