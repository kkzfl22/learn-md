# SpringMVC拦截器

# 单个拦截器

## 1. 拦截器示意图

![image-20230208224512831](D:\work\myself\learn\learn-md\spring-mvc\img\image-20230208224512831.png)

## 2 基础代码

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



webapp/WEB-INF/jsp/success.jsp

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





## 3 拦截器实现

```java
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 自定义SringMVC的拦截器
 *
 * @author liujun
 * @since 2023/2/8
 */
public class MyIntercepter01 implements HandlerInterceptor {

  /**
   * 该方法会在Handler方法业务执行逻辑执行之前执行
   *
   * @param request
   * @param response
   * @param handler
   * @return 是否放行。 true 放行，false 拦截
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("MyIntercepter01 preHandle...");
    return true;
  }

  /**
   * 会在handler方法业务逻辑执行之后尚未跳转页面时执行
   *
   * @param request
   * @param response
   * @param handler
   * @param modelAndView
   * @throws Exception
   */
  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {

    System.out.println("MyIntercepter01 postHandle...");
  }

  /**
   * 页面已经跳转渲染完成后执行
   *
   * @param request
   * @param response
   * @param handler
   * @param ex
   * @throws Exception
   */
  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {

    System.out.println("MyIntercepter01 afterCompletion...");
  }
}
```

DateTimeController.java

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

拦截器配制

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

    <bean id="formatStringToDate" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="om.nullnull.lean.controller.StringParseDateConverter"/>
            </set>
        </property>
    </bean>

    <!--注册SpringMVC的拦截器-->
    <mvc:interceptors>
        <!--拦截所有handler-->
        <!--        <bean class="om.nullnull.lean.interceptor.MyIntercepter01"/>-->
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <!--配制排除的地址-->
            <!--            <mvc:exclude-mapping path="/demo"/>-->
            <bean class="om.nullnull.lean.interceptor.MyIntercepter01"/>
        </mvc:interceptor>
    </mvc:interceptors>
</beans>
```





## 4 测试

在浏览器中输入：

http://127.0.0.1:8080/demo/handlerTime

可以看到页面正常响应：

```
跳转成功！服务器时间：2023-02-10T17:03:17.178
```



控制台输出：

```
MyIntercepter01 preHandle...
MyIntercepter01 postHandle...
MyIntercepter01 afterCompletion...
```

这样更加直观的看到了一个拦截器的执行拦截点的过程。



# 多个拦截器

多个拦截器，即在拦截器中再加上一个，观察下拦截器的执行顺序

```java
public class MyIntercepter02 implements HandlerInterceptor {

  /**
   * 该方法会在Handler方法业务执行逻辑执行之前执行
   *
   * @param request
   * @param response
   * @param handler
   * @return 是否放行。 true 放行，false 拦截
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("MyIntercepter02 preHandle...");
    return true;
  }

  /**
   * 会在handler方法业务逻辑执行之后尚未跳转页面时执行
   *
   * @param request
   * @param response
   * @param handler
   * @param modelAndView
   * @throws Exception
   */
  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {

    System.out.println("MyIntercepter02 postHandle...");
  }

  /**
   * 页面已经跳转渲染完成后执行
   *
   * @param request
   * @param response
   * @param handler
   * @param ex
   * @throws Exception
   */
  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {

    System.out.println("MyIntercepter02 afterCompletion...");
  }
}
```



拦截器配制

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


    <bean id="formatStringToDate" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="om.nullnull.lean.controller.StringParseDateConverter"/>
            </set>
        </property>
    </bean>

    <!--注册SpringMVC的拦截器-->
    <mvc:interceptors>
        <!--拦截所有handler-->
        <!--        <bean class="om.nullnull.lean.interceptor.MyIntercepter01"/>-->
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <!--配制排除的地址-->
            <!--            <mvc:exclude-mapping path="/demo"/>-->
            <bean class="om.nullnull.lean.interceptor.MyIntercepter01"/>
        </mvc:interceptor>

        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <!--配制排除的地址-->
            <!--            <mvc:exclude-mapping path="/demo"/>-->
            <bean class="om.nullnull.lean.interceptor.MyIntercepter02"/>
        </mvc:interceptor>

    </mvc:interceptors>

</beans>
```



## 测试

在浏览器中输入：

http://127.0.0.1:8080/demo/handlerTime

响应页面：

```
跳转成功！服务器时间：2023-02-10T17:14:42.172
```

观察控制台：

```
MyIntercepter01 preHandle...
MyIntercepter02 preHandle...
MyIntercepter02 postHandle...
MyIntercepter01 postHandle...
MyIntercepter02 afterCompletion...
MyIntercepter01 afterCompletion...
```

通过观察发现，此响应就是我们配制的顺序依次来执行。



执行顺序

![image-20230210174831406](D:\work\myself\learn\learn-md\spring-mvc\img\image-20230210174831406.png)