package com.LinYuda.www.po;

/**
 * t_order对应的实体类
 * 订单实体类
 */
public class Order {
    private long id;
    private long orderMealNo;
    private int orderMealAmount;
    private long orderPersonId;
    private String orderTime;
    private String sendTime;
    private int status;

    public Order() {
    }

    public Order(long id, long orderMealNo, int orderMealAmount, long orderPersonId, String orderTime, String sendTime, int status) {
        this.id = id;
        this.orderMealNo = orderMealNo;
        this.orderMealAmount = orderMealAmount;
        this.orderPersonId = orderPersonId;
        this.orderTime = orderTime;
        this.sendTime = sendTime;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderMealNo() {
        return orderMealNo;
    }

    public void setOrderMealNo(long orderMealNo) {
        this.orderMealNo = orderMealNo;
    }

    public int getOrderMealAmount() {
        return orderMealAmount;
    }

    public void setOrderMealAmount(int orderMealAmount) {
        this.orderMealAmount = orderMealAmount;
    }

    public long getOrderPersonId() {
        return orderPersonId;
    }

    public void setOrderPersonId(long orderPersonId) {
        this.orderPersonId = orderPersonId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderMealNo=" + orderMealNo +
                ", orderMealAmount=" + orderMealAmount +
                ", orderPersonId=" + orderPersonId +
                ", orderTime='" + orderTime + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", status=" + status +
                '}';
    }
}
