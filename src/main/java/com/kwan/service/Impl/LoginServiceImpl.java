package com.kwan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.bean.Login;
import com.kwan.bean.User;
import com.kwan.dao.LoginDao;
import com.kwan.dao.UserDao;
import com.kwan.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;

    @Override   //  通过用户名查找密码（登录）
    public Login checkCredentials(Login login) {
        //  对密码进行md5加密后在与数据库进行验证
        String password = DigestUtils.md5DigestAsHex(login.getPassword().getBytes());
        //  查询数据库
        LambdaQueryWrapper<Login> lqw = new LambdaQueryWrapper<>();
        //  同时匹配用户名和密码
        lqw.eq(Login::getUsername, login.getUsername()).eq(Login::getPassword, password);
        return loginDao.selectOne(lqw);
    }

    @Override   //  更新账号信息
    public boolean updateAccout(Login login) {
        return loginDao.updateById(login) == 1;
    }


    @Override   //  用户注册
    public boolean insertLogin(Login login) {
        //  对密码进行md5加密后在插入到数据库
        String password = DigestUtils.md5DigestAsHex(login.getPassword().getBytes());
        login.setPassword(password);
        return loginDao.insert(login)==1;
    }

    @Override
    public IPage<Login> getAllLogins(String username, Integer start, Integer size) {
        IPage page = new Page(start, size);
        QueryWrapper<Login> qw = new QueryWrapper();
        qw.like("username", username);
        return loginDao.selectPage(page, qw);
    }

    @Override
    public boolean deleteUser(int id) {
        return loginDao.deleteById(id) == 1;
    }
}
