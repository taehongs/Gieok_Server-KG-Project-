const with_accept_btn = document.querySelectorAll(".with_accept");
const board_no_list = document.querySelectorAll(".with_submit_board_no");
const board_writer_list = document.querySelectorAll(".with_submit_board_writer");

for(let i=0; i<with_accept_btn.length; i++) {
	
	with_accept_btn[i].addEventListener("click", () => {
		
		const user_code = document.querySelector("#user_code");

		if(user_code.value=="") {
			alert("로그인이 필요합니다!");
			window.location.href = "/login";
		}else {
			const board_no = board_no_list[i].value;
			const board_writer = board_writer_list[i].value;
			const info = window.prompt("신청을 보내시려는 분에게 간단한 소개를 해주세요!😊\n" +
									   "ex) 이름: ㅇㅇㅇ / 나이: ㅇㅇ세 / 성별: 남|여 / 동행신청합니다~ ");
			
			
			if(info=="") {
				alert("신청자의 정보를 입력해주세요!😊");
			}else if(info.length > 50) {
				alert("소개글이 너무 길어요😭");
			}else if(info!=""&&info!=null) {
				
				fetch("https://gieok.icu/board_with_sinchung", {
					method: "POST",
					headers: {
						"Content-Type" : "application/json",
					},
					body: JSON.stringify({
						board_no: board_no,
						board_writer: board_writer,
						with_user_info: info,
					})
				})
				.then(res => res.text())
				.then(res => {
					if(res=="") {
						alert("신청이 완료되었습니다!😊");
						window.location.reload();
					}else if(res=="fail") {
						alert("시스템 오류! 관리자에게 문의하세요😭");
					}else if(res == "false") {
						alert("이미 신청한 동행입니다!😭");
					}else if(res == "noSession") {
						alert("로그인이 필요합니다!😭");
						window.location.href = "/login";
					}
						
				});
			}
		}
		
	});
	
}





/**
 *  관리자 삭제하기
 */

const del_Btn = document.querySelectorAll("#del_Btn");
const boardDelList = document.querySelectorAll(".with_submit_board_no");
const page = document.querySelector("#page").value;

for (let i = 0; i < del_Btn.length; i++) {
	
	del_Btn[i].addEventListener("click", () => {
		
		const user_code = document.querySelector("#user_code");
		const user_grade = document.querySelector("#user_grade");
		const category = document.querySelector(".with_category");
		const keyword = document.querySelector(".search_field");
		
		if(user_code.value=="") {
			alert("관리자 로그인이 필요합니다!");
			window.location.href = "/login";
		}
		else if(user_grade.value == "a" || user_grade.value == "s") {
			
			const board_no = boardDelList[i].value;
			let isReload = false;
			
			fetch("https://gieok.icu/board_with_delbtn", {
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
				if(res=="") {
					alert("삭제가 완료되었습니다!😊");
					isReload = true;
					//window.location.href = `/board_with_list?page=${page}&category=${category}&keyword=${keyword}`;
				}
				else if (res == "fail") {
					alert("시스템 오류! 관리자에게 문의하세요😭");
				}
				else if (res == "noSession") {
					alert("관리자 로그인이 필요합니다!😭");
					window.location.href = "/login";
				}
				
				if(isReload) {
					window.location.reload();
				}
				
			});
			
		}

	});
	
}


/* ======신고====== */
const report_btn = document.querySelectorAll(".useBtn");
const report_with_no = document.querySelectorAll(".report_with_no");
const report_with_writer = document.querySelectorAll(".report_with_writer");
let report_no = "";

for(let i=0; i<report_btn.length; i++) {

	report_btn[i].addEventListener("click", () => {
	
		const user_code = document.querySelector("#user_code");

		if(user_code.value=="") {
			alert("로그인이 필요합니다!");
			window.location.href = "/login";
		}
		else {
			let report_type = prompt("신고 유형 번호를 입력해주세요!\n1. 부적절한 내용\n2. 욕설/비방\n3. 광고/홍보\n4. 도배");
		
			if(report_type==null){
			}							  	
			else if(report_type!="1" && report_type!="2"&& report_type!=="3" && report_type!="4") {
				alert("유효하지 않은 옵션입니다!");
			}else {
				report_no = report_with_no[i].value;
				report_writer = report_with_writer[i].value;
				
				fetch("https://gieok.icu/board_with_report", {
					method: "POST",
					headers: {
						"Content-Type": "application/json",
					},
					body: JSON.stringify({
						report_no: report_no,
						report_writer: report_writer,
						report_type: report_type,
					}),
				})
				.then(res => res.json())
				.then(res => {
					alert(res.msg);
					if(res.url!=undefined) {
						window.location.href = res.url;
					}
				})
				
			}
			
		
		}
		
	})
}










