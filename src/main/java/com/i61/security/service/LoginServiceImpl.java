package com.i61.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Override
    public String login() {
        return "hello login call!";
    }
}
