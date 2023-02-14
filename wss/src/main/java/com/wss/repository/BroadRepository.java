package com.wss.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wss.entity.Broad;

public interface BroadRepository extends JpaRepository<Broad, Long>{
	//List<BroadFormDto> findByBroad(BroadFormDto broadFormDto );
	
	Broad findByMemberId(Long memberId); //여기야2
	
	@Modifying
	@Transactional
	@Query(value = "delete from broad where broad.member_id = :memId", nativeQuery = true)
	void broadDel(@Param("memId") Long Id);
}
