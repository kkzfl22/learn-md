# SpringMVC-url-pattern处理

## url-pattern使用/*拦截所有的请求

以一个代码示例来说明

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

xml文件配制

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

</beans>
```

web.xml文件配制

```
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
        第二种，使用/,不找拦截jsp
        第三种，使用/*，拦截所有，包括jsp
        -->
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
```



success.jsp

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



当在浏览器请求后，会出现

![image-20230202125316612](.\img\all-404--20230202125316612.png)

控制台输出

```
[WARNING] No mapping for GET /WEB-INF/jsp/success.jsp
```



结果：

通过观察控制台的输出，可以知道访问http://127.0.0.1:8080/demo/handlerTime 请求，已经到达了后台，此controller需要做处理，然后跳转至success.jsp,跳转这一步，即向/WEB-INF/jsp/success.jsp发起一个get请求，然此时URL又被Spring拦截进入了SpringMVC进行了一个处理。找与此URL匹配的URL进行处理。然代码中未定义此处理，故出现了404。



## url-pattern改进使用/

这时候对web.xml进行修改。使用/来配制。

```
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
        第二种，使用/,不找拦截jsp
        第三种，使用/*，拦截所有，包括jsp
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

当这时候使用浏览器来访问资源时，就能查看到

![image-20230202224701749](D:\doc\工作\迈为\开发文档\image-20230202224701749.png)

至此url-pattern配制为/，JSP页面将能够正常的访问，但是静态资源将会被拦截。静态资源：除了jsp和Servlet之外的js、CSS、png、html等

### 验证静态资源的访问

既然如此来做个验证

添加一个html页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>demo</title>
</head>
<body>
this is demo html...
</body>
</html>
```

将些html放入至webapp目录下。

访问测试

![image-20230202225801108](.\img\image-20230202225801108.png)



此时控制台也输出了

```
[WARNING] No mapping for GET /demo.html
```



### 为什么url-pattern配制为/不拦截jsp却拦截了静态资源呢？ 

  这是因为在tomcat中也存在一个web.xml文件，每个应用中也存在一个web.xml文件，我们将tomcat的web.xml文件称为父xml文件，而应用中的web.xml文件称为子xml文件，它们之前是一个继承关系，当子web.xml文件中配制了父web.xml文件中的url-pattern时，子xml文件的内容将覆盖了父xml文件。 打开tomcat/conf下的web.xml文件查看。

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
    version="2.5">

  <!-- ======================== Introduction ============================== -->
  <!-- This document defines default values for *all* web applications      -->
  <!-- loaded into this instance of Tomcat.  As each application is         -->
  <!-- deployed, this file is processed, followed by the                    -->
  <!-- "/WEB-INF/web.xml" deployment descriptor from your own               -->
  <!-- applications.                                                        -->
  <!--                                                                      -->
  <!-- WARNING:  Do not configure application-specific resources here!      -->
  <!-- They should go in the "/WEB-INF/web.xml" file in your application.   -->


  <!-- ================== Built In Servlet Definitions ==================== -->


  <!-- The default servlet for all web applications, that serves static     -->
  <!-- resources.  It processes all requests that are not mapped to other   -->
  <!-- servlets with servlet mappings (defined either here or in your own   -->
  <!-- web.xml file).  This servlet supports the following initialization   -->
  <!-- parameters (default values are in square brackets):                  -->
  <!--                                                                      -->
  <!--   debug               Debugging detail level for messages logged     -->
  <!--                       by this servlet.  [0]                          -->
  <!--                                                                      -->
  <!--   fileEncoding        Encoding to be used to read static resources   -->
  <!--                       [platform default]                             -->
  <!--                                                                      -->
  <!--   input               Input buffer size (in bytes) when reading      -->
  <!--                       resources to be served.  [2048]                -->
  <!--                                                                      -->
  <!--   listings            Should directory listings be produced if there -->
  <!--                       is no welcome file in this directory?  [false] -->
  <!--                       WARNING: Listings for directories with many    -->
  <!--                       entries can be slow and may consume            -->
  <!--                       significant proportions of server resources.   -->
  <!--                                                                      -->
  <!--   output              Output buffer size (in bytes) when writing     -->
  <!--                       resources to be served.  [2048]                -->
  <!--                                                                      -->
  <!--   readonly            Is this context "read only", so HTTP           -->
  <!--                       commands like PUT and DELETE are               -->
  <!--                       rejected?  [true]                              -->
  <!--                                                                      -->
  <!--   readmeFile          File name to display with the directory        -->
  <!--                       contents. [null]                               -->
  <!--                                                                      -->
  <!--   sendfileSize        If the connector used supports sendfile, this  -->
  <!--                       represents the minimal file size in KB for     -->
  <!--                       which sendfile will be used. Use a negative    -->
  <!--                       value to always disable sendfile.  [48]        -->
  <!--                                                                      -->
  <!--   useAcceptRanges     Should the Accept-Ranges header be included    -->
  <!--                       in responses where appropriate? [true]         -->
  <!--                                                                      -->
  <!--  For directory listing customization. Checks localXsltFile, then     -->
  <!--  globalXsltFile, then defaults to original behavior.                 -->
  <!--                                                                      -->
  <!--   localXsltFile       Make directory listings an XML doc and         -->
  <!--                       pass the result to this style sheet residing   -->
  <!--                       in that directory. This overrides              -->
  <!--                       contextXsltFile and globalXsltFile[null]       -->
  <!--                                                                      -->
  <!--   contextXsltFile     Make directory listings an XML doc and         -->
  <!--                       pass the result to this style sheet which is   -->
  <!--                       relative to the context root. This overrides   -->
  <!--                       globalXsltFile[null]                           -->
  <!--                                                                      -->
  <!--   globalXsltFile      Site wide configuration version of             -->
  <!--                       localXsltFile. This argument must either be an -->
  <!--                       absolute or relative (to either                -->
  <!--                       $CATALINA_BASE/conf or $CATALINA_HOME/conf)    -->
  <!--                       path that points to a location below either    -->
  <!--                       $CATALINA_BASE/conf (checked first) or         -->
  <!--                       $CATALINA_HOME/conf (checked second).[null]    -->

    <servlet>
        <servlet-name>default</servlet-name>
        <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
        <init-param>
            <param-name>debug</param-name>
            <param-value>0</param-value>
        </init-param>
        <init-param>
            <param-name>listings</param-name>
            <param-value>false</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
  
  ......
  <!-- ================ Built In Servlet Mappings ========================= -->


  <!-- The servlet mappings for the built in servlets defined above.  Note  -->
  <!-- that, by default, the CGI and SSI servlets are *not* mapped.  You    -->
  <!-- must uncomment these mappings (or add them to your application's own -->
  <!-- web.xml deployment descriptor) to enable these services              -->

    <!-- The mapping for the default servlet -->
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>


  ......
  </web-app>
```



 这里有一个很关键的描述

  <!-- The default servlet for all web applications, that serves static     -->
  <!-- resources.  It processes all requests that are not mapped to other   -->

这个默认的servlet 生效于所有的Servlet，生效于静态资源。

父的web.xml文件中有一个DefaultServlet,而url-pattern是/,

此时我们自己的应用中也配制了一个/，将覆写了父web.xml中的配制。

**那为什么不会拦截jsp呢？**

同样在再次查看tomcat的web.xml文件

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
    version="2.5">
    
     <servlet>
        <servlet-name>jsp</servlet-name>
        <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
        <init-param>
            <param-name>fork</param-name>
            <param-value>false</param-value>
        </init-param>
        <init-param>
            <param-name>xpoweredBy</param-name>
            <param-value>false</param-value>
        </init-param>
        <load-on-startup>3</load-on-startup>
    </servlet>
    
    ......
    
    <!-- The mapping for the JSP servlet -->
    <servlet-mapping>
        <servlet-name>jsp</servlet-name>
        <url-pattern>*.jsp</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>jsp</servlet-name>
        <url-pattern>*.jspx</url-pattern>
    </servlet-mapping>
    ......
</web-app>    
```

在默认的web.xml文件中对于jsp做了特别的处理，而在子的web.xml文件中又没有对其进行覆盖，所以，此jsp将直接生效。



因为在父的web.xml文件中有一个JspServlet，这个Servlet拦截jsp文件，而我们并没有覆写 这个配制。所以SpringMVC不拦截jsp文件，JSP的处理交给了tomcat。



### 如何配制为/解决拦截静态资源？



#### 方案1. 将请求交还给Tomcat容器处理

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

    <!--方案1，交给Tomcat容器处理-->
  	<!-- 会创建一个DefaultServletHttpRequestHandler对象，将检查DispatcherServlet的所有URL，进行筛查，发现为静态资源，将交给Tomcat容器处理。 -->
    <mvc:default-servlet-handler />
</beans>
```

测试查看一下效果

![image-20230202233830745](.\img\image-20230202233830745.png)

**问题**

​    针对此方案，相关的静态资源必须放置到webapp目录下，放置在其他目录下将不能访问。比如放置到classpath目录下。





#### 方案2，使用SpringMVC框架处理静态资源

在resources目录下创建一个res/res.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>data-res</title>
</head>
<body>
    this is test resource html
</body>
</html>
```

配制静态资源处理

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


    <!-- 方案二：使用SpringMVC提供的静态资源处理   -->
    <mvc:resources location="classpath:res/" mapping="/resource/**"/>
</beans>
```



来测试一下效果



![image-20230203090951560](.\img\image-20230203090951560.png)



此方案将不再受webapp目录的限制。















