package com.trable.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trable.dto.MemberFormDto;
import com.trable.dto.PostFormDto;
import com.trable.dto.PostSearchDto;
import com.trable.entity.Member;
import com.trable.entity.Post;
import com.trable.service.MemberService;
import com.trable.service.PostImgService;
import com.trable.service.PostService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {

	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final PostService postservice;
	private final PostImgService postImgService;
	
	@Value("${ImgLocation}")
	private String imgLocation;
	
	// MAINPAGE
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	// OPEN WRITING PAGE
	@GetMapping(value = "/write")
	public String write(Model model) {

		model.addAttribute("postFormDto", new PostFormDto());
		return "/user/writingpage";
	}
	
	// CLICK WRITING BUTTON
	@PostMapping(value = "/write")
	public String writenew(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model,
			@RequestParam("PostImgFile") List<MultipartFile> postImgFileList,
			@RequestParam("MainImgFile") MultipartFile postMainImg) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		if(bindingResult.hasErrors()) {
			return "/user/writingpage";
		}
		if(postImgFileList.get(0).isEmpty() && postFormDto.getPostname() == null) {
			model.addAttribute("errorMessage", "게시글에 최소 1장의 사진을 업로드 해주세요.");
		}
		// INPUT MAIN POSTIMG
		try {
			postservice.savePost(postFormDto, postImgFileList, postMainImg, email);
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시물 업로드 중 에러가 발생했습니다!");
			return "/user/writingpage";
		} 
		return "redirect:/";
	}
	
	@GetMapping(value = "/view")
	public String dtlpage() {
		return "/travel/dtlpage";
	}
	
	// OPEN USERPAGE
	@GetMapping(value = "/user")
	public String userpage(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails user = memberService.loadUserByUsername(id);
		Member member = memberService.findMember(user.getUsername());	
		List<Post> memberpost = postservice.getUserPost(member);
		int heart = 0;
		
		for(int i =0; i<memberpost.size(); i++) {
			heart += memberpost.get(i).getHeart();
		}
		
		model.addAttribute("heart", heart);
		model.addAttribute("member", member);
		model.addAttribute("posts", memberpost);
		return "/user/userpage";
	}
	
	// SEARCH PAGE
	@GetMapping(value = "/find")
	public String searchpage(PostSearchDto postSearchDto, Model model) {
		
		List<Post> post = postservice.getPostPage();
		
		model.addAttribute("posts",post);
		model.addAttribute("imgLocation",imgLocation);
		return "/travel/searchpage";
	}
	@GetMapping(value = "/like")
	public String likepage() {
		return "/travel/likepage";
	}
	@GetMapping(value = "/setting")
	public String settingpage() {
		return "/user/usersettingpage";
	}
}
