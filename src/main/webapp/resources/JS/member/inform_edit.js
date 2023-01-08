// 이메일, 도메인
let member_email = document.querySelector(
    ".member_edit_email>input:nth-of-type(1)"
);
let member_domain = document.querySelector(
    ".member_edit_email>input:nth-of-type(2)"
);

// 이메일 select 선택 시 도메인 input에 출력하는 함수
function email_domain_selector() {
    let email_domain = document.getElementById("member_edit_email");

    email_domain.addEventListener("change", () => {
        /**
         * "change":
         * email_domain의 input값이 변경될 때마다 이벤트 실행
         * ==> select값이 바뀔 때마다 input의 값이 변경되므로
         * select값이 바뀔 때마다 실행된다고 볼 수 있음
         */
        let text = email_domain.value;
        member_domain.value = text;

        if (text != "") {
            member_domain.setAttribute("readonly", true);
        } else {
            member_domain.removeAttribute("readonly");
        }
        /**
         * 도메인 값이 비어있지 않으면 disabled 속성을 줘서
         * 수정할 수 없게 하고
         * 도메인 값이 비어있으면 disabled 속성을 삭제해서
         * 수정할 수 있도록 해줌
         *
         * disabled -> 태그 속성
         * 해당 input이 활성화되지 않도록 변경(입력X 변경X)
         */
    });
}

email_domain_selector();

const edit_form = document.edit_form;
let chkMobcarr = document.getElementById('member_edit_phone');
let chkPhone1 = edit_form.user_ph1;
let chkPhone2 = edit_form.user_ph2;
let chkPhone3 = edit_form.user_ph3;
let chkEmail1 = edit_form.user_email;
let chkEmail2 = edit_form.user_domain;


const profile = document.querySelector(".member_image>img");


function edit_button_check() {

	let chk1 = /^[a-zA-Z0-9]([-.]?[a-zA-Z0-9])/;
    let chk2 = /^([-.]?[a-z])*.[a-z]{2,3}$/;
    
    // 연락처 유효성
    if (chkMobcarr.value == "none") {
        alert("통신사를 선택해주세요");
        chkMobcarr.focus();
        return false;
    }

    if (
        chkPhone1.value != "010" &&
        chkPhone1.value != "011" &&
        chkPhone1.value != "016" &&
        chkPhone1.value != "019"
    ) {
        alert("잘못된 연락처입니다");
        chkPhone1.value = "";
        chkPhone1.focus();
        return false;
    } else if (chkPhone1.value.length != 3) {
        alert("정확한 연락처를 입력해주세요");
        chkPhone1.focus();
        return false;
    } else if (chkPhone2.value.length != 4) {
        alert("정확한 연락처를 입력해주세요");
        chkPhone2.focus();
        return false;
    } else if (chkPhone3.value.length != 4) {
        alert("정확한 연락처를 입력해주세요");
        chkPhone3.focus();
        return false;
    }

    //이메일유효성
    if (chkEmail1.value == "") {
        alert("이메일을 확인해주세요");
        chkEmail1.focus();
        return false;
    } else if (!chk1.test(chkEmail1.value)) {
        alert("잘못된 이메일 형식입니다");
        email.focus();
        return false;
    }

    if (chkEmail2.value == "") {
        alert("이메일 도메인을 확인해주세요");
        chkEmail2.focus();
        return false;
    } else if (!chk2.test(chkEmail2.value)) {
        alert("잘못된 도메인 형식입니다");
        domain.focus();
        return false;
    }

	// new FormData(document.폼name) => 폼 태그 안에 입력한 파일, 값들이 담김
	const edit_form_data = new FormData(document.edit_form);
	
	fetch("https://gieok.icu/member/inform_edit", {
		method: "POST",
		body: edit_form_data,
	})
	.then(response => response.json())
	.then(function(response) {
		if(response!=undefined) {
			alert("변경 성공");
			profile.setAttribute("src", `/resources/upload/profile/${response.user_profile}`);
		} else {
			alert("변경 실패");
		}
	})
	
    return true;
}

const member_edit_button = document.querySelector('.member_edit_button>input[type="button"]:nth-of-type(1)');
member_edit_button.addEventListener('click', () => {
	edit_button_check();
})


const user_profile_box = document.querySelector('.member_profile_img>input[type="file"]');
let user_label = document.querySelector('.user_profile+label');
let user_image = document.querySelector('.user_profile');


