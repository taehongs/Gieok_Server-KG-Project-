	package icu.gieok.controller.admin.map;
	
	import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.MapService;
import icu.gieok.utils.CheckAdmin;
import icu.gieok.vo.AttrVO;
	
	@Controller
	@RequestMapping("/admin/*")
	public class AdminAttrController {
		
		@Resource(name="uploadPath")
		private String uploadPath;
		
		@Autowired
		private MapService mapService;
		
		private ModelAndView checkUser;
	
		// 관리자 여부 확인 
		public ModelAndView checkAdmin(HttpSession session) {
			
			CheckAdmin checkAdmin = new CheckAdmin(session);
			
			return checkAdmin.getCheckAdmin();
			
		}
		
		
		// 명소 목록
		@GetMapping("/attr_list")
		public ModelAndView attrList(HttpSession session, HttpServletRequest request,
									String sort, String category, String keyword) {
			
			checkUser = checkAdmin(session);
			
			if(checkUser != null) {
				return checkUser;
			}
			
			int page = 1;
			if(request.getParameter("page")!=null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			int startRow = (page-1)*10+1;
			int endRow = page*10;
			
			// 검색 및 정렬
			if(sort==null) {
				sort = "";
			}
			
			if(category==null) {
				category="";
			}
			
			if(keyword==null) {
				keyword = "";
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("startRow", startRow);
			map.put("endRow", endRow);
			map.put("category", category);
			map.put("keyword", keyword);
			map.put("sortBy", sort);
			
			
			
			int totalCount = mapService.countAttr(map);
			
			int totalPage = (totalCount/10);
			if((totalCount%10)!=0) {
				totalPage++;
			}
			
			int startPage = 0;
			int endPage = 0;
			
			
			if(page/10<1 || page==10) {
				startPage = 1;
				endPage = 10;
				if(totalPage<10) {
					endPage = totalPage;
				}
			}else {
				
				startPage = ((page/10)*10)+1;
				endPage = startPage+9;
				
				if(endPage>=totalPage) {
					endPage = totalPage;
				}
				
			}
			
			
			List<AttrVO> list = mapService.getAttrList(map);
			if(list.size()>0) {
				for(AttrVO attr : list) {
					attr.setAttr_regDate(attr.getAttr_regDate().substring(0, 10));
				}
				
			}
			
			ModelAndView mv = new ModelAndView();
			
			mv.addObject("attr_list", list);
			mv.addObject("page", page);
			mv.addObject("startPage", startPage);
			mv.addObject("endPage", endPage);
			mv.addObject("totalPage", totalPage);
			mv.addObject("totalCount", totalCount);
			mv.addObject("sort", sort);
			mv.addObject("category", category);
			mv.addObject("keyword", keyword);
			mv.setViewName("/admin/attr_list");
			
			return mv;
		}
		
		// 명소 등록 FRONT
		@GetMapping("/attr_regist")
		public ModelAndView attrRegist(HttpSession session) {
			
			checkUser = checkAdmin(session);
	
			if(checkUser != null) {
				return checkUser;
			}
	
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/admin/attr_regist");
			
			return mv;
		}
		
		// 명소 등록 BACK
		@PostMapping("/attr_regist")
		public ModelAndView attrRegistOK(MultipartHttpServletRequest files, @RequestParam("photo") MultipartFile photo, 
				HttpSession session, String province_id, AttrVO attr) {
			
			checkUser = checkAdmin(session);
	
			if(checkUser != null) {
				return checkUser;
			}
				
			ModelAndView mv = new ModelAndView();
	
			// 로그인한 관리자의 코드 번호 
			attr.setUser_code((int)(session.getAttribute("code")));
			// 링크가 없으면 none으로 
			if(attr.getAttr_link().equals("")) {
				attr.setAttr_link("등록된 링크가 없습니다.");
			}
			
	//		// 도 폴더
	//		String province_name = mapService.getProvinceName(province_id);
			
			// 시 이름
			String city_name = mapService.getCityName(attr.getCity_code());
			attr.setAttr_city(city_name);
			
			// 명소 등록 폴더 경로
			String root = uploadPath + "attr";
			
			// 도 폴더 경로 
			File provDir = new File(root+"/"+province_id);
			if(!(provDir.exists())) {
				provDir.mkdir();
			}
			
			// 최종 명소 등록 경로
			String dir = root + "/" + province_id + "/" + attr.getCity_code();
			
			// DB에 저장할 이미지 경로
			String dbDir = "attr/"+province_id+"/"+attr.getCity_code();
			
			File path = new File(dir);
			
			// 해당 경로가 없으면 폴더를 생성
			if(!(path.exists())) {
				path.mkdir();
			}
			
			// 랜덤함수
			Random rand = new Random();
	
			// 다중 파일 업로드 
			List<MultipartFile> list = files.getFiles("images");
			// photo파일을 추가
			list.add(photo);
			
			// 각각의 파일명을 저장할 배열
			String[] fullFileNames = new String[3];
			
			for(int i=0; i<list.size(); i++) { // 3번 반복
				fullFileNames[i] = list.get(i).getOriginalFilename();
				
				// 확장자 인덱스 
				int index = fullFileNames[i].lastIndexOf(".");
	
				// 파일명 구하기
	//			String fileName = fullFileNames[i].substring(0, index);
				// 확장자 구하기
				String ext = fullFileNames[i].substring(index); // .jpg .png 
				// 난수 발생
				int num = rand.nextInt(1000000000);
				
				// 새 파일명
				Calendar c=Calendar.getInstance();
				int year=c.get(Calendar.YEAR);
				int month=c.get(Calendar.MONTH)+1;
				int date=c.get(Calendar.DATE);
				
				String reFileName = year + "-" + month + "-" + date + "-" + num + ext;
				
				File saveFile = new File(dir, reFileName);
				
				try {
					list.get(i).transferTo(saveFile);
					if(i==0) {
						attr.setAttr_img1(dbDir + "/" + reFileName);
					}else if(i==1) {
						attr.setAttr_img2(dbDir + "/" +reFileName);
					}else if(i==2) {
						attr.setAttr_photo(dbDir + "/" + reFileName);
					}
				}catch (IllegalStateException ie) {
					ie.printStackTrace();
				}catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			
			int res = mapService.insertAttr(attr);
			
			if(res==1) {
				mv.setViewName("redirect:/admin/attr_list");
			}else {
				mv.addObject("msg", "에러발생! 시스템 관리자에게 문의하세요!");
				mv.addObject("url", "/admin/addr_list");
				mv.setViewName("message");
			}
			
			
			return mv;
		}
		
		
		// 명소 상세
		@GetMapping("/attr_detail")
		public ModelAndView attrDetail(int attr_code, int page, String sort, String category, 
											String keyword,  HttpSession session) {
			
			checkUser = checkAdmin(session);
	
			if(checkUser != null) {
				return checkUser;
			}
			
			AttrVO attr = mapService.getAttr(attr_code);
			String province_id = mapService.getProvinceId(attr.getCity_code());
			
			ModelAndView mv = new ModelAndView();
	
			mv.addObject("attr", attr);
			mv.addObject("province", province_id);
			mv.addObject("page", page);
			mv.addObject("sort", sort);
			mv.addObject("category", category);
			mv.addObject("keyword", keyword);
			mv.setViewName("/admin/attr_detail");
			
			return mv;
		}
		
		// 명소 수정
		@PostMapping("/admin/attr_detail")
		public ModelAndView attrUpdate(MultipartHttpServletRequest files, @RequestParam("photo") MultipartFile photo, 
				HttpSession session, String province_id, int city_code, AttrVO newAttr, 
				int page, String sort, String category, String keyword) throws IllegalStateException, IOException {
			
			checkUser = checkAdmin(session);
	
			if(checkUser != null) {
				return null;
			}
			
			AttrVO attr = mapService.getAttr(newAttr.getAttr_code());
			
			if(newAttr.getAttr_link().equals("")) {
				newAttr.setAttr_link("등록된 링크가 없습니다.");
			}
			
			newAttr.setCity_code(city_code);
			newAttr.setAttr_city(mapService.getCityName(city_code));
	
			// 사진 수정을 안할시 기존 파일명 유지
			newAttr.setAttr_img1(attr.getAttr_img1());
			newAttr.setAttr_img2(attr.getAttr_img2());
			newAttr.setAttr_photo(attr.getAttr_photo());
			
			List<MultipartFile> list = files.getFiles("images");
			
			if(list.size()==2) {
				
				int prevIndex = attr.getAttr_img1().lastIndexOf("/");
				String prevDir = attr.getAttr_img1().substring(0, prevIndex);
				String prevImg1 = attr.getAttr_img1().substring(prevIndex+1);
				String prevImg2 = attr.getAttr_img2().substring(prevIndex+1);
				
				// 기존 파일 삭제1
				File delFile = new File(uploadPath + prevDir, prevImg1);
				if(delFile.exists()) {
					delFile.delete();
				}
				
				// 기존 파일 삭제2
				delFile = new File(uploadPath + prevDir, prevImg2);
				if(delFile.exists()) {
					delFile.delete();
				}
				
				
				// 도 폴더가 없으면 생성
				File provDir = new File(uploadPath + "attr/" + province_id);
				if(!(provDir.exists())) {
					provDir.mkdir();
				}
	
				String[] fullFileNames = new String[2];
				String dir = uploadPath + "attr" + "/" + province_id + "/" + city_code;
				
				File path = new File(dir);
				
				// 해당 경로가 없으면 폴더를 생성
				if(!(path.exists())) {
					path.mkdir();
				}
				
				for(int i=0; i<2; i++) {
					fullFileNames[i] = list.get(i).getOriginalFilename();
					int index = fullFileNames[i].lastIndexOf(".");
					String ext = fullFileNames[i].substring(index);
					
					String reFileName = "";
					StringTokenizer st;
					
					if(i==0) {
						st = new StringTokenizer(prevImg1, ".");
						reFileName = st.nextToken() + ext;
						newAttr.setAttr_img1("attr" + "/" + province_id + "/" + city_code + "/" + reFileName);
					}else if(i==1) {
						st = new StringTokenizer(prevImg2, ".");
						reFileName = st.nextToken() + ext;
						newAttr.setAttr_img2("attr" + "/" + province_id + "/" + city_code + "/" + reFileName);
					}
					
					File saveFile = new File(dir, reFileName);
					list.get(i).transferTo(saveFile);
				
					
				}
			}
			
			if(photo.getSize()>0) {
				
				int prevIndex = attr.getAttr_photo().lastIndexOf("/");
				String prevDir = attr.getAttr_photo().substring(0, prevIndex);
				String prevPhoto = attr.getAttr_photo().substring(prevIndex+1);
				
				// 기존 파일 삭제 3
				File delFile = new File(uploadPath + prevDir, prevPhoto);
				if(delFile.exists()) {
					delFile.delete();
				}
				
				String fullFileName = photo.getOriginalFilename();
				int index = fullFileName.lastIndexOf(".");
				String ext = fullFileName.substring(index);
				
				String dir = uploadPath + "attr" + "/" + province_id + "/" + city_code; 
				StringTokenizer st = new StringTokenizer(prevPhoto, ".");
				String reFileName = st.nextToken() + ext;
	
				File saveFile = new File(dir, reFileName);
				photo.transferTo(saveFile);
	
				newAttr.setAttr_photo("attr" + "/" + province_id + "/" + city_code + "/" + reFileName);
			}
			
			int res = mapService.updateAttr(newAttr);
	
			String msg = "";
	
			if(res==1) {
				msg = "명소 수정을 완료했어요 :)";
			}else {
				msg = "명소 수정에 실패했습니다 :(";
			}
			
			String url = "/admin/attr_detail?attr_code="+newAttr.getAttr_code()
						+"&page="+page+"&sort="+sort+"&category="+category+"&keyword="+keyword;
			
			ModelAndView mv = new ModelAndView();
			mv.addObject("msg", msg);
			mv.addObject("url", url);
			mv.setViewName("message");
			
			return mv;
		}
		
		// 명소 삭제 attr_delete
		@PostMapping("/attr_delete")
		public ModelAndView attrDelete(HttpSession session, @RequestParam List<Integer> attr_checkBox, int page,
										String sort, String category, String keyword) {
			
			checkUser = checkAdmin(session);
			
			if(checkUser != null) {
				return null;
			}
			
			List<AttrVO> list = mapService.getDeleteAttrList(attr_checkBox);
			
			for(AttrVO attr : list) {
				int index = attr.getAttr_img1().lastIndexOf("/");
				String dir = attr.getAttr_img1().substring(0, index);
				String img1 = attr.getAttr_img1().substring(index+1);
				String img2 = attr.getAttr_img2().substring(index+1);
				System.out.println(uploadPath + dir);
				// 파일 삭제1
				File delFile = new File(uploadPath + dir, img1);
				if(delFile.exists()) {
					delFile.delete();
				}
				
				// 파일 삭제2
				delFile = new File(uploadPath + dir, img2);
				if(delFile.exists()) {
					delFile.delete();
				}		
				
				int photoIndex = attr.getAttr_photo().lastIndexOf("/");
				String photoDir = attr.getAttr_photo().substring(0, photoIndex);
				String photo = attr.getAttr_photo().substring(photoIndex+1);
				System.out.println(uploadPath + photoDir);
				// 기존 파일 삭제 3
				delFile = new File(uploadPath + photoDir, photo);
				
				if(delFile.exists()) {
					delFile.delete();
				}
				
			}
			
			
			int listSize = attr_checkBox.size();
			int res = mapService.deleteAttr(attr_checkBox);
			
			String msg="";
			
			if(listSize == res) {
				msg = "총 " + res + "개의 데이터가 삭제되었습니다!";
			}else {
				msg = "삭제 실패! 관리자에게 문의하세요!";
			}
			String url = "/admin/attr_list?page="+page+"&sort="+sort+"&category="+category+"&keyword="+keyword;
			
			
			ModelAndView mv = new ModelAndView();
			mv.addObject("msg", msg);
			mv.addObject("url", url);
			mv.setViewName("message");
			
			
			return mv;
		}
		
	
	
	
	
	}
		
		
		
		
		
	
