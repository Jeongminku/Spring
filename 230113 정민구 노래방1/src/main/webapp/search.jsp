<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<div class="title">노래검색</div>
			<form name = frm action = "searchsong">
				<div class = "table">
					<table style="width:700px">
						<tr>
							<th>검색할 노래 제목을 입력 해주세요.</th>
							<td><input type="text" name="songtitle"></td>
						</tr>
						<tr>
							<td colspan="2">
							<button class="search_btn" type="submit" onclick="fn_submit(); return false;">노래조회</button>
							<button class="search_btn" type="button" onclick="location.href='/KARAOKE/home'">홈으로</button>
							</td>
						</tr>
					</table>
				</div>
		
		</form>		
	</section>
	<%@ include file="footer.jsp" %>
</body>
</html>