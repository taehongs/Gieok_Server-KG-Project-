package icu.gieok.controller.member.timetable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimeTableController {

	
	
	
	
	@GetMapping("/time_table")
	public ModelAndView time_table() {
		
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/member/time_table");
		
		return mv;
	}
	
	
}
