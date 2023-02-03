package com.wss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wss.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
