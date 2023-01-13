package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.*;

public class SongDAO {
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
    //메소드 방식    , open()메소드는 데이터 베이스와의 연결 수행 메소드입니다.
    public Connection open( ) {
    	Connection conn = null;
    	try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234");
		} catch (Exception e) {
			e.printStackTrace();
			
		}
    	
    	return conn; //데이터 베이스의 연결객체를 리턴합니다.
    	
    }
	
	public ArrayList<Result> getList(HttpServletRequest request) throws Exception {
		Connection conn = open();
		ArrayList<Result> songList = new ArrayList<Result>();
		String songtitle = request.getParameter("songtitle");
		String sql= "select songno, title, singer from song where title like '%"+songtitle+"%'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try (conn; pstmt; rs;) {
			while (rs.next()) {
				Result s = new Result();
				s.setSongno(rs.getInt(1));
				s.setSongtitle(rs.getString(2));
				s.setSinger(rs.getString(3));
				
				songList.add(s);
			}
		}
		
		return songList;
	}
	
	public ArrayList<Result> getAllList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = open();
		ArrayList<Result> allList = new ArrayList<Result>();
		String sql = "select songno, title, singer from song order by songno";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs;) {
			while (rs.next()) {
				Result s = new Result();
				s.setSongno(rs.getInt(1));
				s.setSongtitle(rs.getString(2));
				s.setSinger(rs.getString(3));
				
				allList.add(s);
			}
		}
		return allList;
		
		
	}
	
	public int insertSong(Result s) throws Exception {
		Connection conn = open();
		int result = 0;
		String sql1 = "select songno from song where songno = " + s.getSongno();
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		ResultSet rs = pstmt1.executeQuery();
		System.out.println(sql1);
		String sql2 = "insert into song (songno, title, singer, yaddress) values (?,?,?,?)";
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		try(pstmt1; rs;) {
			if(rs.next()) {
				result = 0;
				System.out.println("pstmt1 rs.next의 result" + result);
			} else {
		try(conn; pstmt2;) {
			pstmt2.setInt(1, s.getSongno());			
			pstmt2.setString(2, s.getSongtitle());			
			pstmt2.setString(3, s.getSinger());			
			pstmt2.setString(4, s.getYaddress());
			result = pstmt2.executeUpdate();
			System.out.println("pstmt2 rs.next의 result222" + result);
				}
			}
		}
		return result;
	}

	
	public Result select(int songno) throws Exception {
		Connection conn = open();
		Result s = new Result();
		String sql = "select songno, title, yaddress, singer from song where songno = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, songno);
		ResultSet rs = pstmt.executeQuery();
		
		
		try(conn; pstmt; rs;) {
			if (rs.next()) {
				s.setSongno(rs.getInt(1));
				s.setSongtitle(rs.getString(2));
				s.setYaddress(rs.getString(3));
				s.setSinger(rs.getString(4));
				
				}
			
			}
			return s;
		}
	
	public String select_Error(int songno) throws Exception {
		Connection conn = open();
		String result = "no"; 
		String sql = "select songno, title, yaddress, singer from song where songno = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, songno);
		ResultSet rs = pstmt.executeQuery();
		
		
		try(conn; pstmt; rs;) {
			if (rs.next()) {
				result = "yes";				
			}
		}
		return result;
	}
	

	
	public int reply(Reply r) throws Exception {
//		int result = 0;
		Connection conn = open();
		String sql = "insert into songreply(commentno, songno, userid, rep_content, rep_date ) values(AUTOPLUS.nextval, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt) {
			pstmt.setInt(1, r.getSongno());
			pstmt.setString(2, r.getUserid());
			pstmt.setString(3, r.getRep_content());
			
			pstmt.executeUpdate();
			
			
		}
		return r.getSongno();
	}
	
	public ArrayList<Reply> getreplyList(HttpServletRequest request) throws Exception {
		Connection conn = open();
		ArrayList<Reply> replyList = new ArrayList<Reply>();
		String sql = "select userid, rep_content, commentno, songno, rep_date from songreply where songno = ? order by commentno";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(request.getParameter("songno")));
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs;) {
			while (rs.next()) {
				Reply r = new Reply();
				r.setUserid(rs.getString(1));
				r.setRep_content(rs.getString(2));
				r.setCommentno(rs.getInt(3));
				r.setSongno(rs.getInt(4));
				r.setRep_date(rs.getDate(5));
				replyList.add(r);
			}
		}
		return replyList;
		
		
	}
	
	public void deleteReply(int commentno) throws Exception {
		Connection conn = open();
		
		String sql = "delete from songreply where commentno=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
			pstmt.setInt(1, commentno);
		
			if (pstmt.executeUpdate() != 1) {
				throw new Exception("삭제된글이 없습니다");
			}
		}
	}
	
	public Result getSongForEdit(int songno) throws Exception {
		Connection conn = open();

		Result s = new Result();
		String sql = "select songno, title, singer, yaddress from song where songno=?";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, songno);
		ResultSet rs = pstmt.executeQuery();

		rs.next();

		try (conn; pstmt; rs) {
			s.setSongno(rs.getInt(1));
			s.setSongtitle(rs.getString(2));
			s.setSinger(rs.getString(3));
			s.setYaddress(rs.getString(4));			

			pstmt.executeQuery();
			return s;
		}
	}
	
	public void updateSong(Result s) throws Exception {
		Connection conn = open();
		String sql = "update song set title = ?, singer = ?, yaddress = ? where songno = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn;pstmt) {
			pstmt.setString(1, s.getSongtitle());
			pstmt.setString(2, s.getSinger());
			pstmt.setString(3, s.getYaddress());
			pstmt.setInt(4, s.getSongno());
			pstmt.executeUpdate();
		}		
	}
	

	
	public void deleteSongReply(int songno) throws Exception {
		Connection conn = open();
		
		String sql = "delete from songreply where songno=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
			pstmt.setInt(1, songno);
			pstmt.executeUpdate();
			}
		}
	public void deleteSong(int songno) throws Exception {
		Connection conn = open();
		
		String sql = "delete from song where songno=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
			pstmt.setInt(1, songno);
			pstmt.executeUpdate();
		}
	}
}
	/*
public ArrayList<Result> songnoList(HttpServletRequest request) throws Exception {
	Connection conn = open();
	ArrayList<Result> songnoList = new ArrayList<Result>();
	String songno = request.getParameter("songno");
	String sql= "select songno, title, singer from song where songno="+ songno;
	PreparedStatement pstmt = conn.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	
	try (conn; pstmt; rs;) {
		while (rs.next()) {
			Result s = new Result();
			s.setSongno(rs.getInt(1));
			s.setSongtitle(rs.getString(2));
			s.setSinger(rs.getString(3));
			
			songnoList.add(s);
		}
	}
	
	return songnoList;
	}
	*/




	/*
	public String search1(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Result> searchResult = new ArrayList<Result>();	
		try {
			conn = getConnection();
			String songtitle = request.getParameter("songtitle");
			String sql = "select songno, title, singer from song where title like '%"+songtitle+"%'";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Result songList = new Result();
				songList.setSongno(rs.getInt(1));
				songList.setSongtitle(rs.getString(2));
				songList.setSinger(rs.getString(3));
				
				searchResult.add(songList);
			}
			
			request.setAttribute("list", searchResult);
			
			
			conn.close();
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "searchResult.jsp";
	}
	
	public String showlist(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Result> showlist = new ArrayList<Result>();	
		try {
			conn = getConnection();
			String sql = "select songno, title, singer from song order by songno";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Result songList = new Result();
				songList.setSongno(rs.getInt(1));
				songList.setSongtitle(rs.getString(2));
				songList.setSinger(rs.getString(3));;
				
				showlist.add(songList);
			}
			
			request.setAttribute("showlist", showlist);
			
			
			conn.close();
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "songlist.jsp";
	}
	
	public int insert(HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		try {
			conn = getConnection();
			int songno = Integer.parseInt(request.getParameter("songno"));
			String songtitle = request.getParameter("songtitle");
			String singer = request.getParameter("singer");
			String yaddress = request.getParameter("yaddress");
			
			String sql = "insert into song (songno, title, singer, yaddress) values (?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, songno);
			ps.setString(2, songtitle);
			ps.setString(3, singer);
			ps.setString(4, yaddress);
			
			result = ps.executeUpdate();
			conn.close();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public String getView(int board_no) throws Exception {
		conn = getConnection();
		addSong s = new addSong();
		String sql = "select "
	}
	*/



