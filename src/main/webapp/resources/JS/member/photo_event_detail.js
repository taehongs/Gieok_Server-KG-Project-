// 수정
const update_button = document.getElementById('update_button');
let board_no = document.getElementById('board_no').innerText;
if(update_button != null) {
   update_button.addEventListener('click', () => {
      location.href = `/photo_event_update?photo_no=${board_no}`;
   });
}


// 뒤로가기
const back_button = document.querySelector('.board_post_button>.back_button');
back_button.addEventListener('click', () => {
   location.href = "/photo_event_list";
});

// 삭제
const delete_button = document.getElementById('delete_button');
if(delete_button != null) {
   delete_button.addEventListener('click', () => {
      location.href = `/photo_event_delete?photo_no=${board_no}`;
   });
}

// 좋아요 & 신고
const like_checkbox = document.getElementById('like_checkbox');
like_checkbox.addEventListener('change', () => {
   like_check();
});

function like_check() {
   fetch(`https://gieok.icu/photo_event_detailLike?`, {
      method: "POST",
      headers: {
      	"Content-Type": "application/json",
      },
      body: JSON.stringify({
      	board_no: board_no,
      }),
   })
   .then((response) => response.json())
   .then(function(response) {
      if(response.msg != "") {
         alert(response.msg);
         if(response.url != undefined) {
            location.href = response.url;
         }
      } else {
         alert('시스템 오류');
      }
   });
}

const report_checkbox = document.getElementById('report_checkbox');
if(report_checkbox != null) {
   report_checkbox.addEventListener('change', () => {
      report_check();
   });
}

function report_check() {

	let report_type = prompt("신고 유형 번호를 입력해주세요!\n1. 부적절한 내용\n2. 욕설/비방\n3. 광고/홍보\n4. 도배");
		
	if(report_type==null){
	}							  	
	else if(report_type!="1" && report_type!="2"&& report_type!=="3" && report_type!="4") {
		alert("유효하지 않은 옵션입니다!");
	}else {

	   fetch(`https://gieok.icu/photo_event_detailReport`, {
	      method: "POST",
	      headers: {
	      	"Content-Type": "application/json",
	      },
	      body: JSON.stringify({
	      	report_type: report_type,
	      	board_no: board_no,
	      }),
	   })
	   .then((response) => response.json())
	   .then(function(response) {
	      if(response.msg != "") {
	         alert(response.msg);
	         if(response.url != undefined) {
	            location.href = response.url;
	         }
	      } else {
	         alert('시스템 오류');
	      }
	   });
	}
}