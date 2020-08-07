package com.cos.securityex01.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.securityex01.model.User;

public class PrincipalDetails implements UserDetails{
	//여기~
	private User user;
	
	public PrincipalDetails (User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	//까지 만들고 오버라이드
	
	//session사용한다면 setter필요한데 안 만든 이유 수정못하게 하려고	
	//유저 정보 수정하고 나서 다시 토큰만들지않고 여기에서 setter로 여기 user만 수정 
	//로그인을 직접시키던가 UserDetails값의 객체를 수정하면 됨
	
	
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
		
		return user.getPassword();
	}
	@Override
	public String getUsername() {
		
		return user.getUsername();
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
