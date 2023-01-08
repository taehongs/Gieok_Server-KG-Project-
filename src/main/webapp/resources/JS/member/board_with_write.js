/**
 * board_with_write.js
 */


/**
 * 도 목록 선택
 */

const provinceOption = document.querySelector("#province");

provinceOption.addEventListener("focus", () => {
	
	fetch("https://gieok.icu/board_with_province", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		}
	})
	.then(res => res.json())
	.then(res => listProv(res))
});

function listProv(data) {
	provinceOption.innerHTML = "<option value='' disabled selected> 도 선택 </option>";
	data.forEach((item) => {
		let province = `<option value=${item.province_id}>
						${item.province_name}
						</option>`
		provinceOption.innerHTML += province;
	})
}


// 도 선택 값 hidden으로 전달
provinceOption.addEventListener("click", () => {
	
	let p_id = document.querySelector("#province");
	let p_nm = document.querySelector("#with_province_name");
	
	p_nm.value = p_id.options[p_id.selectedIndex].text;
	

	
});



/**
 * 시 목록 선택
 */

//const provinceOption = document.querySelector("#province");
const cityOption = document.querySelector("#city");

provinceOption.addEventListener("change", () => {

   fetch("https://gieok.icu/board_with_city", {
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

function listCity(data) {
   cityOption.innerHTML = "<option value='' selected> 시 선택 </option>";
   data.forEach((item) => {
      let city = `<option value=${item.city_code}>
                  ${item.city_name}
               </option>`
      cityOption.innerHTML += city;
   })
}


// 시 선택 값 hidden으로 전달
cityOption.addEventListener("click", () => {
	
	let c_id = document.querySelector("#city");
	let c_nm = document.querySelector("#with_city_name");
	
	c_nm.value = c_id.options[c_id.selectedIndex].text;
	
});



/**
 * 명소 목록 선택
 */

//const citylistOption = document.querySelector("#city");
const attrOption = document.querySelector("#attraction");

cityOption.addEventListener("change", () => {
   fetch("https://gieok.icu/board_with_attr", {
      method: "POST",
      headers: {
         "Content-Type": "application/json",
      },
      body: JSON.stringify({
         city_id: cityOption.value,
      }),
   })
   .then(res => res.json())
   .then(res => listAttr(res))
});

function listAttr(data) {
   attrOption.innerHTML = "<option value='' selected> 명소 선택 </option>";
   data.forEach((item) => {
      let attr = `<option value=${item.attr_code}>
                  ${item.attr_name}
               </option>`
      attrOption.innerHTML += attr;
   })
}


//시 선택 값 hidden으로 전달
attrOption.addEventListener("click", () => {
	
	let a_id = document.querySelector("#attraction");
	let a_nm = document.querySelector("#with_attr_name");
	
	a_nm.value = a_id.options[a_id.selectedIndex].text;

});



/**
 * 기간 선택
 */

// 시작일 오늘 날짜로 설정
document.querySelector("#withStartDay").value = new Date().toISOString().substring(0, 10);

let toDay = document.querySelector("#withStartDay").value;
let withStartDay = document.querySelector("#withStartDay");
let withEndDay = document.querySelector("#withEndDay");

// 시작일 유효성 검사
withStartDay.addEventListener("input", () => {
	
   if(withStartDay.value < toDay){
	   	alert('오늘 날짜 이후로 선택가능합니다 😊');
	   	document.querySelector("#withStartDay").value = toDay;
	   	
	   }
	
});


//// 종료일 유효성 검사
withEndDay.addEventListener("input", function(event) {
   
   if(withStartDay.value > withEndDay.value){
   	alert('시작 날짜 이후로 선택해주세요 😊');
   	document.querySelector("#withEndDay").value = "";
   	
   }
   
});



/**
 * 동행 textarea 글자수 체크
 */

/*const with_board_content_count = document.querySelector(".board_content");
const with_con_counter = document.querySelector("#with_con_counter");
let cont = document.querySelector("#with_board_content");
let noteEditable = document.querySelector('.note-editable');

noteEditable.addEventListener("keyup", (e) => {
	let content = e.target.value;
	let content_length = content.length;
	if(content_length > 50) {
		alert("최대 50자까지 작성 가능합니다");
		e.target.value = content.substring(0,50);
	}
	with_con_counter.value = `${e.target.value.length}/50자`;
});*/



/**
 * 전체 유효성 검사
 */

let form = document.board_with;
let with_province_name_check = form.with_province_name;
let withStartDay_check = form.board_startDay;
let withEndDay_check = form.board_endDay;
let memCount_check = form.board_memCount;
let with_board_title_check = form.with_board_title;
let with_board_content_check = form.board_content;

function formcheck() {


	
	// 도 선택 유효성 검사
	if (with_province_name_check.value == "") {
		alert('도를 선택하세요 😊');
		
		return false;
		
	}
	
	// 시작날짜 유효성 검사
	if (withStartDay_check.value == "") {
		alert('시작날짜를 선택하세요 😊')
		
		return false;
	}
	
	// 종료날짜 유효성 검사
	if (withEndDay_check.value == "") {
		alert('종료날짜를 선택하세요 😊')
		
		return false;
	}
	
	// 모집인원 유효성 검사
	if (memCount_check.value == 0) {
		alert('모집인원은 1인 이상부터 가능합니다 😊');
		
		return false;
		
	}
	
	// 제목 유효성 검사
	if (with_board_title_check.value == "") {
		alert('제목을 입력하세요 😊')
		$('.with_board_title').focus();
		
		return false;
	}
	
	// 내용 유효성 검사
	if (with_board_content_check.value == "") {
		alert('내용을 입력하세요 😊');
		$('.note-editable').focus();
		
		return false;
		
	}
	
	return true;
	
}



/**
 * 취소버튼
 */
const cancelBtn = document.querySelector("#with_cancel");

cancelBtn.addEventListener("click", () => {
	let page = document.querySelector("#page").value;
	let category = document.querySelector("#category").value;
	let keyword = document.querySelector("#keyword").value;
	
	location.href = `/board_with_list?page=${page}&category=${category}&keyword=${keyword}`;
});

/**
 * 글자수 세기
 */
const with_board_content = document.querySelector(".with_board_content");
const checktxt = document.querySelector("#checktxt");

with_board_content.addEventListener("keyup", (e) => {
   let content = e.target.value;
   let content_length = content.length;
   if(content_length > 100) {
      alert("리뷰는 최대 100자까지 작성 가능합니다");
      e.target.value = content.substring(0,100);
   }
   
   checktxt.value = "글자수 : " + content_length + " / 100자";
});


