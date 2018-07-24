package com.i61.security.service;

import org.springframework.security.access.prepost.PreAuthorize;

public interface LoginService {


    @PreAuthorize("hasRole('role:list')")
    String login();

}
