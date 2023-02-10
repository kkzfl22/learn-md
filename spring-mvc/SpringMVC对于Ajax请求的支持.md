# SpringMVC对于Ajax请求的支持

## 基本代码

Maven.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>springmvc-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>
  
    <dependencies>
        <!--引入spring-mvc-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.5.2</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
      
        <!--json数据交互所需jar，start-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.9.0</version>
        </dependency>
        <!--json数据交互所需jar，end-->      
      

    </dependencies>


    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <forkCount>3</forkCount>
                    <reuseForks>true</reuseForks>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>org.junit.jupiter</groupId>
                        <artifactId>junit-jupiter-engine</artifactId>
                        <version>5.5.2</version>
                    </dependency>
                </dependencies>
            </plugin>

            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <port>8080</port>
                    <path>/</path>
                </configuration>
            </plugin>

        </plugins>

    </build>


</project>
```



特别注意，需要加入json解析所需的包，否则会出现415的响应错误。



web.xml

```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>

    <display-name>springmvc-demo</display-name>


    <servlet>
        <servlet-name>spring-mvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:application-mvc.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>spring-mvc</servlet-name>

        <!--
        在xml中共表3种配制：
        第一种，带后缀：*.action,*.do,*.aaa,此配制最为明确，方便，在以前和现在企业中有很大的比例
        第二种，使用/,不找拦截jsp，但会拦截html等静态资源（除了jsp和servlet以外的js、css、png等）
        为什么本所/会拦截静态资源？
          因为在tomcat的容器中，有一个web.xml文件（父），在自己的项目中也有一个web.xml（子），是一个继承关系，
        父的web.xml中有一个DefaultServlet,url-pattern是一个/
        此时我们自己的web.xml中也配制了一个/，覆写了父的web.xml文件的本所
        为什么不拦截jsp呢？
          因为父的tomcat中有一个JspServlet,这个servlet拦截jsp文件，而我们并没有覆写这个配置，所以springmvc此时
        不拦截jsp，jsp处理交给servlet。
        第三种，使用/*，拦截所有，包括jsp
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

application-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd
">

    <!--开启controller扫描-->
    <context:component-scan base-package="om.nullnull.lean.controller"/>

    <!--配制视图解析-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>


    <!--选择最合适的处理器-->
    <mvc:annotation-driven/>

    <!--方案1，交给Tomcat容器处理
    <mvc:default-servlet-handler />-->

    <!-- 方案二：使用SpringMVC提供的静态资源处理   -->
    <mvc:resources location="classpath:res/" mapping="/resource/**"/>
</beans>
```

 

## ajax请求

此主要在于@RequestBody和@ResponseBody的使用



将jquery-3.6.3.min.js拷贝到webapp/WEB-INF/js目录下

UserAddress.java

```java
public class UserAddress {
  private Integer addressId;
  private String address;

  public Integer getAddressId() {
    return addressId;
  }

  public void setAddressId(Integer addressId) {
    this.addressId = addressId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserAddress{");
    sb.append("addressId=").append(addressId);
    sb.append(", address='").append(address).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
```





ajax.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Ajax展示</title>
    <script src="WEB-INF/js/jquery-3.6.3.min.js"/>
    <script type="application/javascript">
        $(function () {
            $("#ajaxBtn").bind("click", function () {
                //发送ajax事件
                $.ajax({
                    url: 'restAjaxHandle01',
                    type: 'POST',
                    data: '{"addressId": "111", "address": "新的地址信息"}',
                    success: function (data) {
                        alert(data);
                    }
                })
            })
        })
    </script>
</head>
<body>
<div>
    <h2>Ajax展示</h2>

    <fieldset>
        <p>SpringMVC对于AJAX的测试</p>
        <input type="button" id="ajaxBtn" value="ajax测试">
    </fieldset>
</div>
</body>
</html>
```



RestAjaxController.java

```java
@Controller
public class RestAjaxController {

  @RequestMapping(value = "/restAjaxTestHandle01")
  public @ResponseBody UserAddress restAjaxHandle01(@RequestBody UserAddress address) {
    System.out.println("request address" + address);
    address.setAddress("新的地址");
    return address;
  }
}
```



测试：

http://127.0.0.1:8080/ajax.jsp

当打开浏览器后，点击发现点击不了

于是控制了浏览器的chrome的控制台报错

```
ajax.jsp:7          GET http://127.0.0.1:8080/WEB-INF/js/jquery-3.6.3.min.js net::ERR_ABORTED 404 (Not Found)
```



进一步查看请求

jquery-3.6.3.min.js

```
http://127.0.0.1:8080/WEB-INF/js/jquery-3.6.3.min.js

HTTP/1.1 404 Not Found
Server: Apache-Coyote/1.1
Content-Type: text/html;charset=utf-8
Content-Language: en
Content-Length: 949
Date: Wed, 08 Feb 2023 01:22:42 GMT

```



也就是此文件未找到。

那问题出在哪里呢？

js文件为静态文件。那就是静态文件处理出现了问题。原来在静态文件配制中仅配制了一个

```xml
    <!-- 方案二：使用SpringMVC提供的静态资源处理   -->
    <mvc:resources location="classpath:res/" mapping="/resource/**"/>

```

找到原因了，就好处理了。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        https://www.springframework.org/schema/mvc/spring-mvc.xsd
">

    <!--开启controller扫描-->
    <context:component-scan base-package="om.nullnull.lean.controller"/>

    <!--配制视图解析-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>


    <!--注册最最合适的处理器映射器，处理器适配器调用handler-->
    <mvc:annotation-driven/>

    <!--方案1，交给Tomcat容器处理
    <mvc:default-servlet-handler />-->

    <!-- 方案二：使用SpringMVC提供的静态资源处理   -->
    <mvc:resources location="classpath:res/" mapping="/resource/**"/>
    <mvc:resources location="WEB-INF/js/" mapping="/js/**"/>
</beans>
```



ajax.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <title>Ajax展示</title>
    <script type="text/javascript" src="/js/jquery-3.6.3.min.js"></script>
    <script>

        $(function () {

            $("#ajaxBtn").bind("click",function () {
                // 发送ajax请求
                $.ajax({
                    url: '/restAjaxTestHandle01',
                    type: 'POST',
                    data: '{"addressId": 111, "address": "新的地址信息"}',
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function (data) {
                        alert(data.address);
                    }
                })
            })
        })


    </script>
</head>
<body>
<div>
    <h2>Ajax展示</h2>

    <fieldset>
        <p>SpringMVC对于AJAX的测试</p>
        <input type="button" id="ajaxBtn" value="ajax测试">
    </fieldset>
</div>
</body>
</html>
```



再次测试

http://127.0.0.1:8080/ajax.jsp

ajax测试 发送请求

便可看到成功响应,弹出了一个文本框，内容为“新的地址”

控制台输出：

```
request addressUserAddress{addressId=111, address='新的地址信息'}
```







