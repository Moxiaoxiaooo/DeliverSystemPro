package com.LinYuda.www.service;

import com.LinYuda.www.dao.NormalUser.NormalUserDao;
import com.LinYuda.www.dao.User.UserDao;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.User;

public class NormalUserService {
    /**
     * 通用用户注册的方法，需要传入一个普通用户对象不带id与通用用户对象不带id
     *
     * @param user       通用用户对象，包括基本的用户数据
     * @param normalUser 普通用户对象，包括用户更为详细的资料数据
     * @return 注册的结果：成功为true，失败为false
     */
    public boolean normalUserRegister(User user, NormalUser normalUser) {
        boolean returnValue = false;
        UserDao userDao = new UserDao();
        NormalUserDao normalUserDao = new NormalUserDao();

        //先确定该用户是否存在
        if (userDao.checkUserIsExist(user)) {
            //如果存在，直接返回false注册失败
            return false;
        }

        //注册两个表格的信息，此时user不带id，normalUser不带id
        //先注册通用用户表格的数据
        boolean check1 = userDao.userRegister(user);
        //获取在通用表格的id
        userDao.setUserId(user);
        //再设置普通用户的id
        normalUser.setId(user.getId());
        //注册普通用户的信息
        boolean check2 = normalUserDao.normalUserRegister(normalUser);

        if (check1 && check2) {
            returnValue = true;
        }

        return returnValue;
    }


    /**
     * 通过一个通用用户的id获取其对应的普通用户normalUser对象
     *
     * @param userId 要获取normalUser对象的id
     * @return 该id对应的normalUser对象
     */
    public NormalUser getNormalUserByUserId(long userId) {
        return new NormalUserDao().getNormalUserByUserId(userId);
    }

}

