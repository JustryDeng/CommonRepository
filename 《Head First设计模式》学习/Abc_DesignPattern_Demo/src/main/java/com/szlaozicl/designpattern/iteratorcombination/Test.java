package com.szlaozicl.designpattern.iteratorcombination;

import com.szlaozicl.designpattern.iteratorcombination.impl.Menu;
import com.szlaozicl.designpattern.iteratorcombination.impl.MenuItem;

/**
 * 迭代器和组合模式 - 测试
 *
 * @author JustryDeng



 * @date 2020/3/23 17:25:34
 */
public class Test {

    public static void main(String[] args) {
        MenuComponent menuComponent = prepareData();
        /// 测试【MenuComponent打印出所有菜MenuItem】的功能
        menuComponent.print();

        System.err.println("\n ------------- JustryDeng的华丽分割线 ------------- \n");

        /// 测试【MenuComponentIterator迭代出所有菜单Menu 和 所有菜MenuItem】的功能
        MenuComponentIterator menuComponentIterator = new MenuComponentIterator(menuComponent.createIterator());
        while (menuComponentIterator.hasNext()) {
            MenuComponent mc = menuComponentIterator.next();
            if (mc instanceof Menu) {
                System.out.println("MenuComponentIterator迭代到的当前项是 => 【菜单】 => " + mc.getName());
                continue;
            }
            System.out.println("MenuComponentIterator迭代到的当前项是 => 【 菜 】 => " + mc.getName());
        }
    }

    /**
     * 组装数据
     *
     * 提示: 组装后的数据结构，见: <image src="迭代器和组合模式 --- 案例测试数据结构图.jpg" />
     *
     * @return  组装后的数据
     */
    private static MenuComponent prepareData() {
        /// => 所有菜单
        // 总菜单
        Menu totalMenu = new Menu("总菜单", "包含了所有菜、菜单");
        // 湘菜菜单
        Menu huNanProvinceMenu = new Menu("湘菜菜单", "包含了所有湖南的菜、菜单");
        // 川菜菜单
        Menu siChuanProvinceMenu = new Menu("川菜菜单", "包含了所有四川的菜、菜单");
        // 绵阳米粉菜单(隶属于川菜菜单)
        Menu mianYangRiceFlourMenu = new Menu("绵阳米粉菜单(隶属于川菜菜单)", "包含了所有绵阳米粉");

        /// => 所有菜
        MenuItem menuItemOne = new MenuItem("土豆炖洋芋", "你可能不知道土豆和洋芋的区别~", 8.88, true) ;
        MenuItem menuItemTwo = new MenuItem("西红柿炒番茄", "西红柿和番茄很配噢~", 12.5, true) ;
        MenuItem menuItemThree = new MenuItem("砂锅鳙鱼头", "提高智商、增强记忆、补充营养、延缓衰老~", 29, false) ;
        MenuItem menuItemFour = new MenuItem("油辣莴笋", "香脆美味又下饭~", 16.5, true) ;
        MenuItem menuItemFive = new MenuItem("干菜焖肉", "好吃不油腻~", 22.5, false) ;
        MenuItem menuItemSix = new MenuItem("麻婆豆腐", "麻婆豆腐麻婆一点~", 10, true) ;
        MenuItem menuItemSeven = new MenuItem("水煮鱼", "喜欢吃鱼~", 35, false) ;
        MenuItem menuItemEight = new MenuItem("担担面", "看样子很好吃欸~", 15, true) ;
        MenuItem menuItemNine = new MenuItem("红汤米粉", "红汤更美味~", 8, false) ;
        MenuItem menuItemTen = new MenuItem("清汤米粉", "清汤也不错噢~", 8, false) ;
        MenuItem menuItemEleven = new MenuItem("清红汤米粉", "别纠结了，清红汤吧~", 8, false) ;

        /// => 将菜(、菜单)和菜单组装起来
        // 组装总菜单
        totalMenu.add(huNanProvinceMenu);
        totalMenu.add(siChuanProvinceMenu);
        totalMenu.add(menuItemOne);
        totalMenu.add(menuItemTwo);
        // 组装湘菜菜单
        huNanProvinceMenu.add(menuItemThree);
        huNanProvinceMenu.add(menuItemFour);
        huNanProvinceMenu.add(menuItemFive);
        // 组装川菜菜单
        siChuanProvinceMenu.add(mianYangRiceFlourMenu);
        siChuanProvinceMenu.add(menuItemSix);
        siChuanProvinceMenu.add(menuItemSeven);
        siChuanProvinceMenu.add(menuItemEight);
        // 组装绵阳米粉菜单
        mianYangRiceFlourMenu.add(menuItemNine);
        mianYangRiceFlourMenu.add(menuItemTen);
        mianYangRiceFlourMenu.add(menuItemEleven);
        return totalMenu;
    }
}
