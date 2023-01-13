<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<div class="wrap">
			<table class="song_list">
				<caption>
					<h1 style="padding: 20px;">노래 목록</h1>
				</caption>
				<thead>
					<tr>
						<th>곡 번호</th>
						<th>노래 제목</th>
						<th>가 수</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty songList}">
						<tr>
						<td></td>
						<td>해당하는 노래가 없습니다.</td>
						<td></td>
						</tr>
				</c:if>
				<c:if test="${songList ne 'null'}">
					<c:forEach var="song" items="${songList}" varStatus="status">
						<tr>
							<td>${song.songno}</td>
							<td>${song.songtitle}</td>
							<td>${song.singer}</td>
						</tr>
					</c:forEach>
				</c:if>
				
				</tbody>
			</table>
				
		</div>
		
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>