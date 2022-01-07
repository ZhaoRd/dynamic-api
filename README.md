# DynamicApi

# 简介

一款简化spring boot 项目controller层的工具，直接将service映射为可访问的web api。

# 使用

maven 引入 `dynamic-api-boot-starter`

```xml
<dependency>
    <groupId>io.github.zhaord</groupId>
    <artifactId>dynamic-api-boot-starter</artifactId>
    <version>Latest Version</version>
</dependency>
```

使用 `@DynamicApi` 注解标记Service实现类

```java
@DynamicApi
public class DemoService {

    public int getAdd(int a, int b){
        return a+b;
    }

    public String getAddWithDto(AddDto input){
        return input.getNum1()+"+"+input.getNum2()+"="+ (input.getNum1()+input.getNum2());
    }

    public AddNameVo addName(NameDto input){
        AddNameVo vo = new AddNameVo();
        vo.setResult("add success "+ input.getName());
        return vo;
    }
}
```

> 这个DemoService映射的api
> 
> [GET]  /api/service/demo/getAdd
> 
> [GET]  /api/service/demo/getAddWithDto
> 
> [POST] /api/service/demo/addName

浏览器中打开`http://127.0.0.1:8080/api/service/demo/getAdd?a=1&b=2` 

![](C:\Users\zrd_1\AppData\Roaming\marktext\images\2022-01-07-13-29-19-image.png)

# 链接

# 映射关系

/api/service/demo/getAdd 为例

* /api/service 是固定前缀

* demo 是 DemoService 类名首小写后，去掉Service剩下的字符串，除了Service, 还有 AppService、Application、ApplicationService 等后缀也会被去掉

* getAdd 即方法名

* get/find 开头的方法名映射为 http get方法

* del/delete/remove 开头的方法名映射为 http delete方法

* add/insert/post 开头的方法名映射为 http post 方法

* update/put 开头的方法名映射为 http  put 方法

* 默认方法映射为 http post 方法

# 版权 | License

[MIT](https://github.com/marktext/marktext/blob/develop/LICENSE)
