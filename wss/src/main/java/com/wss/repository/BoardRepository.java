package com.wss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wss.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
