package com.kwan.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private int id;
    private String bothside;
    private String sender;
    private String receiver;
    private Timestamp time;
    private String msg;

    @TableField(exist = false)
    private String resume;
    @TableField(exist = false)
    private int count;
}
