package com.tianduan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Repair extends Model {

    private static final long SERIALVERSIONUID = 2L;

    //报修客户
    public static final String COL_CLIENT = "client";
    //设备名称
    public static final String COL_NAME = "name";
    //设备编号
    public static final String COL_CODE = "code";
    //问题描述
    public static final String COL_DESCRIPTION = "description";
    //故障部位
    public static final String COL_FAULTPART = "faultpart";
    //清单号
    public static final String COL_TICKET = "ticket";
    //报修时间
    public static final String COL_TIME = "time";
    //照片
    public static final String COL_PICTURES = "pictures";
    //音频
    public static final String COL_AUDIOS = "audios";
    //视频
    public static final String COL_VIDEOS = "videos";
    //状态
    public static final String COL_STATUS = "status";

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = COL_CLIENT, referencedColumnName = Client.COL_PRIMARYKEY, nullable = false)
    @JsonIgnore
    private Client client;
    @Column(name = COL_NAME)
    private String name;
    @Column(name = COL_CODE)
    private String code;
    @Column(name = COL_DESCRIPTION)
    private String description;
    @Column(name = COL_FAULTPART)
    private String faultpart;
    @Column(name = COL_TICKET, nullable = false, unique = true)
    private String ticket;
    @Column(name = COL_TIME, nullable = false)
    private String time;
    @Column(name = COL_PICTURES, length = 2048)
    private String pictures;
    @Column(name = COL_AUDIOS, length = 2048)
    private String audios;
    @Column(name = COL_VIDEOS, length = 2048)
    private String videos;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = RepairStatus.COL_REPAIR)
    private Set<RepairStatus> statuses;

    public Repair() {
    }

//    public Repair(long id) {
//        super(id);
//    }

    public Repair(Client client, String ticket) {
        this.client = client;
        this.ticket = ticket;
    }

    public void addStatus(RepairStatus status) {
        statuses.add(status);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaultpart() {
        return faultpart;
    }

    public void setFaultpart(String faultpart) {
        this.faultpart = faultpart;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getAudios() {
        return audios;
    }

    public void setAudios(String audios) {
        this.audios = audios;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public Set<RepairStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<RepairStatus> statuses) {
        this.statuses = statuses;
    }

}
