package com.wss.service;

import java.util.List;
import java.util.Optional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wss.dto.BoardFormDto;
import com.wss.entity.Board;
import com.wss.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
	@Value("${itemImgLocation}")
	private String imgLocation;
	
	private final FileService fileService;
	private final BoardRepository boardRepository;
	
	public void saveboardImg(Board board, MultipartFile imgfile) throws Exception {
		String oriImgName = imgfile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(imgLocation, oriImgName, imgfile.getBytes());
			imgUrl = "/images/item/"+imgName;
		}
		
		board.updateImg(imgName, imgUrl, oriImgName);
		
	}
	
	public void editboardImg(Board board, MultipartFile imgfile) throws Exception {
	    String oriImgName = imgfile.getOriginalFilename();
	    String imgName = board.getImgName();
	    String imgUrl = board.getImgUrl();

	    if (!StringUtils.isEmpty(oriImgName)) {
	        imgName = fileService.uploadFile(imgLocation, oriImgName, imgfile.getBytes());
	        imgUrl = "/images/item/" + imgName;
	    }

	    board.updateImg(imgName, imgUrl, oriImgName);
	}
	
	
	
	public void saveBoard(Board board) {
		boardRepository.save(board);
		
	}
	
	public List<Board> findByBroadId(Long broadId) {
		List<Board> boardList = boardRepository.findByBroadId(broadId); 
		return boardList;
	}
	
	public Page<Board> findByBroadIdPage(Long broadId, Pageable pageable) {
		Page<Board> boardList = boardRepository.findByBroadIdPage(broadId, pageable); 
		return boardList;
	}
	
	public Board findByBoardId(Long boardId) {
		Board board = boardRepository.findByBoardId(boardId);
		return board;
	}

}
