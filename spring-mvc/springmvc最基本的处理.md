# springmvc最基本的处理



## 1 maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>springmvc-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

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

## 2 进行web.xml文件配置

```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>

    <display-name>springmvc-demo</display-name>


    <servlet>
        <servlet-name>spring-mvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      	
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



## 3 创建controller

```java
@Controller
@RequestMapping("/demo")
public class DateTimeController {


    @RequestMapping("/handlerTime")
    public ModelAndView dateShow() {

        //封装数据和页面信息的ModelAndView
        ModelAndView dataShow = new ModelAndView();
        // 向请求域中添加time属性的数据，等于request.setAttribute("time,xxx);
        dataShow.addObject("time", LocalDateTime.now());
        //视图跳转信息
        dataShow.setViewName("success");

        return dataShow;
    }

}
```





## 4 配制springmvc的xml文件

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
        <property name="prefix" value="/WEB-INF/jsp"/>
        <property name="suffix" value=".jsp"/>
    </bean>


    <!--选择最合适的处理器-->
    <mvc:annotation-driven/>

</beans>
```



## 5 创建success.jsp文件

注意此文件需放在/WEB-INF/jsp目录下

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



## 6 修改web.xml文件，添加springmvc的xml文件

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





## 7 启动tomcat

```
[INFO] --- tomcat7-maven-plugin:2.2:run (default-cli) @ springmvc-demo ---
[INFO] Running war on http://localhost:8080/
[INFO] Using existing Tomcat server configuration at D:\learn\spring\spring-mvc\springmvc-demo\target\tomcat
[INFO] create webapp with contextPath: 
一月 19, 2023 8:30:40 下午 org.apache.coyote.AbstractProtocol init
信息: Initializing ProtocolHandler ["http-bio-8080"]
一月 19, 2023 8:30:40 下午 org.apache.catalina.core.StandardService startInternal
信息: Starting service Tomcat
一月 19, 2023 8:30:40 下午 org.apache.catalina.core.StandardEngine startInternal
信息: Starting Servlet Engine: Apache Tomcat/7.0.47
一月 19, 2023 8:30:40 下午 org.apache.catalina.startup.ContextConfig processAnnotationsJar
......
一月 19, 2023 8:30:42 下午 org.apache.catalina.core.ApplicationContext log
信息: No Spring WebApplicationInitializer types detected on classpath
一月 19, 2023 8:30:42 下午 org.apache.coyote.AbstractProtocol start
信息: Starting ProtocolHandler ["http-bio-8080"]
```



## 8 访问http://127.0.0.1:8080/demo/handlerTime

便可看到浏览器中输出了

```
跳转成功！服务器时间：2023-01-19T20:29:07.998
```

