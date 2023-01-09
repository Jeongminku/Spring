package com.example.springquiz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StudentDto {
	String name;
	int age;
	String javaGrade;
	String oracleGrade;
}
