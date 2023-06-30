package com.kwan.service;

import com.kwan.bean.Chat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatService {
    //  添加聊天记录
    boolean insertChat(Chat chat);
    //  加载双方聊天记录
    List<Chat> getChats(Chat chat);
    //  接收方获取聊天列表
    List<Chat> getChatList(String receiver,String sender, int job, Integer start, Integer size);
    //  删除聊天记录与投递记录
    @Transactional
    boolean deleteChat(String bothside,String username);
    //  获取公告信息
    List<Chat> getAnno(Integer start, Integer size);
}
