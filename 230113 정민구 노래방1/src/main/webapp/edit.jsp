<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/style.css" />
<script type="text/javascript" src="script.js"></script>
<%@ page import="DTO.*" %>
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
		<div class="title">노래 정보 변경하기</div>
			<div class="wrapper" style="display: flex; flex-direction: column; padding: 10px">
				<form name=fra action="update">
					<table>
						<tr>
							<th>곡번호</th>
							<td><input type="text" name="songno" value="${song.songno}" readonly></input></td>
						</tr>
						<tr>
							<th>노래제목</th>
							<td><input type="text" name="songtitle" value="${song.songtitle}"></input></td>
						</tr>
						<tr>
							<th>가 수</th>
							<td><input type="text" name="singer" value="${song.singer}"></input></td>
						</tr>
						<tr>
							<th>유튜브 주소</th>
							<td><input type="text" name="yaddress" value="${song.yaddress}"></input></td>
						</tr>
					</table>
				</form>
					<div style="padding:15px; text-align:center">
						<button class="edit_btn" type="button" onclick="fra_submit(); return false;">수정완료</button>
						<button class="edit_btn" type="button" onclick="location.href='/KARAOKE/list'">취소하기</button>
					</div>
			</div>
		
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>