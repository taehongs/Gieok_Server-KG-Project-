package icu.gieok.controller.member.map;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.MapService;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.CityVO;

@RequestMapping(value="/member")
@Controller
public class MapController {

	@Autowired
	private MapService mapService;
	
	@GetMapping("/map")
	public String showMap() {
		
		return "/member/map";
	}
	
	@ResponseBody
	@PostMapping("/map")
	public List<CityVO> listCity(@RequestBody Map<String, String> map){
		
		String province_id = map.get("province_id");
		List<CityVO> cities = mapService.getCityList(province_id);
		
		return cities;
	}
	
	@GetMapping("/city")
	public ModelAndView showCity(HttpServletRequest request, @RequestParam String city_name,
									String latitude, String longitude) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		List<AttrVO> list = mapService.showAttrList(city_name);
		
		ModelAndView mv = new ModelAndView();

		if(list.size()>0) {
			String province_id = mapService.getProvinceId(list.get(0).getCity_code());
			String province_name = mapService.getProvinceName(province_id);
			
			mv.addObject("attr_list", list);
			mv.addObject("province", province_id);
			mv.addObject("province_name", province_name);
			mv.addObject("latitude", latitude);
			mv.addObject("longitude", longitude);
			mv.setViewName("/member/city");
		}else {
			mv.addObject("msg", "아직 등록된 명소가 없어요...");
			mv.addObject("url", "/member/map");
			mv.setViewName("message");
		}
		
		return mv;
	}
	

	
}
