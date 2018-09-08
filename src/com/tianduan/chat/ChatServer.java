package com.tianduan.chat;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@Scope("prototype")
@ServerEndpoint("/chat")
public class ChatServer {

    private static Logger logger = Logger.getLogger(ChatServer.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("连接建立");
        logger.debug(session.getUserProperties().toString());
    }

    @OnClose
    public void onClose() {
        logger.info("连接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.info("接收到消息：" + message);
            Thread.sleep(2000);
            session.getBasicRemote().sendText("polo"); //发送消息
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new IllegalArgumentException(throwable);
    }
}
