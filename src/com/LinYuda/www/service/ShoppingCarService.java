package com.LinYuda.www.service;

import com.LinYuda.www.dao.productMenu.ProductMenuDao;
import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.ProductMenu;

import java.util.Iterator;
import java.util.List;

public class ShoppingCarService {

    /**
     * 从购物车中移除商品
     *
     * @param meal 要移除的商品
     */
    public void removeFromShoppingCar(ProductMenu meal, ShoppingCar shoppingCar) {
        //通过迭代器删除对象（直接用ArrayList的remove有bug）
        if (meal != null) {
            List<ProductMenu> selectedMeals = shoppingCar.getSelectedMeals();
            Iterator<ProductMenu> iterator = selectedMeals.iterator();
            while (iterator.hasNext()) {
                ProductMenu temp = iterator.next();
                if (temp.equals(meal)) {
                    iterator.remove();
                    break;
                }
            }
            shoppingCar.setTotalPrice(shoppingCar.getTotalPrice() - meal.getPrice());
            shoppingCar.setAmount(shoppingCar.getAmount() - 1);
        }
    }

    /**
     * 添加选中的商品到购物车
     *
     * @param meal 要添加的商品
     */
    public void addInShoppingCar(ProductMenu meal, ShoppingCar shoppingCar) {
        if (meal != null) {
            shoppingCar.getSelectedMeals().add(meal);
            shoppingCar.setTotalPrice(shoppingCar.getTotalPrice() + meal.getPrice());
            shoppingCar.setAmount(shoppingCar.getAmount() + 1);
        }
    }

    /**
     * 清空购物车的方法，能够清空所有加入购物车的商品
     * 并重置记录的总价和总数
     *
     * @param shoppingCar 要被清空的购物车
     */
    public void removeAllFromShoppingCar(ShoppingCar shoppingCar) {
        shoppingCar.getSelectedMeals().clear();
        shoppingCar.setAmount(0);
        shoppingCar.setTotalPrice(0);
    }


    /**
     * 通过商品的id删除购物车中的商品
     *
     * @param mealId      要删除商品的id
     * @param shoppingCar 商品所在购物车
     * @return 删除结果：成功为true失败为false
     */
    public boolean removeFromShoppingCarByMealId(long mealId, ShoppingCar shoppingCar) {
        boolean returnValue = false;
        ProductMenuDao productMenuDao = new ProductMenuDao();
        if (mealId != 0 && shoppingCar != null) {
            ProductMenu productMenu = productMenuDao.getMenuById(mealId);
            removeFromShoppingCar(productMenu, shoppingCar);
            returnValue = true;
        }
        return returnValue;
    }

}
