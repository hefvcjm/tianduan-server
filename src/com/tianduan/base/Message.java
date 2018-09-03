package com.tianduan.base;

public enum Message implements IMessage {

    ExecuteOK(0, "执行成功"),
    ExecuteFail(1000, "执行失败"),
    ExecuteFailSelfDetail(1004, "执行失败"),
    NodataOK(2000, "执行成功，无数据"),
    Default(3000, ""),
    SelfDefine(4000, "");

    private String msg;
    private int code;

    Message(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
