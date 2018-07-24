package com.i61.security.controller;


import com.i61.security.entity.User;
import com.i61.security.mapper.admin.UserMapper;
import com.i61.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;


//    /**
//     * Form 表单登录，登录过滤器
//     * @return
//     */
//    @RequestMapping("/login")
//    public String loginPage(){
//        return "login";
//    }


    /**
     * ajax 登录
     * 参考 UsernamePasswordAuthenticationFilter.attemptAuthentication()方法处理表单登录的方法！！
     * UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
     * this.setDetails(request, authRequest);
     * return this.getAuthenticationManager().authenticate(authRequest);
     *
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password,
                        HttpServletRequest request) {


        /**
         * 注意编码方式及其使用
         */
        User user = userMapper.findUserByUsername(username);
        //编码格式~~~~坑爹
        boolean matches = encoder.matches(password, user.getPassword());
        if(!matches){
            throw new RuntimeException("用户名或者密码错误");
        }

        /**
         * 注入用户权限
         */
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));

        /**
         * 配置 UsernamePasswordAuthenticationToken 认证信息
         *  1.
         */
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities), null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "login success";
    }


    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/home")
    @ResponseBody
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        System.out.println(authorities.toString());
        System.out.println(principal.getPassword());
        return "welcome your home!";
    }


    @RequestMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        String username = principal.getUsername();
        String password = principal.getPassword();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        return String.format("hello, login success! \n username = %s, password = %s,auth = %s", username, password, authorities.toString());
    }

    @RequestMapping("/loginFail")
    @ResponseBody
    public String loginFail() {

        return "login failed, try again";
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "logout success";
    }

}
