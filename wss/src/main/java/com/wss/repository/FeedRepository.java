package com.wss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;

public interface FeedRepository extends JpaRepository<Feed, Long> {
	List<Feed> findById(Broad broadId);
	
	@Query(value = "select * from feed a join broad b on a.broad_id = b.broad_id order by feed_id desc", nativeQuery = true)
	List<Feed> Feedjoinbroad();
	
	
}
