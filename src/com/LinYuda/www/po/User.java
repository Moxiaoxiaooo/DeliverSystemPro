package com.LinYuda.www.po;

/**
 * t_user对应的实体类
 * 也就是通用的用户数据表格
 */
public class User {
    private long id = -1;
    private String userAccount = null;
    private String userPassword = null;
    private int permissionLevel = -1;

    public User() {
    }

    public User(String userAccount, String userPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }

    public User(String userAccount, String userPassword, int permissionLevel) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.permissionLevel = permissionLevel;
    }

    public User(long id, String userAccount, String userPassword, int permissionLevel) {
        this.id = id;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.permissionLevel = permissionLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}
