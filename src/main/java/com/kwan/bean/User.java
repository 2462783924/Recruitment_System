package com.kwan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String name;
    private String sex;
    private String phone;
    private String email;
    private String information;
    private String img;
    private String resume;
}
