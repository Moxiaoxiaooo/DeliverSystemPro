package com.LinYuda.www.service;

import com.LinYuda.www.dao.productMenu.ProductMenuDao;
import com.LinYuda.www.po.ProductMenu;

public class ProductMenuService {
    /**
     * 定义各种类型的菜品对应的编号
     */
    public static final int ALL = 0;
    public static final int YUE = 1;
    public static final int CHUAN = 2;
    public static final int XIANG = 3;

    /**
     * 获取菜单类型的名字
     * 将数字类型转换为字符串类型
     *
     * @param type 要查询名字的类型常量
     * @return 对应的名字
     */
    public static String getTypeName(int type) {
        switch (type) {
            case YUE:
                return "粤菜";
            case CHUAN:
                return "川菜";
            case XIANG:
                return "湘菜";
            default:
                return null;
        }
    }

    /**
     * 用于查询菜品的方法
     * 传入要查询的菜品名字，返回包含的Menu数组
     * 如果查不到则返回null
     *
     * @param searchName 要搜索的名字
     * @return 包含查询结果的所有菜品
     */
    public ProductMenu[] getSearchMenu(String searchName) {

        ProductMenuDao productMenuDao = new ProductMenuDao();
        ProductMenu[] productMenus = productMenuDao.getMenu(ALL);
        ProductMenu[] returnValues = null;
        boolean[] check = new boolean[productMenus.length];

        int size = 0;
        for (int i = 0; i < productMenus.length; i++) {
            if (productMenus[i].getMealName().contains(searchName)) {
                size++;
                check[i] = true;
            } else {
                check[i] = false;
            }
        }

        returnValues = new ProductMenu[size];

        for (int i = 0; i < returnValues.length; i++) {
            returnValues[i] = new ProductMenu();
        }

        int count = 0;
        for (int i = 0; i < productMenus.length; i++) {

            if (check[i]) {
                returnValues[count] = productMenus[i];
                count++;
            }
        }
        if (size != 0) {
            return returnValues;
        } else {
            return null;
        }
    }


    /**
     * 通过类型常量获取不同类型商品方法
     * 所有商品ALL = 0;
     * 粤菜YUE = 1;
     * 川菜CHUAN = 2;
     * 湘菜XIANG = 3;
     *
     * @param type 获取商品对应类型的常量
     * @return 该类型下的商品
     */
    public ProductMenu[] getTypeMenu(int type) {
        return new ProductMenuDao().getMenu(type);
    }


    /**
     * 通过多个id查询到多个商品
     *
     * @param ids 要查询的id数组
     * @return 每个id对应的商品组成的商品数组
     */
    public ProductMenu[] getMenusById(long[] ids) {
        ProductMenu[] returnValues = new ProductMenu[ids.length];
        ProductMenuDao productMenuDao = new ProductMenuDao();
        for (int i = 0; i < ids.length; i++) {
            returnValues[i] = productMenuDao.getMenuById(ids[i]);
        }
        return returnValues;
    }

    /**
     * 通过一个id获取一个商品对象
     * 本质上是调用多个id查询多个商品对象的方法
     *
     * @param id 要查询的id
     * @return 该id对应的商品对象
     */
    public ProductMenu getMenuById(long id) {
        ProductMenu returnValue = null;
        long[] ids = new long[1];
        ids[0] = id;
        returnValue = (getMenusById(ids))[0];
        return returnValue;
    }

    /**
     * 获取所有商品的方法
     *
     * @return 所有商品构成的数组
     */
    public ProductMenu[] getAllMenus() {
        return new ProductMenuDao().getMenu(ALL);
    }

    /**
     * 通过商品id设置商品的新名字
     *
     * @param menuId      要被更新名字的商品
     * @param newMealName 要设置的新名字
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuMealNameByMenuId(long menuId, String newMealName) {
        return new ProductMenuDao().updateMenuMealNameByMenuId(menuId, newMealName);
    }

    /**
     * 通过商品id设置商品的新价格
     *
     * @param menuId   要被更新价格的商品
     * @param newPrice 要设置的新价格
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuPriceByMenuId(long menuId, double newPrice) {
        return new ProductMenuDao().updateMenuPriceByMenuId(menuId, newPrice);
    }


    /**
     * 通过商品id设置商品的新厨师id
     *
     * @param menuId    要被更新厨师id的商品
     * @param newCookNo 要设置的新厨师id
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuCookNoByMenuId(long menuId, long newCookNo) {
        return new ProductMenuDao().updateMenuCookNoByMenuId(menuId, newCookNo);
    }


    /**
     * 通过商品id设置商品的新类型
     *
     * @param menuId      要被更新厨师id的商品
     * @param newMealType 要设置的新类型
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuMealTypeByMenuId(long menuId, String newMealType) {
        return new ProductMenuDao().updateMenuMealTypeByMenuId(menuId, newMealType);
    }

    /**
     * 通过商品id设置商品的新数量
     *
     * @param menuId    要被更新数量的商品
     * @param newAmount 要设置的新数量
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuAmountByMenuId(long menuId, long newAmount) {
        return new ProductMenuDao().updateMenuAmountByMenuId(menuId, newAmount);
    }


    /**
     * 通过商品id设置商品的新窗口编号
     *
     * @param menuId      要被更新窗口编号的的商品
     * @param newWindowNo 要设置的新窗口编号
     * @return 设置结果：成功为true，失败为false
     */
    public boolean updateMenuWindowNoByMenuId(long menuId, int newWindowNo) {
        return new ProductMenuDao().updateMenuWindowNoByMenuId(menuId, newWindowNo);
    }


    /**
     * 通过商品id删除商品
     *
     * @param menuId 要删除的商品id
     * @return 删除结果：成功为true，失败为false
     */
    public boolean deleteMenuByMenuId(long menuId) {
        return new ProductMenuDao().deleteMenuByMenuId(menuId);
    }

    /**
     * 添加新商品的方法
     * 传入的参数是一个不带id的ProductMenu对象（id不需要手动添加）
     *
     * @param productMenu 要添加的商品对象
     * @return 添加结果：成功为true失败为false
     */
    public boolean addNewMenu(ProductMenu productMenu) {
        return new ProductMenuDao().addNewMenu(productMenu);
    }

}
