package com.cos.securityex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.securityex01.config.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본적 스프링 필터체인에 등록. 컨트롤러말고 필터에서 처리 (편하니까)
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //Adapter인 이유 필요한거만 쓰려고.

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
		//cors를 부분적으로 허용해야 한다. 방법은 컨트롤러 메소드 마다 필요한 부분만 @CrossOrigin걸기
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable() // jsessionId안쓸거다. 그럼 어떻게 로그인함? jwt로!
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))//내가 만든 인증 필터 
				//.addFilter(null) // -> 권한처리 
				.authorizeRequests()
				.antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER')  or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll();
		
	}


	
	
}
