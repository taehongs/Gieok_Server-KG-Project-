const cancel_Btn = document.querySelectorAll(".with_cancelBtn");
const cancelList = document.querySelectorAll(".with_submit_board_no");
const sinUser = document.querySelectorAll("#user_id");


// 신청 취소하기
for (let i = 0; i < cancel_Btn.length; i++) {
	
	cancel_Btn[i].addEventListener("click", () => {
		
		const user_code = document.querySelector("#user_code");
		const user_grade = document.querySelector("#user_grade");
		
		if(user_code.value=="") {
			alert("로그인이 필요합니다!");
			window.location.href = "/login";
		}
		else if(user_grade.value == "m" || user_grade.value == "a" || user_grade.value == "s") {
			
			const board_no = cancelList[i].value;
				
			fetch("https://gieok.icu/board_with_sindel", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					board_no: board_no,
					
				})
			})
			.then (res => res.text())
			.then (res => {
				if (res == "") {
					alert("신청이 취소되었습니다!😊");
					window.location.reload();
				}
				else if (res == "fail") {
					alert("시스템 오류! 관리자에게 문의하세요😭");
				}
				else if (res == "noSession") {
					alert("관리자 로그인이 필요합니다!😭");
					window.location.href = "/login";
				}
				
			});
			
		}

	});
	
}