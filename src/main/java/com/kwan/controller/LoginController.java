package com.kwan.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kwan.bean.Login;
import com.kwan.bean.User;
import com.kwan.common.Result;
import com.kwan.dao.UserDao;
import com.kwan.service.LoginService;
import com.kwan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("/login")
//解决跨域问题
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;

    // 点击登录按钮后调用
    @PostMapping("/checkCredentials")
    public Result checkCredentials(@RequestBody Login login, HttpSession session) {
        Login l = loginService.checkCredentials(login);
        if (l != null) {
            //  更新登录时间
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            l.setIntime(currentTimestamp);
            if (!loginService.updateAccout(l)) {
                log.error("登录时间更新失败");
            }
            return Result.scuess(l);
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    //  退出登录时调用
    @PostMapping("/logout")
    public Result loginOut(HttpSession session) {
//        session.removeAttribute("user");
        return Result.scuess(true);
    }

    @Autowired
    private UserDao userDao;

    //  用户注册
    @Transactional
    @PostMapping("/userRegist")
    public Result userRegist(@RequestBody Login login) {
        try {
            loginService.insertLogin(login);
            return Result.scuess("用户注册成功");
        } catch (Exception e) {
            if (e.getClass().toString().equals("class org.springframework.dao.DuplicateKeyException"))
                return Result.error("用户已被注册");
        }
        User user = new User();
        user.setUsername(login.getUsername());
        userDao.insert(user);
        return userDao.insert(user) == 1 ? Result.scuess("用户注册成功") : Result.error("用户注册失败");
    }

    //  获取全部用户
    @PostMapping("/getAllLogins")
    public Result getAllLogins(String username, Integer start, Integer size) {
        IPage<Login> loginIPage = loginService.getAllLogins(username, start, size);
        if (loginIPage.getTotal() == 0) {
            return Result.error("查询不到数据");
        }
        return Result.scuess(loginIPage);
    }

    //  更新用户角色
    @PostMapping("/updateRole")
    public Result updateRole(Login login, String thisuser) {
        if (login.getUsername().equals(thisuser)) {
            return Result.error("自身账号无法修改身份,请联系其他管理员操作");
        }
        if (login.getRole().equals("0")) {
            login.setRole("1");
        } else {
            login.setRole("0");
        }
        return loginService.updateAccout(login) ? Result.scuess("用户角色修改成功") : Result.error("用户角色修改失败");
    }

    //  删除用户
    @PostMapping("/deleteUser")
    public Result deleteUser(int id, int thisID) {
        if (id == thisID) {
            return Result.error("自身账号无法删除,请联系其他管理员操作");
        }
        return loginService.deleteUser(id) ? Result.scuess("用户删除成功") : Result.error("用户删除失败");
    }
}
