package com.LinYuda.www.po;

/**
 * 数据库中：t_normal_user表
 * 也就是普通学生和老师的实体类
 */
public class NormalUser {
    private long id;
    private String userName;
    private String location;
    private String contact;

    public NormalUser() {
    }

    public NormalUser(String userName, String location, String contact) {
        this.userName = userName;
        this.location = location;
        this.contact = contact;
    }

    public NormalUser(long id, String userName, String location, String contact) {
        this.id = id;
        this.userName = userName;
        this.location = location;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
