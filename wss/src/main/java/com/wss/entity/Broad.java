package com.wss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wss.dto.BroadFormDto;
import com.wss.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="broad")
@Getter
@Setter
@ToString
public class Broad {
	
	@Id
	@Column(name="broad_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//방송정보(스트리머 only)
	@Column(name ="broad_info")
	private String broadInfo;
	
	
	//방송카테고리(스트리머 only)
	private String category;

	
	@OneToOne
	@JoinColumn(name="member_id")
	private Member memberId;
	
	
	
	public static Broad createBroad(BroadFormDto broadFormDto, Member member) {
		
		Broad broad = new Broad();
		broad.setBroadInfo(broadFormDto.getBroadInfo());
		broad.setCategory(broadFormDto.getCategory());

		//F키 값을 먼저 넣어줘야함.
		broad.setMemberId(member);
		
		return broad;
		
	}
}
