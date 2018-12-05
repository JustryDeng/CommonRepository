EasyPoi Office的超简单工具类
===========================

 easypoi功能如同名字easy,主打的功能就是容易,让一个没见接触过poi的人员
就可以方便的写出Excel导出,Excel模板导出,Excel导入,Word模板导出,通过简单的注解和模板
语言(熟悉的表达式语法),完成以前复杂的写法

	作者博客：http://blog.afterturn.cn/
	作者邮箱： qrb.jueyue@gmail.com
	QQ群:   1群 364192721(满) 2 群116844390
	
	开发者:魔幻之翼 xf.key@163.com



[VIP技术服务](https://lemur.taobao.com)

    提供一年的技术支持服务
    提供10次内的1V1服务,限1小时
    提供升级指导
    针对lemur提供的所有开源项目提供支持服务

**开发指南**

**[http://www.afterturn.cn/doc/easypoi.html](http://www.afterturn.cn/doc/easypoi.html)**

**_测试是使用master的SNAPSHOT版本,请clone 代码然后install保证所有案例成功运行_**
===========================
不想clone ,把***-SNAPSHOT去掉SNAPSHOT,直接依赖正式版,大部分案例可以成功
===========================

运行 Application 访问界面,几场最常用的代码

提供基础的测试案例,demo代码

为了保证测试的可运行行,可能会创建大量对象,请大家谅解

目录介绍

- tohtml html预览测试
- view 导出的view测试

- cache 自定义缓存测试
- html html互转测试
 - test
    - excel
       - read 读取Excel测试
          - check 导入检查测试
          - hanlder 导入数据处理
          - img 含图片导入测试
    - styler 导出样式自定义测试
    - template 模板导出测试
        - sum 导出含统计测试
    - test 导出测试
        - groupname groupname 属性测试
        - img 导出图片测试
- pdf pdf测试
- word word导出测试
- util util 内部测试