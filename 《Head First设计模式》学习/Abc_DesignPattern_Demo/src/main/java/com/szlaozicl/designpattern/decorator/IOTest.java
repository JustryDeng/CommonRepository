package com.szlaozicl.designpattern.decorator;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 对java.io包下的I/O进行自定义装饰者 测试
 * <p>
 * 说明: java.io包下的I/O就大量用到了 装饰者模式，
 * 这里自己实现一个 InputStream的装饰者 以作练习。
 *
 * @author JustryDeng
 * @date 2019/12/3 12:54
 */
@SuppressWarnings("all")
public class IOTest {

    /** 函数入口 */
    public static void main(String[] args) throws IOException {
        InputStream in = new LowerCaseInputStream(new ByteArrayInputStream("ABCDEFG".getBytes()));
        int c;
        while ((c = in.read()) > 0) {
            // 输出结果为: abcdefg
            System.out.print((char) c);
        }
    }

}

/**
 * 自定义InputStream的装饰者
 *
 * @author JustryDeng
 * @date 2019/12/3 13:17
 */
@SuppressWarnings("all")
class LowerCaseInputStream extends FilterInputStream {

    public LowerCaseInputStream(InputStream in) {
        super(in);
    }

    /**
     * 从此输入流中读取下一个数据字节。返回一个 0 到 255 范围内的 int 字节值。
     * 如果因为已经到达流末尾而没有字节可用，则返回 -1。
     */
    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == -1) {
            return c;
        }
        return Character.toLowerCase((char) c);
    }


    /**
     * 从此输入流中将 len 个字节的数据读入到这个 给定的byte数组中。
     */
    @Override
    public int read(byte[] b, int offset, int len) throws IOException {
        int result = super.read(b, offset, len);
        // 注: len为允许一次读取的最大字节数， 而result为该次实际读取的字节数, 所以得以result为准
        // 注: 数据结果存储在b中，只需要将b里面的大写字母转换为小写即可
        for (int i = offset; i < offset + result; i++) {
            b[i] = (byte) Character.toLowerCase((char) b[i]);
        }
        return result;
    }
}
