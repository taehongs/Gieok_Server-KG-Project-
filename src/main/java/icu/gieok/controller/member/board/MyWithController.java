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

import icu.gieok.service.MyWithService;
import icu.gieok.utils.CheckMember;
import icu.gieok.vo.BoardVO;
import icu.gieok.vo.WithVO;

@Controller
public class MyWithController {

	@Autowired
	private MyWithService myWithService;
	
	private ModelAndView checkUser;
	
	
	// 회원 여부 확인 
	public ModelAndView checkMember(HttpSession session) {
		
		CheckMember checkMember = new CheckMember(session);
		
		return checkMember.getcheckMember();
		
	}
	
	// 나의 동행 목록
	@GetMapping("/mywrite_with_list")
	public ModelAndView mywriteWithList(HttpServletRequest request, HttpSession session,
			String category, String keyword) {
		
		
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			return checkUser;
		}
		
		// 작성자 (로그인한 user)
		String wr_user_id = (String)session.getAttribute("id");
		
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
		map.put("wr_user_id", wr_user_id);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("category", category);
		map.put("keyword", keyword);
		
		// Total - Pagination
		int totalCount = myWithService.countMyWith(map); // 테이블 내 해당 게시물 전체 개수
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
		List<BoardVO> with_li_list = myWithService.getMyWithList(map);
		
		if (with_li_list.size() > 0) {
			for (BoardVO with : with_li_list) {
				with.setBoard_startDay(with.getBoard_startDay().substring(0, 10));
				with.setBoard_endDay(with.getBoard_endDay().substring(0, 10));
				
				int acceptCount = myWithService.countWithAccept(with.getBoard_no());
				int board_memcount = with.getBoard_memCount();
				
				String with_accept = "";
				
				if (acceptCount == board_memcount) {
					with_accept = "모집 완료";
				} else {
					with_accept = "신청 목록";
				}
				
				with.setWith_accept(with_accept);
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
		
		mv.setViewName("/member/mywrite_with_list");
		
		return mv;
		
	} // board_with_list()
	
	
	// 수락한 동행 수
	@ResponseBody
	@PostMapping("/mywrite_with_acceptcount")
	public String mywriteWithAcceptcount(@RequestBody Map<String, String> map, HttpServletRequest request, HttpSession session) {
		
		int board_no = Integer.parseInt(map.get("board_no"));
		
		int totalCount = myWithService.countWithAccept(board_no);
		
		String tCount = Integer.toString(totalCount);
		
		return tCount;
		
	}
	
	// 나의 동행 삭제
	@ResponseBody
	@PostMapping("/mywrite_with_delete")
	public Map<String, String> mywriteWithDelete(@RequestBody Map<String, String> map, HttpServletRequest request, HttpSession session) {
		
		Map<String, String> result = new HashMap<>();
		
		checkUser = checkMember(session);
		
		if(checkUser != null) {
			result.put("msg", "세션이 만료되었습니다!");
			result.put("url", "/login");
			return result;
		}
		
		String msg = "";
		String url = "";
		
		int board_no = Integer.parseInt(map.get("board_no"));
		
		
		int res = myWithService.delMyWith(board_no);
		
		if (res != 1) {
			msg = "시스템 오류!";
		}
		
		result.put("msg", msg);
		result.put("url", url);
		
		return result;
		
	}
//	@ResponseBody
//	@PostMapping("/mywrite_with_delete")
//	public String mywriteWithDelete(@RequestBody Map<String, String> map, HttpServletRequest request, HttpSession session) {
//		
//		checkUser = checkMember(session);
//		
//		if(checkUser != null) {
//			return "noSession";
//		}
//		
//		int board_no = Integer.parseInt(map.get("board_no"));
//		
//		String check = "";
//		
//		int res = myWithService.delMyWith(board_no);
//		
//		if (res != 1) {
//			check = "fail";
//		}
//		
//		return check;
//		
//	}
	
	// 동행 신청 목록
	@ResponseBody
	@PostMapping("/my_with_list")
	public List<WithVO> myWithList(@RequestBody Map<String, String> map, HttpServletRequest request, HttpSession session) {
		
		int board_no = Integer.parseInt(map.get("board_no"));
		
		List<WithVO> with_li_list = myWithService.getMyWithList2(board_no);
		
		return with_li_list;
		
	}
	
	// 동행 수락
	@ResponseBody
	@PostMapping("/my_with_accept")
	public Map<String, String> myWithAccept(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
		
		Map<String, String> result = new HashMap<>();
		
		
		checkUser = checkMember(session);
		if(checkUser != null) {
			
			result.put("check", "noSession");
			
			return result;
		}
		
		int board_no = Integer.parseInt(map.get("board_no"));
		// 신청자
		String with_user_id = map.get("with_user_id");
		
		WithVO wt = new WithVO();
		
		wt.setBoard_no(board_no);
		wt.setWith_user_id(with_user_id);
		
		String check = "";
		
		int res = myWithService.acceptMyWith(wt);
		String count = String.valueOf(myWithService.countWithAccept(board_no));
		
		
		if (res != 1) {
			check = "fail";
		}
		
		
		result.put("check", check);
		result.put("count", count);
		
		return result;
		
	}
	
	// 동행 거절
	@ResponseBody
	@PostMapping("/my_with_reject")
	public Map<String, String> myWithReject(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
		
		Map<String, String> result = new HashMap<>();
		
		checkUser = checkMember(session);
		if(checkUser != null) {
			
			result.put("check", "noSession");
			
			return result;
		}
		
		int board_no = Integer.parseInt(map.get("board_no"));
		// 신청자
		String with_user_id = map.get("with_user_id");
		
		WithVO wt = new WithVO();
		
		wt.setBoard_no(board_no);
		wt.setWith_user_id(with_user_id);
		
		String check = "";
		
		int res = myWithService.rejectMyWith(wt);
		String count = String.valueOf(myWithService.countWithAccept(board_no));
		
		if (res != 1) {
			check = "fail";
		}
		
		result.put("check", check);
		result.put("count", count);
		
		return result;
		
	}
	
	// 마지막 동행 수락 시 동행 신청 상태 '1' > '3'
	@ResponseBody
	@PostMapping("/change_with_accept")
	public String changeWithAccept(@RequestBody Map<String, String> map, HttpSession session, HttpServletRequest request) {
		
		int board_no = Integer.parseInt(map.get("board_no"));
		
		String check = "";
		
		int res = myWithService.changeWith(board_no);
		
		if (res >= 1) {
			check = "success";
		}
		
		return check;
	}
	
	
	
}


