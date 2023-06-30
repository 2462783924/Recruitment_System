package com.kwan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deliver {
    private int id;
    private String username;
    private String company;
    private int job;
    private Timestamp time;
}
