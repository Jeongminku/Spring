package com.wss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.services.youtube.model.Video;
import com.wss.constant.Role;
import com.wss.dto.FeedDto;
import com.wss.dto.MemberStreamerDto;
import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;
import com.wss.repository.BroadRepository;
import com.wss.service.BroadService;
import com.wss.service.FeedService;
import com.wss.service.MemberService;
import com.wss.service.VideoService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final BroadService broadService;
	private final MemberService memberService;
	private final FeedService feedService;
	private final VideoService videoService;
	
	@GetMapping(value = "/")
	public String main(Model model) {
		Role Streamer = Role.STREAMER;
		

		List<MemberStreamerDto> memberStreamerDtoList = memberService.getMemberBroad(Streamer);
		model.addAttribute("members", memberStreamerDtoList);
		
		
		String email =  SecurityContextHolder.getContext().getAuthentication().getName();
		Member member = memberService.findByEmail(email);
		model.addAttribute("setmember", member);
//		System.out.println("셋멤버 시스아웃: " + member);
		
		return "main";
	}
	

//메인 - 방송국 페이지	
	@GetMapping(value = {"/view", "/view/{id}"})
	public String dtlpage(@PathVariable("id") Long memberid, Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함
		try {
			
			Member setmember = memberService.findByEmail(email);
			model.addAttribute("setmember", setmember);
			model.addAttribute("loginmember", setmember);
			
			Member member = memberService.getMember(memberid); //스트리머의 아이디를 통해서 스트리머 member객체를 가져옴.
			model.addAttribute("member", member);
			Broad broad = broadService.findById(member.getId());
			model.addAttribute("broad", broad);
			List <Feed> feedList = feedService.Feedjoinbroad();
			List <Feed> listtest = new ArrayList<>();
			for(int i=0; i<feedList.size(); i++) {
				if(feedList.get(i).getBroad().getId() == broad.getId()) {
					listtest.add(feedList.get(i));
				}
			}
			model.addAttribute("feed", feedList);
			
			model.addAttribute("feed1", listtest);
			
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "게시물 / 사용자를 불러올 수 없습니다.");
			return "main";
		}
		
		
        List<Video> videos = videoService.getMostPopularVideos();
        model.addAttribute("videos", videos);
	  
		
		return "/broad/broadDtl";
	}

	
//피드 작성	
	@PostMapping(value = {"/view", "/view/{id}"})	
	public String feed(@RequestBody String feed, Model model, @PathVariable("id") Long broadId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함
		try {
			Member member = memberService.findByEmail(email);
			Broad broad = broadService.findById(broadId);
			Feed feed1 = Feed.createFeed(feed, member, broad);
			feedService.saveFeed(feed1);
			
			List<Feed> listFeed = feedService.Feedjoinbroad(); //새로 댓글리스트를 다시 불러옴.
			model.addAttribute("feed1", listFeed);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "Feed를 작성할 수 없었습니다.");
			return "member/memberForm";
		}
		
		return "/broad/broadDtl :: feedReview"; //fragment를 돌려줌.
	}
	
//피드 삭제
	@GetMapping(value = {"/del/{feedId}"})
	public String delFeed(@PathVariable("feedId") Long FeedId, Long pageId, Model model, HttpServletResponse resp) {
		try {
			feedService.feedDel(FeedId);
		}catch (Exception e) {
			model.addAttribute("errorMessage", "Feed 삭제중 에러가 발생하였습니다.");
			return AlertMethod.redirectAfterAlert("실패실패.", "/", resp);
		}
		return AlertMethod.redirectAfterAlert("Feed가 삭제되었습니다.", "/"+pageId, resp);
	}
	
}
