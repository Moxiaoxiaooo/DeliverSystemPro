package com.LinYuda.www.po;

/**
 * 数据库中：t_cook对应的实体类
 */
public class Cook {
    private long id;
    private String cookName;
    private long superiorId;
    private String contact;
    private int windowNo;

    public Cook() {
    }

    public Cook(String cookName, String contact) {
        this.cookName = cookName;
        this.contact = contact;
    }

    public Cook(long id, String cookName, long superiorId, String contact, int windowNo) {
        this.id = id;
        this.cookName = cookName;
        this.superiorId = superiorId;
        this.contact = contact;
        this.windowNo = windowNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public long getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(long superiorId) {
        this.superiorId = superiorId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getWindowNo() {
        return windowNo;
    }

    public void setWindowNo(int windowNo) {
        this.windowNo = windowNo;
    }
}
