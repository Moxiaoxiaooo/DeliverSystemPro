package com.LinYuda.www.entity;

/**
 * 完整订单对象
 * 包含除了订单表以外的其他必要信息：订单人，位置，时间，商品名字
 * 先通过获取：普通订单，用户，商品
 * 再通过构造方法完成对完整订单的构造
 */
public class FullOrder {
    private long orderId;
    private String mealName;
    private String orderTime;
    private String orderPersonLocation;
    private String orderPersonName;
    private int status;

    public FullOrder() {
    }

    public FullOrder(long orderId, String mealName, String orderTime, String orderPersonLocation, String orderPersonName, int status) {
        this.orderId = orderId;
        this.mealName = mealName;
        this.orderTime = orderTime;
        this.orderPersonLocation = orderPersonLocation;
        this.orderPersonName = orderPersonName;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderPersonLocation() {
        return orderPersonLocation;
    }

    public void setOrderPersonLocation(String orderPersonLocation) {
        this.orderPersonLocation = orderPersonLocation;
    }

    public String getOrderPersonName() {
        return orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName) {
        this.orderPersonName = orderPersonName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
