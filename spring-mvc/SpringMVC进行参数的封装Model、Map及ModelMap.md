## SpringMVC进行参数的封装Model、Map及ModelMap



### 1. 基本的项代码文件

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

```
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

webapp\WEB-INF\jsp\success.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>index</title>
</head>
<body>
跳转成功！服务器时间：${time}
</body>
</html>
```





### 2. 使用ModelMap进行参数响应

ModelMapController.java

```
@Controller
public class ModelMapController {
    
    @RequestMapping("/requestModelMap")
    public String dataProcess(ModelMap modelMap) {
        modelMap.put("time", LocalDateTime.now());
        return "success";
    }
}
```

进行访问测试

当在浏览器中输入http://127.0.0.1:8080/requestModelMap

则会看到

```
跳转成功！服务器时间：2023-02-03T22:59:09.524
```



### 3. 使用Model进行响应参数

ModelMapController.java

```
@Controller
public class ModelMapController {

    @RequestMapping("/requestModel")
    public String dataProcessModel(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "success";
    }
}
```

进行访问测试

在浏览器中输入http://127.0.0.1:8080/requestModel

则能看到

```
跳转成功！服务器时间：2023-02-03T23:03:34.723
```



### 4. 使用Map进行响应参数

ModelMapController.java

```
@Controller
public class ModelMapController {
    @RequestMapping("/requestMapping")
    public String dataProcessMap(Map<String, Object> map) {
        map.put("time", LocalDateTime.now());
        return "success";
    }
}
```


进行访问测试

在浏览器中输入: http://127.0.0.1:8080/requestMapping

看到浏览器输出了

```
跳转成功！服务器时间：2023-02-04T14:16:55.572
```





### 5. 关于参数的关系

在SpringMVC框架中 ModelMap、Model、Map向这些参数中保存数据，在页面中都可以获取到。那它们之前的一个关系是什么样子的呢？

这时候呢，我们不访问去打印下这些参数信息，还是先看代码

```java
@Controller
public class ModelMapController {

    @RequestMapping("/requestModelMap")
    public String dataProcess(ModelMap modelMap) {
        modelMap.put("time", LocalDateTime.now());
        System.out.println("1---------: " + modelMap.getClass());
        return "success";
    }


    @RequestMapping("/requestModel")
    public String dataProcessModel(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        System.out.println("2---------: " + model.getClass());
        return "success";
    }


    @RequestMapping("/requestMapping")
    public String dataProcessMap(Map<String, Object> map) {
        map.put("time", LocalDateTime.now());
        System.out.println("3---------: " + map.getClass());
        return "success";
    }
}
```

然后再浏览器中访问这些接口

http://127.0.0.1:8080/requestModelMap

http://127.0.0.1:8080/requestModel

http://127.0.0.1:8080/requestMapping



观察控制台的输出：

```
1---------: class org.springframework.validation.support.BindingAwareModelMap
2---------: class org.springframework.validation.support.BindingAwareModelMap
3---------: class org.springframework.validation.support.BindingAwareModelMap
```



通过观察控制台的输出可以得知，虽然我们的参数是ModelMap、Model、Map但实际运行的是BindingAwareModelMap这个类。相当于给BindingAwareModelMap保存的数据，都会保存到请求域中。

然后来看下这三个文件

Map接口

```java
package java.util;

public interface Map<K,V>{
  ......  
}
```

ModelMap类

```
package org.springframework.ui;

public class ModelMap extends LinkedHashMap<String, Object> {
  ......
}
```

Model接口

```
package org.springframework.ui;

public interface Model {
......
}
```



BindingAwareModelMap类

```
package org.springframework.validation.support;

public class BindingAwareModelMap extends ExtendedModelMap {
......
}

package org.springframework.ui;

public class ExtendedModelMap extends ModelMap implements Model {
......
}
```

通过观察可以发现BindingAwareModelMap它是继承限ModelMap,又实现了Model接口，而ModelMap又间接的实现了Map接口。所以它们的关系之间已经清晰了。

