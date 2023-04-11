package com.wss.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.services.youtube.model.SearchResult;
import com.wss.entity.Member;
import com.wss.service.BroadService;
import com.wss.service.FeedService;
import com.wss.service.MemberImgService;
import com.wss.service.MemberService;
import com.wss.service.VideoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class VideoController {
	
	private final VideoService videoService;
	private final MemberService memberService;
	
	@GetMapping("/search/{memberId}")
	public String searchVideos(@RequestParam String query, @RequestParam long number, Model model, @PathVariable Long memberId) {
		try {
			Member member = memberService.getMember(memberId);
			model.addAttribute("member", member);
		}catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "member를 불러올 수 없었습니다.");
			return "main";
		}
		
		List<SearchResult> searchResults = videoService.searchVideos(query, number);
		model.addAttribute("searchResults", searchResults);
		
		return "/broad/broadMedia";
	}
}
