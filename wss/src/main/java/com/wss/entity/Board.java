package com.wss.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.wss.dto.BoardFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
public class Board {
	@Id
	@Column(name="board_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String content;

	@CreatedDate
	private LocalDateTime regTime;

	@Column(name ="board_img_Name")
	private String imgName;
	
	@Column(name ="board_img_url")
	private String imgUrl;
	
	@Column(name ="board_img_ori")
	private String imgOri;
	
	private String writer;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="broad_id")
	private Broad broad;
	
	public static Board createBoard(BoardFormDto boardFormDto) {
		Board board= new Board();
		board.setId(boardFormDto.getId());
		board.setTitle(boardFormDto.getTitle());
		board.setContent(boardFormDto.getContent());
		board.setWriter(boardFormDto.getWriter());
		board.setRegTime(boardFormDto.getRegTime());
		board.setBroad(boardFormDto.getBroad());
		return board;
	}
	
	public void updateImg(String imgName, String imgUrl,String imgOri) {
		this.imgOri = imgOri;
		this.imgUrl = imgUrl;
		this.imgName = imgName;
	}
}
