package com.wss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wss.constant.Role;
import com.wss.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
	
	List<Member> findByRole(Role role);
	
	Member findByImgName(String imgName);
}
