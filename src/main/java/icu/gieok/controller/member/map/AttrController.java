package icu.gieok.controller.member.map;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.AttrService;
import icu.gieok.service.MapService;
import icu.gieok.utils.CheckMember;
import icu.gieok.vo.AttrLikeVO;
import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.FacVO;
@RequestMapping("/member")
@Controller
public class AttrController {
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@Autowired
	private MapService mapService;
	
	@Autowired
	private AttrService attrService;
	
	private ModelAndView checkUser;
	
	// 회원 여부 확인 
	public ModelAndView checkMember(HttpSession session) {
		
		CheckMember checkMember = new CheckMember(session);
		
		return checkMember.getcheckMember();
		
	}
	
	// 명소 페이지
	@GetMapping("/attr")
	public ModelAndView showAttr(@RequestParam int attr_code, HttpSession session,
								String latitude, String longitude) {
		
		ModelAndView mv = new ModelAndView();
		
		AttrVO attr = mapService.getAttr(attr_code);
		
		// attr_info 엔터키 적용
		String content = attr.getAttr_info().replace("\n", "<br>");
		attr.setAttr_info(content);
		
		String province_id = mapService.getProvinceId(attr.getCity_code());
		
		// 식당 목록 (Type="2")
		List<FacVO> restaurantList = attrService.getRestaurantList(attr_code);
		
		// 평균 평점
		String check_rate = attrService.avgReviewRate(attr_code);
		double avg_rate = 0.0;
		if(check_rate != null) {
			avg_rate = Double.parseDouble(check_rate);
		}
		DecimalFormat df = new DecimalFormat("0.00");
		String rev_rate = df.format(avg_rate);
		
		// 좋아요 여부
		if(session.getAttribute("code") != null) {
			int user_code = (int)session.getAttribute("code");
			Map<String, Integer> map = new HashMap<>();
			map.put("user_code", user_code);
			map.put("attr_code", attr_code);
			AttrLikeVO attr_like = attrService.getAttrLike(map);
			if(attr_like!=null) {
				mv.addObject("attr_like", attr_like.getAttr_like());
			}
		}
		
		mv.addObject("attr", attr);
		mv.addObject("latitude", latitude);
		mv.addObject("longitude", longitude);
		mv.addObject("rev_rate", rev_rate);
		mv.addObject("restaurantList", restaurantList);
		mv.addObject("province", province_id);
		mv.setViewName("/member/attr");
		
		return mv;
	}
	
	
	// 식당&카페
	@ResponseBody
	@PostMapping("/attr_fac")
	public List<FacVO> selectAttrOption(@RequestBody Map<String, String> map) {
		String option = map.get("option");
		int attr_code = Integer.parseInt(map.get("attr_code"));
		
		List<FacVO> list = new ArrayList<>();
		
		if(option.equals("restaurant")) {
			list = attrService.getRestaurantList(attr_code);
		}else if(option.equals("cafe")) {
			list = attrService.getCafeList(attr_code);
		}
		
		return list;
	}
	
	// 리뷰 목록
	@ResponseBody
	@PostMapping("/attr_review")
	public List<AttrReviewVO> attrReviewList(@RequestBody Map<String, String> map) {
		int attr_code = Integer.parseInt(map.get("attr_code"));
		List<AttrReviewVO> list = attrService.getReviewList(attr_code);
	
		return list;
	}
	
	// 리뷰 등록
	@ResponseBody
	@PostMapping("/attr_review_write")
	public List<AttrReviewVO> attrReviewList(@RequestParam("image") MultipartFile file, AttrReviewVO review,
								 HttpSession session) throws IllegalStateException, IOException {
		
    	
    	String id = (String)session.getAttribute("id");
    	int user_code = (int)session.getAttribute("code");
    	
    	review.setRev_writer(id);
    	review.setUser_code(user_code);
    	review.setRev_img("");
    	
    	if(file.getSize()>0) {
    		
    		String root = uploadPath + "review" + "/" + review.getAttr_code();
    		
    		// 이미지 등록 경로
    		File dir = new File(root);
    		if(!(dir.exists())) {
    			dir.mkdir();
    		}
    	
    		Random rand = new Random();
    		int num = rand.nextInt(1000000000);
    		
    		int index = file.getOriginalFilename().lastIndexOf(".");
    		String ext = file.getOriginalFilename().substring(index);

    		String reFileName = id+"-"+user_code+"-"+num+ext;
    		
    		File saveFile = new File(dir, reFileName);
    		file.transferTo(saveFile);

    		review.setRev_img(reFileName);
    		
    	}
    	
    	int res = attrService.insertReview(review);
    	
    	List<AttrReviewVO> list = new ArrayList<>();
    	
    	if(res==1) {
    		list = attrService.getReviewList(review.getAttr_code());
    	}
    	
		return list;
	}
	
	// 리뷰 신고
	@ResponseBody
	@PostMapping("/attr_review_report")
	public String attrReviewReport(@RequestBody Map<String, String> map, HttpSession session) {
		
		
    	int rev_code = Integer.parseInt(map.get("rev_code"));
    	int user_code = (int)session.getAttribute("code");
    	String report_type = map.get("report_type");
    	int rev_writer = attrService.getAttrRevieWriter(rev_code);
    	
    	Map<String, Object> codes = new HashMap<>();
    	codes.put("rev_code", rev_code);
    	codes.put("user_code", user_code);
    	
    	AttrReviewReportVO reportVO = attrService.getAttrReviewReport(codes);
    	
    	String msg = "";
    	int res = 0;
    	
    	if(reportVO==null) {
    		codes.put("report_type", report_type);
    		codes.put("rev_writer", rev_writer);
    		res = attrService.insertAttrReviewReport(codes);

    		if(res==1) {
    			msg = "success";
    		}else {
    			msg = "error";
    		}
    	}else {
    		if(reportVO.getRev_report().equals("N")) {
    			res = attrService.updateAttrReviewReport(codes);
    			if(res==1) {
    				msg = "success";
    			}else {
    				msg = "error";
    			}
    		}else {
    			msg = "fail";
    		}
    	}
    	
		return msg;
	}
	
	// 좋아요
	@ResponseBody
	@PostMapping("/attr_like")
	public String changeAttrLike(@RequestBody Map<String, String> map, HttpSession session) {
		
		
		String msg = "fail";
		
		if(session.getAttribute("code")!=null) {
			
			int attr_code = Integer.parseInt(map.get("attr_code"));
			int user_code = (int)session.getAttribute("code");
			
			Map<String, Integer> codes = new HashMap<>();
			codes.put("attr_code", attr_code);
			codes.put("user_code", user_code);
			
			int res = 0;
	
			AttrLikeVO attrLike = attrService.getAttrLike(codes);
			if(attrLike == null) {
				attrLike = new AttrLikeVO();
				attrLike.setAttr_code(attr_code);
				attrLike.setUser_code(user_code);
				attrLike.setAttr_like("Y");
				res = attrService.insertAttrLike(attrLike);
				msg = "Y";
			}else {
				if(attrLike.getAttr_like().equals("Y")) {
					attrLike.setAttr_like("N");
					msg = "N";
				}else {
					attrLike.setAttr_like("Y");
					msg = "Y";
				}
				res = attrService.updateAttrLike(attrLike);
			}
			
			if(res!=1) {
				msg = "fail";
			}
		}		
		
		return msg;
	}
	
	
	
	
	

}
