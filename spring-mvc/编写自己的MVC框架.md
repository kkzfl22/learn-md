# 编写自己的MVC框架

## 1. 简要说明

此处仅编写一个简化版本的MVC框架，用于对MVC的执行流程进行演示。基于此来开发的一个demo的MVC框架。更多的是从原理层面了解MVC框架。

![image-20230212222255556](D:\work\myself\learn\learn-md\spring-mvc\img\image-mvc-flow-56.png)

## 2. 基础的项目文件

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.mvc</groupId>
    <artifactId>nullnull-mvc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>


    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>

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
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                    <compilerArgs>
                        <!--告诉编译器保留参数-->
                        <arg>-parameters</arg>
                    </compilerArgs>
                </configuration>
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



web.xml文件

```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>

    <display-name>mvc-demo</display-name>
    <servlet>
        <servlet-name>mvc-DispatcherServlet</servlet-name>
        <servlet-class>com.nullnull.mvc.servlet.NullNullDispatcherServlet</servlet-class>
        <init-param>
            <param-name>mvcContextPath</param-name>
            <param-value>mvc.properties</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc-DispatcherServlet</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
</web-app>
```

这里是演示效果使用了Properties文件来进行相关的配制工作。



## 3. mvc.properties配制

```
scanBasePackage=com.nullnull.demo
```





## 4. 注解相关的类

@NullNullAutowire

src\main\java\com\nullnull\mvc\annotation\NullNullAutowire.java

```java
package com.nullnull.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullNullAutowire {
  String value() default "";
}

```



@NullNullController

src\main\java\com\nullnull\mvc\annotation\NullNullController.java

```java
package com.nullnull.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullNullController {
  String value() default "";
}

```



@NullNullRequestMapping

src\main\java\com\nullnull\mvc\annotation\NullNullRequestMapping.java

```java
package com.nullnull.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullNullRequestMapping {
  String value() default "";
}

```



@NullNullService

src\main\java\com\nullnull\mvc\annotation\NullNullService.java

```java
package com.nullnull.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullNullService {
  String value() default "";
}
```



## 5. 处理的Servlet

src\main\java\com\nullnull\mvc\servlet\NullNullDispatcherServlet.java

```java
package com.nullnull.mvc.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class NullNullDispatcherServlet extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    // 1.读取配制文件
    String scanPath = getScanPath(config);

    // 2. 进行包扫描，加载相关的注解。
    doScan(scanPath);

    // 3. IOC容器相关的初始化工作。
    doInstance();

    // 4. 进行属性的注入操作
    doAutowire();

    // 5. 建立URL与Method之间的映射关系。
    doHandlerMapping();
  }
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
  }
}

```



## 6. 添加测试文件

src\main\java\com\nullnull\demo\controller\UserInfoController.java

```java
package com.nullnull.demo.controller;

import com.nullnull.demo.service.UserInfoService;
import com.nullnull.mvc.annotation.NullNullAutowire;
import com.nullnull.mvc.annotation.NullNullController;
import com.nullnull.mvc.annotation.NullNullRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户处理请求的控制器
 *
 * @author liujun
 * @since 2023/2/14
 */
@NullNullController
@NullNullRequestMapping("/userInfo")
public class UserInfoController {

  @NullNullAutowire private UserInfoService userInfoService;

  @NullNullRequestMapping("/getUserName")
  public void getUserName(HttpServletRequest request, HttpServletResponse response, String name)
      throws IOException {
    String dataInfo = userInfoService.getUserName(name);
    response.getWriter().write(dataInfo);
  }
}

```

src\main\java\com\nullnull\demo\service\UserInfoService.java

```java
package com.nullnull.demo.service;

/**
 * @author liujun
 * @since 2023/2/15
 */
public interface UserInfoService {
  /**
   * 获取用户名的服务方法
   *
   * @return 随机的用户
   */
  String getUserName(String name);
}

```

这是一个演示的基础项目，当用户在浏览器中输入访问地址后，便随机返回一个数字的用户名。

src\main\java\com\nullnull\demo\service\UserInfoServiceImpl.java

```java
package com.nullnull.demo.service;

import com.nullnull.mvc.annotation.NullNullService;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 用户处理的服务
 *
 * @author liujun
 * @since 2023/2/14
 */
@NullNullService
public class UserInfoServiceImpl implements UserInfoService {

  /**
   * 获取用户名的服务方法
   *
   * @return 随机的用户
   */
  @Override
  public String getUserName(String name) {
    return name + "-->" + String.valueOf(ThreadLocalRandom.current().nextInt());
  }
}
```







## 7. 编写处理的Servlet

### 7.1 编写配制文件加载

```java
  /**
   * 加载配制文件
   *
   * @param config 配制路径
   * @return 扫描的路径
   */
  private String getScanPath(ServletConfig config) {
    String path = config.getInitParameter("mvcContextPath");

    Properties properties = new Properties();
    try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path); ) {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return String.valueOf(properties.get("scanBasePackage"));
  }
```

### 7.2 编写包扫描程序

```java
  /** 扫描的包结构集合 */
  private List<String> scanPkgList = new ArrayList<>();


  /**
   * 目录文件的扫描操作
   *
   * @param path
   */
  private void doScan(String path) {
    // 基础目录
    String pathData =
        Thread.currentThread().getContextClassLoader().getResource("").getPath()
            + path.replaceAll("\\.", "/");

    // 扫描操作
    File data = new File(pathData);

    File[] fileList = data.listFiles();
    for (File item : fileList) {
      //如果为目录，则继续递归
      if (item.isDirectory()) {
        doScan(path + "." + item.getName());
      }
      // 如果当前为文件，则加入至容器中
      else if (item.isFile()) {
        String fileName = item.getName();
        // 非class文件则跳过
        if (!fileName.endsWith(".class")) {
          return;
        }

        // 获取上下文路径
        String className = path + "." + fileName.replaceAll(".class", "");
        // 保存路径
        scanPkgList.add(className);
      }
    }
  }
```

### 7.3 进行对象的实例化操作

```java
  /** 实例容器对象 */
  private Map<String, Object> iocMap = new HashMap<>();

  /** 执行对象的初始化操作 */
  private void doInstance() {
    if (scanPkgList.isEmpty()) {
      return;
    }

    try {
      for (String pkgItem : scanPkgList) {
        // 进行反射加检查操作
        Class pkgClassItem = Class.forName(pkgItem);

        // 检查当前是否存在Service注解。
        if (pkgClassItem.isAnnotationPresent(NullNullService.class)) {

          NullNullService serviceClass =
              (NullNullService) pkgClassItem.getAnnotation(NullNullService.class);
          String value = serviceClass.value();
          // 实例对象
          Object newInstance = pkgClassItem.newInstance();
          // 如果当前设置了实例名，优先使用实例名称，未设置使用默认的首字母小字的名称
          if (!"".equals(value)) {
            iocMap.put(value, newInstance);
          } else {
            String className = pkgClassItem.getSimpleName();
            iocMap.put(toJavaLowFileName(className), newInstance);
          }

          // 在其于面向接口的编程中,可以使用接口注入
          Class<?>[] interfaceClass = pkgClassItem.getInterfaces();
          if (interfaceClass != null && interfaceClass.length > 0) {
            for (Class item : interfaceClass) {
              iocMap.put(item.getSimpleName(), newInstance);
            }
          }
        }
        // 检查当前是否存在@Controller注解
        else if (pkgClassItem.isAnnotationPresent(NullNullController.class)) {
          NullNullController serviceClass =
              (NullNullController) pkgClassItem.getAnnotation(NullNullController.class);
          String value = serviceClass.value();
          // 实例对象
          Object newInstance = pkgClassItem.newInstance();
          // 如果当前设置了实例名，优先使用实例名称，未设置使用默认的首字母小字的名称
          if (!"".equals(value)) {
            iocMap.put(value, newInstance);
          } else {
            String className = pkgClassItem.getSimpleName();
            iocMap.put(toJavaLowFileName(className), newInstance);
          }
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }


  /**
   * 将类首字线改为小写的开头
   *
   * @param name
   * @return
   */
  private String toJavaLowFileName(String name) {
    char[] fileNameArray = name.toCharArray();
    if (fileNameArray[0] >= 'A' && fileNameArray[0] <= 'Z') {
      fileNameArray[0] += 32;
    }
    return String.valueOf(fileNameArray);
  }
```

### 7.4 属性注入操作

```java
  /** 进行属性的注入操作 */
  private void doAutowire() {
    if (iocMap.isEmpty()) {
      return;
    }

    try {
      for (Map.Entry<String, Object> objInstance : iocMap.entrySet()) {
        Class objClass = objInstance.getValue().getClass();
        // 获取定义的属性
        Field[] fileItem = objClass.getDeclaredFields();
        // 检查属性的注解中，是否存在@AutoWire
        for (Field autowireField : fileItem) {
          // 如果不存在，则跳过
          if (!autowireField.isAnnotationPresent(NullNullAutowire.class)) {
            continue;
          }
          // 存在，则进行注入操作
          NullNullAutowire autowireAnnotation = autowireField.getAnnotation(NullNullAutowire.class);
          String instanceName = autowireAnnotation.value();

          Object iocGetInstance = null;

          if (!"".equals(instanceName)) {
            iocGetInstance = iocMap.get(instanceName);
          }
          // 如果为空，则使用接口注入
          else {
            String interfaceName = autowireField.getType().getSimpleName();
            iocGetInstance = iocMap.get(interfaceName);
          }

          // 执行注入操作
          autowireField.setAccessible(Boolean.TRUE);
          autowireField.set(objInstance.getValue(), iocGetInstance);
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
```



### 7.5 建立方法与对象的映射

这里首先需要创建一个对象，由于方法的执行，需要实例对象、参数对象，所以需要对这些参数进行封装。

```java
package com.nullnull.mvc.bean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 方法映射的对象信息
 *
 * @author liujun
 * @since 2023/2/15
 */
public class MethodMapping {

  /** 实例对象 */
  private Object objInstance;

  /** URL对象信息 */
  private String url;

  /** 方法信息 */
  private Method method;

  /** 参数顺序信息,为参数做绑定做准备,key为参数名，值为索引号 */
  private Map<String, Integer> param;

  public MethodMapping(Object objInstance, String url, Method method) {
    this.objInstance = objInstance;
    this.url = url;
    this.method = method;
    this.param = new HashMap<>();
  }

  public Object getObjInstance() {
    return objInstance;
  }

  public String getUrl() {
    return url;
  }

  public Method getMethod() {
    return method;
  }

  public Map<String, Integer> getParam() {
    return param;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MethodMapping{");
    sb.append("objInstance=").append(objInstance);
    sb.append(", url='").append(url).append('\'');
    sb.append(", method=").append(method);
    sb.append(", param=").append(param);
    sb.append('}');
    return sb.toString();
  }
}

```

URL与方法的映射

```java
  /** 方法映射,将URL与方法进行映射 */
  private Map<String, MethodMapping> urlMethodMapping = new HashMap<>();

  /** 建立URL与方法映射 */
  private void doHandlerMapping() {
    if (iocMap.isEmpty()) {
      return;
    }

    // 进行对象的扫描
    for (Map.Entry<String, Object> objInstance : iocMap.entrySet()) {
      Class objClass = objInstance.getValue().getClass();
      // 跳过非Controller对象
      if (!objClass.isAnnotationPresent(NullNullController.class)) {
        continue;
      }
      // 1,获取类上的RequestMapping映射路径
      NullNullRequestMapping requestMappingClassUrl =
          (NullNullRequestMapping) objClass.getAnnotation(NullNullRequestMapping.class);
      String classRequestUrl = requestMappingClassUrl.value();

      // 2.进行方法的遍历的操作
      Method[] declaredMethods = objClass.getDeclaredMethods();
      for (Method declaredMethod : declaredMethods) {
        // 提取方法上的映射
        NullNullRequestMapping methodAnnotationUrl =
            declaredMethod.getDeclaredAnnotation(NullNullRequestMapping.class);

        // 方法的URL
        String url = classRequestUrl + methodAnnotationUrl.value();

        MethodMapping methodMapping =
            new MethodMapping(objInstance.getValue(), url, declaredMethod);

        // 2.提取方法的对象
        Parameter[] methodParams = declaredMethod.getParameters();

        // 读取参数
        for (int i = 0; i < methodParams.length; i++) {
          Parameter parameterItem = methodParams[i];
          if (parameterItem.getType() == HttpServletRequest.class
              || parameterItem.getType() == HttpServletResponse.class) {
            methodMapping.getParam().put(parameterItem.getType().getSimpleName(), i);
          } else {
            // 使用参数名称，此注意需要修改编译器，让其携带参数名，不然获取不到参数名
            methodMapping.getParam().put(parameterItem.getName(), i);
          }
        }

        urlMethodMapping.put(url, methodMapping);
      }
    }
  }
```



## 8.编写请求处理

还上HttpServlet的请求处理的Service方法

```java
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // 1,获取当前请求的地址
    String requestUri = req.getRequestURI();

    MethodMapping runMethod = urlMethodMapping.get(requestUri);

    if (null == runMethod) {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write("method not found");
    }

    Object[] methodParam = new Object[runMethod.getParam().size()];

    // 进行参数的封装操作
    Map<String, String[]> requestParam = req.getParameterMap();
    for (Map.Entry<String, Integer> entryItem : runMethod.getParam().entrySet()) {

      // 如果当前为request对象
      if (entryItem.getKey().equals(HttpServletRequest.class.getSimpleName())) {
        methodParam[entryItem.getValue()] = req;
      }
      // 如果当前响应对象
      else if (entryItem.getKey().equals(HttpServletResponse.class.getSimpleName())) {
        methodParam[entryItem.getValue()] = resp;
      } else {
        methodParam[entryItem.getValue()] = requestString(requestParam.get(entryItem.getKey()));
      }
    }

    try {
      // 进行请求方法的执行
      runMethod.getMethod().invoke(runMethod.getObjInstance(), methodParam);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
```



## 9. 测试

在浏览器中输入：

http://127.0.0.1:8080/userInfo/getUserName?name=nullnull

正确的结果将会看到类似：

```
nullnull-->1134007633
```



## 10. 注意

由于编译器默认是将参数不进行保存的，所以会导入参数名称无法传递，所以，需要给编译添加参数,也就是Maven中的这段

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                    <compilerArgs>
                        <!--告诉编译器保留参数-->
                        <arg>-parameters</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
```

