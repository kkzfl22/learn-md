# SpringMVC对于Rest风格支持

## 基本内容

## 1. 基本的代码文件

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





## 2. 查询请求-Get

webapp/restDemo.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Rest风格的展示</title>
</head>
<body>
<div>
    <h2>Rest风格展示</h2>

    <fieldset>
        <p>SpringMVC的Rest风格支持-get</p>
        <a href="/restHandle01/01">测试</a>
    </fieldset>
</div>
</body>
</html>
```



RestController.java

```java
@Controller
public class RestController {

  @RequestMapping(
      value = "/restHandle01/{id}",
      method = {RequestMethod.GET})
  public ModelAndView handler1(@PathVariable String id) {

    System.out.println("get param id:" + id);

    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



测试

在浏览器中输入：http://127.0.0.1:8080/restDemo.jsp

点击测试，则可以看到页面成功跳转

此时页面输出

```
跳转成功！服务器时间：2023-02-06T09:39:40.909
```

控制台输出

```
get param id:01
```



## 3. 添加数据请求-POST

webapp/restDemo.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Rest风格的展示</title>
</head>
<body>
<div>
    <h2>Rest风格展示</h2>

    <fieldset>
        <p>SpringMVC对于Rest风格的支持-post</p>
        <form method="post" action="/restHandle02">
            <input type="text" name="name"/>
            <input type="submit"/>
        </form>
    </fieldset>
</div>
</body>
</html>
```



RestController.java

```java
@Controller
public class RestController {

  @RequestMapping(
      value = "/restHandle02",
      method = {RequestMethod.POST})
  public ModelAndView handle2(String name) {
    System.out.println("post param name:" + name);

    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



测试：

这时在浏览器中输入：

http://127.0.0.1:8080/restDemo.jsp

在文本框中输入“张三”， 点击提交

可以看到页面成功跳转

```
跳转成功！服务器时间：2023-02-06T09:44:02.623
```

控制台输出：

```
post param name:å¼ ä¸
```

可以发现，此时提交上来的中文数据为乱码，此问题那如何解决呢？

Spring为我们提供了一个解决方法，在过滤器中配制下即可：

```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>

    <display-name>springmvc-demo</display-name>


    <filter>
        <filter-name>encodeing</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodeing</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


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

配制一个过滤器encodeing，然后指定下编码为UTF-8，此将过滤所有请求，以对内容进行转码操作。



再次测试：

在浏览器中输入：http://127.0.0.1:8080/restDemo.jsp

再次输入张三，占击提交。

页面成功跳转提示：

```
跳转成功！服务器时间：2023-02-06T09:47:42.803
```

控制台输出：

```
post param name:张三
```



## 4. 修改数据请求-PUT

webapp/restDemo.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Rest风格的展示</title>
</head>
<body>
<div>
    <h2>Rest风格展示</h2>

    <fieldset>
        <p>SpringMVC对于Rest风格的支持-put</p>
        <form method="post" action="/restHandle03/001">
            <input type="submit"/>
        </form>
    </fieldset>

</div>
</body>
</html>
```

当开始写表单的PUT请求时，就发现一个问题form表单的Method仅有get和post，那如何才能转换为PUT请求呢？

这时候Spring为我们提供了解决方案,分为两步

第一步添加一个隐藏域

```
<input type="hidden" name="_method" value="put"/>
```

之后页面请求就变成了：

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Rest风格的展示</title>
</head>
<body>
<div>
    <h2>Rest风格展示</h2>

    <fieldset>
        <p>SpringMVC对于Rest风格的支持-put</p>
        <form method="post" action="/restHandle03/001">
            <input type="hidden" name="_method" value="put"/>
            <input type="submit"/>
        </form>
    </fieldset>

</div>
</body>
</html>
```

第二步，将请求转换为PUT请求

web.xml

```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>

    <display-name>springmvc-demo</display-name>


    <filter>
        <filter-name>encodeing</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodeing</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


    <!--将隐藏在表单中隐藏的_method进行转换为相应的方法-->
    <filter>
        <filter-name>methodHidden</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>methodHidden</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


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

在xml中配制一个org.springframework.web.filter.HiddenHttpMethodFilter类的信息，可以直接进行请求类型的转换



RestController.java

```java
@Controller
public class RestController {

  @RequestMapping(
      value = "/restHandle03/{id}",
      method = {RequestMethod.PUT})
  public ModelAndView handle3(@PathVariable String id) {
    System.out.println("put param name:" + id);

    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



测试：

在浏览器中输入：

http://127.0.0.1:8080/restDemo.jsp

点击提交按钮,可以看到成功的跳转，并显示：

```
跳转成功！服务器时间：2023-02-06T09:57:41.831
```

控制台输出：

```
put param name:001
```



## 5. 删除请求-DELETE

删除请求同PUT请求，需要做请求的转换。

之后页面请求就变成了：

webapp/restDemo.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Rest风格的展示</title>
</head>
<body>
<div>
    <h2>Rest风格展示</h2>

    <fieldset>
        <p>SpringMVC对于Rest风格的支持-delete</p>
        <form method="post" action="/restHandle04/002">
            <input type="hidden" name="_method" value="delete"/>
            <input type="submit"/>
        </form>
    </fieldset>
  
</div>
</body>
</html>
```

RestController.java

```java
@Controller
public class RestController {

  @RequestMapping(
          value = "/restHandle04/{id}",
          method = {RequestMethod.DELETE})
  public ModelAndView handle4(@PathVariable String id) {
    System.out.println("delete param name:" + id);

    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



测试：

打开浏览器，在浏览器中输入：

http://127.0.0.1:8080/restDemo.jsp

点击提交，可以发现页面成功的跳转，并且输出了：

```
跳转成功！服务器时间：2023-02-06T10:02:10.425
```

控制台输出：

```
delete param name:002
```

