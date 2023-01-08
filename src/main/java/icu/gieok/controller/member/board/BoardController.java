package icu.gieok.controller.member.board;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.BoardService;
import icu.gieok.vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/board_list")
	public ModelAndView board_list(@ModelAttribute BoardVO b, HttpServletRequest request, HttpSession session) {
		
		String board_sort = request.getParameter("board_sort");
		String search_option = request.getParameter("search_option");
		String list_search  = request.getParameter("list_search");

		int page = 1;
		int limit = 10;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int startRow = (page-1)*limit+1;
		int endRow = page*limit;
		int board_count ;

		Map<String, Object> row_sort = new HashMap<>();
		row_sort.put("startRow", startRow);
		row_sort.put("endRow", endRow);
		
		List<BoardVO> blist = new ArrayList<>();
		
		if(board_sort==null && search_option==null && list_search==null) {
			row_sort.put("board_sort", "");
			row_sort.put("search_option", "");
			row_sort.put("list_search", "");
		}else {
			row_sort.put("board_sort", board_sort);
			row_sort.put("search_option", search_option);
			row_sort.put("list_search", list_search);
		}
		board_count = boardService.board_count(row_sort);
		blist = boardService.board_list(row_sort);
		
		ModelAndView m = new ModelAndView();
		
		int totalPage = (board_count/limit);
		if((board_count % limit) != 0) {
			totalPage++;
		}

		int startPage = 0, endPage = 0;

		if(page / limit < 1 || page == limit) {
			startPage = 1;
			endPage = limit;
			if(totalPage < limit) {
				endPage = totalPage;
			}
		}else {
			startPage = ((page/limit)*limit)+1;
			endPage = startPage + 9;
			if(endPage >= totalPage) {
				endPage = totalPage;
			}
		}

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int date = cal.get(Calendar.DATE);
		String today;
		
	    if(date < 10) {
	    	today = year+"-"+month+"-0"+date;
		}else {
			today = year+"-"+month+"-"+date;
		}
		

		m.addObject("blist",blist);
		m.addObject("page", page);
		m.addObject("startPage", startPage);
		m.addObject("endPage", endPage);
		m.addObject("totalPage", totalPage);
		m.addObject("board_sort", board_sort);
		m.addObject("search_option", search_option);
		m.addObject("board_count", board_count);
		m.addObject("list_search", list_search);
		m.addObject("today", today);
		m.setViewName("/member/board_list");

		return m;
	}
	
	@GetMapping("/board_cont")
	public ModelAndView board_content(@RequestParam("no") int board_no, int page, BoardVO b, HttpSession session) {
		
		String grade = (String)session.getAttribute("grade");
		if(grade == null ||grade.equals("m")) {
			b = boardService.board_contM(board_no);
		}else {
			b = boardService.board_cont(board_no);
		}
		ModelAndView cm = new ModelAndView();
		cm.addObject("blist", b);
		cm.addObject("page", page);
		cm.setViewName("/member/board_cont");
		return cm;
	}
	
	

	
	
	
	
}
