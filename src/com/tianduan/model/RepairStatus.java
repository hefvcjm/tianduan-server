package com.tianduan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class RepairStatus extends Model {

    private static final long SERIALVERSIONUID = 7L;

    //报修单
    public static final String COL_REPAIR = "repair";
    //状态更新时间
    public static final String COL_TIME = "time";
    //状态
    public static final String COL_STATUS = "status";

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = COL_REPAIR, referencedColumnName = Repair.COL_PRIMARYKEY, nullable = false)
    @JsonIgnore
    private Repair repair;
    @Column(name = COL_TIME, nullable = false)
    private String time;
    @Column(name = COL_STATUS, nullable = false)
    private String status;

    public RepairStatus() {
    }

//    public RepairStatus(long id) {
//        super(id);
//    }

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
