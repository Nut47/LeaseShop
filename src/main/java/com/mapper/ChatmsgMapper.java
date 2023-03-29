package com.mapper;

import com.entity.UserInfo;
import com.entity.chat.ChatMsg;

import java.util.List;

public interface ChatmsgMapper {

    void insertChatmsg(ChatMsg chatmsg);

    List<UserInfo> LookChatMsg(ChatMsg chatMsg);
}