package com.kwan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login{
    private int id;             //  用户id（主键）
    private String username;    //  用户名（邮箱/电话--唯一性）
    private String password;    //  密码（md5加密）
    private String role;        //  角色（0：普通用户--1：管理员）
    private Timestamp intime;       //  登录时间
    private Timestamp outtime;      //  退出时间
}
