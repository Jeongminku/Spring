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
				<table class="songlist">
					<caption>
						<h1 class="songlist_title">노래 리스트</h1>
					</caption>
				<thead>
					<tr style="padding">
						<th>곡 번호</th>
						<th>노래제목</th>
						<th>가 수</th>
						<th><th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="songlist" items="${alllist}">
						<tr>
							<td>${songlist.songno}</td>
							<td>${songlist.songtitle}</td>
							<td>${songlist.singer}</td>
							<td>
								<a href="./edit?songno=${songlist.songno}" class="edit_list">[수정]</a>
								<a href="./deleteSong?songno=${songlist.songno}" class="delete_list" onclick="songDelete(${songlist.songno}); return false;">[삭제]</a>
							</td>
							
						</tr>
					</c:forEach>
					</tbody>					
				</table>
			</div>
			
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>