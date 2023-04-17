package com.wss.controller;

import java.io.File;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wss.dto.BoardFormDto;
import com.wss.entity.Board;
import com.wss.entity.Broad;
import com.wss.entity.Feed;
import com.wss.entity.Member;
import com.wss.service.BoardService;
import com.wss.service.BroadService;
import com.wss.service.FeedService;
import com.wss.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final MemberService memberService;
	private final BroadService broadService;
	private final BoardService boardService;
	
	@GetMapping(value ="/board/{id}")
	public String boardpage2(@PathVariable("id") Long memberid, Model model, Optional<Integer> page ) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 8); //of는 (int page, int size)
		
		model.addAttribute("maxPage", 5);
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
			//로그인한 멤버의 정보가 필요할때 데려올 setmember
			Member setmember = memberService.findByEmail(email);
			model.addAttribute("setmember", setmember);
			
			
			Member member = memberService.getMember(memberid); //스트리머의 아이디를 통해서 스트리머 member객체를 가져옴.
			model.addAttribute("member", member);
			
			//멤버 아이디로 broad(게시판)넘버를 찾기.
			Long broadId = broadService.findBroadId(memberid);
			Page <Board> paging = boardService.findByBroadIdPage(broadId, pageable);
			List<Board> boardList = paging.getContent();
			//List<Board> boardList = boardService.findByBroadId(broadId);
			model.addAttribute("boardList", boardList);
			model.addAttribute("paging", paging);
			model.addAttribute("page", pageable.getPageNumber());
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "미디어를 불러올 수 없습니다.");
			return "main";
		}
		return "broad/broadBoard";
	}

//	@PostMapping(value ="/board/{id}")
//	public String boardpage(@PathVariable("id") Long memberid, Model model) {
//		
//		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
//		try {
//			//로그인한 멤버의 정보가 필요할때 데려올 setmember
//			Member setmember = memberService.findByEmail(email);
//			model.addAttribute("setmember", setmember);
//			
//			
//			Member member = memberService.getMember(memberid); //스트리머의 아이디를 통해서 스트리머 member객체를 가져옴.
//			model.addAttribute("member", member);
//			
//			//멤버 아이디로 broad(게시판)넘버를 찾기.
//			Long broadId = broadService.findBroadId(memberid);
//			List<Board> boardList = boardService.findByBroadId(broadId);
//			model.addAttribute("boardList", boardList);
//			
//		} catch (EntityNotFoundException e) {
//			model.addAttribute("errorMessage", "미디어를 불러올 수 없습니다.");
//			return "main";
//		}
//		return "broad/broadBoard";
//	}
	
	@GetMapping(value ="/board/fanart/{id}")
	public String boardView2(@PathVariable("id") Long boardId, @RequestParam("broadId") Long broadId, Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
			//로그인한 멤버의 정보
			Member setmember = memberService.findByEmail(email);
			model.addAttribute("setmember", setmember);
			
			Board board = boardService.findByBoardId(boardId);
			model.addAttribute("boardInfo", board);

			//내가 접속한 스트리머의 정보.
			Member member = memberService.getMember(board.getBroad().getMember().getId());
			model.addAttribute("member", member);
			

		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "팬아트를 불러올 수 없습니다.");
			return "main";
		}
		return "broad/broadBoardView";
	}
	
	@PostMapping(value ="/board/write/{id}")
	public String boardWrite(@PathVariable("id") Long memberid, Model model, BoardFormDto boardFormDto) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
			//로그인한 멤버의 정보가 필요할때 데려올 setmember
			Member setmember = memberService.findByEmail(email);
			model.addAttribute("setmember", setmember);
			
			
			Member member = memberService.getMember(memberid); //스트리머의 아이디를 통해서 스트리머 member객체를 가져옴.
			model.addAttribute("member", member);
			
			boardFormDto = new BoardFormDto();
			
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "글쓰기 페이지를 불러올 수 없습니다.");
			return "main";
		}
		return "broad/broadBoardWrite";
	}
	
	@GetMapping(value ="/board/edit/{id}")
		public String boardEditng(@PathVariable("id") Long boardId, Model model) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //View에서 로그인한 아이디랑 이 멤버의 아이디가 비교가 필요하면 필요함 
		try {
			//로그인한 멤버의 정보가 필요할때 데려올 setmember
			Member setmember = memberService.findByEmail(email);
			model.addAttribute("setmember", setmember);

			Board board = boardService.findByBoardId(boardId);
			model.addAttribute("editboard", board);
			System.err.println("editboard: " + board);

			//내가 접속한 스트리머의 정보.
			Member member = memberService.getMember(board.getBroad().getMember().getId());
			model.addAttribute("member", member);
			
			BoardFormDto boardFormDto = new BoardFormDto();
			model.addAttribute("boardFormDto",boardFormDto);
			
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "수정페이지 불러오기 실패");
			return "main";
			
		}
		
			return "broad/broadBoardEdit";
		}
	
	@PostMapping(value ="/board/edit/{boardId}")
	public String boardEdited(@PathVariable("boardId") Long boardId, BoardFormDto boardFormDto, BindingResult bindingResult, HttpServletResponse resp, @RequestParam("uploadFile") MultipartFile file, @RequestParam("broadId") Long broadId) throws Exception {
		
		//입력된 값이 에러뜨면 메인으로 팅굼
		if (bindingResult.hasErrors() ) {
			return "main";
		}
		
		try {
			Board oldboard = boardService.findByBoardId(boardId);
			
			oldboard.setTitle(boardFormDto.getTitle());
			oldboard.setContent(boardFormDto.getContent());
			
			boardService.editboardImg(oldboard, file);
			

			//게시글 업데이트완료
			boardService.saveBoard(oldboard);
		
		} catch (EntityNotFoundException e) {
				
				return AlertMethod.redirectAfterAlert("팬아트 업데이트 실패", "/", resp);			
			}
		
			return AlertMethod.redirectAfterAlert("정상적으로 수정되었습니다.", "/board/"+broadId, resp);
					
		}
	
	
	
	@PostMapping(value ="/board/save")
	public String saveBoard(@Valid BoardFormDto boardFormDto,BindingResult bindingResult, Principal principal, @RequestParam("uploadFile") MultipartFile file, Model model, @RequestParam("broadId") Long broadId) throws Exception {
	 
		if(bindingResult.hasErrors()) {
			return "main";
		}
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		boardFormDto.setRegTime(LocalDateTime.now());
		boardFormDto.setWriter(memberService.findByEmail(email).getNickname());
		boardFormDto.setBroad(broadService.findById(broadId));
		Board board = Board.createBoard(boardFormDto);
		if (file != null) {
			boardService.saveboardImg(board, file);
		}
		
		boardService.saveBoard(board);
		return "redirect:/board/"+ broadId;		
	}
	
	
}
