package com.tianduan.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Role extends Model {

    //权限名
    public static final String COL_ROLENAME = "name";
    //权限描述
    public static final String COL_DESCRIPTION = "description";

    @Column(name = COL_ROLENAME, nullable = false, unique = true)
    private String name;
    @Column(name = COL_DESCRIPTION)
    private String description;

    public Role() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
