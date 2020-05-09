package com.LinYuda.www.service;

import com.LinYuda.www.dao.User.UserDao;
import com.LinYuda.www.dao.cook.CookDao;
import com.LinYuda.www.dao.order.OrderDao;
import com.LinYuda.www.dao.productMenu.ProductMenuDao;
import com.LinYuda.www.po.Cook;
import com.LinYuda.www.po.Order;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.po.User;

public class CookService {
    /**
     * 厨师注册方法，传入的对象均不带id，但会在这里设置其id
     *
     * @param user 要注册的通用对象，不带id
     * @param cook 要注册的厨师对象，不带id
     * @return 注册结果：成功为true，失败为false
     */
    public boolean cookRegister(User user, Cook cook) {
        boolean returnValue = false;
        UserDao userDao = new UserDao();
        CookDao cookDao = new CookDao();
        if (userDao.checkUserIsExist(user)) {
            return false;
        }
        boolean check1 = userDao.userRegister(user);
        userDao.setUserId(user);
        cook.setId(user.getId());
        boolean check2 = cookDao.cookRegister(cook);
        if (check1 && check2) {
            returnValue = true;
        }
        return returnValue;
    }


    /**
     * 通过id获取厨师对象
     *
     * @param userId 要获取的id
     * @return 该id对应的厨师对象, 如果获取不到则为null
     */
    public Cook getCookByUserId(long userId) {
        return new CookDao().getCookByUserId(userId);
    }


    /**
     * 通过厨师id获取该id下管理的商品
     *
     * @param cookId 厨师的id
     * @return 该id下对应的商品
     */
    public ProductMenu[] getCookMenuByCookId(long cookId) {
        return new ProductMenuDao().getCookMenuByCookId(cookId);
    }


    /**
     * 通过厨师id获取该id对应的所有订单消息
     *
     * @param cookId 要查询的厨师id
     * @return 该厨师对应的所有订单
     */
    public Order[] getCookOrderByCookId(long cookId) {

        Order[] returnValue = null;
        int size = 0;

        ProductMenu[] productMenu = getCookMenuByCookId(cookId);
        OrderDao orderDao = new OrderDao();
        Order[][] temp = new Order[productMenu.length][];
        for (int i = 0; i < productMenu.length; i++) {
            temp[i] = orderDao.getCookOrdersByMealId(productMenu[i].getId());
            size += temp[i].length;
        }
        returnValue = new Order[size];

        int position = 0;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                returnValue[position] = temp[i][j];
                position++;
            }
        }
        return returnValue;
    }


    /**
     * 通过订单id修改订单状态为完成
     *
     * @param orderId 订单的id
     * @return 修改结果：成功为true，失败为false
     */
    public boolean finishOrderByOrderId(long orderId) {
        boolean returnValue = false;
        OrderDao orderDao = new OrderDao();
        returnValue = orderDao.finishOrder(orderId);
        return returnValue;
    }
}
