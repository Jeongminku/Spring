package com.wss.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.wss.entity.Board;
import com.wss.entity.Broad;
import com.wss.entity.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardFormDto {
	
	private Long id; //게시글 번호
	
	@NotBlank(message = "게시글 제목을 입력하세요")
	private String title;
	
	@NotBlank(message = "게시글 내용을 입력하세요")
	private String content;
	
	
	private String writer;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime regTime;
	
	
	private Broad broad;

	private Long memberId;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Board createBoard() {// 게시글을 저장(만들어줌)
		return modelMapper.map(this, Board.class);
	}
	
	public static BoardFormDto of(Board board) { //entity랑 Dto랑 맵핑
		return modelMapper.map(board, BoardFormDto.class);
	}
	
}
