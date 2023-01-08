/* =====// board list start //===== */
const board_list = document.querySelector(".board_list");

/* =====// board list end //===== */

/* =====// upload button //===== */
const upload_button = document.querySelector('.board_title>input[type="button"]');

if(upload_button != null) {
	upload_button.addEventListener('click', () => {
		location.href = "/photo_event_upload";
	});
}

/* =====// search start //===== */
const board_search_button = document.querySelector('.board_search>input[type="submit"]:last-of-type');
const board_search_input = document.querySelector('.board_search>input[type="text"]');

function search_box_check() {
	board_search_button.addEventListener('click', () => {
		if(board_search_input.value == "") {
			alert('검색어를 입력해주세요');
		}
	});
}

board_search_button.addEventListener('click', () => {
	search_box_check();
});
/* =====// search end //===== */


/* =====// delete select start //===== */


function delete_select() {
	let check_delete_select = document.querySelectorAll('.board_list>ul>li>input[name="photo_no"]');
	let check_count = 0;
	for(let i = 0; i < check_delete_select.length; i++) {
		if(check_delete_select[i].checked) {
			check_count++;
		}
	}
	
	if(check_count == 0) {
		alert('게시글이 선택되지 않았습니다');
		return false;
	}	
	
	return "/photo_event_listDelete";
}


/* =====// delete select end //===== */