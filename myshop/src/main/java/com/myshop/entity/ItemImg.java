package com.myshop.entity;

import javax.persistence.*;

import lombok.*;

@Entity 				//이것을 Entity클래스로 사용할 것이라고 어노테이션합니다.
@Table(name="item_img") 	//테이블명을 설정합니다.
@Getter 				//Getter,Setter,ToString 롬복을 사용합니다.
@Setter
public class ItemImg extends BaseEntity {

	@Id
	@Column(name="item_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	@Column(name="img_name")
	private String imgName; //이미지 파일명
	
	@Column(name="ori_img_name")
	private String oriImgName; //원본 이미지 파일명
	
	@Column(name="img_url")
	private String imgUrl; //이미지 조회 경로
	
	@Column(name="rep_img_yn")
	private String repimgYn; //대표 이미지 여부
	
	//원본 이미지의 파일명, 업데이트 할 이미지의 파일명, 이미지 경로를 파라메터로 받아서 이미지 정보를 업데이트 하는 메소드 입니다.
	public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
	
	
}
