package com.cos.securityex01.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService 진입");
		User user = userRepository.findByUsername(username);
		// 못찾은거는 Exception를 던질텐데 내가 잡아서 Global~~로 처리해주면 된다.
		System.out.println("user : " + user);
		
		//session.setAttribute("loginUser", user);
			return new PrincipalDetails(user); //@AuthenticationPrincipal로 꺼내쓰게 되어있다.
			
			//세션 2개이고 로그아웃할 때 invalidation할건데 그럼 다 사라지니까 상관없다.
		
	}

}
