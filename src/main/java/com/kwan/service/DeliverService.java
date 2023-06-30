package com.kwan.service;

import com.kwan.bean.Deliver;

public interface DeliverService {
    //  投递简历（插入）
    boolean saveDeliver(Deliver deliver);
    //  取消投递简历（删除）
    boolean deleteDeliver(int id);
}
