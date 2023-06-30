package com.kwan.service;

import com.kwan.bean.User;

public interface UserService {
    //  通过用户名查找用户
    User getByUsername(String username);

    //  更新简历信息
    boolean updateResume(String username, String fileName);

    //  更新用户信息
    boolean updateUser(User user);
}
