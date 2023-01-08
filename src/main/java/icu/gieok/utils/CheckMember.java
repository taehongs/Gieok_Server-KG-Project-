package icu.gieok.utils;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public class CheckMember {
	
private ModelAndView checkMember = null;
	
	public CheckMember(HttpSession session) {
		
		if(session.getAttribute("code") == null) {
			checkMember = new ModelAndView();
			checkMember.addObject("msg", "로그인이 필요한 페이지입니다!");
			checkMember.addObject("url", "/login");
			checkMember.setViewName("message");
		}
	}
	
	public ModelAndView getcheckMember() {
		return checkMember;
	}

}
