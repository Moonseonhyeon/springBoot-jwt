package com.cos.securityex01.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.securityex01.model.User;

public class PrincipalDetails implements UserDetails{
	
	private User user;
	
	public PrincipalDetails (User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	
	//session사용한다면
	//setter안 만든 이유 수정못하게 하려고	
	//유저 정보 수정하고 나서 다시 토큰만들지않고 여기에서 setter로 여기 user만 수정 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoleList().forEach(r->{
			authorities.add(()->{return r;}); // 무슨 타입을 넣어야할 지 고민하지 말고 화살표로 넣어줬다.			
		});
		return authorities;
		
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
