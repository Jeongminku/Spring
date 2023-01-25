package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByItemNm(String itemNm);
}
