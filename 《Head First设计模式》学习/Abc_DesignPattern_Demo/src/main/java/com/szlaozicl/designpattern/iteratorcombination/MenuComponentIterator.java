package com.szlaozicl.designpattern.iteratorcombination;

import com.szlaozicl.designpattern.iteratorcombination.impl.Menu;

import java.util.Iterator;
import java.util.Stack;

/**
 * MenuComponent专用迭代器
 *
 * @author JustryDeng
 * @date 2020/3/23 15:30:55
 */
public class MenuComponentIterator implements Iterator<MenuComponent> {

    Stack<Iterator<MenuComponent>> stack = new Stack<>();

    public MenuComponentIterator(Iterator<MenuComponent> iterator) {
        if (iterator != null) {
            this.stack.push(iterator);
        }
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty()) {
            return false;
        }
        Iterator<MenuComponent> iterator = stack.peek();
        if (iterator.hasNext()) {
            return true;
        } else {
            stack.pop();
            return hasNext();
        }
    }

    @Override
    public MenuComponent next() {
        return next(false);
    }

    /**
     * 获取下一项
     *
     * @param onlyLeafNode
     *            是否只获取叶节点所代表的元素项
     *            true  - 只获取叶节点所代表的元素项
     *            false - 获取树中的所有节点(叶节点 + 子节点 + 根节点)所代表的元素项
     * @return  菜单组件项
     * @date 2020/3/25 0:06:30
     */
    private MenuComponent next(boolean onlyLeafNode) {
        if (!hasNext()) {
            return null;
        }
        // 到这里,hasNext()必定为true;
        Iterator<MenuComponent> iterator = stack.peek();
        // 根据中hasNext()中的逻辑，这里iterator.next()一定能取得元素
        MenuComponent menuComponent = iterator.next();
        if (menuComponent instanceof Menu) {
            Iterator<MenuComponent> tmpIterator = menuComponent.createIterator();
            stack.push(tmpIterator);
            /*
             * 如果走 return next();的话,
             *     那么 => 会遍历所有的MenuItem叶节点(排除根节点 和 中间的Menu子节点)
             *
             * 如果不走return next();注释掉的话,
             *     那么 => 会遍历所有的节点(MenuItem叶节点 + Menu子节点 + Menu根节点)
             */
            if (onlyLeafNode) {
                return next();
            }
        }
        return menuComponent;
    }
}
