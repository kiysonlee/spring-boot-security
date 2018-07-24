package com.i61.security.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptEncoder {


    @Bean
    @Primary
    public BCryptPasswordEncoder config(){
        return new BCryptPasswordEncoder();
    }

}
