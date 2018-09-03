package com.tianduan.model;


import com.tianduan.base.annotation.ToStringIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends Model {

    private static final long SERIALVERSIONUID = 1L;

    //用户账号名
    public static final String COL_USER = "user";
    //用户姓名
    public static final String COL_NAME = "name";
    //用户手机号
    public static final String COL_PHONE = "phone";
    //密码
    public static final String COL_PASSWORD = "password";
    //性别
    public static final String COL_SEX = "sex";
    //单位
    public static final String COL_COMPANY = "company";
    //地址
    public static final String COL_ADDRESS = "address";
    //头像资源uri
    public static final String COL_PICTURE = "picture";
    //职务
    public static final String COL_DUTY = "duty";
    //注册时间
    public static final String COL_REGISTERTIEM = "registertime";
    //最后一次登录时间
    public static final String COL_LOGINTIME = "logintime";
    //用户类型
    public static final String COL_TYPE = "type";

    public User() {
    }

    @Column(name = COL_USER, unique = true, nullable = false)
    private String user;
    @Column(name = COL_NAME)
    private String name;
    @Column(name = COL_PHONE, nullable = false, unique = true)
    private String phone;
    @Column(name = COL_PASSWORD, nullable = false)
    @ToStringIgnore
    private String password;
    @Column(name = COL_SEX)
    private String sex;
    @Column(name = COL_COMPANY)
    private String company;
    @Column(name = COL_ADDRESS)
    private String address;
    @Column(name = COL_PICTURE)
    private String picture;
    @Column(name = COL_DUTY)
    private String duty;
    @Column(name = COL_REGISTERTIEM, nullable = false)
    private String registertime;
    @Column(name = COL_LOGINTIME)
    private String logintime;
    @Column(name = COL_TYPE, nullable = false)
    private String type;

    public User(String user, String phone, String password, String type) {
        this.user = user;
        this.phone = phone;
        this.password = password;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
