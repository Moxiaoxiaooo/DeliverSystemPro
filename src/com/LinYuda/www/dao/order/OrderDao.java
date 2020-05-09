package com.LinYuda.www.dao.order;

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

/**
 * 订单处理的dao
 */
public class OrderDao {

    /**
     * 完成订单的方法，即将status设置为1
     *
     * @param orderId 要被完成的订单的id
     * @return 订单处理结果：成功设置返回true，设置失败返回false
     */
    public boolean finishOrder(long orderId) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String updateSql = "update t_order set status = 1 where id = ? ";
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setLong(1, orderId);

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
     * 用户下单方法，只会将数据插入到t_order中，不改变t_menu中的字段
     *
     * @param user        下单的用户
     * @param shoppingCar 下单用户的购物车
     * @return 下单结果，成功为true失败为false
     */
    public boolean takeOrder(NormalUser user, ShoppingCar shoppingCar) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<ProductMenu> selectedMeals = shoppingCar.getSelectedMeals();
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            Iterator<ProductMenu> iterator = selectedMeals.iterator();
            int rows = 0;
            while (iterator.hasNext()) {
                String date = new SimpleDateFormat("MM-dd hh:mm").format(new Date());
                String insertOrder = "insert into t_order(order_meal_no, order_meal_amount, order_person_id, order_time, send_time, status) values (?,?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(insertOrder);

                ProductMenu meals = iterator.next();
                preparedStatement.setLong(1, meals.getId());
                preparedStatement.setInt(2, 1);
                preparedStatement.setLong(3, user.getId());
                preparedStatement.setString(4, date);
                preparedStatement.setString(5, date);
                preparedStatement.setInt(6, 0);
                rows += preparedStatement.executeUpdate();
            }
            if (rows == selectedMeals.size()) {
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
     * 获取历史记录订单
     * !!!!!!!!!!!!!!现在还没修改其toString方法，因为外联一张Menu表
     *
     * @param user 想要获取的用户对象
     * @return 订单数组
     */
    public Order[] getUserHistoryOrder(User user) {
        Order[] returnValues = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int size = 0;
        try {
            connection = JDBCUtil.getConnection();
            String selectSql = "select * from t_order where order_person_id = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(1, user.getId());

            //执行sql语句以获取长度，创建数组
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                size++;
            }
            returnValues = new Order[size];
            for (int i = 0; i < size; i++) {
                returnValues[i] = new Order();
            }

            //执行sql语句以获取对象并存入数组
            resultSet = preparedStatement.executeQuery();
            for (int i = 0; i < size && resultSet.next(); i++) {

                returnValues[i].setId(resultSet.getLong("id"));
                returnValues[i].setOrderMealNo(resultSet.getLong("order_meal_no"));
                returnValues[i].setOrderMealAmount(resultSet.getInt("order_meal_amount"));
                returnValues[i].setOrderPersonId(resultSet.getLong("order_person_id"));
                returnValues[i].setOrderTime(resultSet.getString("order_time"));
                returnValues[i].setSendTime(resultSet.getString("send_time"));
                returnValues[i].setStatus(resultSet.getInt("status"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
            return returnValues;
        }


    }


    /**
     * 通过商品id获取订单信息
     *
     * @param mealId 商品的id
     * @return 该id对应的所有订单，包括完成与未完成
     */
    public Order[] getCookOrdersByMealId(long mealId) {
        Order[] returnValues = null;
        int size = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String selectSql = "select * from t_order where order_meal_no = ?";
            preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(1, mealId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                size++;
            }
            returnValues = new Order[size];
            for (int i = 0; i < size; i++) {
                returnValues[i] = new Order();
            }

            //循环获取基本信息与额外信息
            resultSet = preparedStatement.executeQuery();
            for (int i = 0; resultSet.next(); i++) {

                returnValues[i].setId(resultSet.getLong("id"));
                returnValues[i].setOrderMealAmount(resultSet.getInt("order_meal_amount"));
                returnValues[i].setOrderMealNo(resultSet.getLong("order_meal_no"));
                returnValues[i].setOrderPersonId(resultSet.getLong("order_person_id"));
                returnValues[i].setOrderTime(resultSet.getString("order_time"));
                returnValues[i].setSendTime(resultSet.getString("send_time"));
                returnValues[i].setStatus(resultSet.getInt("status"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
            return returnValues;
        }


    }


    /**
     * 通过id删除order表中的数据
     *
     * @param mealId 商品数据
     * @return 删除结果
     */
    public boolean deleteMenuFromOrderByMealId(long mealId) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "delete from t_order where order_meal_no = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, mealId);
            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }
        return (rows >= 0);
    }
}