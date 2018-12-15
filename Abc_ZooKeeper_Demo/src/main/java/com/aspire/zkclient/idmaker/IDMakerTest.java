package com.aspire.zkclient.idmaker;

/**
 * 测试使用IDMaker
 *
 * @author JustryDeng
 * @date 2018/12/15 11:14
 */
public class IDMakerTest {

    public static void main(String[] args) throws Exception {
        Thread threadOne = new Thread(() -> {
            IDMaker idMaker = new IDMaker("10.8.109.32:2181");
            try {
                idMaker.start();
                for (int i = 0; i < 10; i++) {
                    String id = idMaker.generateId(IDMaker.DeleteNodeWay.DELAY);
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "获取到的ID(字符串)为:" + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    idMaker.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadTwo = new Thread(() -> {
            IDMaker idMaker = new IDMaker("10.8.109.32:2181");
            try {
                idMaker.start();
                for (int i = 0; i < 10; i++) {
                    String id = idMaker.generateId(IDMaker.DeleteNodeWay.DELAY);
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "获取到的ID(字符串)为:" + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    idMaker.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threadOne.start();
        threadTwo.start();
    }

}
