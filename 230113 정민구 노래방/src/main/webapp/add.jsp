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
		<div class="title">노래 추가하기</div>
			<div class="wrapper" style="display: flex; flex-direction: column; padding: 10px">
				<form name=fra action="insert">
					<table>
						<tr>
							<th>곡번호</th>
							<td><input type="text" name=songno placeholder="예시) 12345"></input></td>
						</tr>
						<tr>
							<th>노래제목</th>
							<td><input type="text" name=songtitle></input></td>
						</tr>
						<tr>
							<th>가 수</th>
							<td><input type="text" name=singer></input></td>
						</tr>
						<tr>
							<th>유튜브 주소</th>
							<td><input type="text" name=yaddress placeholder="유튜브 링크"></input></td>
						</tr>
					</table>
					<div style="padding:15px;text-align: center;">
						<button class="add_btn" type="button" onclick="fra_submit(); return false;" style="width: 100px; height: 30px; font-size: 15px;">등록하기</button>
						<input class="add_btn" type="reset" value="초기화" style="width: 100px; height: 30px; font-size: 15px;">
					</div>
				</form>
			</div>
		
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>