package com.tianduan.base.enums;

public enum RepairStatusesEnum {


    COMMITED(1, "提交报修", "已提交报修，等待处理..."),
    DISTRIBUTING(2, "派单", "正在派单..."),
    COMPLETED(3, "完成维修", "完成维修");


    //顺序
    private int order;
    //名称
    private String name;
    //描述
    private String description;

    RepairStatusesEnum(int order, String name, String description) {
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
