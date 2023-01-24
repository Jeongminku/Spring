package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.myshop.constant.ItemSellStatus;
import com.myshop.dto.ItemSearchDto;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
	//2. 사용자정의 인터페이스 상속받아주기 & 구현해주기.

	private JPAQueryFactory queryFactory;
	
	//생성자로 의존성 주입하기.
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now(); //현재 날짜와 시간을 구함. 검색시간을 기준으로 1일,1주일,1달 등 기간을 잡아야하기때문.
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
			return null;  //서치 데이트타입이 all 이거나 null이면 전체를 리턴함(?)
		} else if(StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		} else if(StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1); 
		} else if(StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);}
		
		return QItem.item.regTime.after(dateTime);
	}
	
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("itemNm", searchBy)) {
			return QItem.item.itemNm.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createBy", searchQuery)) {
			return QItem.item.createBy.like("%" + searchQuery + "%");
		}
		
		return null;
	}
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		List<Item> content = queryFactory.selectFrom(QItem.item) //select * from item 과 같은 뜻.
												 .where(regDtsAfter(itemSearchDto.getSearchDateType()), //where reg_time = ?     이라는 뜻과 유사해짐... 
														 												// 그냥 데이터만 주면 안되고 변환값을 설정 = regDtsAfter해줘야.
														 searchSellStatusEq(itemSearchDto.getSearchSellStatus()), //and sell status = ?
														 searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchBy())) //and itemNm Like %검색어%     
												 																				//ItemNm 이나 createBy로 들어오기때문에 그거에 대한 처리를 해줘야 함.
												 .orderBy(QItem.item.id.desc())
												 .offset(pageable.getOffset()) //데이터를 가져올 시작 index입니다.
												 .limit(pageable.getPageSize())//한번에 가지고 올 페이지 최대 개수 입니다.
												 .fetch();
		
		long total = content.size(); //전체 레코드 개수를 가져옵니다.
		
		return new PageImpl<>(content, pageable, total);
		
	}
	
}
