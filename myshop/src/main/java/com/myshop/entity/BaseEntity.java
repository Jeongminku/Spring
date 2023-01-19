package com.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해서 사용하는 @어노테이션 입니다.
@MappedSuperclass //부모 클래스를 상속받는 자식 클래스에게 매핑정보를 제공합니다.
@Getter
public class BaseEntity extends BaseTimeEntity {
	
	@CreatedBy
	@Column(updatable = false)
	private String createBy;  //등록자
	
	@LastModifiedBy
	private String modifiedBy;//수정자
}
