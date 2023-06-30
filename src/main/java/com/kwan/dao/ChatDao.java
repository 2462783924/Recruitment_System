package com.kwan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.bean.Chat;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatDao extends BaseMapper<Chat> {
    //  查询对应岗位的应聘者留言
    @Select("SELECT COUNT(DISTINCT chat.sender) AS count, chat.bothside, chat.sender, chat.receiver, CASE WHEN deliver.job = #{job} THEN user.resume ELSE NULL END AS resume " +
            "FROM chat " +
            "LEFT JOIN user ON chat.sender = user.username " +
            "LEFT JOIN deliver ON chat.sender = deliver.username AND deliver.job = #{job} " +
            "WHERE chat.receiver = #{receiver} " +
            "AND chat.sender LIKE CONCAT('%', #{sender}, '%')" +
            "GROUP BY chat.bothside, chat.sender, chat.receiver " +
            "LIMIT #{start}, #{size}")
    List<Chat> getChat(String receiver,String sender, int job, Integer start, Integer size);

    //  获取公告信息
    @Select("SELECT chat.*, COUNT(*) OVER() as count " +
            "FROM chat " +
            "WHERE chat.sender IS NULL AND chat.receiver IS NULL AND chat.bothside IS NOT NULL " +
            "ORDER BY chat.time DESC " +
            "LIMIT #{start}, #{size}")
    List<Chat> getAnno(Integer start, Integer size);
}
