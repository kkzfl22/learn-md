# Spring+SpringMVC+MyBatis整合



## 1. 整合Spring+MyBatis

### 1.1 maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.ssm</groupId>
    <artifactId>ssm</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <name>ssm Maven Webapp</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <!--junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>
        <!--spring相关-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.9</version>
        </dependency>
        <!--mybatis与spring的整合包-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.3</version>
        </dependency>
        <!--数据库驱动jar-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>
        <!--druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.21</version>
        </dependency>
				
				<!--sqlliteJDBC驱动-->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.30.1</version>
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



### 1.2 进行相关包的创建

在maven工程的src/main/java目录下，创建以下目录

```
com.nullnull.ssm.mapper
com.nullnull.ssm.service
com.nullnull.ssm.entity
```



### 1.3 进行Spring的数据库配制

applicationcontext-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
 http://www.springframework.org/schema/beans
 http://www.springframework.org/schema/beans/spring-beans.xsd
 http://www.springframework.org/schema/context
 http://www.springframework.org/schema/context/spring-context.xsd
 http://www.springframework.org/schema/tx
 http://www.springframework.org/schema/tx/spring-tx.xsd
">
    <!--配制数据库的包扫描-->
    <context:component-scan base-package="com.nullnull.ssm.mapper"/>

    <!--加载资源文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!--定义数据库连接池-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!--定义SqlSessionFactor对象-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--别名映射扫描-->
        <property name="typeAliasesPackage" value="com.nullnull.ssm.entity"/>
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--动态代理产生的对象交给Spring管理-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.nullnull.ssm.mapper"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>

</beans>
```

jdbc.properties

```properties
jdbc.driver=org.sqlite.JDBC
jdbc.url=jdbc:sqlite:/D:/java/workspace/selfwork/github/learn/mybatis/db/mybatis.db
jdbc.username=
jdbc.password=
```



### 1.4 进行service的配制

applicationcontext-service.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
 http://www.springframework.org/schema/beans
 http://www.springframework.org/schema/beans/spring-beans.xsd
 http://www.springframework.org/schema/context
 http://www.springframework.org/schema/context/spring-context.xsd
 http://www.springframework.org/schema/tx
 http://www.springframework.org/schema/tx/spring-tx.xsd
">
    <!--配制service的包扫描-->
    <context:component-scan base-package="com.nullnull.ssm.service"/>

    <!--事务管理-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--事务管理器注解驱动-->
    <tx:annotation-driven transaction-manager="transactionManager"/>


</beans>
```

### 1.5 编写实体信息

```java
package com.nullnull.ssm.entity;

/**
 * 用户对象
 *
 * @author nullnull
 * @since 2022/6/29
 */
public class UserMsg {

    /**
     * 用户的id
     */
    private Integer id;

    /**
     * 名称的信息
     */
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
        final StringBuilder sb = new StringBuilder("UserMsgPO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

```





### 1.6 开始编写mybatis的mapper的接口

```java
package com.nullnull.ssm.mapper;

import com.nullnull.ssm.entity.UserMsg;

import java.util.List;

/**
 * 基于mybatis的SQL操作
 *
 * @author nullnull
 * @since 2023/2/20
 */
public interface UserMsgMapper {


    /**
     * 数据保存
     *
     * @param user
     * @return
     */
    int insert(UserMsg user);


    /**
     * 查询所有的用户信息
     *
     * @return
     */
    List<UserMsg> queryAll();

}

```

### 1.7 编写mapper的数据库查询实现

src\main\resources\com\nullnull\ssm\mapper\UserMsgMapper.xml

```
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nullnull.ssm.mapper.UserMsgMapper">
    <insert id="insert" parameterType="com.nullnull.ssm.entity.UserMsg">
        insert into user_msg(id,name)
        values(#{id},#{name})
    </insert>

    <select id="queryAll" resultType="com.nullnull.ssm.entity.UserMsg">
        select * from user_msg
    </select>

</mapper>
```



### 1.8 编写service的实现

```java
package com.nullnull.ssm.service;

import com.nullnull.ssm.entity.UserMsg;
import com.nullnull.ssm.mapper.UserMsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户操作的服务
 *
 * @author nullnull
 * @since 2023/2/20
 */
@Service
public class UserMsgService {


    /**
     * 用户服务
     */
    @Autowired
    private UserMsgMapper userMsgMapper;


    public boolean insert(UserMsg user) {
        int insert = userMsgMapper.insert(user);

        return insert > 0;
    }

    public List<UserMsg> queryAll() {
        return userMsgMapper.queryAll();
    }
}

```

### 1.9 测试集成结果

编写一个单元测试

src\test\java\com\nullnull\ssm\service\TestUserMsgService.java

```java
package com.nullnull.ssm.service;

import com.nullnull.ssm.entity.UserMsg;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author nullnull
 * @since 2023/2/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationcontext-*.xml"})
public class TestUserMsgService {


    /**
     * 用户服务
     */
    @Autowired
    private UserMsgService userMsgService;


    @Test
    public void runData() {
        UserMsg userMsg = new UserMsg();
        userMsg.setId(321);
        userMsg.setName("dataName321");

        boolean addRsp = userMsgService.insert(userMsg);
        Assert.assertEquals(true, addRsp);

        List<UserMsg> dataList = userMsgService.queryAll();
        for (UserMsg msg : dataList) {
            System.out.println(msg);
        }
        Assert.assertNotEquals(0, dataList.size());
    }

}

```

结果信息：

```
UserMsgPO{id=1, name='updateName'}
UserMsgPO{id=2, name='name2'}
UserMsgPO{id=11, name='name11'}
UserMsgPO{id=100, name='testName100'}
UserMsgPO{id=-905957374, name='testName100'}
UserMsgPO{id=321, name='dataName321'}
UserMsgPO{id=23, name='dataName321'}
```

至此Spring+Mybatis的整合就已经成功了。

接下就开始整合SpringMVC

## 2. 整合SpringMVC

### 2.1 添加SpringMVC的依赖

```xml

        <!--SpringMVC-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <!--jsp-api&servlet-api-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <!--⻚⾯使⽤jstl表达式-->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
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
```

### 2.2 添加controller

```java
package com.nullnull.ssm.controller;

import com.nullnull.ssm.entity.UserMsg;
import com.nullnull.ssm.service.UserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author nullnull
 * @since 2023/2/21
 */
@Controller
@RequestMapping("/user")
public class UserMsgController {


    /**
     * 用户操作的服务
     */
    @Autowired
    private UserMsgService userMsgService;


    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<UserMsg> queryAll() {
        return userMsgService.queryAll();
    }
}

```

### 2.3 配制SpringMVC的配制文件

springmvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
 http://www.springframework.org/schema/beans/spring-beans.xsd
 http://www.springframework.org/schema/context
 http://www.springframework.org/schema/context/spring-context.xsd
 http://www.springframework.org/schema/mvc
 http://www.springframework.org/schema/mvc/spring-mvc.xsd
">
    <context:component-scan base-package="com.nullnull.ssm.controller"/>

    <mvc:annotation-driven/>

</beans>
```

### 2.4 配制web.xml

src\main\webapp\WEB-INF\web.xml

```
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >
<web-app>
    <display-name>ssm</display-name>


    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:applicationcontext*.xml</param-value>
    </context-param>

    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>


    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>


</web-app>

```



### 2.5 启动测试

在浏览器中输入

http://127.0.0.1:8080/user/queryAll

便可看到页面输出了

```
[{"id":1,"name":"updateName"},{"id":2,"name":"name2"},{"id":11,"name":"name11"},{"id":100,"name":"testName100"},{"id":-905957374,"name":"testName100"},{"id":321,"name":"dataName321"},{"id":23,"name":"dataName321"}]
```



至此与SpringMVC的整合已经成功。