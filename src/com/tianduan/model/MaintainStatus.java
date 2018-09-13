package com.tianduan.model;

import javax.persistence.*;

@Entity
public class MaintainStatus extends Model {

    private static final long SERIALVERSIONUID = 6L;

    //维修单
    public static final String COL_MAINTAIN = "maintain";
    //状态更新时间
    public static final String COL_TIME = "time";
    //状态
    public static final String COL_STATUS = "status";


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = COL_MAINTAIN, referencedColumnName = Maintain.COL_PRIMARYKEY, nullable = false)
    private Maintain maintain;
    @Column(name = COL_TIME, nullable = false)
    private String time;
    @Column(name = COL_STATUS, nullable = false)
    private String status;

    public MaintainStatus() {
    }

//    public MaintainStatus(long id) {
//        super(id);
//    }

    public Maintain getMaintain() {
        return maintain;
    }

    public void setMaintain(Maintain maintain) {
        this.maintain = maintain;
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
