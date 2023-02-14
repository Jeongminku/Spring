package com.wss.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wss.entity.Broad;
import com.wss.entity.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	List<Feed> findById(Broad broadId);
	
	@Query(value = "select * from feed a join broad b on a.broad_id = b.broad_id order by feed_id desc", nativeQuery = true)
	List<Feed> Feedjoinbroad();

	@Modifying
	@Transactional
	@Query(value = "delete from feed where feed.member_id = :memId", nativeQuery = true)
	void feedDel(@Param("memId") Long Id);
	
	
}
