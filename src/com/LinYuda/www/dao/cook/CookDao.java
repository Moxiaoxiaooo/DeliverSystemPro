package com.LinYuda.www.dao.cook;

import com.LinYuda.www.po.Cook;
import com.LinYuda.www.uitl.JDBCUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 厨师用户的dao
 */
public class CookDao {

    /**
     * 普通用户的注册方法，用于在调用User的注册方法之后使用
     *
     * @param cook 要注册的厨师
     * @return 注册结果：成功为true，失败为false
     */
    public boolean cookRegister(Cook cook) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into t_cook (id, cook_name, superior_id, contact, window_no) values (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cook.getId());
            preparedStatement.setString(2, cook.getCookName());
            preparedStatement.setLong(3, cook.getSuperiorId());
            preparedStatement.setString(4, cook.getContact());
            preparedStatement.setInt(5, cook.getWindowNo());
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
     * 通过用户id查询厨师对象的方法
     *
     * @param userId 要查询的用户id
     * @return 该id对应的厨师对象，如果没有则返回null
     */
    public Cook getCookByUserId(long userId) {
        Cook returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_cook where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnValue = new Cook();
                returnValue.setId(resultSet.getLong("id"));
                returnValue.setWindowNo(resultSet.getInt("window_no"));
                returnValue.setSuperiorId(resultSet.getLong("superior_id"));
                returnValue.setContact(resultSet.getString("contact"));
                returnValue.setCookName(resultSet.getString("cook_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }

        return returnValue;
    }
}
