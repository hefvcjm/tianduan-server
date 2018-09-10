package com.tianduan.chat;

import com.tianduan.chat.message.Message;
import com.tianduan.exception.NullFieldException;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{objectId}")
public class ChatServer {

    private static Logger logger = Logger.getLogger(ChatServer.class);
    //在线人数
    private static int onlineCount = 0;
    // Map<用户id，用户Session>
    private static Map<String, Session> userMap = new ConcurrentHashMap<String, Session>(); // 在线用户

    @OnOpen
    public void onOpen(@PathParam("objectId") String objectId, Session session) {
        logger.info("开始建立连接objectId=" + objectId);
        userMap.put(objectId, session);
        addOnlineCount();
        logger.info("连接建立");
    }

    @OnClose
    public void onClose(@PathParam("objectId") String objectId, CloseReason closeReason) {
        logger.info("连接关闭, [reason:" + closeReason.getCloseCode() + "--" + closeReason.getReasonPhrase() + "]");
        userMap.remove(objectId);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            Message msg = new Message(message);
            logger.info("接收到消息：" + message);
            String userId = msg.getReceiverId();
            logger.info("receiverId=" + userId);
            Session rcvSession = userMap.get(userId);
            rcvSession.getBasicRemote().sendText(msg.createMessage().toString());
            logger.info(msg.createMessage().toString());
        } catch (NullFieldException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new IllegalArgumentException(throwable);
    }

    private void addOnlineCount() {
        onlineCount++;
    }

    private void subOnlineCount() {
        onlineCount--;
        if (onlineCount <= 0) {
            onlineCount = 0;
        }
    }
}
