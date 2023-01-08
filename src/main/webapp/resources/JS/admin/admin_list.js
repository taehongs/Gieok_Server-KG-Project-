const serach_button = document.getElementById('.serach_button');
const get_user_id = document.getElementById('.user_id');
const get_user_code = document.getElementById('.user_code');

const search_admin_button = document.getElementById('search_admin_user');
search_admin_button.addEventListener('click', () => {
	show_detail();
})

let page = document.getElementById('page');

function view_detail() {
	
	let search_id = document.getElementById('search_user').value;
	
	if(search_id == '') {
		alert('검색어를 입력하세요');
		return false;
	}
	
	fetch("https://gieok.icu/admin/admin_search", {
		method: "POST",
		headers: {
			"Content-Type" : "application/json",
		},
		body: JSON.stringify({
			search_id: search_id,
		}),
	})
	.then(response => response.json())
	.then(response => {
		if(response.msg != "") {
			alert(response.msg);
		} else {
			user_detail(response)
		}
	});

}

// 열기
const detail_box = document.querySelector('.user_detail_box');
function show_detail() {
	detail_box.style.top="calc(50% - 150px)";
	detail_box.style.opacity = "1";
	detail_box.style.zIndex = 10;
}

// 닫기
function close_detail() {
	detail_box.style.top="calc(50% - 250px)";
	detail_box.style.opacity = "0";
	detail_box.style.zIndex = -1;
}

// 관리자 권한 주기 버튼
const add_button = document.querySelector('.add_admin_button');
add_button.addEventListener('click', () => {
	let user_id = document.querySelector('.user_id').innerText;
	add_admin(user_id);
});

// 관리자 위임
function add_admin(user_id) {
	fetch("https://gieok.icu/admin/add_admin", {
		method: "POST",
		headers: {
			"Content-Type" : "application/json",
		},
		body: JSON.stringify({
			user_id: user_id,
		}),
	})
	.then(response => response.json())
	.then(response => {
		if(response.msg == "") {
			alert('위임 실패');
		} else {
			alert(response.msg);
			window.location.href = response.url;
		}
	});
}

// 관리자 권한 해제 버튼
const delete_button = document.querySelector('.delete_admin_button');
delete_button.addEventListener('click', () => {
	let user_id = document.querySelector('.user_id').innerText;
	delete_admin(user_id);
});

// 관리자 해임
function delete_admin(user_id) {
	fetch("https://gieok.icu/admin/delete_admin", {
		method: "POST",
		headers: {
			"Content-Type" : "application/json",
		},
		body: JSON.stringify({
			user_id: user_id,
		}),
	})
	.then(response => response.json())
	.then(response => {
		if(response.msg == "") {
			alert('해임 실패');
		} else {
			alert(response.msg);
			window.location.href = response.url;
		}
	});
}

// 검색 상세
let admin_id = "";
let admin_name = "";
let input_id = "";
function user_detail(response) {
	show_detail();
	
	admin_id = document.querySelector('.user_id');
	admin_name = document.querySelector('.user_name');
	input_id = document.getElementById('search_user');
	
	let search_button = document.querySelector('.search_button');
	
	admin_id.innerText = `${response.user.user_id}`;
	admin_name.innerText = `${response.user.user_name}`;
}

// 취소
const back_button = document.querySelector('.admin_state_button>input[type="button"]:last-of-type');
back_button.addEventListener('click', () => {
	admin_id.innerText = "";
	admin_name.innerText = "";
	input_id.value = "";
	
	close_detail();
});

// 검색
const search_button = document.querySelector('.search_button');
search_button.addEventListener('click', () => {
	view_detail();
});











