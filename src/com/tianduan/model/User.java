package com.tianduan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tianduan.base.annotation.ToStringIgnore;
import com.tianduan.repository.Repository;
import com.tianduan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Entity
public class User extends Model {

    private static final long SERIALVERSIONUID = 1L;

    //用户账号名
    public static final String COL_USER = "username";
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
    //token
    public static final String COL_TOKEN = "token";

    @Column(name = COL_USER, unique = true, nullable = false)
    private String username;
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
    @Column(name = COL_TOKEN, nullable = false)
    private String token;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Role> roles;

    @Transient
    @JsonIgnore
    @ToStringIgnore
    @Autowired
    UserRepository repository;

    public User() {
    }

    public User(long id) {
        User user = repository.findOne(id);
        copy(user);
    }

    public User(String objectId) {
        User user = repository.findByObjectId(objectId);
        copy(user);
    }

    public User(String username, String phone, String password, String type) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.type = type;
    }

    private void copy(User user) {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Column.class) != null) {
                String fieldName = field.getName();
                String getName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                String setName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Method getMethod = getClass().getMethod(getName);
                    Method setMethod = getClass().getMethod(setName);
                    setMethod.invoke(this, getMethod.invoke(user));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
