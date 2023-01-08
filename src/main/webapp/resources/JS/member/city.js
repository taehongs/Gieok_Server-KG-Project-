// var latitude = document.getElementById("lg").value;
// var logitude = document.getElementById("lt").value;


// 코드 추가 by 진혁

const url = new URL(window.location.href);
const latitude = url.searchParams.get("latitude");
const longitude = url.searchParams.get("longitude");

const pin = document.querySelectorAll(".attr_pin");
const attr_name = document.querySelectorAll(".attr_card_name");

const province = document.querySelector("#province").value;

let map_level = 9;


if(province === "Tseoul") {
	map_level = 7;
}


// 지도 api

var mapContainer = document.getElementById('map'); // 지도를 표시할 div 

// 코드 추가 by 진혁
kakao.maps.load(() => { 
	mapOption = {
		center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
    	level: map_level // 지도의 확대 레벨
		}	
	var map = new kakao.maps.Map(mapContainer, mapOption);
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();
	
	// 지도 핀
	for(let i=0; i<pin.length; i++) {
	
		geocoder.addressSearch(pin[i].value, function(result, status) {
	
			// 정상적으로 검색이 완료되면
			 if (status === kakao.maps.services.Status.OK) {
		
				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
		
				// 결과값으로 받은 위치를 마커로 표시
				var marker = new kakao.maps.Marker({
					map: map,
					position: coords
				});
		
				// 인포윈도우로 장소에 대한 설명 표시
				var infowindow = new kakao.maps.InfoWindow({
					content: `<div style="width:150px;text-align:center;padding:6px 0;">${attr_name[i].innerText}</div>`
				});
				infowindow.open(map, marker);
		
			} 
		}); 
	}


	
	
	
});  



