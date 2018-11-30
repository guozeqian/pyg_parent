<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker入门小DEMO </title>
</head>

<body>
<#--我只是一个注释，我不会有任何输出  -->
${name},你好。${message}
<#assign a="定义变量a">
a:${a}
<#assign  info={"a":"aaa","b":"bbb"}>
info:${info.a}++${info.b}

<#include "head.ftl">


<#if success=true>
    通过认证
<#else>
未通过认证
</#if>
----商品价格表----<br>
<#list goodsList as goods>
${goods_index+1} 商品名称： ${goods.name} 价格：${goods.price}<br>
</#list>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank}  账号：${data.account}

当前日期：${today?date} <br>
当前时间：${today?time} <br>
当前日期+时间：${today?datetime} <br>
日期格式化：  ${today?string("yyyy年MM月")}
累计积分：${point}
累计积分：${point?c}
<#if aaa??>
aaa变量存在
<#else>
aaa变量不存在
</#if>
${aaa!'-'}











</body>
</html>