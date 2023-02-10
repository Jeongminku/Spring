package com.wss.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class BaseTimeEntity {
	@CreatedDate //엔티티가 생성되고 저장이 될 때 시간을 자동으로 저장
	@Column(updatable = false) //"이 컬럼은 수정이 불가능합니다"
	private LocalDateTime regTime; //등록날짜.
}
