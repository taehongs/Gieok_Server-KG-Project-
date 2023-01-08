package icu.gieok.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import icu.gieok.service.UserService;
import icu.gieok.vo.UserVO;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{

   @Autowired
   private UserService userService;
   
   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
         Authentication auth) throws IOException, ServletException {

      HttpSession session = request.getSession();
      
      List<String> roleName = new ArrayList<>();
      
      auth.getAuthorities().forEach(authority -> {
         roleName.add(authority.getAuthority());
      });
      
      String user_id = request.getParameter("user_id");
      
      UserVO uv = userService.userCheck_sec(user_id);
      
      Map<String, Object> countMsg = new HashMap<>();
      countMsg.put("message_receiver", user_id);
	
      int msgCount = userService.countUnreadMessage(countMsg);
      
      
      session.setAttribute("code", uv.getUser_code());
      session.setAttribute("id", uv.getUser_id());
      session.setAttribute("name", uv.getUser_name());
      session.setAttribute("grade", uv.getUser_grade());
      session.setAttribute("profile", uv.getUser_profile());
      session.setAttribute("msgCount", msgCount);
      
      String url = (String) session.getAttribute("referer");
      if(url==null) {
         url = "/";
      }
      response.sendRedirect(url);
   }
}
