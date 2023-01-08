package icu.gieok.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import icu.gieok.vo.UserVO;

public class MemberUser extends User {

	public MemberUser(UserVO uv) {
		
		
		super(uv.getUser_id(), uv.getUser_pw(), (Collection<? extends GrantedAuthority>) uv.getAuthList());
		
		
//		super(uv.getUser_id(), uv.getUser_pw(), uv.getAuthList().stream().map(
//				auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
//		if(uv!=null) {
//		}
		
	}
	
	
}
