package com;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.aspire.model.HandsomeBoyPOJO;
import com.aspire.model.ResultPOJO;
import com.aspire.model.StudentVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyPoiApplicationTests {

    /**
     * 直接导出(无需模板)
     * 注:此方式存在一些不足之处，在对性能、excel要求比较严格时不推荐使用
     *
     * @author JustryDeng
     * @date 2018/12/5 11:44
     */
    @Test
    public void directExportExcel() throws IOException {
        // Map作为每一行的数据容器，List作为行的容器
        List<Map<String, Object>> rowDataList = new ArrayList<>();
        // 每个ExcelExportEntity存放Map行数据的key
        List<ExcelExportEntity> keyList = new ArrayList<>();
        Map<String, Object> aRowMap;
        final int COMMON_KEY_INDEX = 10;
        for (int i = 0; i < 5; i++) {
            // 一个Map对应一行数据（如果需要导出多行数据，那么需要多个Map）
            aRowMap = new HashMap<>(16);
            for (int j = 0; j < COMMON_KEY_INDEX; j++) {
                String key = j + "";
                aRowMap.put(key, "坐标为(" + i + "," + j + ")");
            }
            rowDataList.add(aRowMap);
            // 同一列对应的cell,在从Map里面取值时，会共用同一个key
            // 因此ExcelExportEntity的个数要保持和列数做多的行 的map.size()大小一致
            if (i == 0) {
                ExcelExportEntity excelExportEntity;
                for (int j = 0; j < COMMON_KEY_INDEX; j++) {
                    excelExportEntity = new ExcelExportEntity();
                    excelExportEntity.setKey(j + "");
                    // 设置cell宽
                    excelExportEntity.setWidth(15D);
                    // 设置cell是否自动换行
                    excelExportEntity.setWrap(true);
                    keyList.add(excelExportEntity);
                }
            }
        }
        // excel总体设置
        ExportParams exportParams = new ExportParams();
        // 不需要标题
        exportParams.setCreateHeadRows(false);
        // 指定sheet名字
        exportParams.setSheetName("直接导出数据测试");
        // 生成workbook 并导出
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, keyList, rowDataList);
        File savefile = new File("C:/Users/JustryDeng/Desktop/");
        if (!savefile.exists()) {
            boolean result = savefile.mkdirs();
            System.out.println("目录不存在，创建" + result);
        }
        FileOutputStream fos = new FileOutputStream("C:/Users/JustryDeng/Desktop/abc.xls");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 对象---直接导出(无需模板)
     *
     * 注:如果模型 的父类的属性也有@Excel注解，那么导出excel时，会连该模型的父类的属性也一会儿导出
     *
     * @author JustryDeng
     * @date 2018/12/5 11:44
     */
    @Test
    public void directExportExcelByObject() throws IOException {
        List<StudentVO> list = new ArrayList<>(16);
        StudentVO student;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            student = new StudentVO(i + "",
                    "name" + i,
                    random.nextInt(2),
                    random.nextInt(100),
                    new Date(),
                    "className" + i);
            student.setSchoolName("学校名称" + i);
            student.setSchoolAddress("学校地址" +i);
            list.add(student);
        }
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName("我是sheet名字");
        // 生成workbook 并导出
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, StudentVO.class, list);
        File savefile = new File("C:/Users/JustryDeng/Desktop/");
        if (!savefile.exists()) {
            boolean result = savefile.mkdirs();
            System.out.println("目录不存在，创建" + result);
        }
        FileOutputStream fos = new FileOutputStream("C:/Users/JustryDeng/Desktop/student.xls");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 模板导出---Map组装数据
     *
     * 注:.xls的模板可以导出.xls文件，也可以导出xlsx的文件;
     *    同样的, .xlsx的模板可以导出.xls文件，也可以导出xlsx的文件;
     *
     * @author JustryDeng
     * @date 2018/12/5 11:44
     */
    @Test
    public void templateExportExcelByMap() throws IOException {
        // 加载模板
        TemplateExportParams params = new TemplateExportParams("templates/templateMap.xls");
        Map<String, Object> map = new HashMap<>(16);
        map.put("title", "全亚洲,最帅气人员名单");
        map.put("date", "2018-12-05");
        map.put("interviewer", "JustryDeng");
        List<Map<String, Object>> list = new ArrayList<>(16);
        Map<String, Object> tempMap;
        for (int i = 0; i < 5; i++) {
            tempMap = new HashMap<>();
            tempMap.put("name", "邓沙利文");
            tempMap.put("gender", new Random().nextInt(2) == 0 ? "男" : "女");
            tempMap.put("age", new Random().nextInt(90) + 11);
            tempMap.put("hobby", "活的，女的！！！");
            tempMap.put("handsomeValue", "100分(满分100分)");
            tempMap.put("motto", "之所以只帅到了全亚洲，是因为其他地方审美不同！");
            list.add(tempMap);
        }
        map.put("dataList", list);
        // 生成workbook 并导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("C:/Users/JustryDeng/Desktop/");
        if (!savefile.exists()) {
            boolean result = savefile.mkdirs();
            System.out.println("目录不存在,进行创建,创建" + (result ? "成功!" : "失败！"));
        }
        FileOutputStream fos = new FileOutputStream("C:/Users/JustryDeng/Desktop/templateMapResult.xlsx");
        workbook.write(fos);
        fos.close();
    }


    /**
     * 模板导出---对象组装数据
     *
     * 注:实际上仍然是"模板导出---Map组装数据",不过这里借助了工具类，将对象先转换为了Map<String, Object>
     *
     * 注:.xls的模板可以导出.xls文件，也可以导出xlsx的文件;
     *    同样的, .xlsx的模板可以导出.xls文件，也可以导出xlsx的文件;
     *
     * @author JustryDeng
     * @date 2018/12/5 11:44
     */
    @Test
    public void templateExportExcelByObject() throws IOException, IllegalAccessException {
        // 加载模板
        TemplateExportParams params = new TemplateExportParams("templates/templateObject.xlsx");
        // 组装数据
        ResultPOJO resultPOJO = new ResultPOJO();
        resultPOJO.setTitle("全亚洲最帅人员名单");
        resultPOJO.setInterviewer("邓沙利文");
        resultPOJO.setDate("2018-12-05");
        List<HandsomeBoyPOJO> list = new ArrayList<>(8);
        resultPOJO.setList(list);
        HandsomeBoyPOJO handsomeBoyPOJO;
        for (int i = 0; i < 5; i++) {
            handsomeBoyPOJO = new HandsomeBoyPOJO();
            handsomeBoyPOJO.setAge(20 + i);
            handsomeBoyPOJO.setGender(i % 2 == 0 ? "女" : "男");
            handsomeBoyPOJO.setHandsomeValue(95 + i + "(满分100分)");
            handsomeBoyPOJO.setHobby("女。。。。");
            handsomeBoyPOJO.setMotto("我是一只小小小小鸟~");
            handsomeBoyPOJO.setName("JustryDeng");
            list.add(handsomeBoyPOJO);
        }
        // 生成workbook 并导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, objectToMap(resultPOJO));
        File savefile = new File("C:/Users/JustryDeng/Desktop/");
        if (!savefile.exists()) {
            boolean result = savefile.mkdirs();
            System.out.println("目录不存在,进行创建,创建" + (result ? "成功!" : "失败！"));
        }
        FileOutputStream fos = new FileOutputStream("C:/Users/JustryDeng/Desktop/templateObjectResult.xls");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 对象转换为Map<String, Object>的工具类
     *
     * @param obj
     *            要转换的对象
     * @return map
     * @throws IllegalAccessException
     */
    private static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>(16);
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            /*
             * Returns the value of the field represented by this {@code Field}, on the
             * specified object. The value is automatically wrapped in an object if it
             * has a primitive type.
             * 注:返回对象该该属性的属性值，如果该属性的基本类型，那么自动转换为包装类
             */
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }
}