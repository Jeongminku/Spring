package com.wss.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.wss.entity.BroadcastImg;

import lombok.*;

@Getter
@Setter
public class BroadcastFormDto {
	
	private Long id;
	
	@NotBlank(message ="방송하시는 주소는 꼭 입력해주세요")
	private String streamUrl;	//트위치 주소

	private String streamOriImg; //오리지널 파일명
	
	private String streamImg;   //이미지
	
	private String streamImgUrl; //이미지 주소

	@NotBlank(message ="방송국 소개를 꼭 입력해주세요")
	private String streamInfo;
	
	@NotBlank(message ="방송국이름을 꼭 지어주세요")
	private String streamName;
	
	private List<BroadcastImgDto> broadcastImgDtoList = new ArrayList<>();
	
	private List<Long> broadcastImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//??
	public static BroadcastFormDto of(BroadcastImg broadcastImg) {
		return modelMapper.map(broadcastImg, BroadcastFormDto.class);
	}
}
