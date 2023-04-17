package com.wss.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wss.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query(value = "select board_img_Name, board_id, board_img_ori, board_img_url, content, title, broad_id, reg_time, writer FROM board WHERE broad_id = :broId order by board_id desc", nativeQuery = true)
	List<Board> findByBroadId(@Param("broId") Long broadId);

	@Query(value = "select * FROM board WHERE broad_id = :broId order by board_id desc", nativeQuery = true)
	Page<Board> findByBroadIdPage(@Param("broId") Long broadId, Pageable pageable);

	@Query(value = "select board_img_Name, board_id, board_img_ori, board_img_url, content, title, broad_id, reg_time, writer FROM board WHERE board_id = :boaId ", nativeQuery=true)
	Board findByBoardId(@Param("boaId") Long boardId);	
	
}
