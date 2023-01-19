package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해서 사용하는 @어노테이션 입니다.
@MappedSuperclass //부모 클래스를 상속받는 자식 클래스에게 매핑정보를 제공합니다.
@Getter
@Setter
public class BaseTimeEntity {

	@CreatedDate //엔티티가 생성되서 저장이 될 때 시간을 자동으로 저장해줍니다.
	@Column(updatable = false) //"이 컬럼은 수정이 불가능합니다" 상태로 만들어 줍니다.
	private LocalDateTime regTime; //등록날짜.
	
	@LastModifiedDate
	private LocalDateTime upDateTime; //수정날짜.
}
