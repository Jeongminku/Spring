package com.wss.entity;

import javax.persistence.*;


import lombok.*;

@Entity
@Table(name="broadcast")
@Getter
@Setter
public class BroadcastImg {

	@Id
	@Column(name="stream_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="stream_url")
	private String streamUrl; //트위치 경로
	
	@Column(name="stream_img")
	private String streamImg; //이미지 파일명
	
	@Column(name="stream_img_url")
	private String streamImgUrl; //이미지 주소

	@Column(name="stream_ori_img")
	private String streamOriImg; //원본 이미지 파일명 
	
	
	@Column(name="stream_info")
	private String streamInfo;
	
	@Column(name="stream_name")
	private String streamName;
	
	public void updateBroadcastImg(String streamOriImg, String streamImg, String streamImgUrl) {
		this.streamOriImg = streamOriImg;
		this.streamImg = streamImg;
		this.streamImgUrl = streamImgUrl;
	}
	
}
