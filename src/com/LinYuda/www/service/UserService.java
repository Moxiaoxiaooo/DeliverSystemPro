package com.LinYuda.www.service;

import com.LinYuda.www.dao.User.UserDao;
import com.LinYuda.www.po.User;

public class UserService {

    public static final int LOGIN_ERROR = -1;
    public static final int ACCOUNT_NOT_EXIST = -2;
    public static final int PASSWORD_ERROR = -3;

    /**
     * 通用用户登录的方法，传入一个不带id和permissionLevel的user对象
     * 期中有一些常量对应各种状态：
     * LOGIN_ERROR = -1
     * ACCOUNT_NOT_EXIST = -2
     * PASSWORD_MISTAKE = -3
     *
     * @param user 要登录的用户对象
     * @return 错误码或用户等级
     */
    public int userLogin(User user) {
        int returnValue = -1;
        UserDao userDao = new UserDao();

        //设置user对象的id和permissionLevel
        boolean check1 = userDao.setUserId(user);
        boolean check2 = userDao.setUserPermissionLevel(user);

        if (check1 && check2) {
            //获取成功，可以登录
            boolean result = userDao.userLogin(user);
            if (result) {
                //所有信息正确
                returnValue = user.getPermissionLevel();
            } else {
                //密码错误
                returnValue = PASSWORD_ERROR;
            }
        } else {
            //获取失败，没有注册
            returnValue = ACCOUNT_NOT_EXIST;
        }

        return returnValue;
    }

    /**
     * 用户修改密码的方法
     *
     * @param user        要修改密码的用户
     * @param newPassword 要修改的新密码
     * @return 修改的结果：成功true，失败为false
     */
    public boolean updateUserPassword(User user, String newPassword) {
        boolean returnValue;
        UserDao userDao = new UserDao();
        returnValue = userDao.updateUserPassword(user, newPassword);
        return returnValue;
    }

}
