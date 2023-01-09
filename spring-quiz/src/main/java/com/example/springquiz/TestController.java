package com.example.springquiz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping(value="/student1")
	public StudentDto student1() {
		StudentDto studentDto = new StudentDto();
		studentDto.setName("yuna");
		studentDto.setAge(20);
		studentDto.setJavaGrade("A+");
		studentDto.setOracleGrade("C");
		
		System.out.println("객체정보 :" + studentDto.toString());
		
		return studentDto;
	}
	@GetMapping(value="/student2")
	public StudentDto student2() {
		StudentDto studentDto = new StudentDto();
		studentDto.setName("jimin");
		studentDto.setAge(21);
		studentDto.setJavaGrade("B+");
		studentDto.setOracleGrade("F");
		
		System.out.println("객체정보 :" + studentDto.toString());
		
		return studentDto;
	}
}
