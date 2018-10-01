package com.tianduan.chat;

import com.tianduan.chat.message.Message;
import com.tianduan.exception.NullFieldException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

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
        if (userMap.keySet().contains(objectId)) {
            userMap.remove(objectId);
            subOnlineCount();
        }
    }

    @OnMessage
    public void onMessage(@PathParam("objectId") String objectId, String message) {
        try {
            logger.info("接收到消息：" + message);
            JSONObject json = new JSONObject(message);
            String type = json.getString("type");
            String userId;
            Session rcvSession;
            switch (type) {
                case "message":
                    Message msg = new Message(message);
                    userId = msg.getReceiverId();
                    logger.info("receiverId=" + userId);
                    rcvSession = userMap.get(userId);
                    rcvSession.getBasicRemote().sendText(msg.createMessage().toString());
                    logger.info(msg.createMessage().toString());
                    break;
                case "ping":
                    logger.info("receiverId=" + objectId);
                    rcvSession = userMap.get(objectId);
                    rcvSession.getBasicRemote().sendText(new JSONObject().put("type", "pong").toString());
                    break;
                default:
                    break;
            }
        } catch (NullFieldException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(@PathParam("objectId") String objectId, Throwable throwable) {
        if (userMap.keySet().contains(objectId)) {
            userMap.remove(objectId);
            subOnlineCount();
        }
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
