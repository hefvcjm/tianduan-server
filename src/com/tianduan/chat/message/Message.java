package com.tianduan.chat.message;

import com.tianduan.exception.NullFieldException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Message {

    private static Logger logger = Logger.getLogger(Message.class);

    private static final String KEY_TYPE = "type";
    private static final String KEY_SENDER = "sender";
    private static final String KEY_RECEIVER_TYPE = "receiverType";
    private static final String KEY_RECEIVER_ID = "receiverId";
    private static final String KEY_CONTENT_TYPE = "contentType";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_SENDER_NAME = "senderName";
    private static final String KEY_RECEIVER_NAME = "receiverName";
    private static final String KEY_TIME = "time";

    //消息类型
    @NotNull
    private String type;
    //发送者ID
    @NotNull
    private String sender;
    //接收者类型
    @NotNull
    private String receiverType;
    //接收者ID
    @NotNull
    private String receiverId;
    //接收内容类型
    @NotNull
    private String contentType;
    //消息内容
    @NotNull
    private String content;
    //发送者名字
    @NotNull
    private String senderName;
    //接收者名字
    @NotNull
    private String receiverName;
    //时间
    private Date time;

    public Message() {

    }

    public Message(JSONObject json) {
        Set<String> keys = json.keySet();
        setType(json.getString(KEY_TYPE));
        setSender(json.getString(KEY_SENDER));
        setReceiverType(json.getString(KEY_RECEIVER_TYPE));
        setReceiverId(json.getString(KEY_RECEIVER_ID));
        setContentType(json.getString(KEY_CONTENT_TYPE));
        setContent(json.getString(KEY_CONTENT));
        setSenderName(json.getString(KEY_SENDER_NAME));
        setReceiverName(json.getString(KEY_RECEIVER_NAME));
        if (keys.contains(KEY_TIME)) {
            try {
                setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) json.get(KEY_TIME)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            setTime(new Date());
        }
    }

    public Message(String json) {
        this(new JSONObject(json));
    }

    public JSONObject createMessage() throws NullFieldException {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getAnnotation(NotNull.class) != null) {
                try {
                    field.setAccessible(true);
                    Object get = field.get(this);
                    if (get == null) {
                        throw new NullFieldException(field);
                    }
                    continue;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return messageBuilder();
    }

    private JSONObject messageBuilder() {
        JSONObject json = new JSONObject().put(KEY_TYPE, type)
                .put(KEY_SENDER, sender)
                .put(KEY_CONTENT_TYPE, contentType)
                .put(KEY_CONTENT, content)
                .put(KEY_RECEIVER_TYPE, receiverType)
                .put(KEY_RECEIVER_ID, receiverId)
                .put(KEY_SENDER_NAME, senderName)
                .put(KEY_RECEIVER_NAME, receiverName);
        if (getTime() == null) {
            setTime(new Date());
        }
        json.put(KEY_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
        return json;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @NotNull
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
