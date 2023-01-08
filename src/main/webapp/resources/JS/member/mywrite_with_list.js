// 신청 목록 modal
const modalBg = $('#modal_back');
const modalPop = $('#modal_container');

// 선택된 동행 게시판 번호 
let selected_board_no = -1;
let board_memCount = -1;
let memCount2 = -1;

// 모집 완료시 리로드 토글
let toggle = false;

	
function popClose() {

	$(modalBg).hide();
	$(modalPop).hide();
	if(board_memCount == memCount2 && toggle == true) {
		window.location.reload();
	}
	card_list.innerHTML = '';
}

// 버튼
const board_no_list = document.querySelectorAll(".with_submit_board_no");
const board_writer_list = document.querySelectorAll(".with_submit_board_writer");
const board_memCount_list = document.querySelectorAll(".with_submit_board_memCount");

const with_delete_btn = document.querySelectorAll("#delBtn");
const with_accept_btn = document.querySelectorAll("#with_accept");

const card_list = document.querySelector(".with_user_con");




for (let i=0; i < with_delete_btn.length; i++) {
	
	// 삭제 버튼
	with_delete_btn[i].addEventListener("click", () => {
		
		const user_code = document.querySelector("#user_code");
		const page = document.querySelector("#page").value;
		
		if (user_code.value == "") {
			alert("로그인이 필요합니다!");
			window.location.href = "/login";
		} else {
			const board_writer = board_writer_list[i].value;
			const board_no = board_no_list[i].value;
		
			fetch("https://gieok.icu/mywrite_with_delete", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					board_no: board_no,
				})
			})
			.then(res => res.json())
			.then(res => {
				if(res.msg != "") {
					alert(res.msg);
					if(res.url != "") {
						window.location.href = res.url;
					}
				}else {
					alert("나의 동행이 삭제되었습니다!😊");
					window.location.href = "/mywrite_with_list?board_writer="+board_writer;
				}
			})
			
			/* 서버가 이상해...?
			.then (res => res.text())
			.then (res => {
				if(res=="") {
					alert("나의 동행 삭제가 완료되었습니다!😊");
					isReload = true;
					//window.location.href = `/board_with_list?page=${page}&category=${category}&keyword=${keyword}`;
				} else if (res == "fail") {
					alert("시스템 오류! 관리자에게 문의하세요😭");
				} else if (res == "noSession") {
					alert("로그인이 필요합니다!😭");
					window.location.href = "/login";
				}
				
				if(isReload) {
					window.location.reload();
				}
					
			});
			
			*/
			
		}
		
	});
	
	// 신청 목록 버튼
	with_accept_btn[i].addEventListener("click", () => {
		selected_board_no = board_no_list[i].value;
		board_memCount = board_memCount_list[i].value;
		
		
		// 수락한 동행 수
		fetch("https://gieok.icu/mywrite_with_acceptcount", {
			method: "POST",
			headers: {
				"Content-Type" : "application/json",
			},
			body: JSON.stringify({
				board_no: selected_board_no,
			})
		})
		.then(res => res.text())
		.then(res => {
			memCount2 = res;
			if(board_memCount == memCount2) {
				toggle = false;
			}
			list(selected_board_no ,board_memCount, memCount2);
		})
	});	
	
}

// 신청 목록 그리기
// memCount : 모집인원, memCount2 : 수락한 동행 수
function list(board_no, memCount, memCount2) {

	const user_code = document.querySelector("#user_code");

	$(modalBg).show();
	$(modalPop).show();

	const with_count = document.getElementById("with_count");
	with_count.textContent = "모집인원 : " + memCount + " 명";
	
	const accept_count = document.getElementById("accept_count");
	accept_count.textContent = "수락한 동행 수 : " + memCount2 + " 명";
	
	// 나에게 신청한 동행 수
	fetch("https://gieok.icu/my_with_list", {
		method: "POST",
			headers: {
			"Content-Type" : "application/json",
		},
		body: JSON.stringify({
			board_no: board_no,
		})
	})
	.then (res => res.json())
	.then (function(data) {
	
		card_list.innerHTML = '';
		
		if (data.length == 0) {
			card_list.innerHTML = `<div class="with_user_con">신청 목록이 없습니다.</div>`;
		} else {
			data.forEach((item) => {
			
								let content = ``;

							  
			  if(memCount != memCount2) {
			  
				  if(item.with_accept=="1") {
				  	content +=  `
								<div class="with_user_con">
									<div class="with_user_info1">
										<div class="with_user_info_text">
											신청자 : ${item.with_user_id}
										</div>
								  		<div class="with_user_info_btn1">
								  			<button class="chat_btn md_btn" onclick="showMessage('${item.with_user_id}', ${item.with_user_code}, ${item.board_no})">메세지</button>
								  			<button class="accept_btn md_btn" onclick="mywithaccept(${item.board_no}, '${item.with_user_id}', ${memCount})">수락</button>
											<button class="reject_btn md_btn" onclick="mywithreject(${item.board_no}, '${item.with_user_id}', ${memCount})">거절</button>
										</div>
									</div>
									<div class="with_user_info2">
										${item.with_user_info}
									</div>
								</div>
								  				
								  `;
								  }else if(item.with_accept=="2") {
									content +=	`
								<div class="with_user_con">
									<div class="with_user_info1">
										<div class="with_user_info_text">
											신청자 : ${item.with_user_id}
										</div>
										<div class="with_user_info_btn1">
											<button class="chat_btn md_btn" onclick="showMessage('${item.with_user_id}', ${item.with_user_code}, ${item.board_no})">메세지</button>
											<span class="with_result1">수락됨</span>
											<button class="reject_btn md_btn" onclick="mywithreject(${item.board_no}, '${item.with_user_id}', ${memCount})">거절</button>
										</div>
									</div>
									<div class="with_user_info2">
										${item.with_user_info}
									</div>
								</div>
								  				`;
								  }else if(item.with_accept=="3") {
									content +=	`
								<div class="with_user_con">
									<div class="with_user_info1">
										<div class="with_user_info_text">
											신청자 : ${item.with_user_id}
										</div>									
										<div class="with_user_info_btn1">
											<button class="chat_btn md_btn" onclick="showMessage('${item.with_user_id}', ${item.with_user_code}, ${item.board_no})">메세지</button>
								  			<button class="accept_btn md_btn" onclick="mywithaccept(${item.board_no}, '${item.with_user_id}', ${memCount})">수락</button>
											<span class="with_result2">거절됨</span>
										</div>
									</div>
									<div class="with_user_info2">
										${item.with_user_info}
									</div>
								</div>
								  				`;
								  }
							  }else {
							  
							  	  if(item.with_accept=="2") {
									content +=	`
								<div class="with_user_con">
									<div class="with_user_info1">
										<div class="with_user_info_text">
											신청자 : ${item.with_user_id}
										</div>
										<div class="with_user_info_btn2">
											<button class="chat_btn md_btn" onclick="showMessage('${item.with_user_id}', ${item.with_user_code}, ${item.board_no})">메세지</button>
											<span class="with_result1">수락됨</span>
										</div>
									</div>
									<div class="with_user_info2">
										${item.with_user_info}
									</div>
								</div>
								  				`;
								  }else if(item.with_accept=="3") {
								  	content += '';
								  }
							  }
							  
				card_list.innerHTML += content;
				
			});	
		}
	
	})
}



// 동행 신청 수락
function mywithaccept(board_no, with_user_id, memCount) {

	const accept_count = document.querySelector("#accept_count");
	
	if (memCount2 == (memCount - 1)) { // 마지막 동행 수락
		let result = confirm("마지막 동행 수락입니다.\n마지막 동행 수락이 완료되면 더이상 동행 수락 또는 거절이 불가능합니다.\n\n동행을 수락하시겠습니까?");
		
		if (result == true) {
			acceptCheck(board_no, with_user_id, memCount);
			memCount2 = memCount;
			
			// 나머지 동행 신청 상태 '1' > '3'
			fetch("https://gieok.icu/change_with_accept", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					board_no: selected_board_no,
				})
			})
			.then(res => res.text())
			.then(res => {
				if (res != "success") {
					alert("시스템 오류! 관리자에게 문의하세요😭")
				} 
			});
			alert("모든 동행 수락이 완료되었습니다!😊");
			toggle = true;
		} else {
			return false;
		}
		
	} else {
		acceptCheck(board_no, with_user_id, memCount);
		alert("동행 신청이 수락되었습니다!😊");
	}
	
}

// 동행 수락 기능
function acceptCheck(board_no, with_user_id, memCount) {
	fetch("https://gieok.icu/my_with_accept", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			board_no: board_no,
			with_user_id: with_user_id,
		}),
	})
	.then(res => res.json())
	.then(res => {
		if(res.check=="") {
			list(selected_board_no, memCount, res.count);
		}else if(res.check=="fail") {
			alert("시스템 오류! 관리자에게 문의하세요😭");
		}else if(res.check == "noSession") {
			alert("로그인이 필요합니다!😭");
			window.location.href = "/login";
		}
	});
}

// 동행 신청 거절
function mywithreject(board_no, with_user_id, memCount) {
	
	fetch("https://gieok.icu/my_with_reject", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			board_no: board_no,
			with_user_id: with_user_id,
		}),
	})
	.then(res => res.json())
	.then(res => {
		if(res.check=="") {
			alert("동행 신청이 거절되었습니다!😊");
			list(selected_board_no, memCount, res.count);
		}else if(res.check=="fail") {
			alert("시스템 오류! 관리자에게 문의하세요😭");
		}else if(res.check == "noSession") {
			alert("로그인이 필요합니다!😭");
			window.location.href = "/login";
		}
	});

}

const message_container = document.querySelector(".message_container");
const message_block = document.querySelector(".message_block");
const modal_container = document.querySelector(".modal_container");

// 메세지 창 로드
function showMessage(user_id, user_code, board_no) {
	message_container.style.transform = "translateY(0)";
	message_container.style.opacity = "1";
	message_block.style.display = "block";
	
	message_block.addEventListener("click", () => {
		closeMessage();
	});
	
	let message = `
					<span class="message_receiver">받는 이: ${user_id}</span>
					<input type="text" placeholder="제목" class="message_title">
					<textarea class="message_content"></textarea>
					<div class="message_btn_container">
						<input type="button" value="보내기" id="message_submit_btn">
						<input type="button" value="취소" id="message_cancel_btn" onclick="closeMessage();">
					</div>
				  `
	message_container.innerHTML = message;
	
	const message_submit_btn = document.querySelector("#message_submit_btn");
	
	message_submit_btn.addEventListener("click", () => {
		
		const message_receiver = user_id;
		const message_title = document.querySelector(".message_title").value;
		const message_content = document.querySelector(".message_content").value;
	
		fetch("https://gieok.icu/member/sendMessage", {
			method: "POST",
			headers: {
				"Content-Type" : "application/json",
			},
			body: JSON.stringify({
				message_receiver: message_receiver,
				message_title: message_title,
				message_content: message_content,
			}),
		})
		.then(res => res.json())
		.then(res => {
			alert(res.msg);
			if(res.url!=undefined) {
				window.location.href = res.url;
			}else {
				closeMessage();
			}
		})
	});

}

function closeMessage() {
	message_container.style.transform = "translateY(-200%)";
	message_container.style.opacity = "0";
	message_block.style.display = "none";
}

