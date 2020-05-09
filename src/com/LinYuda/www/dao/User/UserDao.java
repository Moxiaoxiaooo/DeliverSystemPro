package com.LinYuda.www.dao.User;

import com.LinYuda.www.po.User;
import com.LinYuda.www.uitl.JDBCUtil.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 普通用户dao
 */
public class UserDao {
    /**
     * 用户登录的实现
     * 在使用该dao之前
     * 需要先调用setUserId,setUserPermissionLevel方法以设置用户对象的等级与id
     *
     * @param user 要登录的用户
     * @return 登录结果：成功为true，失败为false
     */
    public boolean userLogin(User user) {
        //确定user是否设置id和permissionLevel，若没设置则返回false
        if (user.getPermissionLevel() == -1 || user.getId() == -1 || user.getUserAccount() == null || user.getUserPassword() == null) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String account = user.getUserAccount();
        String password = user.getUserPassword();
        boolean returnValue = false;

        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_user where user_account= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                returnValue = true;
            } else {
                returnValue = false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
            return returnValue;
        }
    }

    /**
     * 用户注册的实现
     * 传入的用户是一个不完整的User对象（不包含id和其更进一步的信息）
     *
     * @param user 要注册用户的信息
     * @return 注册结果
     */
    public boolean userRegister(User user) {
        //确定注册需要的信息是否都存在
        if (user.getPermissionLevel() == -1 || user.getUserAccount() == null || user.getUserPassword() == null) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean returnValue = false;
        int rows = 0;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into t_user (user_account, user_password, permission_level) values(?,?,?) ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserAccount());
            preparedStatement.setString(2, user.getUserPassword());
            preparedStatement.setInt(3, user.getPermissionLevel());
            rows = preparedStatement.executeUpdate();

            //校验注册
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
     * 升级用户密码的方法
     * 同样的传入的user需要是一个完整的对象
     *
     * @param user 要修改的user对象
     * @return 修改结果
     */
    public boolean updateUserPassword(User user, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean returnValue = false;
        long id = user.getId();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "update t_user set user_password = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            int rows = -1;
            rows = preparedStatement.executeUpdate();

            if (rows == 1) {
                returnValue = true;
            } else {
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
     * 实现设置登录时候用户对象的PermissionLevel的方法
     * 此时的用户对象仅有 账号 和 密码
     *
     * @param user 要被设置的用户对象
     * @return 设置的结果
     */
    public boolean setUserPermissionLevel(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean returnValue = false;
        String account = user.getUserAccount();
        String password = user.getUserPassword();
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_user where user_account = ? and user_password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            //处理查询结果集
            if (resultSet.next()) {
                int permissionLevel = resultSet.getInt("permission_level");
                user.setPermissionLevel(permissionLevel);
                returnValue = true;
            } else {
                returnValue = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
            return returnValue;
        }
    }

    /**
     * 实现设置登录时候用户对象的id的方法
     * 此时的用户对象仅有 账号 和 密码
     *
     * @param user 要被设置的用户对象
     * @return 设置结果
     */
    public boolean setUserId(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean returnValue = false;
        String account = user.getUserAccount();
        String password = user.getUserPassword();

        //要是该用户无密码，则直接不获取返回false
        if (password == null) {
            return false;
        }
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_user where user_account = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            resultSet = preparedStatement.executeQuery();

            //处理查询结果集
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                user.setId(id);
                returnValue = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
            return returnValue;
        }
    }

    /**
     * 通过尝试获取user对象在表格中的id，如果能获得则说明帐号已经存在
     * 如果能登录说明帐号已存在
     * 否则不存在
     *
     * @param user 要校验的user对象
     * @return 检查的结果，存在返回true，不存在返回false
     */
    public boolean checkUserIsExist(User user) {
        return (new UserDao().setUserId(user));
    }
}
