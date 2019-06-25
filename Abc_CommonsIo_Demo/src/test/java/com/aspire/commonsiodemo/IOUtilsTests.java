package com.aspire.commonsiodemo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试使用 IOUtils
 *
 * @author JustryDeng
 * @date 2019/6/13 19:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("unused")
public class IOUtilsTests {


    /**
     * 通过InputStream，获取BufferedInputStream
     * 通过OutputStream，获取BufferedOutputStream
     * 通过Reader，获取BufferedReader
     * 通过Writer，获取BufferedWriter
     *
     * 注:方法使用方式一样，本人这里只演示 过InputStream，获取BufferedInputStream
     *
     *
     * BufferedInputStream	buffer(InputStream inputStream)
     * BufferedInputStream	buffer(InputStream inputStream, int size)
     *
     * BufferedOutputStream	buffer(OutputStream outputStream)
     * BufferedOutputStream	buffer(OutputStream outputStream, int size)
     *
     * BufferedReader	buffer(Reader reader)
     * BufferedReader	buffer(Reader reader, int size)
     *
     * BufferedWriter	buffer(Writer writer)
     * BufferedWriter	buffer(Writer writer, int size)
     *
     * @date 2019/6/25 10:14
     */
    @Test
    public void testOne() throws IOException {
        InputStream io = FileUtils.openInputStream(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"));
        // 通过InputStream获取BufferedInputStream，如果InputStream本人就是BufferedInputStream的话，
        // 那么直接返回；否者创建BufferedInputStream
        // 如果BufferedInputStream是新建的的话，那么其默认buffer尺寸为8192
        BufferedInputStream bufferedInputStreamOne = IOUtils.buffer(io);
        // 通过InputStream获取BufferedInputStream，如果BufferedInputStream是新建的的话，那么设置此buffer的尺寸
        BufferedInputStream bufferedInputStreamTwo = IOUtils.buffer(io, 1024);

    }

    /**
     * 演示一：通过CharSequence、String，获取InputStream
     * 演示二：通过byte[]，获取String
     * 演示三：通过InputStream、Reader、URL，获取String
     * 演示四：通过InputStream、Reader，获取BufferedInputStream、BufferedReader
     * 演示五：通过InputStream、Reader、String、URI、URL、URLConnection，获取byte[]
     * 演示六：通过InputStream、Reader，获取char[]
     *
     * static InputStream	toInputStream(CharSequence input)
     * static InputStream	toInputStream(CharSequence, Charset)
     * static InputStream	toInputStream(CharSequence input, Charset encoding)
     * static InputStream	toInputStream(CharSequence input, String encoding)
     * static InputStream	toInputStream(String input)
     * static InputStream	toInputStream(String, Charset)
     * static InputStream	toInputStream(String input, Charset encoding)
     * static InputStream	toInputStream(String input, String encoding)
     *
     * static String	toString(byte[] input)
     * static String	toString(byte[] input, String encoding)
     * static String	toString(InputStream input)
     * static String	toString(InputStream, Charset)
     * static String	toString(InputStream input, Charset encoding)
     * static String	toString(InputStream input, String encoding)
     * static String	toString(Reader input)
     * static String	toString(URI uri)
     * static String	toString(URI, Charset)
     * static String	toString(URI uri, Charset encoding)
     * static String	toString(URI uri, String encoding)
     * static String	toString(URL url)
     * static String	toString(URL, Charset)
     * static String	toString(URL url, Charset encoding)
     * static String	toString(URL url, String encoding)
     *
     * static InputStream	toBufferedInputStream(InputStream input)
     * static InputStream	toBufferedInputStream(InputStream input, int size)
     * static BufferedReader	toBufferedReader(Reader reader)
     * static BufferedReader	toBufferedReader(Reader reader, int size)
     *
     * static byte[]	toByteArray(InputStream input)
     * static byte[]	toByteArray(InputStream input, int size)
     * static byte[]	toByteArray(InputStream input, long size)
     * static byte[]	toByteArray(Reader input)
     * static byte[]	toByteArray(Reader, Charset)
     * static byte[]	toByteArray(Reader input, Charset encoding)
     * static byte[]	toByteArray(Reader input, String encoding)
     * static byte[]	toByteArray(String input)
     * static byte[]	String.getBytes()
     * static byte[]	toByteArray(URI uri)
     * static byte[]	toByteArray(URL url)
     * static byte[]	toByteArray(URLConnection urlConn)
     *
     * static char[]	toCharArray(InputStream is)
     * static char[]	toCharArray(InputStream, Charset)
     * static char[]	toCharArray(InputStream is, Charset encoding)
     * static char[]	toCharArray(InputStream is, String encoding)
     * static char[]	toCharArray(Reader input)
     *
     * @date 2019/6/25 10:14
     */
    @Test
    public void testTwo() throws IOException {
        // 演示一：通过CharSequence、String，获取InputStream
        InputStream io = IOUtils.toInputStream("我是一只小小小小鸟~嗷！嗷！", Charset.forName("GBK"));

        // 演示二：通过byte[]，获取String
        byte[] bytesTmp = new byte[]{97, 98, 99, 100};
        String strOne = IOUtils.toString(bytesTmp, "UTF-8");
        System.out.println(strOne);

        // 演示三：通过InputStream、Reader、URL，获取String
        String strTwo = IOUtils.toString(io, Charset.forName("GBK"));
        System.out.println(strTwo);

        // 演示四：通过InputStream、Reader，获取BufferedInputStream、BufferedReader
        InputStream isTwo = FileUtils.openInputStream(FileUtils.getFile("C:\\Users\\JustryDeng\\Desktop\\a.txt"));
        InputStream isTmp = IOUtils.toBufferedInputStream(isTwo);
        System.out.println(isTmp);
        Reader readerOne = new FileReader(("C:\\Users\\JustryDeng\\Desktop\\a.txt"));
        BufferedReader brTmp = IOUtils.toBufferedReader(readerOne);
        System.out.println(brTmp);

        // 演示五：通过InputStream、Reader、String、URI、URL、URLConnection，获取byte[]
        FileInputStream fis = new FileInputStream("C:\\Users\\JustryDeng\\Desktop\\a.txt");
        InputStreamReader isr = new InputStreamReader(fis, "GBK");
        byte[] byteData = IOUtils.toByteArray(isr, Charset.forName("GBK"));
        System.out.println(new String(byteData, Charset.forName("GBK")));

        // 演示六：通过InputStream、Reader，char[]
        InputStream ioTwo = IOUtils.toInputStream("我是一只小小小小鸟~嗷！嗷！", Charset.forName("GBK"));
        char[] charData = IOUtils.toCharArray(ioTwo, Charset.forName("GBK"));
        for (char c : charData) {
            System.out.println(c);
        }
    }

    /**
     * 比较两个流的数据是否相等
     *
     * 相似的方法还有:
     *      boolean contentEquals(InputStream input1, InputStream input2)
     *      boolean contentEquals(Reader input1, Reader input2)
     *      boolean contentEqualsIgnoreEOL(Reader input1, Reader input2)
     * @date 2019/6/25 10:14
     */
    @Test
    public void testThree() throws IOException {
        InputStream isOne = IOUtils.toInputStream("我是一只小小小小鸟~嗷！嗷！", Charset.forName("GBK"));
        InputStream isTwo = IOUtils.toInputStream("我是一只小小小小鸟~嗷！嗷！", Charset.forName("GBK"));
        boolean result = IOUtils.contentEquals(isOne, isTwo);
        System.out.println(result);
    }

    /**
     * 将输入流的数据，拷贝至输出流
     *
     * 相似的方法还有:
     *      copy(InputStream input, OutputStream output)
     *      copy(InputStream input, OutputStream output, int bufferSize)
     *      copy(InputStream, Writer, Charset) instead
     *      copy(InputStream input, Writer output, Charset inputEncoding)
     *      copy(InputStream input, Writer output, String inputEncoding)
     *      copy(Reader, OutputStream, Charset) instead
     *      copy(Reader input, OutputStream output, Charset outputEncoding)
     *      copy(Reader input, OutputStream output, String outputEncoding)
     *      copy(Reader input, Writer output)
     *
     *      copyLarge(InputStream input, OutputStream output)
     *      copyLarge(InputStream input, OutputStream output, byte[] buffer)
     *      copyLarge(InputStream input, OutputStream output, long inputOffset, long length)
     *      copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer)
     *      copyLarge(Reader input, Writer output)
     *      copyLarge(Reader input, Writer output, char[] buffer)
     *      copyLarge(Reader input, Writer output, long inputOffset, long length)
     *      copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer)
     * @date 2019/6/25 10:14
     */
    @Test
    public void testFour() throws IOException {
        InputStream is = IOUtils.toInputStream("我是一只小小小小鸟~嗷！嗷！", Charset.forName("GBK"));
        OutputStream os = FileUtils.openOutputStream(new File("C:\\Users\\JustryDeng\\Desktop\\c.txt"));
        // 复制
         long bytesCount = IOUtils.copy(is, os, 10240);
        // 复制(这个方法其实是对copy(InputStream input, OutputStream output, int bufferSize)的封
        //      装，只是buffer尺寸设置得较大，所以这个方法适合拷贝较大的数据流，比如2G以上)
        // long bytesCount = IOUtils.copyLarge(is, os);
        System.out.println("复制了的字节数：" + bytesCount);
    }

    /**
     * 以行为单元，从输入流中读取数据
     *
     * 相似的方法还有:
     *      lineIterator(InputStream input, Charset encoding)
     *      lineIterator(InputStream input, String encoding)
     *      lineIterator(Reader reader)
     *
     *      readLines(InputStream input)
     *      readLines(InputStream, Charset) instead
     *      readLines(InputStream input, Charset encoding)
     *      readLines(InputStream input, String encoding)
     *      readLines(Reader input)
     *
     * @date 2019/6/25 11:32
     */
    @Test
    public void testFive() throws IOException {
        // 示例lineIterator
        InputStream isOne = IOUtils.toInputStream("我是\n一只\n小小小小\n鸟~\n嗷！嗷！", Charset.forName("UTF-8"));
        LineIterator iterator= IOUtils.lineIterator(isOne, Charset.forName("UTF-8"));
        while (iterator.hasNext()) {
            System.out.println(iterator.nextLine());
        }
        // 示例readLines
        InputStream isTwo = IOUtils.toInputStream("这里的\n山路\n十八弯！", Charset.forName("GBK"));
        List<String> list= IOUtils.readLines(isTwo, Charset.forName("GBK"));
        System.out.println(list);
    }

    /**
     * 从流中读取数据至byte[]
     *
     * 相似的方法还有:
     *      read(InputStream input, byte[] buffer)
     *      read(InputStream input, byte[] buffer, int offset, int length)
     *      read(ReadableByteChannel input, ByteBuffer buffer)
     *      read(Reader input, char[] buffer)
     *      read(Reader input, char[] buffer, int offset, int length)
     *
     *      readFully(InputStream input, byte[] buffer)
     *      readFully(InputStream input, byte[] buffer, int offset, int length)
     *      static byte[]	readFully(InputStream input, int length)
     *      readFully(ReadableByteChannel input, ByteBuffer buffer)
     *      readFully(Reader input, char[] buffer)
     *      readFully(Reader input, char[] buffer, int offset, int length)
     * @date 2019/6/25 11:41
     */
    @Test
    public void testSix() throws IOException {
        // 示例read
        InputStream isOne = IOUtils.toInputStream("我是\n一只\n小小小小\n鸟~\n嗷！嗷！", Charset.forName("UTF-8"));
        byte[] buffer = new byte[50];
        IOUtils.read(isOne, buffer);
        System.out.println(new String(buffer));

        // 示例readFully
        // 注意:与read不同的是，如果输入流中的字节长度不足的话，readFully会抛出异常！(而read不会)
        InputStream isTwo = IOUtils.toInputStream("我是\n一只\n小小小小\n鸟~\n嗷！嗷！", Charset.forName("UTF-8"));
        byte[] data = IOUtils.readFully(isTwo,40);
        System.out.println(new String(data));
    }


    /**
     * 将数据写入到输出流
     *
     * 类似的还有：
     *      static void	writeLines(Collection<?> lines, String lineEnding, OutputStream output)
     *      static void	writeLines(Collection, String, OutputStream, Charset)
     *      static void	writeLines(Collection<?> lines, String lineEnding, OutputStream output, Charset encoding)
     *      static void	writeLines(Collection<?> lines, String lineEnding, OutputStream output, String encoding)
     *      static void	writeLines(Collection<?> lines, String lineEnding, Writer writer)
     *
     *      static void	write(byte[] data, OutputStream output)
     *      static void	write(byte[] data, Writer output)
     *      static void	write(byte[], Writer, Charset)
     *      static void	write(byte[] data, Writer output, Charset encoding)
     *      static void	write(byte[] data, Writer output, String encoding)
     *      static void	write(char[] data, OutputStream output)
     *      static void	write(char[], OutputStream, Charset)
     *      static void	write(char[] data, OutputStream output, Charset encoding)
     *      static void	write(char[] data, OutputStream output, String encoding)
     *      static void	write(char[] data, Writer output)
     *      static void	write(CharSequence data, OutputStream output)
     *      static void	write(CharSequence, OutputStream, Charset)
     *      static void	write(CharSequence data, OutputStream output, Charset encoding)
     *      static void	write(CharSequence data, OutputStream output, String encoding)
     *      static void	write(CharSequence data, Writer output)
     *      static void	write(StringBuffer data, OutputStream output)
     *      static void write(CharSequence, OutputStream)
     *      static void	write(StringBuffer data, OutputStream output, String encoding)
     *      static void	write(CharSequence, OutputStream, String)
     *      static void	write(StringBuffer data, Writer output)
     *      static void	write(CharSequence, Writer)
     *      static void	write(String data, OutputStream output)
     *      static void	write(String, OutputStream, Charset)
     *      static void	write(String data, OutputStream output, Charset encoding)
     *      static void	write(String data, OutputStream output, String encoding)
     *      static void	write(String data, Writer output)
     *
     *      static void	writeChunked(byte[] data, OutputStream output)
     *      static void	writeChunked(char[] data, Writer output)

     *
     * @date 2019/6/25 11:57
     */
    @Test
    public void testSeven() throws IOException {
        // 示例writeLines
        OutputStream osOne = FileUtils.openOutputStream(new File("C:\\Users\\JustryDeng\\Desktop\\c.txt"), true);
        List<String> linesData = new ArrayList<>(8);
        linesData.add(IOUtils.LINE_SEPARATOR);
        linesData.add("邓沙利文");
        linesData.add("亨得帅");
        linesData.add("邓二洋");
        IOUtils.writeLines(linesData, IOUtils.LINE_SEPARATOR, osOne, "GBK");

        // 示例write
        OutputStream osTwo = FileUtils.openOutputStream(new File("C:\\Users\\JustryDeng\\Desktop\\c.txt"), true);
        char[] charData = new char[]{'s', 'w', 'a', 'g', 'g', 'e', 'r', '挺', '好', '的', '!'};
        IOUtils.write(charData, osTwo, "GBK");

        byte[] byteData = (IOUtils.LINE_SEPARATOR + "我是一只小小小小鸟~").getBytes("GBK");
        IOUtils.write(byteData, osTwo);
    }

}
