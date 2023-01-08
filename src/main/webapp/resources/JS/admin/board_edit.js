let start_date_edit = document.getElementById('start_date');
let end_date_edit = document.getElementById('end_date');
let board_edit_date_img = document.querySelector(".board_date_img");


function board_write_reset(){
    let board_content_value = document.querySelector("#board_content").value;
    let board_title_value = document.querySelector("#board_title").value;
    
    if((board_title_value != "") || (board_content_value != "")){
        var reset_msg ="작성 중인 글이 있습니다.\n정말로 취소하시겠어요?";
        let check = confirm(reset_msg);
        if(check == true) {
	        location.href='/board_list';
        }
    }else{
        location.href='/board_list';
    }    
}

//등록 버튼 클릭시
function board_write_submit(){
    let board_title = document.querySelector("#board_title");
	let board_content = document.querySelector("#board_content");
    
    if(board_title.value == ""){
        alert('글 제목을 작성해 주세요!');	
    }else if(board_content.value == "") {
        alert('글 내용을 작성해 주세요!');	
    }
}

//콤보박스(공지사항, 이벤트) 선택 시 
let board_edit_content = document.querySelector(".board_content");
let board_type = document.querySelector("#board_type").value;

end_date.addEventListener("input", function(e){
    
    if(start_date_edit > end_date_edit){
    	alert('날짜를 확인해 주세요:)');
    	document.getElementById('end_date').value = "";
    }
});


//이미지 썸네일
function previewImage1() {
    var preview = new FileReader();
        preview.onload = function(e){
            document.getElementById('upload_img1').src = e.target.result;
            };
        preview.readAsDataURL(document.getElementById('file1').files[0]);
}

function previewImage2(){
    var preview = new FileReader();
    preview.onload = function(e){
        document.getElementById('upload_img2').src = e.target.result;
    };
    preview.readAsDataURL(document.getElementById('file2').files[0]);
}


let file1_edit = document.getElementById('file1');
let file2_edit = document.getElementById('file2');

if(board_type === "notice"){
    board_edit_date_img.style.cssText = 'width : 0px';
    board_edit_content.style.width = '780px';
    document.getElementsByTagName('h1')[0].innerText="공지사항 수정";
    start_date_edit.removeAttribute("required");
	end_date_edit.removeAttribute("required");	
}else{
    board_edit_date_img.style.cssText = 'width : 185px; display : flex';
    board_edit_content.style.width = '630px';
    document.getElementsByTagName('h1')[0].innerText="이벤트 수정";
	start_date_edit.setAttribute("required", "true");
	end_date_edit.setAttribute("required", "true");	
}
