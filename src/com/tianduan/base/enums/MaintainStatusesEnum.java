package com.tianduan.base.enums;

public enum MaintainStatusesEnum {

    HANDLING(1, "处理中", "处理中..."),
    READY(2, "就位", "工程师已就位！"),
    COMPLETED(3, "完成维修", "完成维修");


    //顺序
    private int order;
    //名称
    private String name;
    //描述
    private String description;

    MaintainStatusesEnum(int order, String name, String description) {
        this.order = order;
        this.name = name;
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
