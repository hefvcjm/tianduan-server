package com.tianduan.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Engineer extends Model {

    private static final long SERIALVERSIONUID = 4L;

    //用户
    public static final String COL_USER = "user";
    //工程师编号
    public static final String COL_CODE = "code";

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = COL_USER, referencedColumnName = User.COL_PRIMARYKEY, unique = true)
    private User user;
    @Column(name = COL_CODE, unique = true, nullable = false)
    private String code;
    @OneToMany(mappedBy = Maintain.COL_ENGINEER)
    private Set<Maintain> maintains;

    public Engineer() {
    }

//    public Engineer(long id) {
//        super(id);
//    }

    public Engineer(User user, String code) {
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

    public Set<Maintain> getMaintains() {
        return maintains;
    }

    public void setMaintains(Set<Maintain> maintains) {
        this.maintains = maintains;
    }
}
