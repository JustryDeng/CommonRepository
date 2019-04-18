package com.normal;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * FreeMarker一般的使用方式 示例
 *
 * @author JustryDeng
 * @date 2019/4/17 21:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NormalTests {

    @Test
    public void contextLoads() throws IOException, TemplateException {

        String templateDir = "E:/Java/demo/Abc_FreeMarker_Demo/src/main/resources/templates/";

        String resultDir = "E:/Java/demo/Abc_FreeMarker_Demo/src/main/resources/html/";

        // -> 获取FreeMarker配置实例
        Configuration conf = new Configuration(Configuration.VERSION_2_3_0);

        // -> 指定模板文件所在文件夹
        // 直接指明文件夹路径的方式
         conf.setDirectoryForTemplateLoading(new File(templateDir));
        // 通过类加载器，使用相对路径的方式
        // conf.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "/templates/");

        // -> 设置编码(注:如果不设置或设置错了此项的话，那么加载模板的时候，可能导致中文乱码)
        conf.setDefaultEncoding("utf-8");

        // -> 获取ftl模板
        Template template = conf.getTemplate("normal.ftl");

        // -> 准备数据数据
        Map<String, Object> root = new HashMap<>(8);
        // 注意:因为freemarker模板会试着解析key,所以key命名时不要有敏感词汇；如:这里key取的是【root-key】的话，那么就会出错
        root.put("rootKey", "JustryDeng邓帅");

        // -> 准备输出流
        // 此方式可能导致输出时中文乱码
        // Writer out = new FileWriter(new File("E:/demo/templates/normal.html"));
        // 此方式可保证输出时中文不乱码
        Writer out =
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(
                                        new File(resultDir + "normal.html")), StandardCharsets.UTF_8));

        // -> 数据 + 输出流 = 生成的文件
        template.process(root, out);

        // -> 释放资源
        out.flush();
        out.close();
    }

}