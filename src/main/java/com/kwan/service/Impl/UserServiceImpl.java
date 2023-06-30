package com.kwan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kwan.bean.User;
import com.kwan.dao.UserDao;
import com.kwan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        return userDao.selectOne(lqw);
    }

    @Override
    public boolean updateResume(String username, String fileName) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        User user = new User();
        user.setUsername(username);
        user.setResume(fileName);
        return userDao.update(user, lqw) == 1;
    }

    @Override
    public boolean updateUser(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, user.getUsername());
        return userDao.update(user, lqw) == 1;
    }
}
