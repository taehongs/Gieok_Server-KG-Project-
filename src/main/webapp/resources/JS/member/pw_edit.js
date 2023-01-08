let member_pedit_pw0 = document.querySelector(".member_pedit_p1 > input");
let member_pedit_pw1 = document.querySelector(".member_pedit_p2 > input");
let member_pedit_pw2 = document.querySelector(".member_pedit_p3 > input");

let pw1_count = 0;
let pw2_count = 0;

// 비밀번호 유효성
function pw1_check() {
    let chkLower = document.querySelector(".member_pedit_p2>span:nth-of-type(1)");
    let chkUpper = document.querySelector(".member_pedit_p2>span:nth-of-type(2)");
    let chkNumber = document.querySelector(".member_pedit_p2>span:nth-of-type(3)");
    let chkCharacter = document.querySelector(".member_pedit_p2>span:nth-of-type(4)");
    let chkLength = document.querySelector(".member_pedit_p2>span:nth-of-type(5)");

    let pw1 = member_pedit_pw1.value;

    let chk1 = /(?=.*?[a-z])/;
    let chk2 = /(?=.*?[A-Z])/;
    let chk3 = /(?=.*?[0-9])/;
    let chk4 = /(?=.*?[!@#$%^*+=])/;
    let chk5 = /^.{8,18}$/;
    /**
     * chk1:
     * a ~ z 가 포함되어있는지 확인
     * chk2:
     * A ~ Z 가 포함되어있는지 확인
     * chk3:
     * 0 ~ 9 가 포함되어있는지 확인
     * chk4:
     * !, @, #, $, %, ^, *, +, = 중 하나가 포함되어있는지 확인
     * chk5:
     * 8자 ~ 18자 사이인지 확인
     */

    // 비밀번호 유효성
    if (!chk1.test(pw1)) {
        // chk1 통과 X
        chkLower.style.color = "var(--color-red)";
        chkLower.style.stroke = "var(--color-red)";
    } else {
        // chk1 통과 O
        chkLower.style.color = "var(--color-green)";
        chkLower.style.stroke = "var(--color-green)";
    }

    if (!chk2.test(pw1)) {
        chkUpper.style.color = "var(--color-red)";
        chkUpper.style.stroke = "var(--color-red)";
    } else {
        chkUpper.style.color = "var(--color-green)";
        chkUpper.style.stroke = "var(--color-green)";
    }

    if (!chk3.test(pw1)) {
        chkNumber.style.color = "var(--color-red)";
        chkNumber.style.stroke = "var(--color-red)";
    } else {
        chkNumber.style.color = "var(--color-green)";
        chkNumber.style.stroke = "var(--color-green)";
    }

    if (!chk4.test(pw1)) {
        chkCharacter.style.color = "var(--color-red)";
        chkCharacter.style.stroke = "var(--color-red)";
    } else {
        chkCharacter.style.color = "var(--color-green)";
        chkCharacter.style.stroke = "var(--color-green)";
    }

    if (!chk5.test(pw1)) {
        chkLength.style.color = "var(--color-red)";
        chkLength.style.stroke = "var(--color-red)";
    } else {
        chkLength.style.color = "var(--color-green)";
        chkLength.style.stroke = "var(--color-green)";
    }

    if (pw1 == "") {
        // pw1의 input 값이 비어있을 경우 초기화
        chkLower.style.color = "var(--color-darkgray)";
        chkLower.style.stroke = "var(--color-darkgray)";
        chkUpper.style.color = "var(--color-darkgray)";
        chkUpper.style.stroke = "var(--color-darkgray)";
        chkNumber.style.color = "var(--color-darkgray)";
        chkNumber.style.stroke = "var(--color-darkgray)";
        chkCharacter.style.color = "var(--color-darkgray)";
        chkCharacter.style.stroke = "var(--color-darkgray)";
        chkLength.style.color = "var(--color-darkgray)";
        chkLength.style.stroke = "var(--color-darkgray)";
    }
}

function pw2_check() {
    let chkPw2 = document.querySelector(".member_pedit_p3>span");

    let pw1 = member_pedit_pw1.value;
    let pw2 = member_pedit_pw2.value;

    // 비밀번호 확인 유효성
    if (pw1 == pw2) {
        chkPw2.style.color = "var(--color-green)";
        chkPw2.style.stroke = "var(--color-green)";
    } else {
        chkPw2.style.color = "var(--color-red)";
        chkPw2.style.stroke = "var(--color-red)";
    }

    if (pw2 == "") {
        // pw2의 input 값이 비어있을 경우 초기화
        chkPw2.style.color = "var(--color-darkgray)";
        chkPw2.style.stroke = "var(--color-darkgray)";
    }
}

member_pedit_pw1.addEventListener("keyup", () => {
    pw1_check();
});
member_pedit_pw2.addEventListener("keyup", () => {
    pw2_check();
});
/**
 * member_pw1의 input(위),
 * member_pw2의 input(아래) 내에서 키보드가 눌렸다가 떼어질 때
 * 실행되는 이벤트 추가
 * idCheck()랑 같은 방식으로 실행
 *
 * 2번째 함수 pw2Check()는 첫번째 비밀번호 값이 저장된 pw1 변수의 값과
 * 두번째 비밀번호 확인 값이 저장된 pw2 변수의 값이 같은지
 * 확인하는 함수
 */

//기존비밀번호와 새로운 비밀번호 일치하지않아야함
function pw0_check() {
    let chkPw1 = document.querySelector(".member_pedit_p2>span");

    let pw0 = member_pedit_pw0.value;
    let pw1 = member_pedit_pw1.value;
    let pw2 = member_pedit_pw2.value;

    if (pw0 == "") {
        // pw0의 input 값이 비어있을 경우 기존 비밀번호를 입력하라고 해야함
        alert("기존 비밀번호를 입력해주세요~!");
        member_pedit_pw0.focus();
        return false;
    }
    if (pw1 == "") {
        //pw1의 input값이 비어있을 경우 변경 할 비밀번호를 입력하라고 해야함
        alert("변경 할 비밀번호를 입력해주세요~!");
        member_pedit_pw1.focus();
        return false;
    }
    if (pw2 == "") {
        //pw1의 input값이 비어있을 경우 변경 할 비밀번호를 입력하라고 해야함
        alert("비밀번호 확인 입력란을 입력해주세요~!!");
        member_pedit_pw2.focus();
        return false;
    }
    
    // 비밀번호 유효성 확인
    let pw_check1 = document.querySelectorAll('.member_pedit_p2>span');
    let pw_check2 = document.querySelector('.member_pedit_p3>span');
    
    let check_count = 0;
    for(let i = 0; i < pw_check1.length; i++) {
    	if(pw_check1[i].style.color == "var(--color-green)") {
    		check_count++;
    	}
    }
    let check_count2 = 0;
    if(pw_check2.style.color == "var(--color-green)") {
    	check_count2++;
    }
    if(check_count + check_count2 != 6) {
    	alert('비밀번호를 확인해주세요');
    	return false;
    }

    return true;
}
