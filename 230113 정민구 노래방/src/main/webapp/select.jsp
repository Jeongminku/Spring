<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/style.css" />
<script type="text/javascript" src="script.js"></script>
</head>
<body>
	<%@ include file="header.jsp" %>
	<section>
		<div style="display: flex; flex-direction: row; justify-content: center; margin: 80px;">
			<div>
			<iframe id="iframeid" width="950" height="450" src="https://www.youtube.com/embed/${song.yaddress}?autoplay=1&mute=1" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
			</div>
			<div>
			<form name=rep action=reply>
			<input type = "hidden" name="songno" value="${song.songno}">
				<div class="main_comment">
				<table>
				<tr>
				<td> <input type="text" name="userid" id="rep_userid" placeholder="닉네임"> </td>
				<td> <input type="text" name="rep_content" id="rep_content" placeholder= "댓글 추가..."> </td>
				<th></th>
				<td> <button class="rep_btn" type="submit" onclick="fm_repSubmit(); return false;">댓글</button> <td>
				</tr>
				<tr>
				<th class="reply_list">아이디</th>
				<th class="reply_list">댓글 내용</th>
				<th class="reply_list">등록 시간</th>
				</tr>
			
				<c:forEach var="replylist" items="${replylist}">
					<tr>
						<td>${replylist.userid}</td>
						<td>${replylist.rep_content}</td>
						<td>${replylist.rep_date}</td>
						<td><button type="button" onclick="chkDelete(${replylist.commentno},${replylist.songno})">삭제하기</button></td>
					</tr>
				</c:forEach>
				</table>  
				</div>
			</form>	
			</div>
		</div>
		<form name=songfm action="select">
		<div style= "display: flex; justify-content: center;">
			<input class="inputcode" type="text" name="songno" placeholder="노래 코드를 입력해주세요" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" required></input>
			<button class="btn" type="button" onclick="fn_submit(); return false;">확인</button>
		</div>
		</form>
		

	</section>
	<%@ include file="footer.jsp" %>

</body>
</html>