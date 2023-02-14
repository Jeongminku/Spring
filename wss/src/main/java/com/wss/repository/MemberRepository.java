package com.wss.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wss.constant.Role;
import com.wss.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
	
	List<Member> findByRole(Role role);
	
	Member findByImgName(String imgName);
	
	@Modifying
	@Transactional
	@Query(value = "delete from member where member.member_id = :memId", nativeQuery = true)
	void memberDel(@Param("memId") Long Id);
}
