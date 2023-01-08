package icu.gieok.controller.member.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.BoardWithService;
import icu.gieok.utils.CheckMember;
import icu.gieok.vo.AttrVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.CityVO;
import icu.gieok.vo.ProvinceVO;
import icu.gieok.vo.WithVO;

@Controller
public class WithController {

	@Autowired
	private BoardWithService boardWithService;
	
	private ModelAndView checkUser;
	
	
	// 회원 여부 확인 
	public ModelAndView checkMember(HttpSession session) {
		
		CheckMember checkMember = new CheckMember(session);
		
		return checkMember.getcheckMember();
		
	}
	
	
	// 동행 게시판 작성 뷰
	@GetMapping("/board_with_write")
	public ModelAndView board_with_write(HttpSession session, String category, String keyword, int page) {
		
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			
			return checkUser;
			
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("page", page);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.setViewName("/member/board_with_write");
		
		return mv;
		
	}
	
	
	// 동행 게시판 작성 시 (도 선택)
	@ResponseBody
	@PostMapping("/board_with_province")
	public List<ProvinceVO> listProvince(){
		
		List<ProvinceVO> provinces = boardWithService.getProvinceList();
		
		return provinces;
		
	}
	
	
	// 동행 게시판 작성 시 (시 선택)
	@ResponseBody
	@PostMapping("/board_with_city")
	public List<CityVO> listCity(@RequestBody Map<String, String> place){
		
		String province_id = place.get("province_id");
		List<CityVO> cities = boardWithService.getCityList(province_id);
		
		return cities;
		
	}
	
	
	// 동행 게시판 작성 시 (명소 선택)
	@ResponseBody
	@PostMapping("/board_with_attr")
	public List<AttrVO> listAttr(@RequestBody Map<String, String> place){
		
		String city_id = place.get("city_id");
		List<AttrVO> attres = boardWithService.getAttrList(city_id);
		
		return attres;
		
	}
	
	
	// 동행 게시판 작성 및 저장 폼
	@PostMapping("/board_with_write")
	public ModelAndView board_with_write_ok(String with_province_name, String with_city_name, String with_attr_name,
			BoardVO bw, HttpServletRequest request, HttpSession session,
			String category, String keyword, int page) {
		
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			
			return checkUser;
			
		}
		
		String id = (String)session.getAttribute("id");
		int code = (int)session.getAttribute("code");
		bw.setBoard_writer(id);
		bw.setUser_code(code);
		
//		System.out.println("=================");
//		System.out.println((String)session.getAttribute("id"));
//		System.out.println("=================");
//		System.out.println((int)session.getAttribute("code"));
//		System.out.println("=================");
//		System.out.println(bw.getBoard_startDay());
//		System.out.println("=================");
		
		String with_board_location = with_province_name + " " + with_city_name + " " + with_attr_name;
		
		if (with_city_name == null) {
			
			with_board_location = with_province_name;
			
		}
		
		else if (with_attr_name == null || with_attr_name.equals("명소 선택")) {

			with_board_location = with_province_name + " " + with_city_name;
			
		}
		
		bw.setBoard_location(with_board_location.trim());
		
//		System.out.println("===================");
//		System.out.println(with_board_location.trim());
//		System.out.println("===================");
//		System.out.println(bw.getBoard_startDay());
//		System.out.println("===================");
//		System.out.println(bw.getBoard_endDay());
//		System.out.println("===================");
		
		int res = boardWithService.insertWith(bw);
		
		String msg = "";
		
		if (res == 1) {
			
			msg = "동행 등록이 완료되었습니다! 😊";
			
		}
		else {
			
			msg = "에러발생! 시스템 관리자에게 문의하세요. 😭";
			
		}
		
		
		String url = "/board_with_list?page=" + page + "&category=" + category + "&keyword=" + keyword;
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg", msg);
		mv.addObject("url", url);
		mv.setViewName("message");
		
		return mv;
		
	}
	
	
	// 동행 게시판 목록 뷰
	@GetMapping("/board_with_list")
	public ModelAndView board_with_list(HttpServletRequest request, HttpSession session,
			String category, String keyword) {
		
		// Pagination
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		// 목록 개수 - Pagination
		int startRow = (page - 1) * 3 + 1;  // 현재 페이지 첫번째 게시물 값
		int endRow = page * 3;  // 현재 페이지 마지막 게시물 값
		
		// 검색 - Pagination
		if (category == null) {
			category = "";
		}
		
		if (keyword == null) {
			keyword = "";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("category", category);
		map.put("keyword", keyword);
		
		// Total - Pagination
		int totalCount = boardWithService.countWith(map); // 테이블 내 해당 게시물 전체 개수
		int totalPage = (totalCount / 3);  // 전체 페이지 수
		if ((totalCount % 3) != 0) {
			totalPage++;  // 나머지 게시물을 위해서 페이지++
		}
		
		// Num - Pagination(한 싸이클의 페이지 개수)
		int startPage = 1;
		int endPage = 1;
		
		if (page / 10 < 1 || page == 10) {  // 1 ~ 10까지는 → endPage = 10
			
			endPage = 10;
			
			if (totalPage < 10) {  // totalPage가 10보다 작으면
				endPage = totalPage;
			}
			
		}
		else {
			startPage = ((page / 10) * 10) + 1;  // startPage = 11..21..31..
			endPage = startPage + 9;  // endPage = 10..20..30..
			
			if (endPage >= totalPage) {  // 만약 endPage가 totalPage보다 크거나 같으면
				endPage = totalPage;	// endPage가 totalPage가 된다.
			}
			
		}
		
		// 게시물 목록
		List<BoardVO> with_li_list = boardWithService.getWithList(map);
		
		String user_id = (String)session.getAttribute("id");
		
		Map<String, Object> withMap = new HashMap<>();
		
		withMap.put("with_user_id", user_id);
		
		if (with_li_list.size() > 0 && user_id!=null) {
			for (BoardVO with : with_li_list) {
				with.setBoard_startDay(with.getBoard_startDay().substring(0, 10));
				with.setBoard_endDay(with.getBoard_endDay().substring(0, 10));
				
				withMap.put("board_no", with.getBoard_no());
				WithVO w = boardWithService.getwithAccept(withMap);
				
				if(w!=null) {
					
					String with_accept = "";
					
					switch(w.getWith_accept()) {
					case "1":
						with_accept = "대기중";
						break;
					case "2":
						with_accept = "수락됨";
						break;
					case "3":
						with_accept = "거절됨";
						break;
					}
					
					with.setWith_accept(with_accept);
				}
				
			}
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalPage", totalPage);
		mv.addObject("totalCount", totalCount);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.addObject("with_li_list", with_li_list);
		
		mv.setViewName("/member/board_with_list");
		
		return mv;
		
	} // board_with_list()
	
	
	// 신청하기
	@ResponseBody
	@PostMapping("/board_with_sinchung")
	public String board_with_sinchung(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
		

		checkUser = checkMember(session);
		if(checkUser != null) {
			
			return "noSession";
		}
		
		
		// 작성자
		int board_no = Integer.parseInt(map.get("board_no"));
		String board_writer = map.get("board_writer");
		
		
		// 신청자
		String with_user_id = (String)session.getAttribute("id");
		int with_user_code = (int)session.getAttribute("code");
		String with_user_info = map.get("with_user_info");
		
		
		WithVO wt = new WithVO();
		
		wt.setBoard_no(board_no);
		wt.setBoard_writer(board_writer);
		wt.setWith_user_id(with_user_id);
		wt.setWith_user_code(with_user_code);
		wt.setWith_user_info(with_user_info);
		
		
		String check = "";
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("board_no", board_no);
		map2.put("with_user_code", with_user_code);
		
		WithVO wtCheck = boardWithService.selectWith(map2);
		
		if (wtCheck != null) {
			check = "false";
		} else {
			int res = boardWithService.insert_WT(wt);
			
			if (res != 1) {
				check = "fail";
			}
		}
		
		return check;
		
	} // board_with_sinchung
	
	
	
	// 내가 신청한 동행 목록
	@GetMapping("/board_with_sindong")
	public ModelAndView board_with_sindong(HttpServletRequest request, HttpSession session,
			String category, String keyword) {
		
		
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			
			return checkUser;
			
		}
		
		
		// 작성자 (로그인한 user)
		String loging_user = (String)session.getAttribute("id");
		
		// Pagination
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		// 목록 개수 - Pagination
		int startRow = (page - 1) * 3 + 1;  // 현재 페이지 첫번째 게시물 값
		int endRow = page * 3;  // 현재 페이지 마지막 게시물 값
		
		// 검색 - Pagination
		if (category == null) {
			category = "";
		}
		
		if (keyword == null) {
			keyword = "";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("loging_user", loging_user);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("category", category);
		map.put("keyword", keyword);
		
		// Total - Pagination
		int totalCount = boardWithService.SinCountWith(map); // 테이블 내 해당 게시물 전체 개수
		int totalPage = (totalCount / 3);  // 전체 페이지 수
		if ((totalCount % 3) != 0) {
			totalPage++;  // 나머지 게시물을 위해서 페이지++
		}
		
		// Num - Pagination(한 싸이클의 페이지 개수)
		int startPage = 1;
		int endPage = 1;
		
		if (page / 10 < 1 || page == 10) {  // 1 ~ 10까지는 → endPage = 10
			
			endPage = 10;
			
			if (totalPage < 10) {  // totalPage가 10보다 작으면
				endPage = totalPage;
			}
			
		}
		else {
			startPage = ((page / 10) * 10) + 1;  // startPage = 11..21..31..
			endPage = startPage + 9;  // endPage = 10..20..30..
			
			if (endPage >= totalPage) {  // 만약 endPage가 totalPage보다 크거나 같으면
				endPage = totalPage;	// endPage가 totalPage가 된다.
			}
			
		}
		
		// 내가 신청한 동행 게시물 목록
		List<BoardVO> with_li_list = boardWithService.getWithSinDong(map);
		
		
		if (with_li_list.size() > 0) {
			for (BoardVO with : with_li_list) {
				with.setBoard_startDay(with.getBoard_startDay().substring(0, 10));
				with.setBoard_endDay(with.getBoard_endDay().substring(0, 10));
				
					switch(with.getWith_accept()) {
					case "1":
						with.setWith_accept("대기중");
						break;
					case "2":
						with.setWith_accept("수락됨");
						break;
					case "3":
						with.setWith_accept("거절됨");
						break;
					}
					
				}
				
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalPage", totalPage);
		mv.addObject("totalCount", totalCount);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.addObject("with_li_list", with_li_list);
		
		mv.setViewName("/member/board_with_sindong");
		
		return mv;
	
	} // board_with_sindong()
	
	
	// 내가 신청한 동행 취소
	@ResponseBody
	@PostMapping("/board_with_sindel")
	public String board_with_sindelok(@RequestBody Map<String, String> map, HttpSession session,
			HttpServletRequest request) {
		
		checkUser = checkMember(session);
		
		if (checkUser != null) {
			
			return "noSession";
			
		}
		
		
		int board_no = Integer.parseInt(map.get("board_no"));
		String loging_user = (String)session.getAttribute("id");
		
		
		WithVO wt = new WithVO();
		
		wt.setBoard_no(board_no);
		wt.setWith_user_id(loging_user);
		
		
		String sindel_Check = "";
		
		int res = boardWithService.sinCancel(wt);
		
		
		if (res != 1) {
			
			sindel_Check = "fail";
			
		}	
		
		
		return sindel_Check;
		
	} // board_with_sindelok()
	
	
	
	// 동행 신고
	@ResponseBody
	@PostMapping("/board_with_report")
	public Map<String, String> board_with_report(HttpSession session, @RequestBody Map<String, String> map) {
		
		Map<String, String> result = new HashMap<>();
		
		checkUser = checkMember(session);
		if(checkUser!=null) {
			result.put("msg", "세션이 만료되었습니다!");
			result.put("url", "/login");
			
			return result;
		}
		
		int user_code = (int)session.getAttribute("code");
		int bad_member = Integer.parseInt(map.get("report_writer"));
		int board_no = Integer.parseInt(map.get("report_no"));
		String report_type = map.get("report_type");
		
		Map<String, Integer> code = new HashMap<>();
		code.put("user_code", user_code);
		code.put("board_no", board_no);
		
		BoardLikeReportVO report = boardWithService.getReportBoardWith(code);
		
		String msg = "이미 신고한 동행입니다!";
		
		if(report==null) {
			report = new BoardLikeReportVO();
			report.setUser_code(user_code);
			report.setBad_member(bad_member);
			report.setBoard_no(board_no);
			report.setReport_type(report_type);

			int res = boardWithService.insertReportBoardWith(report);
		
			if(res==1) {
				msg = "신고가 접수되었습니다! :)";
			}else {
				msg = "시스템 오류! 관리자에게 문의하세요 :(";
			}
		}
	
		result.put("msg", msg);
		
		return result;
	}
	
	
	
	
	
	
}


