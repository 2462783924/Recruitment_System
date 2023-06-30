package com.kwan.controller;

import com.kwan.bean.Chat;
import com.kwan.common.Result;
import com.kwan.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/insertChat")
    public Result insertChat(Chat chat) {
        int result = chat.getSender().compareTo(chat.getReceiver());
        String bothside = null;
        if (result < 0) {
            bothside = chat.getSender() + "&" + chat.getReceiver();
        } else if (result > 0) {
            bothside = chat.getReceiver() + "&" + chat.getSender();
        } else {
            return Result.error("无法与自身用户交流");
        }
        chat.setBothside(bothside);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        chat.setTime(currentTimestamp);
        return chatService.insertChat(chat) ? Result.scuess(currentTimestamp.toString().substring(0, 19)) : Result.error("发送失败");
    }

    @PostMapping("/getChat")
    public Result getChat(Chat chat) {
        int result = chat.getSender().compareTo(chat.getReceiver());
        String bothside = null;
        if (result < 0) {
            bothside = chat.getSender() + "&" + chat.getReceiver();
        } else if (result > 0) {
            bothside = chat.getReceiver() + "&" + chat.getSender();
        } else {
            return Result.error("无法与自身用户交流");
        }
        chat.setBothside(bothside);
        List<Chat> chats = chatService.getChats(chat);
        if (chats.isEmpty()) {
            Result.error("没有聊天记录");
        }
        return Result.scuess(chats);
    }

    @PostMapping("/getChatList")
    public Result getChatList(String receiver, String sender, int job, Integer start, Integer size) {
        int s = (start - 1) * size;
        List<Chat> chats = chatService.getChatList(receiver, sender, job, s, size);
        if (chats.isEmpty()) {
            return Result.error("查询不到留言");
        }
        return Result.scuess(chats);
    }

    //  删除投递记录和聊天记录
    @PostMapping("/deleteChatAndDeliver")
    public Result deleteChatAndDeliver(String bothside, String username) {
        chatService.deleteChat(bothside, username);
        return Result.scuess("聊天记录和投递记录删除成功");
    }

    //  发布公告
    @PostMapping("/sendAnnouncement")
    public Result sendAnnoncement(String sender, String msg) {
        Chat chat = new Chat();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        chat.setTime(currentTimestamp);
        chat.setBothside(sender);
        chat.setMsg(msg);
        return chatService.insertChat(chat) ? Result.scuess("公告发布成功") : Result.error("公告发布失败");
    }

    //  获取公告
    @PostMapping("/getAnno")
    public Result getAnno(Integer start, Integer size){
        int s = (start - 1) * size;
        List<Chat> chats = chatService.getAnno(s, size);
        if (chats.isEmpty()){
            return Result.error("查询不到公告");
        }
        return Result.scuess(chats);
    }
}
