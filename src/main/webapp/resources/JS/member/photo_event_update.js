// 지역 선택
const post_select = document.getElementById("province");
// 사진 업로드
const post_photo = document.getElementById("post_photo");
// 사진 미리보기
const post_photo_preview = document.querySelector(".post_photo_preview");
// 사진 이름
const post_photo_title = document.querySelector(".post_photo_title");
// 게시물 제목
const post_title = document.querySelector(".post_title");
// 게시물 내용
const post_contents = document.getElementById("post_contents");
// 등록 버튼
const submit_button = document.querySelector(".board_post_button>input:first-of-type");

// 취소 버튼
const cancel_button = document.querySelector(".board_post_button>input:last-of-type");

function set_preview(event) {
    let reader = new FileReader();

    reader.onload = function (event) {
        let image = `url(${event.target.result})`;
        post_photo_preview.style.backgroundImage = image;
        post_photo_preview.style.backgroundSize = "cover";
        let photo_name = post_photo.value.split("\\");
        post_photo_title.value = photo_name[photo_name.length - 1];
    };
    reader.readAsDataURL(event.target.files[0]);
}

// 버튼 폼 전송 체크 이벤트
submit_button.addEventListener("click", () => {
    event_upload_check();
});

function event_upload_check() {
    // form 데이터 저장
    let photo_event_form = new FormData(document.photo_event_form);

    if (post_select.value == "") {
        alert("지역을 선택해주세요");
        post_select.focus();
        return false;
    }
    if (post_title.value == "") {
        alert("제목을 입력해주세요");
        post_title.focus();
        return false;
    }
    if (post_contents.value == "") {
        alert("내용을 입력해주세요");
        post_contents.focus();
        return false;
    }

    fetch("https://gieok.icu/photo_event_update", {
        method: "POST",
        body: photo_event_form,
    })
        .then((response) => response.json())
        .then(function (response) {
            if (response.msg!="") {
                alert(response.msg);
                location.href = response.url;
            } else {
                alert("시스템 오류");
            }
        });

    	return true;
}

cancel_button.addEventListener('click', () => {
	location.href="/photo_event_list";
});