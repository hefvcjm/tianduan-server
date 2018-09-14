package com.tianduan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = COL_REPAIR, referencedColumnName = Repair.COL_PRIMARYKEY, nullable = false, unique = true)
    private Repair repair;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "maintain_engineer"
            , joinColumns = @JoinColumn(name = "maintain_id", referencedColumnName = Maintain.COL_PRIMARYKEY)
            , inverseJoinColumns = @JoinColumn(name = "engineer_id", referencedColumnName = Engineer.COL_PRIMARYKEY))
    private Set<Engineer> engineers;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = MaintainStatus.COL_MAINTAIN)
    private Set<MaintainStatus> statuses;

    public Maintain() {
    }

//    public Maintain(long id) {
//        super(id);
//    }

    public void addEngineer(Engineer engineer) {
        engineers.add(engineer);
    }


    public void addStatus(MaintainStatus status) {
        statuses.add(status);
    }

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public Set<Engineer> getEngineers() {
        return engineers;
    }

    public void setEngineers(Set<Engineer> engineers) {
        this.engineers = engineers;
    }

    public Set<MaintainStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<MaintainStatus> statuses) {
        this.statuses = statuses;
    }
}
