# SpringMVC异常处理

## 1.基础的工程文件

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
        <!--⽂件上传所需jar坐标-->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
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

commons-fileupload为文件上传的包



## 2. springMVC的配制

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
    <mvc:annotation-driven conversion-service="formatStringToDate"/>

    <!--方案1，交给Tomcat容器处理
    <mvc:default-servlet-handler />-->

    <!-- 方案二：使用SpringMVC提供的静态资源处理   -->
    <mvc:resources location="classpath:res/" mapping="/resource/**"/>
    <mvc:resources location="WEB-INF/js/" mapping="/js/**"/>


</beans>
```

## 3.  单个Controller的异常处理

ExceptionController的异常处理

```java
@Controller
public class ExceptionController {

  @ExceptionHandler(ArithmeticException.class)
  public ModelAndView exceptionHandler(
      ArithmeticException exception, HttpServletResponse response) {
    ModelAndView result = new ModelAndView();

    result.addObject("errorMsg", exception.getMessage());
    result.addObject("time", LocalDateTime.now());
    result.setViewName("error");

    return result;
  }

  @RequestMapping(
      value = "/exception01",
      method = {RequestMethod.GET})
  public ModelAndView exceptionProcess() {
    ModelAndView result = new ModelAndView();

    int value = 100 / 0;

    result.setViewName("success" + value);

    return result;
  }
}
```

### 3.1 添加异常处理页面

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>error</title>
</head>
<body>
发生错误，服务器时间：${time}，异常信息：${errorMsg}
</body>
</html>
```

## 3.2 测试 

在浏览器中输入：

http://127.0.0.1:8080/exception01

便可看到结果:

```
发生错误，服务器时间：2023-02-12T14:12:23.207，异常信息：/ by zero
```





## 4. 全局异常处理 

### 4.1 添加全局的异常处理



GlobExceptionHandler.java

```java
package om.nullnull.lean.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * 全局异常处理器
 *
 * @author liujun
 * @since 2023/2/12
 */
@ControllerAdvice
public class GlobExceptionHandler {

  /**
   * 专门用来处理用0做除数的异常
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ArithmeticException.class)
  public ModelAndView handleException(ArithmeticException e) {

    ModelAndView result = new ModelAndView();

    result.addObject("errorMsg", "glob " + e.getMessage());
    result.addObject("time", LocalDateTime.now());

    result.setViewName("error");

    return result;
  }
}
```

ExceptionController.java

```
@Controller
public class ExceptionController {

  @RequestMapping(
      value = "/exception01",
      method = {RequestMethod.GET})
  public ModelAndView exceptionProcess() {
    ModelAndView result = new ModelAndView();

    int value = 100 / 0;

    result.setViewName("success" + value);

    return result;
  }
}
```



### 4.2 测试

在浏览器中输入：

http://127.0.0.1:8080/exception01

结果：

```
发生错误，服务器时间：2023-02-12T14:21:03.230，异常信息：glob / by zero
```



## 5. 局部异常处理VS全局异常处理

在Controller中配制了异常处理时，此异常仅针对当前异常生效。

当全局异常与局部异常处理都存在时，在当前Controller能处理的情况下，优先在Controller处理，否则将使用全局的异常处理。



