package com.tianduan.base;

import java.io.Serializable;

public class FailDetail implements Serializable {

    /**
     * 描述
     */
    private Object description;

    /**
     * 指导提示
     */
    private Object tips;

    public FailDetail(Object description, Object tips) {
        this.description = description;
        this.tips = tips;
    }

    public FailDetail(Object description) {
        this.description = description;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getTips() {
        return tips;
    }

    public void setTips(Object tips) {
        this.tips = tips;
    }
}
