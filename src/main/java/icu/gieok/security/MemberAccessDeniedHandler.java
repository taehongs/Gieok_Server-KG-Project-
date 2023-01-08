package icu.gieok.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import icu.gieok.service.UserService;
import icu.gieok.vo.UserVO;

public class MemberAccessDeniedHandler implements AccessDeniedHandler{

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response,
         AccessDeniedException accessDeniedException) throws IOException, ServletException {

	   HttpSession session = request.getSession();
	   session.invalidate();
			
      response.sendRedirect("/");
   }
   
}