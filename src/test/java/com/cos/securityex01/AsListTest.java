package com.cos.securityex01;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;



public class AsListTest {
	
	@Test
	public void 컬렉션_테스트() {
		String[] str = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER"};
		List<String> list = Arrays.asList(str);  //asList안에 배열을 넣으면 된다. 그럼 컬렉션으로 출력해주는 거다
		for(String s : list) {
			System.out.println(s);
		}
	}

}
