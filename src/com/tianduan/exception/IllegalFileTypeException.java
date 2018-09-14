package com.tianduan.exception;

public class IllegalFileTypeException extends Exception {
    public IllegalFileTypeException() {
        super("非法文件类型");
    }

    public IllegalFileTypeException(String fileType, String type) {
        super(String.format("不支持\"%s\"格式的%s类型", fileType, type));
    }
}
