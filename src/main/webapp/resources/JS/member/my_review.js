const isNotEmpty = document.querySelector("#isNotEmpty").value;
const attr_review_form = document.querySelector(".attr_review_form");
const page = document.querySelector("#page").value;

let isOn = false;


if(isNotEmpty) {
	const review_detail_btn = document.querySelectorAll(".review_detail_btn");
	const rev_code_list = document.querySelectorAll(".rev_code_list");
	
	for(let i=0; i<review_detail_btn.length; i++) {
		review_detail_btn[i].addEventListener("click", () => {
		
			let rev_code = rev_code_list[i].value;
		
			fetch("https://gieok.icu/member/review_detail", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					rev_code: rev_code_list[i].value,
				}),
			})
			.then(res => res.json())
			.then(res => {
				reviewDetail(res)
				const back_btn = document.querySelector(".back_btn");
				back_btn.addEventListener("click", () => {
					closeForm();
				});
				
				const attr_review_content = document.querySelector(".attr_review_content");
				const content_limit = document.querySelector("#content_limit");
				content_limit.value = `${attr_review_content.value.length}/80자`;
				
				attr_review_content.addEventListener("keyup", (e) => {
					let content = e.target.value;
					let content_length = content.length;
					if(content_length > 80) {
						alert("리뷰는 최대 80자까지 작성 가능합니다");
						e.target.value = content.substring(0,80);
					}
					content_limit.value = `${e.target.value.length}/80자`;
				});
				
				const update_btn = document.querySelector(".review_submit_btn");
				update_btn.addEventListener("click", () => {
					const formData = new FormData(attr_review_form);
					updateReview(formData);
				});

				const delete_btn = document.querySelector(".review_delete_btn");
				delete_btn.addEventListener("click", () => {
					deleteReview(rev_code, page);
				});
				
				
				
			}); //then
		});
		
	}
}



function showForm() {
	if(isOn==false) {
		attr_review_form.style.opacity = "1";
		attr_review_form.style.zIndex = "5";
		attr_review_form.style.transform = "translateY(100%)";
	}
}

function closeForm() {
	attr_review_form.style.opacity = "0";
	attr_review_form.style.zIndex = "-1";
	attr_review_form.style.transform = "translateY(0)";
}

function updateReview(formData) {
	fetch("https://gieok.icu/member/review_update", {
		method:"POST",
		body: formData,
	})
	.then(res => res.json())
	.then(res => {
		if(res.msg=="") {
			alert("시스템 오류! 관리자에게 문의해주세요 :(");
		}else {
			alert(res.msg);
			window.location.href=res.url+page;
		}
	});
}

function deleteReview(rev_code, page) {

	fetch("https://gieok.icu/member/review_delete", {
		method:"POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			page: page,
			rev_code: rev_code,
		}),
	})
	.then(res => res.json())
	.then(res => {
		if(res.msg=="") {
			alert("시스템 오류! 관리자에게 문의해주세요 :(");
		}else {
			alert(res.msg);
			window.location.href=res.url;
		}
	});
}

function reviewDetail(review) {

	showForm();

	let review_content = 	`
							<h1>리뷰 수정</h1>
					            <input type="hidden" name="rev_code" value="${review.rev_code}">
					            <textarea class="attr_review_content" name="rev_content">${review.rev_content}</textarea>
					            <input type="text" value="0/80자" id="content_limit" readonly>
					            <div class="file_and_rate">
					                <input type="file" name="image">
					                <select name="rev_rate">
					                    <option value="5">	
					                 		★★★★★
					                    </option>
					                    <option value="4">
					                                                         ★★★★
					                  	</option>
					                    <option value="3">
						                  	★★★
					                  	</option>
					                    <option value="2">
						                  	★★
					                  	</option>
					                    <option value="1">
						                  	★
					                  	</option>
					                </select>
					            </div>
					            <div class="form_button">
					                <input type="button" value="수정" class="review_submit_btn">
					                <input type="button" value="취소" class="back_btn">
					                <span class="review_delete_btn">삭제</span>
					            </div>
							`
	
	attr_review_form.innerHTML = review_content;
	document.querySelector(".attr_review_content").focus();
	
}




	
