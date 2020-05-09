package com.LinYuda.www.po;

import java.util.Objects;

public class ProductMenu implements Comparable {
    private long id = -1;
    private String mealName = null;
    private double price = -1;
    private long cookNo = -1;
    private String mealType = null;
    private long amount = -1;
    private int windowNo = -1;

    public ProductMenu() {
    }

    public ProductMenu(String mealName, double price, long cookNo, String mealType, long amount, int windowNo) {
        this.mealName = mealName;
        this.price = price;
        this.cookNo = cookNo;
        this.mealType = mealType;
        this.amount = amount;
        this.windowNo = windowNo;
    }

    public ProductMenu(long id, String mealName, double price, long cookNo, String mealType, long amount, int windowNo) {
        this.id = id;
        this.mealName = mealName;
        this.price = price;
        this.cookNo = cookNo;
        this.mealType = mealType;
        this.amount = amount;
        this.windowNo = windowNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCookNo() {
        return cookNo;
    }

    public void setCookNo(long cookNo) {
        this.cookNo = cookNo;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getWindowNo() {
        return windowNo;
    }

    public void setWindowNo(int windowNo) {
        this.windowNo = windowNo;
    }

    @Override
    public String toString() {
        return
                "名字：" + mealName +
                        "       价格：" + price +
                        "       厨师编号：" + cookNo +
                        "       商品类型：" + mealType +
                        "       数量：" + amount +
                        "       窗口编号：" + windowNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMenu)) return false;
        ProductMenu that = (ProductMenu) o;
        return getId() == that.getId() &&
                Double.compare(that.getPrice(), getPrice()) == 0 &&
                getCookNo() == that.getCookNo() &&
                getAmount() == that.getAmount() &&
                getWindowNo() == that.getWindowNo() &&
                getMealName().equals(that.getMealName()) &&
                getMealType().equals(that.getMealType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMealName(), getPrice(), getCookNo(), getMealType(), getAmount(), getWindowNo());
    }

    @Override
    public int compareTo(Object o) {
        return this.getMealName().compareTo(((ProductMenu) o).getMealName());
    }
}
