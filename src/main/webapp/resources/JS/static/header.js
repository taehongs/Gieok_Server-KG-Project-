//===== logo : text change start ===== //
let logoText = document.querySelector(".header_logo>a");

let logoTextArr = ["ㄱㅎㅈ", "ㄱ하자", "기억하자!"];

let index = 0;
let isAdding = true;
let logoTextArrIndex = 0;

function logoChange() {
    setTimeout(() => {
        logoText.innerText = logoTextArr[logoTextArrIndex].slice(0, index);

        if (isAdding) {
            if (index > logoTextArr[logoTextArrIndex].length) {
                isAdding = false;
                logoText.classList.add("change_logo");

                setTimeout(() => {
                    logoText.classList.remove("change_logo");
                    logoChange();
                }, 2000);

                return;
            } else {
                index++;
            }
        } else {
            if (index == 0) {
                isAdding = true;

                logoTextArrIndex = (logoTextArrIndex + 1) % logoTextArr.length;
            } else {
                index--;
            }
        }
        logoChange();
    }, 200);
}

logoChange();
// ===== logo : text change end ===== //

// ===== menu underline : position change start ===== //
let menu_list = document.querySelectorAll(".header_menu li");
let underline = document.querySelector(".menu_underline");
let show = document.querySelector(".show");

function moveUnderline() {
    for (let i = 0; i < menu_list.length; i++) {
        menu_list[i].addEventListener("mouseover", () => {
            underline.classList.add("show");
            underline.style.left = `${150 * i}px`;
        });

        menu_list[i].addEventListener("mouseout", () => {
            underline.classList.remove("show");
        });
    }
}

moveUnderline();
// ===== menu underline : position change end ===== //


// 이미지 & 알림
const profile_image = document.querySelector("#profile_image");
if(profile_image!=null) {
	profile_image.addEventListener("click", () => {
		window.location.href = "/member/inform_edit";
	});
}
const msgCount = document.querySelector("#msgCount");

if(msgCount!=null) {
	msgCount.addEventListener("click", () => {
		window.location.href = "/member/my_message";
	});
}




