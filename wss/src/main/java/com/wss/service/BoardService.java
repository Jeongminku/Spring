package com.wss.service;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
			imgUrl = "/image/data/img/"+imgName;
		}
		
		board.updateImg(oriImgName, imgName, imgUrl);
		
	}
	
	public void saveBoard(Board board) {
		boardRepository.save(board);
		
	}
	
	
}
