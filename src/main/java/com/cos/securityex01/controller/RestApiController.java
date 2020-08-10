package com.cos.securityex01.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.config.auth.SessionUser;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
//@CrossOrigin //CORS허용
public class RestApiController {
	
	//둘다 IoC하는 건데 AutoWierd는 스프링 전용 어노테이션, @inject는 자바에서 다 사용가능.
	private final UserRepository userRepository; 
	private final BCryptPasswordEncoder bCrytPasswordEncoder;

	
	//모든 사람이 접근가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	//메니져가 접근가능
	@GetMapping("manager/reports")
	public String reports() {
		return "<h1>reports</h1>";
	}
	
	//어드민이 접근가능
	@GetMapping("admin/users")
	public List<User> users(){
		return null;
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCrytPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입 완료";
	}
		//권한을 확인하기 위해서 principalDetails에서 꺼내서 권한 확인을 해야한다.
	@GetMapping("user")
	public String user(Authentication authentication) {
		/*
		 * PrincipalDetails principal = (PrincipalDetails)
		 * authentication.getPrincipal(); System.out.println("principal : "+
		 * principal.getUser().getId()); System.out.println("princicpal : "+
		 * principal.getUser().getUsername()); System.out.println("princicpal : "+
		 * principal.getUser().getRoles());
		 */
		return "<h1>user</h1>";
	}
				
	}

