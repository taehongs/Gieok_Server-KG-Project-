function change_type(){
    if(board_type === "event"){
        document.getElementsByTagName('h1')[0].innerText="이벤트";
    }else{
    	document.querySelector("#board_date").style.display = 'none';
    }
}

//콤보박스(공지사항, 이벤트) 선택 시 
let board_type = document.querySelector("#board_type").value;
change_type();

// page랑 board_no 값
const page = document.getElementById("page").value;

// 수정 버튼 

const grade = document.getElementById("grade").value;
if(grade == "a" || grade == "s") {
	const board_no = document.getElementById("board_no").value;
	const edit_btn = document.getElementById("edit_btn");
	edit_btn.addEventListener("click", () => {
		location.href = `/admin/board_edit?no=${board_no}&page=${page}`;
	})

}

// 목록 버튼
const list_btn = document.getElementById("list_btn");
list_btn.addEventListener("click", () => {
	location.href=`board_list?page=${page}`;
})

