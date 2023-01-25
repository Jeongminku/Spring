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
import com.myshop.dto.MainItemDto;
import com.myshop.dto.QMainItemDto;
import com.myshop.entity.Item;
import com.myshop.entity.QItem;
import com.myshop.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
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
			return QItem.item.itemNm.like("%" + searchQuery + "%"); //itemNm LIKE %청바지%
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QItem.item.createdBy.like("%" + searchQuery + "%"); //createdBy LIKE %test.com%
		}
		
		return null;
	}
		
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		List<Item> content = queryFactory.selectFrom(QItem.item) //select * from item 과 같은 뜻.
												 .where(regDtsAfter(itemSearchDto.getSearchDateType()), //where reg_time = ?     이라는 뜻과 유사해짐... 
														 												// 그냥 데이터만 주면 안되고 변환값을 설정 = regDtsAfter해줘야.
														 searchSellStatusEq(itemSearchDto.getSearchSellStatus()), //and sell status = ?
														 searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())) //and itemNm Like %검색어%     
												 																				//ItemNm 이나 createBy로 들어오기때문에 그거에 대한 처리를 해줘야 함.
												 .orderBy(QItem.item.id.desc())
												 .offset(pageable.getOffset()) //데이터를 가져올 시작 index입니다.
												 .limit(pageable.getPageSize())//한번에 가지고 올 페이지 최대 개수 입니다.
												 .fetch();
		
		long total = queryFactory.select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne();
		
		return new PageImpl<>(content, pageable, total);
		
	}

	
	private BooleanExpression itemNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery +"%");
				
	}
	
	@Override
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		QItem item = QItem.item;
		QItemImg itemImg = QItemImg.itemImg;

		List<MainItemDto> content = queryFactory.select(
				new QMainItemDto(
						item.id,
						item.itemNm,
						item.itemDetail,
						itemImg.imgUrl,
						item.price)
					)
					.from(itemImg)
					.join(itemImg.item, item) //item을 통해서 2개의 테이블을 조인함.
					.where(itemImg.repimgYn.eq("Y"))
					.where(itemNmLike(itemSearchDto.getSearchQuery()))
					.orderBy(item.id.desc())
					.offset(pageable.getOffset())	//데이터를 가져올 시작 index(공식처럼 사용함)
					.limit(pageable.getPageSize())	//한번에 가지고 올 최대 개수(공식처럼 사용함)
					.fetch();	
		//모든 레코드의 개수를 구함. Wildcard.count = count()
		long total = queryFactory.select(Wildcard.count).from(itemImg)
				.join(itemImg.item, item) //item을 통해서 2개의 테이블을 조인함.
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmLike(itemSearchDto.getSearchQuery()))
				.fetchOne(); //count끝낼때는 fetchOne
		
		//PageImpl 리턴 시 스프링에서 자동으로 페이징 해결함.
		return new PageImpl<>(content, pageable, total);
	}	
}
