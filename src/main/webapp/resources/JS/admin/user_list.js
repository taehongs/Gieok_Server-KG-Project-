const user_list = document.querySelectorAll('.user_list>li');
const get_user_code = document.querySelectorAll('.user_codes');
const get_user_id = document.querySelectorAll('.user_ids');


let page = document.getElementById('page').value;

function view_detail() {
	let user_code;
	let user_id;
	
	for(let i = 0; i < user_list.length; i++) {
		user_list[i].addEventListener('click', () => {

			user_code = get_user_code[i].value;
			user_id = get_user_id[i].value;
			
			fetch("https://gieok.icu/admin/user_detail", {
				method: "POST",
				headers: {
					"Content-Type" : "application/json",
				},
				body: JSON.stringify({
					user_code: user_code,
					user_id: user_id
				}),
			})
			.then(response => response.json())
			.then(response => {
				user_detail(response)
			});
		});
	}
			
	const back_button = document.querySelector('.user_state_button>input[type="button"]:last-of-type');
	back_button.addEventListener('click', () => {
		close_detail();
	});
	
	const delete_button = document.querySelector('.user_delete_button');
	delete_button.addEventListener('click', () => {
		delete_user(user_id, page);
	});
	
}

view_detail();

const detail_box = document.querySelector('.user_detail_box');
function show_detail() {
	detail_box.style.top="calc(50% - 150px)";
	detail_box.style.opacity = "1";
	detail_box.style.zIndex = 10;
}

function close_detail() {
	detail_box.style.top="calc(50% - 250px)";
	detail_box.style.opacity = "0";
	detail_box.style.zIndex = -1;
}

function delete_user(user_id, page) {

	fetch("https://gieok.icu/admin/user_delete", {
		method: "POST",
		headers: {
			"Content-Type" : "application/json",
		},
		body: JSON.stringify({
			page: page,
			user_id: user_id,
		}),
	})
	.then(response => response.json())
	.then(response => {
		if(response.msg == "") {
			alert('삭제 실패');
		} else {
			alert(response.msg);
			window.location.href = response.url;
		}
	});
	
}

function user_detail(response) {
	show_detail();

	const user_profile = document.querySelector('.user_profile');
	const user_id = document.querySelector('.user_id');
	const user_phone = document.querySelector('.user_phone>span');
	const user_email = document.querySelector('.user_email>span');

	user_profile.style.backgroundImage = `url(/resources/upload/profile/${response.user_profile})`
	user_profile.style.backgroundSize = 'cover';
	user_id.innerText=`${response.user_id}`;
	user_phone.innerText=`${response.user_ph1}-${response.user_ph2}-${response.user_ph3}`;
	user_email.innerText=`${response.user_email}@${response.user_domain}`;
}















// 관권빼, 어드민취소







