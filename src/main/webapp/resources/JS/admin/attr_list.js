// 명소 등록 버튼

const attr_regist_btn = document.querySelector(".add_contents");
attr_regist_btn.addEventListener("click", () => {
	location.href = "/admin/attr_regist";
})

// 검색 버튼
const search_btn = document.querySelector("#search_btn");
const search_form = document.search_form;
	
search_btn.addEventListener("click", (e) => {
	let category = search_form.category;
	if(category.value == "") {
		alert("검색할 카테고리를 선택해 주세요!");
		e.preventDefault();
	}else {
		if(search_form.keyword.value == "") {
			alert("검색어를 입력해 주세요!");
			e.preventDefault();
		}else {
			let sort = search_form.sort;
			sort.options[0].selected = true;
		}
	}
});

// 정렬 버튼
const sort_btn = document.querySelector("#sort_btn");
sort_btn.addEventListener("click", () => {
	if(search_form.sort.value == "") {
		search_form.category.value="";
		search_form.keyword.value="";
	}	

});


// 전체 선택 버튼

let select_all_btn_checked = false;

const select_all_btn = document.querySelector(".board_select");
const attr_checkBox = document.querySelectorAll(".attr_checkBox");
select_all_btn.addEventListener("click", () => {

	if(select_all_btn_checked) {
		select_all_btn_checked = false; 
		attr_checkBox.forEach((item) => {
			item.checked = false;
		});
	}else {
		select_all_btn_checked = true; 
		attr_checkBox.forEach((item) => {
			item.checked = true;
		});
	}
});


// 선택 삭제 버튼
const select_delete_btn = document.querySelector(".select_delete");
let isEmpty = true;
select_delete_btn.addEventListener("click", () => {
	isEmpty = true;
	for(let i=0; i<attr_checkBox.length; i++) {
		if(attr_checkBox[i].checked===true) {
			isEmpty = false;
		}
	}
});

function attr_delete_validate() {
	if(isEmpty === false) {
		return true;
	}
	
	alert("삭제할 명소를 선택해주세요!");
	return false;
}








