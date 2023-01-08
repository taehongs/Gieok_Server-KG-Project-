const isNotEmpty = document.querySelector("#isNotEmpty").value;
const review_report_detail = document.querySelector(".review_report_detail");
const page = document.querySelector("#page").value;

let isOn = false;

if(isNotEmpty) {
	const review_report_detail_list = document.querySelectorAll(".review_report_detail_list");
	const bad_review_code = document.querySelectorAll(".bad_review_code");
	
	for(let i=0; i<review_report_detail_list.length; i++) {
		review_report_detail_list[i].addEventListener("click", () => {
		
			let rev_code = bad_review_code[i].value;
		
			fetch("https://gieok.icu/admin/review_report_detail", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					rev_code: rev_code,
				}),
			})
			.then(res => res.json())
			.then(res => {
			
				if(res.rev_code!="") {
					reviewDetail(res)
					const back_btn = document.querySelector(".back_btn");
					back_btn.addEventListener("click", () => {
						closeForm();
					});
					const delete_btn = document.querySelector(".review_delete_btn");
					delete_btn.addEventListener("click", () => {
						deleteReview(rev_code, page);
					});
				}else {
					alert("세션이 만료되었습니다!");
					window.location.href = "/login";
				}

				
			}); //then
		});
		
	}
}



function showForm() {
	if(isOn==false) {
		review_report_detail.style.opacity = "1";
		review_report_detail.style.zIndex = "5";
		review_report_detail.style.transform = "translateY(60%)";
	}
}

function closeForm() {
	review_report_detail.style.opacity = "0";
	review_report_detail.style.zIndex = "-1";
	review_report_detail.style.transform = "translateY(0)";
}

function deleteReview(rev_code, page) {

	fetch("https://gieok.icu/admin/review_report_delete", {
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
				            <p class="attr_review_content">${review.rev_content}</p>
				            <div class="file_and_rate">
				            	<span class="writer">작성자: ${review.rev_writer} </span>
				                <span class="rate">
                            `;
							for(let i=0; i<review.rev_rate; i++) {
								review_content += `★`;
							};
							                 
		review_content +=   `</span>`;
		
							if(review.rev_img!=null) {
				            	review_content += `<img src="../resources/upload/review/${review.attr_code}/${review.rev_img}">`
							
							}
		review_content += 	`
							</div>
				            <div class="form_button">
				                <input type="button" value="삭제" class="review_delete_btn">
				                <input type="button" value="취소" class="back_btn">
				            </div>
							`
				            
	
	review_report_detail.innerHTML = review_content;
}




	
