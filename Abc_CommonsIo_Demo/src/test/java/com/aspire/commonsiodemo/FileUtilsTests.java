package com.aspire.commonsiodemo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 测试使用 FileUtils
 *
 * 提示: copy功能与move功能用法类似，本人下面就这两部分的内容，只演示了部分copy功能； move功能类比着使用即可
 *
 * @author JustryDeng
 * @date 2019/6/13 19:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUtilsTests {

    private static final String BASE_DIR = "C:\\Users\\JustryDeng\\Desktop\\abc";

    /**
     * 将指定大小的bytes， 转换为易读的单位， 如： bytes、KB、MB、GB、TB……
     *
     * @date 2019/6/13 19:09
     */
    @Test
    public void testOne() {
        String resultOne = FileUtils.byteCountToDisplaySize(25L);
        Assert.assertEquals("25 bytes", resultOne);

        String resultTwo = FileUtils.byteCountToDisplaySize(25 * 1024 *1024L);
        Assert.assertEquals("25 MB", resultTwo);

        String resultThree = FileUtils.byteCountToDisplaySize(25 * 1024 * 1024 *1024L);
        Assert.assertEquals("25 GB", resultThree);
    }

    /**
     * 将泛型为 File的集合 转换为数组
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testTwo() {
        List<File> list = new ArrayList<>(8);
        list.add(new File(BASE_DIR + File.separator + "a.txt"));
        list.add(new File(BASE_DIR + File.separator + "a.txt"));
        list.add( new File(BASE_DIR + File.separator + "c.txt"));
        File[] fileArray = FileUtils.convertFileCollectionToFileArray(list);
        for (int i = 0; i < fileArray.length; i++) {
            Assert.assertEquals(list.get(i), fileArray[i]);
        }
        // System.out.println(Arrays.deepToString(fileArray));
    }

    /**
     * 查找指定目录下的(所有)文件
     * 注:既能查找出有后缀名的文件，又能查找出无后缀名的文件
     *
     * public static Collection<File> listFiles(final File directory, final String[] extensions, final boolean recursive)
     * 方法的三个参数分别是:
     *  File directory: 指定文件夹目录
     *  String[] extensions:筛选出指定后缀名的文件，若为null,表示不作筛选，直接返回查到的所有文件
     *  boolean recursive:是否递归
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testThree() {
        // 筛选出指定后缀名的文件
        String[] extensions = new String[]{"java", "xml", "txt"};
        // null表示不作筛选，FileUtils.listFiles会直接返回所有的文件
        // String[] extensions = null;

        // 遍历查找指定目录下的所有文件
        Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ), extensions, true);
        for (File file: filesList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * 查找指定目录下的(所有)文件
     * 注:既能查找出有后缀名的文件，又能查找出无后缀名的文件
     * 注:此方法与testThree方法功能几乎一致，不过 在public static Collection<File> listFiles(final File directory, final IOFileFilter fileFilter, final IOFileFilter dirFilter)
     *    方法，比testThree中的FileUtils.listFiles方法更灵活。
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testFour() {
        // (递归)遍历查找指定目录下的所有文件
        // Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        /*
         * (递归)遍历查找指定目录下的所有文件，返回以【m.xml】结尾的文件
         * 如果筛选条件有多个的话，可以使用FileFilterUtils.and()、FileFilterUtils.or()来实现
         */
        Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ), FileFilterUtils.suffixFileFilter("m.xml"), TrueFileFilter.INSTANCE);
        for (File file: filesList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * 比较两个文件中的内容是否完全一致
     * 注:有一个空格都不算一致
     * 注:若两个文件都不存在，那么也算一致
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testSix() throws IOException {
        boolean result = FileUtils.contentEquals(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"),
                                                 new File("C:\\Users\\JustryDeng\\Desktop\\b.txt"));
        System.out.println(result);
    }

    /**
     * 将指定目录下的所有内容(文件、文件夹)，拷贝到一个新的目录下
     * 注:若这个新的目录不存在，那么会自动创建
     * 注:若目的文件夹下存在相同名字的文件、文件夹，那么会进行覆盖
     * 注:只拷贝源目录下的内容，源目录不会被拷贝
     * 注:其有一个比较实用的重载方法copyDirectory(final File srcDir, final File destDir,final FileFilter filter)，
     *    该方法能对源文件、文件夹进行过滤，只有满足FileFilter的文件/文件件，才会被拷贝进 目的文件夹下， 可详见源码
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testSeven() throws IOException {
        FileUtils.copyDirectory(new File(BASE_DIR), new File("C:\\Users\\JustryDeng\\Desktop\\xyz\\jpg"));
    }

    /**
     * 将指定目录以及该目录下的的所有内容(文件、文件夹)，拷贝到一个新的目录下
     * 注:若这个新的目录不存在，那么会自动创建
     * 注:若目的文件夹下存在相同名字的文件、文件夹，那么会进行覆盖
     * 注:与testSeven不同的是，这里直接连源目录都一起进行拷贝了
     * 注:其有一个比较实用的重载方法copyDirectory(final File srcDir, final File destDir,final FileFilter filter)，
     *    该方法能对源文件、文件夹进行过滤，只有满足FileFilter的文件/文件件，才会被拷贝进 目的文件夹下， 可详见源码
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testEight() throws IOException {
        FileUtils.copyDirectoryToDirectory(new File(BASE_DIR), new File("C:\\Users\\JustryDeng\\Desktop\\xyz\\jpg"));
    }

    /**
     * 拷贝一个文件，到一个新的文件
     *
     * 注:FileUtils.copyFile方法只能拷贝文件，不能拷贝文件夹
     * 注:新文件的存放路径如果不存在，会自动创建
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testNine() throws IOException {
        FileUtils.copyFile(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"), new File("C:\\Users\\JustryDeng\\Desktop\\lv\\re.txt"));
    }

    /**
     * 拷贝一个文件，到一个输出流
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testTen() throws IOException {
        try (OutputStream os = System.out) {
            FileUtils.copyFile(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"), os);
            String str = os.toString();
            System.out.println(str);
        }
    }

    /**
     * 拷贝一个输入流，到一个文件
     * 注:会自动创建文件
     * 注:如果路径不存在的话，会自动创建相关文件夹
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testEleven() throws IOException {
        File tmpFile = new File("C:\\Users\\JustryDeng\\Desktop\\b.txt");
        try (InputStream is = new FileInputStream(tmpFile)) {
            // 等价于copyToFile(InputStream source, File destination)
            FileUtils.copyInputStreamToFile(is, new File("C:\\Users\\JustryDeng\\Desktop\\mq\\tmp\\b2.txt"));
        }
    }

    /**
     * 拷贝文件到指定目录下
     * 注:如果目标目录不存在，那么会自动创建
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testTwelve() throws IOException {
        FileUtils.copyFileToDirectory(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"),
                new File("C:\\Users\\JustryDeng\\Desktop\\mq\\tmp\\"));
    }

    /**
     * 拷贝【文件】或【文件夹】 到 指定目录下
     * 注:如果目标目录不存在，那么会自动创建
     * 注:如果拷贝的是文件夹，那么连该文件夹下的所有东西，都会一同进行拷贝
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testThirteen() throws IOException {
        FileUtils.copyToDirectory(new File("C:\\Users\\JustryDeng\\Desktop\\abc"),
                new File("C:\\Users\\JustryDeng\\Desktop\\mq\\tmp\\"));
    }

    /**
     * 拷贝URL指向的文件 到 指定文件(即:下载URL指向的文件)
     * 注:如果文件的相关目录不存在，那么会自动创建相关文件夹
     * 注:copyURLToFile还有一个重载方法copyURLToFile(URL source, File destination, int connectionTimeout, int readTimeout)
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testFourteen() throws IOException {
        URL url = new URL("https://avatar.csdn.net/D/8/D/3_justry_deng.jpg");
        FileUtils.copyURLToFile(url, new File("C:\\Users\\JustryDeng\\Desktop\\mq\\tmp\\ds.jpg"));
    }

    /**
     * 清除目录(而不删除它)
     *
     * @date 2019/6/13 19:44
     */
    @Test
    public void testFive() throws IOException {
        FileUtils.cleanDirectory(new File("C:\\Users\\JustryDeng\\Desktop\\xyz"));
    }

    /**
     * 删除文件夹(及里面的内容)
     *
     * 注:如果删除失败的话，那么会IOException抛出异常
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testFiveteen() throws IOException {
        FileUtils.deleteDirectory(new File("C:\\Users\\JustryDeng\\Desktop\\mq"));
    }

    /**
     * 删除指定的文件 或 指定的文件夹(及里面的内容)
     *
     * 注:如果删除失败的话，那么会IOException抛出异常
     * 注:此方法不会抛出异常，只会返回 删除成功与否(提示:若文件/文件夹不存在，则会被视为删除失败，进而返回false)
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testSixteen() {
        boolean result = FileUtils.deleteQuietly(new File("C:\\Users\\JustryDeng\\Desktop\\mq"));
        System.out.println(result);
    }

    /**
     * 判断指定目录下，是否存在 指定的文件、文件夹
     * 注:不仅能看子级的，还能看子孙级别的
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testSeventeen() throws IOException {
        boolean result = FileUtils.directoryContains(new File(BASE_DIR),
                                                     new File(BASE_DIR + "\\z\\tmp\\l.txt"));
        System.out.println(result);
    }

    /**
     * 根据给定的文件,创建其所在的目录
     *
     * 注:若父目录也不存在，那么会自动创建
     * 注:还有一个相似的方法:创建给定的文件夹目录FileUtils.forceMkdir
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testEighteen() throws IOException {
        FileUtils.forceMkdirParent(new File("C:\\Users\\JustryDeng\\Desktop\\ppt\\tmp\\mq.pdf"));

    }

    /**
     * 演示一: 通过工具类获取File
     * 演示二: 查看文件、文件夹的大小
     *
     * 注:若父目录也不存在，那么会自动创建
     * 注:还有一个相似的方法:创建给定的文件夹目录FileUtils.forceMkdir
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testNineteen() {
        // 通过工具类获取File
        File file = FileUtils.getFile(new File("C:\\Users\\JustryDeng\\Desktop\\阿里巴巴Java开发手册1.4.0.pdf"));
        // 查看文件、文件夹的大小
        System.out.println(FileUtils.sizeOf(file) + "bytes");
        System.out.println(FileUtils.sizeOf(file) * 1.0 / 1024 / 1024 + "MB");
    }

    /**
     * 演示一: 根据文件，获得输入流
     * 演示二: 根据文件，获得输出流
     * 演示三: 将文件的内容读入字节数组
     * 演示四: 将文件的内容读入字符串
     *
     * 注:若父目录也不存在，那么会自动创建
     * 注:还有一个相似的方法:创建给定的文件夹目录FileUtils.forceMkdir
     *
     * TODO 本人这里主要是介绍这些个功能，所以本人这里就没有管是否释放资源； 在实际使用时记得要释放资源
     *
     * @date 2019/6/24 14:19
     */
    @Test
    @SuppressWarnings("unused")
    public void testTwenty() throws IOException {
        // 根据文件，获得输入流
        InputStream is = FileUtils.openInputStream(new File("C:\\Users\\JustryDeng\\Desktop\\阿里巴巴Java开发手册1.4.0.pdf"));
        /*
         * 根据文件，获得输出流(若指定位置下有源文件，那么会覆盖源文件)；
         * 注:此方法还有一个重载方法FileUtils.openOutputStream(final File file, final boolean append)
         *    次二哥参数，控制 是覆盖源文件，还是在源文件后面追加(默认为覆盖)。
         */
        OutputStream os = FileUtils.openOutputStream(new File("C:\\Users\\JustryDeng\\Desktop\\m\\d\\阿里巴巴Java开发手册1.4.0.pdf"));
        // 将文件的内容读入字节数组
        byte[] contentBytes = FileUtils.readFileToByteArray(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"));
        // 将文件的内容读入字符串
        String contentString = FileUtils.readFileToString(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"), StandardCharsets.UTF_8);
    }

    /**
     * 将文件的内容按照行为划分，读取出来
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testTwentyOne() throws IOException {
        List<String> lineStrList = FileUtils.readLines(new File("C:\\Users\\JustryDeng\\Desktop\\a.txt"), StandardCharsets.UTF_8);
        for (String lineStr : lineStrList) {
            System.out.println(lineStr);
        }
    }

    /**
     * 向指定文件中写入内容
     * 注:若文件不存在(或相关文件夹不存在)，那么会自动创建
     *
     * @date 2019/6/24 14:19
     */
    @Test
    public void testTwentyTwo() throws IOException {
        File targetFile = new File("C:\\Users\\JustryDeng\\Desktop\\asd\\sdf\\f\\c.txt");
        // 通过最后一个参数控制，是覆盖 还是追加
        // 注意:追加数据时，是直接追加，不会另起一行追加；如果需要换行的话，那么可以使用IOUtils.LINE_SEPARATOR
        FileUtils.writeStringToFile(targetFile, IOUtils.LINE_SEPARATOR + "i am data 咿呀咔咔!", Charset.forName("GBK"), true);
    }
}


