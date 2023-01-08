// 아이디, 비밀번호 찾기 구분
const find_select = document.querySelectorAll('input[type="radio"]');
let radio_select = "find_id"; // 아이디 찾기 = form_id, 비밀번호 찾기 = form_pw
let radio_select_not = "find_pw";
for (let i = 0; i < find_select.length; i++) {
    find_select[i].addEventListener("change", () => {
        if (find_select[i].checked) {
            if (i == 0) {
                radio_select = "find_id";
                radio_select_not = "find_pw";
            } else {
                radio_select = "find_pw";
                radio_select_not = "find_id";
            }
        }
        find_check();
    });
}

let user_id = document.querySelector(".user_id_box>input");
let user_mobcarr = "";
let user_ph1 = "";
let user_ph2 = "";
let user_ph3 = "";
let user_email = "";
let user_domain = "";
// 버튼 초기값
let find_button = document.querySelectorAll(".user_button_box>input:first-of-type");

function find_check() {
    // 초기화
    user_mobcarr = document.querySelector(`#${radio_select_not} #user_mobcarr`);
    user_ph1 = document.querySelector(`#${radio_select_not}>.user_phone_box>input:first-of-type`);
    user_ph2 = document.querySelector(`#${radio_select_not}>.user_phone_box>input:nth-of-type(2)`);
    user_ph3 = document.querySelector(`#${radio_select_not}>.user_phone_box>input:last-of-type`);
    user_email = document.querySelector(`#${radio_select_not}>.user_email_box>input:first-of-type`);
    user_domain = document.querySelector(`#${radio_select_not}>.user_email_box>input:last-of-type`);
    user_domain_select = document.querySelector(`#${radio_select_not}>.user_email_box>#user_domain_select`);

    user_id.value = "";
    user_mobcarr[0].selected = true;
    user_ph1.value = "";
    user_ph2.value = "";
    user_ph3.value = "";
    user_email.value = "";
    user_domain.value = "";
    user_domain_select[0].selected = true;

    //값 입력
    user_mobcarr = document.querySelector(`#${radio_select} #user_mobcarr`);
    user_ph1 = document.querySelector(`#${radio_select}>.user_phone_box>input:first-of-type`);
    user_ph2 = document.querySelector(`#${radio_select}>.user_phone_box>input:nth-of-type(2)`);
    user_ph3 = document.querySelector(`#${radio_select}>.user_phone_box>input:last-of-type`);
    user_email = document.querySelector(`#${radio_select}>.user_email_box>input:first-of-type`);
    user_domain = document.querySelector(`#${radio_select}>.user_email_box>input:last-of-type`);
    user_domain_select = document.querySelector(`#${radio_select}>.user_email_box>#user_domain_select`);

    // 이메일 직접입력, 선택
    user_domain_select.addEventListener("change", () => {
        user_domain.value = user_domain_select.value;
        if (user_domain.value != "") {
            user_domain.setAttribute("readonly", true);
        } else {
            user_domain.removeAttribute("readonly");
        }
    });
}

find_check();

function find_submit() {
    if (user_mobcarr.value == "") {
        alert("통신사를 선택해주세요");
        user_mobcarr.focus();
        return false;
    }

    let ph1Val = user_ph1.value;
    let ph2Val = user_ph2.value;
    let ph3Val = user_ph3.value;

    if (ph1Val == "" || ph1Val.length < 3) {
        alert("연락처를 확인해주세요");
        user_ph1.focus();
        return false;
    } else if (ph1Val != "010" && ph1Val != "011" && ph1Val != "016" && ph1Val != "017" && ph1Val != "019") {
        alert("잘못된 형식입니다");
        user_ph1.focus();
        return false;
    }

    if (ph2Val == "" || ph2Val.length < 4) {
        alert("연락처를 확인해주세요");
        user_ph2.focus();
        return false;
    }

    if (ph3Val == "" || ph3Val.length < 4) {
        alert("연락처를 확인해주세요");
        user_ph3.focus();
        return false;
    }

    let emailVal = user_email.value;
    let domainVal = user_domain.value;

    if (emailVal == "") {
        alert("이메일을 입력해주세요");
        user_email.focus();
        return false;
    }
    if (domainVal == "") {
        alert("도메인을 입력 또는 선택해주세요");
        user_domain.focus();
        return false;
    }
//  find_id_form
//  find_pw_form
    
    const find_id_form = new FormData(document.find_id_form);
    const find_pw_form = new FormData(document.find_pw_form);
    
    if(radio_select == 'find_id') {
		fetch("https://gieok.icu/member/find_id_pwOK", {
				method: "POST",
				body: find_id_form,
		})
		.then(response => response.json())
		.then(function(response) {
			if(response.msg !=""){
				alert(response.msg);
			} else {
				alert('해당하는 정보가 없습니다');
			}
		});
    } else {
		fetch("https://gieok.icu/member/find_id_pwOK", {
				method: "POST",
				body: find_pw_form,
		})
		.then(response => response.json())
		.then(function(response) {
			if(response.msg !=""){
				alert(response.msg);
			} else {
				alert('해당하는 정보가 없습니다');
			}
		});
    }
    

    return true;
}

for (let i = 0; i < find_button.length; i++) {
    find_button[i].addEventListener("click", () => {
        find_submit();
    });
}


