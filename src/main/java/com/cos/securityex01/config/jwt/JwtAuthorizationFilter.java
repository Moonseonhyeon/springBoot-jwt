package com.cos.securityex01.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.config.auth.SessionUser;
import com.cos.securityex01.dto.LoginRequestDto;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

//인가
//BasicAuthenticationFilter : HEARER 전문 필터 header값 검증에서 
//시큐리티 컨택스트 holder에 넣어줄거임. = 세션을 만드는 거임.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	UserRepository userRepository;
		
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);	
		this.userRepository = userRepository;
	}	
	
	//SecurityConfig에서 첫번째 필터  다음으로 두번째 필터가 여기다!
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//서명할 때 첫번째로 HEADER값 확인
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		if(header == null || !header.startsWith(JwtProperties.TOKEN_REFIX)) {
			//무조건 있는게 아니라 (빈값 확인) 토큰이 Bearer인지 확인
			chain.doFilter(request, response); //아무것도 안하고 돌려보냄
		}
			String token = request.getHeader(JwtProperties.HEADER_STRING)
			// jwt token에는 공백들어있으면 안되고 == 이렇게 생긴 패딩이 들어오면 안된다. (url로 하다보면 공백이나 =이 들어와있을 수가 있다)
			.replace(JwtProperties.TOKEN_REFIX, "") //프리픽스 bearer도 날림. 그래야 딱 토큰만 남아요.
			.replace("", "")
			.replace("=", "");
			
			// 5. 토큰 검증(이게 인증이기 때문에 AuthenticationManager도 필요없고
			//내가 SecurityContext에 직접 접근해서 자동으로 UserDetailsService에 있는 loadByusername이 호출된다.
			//(a.b + 팽귄악어)를 해쉬한값이랑 c를 디코드한 값이랑 똑같으면 서명 완료.
				String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)) //디코드하고있다.
						.build()
						.verify(token) // 마법 => (a.b + 팽귄악어)를 해쉬한 값이랑 c를 디코드한 값이랑 똑같으면 서명 완료.
						.getClaim("username").asString();
				System.out.println("토큰 검증 jwt의 username?" + username);
				
				//비공개 크레임에 
			
		if(username != null) {
			User user = userRepository.findByUsername(username);
			
			//인증은 토큰 검증 시 끝, 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
			//아래와 같이 토큰을 만들어서 Athentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					principalDetails, //나중에 컨트롤러에서 DI해서 쓸때 사용하기 편함.
					null,  //패스워드는 모르니까 null처리, 어차피 지금 인증하는게 아니니까!
					principalDetails.getAuthorities());
			
			/*
			 * SessionUser sessioinUser = SessionUser.builder() .id(user.getId())
			 * .username(user.getUsername()) .roles(user.getRoleList()) .build();
			 * HttpSession session = request.getSession();
			 * session.setAttribute("sessionUser", sessioinUser);
			 */
			
			//강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
				chain.doFilter(request, response);
				
					
		/*
		 * Authentication authentication = new
		 * UsernamePasswordAuthenticationToken(username, ""); //강제로 넣으면
		 * PrincipalDetailsService를 자동호출.
		 * SecurityContextHolder.getContext().setAuthentication(authentication); //로그인할
		 * 필요없고 서명만하면 끝인데 그래서 메니져 필요 없다.
		 */					
				}
			
	}

	

