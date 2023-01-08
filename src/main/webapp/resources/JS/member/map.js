const outline = document.querySelectorAll(".south-ko");
const block = document.querySelector(".block");
const cityTab = document.querySelector(".korea-map-city-tab");
const map = document.querySelector(".korea-map");

const cityUl = document.querySelector(".korea-map-city-ul");

let textToggle = false;
let selectedCity = "";


function listCity(data) {
	data.forEach((item) => {
		let city = `<li>
						<a href="city?city_name=${item.city_name}&latitude=${item.city_latitude}&longitude=${item.city_longitude}">
							${item.city_name}
						</a>
					</li>`
		cityUl.innerHTML += city;
	})
}

outline.forEach((item) => {
    let cityId = item.id.split("#");
    let cityText = document.querySelector(`#T${cityId}`);

    item.addEventListener("mouseover", () => {
        cityText.style.fontSize = "20px";
        cityText.style.opacity = "1";
        cityText.style.transform = "translateY(50px)";
        cityText.style.color = "#333";
    })
    
    item.addEventListener("mouseout", () => {
        if(!textToggle){
            cityText.style.fontSize = "0";
            cityText.style.opacity = "0";
            cityText.style.transform = "translateY(0)"
        }
    })
    
    item.addEventListener("click", () => {
    
    	fetch("https://gieok.icu/member/map", {
    		method: "POST",
    		headers: {
    			"Content-Type": "application/json",
    		},
    		body: JSON.stringify({
    			province_id: cityText.id,
    			
    		}),
    	})
    	.then(res => res.json())
    	.then(res => {
    		if(res.length==0){
    			cityUl.innerHTML += "<li style='text-align: center; line-height: 400px;'><h2>아직 등록된 도시가 없습니다!</h2></li>";
    		}else {
    			listCity(res)
    		}
    	});
    	
    	cityTab.style.zIndex="100";
    
    
    
        cityTab.style.transform = "translateX(0)";
        cityTab.style.opacity = "1";
        block.style.zIndex = "3";
        selectedCity = cityText;
		map.style.transform = "translateX(0)";		
        textToggle = true;

		item.style.fill = "rgb(231, 255, 205)";
		if(item.hasChildNodes) {
			for(let i=1; i<item.childNodes.length; i+=2) {
				item.childNodes[i].style.fill = "rgb(231, 255, 205)";
			}			
		}
		
		
		block.addEventListener("click", () => {
		    cityTab.style.transform = "translateX(100%)";
		    cityTab.style.opacity = "0";
		    block.style.zIndex = "0";
		    selectedCity.style.fontSize = "0";
		    selectedCity.style.opacity = "0";
		    selectedCity.style.transform = "translateY(0)"
			map.style.transform = "translateX(200px)";
			
			cityUl.innerHTML = "";
		
			textToggle = false;
		
			item.style.fill = "#509797";
			if(item.hasChildNodes) {
				for(let i=1; i<item.childNodes.length; i+=2) {
					item.childNodes[i].style.fill = "#509797";
				}			
			}
			
			item.addEventListener("mouseover", () => {
				item.style.fill = "rgb(231, 255, 205)";
				if(item.hasChildNodes) {
					for(let i=1; i<item.childNodes.length; i+=2) {
						item.childNodes[i].style.fill = "rgb(231, 255, 205)";
					}			
				}			
			});
			
			item.addEventListener("mouseout", () => {
		        if(!textToggle){
		           item.style.fill = "#509797";
					if(item.hasChildNodes) {
						for(let i=1; i<item.childNodes.length; i+=2) {
							item.childNodes[i].style.fill = "#509797";
						}			
					}	
		        }
		    });
	
	
	    	
		});


    })
});


      


