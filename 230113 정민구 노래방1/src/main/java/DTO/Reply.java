package DTO;

import java.sql.Date;

public class Reply {
	int commentno;
	int songno;
	String userid;
	String rep_content;
	Date rep_date;
	
	public int getCommentno() {
		return commentno;
	}
	public void setCommentno(int commentno) {
		this.commentno = commentno;
	}
	public int getSongno() {
		return songno;
	}
	public void setSongno(int songno) {
		this.songno = songno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRep_content() {
		return rep_content;
	}
	public void setRep_content(String rep_content) {
		this.rep_content = rep_content;
	}
	public Date getRep_date() {
		return rep_date;
	}
	public void setRep_date(Date rep_date) {
		this.rep_date = rep_date;
	}
	
	
}
