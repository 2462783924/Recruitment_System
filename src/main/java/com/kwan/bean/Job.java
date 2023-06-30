package com.kwan.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    private int id;
    private String company;
    private String name;
    private String salary;
    private String province;
    private String city;
    private String area;
    private String description;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)  //  投简历时间
    private String time;
    @TableField(exist = false)  //  投递id
    private int deliver;
    @TableField(exist = false)  //  职位对应公司的用户名（留言功能）
    private String receiver;
    // 获取记录条数
    @TableField(exist = false)
    private int count;
}
