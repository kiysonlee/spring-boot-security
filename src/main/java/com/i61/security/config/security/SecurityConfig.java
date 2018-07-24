package com.i61.security.config.security;


import com.i61.security.config.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



/**
 * 安全设置
 *
 * @date 2018/06/09
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Qualifier("userDetailsServiceImpl")
//    @Autowired
//    UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder encoder;

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth)
//            throws Exception {
//
//        auth
//                .userDetailsService(this.userDetailsService)
//                //密码加密方式
//                .passwordEncoder(encoder);
//
//    }

    @Override
    protected void configure(final HttpSecurity http)
            throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and();

//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("username").passwordParameter("password")
//                .successForwardUrl("/loginSuccess")
//                .failureForwardUrl("/loginFail")
//                .permitAll()
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        http.addFilterBefore(new CorsFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}