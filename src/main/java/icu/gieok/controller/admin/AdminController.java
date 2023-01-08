package icu.gieok.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.AdminService;
import icu.gieok.service.MapService;
import icu.gieok.service.UserService;
import icu.gieok.utils.CheckAdmin;
import icu.gieok.vo.AttrReviewReportVO;
import icu.gieok.vo.AttrReviewVO;
import icu.gieok.vo.BoardLikeReportVO;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.UserVO;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MapService mapService;

	private ModelAndView checkUser;

	// 관리자 여부 확인 
	public ModelAndView checkAdmin(HttpSession session) {
		
		CheckAdmin checkAdmin = new CheckAdmin(session);
		
		return checkAdmin.getCheckAdmin();
		
	}
	
	/*========================================*/
	
	/* ===== 관리자 목록 ===== */
	@GetMapping("/admin_list")
	private ModelAndView adminList(HttpSession session,
			HttpServletRequest request, String category, String keyword) {
		
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int startRow = (page - 1) * 10 + 1;
		int endRow = page * 10;
		
		if(category == null) {
			category = "";
		}
		
		if(keyword == null) {
			keyword = "";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("category", category);
		map.put("keyword", keyword);
		
		int totalCount = adminService.adminListCount(map);
		int totalPage = totalCount / 10;
		
		if(totalCount % 10 != 0) {
			totalPage++;
		}
		
		int startPage = 0;
		int endPage = 0;
		
		if(page / 10 < 1 || page == 10) {
			startPage = 1;
			endPage = 10;
			
			if(totalPage < 10) {
				endPage = totalPage;
			}
		} else {
			startPage = page / 10 * 10 + 1;
			endPage = startPage + 9;
			
			if(endPage >= totalPage) {
				endPage = totalPage;
			}
		}
		
		List<UserVO> list = adminService.getAdminList(map);
		
		if(list.size() > 0) {
            for(UserVO user : list) {
            	user.setUser_regDate(user.getUser_regDate().substring(0, 10));
            }
        }
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("admin_list", list);
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalCount", totalCount);
		mv.addObject("totalPage", totalPage);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.setViewName("/admin/admin_list");
		
		return mv;
	}
	
	/* ===== 관리자 임명, 삭제할 유저 검색 ===== */
	@ResponseBody
	@PostMapping("/admin_search")
	public Map<String, Object> adminSearch(HttpSession session,
			@RequestBody Map<String, String> id) {
		
		String user_id = id.get("search_id");
		
		String msg = "";
		
		UserVO user = adminService.getAdmin(user_id);
		
		if(user == null) {
			msg = "검색값이 없습니다";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("msg", msg);
		
		return map;
	}
	
	/* ===== 관리자 임명 ===== */
	@ResponseBody
	@PostMapping("/add_admin")
	public Map<String, Object> addAdmin(HttpSession session,
			@RequestBody Map<String, String> id) {
		
		String user_id = id.get("user_id");
		
		String msg = "";
		String url = "/admin/admin_list";
		
		int result = adminService.addAdmin(user_id);
		if(result == 1) {
			msg = "성공적으로 임명되었습니다";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("url", url);
		
		return map;
	}

	/* ===== 관리자 삭제 ===== */
	@ResponseBody
	@PostMapping("/delete_admin")
	public Map<String, Object> deleteAdmin(HttpSession session,
			@RequestBody Map<String, String> id) {
		
		String user_id = id.get("user_id");
		
		String msg = "";
		String url = "/admin/admin_list";
		
		int result = adminService.deleteAdmin(user_id);
		if(result == 1) {
			msg = "성공적으로 해임되었습니다";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("url", url);
		
		return map;
	}
	/* ===== 유저 목록 ===== */
	@GetMapping("/user_list")
	private ModelAndView userList(HttpSession session,
			HttpServletRequest request, String category, String keyword) {
		
		checkUser = checkAdmin(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int startRow = (page - 1) * 10 + 1;
		int endRow = page * 10;
		
		if(category == null) {
			category = "";
		}
		
		if(keyword == null) {
			keyword = "";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("category", category);
		map.put("keyword", keyword);
		
		int totalCount = adminService.userListCount(map);
		int totalPage = totalCount / 10;
		
		if(totalCount % 10 != 0) {
			totalPage++;
		}
		
		int startPage = 0;
		int endPage = 0;
		
		if(page / 10 < 1 || page == 10) {
			startPage = 1;
			endPage = 10;
			
			if(totalPage < 10) {
				endPage = totalPage;
			}
		} else {
			startPage = page / 10 * 10 + 1;
			endPage = startPage + 9;
			
			if(endPage >= totalPage) {
				endPage = totalPage;
			}
		}
		
		List<UserVO> list = adminService.getUserList(map);
		
		if(list.size() > 0) {
            for(UserVO user : list) {
            	user.setUser_regDate(user.getUser_regDate().substring(0, 10));
            }
        }
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("user_list", list);
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalCount", totalCount);
		mv.addObject("totalPage", totalPage);
		mv.addObject("category", category);
		mv.addObject("keyword", keyword);
		mv.setViewName("/admin/user_list");
		
		return mv;
	}
	
	/* ===== 유저 상세 ===== */
	@ResponseBody
	@PostMapping("/user_detail")
	public UserVO userDetail(HttpSession session,
			@RequestBody Map<String, String> id) {
		
		checkUser = checkAdmin(session);
		if(checkUser!=null) {
			return null;
		}
		
		String user_id = id.get("user_id");
		
		UserVO user = userService.userSelect(user_id);
		
		return user;
	}
	
	/* ===== 유저 삭제 ===== */
	@ResponseBody
	@PostMapping("/user_delete")
	public Map<String, Object> userDelete(HttpSession session,
			HttpServletRequest request, @RequestBody Map<String, String> id) {
		
		String page = id.get("page");
		String user_id = id.get("user_id");
		
		String msg = "";
		String url = "/admin/user_list?page=" + page;
		int result = userService.userDelete(user_id);
		if(result == 1) {
			msg = "정상적으로 삭제되었습니다";
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("msg", msg);
		map.put("url", url);
		
		return map;
	}
	
	
	
	/*========================================*/
	
	
	@GetMapping("/review_report_list")
	public ModelAndView reviewReportList(HttpSession session, HttpServletRequest request) {
		
		checkUser = checkAdmin(session);
		if(checkUser!=null) {
			return checkUser;
		}
		
	  	int page = 1;
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		
		int startRow = (page-1)*10+1;
		int endRow = page*10;
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		int totalCount = adminService.countReport("review");
		
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
    	

		List<AttrReviewReportVO> reviewReport = adminService.getReviewReportList(map);
		
		if(reviewReport.size()>0) {
			for(AttrReviewReportVO report : reviewReport) {
				switch(report.getReport_type()) {
					case "1":
						report.setReport_type("부적절한 내용");
						break;
					case "2":
						report.setReport_type("욕설/비방");
						break;
					case "3":
						report.setReport_type("광고/홍보");
						break;
					case "4":
						report.setReport_type("도배");
						break;
				}
				
				String user_id = adminService.getReportUser(report.getUser_code());
				report.setUser_id(user_id);
				
				AttrReviewVO bad_review = adminService.getBadReview(report.getRev_code());
				bad_review.setAttr_name(mapService.getAttrName(bad_review.getAttr_code()));
				report.setBad_review(bad_review);
				
				
			}
		}
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("reviewReport", reviewReport);
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalPage", totalPage);
		mv.addObject("totalCount", totalCount);
		mv.setViewName("/admin/review_report_list");
		
		
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/review_report_detail")
	public AttrReviewVO reviewReportDetail(HttpSession session, @RequestBody Map<String, String> map) {

		checkUser = checkAdmin(session);
		if(checkUser!=null) {
			return new AttrReviewVO();
		}
		
		int rev_code = Integer.parseInt(map.get("rev_code"));
		AttrReviewVO review = adminService.getBadReview(rev_code);

		return review;
	}

	@ResponseBody
	@PostMapping("/review_report_delete")
	public Map<String, String> reviewReportDelete(HttpSession session, @RequestBody Map<String, String> map) {
		
		Map<String, String> result = new HashMap<>();
		String msg = "";
		String url = "";
		
		checkUser = checkAdmin(session);
		if(checkUser!=null) {
			msg = "세션이 만료되었습니다!";
			url = "/login";
			
			result.put("msg", msg);
			result.put("url", url);
			
			return result;
		}
		
		int rev_code = Integer.parseInt(map.get("rev_code"));
		int page = Integer.parseInt(map.get("page"));

		int res = adminService.reviewReportDelete(rev_code);
		
	
		if(res==1) {
			msg = "신고된 리뷰를 삭제했습니다!";
			url = "/admin/review_report_list?page="+page;
		}
		
		result.put("msg", msg);
		result.put("url", url);
		
		return result;
	}
	
	@GetMapping("/board_report_list")
	public ModelAndView boardReportList(HttpSession session, HttpServletRequest request) {
		
		checkUser = checkAdmin(session);
		if(checkUser!=null) {
			return checkUser;
		}
		
		
		int page = 1;
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		
		int startRow = (page-1)*10+1;
		int endRow = page*10;
		
		Map<String, Object> map = new HashMap<>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		int totalCount = adminService.countReport("board");
		
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
		
		List<BoardLikeReportVO> boardReport = adminService.getBoardReportList(map);
		
		if(boardReport.size()>0) {
			for(BoardLikeReportVO report : boardReport) {
				switch(report.getReport_type()) {
				case "1":
					report.setReport_type("부적절한 내용");
					break;
				case "2":
					report.setReport_type("욕설/비방");
					break;
				case "3":
					report.setReport_type("광고/홍보");
					break;
				case "4":
					report.setReport_type("도배");
					break;
				}
				String user_id = adminService.getReportUser(report.getUser_code());
				report.setUser_id(user_id);
				
				BoardVO bad_board = adminService.getBadBoard(report.getBoard_no());
				report.setBad_board(bad_board);
				
			}
		}
		
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("page", page);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("totalPage", totalPage);
		mv.addObject("totalCount", totalCount);
		mv.addObject("boardReport", boardReport);
		mv.setViewName("/admin/board_report_list");
		
		
		return mv;
	}

}
