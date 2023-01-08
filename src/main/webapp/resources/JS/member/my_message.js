const isNotEmpty = document.querySelector("#isNotEmpty").value;
const message_form = document.querySelector(".message_form");

let children = "";
let message_sender = "";
let message_title = "";
let message_content = "";
let message_date = "";

let isOn = false;


// 검색 버튼
function searchValidate() {
	const category = document.querySelector("#message_category").value;
	const keyword = document.querySelector("#message_keyword").value;
	
	if(keyword.charAt(0)==" ") {
		alert("검색은 공백으로 시작할 수 없습니다! :(");
		return false;
	}
	
	if(category!="all" && keyword.trim()=="") {
		alert("검색어를 입력해주세요!");
		return false;
	}
}
	
	
if(isNotEmpty) {
	const message_detail_btn = document.querySelectorAll(".message_detail_btn");
	const message_no_list = document.querySelectorAll(".message_no_list");
	
	for(let i=0; i<message_detail_btn.length; i++) {
		message_detail_btn[i].addEventListener("click", (e) => {
			
			if(e.target.type != "checkbox") {
			
				children = message_detail_btn[i].children;
			
				message_sender = children[0].innerText;
				message_title = children[1].innerText;
				message_content = children[2].value;
				message_date = children[3].innerText;
				
				// New문구 지워주기 
				children[1].classList.remove("unread_msg");
				
				// 메세지 읽음 처리
				message_no = children[4].value;
				fetch("https://gieok.icu/member/updateMessageRead", {
					method:"POST",
					headers: {
						"Content-Type": "application/json",
					},
					body: JSON.stringify({
						message_no: message_no,
					}),
				})
				.then(res => res.json())
				.then(res => {
					if(res==-1){
						alert("세션이 만료되었습니다!");
						window.location.href = "/login";
					}else if(res==-2) {
						alert("시스템 오류! 관리자에게 문의해주세여 :(");
					}else {
						const msgCount = document.querySelector(".msgCount2");
						if(res==0) {
							msgCount.removeAttribute("id");
						}else {
							msgCount.innerText = res;
						}
					}
				})			
				
				messageDetail(message_sender, message_title, message_content, message_date);
			
			}
			
		});
		
	}
	
	const message_check = document.querySelectorAll(".message_check");
	const message_delete_btn = document.querySelector("#message_delete_btn");
	message_delete_btn.addEventListener("click", () => {
	
		let checked = new Array;
	
		for(let i=0; i<message_check.length; i++) {
			if(message_check[i].checked == true) {
				checked.push(message_check[i].value);
			}
		}
		
		if(checked.length>0) {
			fetch("https://gieok.icu/member/deleteMessage", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					message_no : checked,
				}),
			})
			.then(res => res.json())
			.then(res => {
				if(res.msg!="") {
					alert(res.msg);
					window.location.href = res.url;
				}else {
					alert("시스템 오류! 관리자에게 문의하세요 :(");
				}
			})
		}
		
	});
	
	
}



function showForm() {
	if(isOn==false) {
		message_form.style.opacity = "1";
		message_form.style.zIndex = "5";
		message_form.style.transform = "translateY(100%)";
	}
}

function closeForm() {
	message_form.style.opacity = "0";
	message_form.style.zIndex = "-1";
	message_form.style.transform = "translateY(0)";
}



function messageDetail(sender, title, content, date) {

	showForm();

	let message = `
					<span class="message_sender">보낸 사람: ${sender}</span>
					<input type="text" placeholder="제목" value="${title}" class="message_title" readonly>
					<textarea class="message_content" readonly>${content}</textarea>
					<div class="message_btn_container">
						<input type="button" value="답장하기" id="message_reply_btn" onclick="sendMessage('${sender}')">
						<input type="button" value="취소" id="message_cancel_btn" onclick="closeForm();">
					</div>
				  `
	
	message_form.innerHTML = message;
	
	const reply_btn = document.querySelector(".message_reply_btn")
	
	
}

function sendMessage(receiver) {

	let message = `
					<span class="message_receiver">받는 사람: ${receiver}</span>
					<input type="text" placeholder="제목" class="message_title">
					<textarea class="message_content"></textarea>
					<div class="message_btn_container">
						<input type="button" value="보내기" id="message_submit_btn">
						<input type="button" value="취소" id="message_cancel_btn" onclick="closeForm();">
					</div>
				  `
	message_form.innerHTML = message;
	
	const submit_btn = document.querySelector("#message_submit_btn");
	
	submit_btn.addEventListener("click", () => {
		
		const message_receiver = receiver;
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
				closeForm();
			}
		})
	});	
	

}




	
