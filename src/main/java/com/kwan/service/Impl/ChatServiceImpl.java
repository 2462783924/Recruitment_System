package com.kwan.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kwan.bean.Chat;
import com.kwan.bean.Deliver;
import com.kwan.dao.ChatDao;
import com.kwan.dao.DeliverDao;
import com.kwan.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatDao chatDao;

    @Override
    public boolean insertChat(Chat chat) {
        return chatDao.insert(chat) == 1;
    }

    @Override
    public List<Chat> getChats(Chat chat) {
        LambdaQueryWrapper<Chat> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Chat::getBothside, chat.getBothside());
        return chatDao.selectList(lqw);
    }

    @Override
    public List<Chat> getChatList(String receiver,String sender, int job, Integer start, Integer size) {
        return chatDao.getChat(receiver,sender, job, start, size);
    }

    @Autowired
    private DeliverDao deliverDao;

    @Override   //  事务管理：同时删除投递记录和聊天记录
    public boolean deleteChat(String bothside, String username) {
        LambdaQueryWrapper<Deliver> deliverLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deliverLambdaQueryWrapper.eq(Deliver::getUsername, username);
        deliverDao.delete(deliverLambdaQueryWrapper);
        LambdaQueryWrapper<Chat> chatLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatLambdaQueryWrapper.eq(Chat::getBothside, bothside);
        chatDao.delete(chatLambdaQueryWrapper);
        return true;
    }

    @Override
    public List<Chat> getAnno(Integer start, Integer size) {
        return chatDao.getAnno(start, size);
    }
}
