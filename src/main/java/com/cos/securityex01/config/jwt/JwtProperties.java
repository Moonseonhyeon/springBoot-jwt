package com.cos.securityex01.config.jwt;

public interface JwtProperties {
	String SECRET = "팽귄악어" ; // 우리 서버만 알고 있는 비밀값
	int EXPIRATION_TIME = 864000000; //10일 단위가 ms(밀리세컨즈, 1/1000초)
	String TOKEN_REFIX= "Bearer ";
	String HEADER_STRING= "Authorization";
}
