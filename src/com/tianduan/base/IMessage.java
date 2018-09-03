package com.tianduan.base;

public interface IMessage {
    /**
     * 获取返回消息
     *
     * @return 返回消息
     */
    String getMessage();

    /**
     * 获取返回状态码
     *
     * @return 状态码
     */
    int getCode();
}
