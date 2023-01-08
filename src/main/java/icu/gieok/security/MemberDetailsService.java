package icu.gieok.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import icu.gieok.service.UserService;
import icu.gieok.vo.AuthVO;
import icu.gieok.vo.UserVO;

public class MemberDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	
	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {

		UserVO uv = userService.userCheck_sec(user_id);	
		
		if(uv==null) {
			uv = new UserVO();
			uv.setUser_id("Not Exist!");
			uv.setUser_pw("Invalid");
		}
		
		List<AuthVO> authList = new ArrayList<>();
		uv.setAuthList(authList);
		
		return new MemberUser(uv);
	}
	
	
}
