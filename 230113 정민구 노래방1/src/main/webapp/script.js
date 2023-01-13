function fn_submit(){
	var fn = document.songfm;
	if(fn.songno.value == ""){
		alert('검색어가 입력되지않았습니다.');
		fn.songtitle.focus();
		return false;
	}
	

	fn.submit();
}


function fm_repSubmit(){
	var fm = document.rep;
	
	if(fm.userid.value ==""){
		alert('닉네임을 입력해주세요');
		fm.userid.focus();
		return false;
	}
	
	if(fm.rep_content.value == ""){
		alert('댓글 내용이 없습니다');
		fm.reply.focus();
		return false;
		
	}
	fm.submit();
}

/*function fm_repSubmit(){
	var fm = document.reply;
	
	if(fm.nickname.value ==""){
		alert('닉네임을 입력해주세요');
		fm.nickname.focus();
		return false;
	}
	
	if(fm.reply.value == ""){
		alert('댓글 내용이 없습니다');
		fm.reply.focus();
		return false;
		
	}
	fm.submit();
}*/
function chkDelete(a, b) {
	
	if(confirm("댓글을 삭제하시겠습니까?")){
		const url = location.origin;
		console.log("qwe123");
		location.href = 'delete?commentno='+a+'&songno='+b;
		
	} else{
		return false;
	}
}
function songDelete(songno) {
	const result = confirm("노래를 삭제하시겠습니까?");
	
	if(result) {
		const url = location.origin;
		location.href = url + 'delete?songno='+songno;
	} else {
		return false;
	}	
}

function chkForm() {
	var r = document.reply;
	r.submit();
}

function fra_submit(){
	var fra = document.fra; 
	
	if (fra.songno.value == "") {
		alert("곡 번호가 입력되지 않았습니다.");
		fra.songno.focus(); 
		return false; //함수 종료
	}
	if (fra.songtitle.value == "") {
		alert("노래제목이 입력되지 않았습니다.");
		fra.songtitle.focus(); 
		return false; //함수 종료
	}
	if (fra.singer.value == "") {
		alert("가수가 입력되지 않았습니다.");
		fra.singer.focus();
		return false; //함수 종료
	}
	if (fra.yaddress.value == "") {
		alert("유튜브주소가 입력되지 않았습니다.");
		fra.yaddress.focus();
		return false; //함수 종료
	}
	
	fra.submit(); //컨트롤러(서버)에 전송합니다.

}