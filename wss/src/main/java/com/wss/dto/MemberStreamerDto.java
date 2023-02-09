package com.wss.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.wss.constant.Role;
import com.wss.entity.Broad;
import com.wss.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberStreamerDto {
    
	public MemberStreamerDto(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.imgUrl = member.getImgUrl();
        this.nickname = member.getNickname();
        this.category = member.getCategory();
    }
    
	private Long id;
	
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String nickname;
	
	//프사 이미지 파일명
	private String imgName;
	
	//프사 이미지 주소
	private String imgUrl;
	
	//프사 원본 이미지명
	private String imgOri;
	
	//방송소개(스트리머 only)
	private String broadInfo;
	
	//방송 카테고리(스트리머 only)
	private String category;
	
	private BroadFormDto broadFormDto = new BroadFormDto();
	
	//매핑용.
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Member createMember() {
		return modelMapper.map(this, Member.class);
	}
	
	//스트리머의 정보(broadFormDto)를 broadFormDtoList에 담아준다.
	/*
	 * public void addBroadFormDtoList(BroadFormDto broadFormDto){
	 * broadFormDtoList.add(broadFormDto); }
	 */
}
