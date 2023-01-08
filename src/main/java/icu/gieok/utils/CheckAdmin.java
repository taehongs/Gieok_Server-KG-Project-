package icu.gieok.utils;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public class CheckAdmin {

	
	private ModelAndView checkAdmin = null;
	
	public CheckAdmin(HttpSession session) {
		
		if(session.getAttribute("code") == null) {
			checkAdmin = new ModelAndView();
			checkAdmin.addObject("msg", "세션이 만료되었습니다! 다시 로그인해주세요.");
			checkAdmin.addObject("url", "/login");
			checkAdmin.setViewName("message");
		}else {
			if(session.getAttribute("grade").equals("m")) {
				checkAdmin = new ModelAndView();
				checkAdmin.addObject("msg", "접근이 허용되지 않습니다!");
				checkAdmin.addObject("url", "/");
				checkAdmin.setViewName("message");
			}
		}
		
	}
	
	public ModelAndView getCheckAdmin() {
		return checkAdmin;
	}
 	
	
}
