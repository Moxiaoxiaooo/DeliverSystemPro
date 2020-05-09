package com.LinYuda.www.service;

import com.LinYuda.www.dao.order.OrderDao;
import com.LinYuda.www.dao.productMenu.ProductMenuDao;
import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.Order;
import com.LinYuda.www.po.User;

public class OrderService {
    /**
     * 通过user对象获取该对象的所有历史订单
     *
     * @param user 要查询的user对象
     * @return 该user对应的order数组
     */
    public Order[] getHistoryOrders(User user) {
        Order[] returnValues = null;
        OrderDao orderDao = new OrderDao();
        returnValues = orderDao.getUserHistoryOrder(user);
        return returnValues;
    }


    /**
     * 普通用户的下单方法，需要先获取整个normalUser对象，再进行下单
     *
     * @param user        要下单的普通用户
     * @param shoppingCar 用户的购物车
     * @return 下单的结果：成功为true失败为false
     */
    public boolean takeOrder(NormalUser user, ShoppingCar shoppingCar) {
        boolean returnValue = false;
        OrderDao orderDao = new OrderDao();
        ProductMenuDao productMenuDao = new ProductMenuDao();
        ShoppingCarService shoppingCarService = new ShoppingCarService();
        boolean check1 = orderDao.takeOrder(user, shoppingCar);
        boolean check2 = productMenuDao.takeOrder(shoppingCar);
        returnValue = check1 && check2;
        if (returnValue) {
            shoppingCarService.removeAllFromShoppingCar(shoppingCar);
        }
        return returnValue;
    }
}
