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
	<%@ include file="header.jsp"%>
	<section>
		<div class="index_div" style="display: flex; flex-direction: row; justify-content: center;">
			<div>
				<iframe id="iframeid" width="1080" height="450"
					src="https://www.youtube.com/embed/wI38vZqNLd0?autoplay=1&mute=1"
					title="YouTube video player" frameborder="0"
					allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
					allowfullscreen></iframe>
			</div>
		</div>
		<form name=songfm action=select>
			<div style="display: flex; flex-direction: row; justify-content: center;">
				<input class="inputcode" type="text" name="songno" placeholder="노래 코드를 입력해주세요" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" required></input>
				<button class="btn" type="button" onclick="fn_submit(); return false;">확인</button>
			</div>
		</form>
	</section>

	<%@ include file="footer.jsp"%>
</body>
</html>