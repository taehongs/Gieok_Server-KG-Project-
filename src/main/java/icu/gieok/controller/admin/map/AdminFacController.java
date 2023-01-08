package icu.gieok.controller.admin.map;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.FacService;
import icu.gieok.service.MapService;
import icu.gieok.utils.CheckAdmin;
import icu.gieok.vo.FacVO;

@Controller
@RequestMapping("/admin/*")
public class AdminFacController {
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	@Autowired
	private MapService mapService;
	
	@Autowired
	private FacService facService;
	
	private ModelAndView checkUser;
	
	// 관리자 여부 확인 
	public ModelAndView checkAdmin(HttpSession session) {
		
		CheckAdmin checkAdmin = new CheckAdmin(session);
		
		return checkAdmin.getCheckAdmin();
		
	}
	
	// 주변 시설 목록
	@GetMapping("/fac_list")
	public ModelAndView facList(HttpSession session, HttpServletRequest request, int attr_code, String sort, String category, String keyword) {
		
		// 관리자 여부 체크
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		// 주변 시설 목록 title
		String attr_name = mapService.getAttrName(attr_code);
		
		// 페이징
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		// 정렬 / 검색
		int startRow = (page - 1) * 10 + 1; // page > row1
		int endRow = page * 10; // page > row10
		
		if (sort == null) {
			sort = "";
		}
		if (category == null) {
			category = "";
		} 
		if (keyword == null) {
			keyword = "";
		} 
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow); // page > row1
		map.put("endRow", endRow); // page > row10
		map.put("category", category);
		map.put("keyword", keyword);
		map.put("sortBy", sort);
		map.put("attr_code", attr_code);
		
		int totalCount = facService.countFac(map);
		int totalPage = (totalCount / 10); // 전체 페이지
		if ((totalCount % 10) != 0) { 
			totalPage++; // 나머지 row의 페이지
		}
		
		int startPage = 1;
		int endPage = 1;
		
		if (page / 10 < 1 || page == 10) {
			endPage = 10;
			
			if(totalPage < 10) {
				endPage = totalPage;
			}
		}else {
			startPage = ((page / 10) * 10) + 1;
			endPage = startPage + 9;
			
			if(endPage >= totalPage) {
				endPage = totalPage;
			}
		}
		
		// 주변 시설 목록
		List<FacVO> fac_list = facService.getFacList(map);
		// System.out.println(fac_list.size());
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalPage", totalPage);
		mv.addObject("totalCount", totalCount);
		mv.addObject("attr_code", attr_code);
		mv.addObject("attr_name", attr_name);
		mv.addObject("sort", sort);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.addObject("fac_list", fac_list); // 주변 시설 목록
		
		mv.setViewName("/admin/fac_list");
		
		return mv;
	}
	
	
	// 주변 시설 등록 FRONT
	@GetMapping("/fac_regist")
	public ModelAndView facRegist(HttpSession session, int attr_code) {
		
		checkUser = checkAdmin(session);

		if(checkUser != null) {
			return checkUser;
		}

		String attr_name = mapService.getAttrName(attr_code);
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("attr_name", attr_name);
		mv.addObject("attr_code", attr_code);
		mv.setViewName("/admin/fac_regist");
		
		// System.out.println("page = " + page);
		
		return mv;
	}
	
	// 주변 시설 등록 BACK
	@PostMapping("/fac_regist")
	public ModelAndView facRegistOK(@RequestParam("image") MultipartFile image, HttpSession session, int attr_code, FacVO fac) {
		
		checkUser = checkAdmin(session);

		if(checkUser != null) {
			return checkUser;
		}
		
		// 로그인한 관리자의 코드 번호 
		fac.setUser_code((int)(session.getAttribute("code")));
		
		// 시간을 입력하지 않을 경우 
		if(fac.getFac_optime() == null) {
			fac.setFac_optime("none");
		}
		if(fac.getFac_cltime() == null) {
			fac.setFac_cltime("none");
		}
		
		
		// province_id > 도 폴더
		String fprovince_id = facService.getProvinceId(attr_code);

		
		// city_code > 시 폴더
		int fcity_code = facService.getCityCode(attr_code);
		
		// 주변 시설 이미지 저장 폴더 경로
		String root = uploadPath + "fac";
		
		// 도 폴더 경로 
		File provDir = new File(root + "/" + fprovince_id);
		if (!(provDir.exists())) {
			provDir.mkdir();
		}
		
		// 시 폴더 경로
		File cityDir = new File(provDir + "/" + fcity_code);
		if (!(cityDir.exists())) {
			cityDir.mkdir();
		}
		
		// 최종 주변시설 등록 경로
		String dir = root + "/" + fprovince_id + "/" + fcity_code + "/" + attr_code;
		String savedir = "fac" + "/" + fprovince_id + "/" + fcity_code + "/" + attr_code + "/";
		
		// 최종 주변시설 경로가 없으면 폴더를 생성
		File path = new File(dir);
		if(!(path.exists())) {
			path.mkdir();
		}
		
		// 랜덤함수
		Random rand = new Random();

		if (image.getSize() > 0) {
			String fileName = image.getOriginalFilename();
			
			// 확장자 인덱스 
			int index = fileName.lastIndexOf(".");
			// 확장자 구하기
			String ext = fileName.substring(index); // .jpg .png 
			// 난수 발생
			int num = rand.nextInt(1000000000);
			
			// 새 파일명
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			
			String reFileName = year + "-" + month + "-" + date + "-" + num + ext;
			
			File saveFile = new File(dir, reFileName);
			
			try {
				image.transferTo(saveFile);
				fac.setFac_img(savedir + reFileName);
			} catch (IllegalStateException ie) {
				ie.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		int res = facService.insertFac(fac);
		
		String msg = "";
		
		if (res == 1) {
			msg = "주변시설 등록이 완료되었습니다.";
		} else {
			msg = "에러발생! 시스템 관리자에게 문의하세요!";
		}
		
		String url = "/admin/fac_list?attr_code=" + attr_code;
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg", msg);
		mv.addObject("url", url);
		mv.setViewName("message");
		
		return mv;
	}
	
	// 주변 시설 수정 FRONT
	@GetMapping("/fac_detail")
	public ModelAndView facDetail(HttpSession session, int fac_code, int page, String sort, String category, String keyword) {
		
		checkUser = checkAdmin(session);

		if(checkUser != null) {
			return checkUser;
		}
	
		FacVO fac = facService.getFac(fac_code);
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("fac", fac);
		mv.addObject("page", page);
		mv.addObject("sort", sort);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.setViewName("/admin/fac_detail");
		
		return mv;
	}
	
	// 주변 시설 수정 BACK
	@PostMapping("/fac_detail")
	public ModelAndView facUpdate(@RequestParam("image") MultipartFile image, HttpSession session, int fac_code, FacVO upFac, int page, String sort, String category, String keyword) {
		
		checkUser = checkAdmin(session);

		if(checkUser != null) {
			return checkUser;
		}
		
		FacVO fac = facService.getFac(fac_code);
		
		int attr_code = upFac.getAttr_code();
		
		// 시간을 입력하지 않을 경우 
		if(upFac.getFac_optime() == null) {
			upFac.setFac_optime("none");
		}
		if(upFac.getFac_cltime() == null) {
			upFac.setFac_cltime("none");
		}
		
		// 사진을 업데이트 하지 않을 경우
		upFac.setFac_img(fac.getFac_img());
		
		// 주변 시설 이미지 저장 폴더 경로
		String dir = uploadPath;
		
		// 랜덤함수
		Random rand = new Random();
		
		if (image.getSize() > 0) {
			
			// fac.getAttr_img에서 마지막 / 전까지 자르기 
			int imgIndex = fac.getFac_img().lastIndexOf("/");
			// fac.getAttr_img에서 이미지 저장 경로 추출
			String imgdir = fac.getFac_img().substring(0, imgIndex);
			// fac.getAttr_img에서 이미지 이름 추출
			String imgname = fac.getFac_img().substring(imgIndex + 1);
			
			// 기존 파일 삭제
			File delFile = new File(dir + imgdir, imgname);
			if (delFile.exists()) {
				delFile.delete();
			}
			
			// 업로드 파일이름
			String fileName = image.getOriginalFilename();
			// 확장자 인덱스 
			int index = fileName.lastIndexOf(".");
			// 확장자 구하기
			String ext = fileName.substring(index); // .jpg .png 
			// 난수 발생
			int num = rand.nextInt(1000000000);
			
			// 새 파일명
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			
			String reFileName = imgdir + "/" + year + "-" + month + "-" + date + "-" + num + ext;
			
			File saveFile = new File(dir, reFileName);
			
			try {
				image.transferTo(saveFile);
				upFac.setFac_img(reFileName);
			} catch (IllegalStateException ie) {
				ie.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
		
		int res = facService.updateFac(upFac);
		
		String msg = "";
		
		if (res == 1) {
			msg = "주변 시설 수정이 완료되었습니다.";
		} else {
			msg = "에러발생! 시스템 관리자에게 문의하세요!";
		}
		
		String url = "/admin/fac_list?attr_code=" + attr_code + "&page=" + page + "&sort=" + sort + "&category=" + category + "&keyword=" + keyword;
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg", msg);
		mv.addObject("url", url);
		mv.setViewName("message");
		
		return mv;
		
	}
	
	@PostMapping("/fac_delete")
	public ModelAndView facDelete(HttpSession session, @RequestParam List<Integer> fac_checkBox, int page, String sort, String category, String keyword, int attr_code) {
		
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		List<FacVO> list = facService.getDeleteFacList(fac_checkBox);
		
		for (FacVO fac : list) {
			// fac_img에서 마지막 /를 기준으로 파일경로, 파일명 추출
			int imgindex = fac.getFac_img().lastIndexOf("/");
			String imgDir = fac.getFac_img().substring(0, imgindex);
			String fac_img = fac.getFac_img().substring(imgindex + 1);

			// 기존 파일 삭제
			File delFile = new File(uploadPath + imgDir, fac_img);
			
			if (delFile.exists()) {
				delFile.delete();
			}
			
		}
		
		int listSize = fac_checkBox.size();
		int res = facService.deleteFac(fac_checkBox);
		
		String msg = "";
		
		if (listSize == res) {
			msg = "총 " + res + "개의 데이터가 삭제되었습니다!";
		} else {
			msg = "에러발생! 시스템 관리자에게 문의하세요!";
		}
		
		String url = "/admin/fac_list?attr_code=" + attr_code + "&page=" + page + "&sort=" + sort + "&category=" + category + "&keyword=" + keyword;
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg", msg);
		mv.addObject("url", url);
		mv.setViewName("message");
		
		return mv;
		
	}
	
}
	


