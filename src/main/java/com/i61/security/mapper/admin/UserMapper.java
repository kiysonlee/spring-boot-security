package com.i61.security.mapper.admin;

import com.i61.security.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @date 2018/06/09
 */
public interface UserMapper {

    User findUserByUsername(@Param("username") String username);

    User find(@Param("username") String username, @Param("password") String password);
}