package com.myshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.entity.Item;

//JpaRepository : 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어있습니다.
						//JpaRepository<사용할 엔티티 클래스, 해당엔티티의 기본속성타입>
public interface ItemRepository extends JpaRepository<Item, Long>{

}
