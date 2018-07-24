package com.i61.security;

import com.i61.security.entity.User;
import com.i61.security.mapper.admin.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityApplicationTests {

	@Autowired
	UserMapper mapper;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Test
	public void contextLoads() {
		String encode1 = encoder.encode("admin123");
		String encode2 = encoder.encode("admin123");
		System.out.println(encode1);
		System.out.println(encode2);
		System.out.println(encoder.matches("admin123",encode1));
		System.out.println(encoder.matches("admin123",encode1));
	}

}
