package icu.gieok.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import icu.gieok.service.UserService;
import icu.gieok.vo.UserVO;

public class LoginFailureHandler implements AuthenticationFailureHandler{

   @Autowired
   private UserService userService;

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;
   
   @Override
   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
         AuthenticationException exception) throws IOException, ServletException {
      
      String id = request.getParameter("user_id");
      String pw = request.getParameter("user_pw");
      
      String msg = "";
      String url = "";
      
      Map<String, String> map = new HashMap<>();
      
      map.put("user_id", id);
      
      
      
      UserVO user = userService.checkAuth(map);
      
      if(user==null) {
         msg = "아이디 혹은 비밀번호가 일치하지 않아요 :(";
         url = "/login";
      }else if(!bCryptPasswordEncoder.matches(pw, user.getUser_pw())) {
    	  msg = "아이디 혹은 비밀번호가 일치하지 않아요 :(";
    	  url = "/login";
      }
      else {
         int user_auth = user.getUser_auth();

         if(user_auth==0) {
            msg = "이메일 인증이 필요합니다! :(";
            url = "/login";
         }
      }
      response.setContentType("text/html; charset=UTF-8");
//      request.setAttribute("msg", msg);
//      request.setAttribute("url", url);
//      RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//      rd.forward(request, response);
//      response.sendRedirect("message");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('"+msg+"');");
      out.println("location.href='"+url+"'");
      out.println("</script>");
      
   }
}