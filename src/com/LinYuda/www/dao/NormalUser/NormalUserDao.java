package com.LinYuda.www.dao.NormalUser;


import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.NormalUser;
import com.LinYuda.www.po.Order;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.po.User;
import com.LinYuda.www.uitl.JDBCUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NormalUserDao {


    /**
     * 普通用户的注册方法，用于在调用User的注册方法之后使用
     * 需要传入一个完整的normalUser对象
     *
     * @param normalUser 要注册的一个完整的普通用户
     * @return 注册的结果：成功为true，失败为false
     */
    public boolean normalUserRegister(NormalUser normalUser) {

        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into t_normal_user (id,user_name,location,contact)values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, normalUser.getId());
            preparedStatement.setString(2, normalUser.getUserName());
            preparedStatement.setString(3, normalUser.getLocation());
            preparedStatement.setString(4, normalUser.getContact());
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
                returnValue = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
            return returnValue;
        }

    }


    /**
     * 通过一个通用用户的id获取其对应的普通用户normalUser对象
     *
     * @param userId 要获取normalUser对象的id
     * @return 该id对应的normalUser对象
     */
    public NormalUser getNormalUserByUserId(long userId) {
        NormalUser returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_normal_user where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnValue = new NormalUser();
                returnValue.setId(userId);
                returnValue.setUserName(resultSet.getString("user_name"));
                returnValue.setLocation(resultSet.getString("location"));
                returnValue.setContact(resultSet.getString("contact"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }


        return returnValue;
    }

}
