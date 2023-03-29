package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.UserInfo;
import com.entity.chat.ChatMsg;
import com.service.ChatMsgService;
import com.service.NoticesService;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@ServerEndpoint(value = "/websocket/{userno}")
public class ChatWebSocket {
    private static ChatMsgService chatMsgService;
    private static UserInfoService mineService;

    @Autowired
    public void setChatService(ChatMsgService chatService, UserInfoService mineService) {
       ChatWebSocket.chatMsgService = chatService;
       ChatWebSocket.mineService=mineService;
    }

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, ChatWebSocket> webSocketSet = new ConcurrentHashMap<String, ChatWebSocket>();
    private Session WebSocketsession;
    private String userno = "";

    @OnOpen
    public void onOpen(@PathParam(value = "userno") String param, Session WebSocketsession, EndpointConfig config) {
        userno = param;
        this.WebSocketsession = WebSocketsession;
        webSocketSet.put(param, this);
        addOnlineCount();
        mineService.UpdateUserInfo(new UserInfo().setUserid(userno).setStatus("online"));
    }

    @OnClose
    public void onClose() {
        if (!userno.equals("")) {
            webSocketSet.remove(userno);
            subOnlineCount();
            mineService.UpdateUserInfo(new UserInfo().setUserid(userno).setStatus("offline"));
        }
    }

	@OnMessage
    public void onMessage(String mine, Session session) {
        JSONObject jsonObject = JSONObject.parseObject(mine);
        String msgtype=jsonObject.getString("type");
        if(msgtype.equals("friend")){
            sendToUser(jsonObject.toJavaObject(UserInfo.class));
        }else if(msgtype.equals("group")){
            sendAll(jsonObject.toJavaObject(UserInfo.class));
        }
    }

    public void sendToUser(UserInfo message) {
        String reviceUserid = message.getToid();
        chatMsgService.insertChatmsg(new ChatMsg().setMsgtype("0").setReciveuserid(reviceUserid).setSenduserid(message.getId()).setContent(message.getContent()));
        try {
            if (webSocketSet.get(reviceUserid) != null) {
                webSocketSet.get(reviceUserid).sendMessage(JSONObject.toJSONString(message));
            }else{
                webSocketSet.get(userno).sendMessage("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAll(UserInfo message) {

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChatWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChatWebSocket.onlineCount--;
    }

}

