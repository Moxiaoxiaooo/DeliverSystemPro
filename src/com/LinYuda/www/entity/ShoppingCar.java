package com.LinYuda.www.entity;

import com.LinYuda.www.po.ProductMenu;

import java.util.ArrayList;
import java.util.List;


/**
 * 购物车对象，不在数据库里面，作为一个临时对象被使用
 * 内置于NormalUser
 */
public class ShoppingCar {

    private double totalPrice = 0;
    private int amount = 0;
    private ArrayList<ProductMenu> selectedMeals = new ArrayList<>();

    public ShoppingCar() {
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public List<ProductMenu> getSelectedMeals() {
        return selectedMeals;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
