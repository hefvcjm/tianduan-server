package com.tianduan.exception;

public class NoReqiredJsonKeyException extends Exception {
    public NoReqiredJsonKeyException(String key) {
        super("缺少键(" + key + ")");
    }

    public NoReqiredJsonKeyException(String superKey, String subkey) {
        super("键(" + superKey + ")下缺少子键(" + subkey + ")");
    }
}
