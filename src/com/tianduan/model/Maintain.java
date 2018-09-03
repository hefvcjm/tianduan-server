package com.tianduan.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Maintain extends Model {

    private static final long SERIALVERSIONUID = 3L;

    //报修单
    public static final String COL_REPAIR = "repair";
    //维修工程师
    public static final String COL_ENGINEER = "engineer";
    //状态
    public static final String COL_STATUS = "status";

    @ManyToOne
    @JoinColumn(name = COL_REPAIR, nullable = false)
    private Repair repair;
    @ManyToOne
    @JoinColumn(name = COL_ENGINEER, nullable = false)
    private Engineer engineer;
    @OneToMany(mappedBy = MaintainStatus.COL_PRIMARYKEY)
    private Set<MaintainStatus> statuses;

    public Maintain() {
    }

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public Set<MaintainStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<MaintainStatus> statuses) {
        this.statuses = statuses;
    }
}
