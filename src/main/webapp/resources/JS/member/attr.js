// 명소 도, 시, 코드
const province = document.querySelector("#province").value;
const city_code = document.querySelector("#city_code").value;
const attr_code = document.querySelector("#attr_code").value;
let user_code = document.querySelector("#user_code").value;
const user_grade = document.querySelector("#user_grade").value;

const latitude = document.querySelector("#latitude").value;
const longitude = document.querySelector("#longitude").value;
const city_name = document.querySelector("#city_name").value;

// 사진 슬라이드 
// 이미지 선택 버튼
const attr_image_btn1 = document.querySelector("#attr_image_btn1");
const attr_image_btn2 = document.querySelector("#attr_image_btn2");
let selectedImage = true; 

const attr_image1 = document.querySelector("#attr_image1");
const attr_image2 = document.querySelector("#attr_image2");

autoImage1();

function autoImage2() {

	attr_image_btn2.style.background = "#333";
	attr_image_btn2.style.border = "2px solid #fff";
	attr_image_btn1.style.background = "#fff";
	attr_image_btn1.style.border = "2px solid #333";
	attr_image1.style.transform = "translateX(-120%)";
	attr_image1.style.opacity = "0";
	attr_image2.style.transform = "translateX(0)"
	attr_image2.style.opacity = "1";

	setTimeout(() => {
		autoImage1();
	}, 3000);
	
	

}


function autoImage1() {

	attr_image_btn1.style.background = "#333";
	attr_image_btn1.style.border = "2px solid #fff";
	attr_image_btn2.style.border = "2px solid #333";
    attr_image_btn2.style.background = "#fff";
    attr_image2.style.transform = "translateX(110%)";
    attr_image2.style.opacity = "0";
    attr_image1.style.transform = "translateX(0)";
    attr_image1.style.opacity = "1";
    
    setTimeout(() => {
	    autoImage2();
    }, 3000);
    

}

attr_image_btn1.addEventListener("click", () => {
    attr_image_btn1.style.background = "#333";
	attr_image_btn1.style.border = "2px solid #fff";
	attr_image_btn2.style.border = "2px solid #333";
    attr_image_btn2.style.background = "#fff";
    attr_image2.style.transform = "translateX(110%)";
    attr_image2.style.opacity = "0";
    attr_image1.style.transform = "translateX(0)";
    attr_image1.style.opacity = "1";
    
})

attr_image_btn2.addEventListener("click", () => {
    attr_image_btn2.style.background = "#333";
	attr_image_btn2.style.border = "2px solid #fff";
    attr_image_btn1.style.background = "#fff";
    attr_image_btn1.style.border = "2px solid #333";
    attr_image1.style.transform = "translateX(-120%)";
    attr_image1.style.opacity = "0";
    attr_image2.style.transform = "translateX(0)"
    attr_image2.style.opacity = "1";
})



// 뒤로가기 

const back_btn = document.querySelector(".back_btn");
back_btn.addEventListener("click", () => {
	location.href = `/member/city?city_name=${city_name}&latitude=${latitude}&longitude=${longitude}`;
});

// 시 이름 클릭
if(user_grade == "m") {
	const go_city = document.querySelector(".go_city");
	go_city.addEventListener("click", () => {
		location.href = `/member/city?city_name=${city_name}&latitude=${latitude}&longitude=${longitude}`;
	});
}


// 별점
const attr_rate = document.querySelector(".attr_rate");

// 메뉴 탭
const side_option = document.getElementsByName("option");
const attr_card_list = document.querySelector(".attr_card_list");



// 메뉴 버튼 클릭 이벤트
side_option.forEach((option) => {
	option.addEventListener("click", (e) => {
		
		// 클릭 시 스크롤 리셋
		attr_card_list.scrollTop = 0;
	
		// 리뷰 버튼 클릭
		if(e.target.value==="review") {
			fetch("https://gieok.icu/member/attr_review", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					attr_code: attr_code,
				}),
			})
			.then(res => res.json())
			.then(res => {
				
				// 리뷰 목록
				attrReview(res);
				
				attrReviewSubmit();
				
			}); // then
			
		}else {
			// 식당&카페 클릭 시
			fetch("https://gieok.icu/member/attr_fac", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					option: option.value,
					attr_code: attr_code,
					
				}),
			})
			.then(res => res.json())
			.then(res => changeSideOption(res))
		}
	});
	
});


// 식당&카페 목록
function changeSideOption(list) {
	attr_card_list.innerHTML = "";
	if(list.length == 0) {
		attr_card_list.innerHTML = "<span class='no_list'> 아직 등록된 장소가 없습니다 :(</span>";
	}else {
	
		list.forEach((item) => {
			let content = `<li class="attr_card_item">
									<img src="../resources/upload/${item.fac_img }/" class="attr_card_img">
									<span class="attr_card_name">${item.fac_name }</span>
									<span class="attr_card_name">${item.fac_addr }</span>
									<a href="${item.fac_link }" target="_blank"><span class="fac_link">
									${item.fac_name } 네이버 링크</span></a>
							`;
			
			if(item.fac_optime == null || item.fac_cltime == null) {
				content += `</li>`;
			}else {
				content += ` <span class="attr_card_name">${item.fac_optime } ~ 
							 ${item.fac_cltime }</span></li>
							`;
			}
	
			attr_card_list.innerHTML += content;
		});
	}
}


// 리뷰 목록
function attrReview(list) {
	
	attr_card_list.innerHTML = "";
	let review_form = `<li class="attr_card_item">
					   	<form class="attr_review_form" name="attr_review_form" enctype="multipart/form-data">
				 			<input type="hidden" value="${attr_code }" name="attr_code" id="code">
							<textarea class="attr_review_content" name="rev_content"></textarea>
						 	<input type="text" value="0/80자" id="content_limit" readonly>
						 	<input type="file" accept="image/*" name="image" class="attr_review_file">
						 	<select name="rev_rate">
						 		<option value="5" selected>⭐⭐⭐⭐⭐</option>
						 		<option value="4">⭐⭐⭐⭐</option>
						 		<option value="3">⭐⭐⭐</option>
						 		<option value="2">⭐⭐</option>
						 		<option value="1">⭐</option>
						 	</select>
						 	<input type="button" value="작성" class="review_submit_btn">
					 	</form>
					   </li>`;
	
	attr_card_list.innerHTML += review_form;



	if(list.length == 0) {
		attr_card_list.innerHTML += "<span class='no_list'> 아직 등록된 리뷰가 없습니다 :(</span>";
	}else {					
		list.forEach((item) => {
			let review = `<li class="attr_card_item">
							<span class="review_writer">${item.rev_writer}</span>
						 `;
						 
			for(let i=0; i<item.rev_rate; i++) {
				review += `⭐`;
			}
			
			if(item.user_code!=user_code) {
				review += `<input type="button" value="신고하기" class="attr_review_delete" 
							onclick="reviewReport(${item.rev_code});">`
			}
			if(item.rev_img != null) {
				review += `<img src="/resources/upload/review/${item.attr_code}/${item.rev_img}" class="attr_review_img">`
			}
			
			review +=	  `<p class="review_list">
								${item.rev_content}
							</p>
						  </li>
						  `;
			
			attr_card_list.innerHTML += review;
		});
	}
	
}

function attrReviewSubmit() {

	// 리뷰 줄 제한
	const attr_review_content = document.querySelector(".attr_review_content");
	const content_limit = document.querySelector("#content_limit");
	
	attr_review_content.addEventListener("keyup", (e) => {
		let content = e.target.value;
		let content_length = content.length;
		if(content_length > 80) {
			alert("리뷰는 최대 80자까지 작성 가능합니다");
			e.target.value = content.substring(0,80);
		}
		content_limit.value = `${e.target.value.length}/80자`;
	});

	// 리뷰 등록
	const review_submit_btn = document.querySelector(".review_submit_btn");
	const review_write_form = document.attr_review_form;
	
	review_submit_btn.addEventListener("click", () => {
		user_code = document.querySelector("#user_code").value;
		if(user_code== "") {
			if(confirm("리뷰 작성은 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
				location.href = "/login";
			}
		}else if(attr_review_content.value.trim() == "") {
			alert("리뷰를 입력해주세요!");
		}else {
			
			const review = new FormData(review_write_form);
			
			fetch("https://gieok.icu/member/attr_review_write", {
				method: "POST",
				body : review,
			})
			.then(res => res.json())
			.then(function(res) {
				if(res.length > 0) {
					alert("리뷰가 등록되었습니다!");
					attrReview(res);
					
					
					let sum = 0;
					let length = res.length;
					res.forEach((item) => {
							sum += item.rev_rate				
					})
					

					
					let avg = Math.ceil(sum / length * 100) / 100;
					
					attr_rate.innerText = `⭐ ${avg}`;
					
					attrReviewSubmit();
				}else {
					alert("시스템 오류! 관리자에게 문의하세요:(");
				}				
			})
				
		}
	});
}

// 리뷰 신고하기 버튼
function reviewReport(rev_code) {

	user_code = document.querySelector("#user_code").value;
	if(user_code== "") {
		if(confirm("신고하기는 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
			location.href = "/login";
		}
	}else {
		let report_type = prompt("신고 유형 번호를 입력해주세요!\n1. 부적절한 내용\n2. 욕설/비방\n3. 광고/홍보\n4. 도배");
		
		if(report_type==null){
		}							  	
		else if(report_type!="1" && report_type!="2"&& report_type!=="3" && report_type!="4") {
			alert("유효하지 않은 옵션입니다!");
		}else {
			fetch("https://gieok.icu/member/attr_review_report", {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify({
					rev_code: rev_code,
					report_type: report_type,
				}),
			})
			.then(res => res.text())
			.then(res => {
				if(res=="success") {
					alert("신고가 접수되었습니다!");
				}else if(res=="fail") {
					alert("이미 신고한 리뷰입니다!");
				}else if(res=="error") {
					alert("시스템 오류! 관리자에게 문의해주세요!");
				}
			})
		}
	

	}
	
}

// 좋아요 버튼

const like_btn = document.querySelector("#check_like");

like_btn.addEventListener("click", () => {
	
	user_code = document.querySelector("#user_code").value;
	if(user_code== "") {
		if(confirm("좋아요는 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")) {
			location.href = "/login";
		}
		like_btn.checked = false;
	}else {
		fetch("https://gieok.icu/member/attr_like", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				attr_code: attr_code,
			}),
		})
		.then(res => res.text())
		.then(res => {
			if(res == "Y") {
				like_btn.checked = true;
			}else if(res == "N") {
				like_btn.checked = false;
			}else if(res == "fail") {
				alert("시스템 오류! 관리자에게 문의하세요:(");
			}
		})
	}
	
});





