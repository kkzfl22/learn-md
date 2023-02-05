## SpringMVC参数绑定

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







### 2. SpringMVC对于原生参数获取的支持

webapp/dataParam1.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数绑定验证</title>
</head>
<body>
<div>
    <h2>请求参数绑定</h2>

    <fieldset>
        <p>SpringMVC对原生ServletAPI的支持</p>
        <a href="/handle01?id=11">测试</a>
    </fieldset>

</div>
</body>
</html>
```





controller处理

```
@Controller
public class ParamBinderController {


    @RequestMapping("/handle01")
    public ModelAndView handler1(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        System.out.println("id:" + id);


        ModelAndView result = new ModelAndView();

        result.addObject("time", LocalDateTime.now());
        result.setViewName("success");

        return result;
    }

}
```



结果

当访问后，可以看到

```
id:11
```

页面可以正常的跳转至success.jsp





### 3. 基本参数的自动绑定

webapp/dataParam1.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数绑定验证</title>
</head>
<body>
<div>
    <h2>请求参数绑定</h2>

    <fieldset>
        <p>SpringMVC的基本参数绑定</p>
        <a href="/handle02?id=11">测试</a>
    </fieldset>

</div>
</body>
</html>
```



controller处理

```
@Controller
public class ParamBinderController {
    @RequestMapping("/handle02")
    public ModelAndView handler2(Integer id) {
        System.out.println("id:" + id);
        ModelAndView result = new ModelAndView();

        result.addObject("time", LocalDateTime.now());
        result.setViewName("success");

        return result;
    }
}

```



测试

在浏览器中输入

http://127.0.0.1:8080/handle02?id=11

页面成功展示

```
跳转成功！服务器时间：2023-02-05T11:35:07.352
```



控制吧输出:

```
id:11
```



注：

此要求request请求的参数名需要与形参的参数名一致，即可接收。但如果参数名称不致，则需要使用@RequestParam来使用

样例：

webapp/dataParam1.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数绑定验证</title>
</head>
<body>
<div>
    <h2>请求参数绑定</h2>

    <fieldset>
        <p>SpringMVC的基本参数绑定参数名称不一致</p>
        <a href="/handle021?idData=11">测试</a>
    </fieldset>

</div>
</body>
</html>
```

controller处理

```java
@Controller
public class ParamBinderController {


    @RequestMapping("/handle021")
    public ModelAndView handler21(@RequestParam("idData") Integer id) {
        System.out.println("id:" + id);
        ModelAndView result = new ModelAndView();

        result.addObject("time", LocalDateTime.now());
        result.setViewName("success");

        return result;
    }
    
}
```



测试

浏览器中输入

http://127.0.0.1:8080/handle021?idData=12

```
跳转成功！服务器时间：2023-02-05T11:48:05.736
```

控制台中

```
id:12
```

在单一的请求参数中，需要使用包装类型，如果使用基本的数据类型，将会存在传递null值的而转换异常的问题。



### 4. 绑定POJO的数据类型

UserData.java

```java
public class UserData {
  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserData{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
```



ParamBinderController.java

```
@Controller
public class ParamBinderController {
  @RequestMapping("/handle03")
  public ModelAndView handle03(UserData userInfo) {
    System.out.println("userInfo:" + userInfo);
    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



dataParam1.jsp

```
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数绑定验证</title>
</head>
<body>
<div>
    <h2>请求参数绑定</h2>

    <fieldset>
        <p>SpringMVC的绑定POJO类型</p>
        <a href="/handle03?id=11&name=test">测试</a>
    </fieldset>

</div>
</body>
</html>
```



请求页面

http://127.0.0.1:8080/dataParam1.jsp 点击测试

或者直接在浏览器中输入

http://127.0.0.1:8080/handle03?id=11&name=test

可以看到页面访问成功

```
跳转成功！服务器时间：2023-02-05T12:00:18.562
```

控制台输出:

```
userInfo:UserData{id=11, name='test'}
```

在绑定POJO类型时，直接形参声明即可，类型就POJO的类型，形参名无所谓，但要求传递的参数与必须和POJO的属性名保持一致。



还存在属性直接可以直接引用的场景。

还是实体的信息

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



UserData.java

```java
public class UserData {
  private Integer id;
  private String name;

  private UserAddress address;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserAddress getAddress() {
    return address;
  }

  public void setAddress(UserAddress address) {
    this.address = address;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserData{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", address=").append(address);
    sb.append('}');
    return sb.toString();
  }
}
```



ParamBinderController.java

```java
@Controller
public class ParamBinderController {

  @RequestMapping("/handle031")
  public ModelAndView handle031(UserData userInfo) {
    System.out.println("userInfo31:" + userInfo);
    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



dataParam1.jsp

```
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数绑定验证</title>
</head>
<body>
<div>
    <h2>请求参数绑定</h2>
    <fieldset>
        <p>SpringMVC的绑定POJO类型带嵌套</p>
        <a href="/handle031?id=11&name=test&address.addressId=12&address.address=shanghai">测试</a>
    </fieldset>

</div>
</body>
</html>
```



请求访问页面

http://127.0.0.1:8080/dataParam1.jsp

或者在浏览器中输入

http://127.0.0.1:8080/handle031?id=11&name=test&address.addressId=12&address.address=shanghai



可看到页面成功跳转

```
跳转成功！服务器时间：2023-02-05T12:10:55.867
```

控制台输出：

```
userInfo31:UserData{id=11, name='test', address=UserAddress{addressId=12, address='shanghai'}}
```



当使用嵌套参数传递时，需要使用一级对象的引用名称.二级属性名来进行参数传递。 





### 5. 参数类型转换绑定

dataParam1.jsp

```jsp
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>参数绑定验证</title>
</head>
<body>
<div>
    <h2>请求参数绑定</h2>

    <fieldset>
        <p>SpringMVC的绑定POJO类型时间转换</p>
        <a href="/handle05?date=2023-02-05">测试</a>
    </fieldset>
</div>
</body>
</html>
```



ParamBinderController.java

```
@Controller
public class ParamBinderController {

  @RequestMapping("/handle05")
  public ModelAndView handle05(Date date) {
    System.out.println("date:" + date);
    ModelAndView result = new ModelAndView();

    result.addObject("time", LocalDateTime.now());
    result.setViewName("success");

    return result;
  }
}
```



测试

这时候打开浏览器，输入

http://127.0.0.1:8080/dataParam1.jsp

选择

或者直接请求

http://127.0.0.1:8080/handle05?date=2023-02-05



将后台看页面输出：

```
HTTP Status 400 -
type Status report

message

description The request sent by the client was syntactically incorrect.

Apache Tomcat/7.0.47
```



页面报出了400,代表请求参数错误。

