

const side_admin_list_btn = document.getElementById("admin_list_btn");
if(side_admin_list_btn != null) {
	side_admin_list_btn.addEventListener("click", () => {
		location.href = "/admin/admin_list";
	});
}


const side_member_list_btn = document.getElementById("member_list_btn");
side_member_list_btn.addEventListener("click", () => {
	location.href = "/admin/user_list";
});

const side_review_report_list_btn = document.getElementById("review_report_list_btn");
side_review_report_list_btn.addEventListener("click", () => {
	location.href = "/admin/review_report_list";
});

const side_board_report_list_btn = document.getElementById("board_report_list_btn");
side_board_report_list_btn.addEventListener("click", () => {
	location.href = "/admin/board_report_list";
});

const side_attr_list_btn = document.getElementById("attr_list_btn");
side_attr_list_btn.addEventListener("click", () => {
	location.href = "/admin/attr_list";
});

const side_board_list_btn = document.getElementById("board_list_btn");
side_board_list_btn.addEventListener("click", () => {
	location.href = "/board_list";
});

const side_with_list_btn = document.getElementById("with_list_btn");
side_with_list_btn.addEventListener("click", () => {
	location.href = "/board_with_list";
});

const side_photo_event_list_btn = document.getElementById("photo_event_list_btn");
side_photo_event_list_btn.addEventListener("click", () => {
	location.href = "/photo_event_list";
});












