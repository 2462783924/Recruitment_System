package com.kwan.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Integer code;   // 编码：1成功，0和其它数字为失败
    private Object data;    // 数据
    private String msg;     // 错误信息

    public static Result scuess(Object object) {
        Result r = new Result();
        r.code = 1;
        r.data = object;
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.code = 0;
        r.msg = msg;
        return r;
    }
}
