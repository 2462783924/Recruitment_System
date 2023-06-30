package com.kwan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kwan.bean.Login;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService {
    //  匹配用户名和密码（登录）
    Login checkCredentials(Login login);
    //  更新账户信息（更改密码）
    boolean updateAccout(Login login);
    //  注册用户（新建用户）
    boolean insertLogin(Login login);
    //  查找全部用户
    IPage<Login> getAllLogins(String username, Integer start, Integer size);
    //  删除用户
    boolean deleteUser(int id);
}
