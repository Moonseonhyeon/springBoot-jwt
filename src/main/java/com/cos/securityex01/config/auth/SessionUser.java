package com.cos.securityex01.config.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//SessionUser 이거 안씀
public class SessionUser {
	//User오브젝트로 담으면 패스워드가 담기기 때문에 만듬.
	private long id;
	private String username;
	private List<String> roles;

}
