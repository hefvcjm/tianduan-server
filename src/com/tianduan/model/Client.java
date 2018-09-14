package com.tianduan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Client extends Model {

    private static final long SERIALVERSIONUID = 5L;

    //用户
    public static final String COL_USER = "user";
    //账号
    public static final String COL_CODE = "code";

    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = COL_USER, referencedColumnName = User.COL_PRIMARYKEY, unique = true)
    private User user;
    @Column(name = COL_CODE, unique = true, nullable = false)
    private String code;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = Repair.COL_CLIENT)
    private Set<Repair> repairs;

    public Client() {
    }

//    public Client(long id) {
//        super(id);
//    }

    public Client(User user, String code) {
        this.user = user;
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Repair> getRepairs() {
        return repairs;
    }

    public void setRepairs(Set<Repair> repairs) {
        this.repairs = repairs;
    }
}
