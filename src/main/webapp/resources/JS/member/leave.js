let pw_input = document.getElementById('user_pw');

function pw_check() {
    if (pw_input.value == "") {
        alert("비밀번호를 입력해주세요");
        return false;
    }
    
    return true;
}
