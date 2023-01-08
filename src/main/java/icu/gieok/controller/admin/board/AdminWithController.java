package icu.gieok.controller.admin.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import icu.gieok.service.BoardWithService;
import icu.gieok.utils.CheckMember;
import icu.gieok.vo.WithVO;

@Controller
public class AdminWithController {
	
	@Autowired
	private BoardWithService boardWithService;
	
	private ModelAndView checkUser;
	
	
	// 회원 여부 확인 
	public ModelAndView checkMember(HttpSession session) {
		
		CheckMember checkMember = new CheckMember(session);
		
		return checkMember.getcheckMember();
		
	}
	
	
	// 동행 목록 관리자 삭제
	@ResponseBody
	@PostMapping("/board_with_delbtn")
	public String board_with_delok(@RequestBody Map <String, String> map,
			HttpSession session, HttpServletRequest request) {
		
		
		checkUser = checkMember(session);
		
		if (checkUser != null) {
			
			return "noSession";
			
		}
		
		
		int board_no = Integer.parseInt(map.get("board_no"));
		
		
		String delCheck = "";
		
		int res = boardWithService.delete_WT(board_no);
		
		if (res != 1) {
			
			delCheck = "fail";
			
		}
		
		return delCheck;
	}

}
