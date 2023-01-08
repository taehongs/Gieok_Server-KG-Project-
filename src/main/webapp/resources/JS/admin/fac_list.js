// 주변 시설 등록 버튼
const fac_regist_btn = document.querySelector(".add_contents");

fac_regist_btn.addEventListener("click", () => {
	let attr_code = document.querySelector("#attr_code").value;
	location.href = `/admin/fac_regist?attr_code=${attr_code}`;
});


// 전체 선택 버튼
let select_all_btn_checked = false;

const select_all_btn = document.querySelector(".board_select");
const fac_checkBox = document.querySelectorAll(".fac_checkBox");

select_all_btn.addEventListener("click", () => {

	if (select_all_btn_checked) {
		select_all_btn_checked = false; 
		fac_checkBox.forEach((item) => {
			item.checked = false;
		});
	} else {
		select_all_btn_checked = true; 
		fac_checkBox.forEach((item) => {
			item.checked = true;
		});
	}
	
});

// 선택 삭제 버튼
const select_delete_btn = document.querySelector(".select_delete");
let isEmpty = true;

select_delete_btn.addEventListener("click", () => {

	isEmpty = true;

	for (let i = 0; i < fac_checkBox.length; i++) {
		if(fac_checkBox[i].checked === true) {
			isEmpty = false;
		}
	}
	
});

function fac_delete_validate() {
	if(isEmpty === false) {
		return true;
	}
	
	alert("삭제할 명소를 선택해주세요!");
	return false;
}

// 정렬 전체 버튼
const sort_btn = document.querySelector("#sort_btn");
sort_btn.addEventListener("click", () => {
	if(search_form.sort.value == "") {
		search_form.category.value="";
		search_form.keyword.value="";
	}	
});


