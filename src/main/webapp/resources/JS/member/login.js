document.querySelector("#user_id").focus();


/* 이전 로그인 방식
function loginCheck(){
		
	const user_id = login_form.user_id.value;
	const user_pw = login_form.user_pw.value;
	
	if(loginValid(user_id, user_pw)===true) {

		fetch("https://gieok.icu/member/login", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				user_id: user_id,
				user_pw: user_pw,
			})
		})
		.then(res => res.json())
		.then(function(res) {
			if(res.msg!="") {
				alert(res.msg);
			}			
			location.href = res.url;
		})
	}
}

function loginValid(id, pw) {
  if (id === "") {
    alert("아이디를 입력하세요");
    loginForm.user_id.focus();
    return false;
  } else if (pw === "") {
    alert("비밀번호를 입력하세요");
    loginForm.user_pw.focus();
    return false;
  } else {
    return true;
  }
}



login_form.addEventListener("submit", function (e) {
  loginCheck();
  e.preventDefault();
});
*/










