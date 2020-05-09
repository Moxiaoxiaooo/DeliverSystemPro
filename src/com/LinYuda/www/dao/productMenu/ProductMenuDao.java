package com.LinYuda.www.dao.productMenu;

import com.LinYuda.www.entity.ShoppingCar;
import com.LinYuda.www.po.ProductMenu;
import com.LinYuda.www.po.User;
import com.LinYuda.www.service.ProductMenuService;
import com.LinYuda.www.uitl.JDBCUtil.JDBCUtil;
import sun.dc.pr.PRError;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

/**
 * 商品的dao类
 */
public class ProductMenuDao {

    /**
     * 获取数据库中的菜单数据
     *
     * @param type 菜品类型，Menu类中有对应的常量可以调用
     * @return 查询的Menu结果集
     */
    public ProductMenu[] getMenu(int type) {

        ProductMenu[] returnValues = null;
        String typeName = ProductMenuService.getTypeName(type);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = null;
        try {

            connection = JDBCUtil.getConnection();
            statement = connection.createStatement();

            if (typeName != null) {
                sql = "select * from t_menu where meal_type = '" + typeName + "'";
            } else {
                sql = "select * from t_menu";
            }
            resultSet = statement.executeQuery(sql);

            int size = 0;
            while (resultSet.next()) {
                size++;
            }
            returnValues = new ProductMenu[size];
            for (int i = 0; i < size; i++) {
                returnValues[i] = new ProductMenu();
            }

            resultSet = statement.executeQuery(sql);
            int i = 0;
            while (resultSet.next()) {

                returnValues[i].setId(resultSet.getLong("id"));
                returnValues[i].setMealName(resultSet.getString("meal_name"));
                returnValues[i].setPrice(resultSet.getDouble("price"));
                returnValues[i].setCookNo(resultSet.getLong("cook_no"));
                returnValues[i].setAmount(resultSet.getLong("amount"));
                returnValues[i].setWindowNo(resultSet.getInt("window_no"));
                returnValues[i].setMealType(resultSet.getString("meal_type"));

                i++;
            }
            return returnValues;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, statement, resultSet);
            return returnValues;
        }

    }


    /**
     * 通过id获取单个商品的对象
     *
     * @param id 要查询的id
     * @return 该id对应的对象
     */
    public ProductMenu getMenuById(long id) {
        ProductMenu returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_menu where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnValue = new ProductMenu();
                returnValue.setId(id);
                returnValue.setMealName(resultSet.getString("meal_name"));
                returnValue.setPrice(resultSet.getDouble("price"));
                returnValue.setCookNo(resultSet.getLong("cook_no"));
                returnValue.setMealType(resultSet.getString("meal_type"));
                returnValue.setAmount(resultSet.getLong("amount"));
                returnValue.setWindowNo(resultSet.getInt("window_no"));
            } else {
                returnValue = null;
            }
            return returnValue;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 用户下单方法，修改t_menu中的商品数量，不修改t_order中的数据
     *
     * @param shoppingCar 要下单的购物车
     * @return t_menu修改结果：成功为true，失败为false
     */
    public boolean takeOrder(ShoppingCar shoppingCar) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<ProductMenu> selectedMeals = shoppingCar.getSelectedMeals();
        int rows = 0;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set amount = ? where id = ?";
            Iterator<ProductMenu> iterator = selectedMeals.iterator();
            while (iterator.hasNext()) {
                ProductMenu temp = iterator.next();
                Long id = temp.getId();
                Long amount = temp.getAmount() - 1;

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, amount);
                preparedStatement.setLong(2, id);
                rows += preparedStatement.executeUpdate();

            }
            if (rows != selectedMeals.size()) {
                connection.rollback();
                return false;
            } else {
                connection.commit();
                returnValue = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过厨师id获取该id下管理的商品
     *
     * @param cookId 要查询的厨师id
     * @return id对应的商品数组
     */
    public ProductMenu[] getCookMenuByCookId(long cookId) {
        ProductMenu[] returnValues = null;
        int size = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtil.getConnection();
            String sql = "select * from t_menu where cook_no = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cookId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                size++;
            }
            returnValues = new ProductMenu[size];
            for (int i = 0; i < size; i++) {
                returnValues[i] = new ProductMenu();
            }

            resultSet = preparedStatement.executeQuery();
            for (int i = 0; resultSet.next(); i++) {
                returnValues[i].setId(resultSet.getLong("id"));
                returnValues[i].setMealName(resultSet.getString("meal_name"));
                returnValues[i].setPrice(resultSet.getDouble("price"));
                returnValues[i].setCookNo(resultSet.getLong("cook_no"));
                returnValues[i].setMealType(resultSet.getString("meal_type"));
                returnValues[i].setAmount(resultSet.getLong("amount"));
                returnValues[i].setWindowNo(resultSet.getInt("window_no"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }
        return returnValues;

    }


    /**
     * 通过商品id设置商品的新名字
     *
     * @param menuId      要被更新名字的商品
     * @param newMealName 要设置的新名字
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuMealNameByMenuId(long menuId, String newMealName) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set meal_name = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newMealName);
            preparedStatement.setLong(2, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过商品id设置商品的新价格
     *
     * @param menuId   要被更新价格的商品
     * @param newPrice 要设置的新价格
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuPriceByMenuId(long menuId, double newPrice) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set price = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setLong(2, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过商品id设置商品的新厨师id
     *
     * @param menuId    要被更新厨师id的商品
     * @param newCookNo 要设置的新厨师id
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuCookNoByMenuId(long menuId, long newCookNo) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set cook_no=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, newCookNo);
            preparedStatement.setLong(2, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过商品id设置商品的新类型
     *
     * @param menuId      要被更新厨师id的商品
     * @param newMealType 要设置的新类型
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuMealTypeByMenuId(long menuId, String newMealType) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set meal_type=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newMealType);
            preparedStatement.setLong(2, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过商品id设置商品的新数量
     *
     * @param menuId    要被更新数量的商品
     * @param newAmount 要设置的新数量
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuAmountByMenuId(long menuId, long newAmount) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set amount=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, newAmount);
            preparedStatement.setLong(2, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过商品id设置商品的新窗口编号
     *
     * @param menuId      要被更新窗口编号的的商品
     * @param newWindowNo 要设置的新窗口编号
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuWindowNoByMenuId(long menuId, int newWindowNo) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "update t_menu set window_no=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newWindowNo);
            preparedStatement.setLong(2, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }

        return returnValue;
    }


    /**
     * 通过商品id删除商品
     *
     * @param menuId 要删除的商品id
     * @return 删除结果：成功为true，失败为false
     */
    public boolean deleteMenuByMenuId(long menuId) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "delete from t_menu where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, menuId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(connection, preparedStatement);
        }
        return returnValue;
    }


    /**
     * 添加新商品的方法
     * 传入的参数是一个不带id的ProductMenu对象（id不需要手动添加）
     *
     * @param productMenu 要添加的商品对象
     * @return 添加结果：成功为true失败为false
     */
    public boolean addNewMenu(ProductMenu productMenu) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into t_menu(meal_name,price,cook_no,meal_type,amount,window_no) values (?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productMenu.getMealName());
            preparedStatement.setDouble(2, productMenu.getPrice());
            preparedStatement.setLong(3, productMenu.getCookNo());
            preparedStatement.setString(4, productMenu.getMealType());
            preparedStatement.setLong(5, productMenu.getAmount());
            preparedStatement.setInt(6, productMenu.getWindowNo());

            int rows = preparedStatement.executeUpdate();
            if (rows == 1) {
                connection.commit();
                returnValue = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

}
