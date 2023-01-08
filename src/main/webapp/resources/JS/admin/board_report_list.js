const board_report_list_btn = document.querySelectorAll(".board_report_list_btn");
const bad_board_no = document.querySelectorAll(".bad_board_no");
const bad_board_type = document.querySelectorAll(".bad_board_type");
const bad_board_title = document.querySelectorAll(".bad_board_title");


for(let i=0; i<board_report_list_btn.length; i++) {
	board_report_list_btn[i].addEventListener("click", () => {
		let board_no = bad_board_no[i].value;
		let board_type = bad_board_type[i].value;

		if(board_type == "3") {
			let board_title = bad_board_title[i].value;
			window.location.href = "/board_with_list?category=board_title&keyword="+board_title;
		}else {
			window.location.href = "/photo_event_detail?photo_no="+board_no;
		}
		
		
	});
}