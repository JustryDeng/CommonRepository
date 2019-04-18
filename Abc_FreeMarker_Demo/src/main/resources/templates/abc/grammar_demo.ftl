<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FreeMarker简单测试</title>
</head>
<body>

<#-- *************************************************************************** -->
      <h2 style="color: red">直接取值(示例)：</h2>
      姓名(直接定位)：${testOne}<br/>
      <#--   FreeMarker提供了一个默认的全局根节点 .globals， 也可以通过其进行定位   -->
      姓名(通过.globals定位)：${.globals.testOne}
      <hr>

<#-- *************************************************************************** -->
      <h2 style="color: red">获取对象属性值(示例)：</h2>
      <#--   还可以调用方法， 如： ${testTwo.getName()}   -->
      姓名(通过属性取值)：  ${testTwo.name}<br/>
      姓名(通过方法取值)：  ${testTwo.getName()}<br/>
      年龄：  ${testTwo.age}<br/>
      性别：  ${testTwo.gender}<br/>
      爱好：  ${testTwo.hobby}<br/>
      座右铭：${testTwo.motto}
      <hr>

<#-- *************************************************************************** -->
      <h2 style="color: red">if(示例)：</h2>
      <#--
         注:如果是在FreeMarker标签的属性位置 取值的话，直接  key打点调用属性 会 key打点调用方法 即可：
            不需要${}取值
      -->
      <#--
             ==                 等于
             !=                 不等于
             &&                 且
             ||                 或
             <   等价于  lt      小于
             <=  等价于  lte     小于等于
             >   等价于  gt      大于
             >=  等价于  gte     大于等于
             ??  等价于 ?exists   判断 属性 存在且不为null

           注:被【??】或【?exists】判断的属性如果不存在；那么判断结果为false;
             被【??】或【?exists】判断的属性的属性值如果为null；那么判断结果也为false;

           注:在比较时  我们可能会使用到如 > 这样的敏感字符，为了让程序认识到其是一个大于符号，而不是标签结束符，
              所以推荐使用()将条件括起来，这样括号里面的字符就会被认为是用来比较大小的大于符号；
               追注:如果不想使用括号的话，那么就需要用 gt 来代替 >，使用gte来代替 >= 了。
      -->
      <#if 1 == 1 > 测试 == </#if> <br/>
      <#if 1 != 2 > 测试 != </#if> <br/>
      <#if 1 == 2 || 3 ==3 > 测试 || </#if> <br/>
      <#if '我' == '我' && '她' == '她' > 测试 && </#if> <br/>
      <#if 1 < 2 > 测试 < </#if> <br/>
      <#if 1 lt 2 > 测试 lt </#if> <br/>
      <#if 1 <= 2 > 测试 <= </#if> <br/>
      <#if 1 lte 2 > 测试 lte </#if> <br/>
      <#if (3 > 2) > 测试 > </#if> <br/>
      <#if 3 gt 2 > 测试 gt </#if> <br/>
      <#if (3 >= 2) > 测试 >= </#if> <br/>
      <#if 3 gte 2 > 测试 gte </#if> <br/>
      <#if testThree.age ?? > 测试 ?? (提示：如果被判断的属性存在，且该属性的属性值不为null，则会显示此语句!) </#if><br/>
      <#if testThree.age ?exists >测试 ?exists (提示：如果被判断的属性存在，且该属性的属性值不为null，则会显示此语句!) </#if> <br/>
      <hr>

<#-- *************************************************************************** -->
      <h2 style="color: red">list(示例)：</h2>
      <#list testFourList as item>
          当前索引：${item?index}, 姓名：${item.name}, 座右铭：${item.getMotto()} <br/>
      </#list>
      <hr>

<#-- *************************************************************************** -->
      <h2 style="color: red">map(示例)：</h2>
      <#list testFiveMap?keys as kk>
          当前键：${kk}, 当前值：${testFiveMap[kk]} <br/>
      </#list>
      <hr>

<#-- *************************************************************************** -->
      <h2 style="color: red">日期(示例)：</h2>
          日期时间：${myDate?datetime}<br/>
          日期：${myDate?date}<br/>
          时间：${myDate?time}<br/>

</body>
</html>


