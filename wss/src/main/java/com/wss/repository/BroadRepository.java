package com.wss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wss.dto.BroadFormDto;
import com.wss.entity.Broad;
import com.wss.entity.Member;

public interface BroadRepository extends JpaRepository<Broad, Long>{
	List<BroadFormDto> findByBroad(BroadFormDto broadFormDto );
	
	Broad findByMemberId(Member memberId);
}
