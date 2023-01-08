
function board_write_reset(){
    let board_content_value = document.querySelector("#summernote").value;
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
	let board_content = document.querySelector("#summernote");
    
    if(board_title.value == ""){
        alert('글 제목을 작성해 주세요!');	
    }else if(board_content.value == "") {
        alert('글 내용을 작성해 주세요!');	
    }
}


//콤보박스(공지사항, 이벤트) 선택 시 
let board_date_img = document.querySelector(".board_date_img");
//let board_content = document.querySelector(".board_content");
let start_date = document.getElementById('start_date')
let end_date = document.getElementById('end_date')
let file1 = document.getElementById('file1');
let file2 = document.getElementById('file2');
document.getElementById("board_type").addEventListener("change", function(){
    let board_type = document.querySelector("#board_type").value;
    
    if(board_type === "notice"){
        board_date_img.style.cssText = 'width : 0px';
        //board_content.style.width = '780px';
        document.getElementsByTagName('h1')[0].innerText="공지사항 등록";
        start_date.removeAttribute("required");
		end_date.removeAttribute("required");
		file1.removeAttribute("required");
        file2.removeAttribute("required");
    }else{
        board_date_img.style.cssText = 'width : 185px; display : flex';
        //board_content.style.width = '630px';
        document.getElementsByTagName('h1')[0].innerText="이벤트 등록";
		start_date.setAttribute("required", "true");
		end_date.setAttribute("required", "true");	
		file1.setAttribute("required", "true");
        file2.setAttribute("required", "true");
    }
   	
   	
    
    
});

//오늘 날짜로 설정
 document.getElementById('start_date').value = new Date().toISOString().substring(0, 10);
 //document.getElementById('end_date').value = new Date().toISOString().substring(0, 10);


end_date.addEventListener("input", function(e){

    let start_date = document.getElementById('start_date').value;
    let end_date = document.getElementById('end_date').value;
    
    if(start_date > end_date){
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
 
