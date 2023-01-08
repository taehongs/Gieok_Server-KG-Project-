const grade = document.getElementById("grade").value;


// 회원 등급 확인 (관리자일때 실행)
if(grade == "a" || grade == "s") {

	var delBtn = document.querySelector(".del_btn");
	addEventListener("change",function(){
		count();
		
	});

	const add_contents = document.querySelector(".add_contents");
	add_contents.addEventListener("click", () => {
		location.href='/admin/board_write';
	});
}


function deleteCheck(){
    var delCount = $("input[name='del_check']:checked").length;
    if(delCount == 0 ){
        alert("선택된 글이 없습니다!");
        alert(delCount);
        location.href="/board_list";
        return true;
    }
}

//체크박스 전체선택 및 해제
  var board_select = document.querySelector(".board_select");
  board_select.addEventListener("click", function(){
      
      const checkboxes = document.getElementsByName('check_num');
      var checkCount = $("input[name='check_num']:checked").length;
      if(checkCount == 0){
          checkboxes.forEach((checkbox)=>{
              checkbox.checked = true;
          });
          count();
      }else if(checkCount > 1 || checkCount< 11){
          checkboxes.forEach((checkbox)=>{
            checkbox.checked = false;
          });
          count();
      }
  });
  
  
  function count(){
      var delCount = $("input[name='check_num']:checked").length;
      if(delCount > 0){
          delBtn.removeAttribute("disabled");
          $(".del_count").text("("+delCount+")");
      }else{
          delBtn.setAttribute("disabled", "true");
             $(".del_count").text("(0)");
      }
   } 
   
  
  
  
  
  