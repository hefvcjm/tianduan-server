package com.tianduan.base;

import java.io.Serializable;

public class JsonResponse implements Serializable {

    private static final long SERIALVERSIONUID = 165468541320564651L;

    /**
     * 数据
     */
    private Object data;
    /**
     * 消息
     */
    private String msg;
    /**
     * 状态码
     */
    private int code;

    /**
     * 标准返回数据
     *
     * @param data    数据体
     * @param message 响应说明message
     */
    public JsonResponse(Object data, Message message) {
        this.data = data;
        this.msg = message.getMessage();
        this.code = message.getCode();
    }

    /**
     * 标准返回数据，无数据
     *
     * @param message 响应说明message
     */
    public JsonResponse(Message message) {
        this.data = null;
        this.msg = message.getMessage();
        this.code = message.getCode();
    }

    /**
     * 标准返回数据，执行成功
     *
     * @param data 数据体
     */
    public JsonResponse(Object data) {
        this.data = data;
        this.msg = Message.ExecuteOK.getMessage();
        this.code = Message.ExecuteOK.getCode();
    }

    /**
     * 自定义返回数据
     *
     * @param data 数据体
     * @param msg  自定义消息
     */
    public JsonResponse(Object data, String msg) {
        this.data = data;
        this.msg = msg;
        this.code = Message.SelfDefine.getCode();
    }

    /**
     * 无返回数据
     */
    public JsonResponse() {
        this.data = null;
        this.msg = Message.NodataOK.getMessage();
        this.code = Message.NodataOK.getCode();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
