// 주소 검색
function find_addr() {
	new daum.Postcode({
	    oncomplete: function(data) { // 선택시 입력값 세팅
	        document.getElementById("attreg_inp_addr").value = data.address; // 주소 넣기
	    }
	}).open();
}

const main_image = document.querySelector("#pre_img");
const main_photo = document.querySelector("#pre_thimg");

let imageCheck = false;
let photoCheck = false;

// 이미지 업로드
function preview1(e) {
	let files = event.target.files;
	
	if (files.length < 2) {
		if(imageCheck == false) {
			alert("메인 이미지는 2개를 선택해야합니다!");
			event.target.value = "";
			preview_img1.src = "";
			preview_img2.src = "";
			
		}else {
			if(files.length <= 1) {
				alert("메인 이미지는 2개를 선택해야합니다!");
				event.target.value = "";
				preview_img1.src = "";
				preview_img2.src = "";
				imageCheck = false;
			}
		}
	 
	}else if (files.length >= 3) {
		alert('이미지는 최대 2개까지 업로드가 가능합니다.');
		event.target.value = "";
		preview_img1.src = "";
		preview_img2.src = "";
		imageCheck = false;
	} else if(files.length==2) {
		preview_img1.src = URL.createObjectURL(files[0]);
		preview_img2.src = URL.createObjectURL(files[1]);
		imageCheck = true;
	}
	
}
function preview2() {
	if(main_photo.value!="") {
		preview_thimg.src=URL.createObjectURL(event.target.files[0]);
		photoCheck = true;
	}else{
		alert('썸네일 사진을 선택해주세요!');
		preview_thimg.src="";
		photoCheck = false;
	}
}

// 도 선택

const provinceOption = document.querySelector("#province");
const cityOption = document.querySelector("#city");

provinceOption.addEventListener("change", () => {
	
	fetch("https://gieok.icu/member/map", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			province_id: provinceOption.value,
		}),
	})
	.then(res => res.json())
	.then(res => listCity(res))
});

// 시 목록
function listCity(data) {
	
	cityOption.innerHTML = "<option value='' disabled selected> 시 선택 </option>";
	data.forEach((item) => {
		let city = `<option value=${item.city_code}>
						${item.city_name}
					</option>`
		cityOption.innerHTML += city;
	})
}

// 유효성 검사

let form = attr_regist;

// double click 방지 

let doubleSubmit = false;

function doubleSubmitCheck() {
	
	if(doubleSubmit) {
		return false;
	}else {
		doubleSubmit = true;
		return true;
	}
}


function formValidate() {
	const name = form.attr_name; 
	const province = form.province_id; 
	const city = form.city_code; 
	const addr = form.attr_addr; 
	const map = form.attr_map; 
	const info = form.attr_info; 
	
	if(name.value=="") {
		alert("명소 이름은 필수 입력 사항입니다!");
		name.focus();
		return false;
	}
	if(province.value=="") {
		alert("도 선택은 필수 선택 사항입니다!");
		province.focus();
		return false;
	}
	if(city.value=="") {
		alert("시 선택은 필수 선택 사항입니다!");
		city.focus();
		return false;
	}
	if(addr.value=="") {
		alert("명소 주소는 필수 검색 사항입니다!");
		addr.focus();
		return false;
	}
	if(map.value=="") {
		alert("네이버 지도 링크는 필수 입력 사항입니다!");
		map.focus();
		return false;
	}
	if(info.value=="") {
		alert("명소 설명은 필수 입력 사항입니다!");
		info.focus();
		return false;
	}
	if(imageCheck == false) {
		alert("메인 이미지를 2개 선택해주세요!");
		return false;
	}
	if(photoCheck == false) {
		alert("썸네일 사진을 선택해주세요!");
		return false;
	}
	
	return doubleSubmitCheck();
}


// 명소 등록 페이지 취소 버튼
const cancel_btn = document.querySelector(".attreg_cac_btn");
cancel_btn.addEventListener("click", () => {
	location.href = "/admin/attr_list";
})














