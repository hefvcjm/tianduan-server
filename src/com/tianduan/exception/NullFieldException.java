package com.tianduan.exception;

import java.lang.reflect.Field;

public class NullFieldException extends Exception {
    public NullFieldException(Field field) {
        super(field.getName() + "属性不能为空");
    }
}
