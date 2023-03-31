# 1. Spring Boot 开发梳理

**文件说明**

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>spring-boot-demo</artifactId>
    <version>1.0-SNAPSHOT</version>


    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>


    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


</project>
```

com.nullnull.learn.boot.SpringBootDemoApplication

```
package com.nullnull.learn.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liujun
 * @since 2023/2/27
 */
@SpringBootApplication
public class SpringBootDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }
}

```

com.nullnull.learn.boot.controller.UserController

```
package com.nullnull.learn.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujun
 * @since 2023/2/27
 */
@RestController
public class UserController {

  @RequestMapping("/demo")
  public String demo() {
    return "hello spring boot";
  }
}


```

com.nullnull.learn.boot.controller.UserControllerTest

```
package com.nullnull.learn.boot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 标识当前为springboot的单元测试类，并加载项目的applicationContext上下文环境
 *
 * @author liujun
 * @since 2023/2/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserController.class})
public class UserControllerTest {

  @Autowired private UserController userController;

  @Test
  public void testHello() {
    String msg = userController.demo();
    System.out.println(msg);
  }
}

```





## 1. 基本用法

### 1.1 Spring boot的热部署

**1. 添加依赖**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
```



**2. IDEA打开热**

![image-20230227223048032](D:\work\myself\learn\learn-md\spring-mvc\img\boot-image-20230227223048032.png)

**3. 指定IDEA工具在程序运行过程中自动编译**

>快捷键:ctrl+shift+alt+/

![image-20230227223400297](D:\work\myself\learn\learn-md\spring-mvc\img\boot-image-20230227223400297.png)

**4.启动服务**



** 5. 浏览器测试**

http://127.0.0.1:8080/demo

```
hello spring boot
```



**6. 修改controller中的内容**

```
package com.nullnull.learn.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liujun
 * @since 2023/2/27
 */
@RestController
public class UserController {

  @RequestMapping("/demo")
  public String demo() {
    return "hello spring boot -- new";
  }
}
```

可以看到控制台自动进行了重启。



**7. 再次访问**

http://127.0.0.1:8080/demo

```
hello spring boot -- new
```





## 2. Spring boot使用properties关联Bean



### 2.1 添加依赖

检查pom.xml文件中，是否存在依赖

```
        <!--配制项的依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
```





### 2.2 编写关联的实体Person

```java
package com.nullnull.learn.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 配制文件关联 @ConfigurationProperties(prefix = "person")
 *
 * <p>将配制文件中以Person开头的属性注入到类中，而且可以在配制文件时，可以有关联的提示效果
 *
 * @author liujun
 * @since 2023/3/1
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

  /** 配制文件对应id */
  private int id;

  /** 名称信息 */
  private String name;

  /** 集合的爱好信息 */
  private List<Object> hobby;

  /** 家庭成员 */
  private String[] family;

  /** 使用Map的类型 */
  private Map<String, Object> map;

  /** 宠物信息 */
  private Pet pet;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Object> getHobby() {
    return hobby;
  }

  public void setHobby(List<Object> hobby) {
    this.hobby = hobby;
  }

  public String[] getFamily() {
    return family;
  }

  public void setFamily(String[] family) {
    this.family = family;
  }

  public Map<String, Object> getMap() {
    return map;
  }

  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  public Pet getPet() {
    return pet;
  }

  public void setPet(Pet pet) {
    this.pet = pet;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SpringBootConfigBean{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", hobby=").append(hobby);
    sb.append(", family=").append(Arrays.toString(family));
    sb.append(", map=").append(map);
    sb.append(", pet=").append(pet);
    sb.append('}');
    return sb.toString();
  }
}

```

pet.java

```java
package com.nullnull.learn.boot.config;

/**
 * 宠物信息
 *
 * @author liujun
 * @since 2023/3/1
 */
public class Pet {

  /** id */
  private String type;

  /** 宠物的名称 */
  private String name;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Pet{");
    sb.append("type='").append(type).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
```





### 2.3 配制application.properties文件

```properties
# 测试配制文件关联
person.id=1
person.name=nullnull
person.hobby=吃饭,睡觉，写代码
person.family=father,mather
person.map.k1=v1
person.map.k2=v2
person.pet.type=dog
person.pet.name=旺财
```



### 2.4 编写单元测试

```java
package com.nullnull.learn.boot.controller;

import com.nullnull.learn.boot.config.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 标识当前为springboot的单元测试类，并加载项目的applicationContext上下文环境
 *
 * @author liujun
 * @since 2023/2/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonConfigTest {

  @Autowired private Person personConfig;

  @Test
  public void testConfig() {
    System.out.println(personConfig);
    Assert.assertNotNull(personConfig);
  }
}

```



### 2.6 运行查看结果

当运行完单元测试后，就可以看到控制台输出：

```
SpringBootConfigBean{id=1, name='nullnull', hobby=[吃饭, 睡觉，写代码], family=[father, mather], map={k1=v1, k2=v2}, pet=Pet{type='dog', name='旺财'}}
```

单元测试也运行成功。



## 3 Spring boot使用yml文件配制关联的Bean

### 3.1 编写关联的config的bean

与properties相同



### 3.2 添加application.yml文件

为了此运行，记得删除掉application.properties

```
person:
  id: 11
  name: cat
  hobby: [吃饭,睡觉,写代码]
  family: [father,mather]
  map: {key:v1,k2:v2}
  pet: {type:dog,name:旺财}
```

### 3.3 单元测试

与properties相同



### 3.4 查看结果

```
SpringBootConfigBean{id=11, name='cat', hobby=[吃饭, 睡觉, 写代码], family=[father, mather], map={keyv1=, k2v2=}, pet=null}
```

经过验证springboot同时也可以使用yml文件来配制关联的Bean



## 4. 配制文件值的注入

### 4.1 使用@ConfigurationProperties

在之前的案例中已经使用此注解将属性注入给对象，此为批次注入多个对象，需要添加set方法进行注入操作。



### 4.2 使有@Value

application.yml

```
person:
  id: 11
  name: cat
  hobby: [吃饭,睡觉,写代码]
  family: [father,mather]
  map: {key:v1,k2:v2}
  pet: {type:dog,name:旺财}
```







Student.java

```
package com.nullnull.learn.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {

  @Value("33")
  private Integer id;

  @Value("${person.name}")
  private String name;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Student{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
```

单元测试

```
package com.nullnull.learn.boot.controller;

import com.nullnull.learn.boot.config.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {

  @Autowired private Student student;

  @Test
  public void studentTest() {
    System.out.println(student);
    Assert.assertNotNull(student);
  }
}

```



控制台输出检查

```
Student{id=33, name='cat'}
```



## 5. 自定义配制文件加载

### 5.1 自定义properties加载

配制自定义配制文件testapplication.properties

```properties
test.person.id=22
test.person.name=nullnull22
test.person.hobby=吃饭,睡觉，写代码,写测试
test.person.family=father,mather
test.person.map.k1=v11
test.person.map.k2=v22
test.person.pet.type=dog1
test.person.pet.name=旺财1
```

配制相关的实体信息

```java
package com.nullnull.learn.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 配制文件关联 @ConfigurationProperties(prefix = "person")
 *
 * <p>将配制文件中以Person开头的属性注入到类中，而且可以在配制文件时，可以有关联的提示效果
 *
 * <p>PropertySource默认不支持加载yaml文件
 *
 * @author liujun
 * @since 2023/3/1
 */
@Component
@PropertySource("classpath:testapplication.properties")
@ConfigurationProperties(prefix = "test.person")
public class TestPerson {

  /** 配制文件对应id */
  private int id;

  /** 名称信息 */
  private String name;

  /** 集合的爱好信息 */
  private List<Object> hobby;

  /** 家庭成员 */
  private String[] family;

  /** 使用Map的类型 */
  private Map<String, Object> map;

  /** 宠物信息 */
  private Pet pet;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Object> getHobby() {
    return hobby;
  }

  public void setHobby(List<Object> hobby) {
    this.hobby = hobby;
  }

  public String[] getFamily() {
    return family;
  }

  public void setFamily(String[] family) {
    this.family = family;
  }

  public Map<String, Object> getMap() {
    return map;
  }

  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  public Pet getPet() {
    return pet;
  }

  public void setPet(Pet pet) {
    this.pet = pet;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SpringBootConfigBean{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", hobby=").append(hobby);
    sb.append(", family=").append(Arrays.toString(family));
    sb.append(", map=").append(map);
    sb.append(", pet=").append(pet);
    sb.append('}');
    return sb.toString();
  }
}

```

执行单元测试

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPersonConfigTest {

  @Autowired private TestPerson personConfig;

  @Test
  public void testConfig() {
    System.out.println(personConfig);
    Assert.assertNotNull(personConfig);
  }
}
```

控制台输出

```tex
SpringBootConfigBean{id=22, name='nullnull22', hobby=[吃饭, 睡觉，写代码, 写测试], family=[father, mather], map={k2=v22, k1=v11}, pet=Pet{type='dog1', name='旺财1'}}
```



### 5.2 随机参数及引用

SpringBoot提供了随机值的生成值

randomApplication.properties

```
# 随机整数
test.random.id=${random.int}
# 随机值
test.random.name=${random.value}
# 随机long类型
test.random.longValue=${random.long}
# 随机uuid
test.random.uuid=${random.uuid}
# 小于值
test.random.lessThen=${random.int(100)}
# 随机范围值
test.random.scope=${random.int[10,99]}
# 值引用
test.random.referenceValue=这是一个值的引用${test.random.uuid}，数字${test.random.id}
```

进行参数绑定

TestRandomValue.java

```
package com.nullnull.learn.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
@PropertySource("classpath:randomApplication.properties")
@ConfigurationProperties(prefix = "test.random")
public class TestRandomValue {

  /** 配制文件对应id */
  private int id;

  /** 名称信息 */
  private String name;

  /** 长整形值 */
  private Long longValue;

  /** uuid的值信息 */
  private String uuid;

  /** 小于一个数的值 */
  private int lessThen;

  private int scope;

  /** 值引用 */
  private String referenceValue;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getLongValue() {
    return longValue;
  }

  public void setLongValue(Long longValue) {
    this.longValue = longValue;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public int getLessThen() {
    return lessThen;
  }

  public void setLessThen(int lessThen) {
    this.lessThen = lessThen;
  }

  public int getScope() {
    return scope;
  }

  public void setScope(int scope) {
    this.scope = scope;
  }

  public String getReferenceValue() {
    return referenceValue;
  }

  public void setReferenceValue(String referenceValue) {
    this.referenceValue = referenceValue;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TestRandomValue{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", longValue=").append(longValue);
    sb.append(", uuid='").append(uuid).append('\'');
    sb.append(", lessThen=").append(lessThen);
    sb.append(", scope=").append(scope);
    sb.append(", referenceValue=").append(referenceValue);
    sb.append('}');
    return sb.toString();
  }
}

```

进行单元测试

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRandomConfigTest {

  @Autowired private TestRandomValue randomValue;

  @Test
  public void testRandomConfig() {
    System.out.println(randomValue);
    Assert.assertNotNull(randomValue);
  }
}
```

运行结果

```
TestRandomValue{id=841890395, name='e854a29fbff348dc79c338f084fd64f8', longValue=4961008847912314911, uuid='7bc1f441-93e8-4c24-bc99-884e4dab799f', lessThen=88, scope=74, referenceValue=这是一个值的引用58690a7b-8c6a-4eeb-91a0-d5180072e818，数字1302854337}
```

# 2. Spring Boot源码阅读

要读取源码，还是从启动类开始吧

地址 https://codeload.github.com/spring-projects/spring-boot/zip/refs/heads/2.2.x

就以案例的启动类为起点

```java
@SpringBootApplication
public class SpringBootDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }
}
```



### 2.1 @SpringBootApplication源码

此时打开@SpringBootApplication的源码

```java
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.repository.Repository;

/**
 * Indicates a {@link Configuration configuration} class that declares one or more
 * {@link Bean @Bean} methods and also triggers {@link EnableAutoConfiguration
 * auto-configuration} and {@link ComponentScan component scanning}. This is a convenience
 * annotation that is equivalent to declaring {@code @Configuration},
 * {@code @EnableAutoConfiguration} and {@code @ComponentScan}.
 *
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @author Andy Wilkinson
 * @since 1.2.0
 */
//注解适用范围，type表示注解可以在类、接口、注解或枚举中
@Target(ElementType.TYPE)
//表示注解的生命周期，runtime表示运行中
@Retention(RetentionPolicy.RUNTIME)
//表示注解可以记录在javadoc中
@Documented
//表示可以被子类继承的注解
@Inherited
//以上是注解的一些自带的属性配制


//自定义注解
//表示该类为配制类
@SpringBootConfiguration
//表示自动配制类
@EnableAutoConfiguration
//包扫描器，类似<context:component-scan base-package="com.xxx.xxx"/>
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {

	/**
	 * Exclude specific auto-configuration classes such that they will never be applied.
	 * @return the classes to exclude
	 */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	Class<?>[] exclude() default {};

	/**
	 * Exclude specific auto-configuration class names such that they will never be
	 * applied.
	 * @return the class names to exclude
	 * @since 1.3.0
	 */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	String[] excludeName() default {};

	/**
	 * Base packages to scan for annotated components. Use {@link #scanBasePackageClasses}
	 * for a type-safe alternative to String-based package names.
	 * <p>
	 * <strong>Note:</strong> this setting is an alias for
	 * {@link ComponentScan @ComponentScan} only. It has no effect on {@code @Entity}
	 * scanning or Spring Data {@link Repository} scanning. For those you should add
	 * {@link org.springframework.boot.autoconfigure.domain.EntityScan @EntityScan} and
	 * {@code @Enable...Repositories} annotations.
	 * @return base packages to scan
	 * @since 1.3.0
	 */
	@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
	String[] scanBasePackages() default {};

	/**
	 * Type-safe alternative to {@link #scanBasePackages} for specifying the packages to
	 * scan for annotated components. The package of each class specified will be scanned.
	 * <p>
	 * Consider creating a special no-op marker class or interface in each package that
	 * serves no purpose other than being referenced by this attribute.
	 * <p>
	 * <strong>Note:</strong> this setting is an alias for
	 * {@link ComponentScan @ComponentScan} only. It has no effect on {@code @Entity}
	 * scanning or Spring Data {@link Repository} scanning. For those you should add
	 * {@link org.springframework.boot.autoconfigure.domain.EntityScan @EntityScan} and
	 * {@code @Enable...Repositories} annotations.
	 * @return base packages to scan
	 * @since 1.3.0
	 */
	@AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
	Class<?>[] scanBasePackageClasses() default {};

	/**
	 * Specify whether {@link Bean @Bean} methods should get proxied in order to enforce
	 * bean lifecycle behavior, e.g. to return shared singleton bean instances even in
	 * case of direct {@code @Bean} method calls in user code. This feature requires
	 * method interception, implemented through a runtime-generated CGLIB subclass which
	 * comes with limitations such as the configuration class and its methods not being
	 * allowed to declare {@code final}.
	 * <p>
	 * The default is {@code true}, allowing for 'inter-bean references' within the
	 * configuration class as well as for external calls to this configuration's
	 * {@code @Bean} methods, e.g. from another configuration class. If this is not needed
	 * since each of this particular configuration's {@code @Bean} methods is
	 * self-contained and designed as a plain factory method for container use, switch
	 * this flag to {@code false} in order to avoid CGLIB subclass processing.
	 * <p>
	 * Turning off bean method interception effectively processes {@code @Bean} methods
	 * individually like when declared on non-{@code @Configuration} classes, a.k.a.
	 * "@Bean Lite Mode" (see {@link Bean @Bean's javadoc}). It is therefore behaviorally
	 * equivalent to removing the {@code @Configuration} stereotype.
	 * @since 2.2
	 * @return whether to proxy {@code @Bean} methods
	 */
	@AliasFor(annotation = Configuration.class)
	boolean proxyBeanMethods() default true;

}
```



### 2.2 @SpringBootConfiguration注解源码

现在开始看看SpringBootConfiguration的源码

```java
package org.springframework.boot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that a class provides Spring Boot application
 * {@link Configuration @Configuration}. Can be used as an alternative to the Spring's
 * standard {@code @Configuration} annotation so that configuration can be found
 * automatically (for example in tests).
 * <p>
 * Application should only ever include <em>one</em> {@code @SpringBootConfiguration} and
 * most idiomatic Spring Boot applications will inherit it from
 * {@code @SpringBootApplication}.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @since 1.4.0
 */
//注解的范围，TYPE表示可以在类、接口、注解或者枚举上
@Target(ElementType.TYPE)
//表示生命周期，RUNTIME表示运行时
@Retention(RetentionPolicy.RUNTIME)
//表示注解可以记录在javadoc中
@Documented
//以上为注解的属性配制


//@Configuration是一个Spring的注解，表示该类为配制类，因此@SpringBootConfiguration是对配制类的一个封装。本质还是一个配制类
@Configuration
public @interface SpringBootConfiguration {

	/**
	 * Specify whether {@link Bean @Bean} methods should get proxied in order to enforce
	 * bean lifecycle behavior, e.g. to return shared singleton bean instances even in
	 * case of direct {@code @Bean} method calls in user code. This feature requires
	 * method interception, implemented through a runtime-generated CGLIB subclass which
	 * comes with limitations such as the configuration class and its methods not being
	 * allowed to declare {@code final}.
	 * <p>
	 * The default is {@code true}, allowing for 'inter-bean references' within the
	 * configuration class as well as for external calls to this configuration's
	 * {@code @Bean} methods, e.g. from another configuration class. If this is not needed
	 * since each of this particular configuration's {@code @Bean} methods is
	 * self-contained and designed as a plain factory method for container use, switch
	 * this flag to {@code false} in order to avoid CGLIB subclass processing.
	 * <p>
	 * Turning off bean method interception effectively processes {@code @Bean} methods
	 * individually like when declared on non-{@code @Configuration} classes, a.k.a.
	 * "@Bean Lite Mode" (see {@link Bean @Bean's javadoc}). It is therefore behaviorally
	 * equivalent to removing the {@code @Configuration} stereotype.
	 * @return whether to proxy {@code @Bean} methods
	 * @since 2.2
	 */
	@AliasFor(annotation = Configuration.class)
	boolean proxyBeanMethods() default true;

}

```

### 2.3 @EnableAutoConfiguration注解源码

```java
package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * Enable auto-configuration of the Spring Application Context, attempting to guess and
 * configure beans that you are likely to need. Auto-configuration classes are usually
 * applied based on your classpath and what beans you have defined. For example, if you
 * have {@code tomcat-embedded.jar} on your classpath you are likely to want a
 * {@link TomcatServletWebServerFactory} (unless you have defined your own
 * {@link ServletWebServerFactory} bean).
 * <p>
 * When using {@link SpringBootApplication @SpringBootApplication}, the auto-configuration
 * of the context is automatically enabled and adding this annotation has therefore no
 * additional effect.
 * <p>
 * Auto-configuration tries to be as intelligent as possible and will back-away as you
 * define more of your own configuration. You can always manually {@link #exclude()} any
 * configuration that you never want to apply (use {@link #excludeName()} if you don't
 * have access to them). You can also exclude them via the
 * {@code spring.autoconfigure.exclude} property. Auto-configuration is always applied
 * after user-defined beans have been registered.
 * <p>
 * The package of the class that is annotated with {@code @EnableAutoConfiguration},
 * usually via {@code @SpringBootApplication}, has specific significance and is often used
 * as a 'default'. For example, it will be used when scanning for {@code @Entity} classes.
 * It is generally recommended that you place {@code @EnableAutoConfiguration} (if you're
 * not using {@code @SpringBootApplication}) in a root package so that all sub-packages
 * and classes can be searched.
 * <p>
 * Auto-configuration classes are regular Spring {@link Configuration @Configuration}
 * beans. They are located using the {@link SpringFactoriesLoader} mechanism (keyed
 * against this class). Generally auto-configuration beans are
 * {@link Conditional @Conditional} beans (most often using
 * {@link ConditionalOnClass @ConditionalOnClass} and
 * {@link ConditionalOnMissingBean @ConditionalOnMissingBean} annotations).
 *
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @since 1.0.0
 * @see ConditionalOnBean
 * @see ConditionalOnMissingBean
 * @see ConditionalOnClass
 * @see AutoConfigureAfter
 * @see SpringBootApplication
 */
//注解的范围，TYPE表示可以在类、接口、注解或者枚举上
@Target(ElementType.TYPE)
//表示生命周期，RUNTIME表示运行时
@Retention(RetentionPolicy.RUNTIME)
//表示注解可以记录在javadoc中
@Documented
//表示可以被子类继承的注解
@Inherited
//以上为注解的基本属性


//自动配制包：会把@SpringBootApplication注解标注的类所在的包名拿到，并对该包及其子包进行扫描，将组件添加到容器中
@AutoConfigurationPackage
//可以帮助Springboot应用将所有符合条件的@Configuration配置都加载到当前的SpringBoot创建并使用的IOC容器（ApplicationContext)中
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

	/**
	 * Environment property that can be used to override when auto-configuration is
	 * enabled.
	 */
	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

	/**
	 * Exclude specific auto-configuration classes such that they will never be applied.
	 * @return the classes to exclude
	 */
	Class<?>[] exclude() default {};

	/**
	 * Exclude specific auto-configuration class names such that they will never be
	 * applied.
	 * @return the class names to exclude
	 * @since 1.3.0
	 */
	String[] excludeName() default {};

}

```

#### 2.3.1 @AutoConfigurationPackage注解源码

```java
/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Indicates that the package containing the annotated class should be registered with
 * {@link AutoConfigurationPackages}.
 *
 * @author Phillip Webb
 * @since 1.3.0
 * @see AutoConfigurationPackages
 */
//注解范围，TYPE表示可以在类、接口、注解或者枚举上
@Target(ElementType.TYPE)
//表示生命周期，RUNTIME表示运行时
@Retention(RetentionPolicy.RUNTIME)
//表示注解可以记录在JAVADoc中
@Documented
//可以被子类继承
@Inherited
//以上为注解的基本属性


//@Import是Spring框架的底层注解，它的作用就是给容器中导入某个组件类
//@Import(AutoConfigurationPackages.Registrar.class)是将Registrar这个组件类导入到容器中
//Registrar默认将主配制类@SpringBootApplication所在的包及其子包里面的所有组件扫描到Spring容器中
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {

}

```

##### 2.3.1.1 导入AutoConfigurationPackages的源码

```java
package org.springframework.boot.autoconfigure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * Class for storing auto-configuration packages for reference later (e.g. by JPA entity
 * scanner).
 *
 * @author Phillip Webb
 * @author Dave Syer
 * @author Oliver Gierke
 * @since 1.0.0
 */
public abstract class AutoConfigurationPackages {

	private static final Log logger = LogFactory.getLog(AutoConfigurationPackages.class);

	private static final String BEAN = AutoConfigurationPackages.class.getName();

......

	/**
	 * Programmatically registers the auto-configuration package names. Subsequent
	 * invocations will add the given package names to those that have already been
	 * registered. You can use this method to manually define the base packages that will
	 * be used for a given {@link BeanDefinitionRegistry}. Generally it's recommended that
	 * you don't call this method directly, but instead rely on the default convention
	 * where the package name is set from your {@code @EnableAutoConfiguration}
	 * configuration class or classes.
	 * @param registry the bean definition registry
	 * @param packageNames the package names to set
	 */
	public static void register(BeanDefinitionRegistry registry, String... packageNames) {
		//如果已经存在该BEAN，则修改期其包的（）
		if (registry.containsBeanDefinition(BEAN)) {
			BeanDefinition beanDefinition = registry.getBeanDefinition(BEAN);
			ConstructorArgumentValues constructorArguments = beanDefinition.getConstructorArgumentValues();
			//将构造函数的第一个参数，设置为包名列表
			constructorArguments.addIndexedArgumentValue(0, addBasePackages(constructorArguments, packageNames));
		}
		//如果不存在该BEAN，则创建一个，并进行注册
		else {
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
			//将BeanClass设置为BasePackages
			beanDefinition.setBeanClass(BasePackages.class);
			//将构造函数的第一个参数设置为包名列表，也就是BasePackages的构造函数
			beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, packageNames);
			beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			registry.registerBeanDefinition(BEAN, beanDefinition);
		}
	}

	private static String[] addBasePackages(ConstructorArgumentValues constructorArguments, String[] packageNames) {
		//获取已经存在的
		String[] existing = (String[]) constructorArguments.getIndexedArgumentValue(0, String[].class).getValue();
		//对包进行合并操作
		Set<String> merged = new LinkedHashSet<>();
		merged.addAll(Arrays.asList(existing));
		merged.addAll(Arrays.asList(packageNames));
		return StringUtils.toStringArray(merged);
	}

	/**
	 * {@link ImportBeanDefinitionRegistrar} to store the base package from the importing
	 * configuration.
	 */
	static class Registrar implements ImportBeanDefinitionRegistrar, DeterminableImports {

		/**
		 * 获取项目主程序启动类所在的目录
		 * @param metadata
		 * @param registry
		 */
		@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
			//默认将会扫描@SpringBootApplication标识的主配置所在的包及其子包下所用的组件
			//new PackageImport(metadata).getPackageName()会返回@SpringBootApplication此注解所在的包路径，如"com.nullnull.learn.boot"
			register(registry, new PackageImport(metadata).getPackageName());
		}

		@Override
		public Set<Object> determineImports(AnnotationMetadata metadata) {
			return Collections.singleton(new PackageImport(metadata));
		}

	}

	/**
	 * Wrapper for a package import.
	 */
	private static final class PackageImport {

		private final String packageName;

		PackageImport(AnnotationMetadata metadata) {
			this.packageName = ClassUtils.getPackageName(metadata.getClassName());
		}

		String getPackageName() {
			return this.packageName;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			return this.packageName.equals(((PackageImport) obj).packageName);
		}

		@Override
		public int hashCode() {
			return this.packageName.hashCode();
		}

		@Override
		public String toString() {
			return "Package Import " + this.packageName;
		}

	}

	/**
	 * Holder for the base package (name may be null to indicate no scanning).
	 */
	static final class BasePackages {

		private final List<String> packages;

		private boolean loggedBasePackageInfo;

		BasePackages(String... names) {
			List<String> packages = new ArrayList<>();
			for (String name : names) {
				if (StringUtils.hasText(name)) {
					packages.add(name);
				}
			}
			this.packages = packages;
		}

		List<String> get() {
			if (!this.loggedBasePackageInfo) {
				if (this.packages.isEmpty()) {
					if (logger.isWarnEnabled()) {
						logger.warn("@EnableAutoConfiguration was declared on a class "
								+ "in the default package. Automatic @Repository and "
								+ "@Entity scanning is not enabled.");
					}
				}
				else {
					if (logger.isDebugEnabled()) {
						String packageNames = StringUtils.collectionToCommaDelimitedString(this.packages);
						logger.debug("@EnableAutoConfiguration was declared on a class in the package '" + packageNames
								+ "'. Automatic @Repository and @Entity scanning is enabled.");
					}
				}
				this.loggedBasePackageInfo = true;
			}
			return this.packages;
		}
	}
}

```

至此已经阅读完一个注解了@AutoConfigurationPackage完毕。

此时，回到@EnableAutoConfiguration注解，继续阅读@Import(AutoConfigurationImportSelector.class)



#### 2.3.2 @Import(AutoConfigurationImportSelector.class) 注解源码

>将所有符合条件的@Configuration配制都加载到当前SpringBoot创建并使用IOC容器中

```java
package org.springframework.boot.autoconfigure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * {@link DeferredImportSelector} to handle {@link EnableAutoConfiguration
 * auto-configuration}. This class can also be subclassed if a custom variant of
 * {@link EnableAutoConfiguration @EnableAutoConfiguration} is needed.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 * @author Madhura Bhave
 * @since 1.3.0
 * @see EnableAutoConfiguration
 */
public class AutoConfigurationImportSelector
		implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware,
		BeanFactoryAware, EnvironmentAware, Ordered {

	private static final AutoConfigurationEntry EMPTY_ENTRY = new AutoConfigurationEntry();

	private static final String[] NO_IMPORTS = {};

	private static final Log logger = LogFactory
			.getLog(AutoConfigurationImportSelector.class);

	private static final String PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE = "spring.autoconfigure.exclude";

	private ConfigurableListableBeanFactory beanFactory;

	private Environment environment;

	private ClassLoader beanClassLoader;

	private ResourceLoader resourceLoader;


	// 这个方法告诉springboot都需要导入那些组件
	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		//判断 enableautoconfiguration注解有没有开启，默认开启（是否进行自动装配）
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		//1. 加载配置文件META-INF/spring-autoconfigure-metadata.properties，从中获取所有支持自动配置类的条件
		//作用：SpringBoot使用一个Annotation的处理器来收集一些自动装配的条件，那么这些条件可以在META-INF/spring-autoconfigure-metadata.properties进行配置。
		// SpringBoot会将收集好的@Configuration进行一次过滤进而剔除不满足条件的配置类
		// 自动配置的类全名.条件=值
		AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader.loadMetadata(this.beanClassLoader);
		AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(autoConfigurationMetadata, annotationMetadata);
		return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
	}

......

	protected static class AutoConfigurationEntry {

        /**
         * 配置类的全类名的数组
         */
		private final List<String> configurations;
        /**
         * 排除的配置类的全类名的数组
         */
		private final Set<String> exclusions;

		private AutoConfigurationEntry() {
			this.configurations = Collections.emptyList();
			this.exclusions = Collections.emptySet();
		}

		/**
		 * Create an entry with the configurations that were contributed and their
		 * exclusions.
		 * @param configurations the configurations that should be imported
		 * @param exclusions the exclusions that were applied to the original list
		 */
		AutoConfigurationEntry(Collection<String> configurations,
				Collection<String> exclusions) {
			this.configurations = new ArrayList<>(configurations);
			this.exclusions = new HashSet<>(exclusions);
		}

		public List<String> getConfigurations() {
			return this.configurations;
		}

		public Set<String> getExclusions() {
			return this.exclusions;
		}

	}

}

```

通过selectImports方法已经确认了导入哪些组件。

首先是判断@enableautoconfiguration注解是否开启，如果未开启就返回了空数组，默认此为开启，然后将通过AutoConfigurationMetadataLoader的loadMetadata方法加载自动装配的对象，最后再通过条件过滤掉无需加载的类，最后返回需要加载的对象。接下为首先将查看AutoConfigurationMetadataLoader类

##### 2.3.2.1 AutoConfigurationMetadataLoader

```java
package org.springframework.boot.autoconfigure;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

/**
 * Internal utility used to load {@link AutoConfigurationMetadata}.
 *
 * @author Phillip Webb
 */
final class AutoConfigurationMetadataLoader {

	protected static final String PATH = "META-INF/" + "spring-autoconfigure-metadata.properties";//文件中为需要加载的配置类的类路径

	private AutoConfigurationMetadataLoader() {
	}

	public static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader) {
		//重载方法
		return loadMetadata(classLoader, PATH);
	}

	static AutoConfigurationMetadata loadMetadata(ClassLoader classLoader, String path) {
		try {
			//1.读取spring-boot-autoconfigure.jar包中spring-autoconfigure-metadata.properties的信息生成urls枚举对象
            // 获得 PATH 对应的 URL 们
			Enumeration<URL> urls = (classLoader != null) ? classLoader.getResources(path) : ClassLoader.getSystemResources(path);
            // 遍历 URL 数组，读取到 properties 中
            Properties properties = new Properties();

			//2.解析urls枚举对象中的信息封装成properties对象并加载
			while (urls.hasMoreElements()) {
				properties.putAll(PropertiesLoaderUtils.loadProperties(new UrlResource(urls.nextElement())));
			}
			// 将 properties 转换成 PropertiesAutoConfigurationMetadata 对象

			//根据封装好的properties对象生成AutoConfigurationMetadata对象返回
			return loadMetadata(properties);
		} catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load @ConditionalOnClass location [" + path + "]", ex);
		}
	}

	static AutoConfigurationMetadata loadMetadata(Properties properties) {
		return new PropertiesAutoConfigurationMetadata(properties);
	}

	/**
	 * {@link AutoConfigurationMetadata} implementation backed by a properties file.
	 */
	private static class PropertiesAutoConfigurationMetadata implements AutoConfigurationMetadata {

        /**
         * Properties 对象
         */
		private final Properties properties;

		PropertiesAutoConfigurationMetadata(Properties properties) {
			this.properties = properties;
		}

		@Override
		public boolean wasProcessed(String className) {
			return this.properties.containsKey(className);
		}

		@Override
		public Integer getInteger(String className, String key) {
			return getInteger(className, key, null);
		}

		@Override
		public Integer getInteger(String className, String key, Integer defaultValue) {
			String value = get(className, key);
			return (value != null) ? Integer.valueOf(value) : defaultValue;
		}

		@Override
		public Set<String> getSet(String className, String key) {
			return getSet(className, key, null);
		}

		@Override
		public Set<String> getSet(String className, String key, Set<String> defaultValue) {
			String value = get(className, key);
			return (value != null) ? StringUtils.commaDelimitedListToSet(value) : defaultValue;
		}

		@Override
		public String get(String className, String key) {
			return get(className, key, null);
		}

		@Override
		public String get(String className, String key, String defaultValue) {
			String value = this.properties.getProperty(className + "." + key);
			return (value != null) ? value : defaultValue;
		}

	}

}

```

在此AutoConfigurationMetadataLoader类中将传递类加载器，并传递一个参数"META-INF/spring-autoconfigure-metadata.properties",此方件为需要加载配制类，通过类加载器，进行加载，那拉下来查看下spring-autoconfigure-metadata.properties文件,此文件位于spring-boot-autoconfigure-2.2.2.RELEASE.jar文件中，路径位于spring-boot-autoconfigure-2.2.2.RELEASE.jar!\META-INF\spring-autoconfigure-metadata.properties，

##### 2.3.2.2 查看spring-autoconfigure-metadata.properties

```properties
#Fri Dec 06 06:02:56 GMT 2019
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration.ConditionalOnClass=com.datastax.driver.core.Cluster,reactor.core.publisher.Flux,org.springframework.data.cassandra.core.ReactiveCassandraTemplate
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration.ConditionalOnClass=org.apache.solr.client.solrj.SolrClient,org.springframework.data.solr.repository.SolrRepository
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration=
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
org.springframework.boot.autoconfigure.jms.artemis.ArtemisXAConnectionFactoryConfiguration=
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration=
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration.ConditionalOnClass=com.couchbase.client.java.Cluster,com.couchbase.client.java.CouchbaseBucket
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.ConditionalOnClass=com.rabbitmq.client.Channel,org.springframework.amqp.rabbit.core.RabbitTemplate
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration.AutoConfigureOrder=-2147483648
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration=
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.AutoConfigureOrder=-2147483638
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration.ConditionalOnClass=com.couchbase.client.java.Bucket,reactor.core.publisher.Flux,org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration=
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration.ConditionalOnClass=javax.sql.DataSource,org.springframework.jdbc.core.JdbcTemplate
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration.ConditionalOnBean=javax.jms.ConnectionFactory
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration=
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration=
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration=
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration=
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration.ConditionalOnClass=javax.sql.DataSource,org.springframework.batch.core.launch.JobLauncher
org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration=
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration=
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration.ConditionalOnClass=org.springframework.data.elasticsearch.core.ElasticsearchTemplate
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration.ConditionalOnSingleCandidate=com.hazelcast.core.HazelcastInstance
org.springframework.boot.autoconfigure.session.JdbcSessionConfiguration=
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration.ConditionalOnClass=org.jooq.DSLContext
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration.ConditionalOnClass=org.springframework.ldap.core.ContextSource
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseConfigurerAdapterConfiguration.ConditionalOnClass=org.springframework.data.couchbase.config.CouchbaseConfigurer
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration=
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration=
org.springframework.boot.autoconfigure.transaction.jta.AtomikosJtaConfiguration=
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration=
org.springframework.boot.autoconfigure.kafka.KafkaStreamsAnnotationDrivenConfiguration.ConditionalOnClass=org.apache.kafka.streams.StreamsBuilder
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration=
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration=
org.springframework.boot.autoconfigure.data.couchbase.SpringBootCouchbaseReactiveDataConfiguration.ConditionalOnBean=org.springframework.data.couchbase.config.CouchbaseConfigurer
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseConfigurerAdapterConfiguration.ConditionalOnBean=org.springframework.boot.autoconfigure.couchbase.CouchbaseConfiguration
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
org.springframework.boot.autoconfigure.freemarker.FreeMarkerReactiveWebConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration.ConditionalOnClass=reactor.core.publisher.Flux,org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity,org.springframework.security.oauth2.client.registration.ClientRegistration
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.http.JsonbHttpMessageConvertersConfiguration.ConditionalOnClass=javax.json.bind.Jsonb
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration=
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration=
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration.ConditionalOnClass=org.springframework.session.Session
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration.ConditionalOnBean=org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.ConditionalOnClass=org.springframework.data.redis.core.RedisOperations
org.springframework.boot.autoconfigure.transaction.jta.BitronixJtaConfiguration.ConditionalOnClass=bitronix.tm.jndi.BitronixContext,org.springframework.transaction.jta.JtaTransactionManager
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.ConditionalOnClass=org.flywaydb.core.Flyway
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration=
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.ConditionalOnClass=javax.sql.DataSource,org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
org.springframework.boot.autoconfigure.session.RedisReactiveSessionConfiguration.ConditionalOnBean=org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
org.springframework.boot.autoconfigure.freemarker.FreeMarkerServletWebConfiguration=
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration.ConditionalOnClass=com.hazelcast.core.HazelcastInstance
org.springframework.boot.autoconfigure.session.RedisSessionConfiguration=
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration.ConditionalOnBean=javax.sql.DataSource
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration.ConditionalOnClass=org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration=
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration.ConditionalOnClass=org.h2.server.web.WebServlet
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration=
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration=
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration.ConditionalOnBean=com.datastax.driver.core.Session
org.springframework.boot.autoconfigure.mustache.MustacheReactiveWebConfiguration=
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration.ConditionalOnClass=org.springframework.jmx.export.MBeanExporter
org.springframework.boot.autoconfigure.session.MongoSessionConfiguration.ConditionalOnClass=org.springframework.data.mongodb.core.MongoOperations,org.springframework.session.data.mongo.MongoIndexedSessionRepository
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
org.springframework.boot.autoconfigure.data.couchbase.SpringBootCouchbaseReactiveDataConfiguration=
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseConfigurerAdapterConfiguration=
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration.ConditionalOnClass=io.searchbox.client.JestClient
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2WebSecurityConfiguration=
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration=
org.springframework.boot.autoconfigure.session.SessionRepositoryFilterConfiguration.ConditionalOnBean=org.springframework.session.web.http.SessionRepositoryFilter
org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration=
org.springframework.boot.autoconfigure.http.GsonHttpMessageConvertersConfiguration=
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration=
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.ConditionalOnClass=javax.servlet.ServletRequest
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration.ConditionalOnSingleCandidate=org.springframework.mail.javamail.JavaMailSenderImpl
org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration=
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration.ConditionalOnClass=org.springframework.cloud.config.java.CloudScanConfiguration
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.ConditionalOnClass=com.fasterxml.jackson.databind.ObjectMapper
org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration=
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration=
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration.ConditionalOnClass=org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration,org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration=
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.ConditionalOnClass=org.springframework.web.client.RestTemplate
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration=
org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration.ConditionalOnBean=org.springframework.data.redis.connection.RedisConnectionFactory
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration=
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration=
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration.ConditionalOnClass=com.datastax.driver.core.Session,org.springframework.data.cassandra.repository.CassandraRepository
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration=
org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration.ConditionalOnClass=org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration.AutoConfigureOrder=-2147483648
org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration.ConditionalOnBean=org.springframework.cache.Cache
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration.ConditionalOnClass=org.springframework.http.codec.CodecConfigurer,org.springframework.web.reactive.function.client.WebClient
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration
org.springframework.boot.autoconfigure.session.JdbcSessionConfiguration.ConditionalOnBean=javax.sql.DataSource
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration.ConditionalOnSingleCandidate=javax.sql.DataSource
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration.ConditionalOnBean=com.mongodb.reactivestreams.client.MongoClient
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration.ConditionalOnClass=org.springframework.http.ReactiveHttpInputMessage
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration.ConditionalOnClass=org.springframework.ws.transport.http.MessageDispatcherServlet
org.springframework.boot.autoconfigure.jdbc.NamedParameterJdbcTemplateConfiguration.ConditionalOnSingleCandidate=org.springframework.jdbc.core.JdbcTemplate
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration.ConditionalOnWebApplication=
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration.ConditionalOnClass=javax.servlet.ServletRegistration,org.glassfish.jersey.server.spring.SpringComponentProvider
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration=
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration.AutoConfigureOrder=-2147483638
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.ConditionalOnClass=reactor.core.publisher.Flux,org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity,org.springframework.security.web.server.WebFilterChainProxy,org.springframework.web.reactive.config.WebFluxConfigurer
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.ConditionalOnClass=javax.servlet.Servlet,org.springframework.web.servlet.DispatcherServlet
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration.AutoConfigureOrder=-2147483648
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration=
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.ConditionalOnClass=org.springframework.security.authentication.ReactiveAuthenticationManager
org.springframework.boot.autoconfigure.mustache.MustacheServletWebConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration=
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.ConditionalOnClass=org.springframework.data.jpa.repository.JpaRepository
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration=
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration.ConditionalOnClass=groovy.text.markup.MarkupTemplateEngine
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration.ConditionalOnClass=org.elasticsearch.client.Client,org.springframework.data.elasticsearch.client.TransportClientFactoryBean
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoDbFactoryDependentConfiguration=
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration.ConditionalOnClass=org.neo4j.ogm.session.SessionFactory,org.springframework.data.neo4j.transaction.Neo4jTransactionManager,org.springframework.transaction.PlatformTransactionManager
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration.ConditionalOnClass=org.springframework.hateoas.EntityModel,org.springframework.plugin.core.Plugin,org.springframework.web.bind.annotation.RequestMapping
org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration=
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration.ConditionalOnClass=javax.naming.ldap.LdapContext,org.springframework.data.ldap.repository.LdapRepository
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration.ConditionalOnClass=org.springframework.http.converter.HttpMessageConverter
org.springframework.boot.autoconfigure.data.couchbase.SpringBootCouchbaseDataConfiguration=
org.springframework.boot.autoconfigure.session.HazelcastSessionConfiguration.ConditionalOnBean=com.hazelcast.core.HazelcastInstance
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.ConditionalOnClass=org.springframework.data.redis.repository.configuration.EnableRedisRepositories
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQXAConnectionFactoryConfiguration.ConditionalOnBean=org.springframework.boot.jms.XAConnectionFactoryWrapper
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration=
org.springframework.boot.autoconfigure.hazelcast.HazelcastClientConfiguration=
org.springframework.boot.autoconfigure.http.JsonbHttpMessageConvertersConfiguration=
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration=
org.springframework.boot.autoconfigure.jms.JmsAnnotationDrivenConfiguration.ConditionalOnClass=org.springframework.jms.annotation.EnableJms
org.springframework.boot.autoconfigure.security.saml2.Saml2LoginConfiguration=
org.springframework.boot.autoconfigure.freemarker.FreeMarkerServletWebConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.ConditionalOnClass=com.google.gson.Gson
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.AutoConfigureOrder=-2147483638
org.springframework.boot.autoconfigure.jms.artemis.ArtemisXAConnectionFactoryConfiguration.ConditionalOnBean=org.springframework.boot.jms.XAConnectionFactoryWrapper
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration=
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.ConditionalOnClass=org.springframework.jdbc.core.JdbcTemplate,org.springframework.transaction.PlatformTransactionManager
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration=
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration.ConditionalOnClass=javax.servlet.Servlet,javax.websocket.server.ServerContainer
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
org.springframework.boot.autoconfigure.mustache.MustacheReactiveWebConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration=
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration=
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration=
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration=
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration=
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration.ConditionalOnClass=org.neo4j.ogm.session.Neo4jSession,org.springframework.data.neo4j.repository.Neo4jRepository
org.springframework.boot.autoconfigure.jms.JmsAnnotationDrivenConfiguration=
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration.ConditionalOnClass=javax.jms.ConnectionFactory,org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration.ConditionalOnClass=com.sendgrid.SendGrid
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.AutoConfigureOrder=2147483647
org.springframework.boot.autoconfigure.session.HazelcastSessionConfiguration=
org.springframework.boot.autoconfigure.security.SecurityDataConfiguration.ConditionalOnClass=org.springframework.security.data.repository.query.SecurityEvaluationContextExtension
org.springframework.boot.autoconfigure.data.neo4j.Neo4jBookmarkManagementConfiguration.ConditionalOnClass=com.github.benmanes.caffeine.cache.Caffeine,org.springframework.cache.caffeine.CaffeineCacheManager
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration.ConditionalOnClass=io.rsocket.RSocketFactory,io.rsocket.transport.netty.server.TcpServerTransport,reactor.netty.http.server.HttpServer,org.springframework.messaging.rsocket.RSocketStrategies
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration=
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration.ConditionalOnClass=com.samskivert.mustache.Mustache
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration.ConditionalOnClass=org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration=
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration.ConditionalOnClass=org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration.ConditionalOnClass=net.sf.ehcache.Cache,org.springframework.cache.ehcache.EhCacheCacheManager
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
org.springframework.boot.autoconfigure.amqp.RabbitAnnotationDrivenConfiguration=
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration.ConditionalOnClass=com.couchbase.client.java.Bucket,org.springframework.data.couchbase.repository.CouchbaseRepository
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration=
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration=
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration=
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration=
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration
org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration=
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
org.springframework.boot.autoconfigure.data.mongo.MongoDbFactoryDependentConfiguration.ConditionalOnBean=org.springframework.data.mongodb.MongoDbFactory
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration
org.springframework.boot.autoconfigure.data.neo4j.Neo4jBookmarkManagementConfiguration.ConditionalOnBean=org.springframework.data.neo4j.bookmark.BeanFactoryBookmarkOperationAdvisor,org.springframework.data.neo4j.bookmark.BookmarkInterceptor
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration.ConditionalOnClass=org.springframework.transaction.PlatformTransactionManager
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration=
org.springframework.boot.autoconfigure.data.redis.JedisConnectionConfiguration=
org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration.ConditionalOnBean=org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration.ConditionalOnClass=org.springframework.data.cassandra.ReactiveSession,org.springframework.data.cassandra.repository.ReactiveCassandraRepository
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration.ConditionalOnBean=org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.ConditionalOnClass=org.springframework.cache.CacheManager
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration.ConditionalOnClass=org.springframework.integration.config.EnableIntegration
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration=
org.springframework.boot.autoconfigure.http.GsonHttpMessageConvertersConfiguration.ConditionalOnClass=com.google.gson.Gson
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration.ConditionalOnClass=com.couchbase.client.java.Bucket,reactor.core.publisher.Flux,org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository
org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration.ConditionalOnClass=com.github.benmanes.caffeine.cache.Caffeine,org.springframework.cache.caffeine.CaffeineCacheManager
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration.ConditionalOnClass=org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration=
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration.ConditionalOnBean=org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration.ConditionalOnClass=javax.sql.DataSource,org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration=
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration=
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration.ConditionalOnBean=org.springframework.data.redis.connection.RedisConnectionFactory
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration=
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration=
org.springframework.boot.autoconfigure.data.neo4j.Neo4jBookmarkManagementConfiguration=
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.ConditionalOnBean=org.springframework.security.config.annotation.ObjectPostProcessor
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration.ConditionalOnClass=javax.jms.ConnectionFactory,org.apache.activemq.ActiveMQConnectionFactory
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration.ConditionalOnClass=org.springframework.web.reactive.config.WebFluxConfigurer
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration.ConditionalOnClass=org.springframework.http.server.reactive.HttpHandler,org.springframework.web.reactive.DispatcherHandler
org.springframework.boot.autoconfigure.kafka.KafkaStreamsAnnotationDrivenConfiguration.ConditionalOnBean=
org.springframework.boot.autoconfigure.session.RedisSessionConfiguration.ConditionalOnBean=org.springframework.data.redis.connection.RedisConnectionFactory
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.ConditionalOnClass=javax.persistence.EntityManager,org.hibernate.engine.spi.SessionImplementor,org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration.ConditionalOnBean=com.datastax.driver.core.Cluster
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.ConditionalOnClass=com.mongodb.client.MongoClient,com.mongodb.MongoClient,org.springframework.data.mongodb.core.MongoTemplate
org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration.ConditionalOnClass=org.springframework.data.redis.connection.RedisConnectionFactory
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration.AutoConfigureOrder=-2147483648
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.ConditionalOnClass=liquibase.change.DatabaseChange,liquibase.integration.spring.SpringLiquibase
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration=
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.ConditionalOnClass=org.springframework.security.config.annotation.web.configuration.EnableWebSecurity,org.springframework.security.oauth2.client.registration.ClientRegistration
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration=
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration=
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.mail.MailSenderJndiConfiguration.ConditionalOnClass=javax.mail.Session
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration.ConditionalOnClass=com.mongodb.reactivestreams.client.MongoClient,reactor.core.publisher.Flux
org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration=
org.springframework.boot.autoconfigure.security.SecurityDataConfiguration=
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration=
org.springframework.boot.autoconfigure.session.MongoReactiveSessionConfiguration.ConditionalOnClass=org.springframework.data.mongodb.core.ReactiveMongoOperations,org.springframework.session.data.mongo.ReactiveMongoSessionRepository
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration=
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration.ConditionalOnWebApplication=REACTIVE
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration=
org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration.ConditionalOnClass=org.elasticsearch.client.RestClient
org.springframework.boot.autoconfigure.freemarker.FreeMarkerServletWebConfiguration.ConditionalOnClass=javax.servlet.Servlet,org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration.ConditionalOnClass=com.mongodb.reactivestreams.client.MongoClient,org.springframework.data.mongodb.core.ReactiveMongoTemplate
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.ConditionalOnClass=org.thymeleaf.spring5.SpringTemplateEngine,org.thymeleaf.templatemode.TemplateMode
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration=
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration=
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
org.springframework.boot.autoconfigure.freemarker.FreeMarkerReactiveWebConfiguration=
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration.ConditionalOnBean=org.glassfish.jersey.server.ResourceConfig
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration=
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration=
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration.ConditionalOnClass=org.apache.solr.client.solrj.impl.CloudSolrClient,org.apache.solr.client.solrj.impl.HttpSolrClient
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.ConditionalOnClass=org.springframework.web.reactive.config.WebFluxConfigurer
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.ConditionalOnBean=javax.sql.DataSource
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration=
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration.ConditionalOnClass=org.springframework.data.web.PageableHandlerMethodArgumentResolver,org.springframework.web.servlet.config.annotation.WebMvcConfigurer
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration=
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration=
org.springframework.boot.autoconfigure.freemarker.FreeMarkerReactiveWebConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration.ConditionalOnClass=io.rsocket.RSocketFactory,io.rsocket.transport.netty.server.TcpServerTransport,org.springframework.messaging.rsocket.RSocketRequester
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration=
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration.ConditionalOnSingleCandidate=com.couchbase.client.java.Bucket
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration.ConditionalOnClass=org.springframework.web.reactive.function.client.WebClient
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration.ConditionalOnClass=org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient,org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration.ConditionalOnClass=javax.activation.MimeType,javax.mail.internet.MimeMessage,org.springframework.mail.MailSender
org.springframework.boot.autoconfigure.mail.MailSenderJndiConfiguration=
org.springframework.boot.autoconfigure.jms.artemis.ArtemisXAConnectionFactoryConfiguration.ConditionalOnClass=javax.transaction.TransactionManager
org.springframework.boot.autoconfigure.transaction.jta.JndiJtaConfiguration=
org.springframework.boot.autoconfigure.session.RedisSessionConfiguration.ConditionalOnClass=org.springframework.data.redis.core.RedisTemplate,org.springframework.session.data.redis.RedisIndexedSessionRepository
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration.ConditionalOnClass=reactor.core.publisher.Flux,org.springframework.data.redis.connection.ReactiveRedisConnectionFactory,org.springframework.data.redis.core.ReactiveRedisTemplate
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.ConditionalOnClass=org.quartz.Scheduler,org.springframework.scheduling.quartz.SchedulerFactoryBean,org.springframework.transaction.PlatformTransactionManager
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration=
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration=
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration.ConditionalOnClass=org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration.ConditionalOnClass=org.springframework.oxm.Marshaller,org.springframework.oxm.Unmarshaller,org.springframework.ws.client.core.WebServiceTemplate
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
org.springframework.boot.autoconfigure.amqp.RabbitAnnotationDrivenConfiguration.ConditionalOnClass=org.springframework.amqp.rabbit.annotation.EnableRabbit
org.springframework.boot.autoconfigure.session.HazelcastSessionConfiguration.ConditionalOnClass=org.springframework.session.hazelcast.HazelcastIndexedSessionRepository
org.springframework.boot.autoconfigure.jdbc.NamedParameterJdbcTemplateConfiguration=
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.ConditionalOnClass=org.springframework.security.authentication.DefaultAuthenticationEventPublisher
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration=
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration.ConditionalOnSingleCandidate=javax.sql.DataSource
org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration=
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration.ConditionalOnClass=javax.json.bind.Jsonb
org.springframework.boot.autoconfigure.mustache.MustacheServletWebConfiguration=
org.springframework.boot.autoconfigure.batch.BatchConfigurerConfiguration=
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration
org.springframework.boot.autoconfigure.cache.HazelcastJCacheCustomizationConfiguration.ConditionalOnClass=com.hazelcast.core.HazelcastInstance
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration=
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration=
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration.ConditionalOnClass=com.unboundid.ldap.listener.InMemoryDirectoryServer
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration=
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration.ConditionalOnClass=com.couchbase.client.java.Bucket,org.springframework.data.couchbase.repository.CouchbaseRepository
org.springframework.boot.autoconfigure.session.MongoReactiveSessionConfiguration.ConditionalOnBean=org.springframework.data.mongodb.core.ReactiveMongoOperations
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration.ConditionalOnWebApplication=
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration=
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration.ConditionalOnBean=org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations,org.springframework.transaction.PlatformTransactionManager
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration.ConditionalOnClass=javax.validation.executable.ExecutableValidator
org.springframework.boot.autoconfigure.cache.HazelcastJCacheCustomizationConfiguration=
org.springframework.boot.autoconfigure.transaction.jta.AtomikosJtaConfiguration.ConditionalOnClass=com.atomikos.icatch.jta.UserTransactionManager,org.springframework.transaction.jta.JtaTransactionManager
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration=
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration=
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration
org.springframework.boot.autoconfigure.session.MongoReactiveSessionConfiguration=
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration=
org.springframework.boot.autoconfigure.batch.BatchConfigurerConfiguration.ConditionalOnBean=javax.sql.DataSource
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration.ConditionalOnClass=com.datastax.driver.core.Cluster,org.springframework.data.cassandra.core.CassandraAdminOperations
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration.ConditionalOnBean=org.springframework.boot.jdbc.XADataSourceWrapper
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration.ConditionalOnClass=org.springframework.jms.core.JmsTemplate
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration.ConditionalOnClass=javax.transaction.Transaction
org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration=
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration=
org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration.ConditionalOnClass=com.hazelcast.core.HazelcastInstance,com.hazelcast.spring.cache.HazelcastCacheManager
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration.ConditionalOnClass=org.influxdb.InfluxDB
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration.ConditionalOnClass=javax.jms.Message,org.springframework.jms.core.JmsTemplate
org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.security.saml2.Saml2LoginConfiguration.ConditionalOnBean=org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository
org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration=
org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration=
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQXAConnectionFactoryConfiguration.ConditionalOnClass=javax.transaction.TransactionManager
org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration=
org.springframework.boot.autoconfigure.jms.artemis.ArtemisEmbeddedServerConfiguration.ConditionalOnClass=org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ
org.springframework.boot.autoconfigure.transaction.jta.JndiJtaConfiguration.ConditionalOnClass=org.springframework.transaction.jta.JtaTransactionManager
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration.ConditionalOnBean=org.springframework.batch.core.launch.JobLauncher
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration.ConditionalOnClass=org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration
org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration=
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration=
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration.ConditionalOnClass=org.springframework.web.reactive.function.client.WebClient
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.ConditionalOnClass=com.mongodb.MongoClient
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration.ConditionalOnClass=com.hazelcast.core.HazelcastInstance,org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration=
org.springframework.boot.autoconfigure.kafka.KafkaStreamsAnnotationDrivenConfiguration=
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2WebSecurityConfiguration.ConditionalOnBean=org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.transaction.jta.BitronixJtaConfiguration=
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration.ConditionalOnClass=com.mongodb.reactivestreams.client.MongoClient,org.springframework.data.mongodb.repository.ReactiveMongoRepository
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration=
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration.ConditionalOnClass=com.mongodb.MongoClient,de.flapdoodle.embed.mongo.MongodStarter
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.ConditionalOnClass=com.mongodb.MongoClient,org.springframework.data.mongodb.repository.MongoRepository
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.ConditionalOnClass=org.springframework.kafka.core.KafkaTemplate
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration=
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration=
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.ConditionalOnClass=org.springframework.web.servlet.DispatcherServlet
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration.ConditionalOnClass=javax.sql.DataSource,javax.transaction.TransactionManager,org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration.ConditionalOnClass=org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration=
org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration.ConditionalOnClass=io.lettuce.core.RedisClient
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration=
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration.ConditionalOnClass=io.netty.buffer.PooledByteBufAllocator,io.rsocket.RSocketFactory,org.springframework.messaging.rsocket.RSocketStrategies
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration=
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.ConditionalOnBean=org.springframework.cache.interceptor.CacheAspectSupport
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration=
org.springframework.boot.autoconfigure.session.RedisReactiveSessionConfiguration.ConditionalOnClass=org.springframework.data.redis.connection.ReactiveRedisConnectionFactory,org.springframework.session.data.redis.ReactiveRedisSessionRepository
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration.ConditionalOnClass=io.rsocket.RSocketFactory,io.rsocket.transport.netty.server.TcpServerTransport,reactor.netty.http.server.HttpServer,org.springframework.messaging.rsocket.RSocketRequester
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration=
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.ConditionalOnClass=freemarker.template.Configuration,org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
org.springframework.boot.autoconfigure.session.RedisReactiveSessionConfiguration=
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration.ConditionalOnClass=org.elasticsearch.client.Client,org.springframework.data.elasticsearch.repository.ElasticsearchRepository
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration=
org.springframework.boot.autoconfigure.session.SessionRepositoryFilterConfiguration=
org.springframework.boot.autoconfigure.session.MongoSessionConfiguration=
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration=
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration.AutoConfigureOrder=-2147483648
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration.ConditionalOnClass=com.datastax.driver.core.Cluster
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.AutoConfigureOrder=-2147483648
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration=
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.ConditionalOnClass=org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQXAConnectionFactoryConfiguration=
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
org.springframework.boot.autoconfigure.session.JdbcSessionConfiguration.ConditionalOnClass=org.springframework.jdbc.core.JdbcTemplate,org.springframework.session.jdbc.JdbcIndexedSessionRepository
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.session.MongoSessionConfiguration.ConditionalOnBean=org.springframework.data.mongodb.core.MongoOperations
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.ConditionalOnClass=org.springframework.security.authentication.AuthenticationManager
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.ConditionalOnClass=org.springframework.security.config.http.SessionCreationPolicy,org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer
org.springframework.boot.autoconfigure.batch.BatchConfigurerConfiguration.ConditionalOnClass=org.springframework.transaction.PlatformTransactionManager
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration.ConditionalOnWebApplication=
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration=
org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration.ConditionalOnClass=javax.cache.Caching,org.springframework.cache.jcache.JCacheCacheManager
org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration.ConditionalOnClass=org.springframework.kafka.annotation.EnableKafka
org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration.ConditionalOnClass=org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration.ConditionalOnClass=com.couchbase.client.java.Bucket,com.couchbase.client.spring.cache.CouchbaseCacheManager
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration.ConditionalOnClass=org.springframework.web.filter.CharacterEncodingFilter
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration.AutoConfigureAfter=org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration.AutoConfigureOrder=-2147483628
org.springframework.boot.autoconfigure.jms.artemis.ArtemisEmbeddedServerConfiguration=
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration.ConditionalOnClass=javax.servlet.MultipartConfigElement,javax.servlet.Servlet,org.springframework.web.multipart.support.StandardServletMultipartResolver
org.springframework.boot.autoconfigure.freemarker.FreeMarkerServletWebConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration.ConditionalOnClass=javax.servlet.Servlet,javax.websocket.server.ServerContainer
org.springframework.boot.autoconfigure.hazelcast.HazelcastClientConfiguration.ConditionalOnClass=com.hazelcast.client.HazelcastClient
org.springframework.boot.autoconfigure.data.couchbase.SpringBootCouchbaseDataConfiguration.ConditionalOnBean=org.springframework.data.couchbase.config.CouchbaseConfigurer
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration=
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration.ConditionalOnClass=reactor.netty.http.client.HttpClient,org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients,org.springframework.web.reactive.function.client.WebClient
org.springframework.boot.autoconfigure.data.redis.JedisConnectionConfiguration.ConditionalOnClass=org.apache.commons.pool2.impl.GenericObjectPool,redis.clients.jedis.Jedis,org.springframework.data.redis.connection.jedis.JedisConnection
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration=
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration=
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration.ConditionalOnWebApplication=SERVLET
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.ConditionalOnClass=javax.servlet.Servlet,org.springframework.web.servlet.config.annotation.WebMvcConfigurer,org.springframework.web.servlet.DispatcherServlet

```

观察这里的配制，发现配制存在着一定的规则的。那规则是什么呢？

这个规则就是 “自动配制的类全名.条件=值”，随便打开一个文件查看下

以org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration.ConditionalOnClass=org.apache.solr.client.solrj.SolrClient,org.springframework.data.solr.repository.SolrRepository为例吧

此表示，只有在类路径下存在SolrClient和SolrRepository这两个类时，才会加载SolrRepositoriesAutoConfiguration

当翻阅到这里时，需要回到AutoConfigurationMetadataLoader的loadMetadata方法，loadMetadata方法会加载spring-boot-autoconfigure.jar包中spring-autoconfigure-metadata.properties文件，封装返回PropertiesAutoConfigurationMetadata。

接下来，将回去AutoConfigurationImportSelector，继续阅读selectImports方法，以上对象的返回就是autoConfigurationMetadata对象，通过此对象，将通过getAutoConfigurationEntry方法，获取到配制对象信息。接下来看开始跟踪至getAutoConfigurationEntry中的代码。

##### 2.3.2.3 关键方法getAutoConfigurationEntry

```java
......
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware,
		ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered {
......
	/**
     * 获得 AutoConfigurationEntry 对象
     *
	 * Return the {@link AutoConfigurationEntry} based on the {@link AnnotationMetadata}
	 * of the importing {@link Configuration @Configuration} class.
	 * @param autoConfigurationMetadata the auto-configuration metadata
	 * @param annotationMetadata the annotation metadata of the configuration class
	 * @return the auto-configurations that should be imported
	 */
	protected AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata, AnnotationMetadata annotationMetadata) {
	    // 1. 判断是否开启注解。如未开启，返回空串
		if (!isEnabled(annotationMetadata)) {
			return EMPTY_ENTRY;
		}
		// 2. 获得注解的属性
		AnnotationAttributes attributes = getAttributes(annotationMetadata);

		// 3. getCandidateConfigurations()用来获取默认支持的自动配置类名列表
		// spring Boot在启动的时候，使用内部工具类SpringFactoriesLoader，查找classpath上所有jar包中的META-INF/spring.factories，
		// 找出其中key为org.springframework.boot.autoconfigure.EnableAutoConfiguration的属性定义的工厂类名称，
		// 将这些值作为自动配置类导入到容器中，自动配置类就生效了
		List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);


		// 3.1 //去除重复的配置类，若我们自己写的starter 可能存在重复的
		configurations = removeDuplicates(configurations);
		// 4. 如果项目中某些自动配置类，我们不希望其自动配置，我们可以通过EnableAutoConfiguration的exclude或excludeName属性进行配置，
		// 或者也可以在配置文件里通过配置项“spring.autoconfigure.exclude”进行配置。
		//找到不希望自动配置的配置类（根据EnableAutoConfiguration注解的一个exclusions属性）
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
		// 4.1 校验排除类（exclusions指定的类必须是自动配置类，否则抛出异常）
		checkExcludedClasses(configurations, exclusions);
		// 4.2 从 configurations 中，移除所有不希望自动配置的配置类
		configurations.removeAll(exclusions);

		// 5. 对所有候选的自动配置类进行筛选，根据项目pom.xml文件中加入的依赖文件筛选出最终符合当前项目运行环境对应的自动配置类

		//@ConditionalOnClass ： 某个class位于类路径上，才会实例化这个Bean。
		//@ConditionalOnMissingClass ： classpath中不存在该类时起效
		//@ConditionalOnBean ： DI容器中存在该类型Bean时起效
		//@ConditionalOnMissingBean ： DI容器中不存在该类型Bean时起效
		//@ConditionalOnSingleCandidate ： DI容器中该类型Bean只有一个或@Primary的只有一个时起效
		//@ConditionalOnExpression ： SpEL表达式结果为true时
		//@ConditionalOnProperty ： 参数设置或者值一致时起效
		//@ConditionalOnResource ： 指定的文件存在时起效
		//@ConditionalOnJndi ： 指定的JNDI存在时起效
		//@ConditionalOnJava ： 指定的Java版本存在时起效
		//@ConditionalOnWebApplication ： Web应用环境下起效
		//@ConditionalOnNotWebApplication ： 非Web应用环境下起效

		//总结一下判断是否要加载某个类的两种方式：
		//根据spring-autoconfigure-metadata.properties进行判断。
		//要判断@Conditional是否满足
		// 如@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })表示需要在类路径中存在SqlSessionFactory.class、SqlSessionFactoryBean.class这两个类才能完成自动注册。
		configurations = filter(configurations, autoConfigurationMetadata);


		// 6. 将自动配置导入事件通知监听器
		//当AutoConfigurationImportSelector过滤完成后会自动加载类路径下Jar包中META-INF/spring.factories文件中 AutoConfigurationImportListener的实现类，
		// 并触发fireAutoConfigurationImportEvents事件。
		fireAutoConfigurationImportEvents(configurations, exclusions);
		// 7. 创建 AutoConfigurationEntry 对象
		return new AutoConfigurationEntry(configurations, exclusions);
	}
......  
}
```

这里前两个，都是基本的注解检查和获取注解属性，不用特别关心，看下明白就OK

接下来就是getCandidateConfigurations方法了

```java
	/**
	 * Return the auto-configuration class names that should be considered. By default
	 * this method will load candidates using {@link SpringFactoriesLoader} with
	 * {@link #getSpringFactoriesLoaderFactoryClass()}.
	 * @param metadata the source metadata
	 * @param attributes the {@link #getAttributes(AnnotationMetadata) annotation
	 * attributes}
	 * @return a list of candidate configurations
	 */
	protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		//让SpringFactoriesLoader去加载一些组件的名字,通过META-INF/spring.factories来配制
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				getBeanClassLoader());
		//断言，非空
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}

	protected Class<?> getSpringFactoriesLoaderFactoryClass() {
		return EnableAutoConfiguration.class;
	}
```

从这里我们可以看出，是让Spring去加载所有EnableAutoConfiguration注解，那通过什么来加载呢？继续查看源码

```java
package org.springframework.core.io.support;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * General purpose factory loading mechanism for internal use within the framework.
 *
 * <p>{@code SpringFactoriesLoader} {@linkplain #loadFactories loads} and instantiates
 * factories of a given type from {@value #FACTORIES_RESOURCE_LOCATION} files which
 * may be present in multiple JAR files in the classpath. The {@code spring.factories}
 * file must be in {@link Properties} format, where the key is the fully qualified
 * name of the interface or abstract class, and the value is a comma-separated list of
 * implementation class names. For example:
 *
 * <pre class="code">example.MyService=example.MyServiceImpl1,example.MyServiceImpl2</pre>
 *
 * where {@code example.MyService} is the name of the interface, and {@code MyServiceImpl1}
 * and {@code MyServiceImpl2} are two implementations.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.2
 */
public final class SpringFactoriesLoader {
	/**
	 * The location to look for factories.
	 * <p>Can be present in multiple JAR files.
	 */
	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
......
	/**
	 * Load the fully qualified class names of factory implementations of the
	 * given type from {@value #FACTORIES_RESOURCE_LOCATION}, using the given
	 * class loader.
	 * @param factoryType the interface or abstract class representing the factory
	 * @param classLoader the ClassLoader to use for loading resources; can be
	 * {@code null} to use the default
	 * @throws IllegalArgumentException if an error occurs while loading factory names
	 * @see #loadFactories
	 */
	public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
		//获取工厂类的名称
		String factoryTypeName = factoryType.getName();
		//加载着急的配制文件，高新并获取类
		return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
	}
	
	private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
		MultiValueMap<String, String> result = cache.get(classLoader);
		if (result != null) {
			return result;
		}

		try {
			//检查classloader，如果不为空，则使用类加载器，去加载META-INF/spring.factories文件
			Enumeration<URL> urls = (classLoader != null ?
					classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
      //将配制信息，读取到，并放置于此容器返回
			result = new LinkedMultiValueMap<>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				UrlResource resource = new UrlResource(url);
				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
				for (Map.Entry<?, ?> entry : properties.entrySet()) {
					String factoryTypeName = ((String) entry.getKey()).trim();
					for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
						result.add(factoryTypeName, factoryImplementationName.trim());
					}
				}
			}
			cache.put(classLoader, result);
			return result;
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load factories from location [" +
					FACTORIES_RESOURCE_LOCATION + "]", ex);
		}
	}
......
}
```

接下来看下META-INF/spring.factories这个配制文件。

```properties
# Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,\
org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener

# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.autoconfigure.BackgroundPreinitializer

# Auto Configuration Import Listeners
org.springframework.boot.autoconfigure.AutoConfigurationImportListener=\
org.springframework.boot.autoconfigure.condition.ConditionEvaluationReportAutoConfigurationImportListener

# Auto Configuration Import Filters
org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=\
org.springframework.boot.autoconfigure.condition.OnBeanCondition,\
org.springframework.boot.autoconfigure.condition.OnClassCondition,\
org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition

# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration,\
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration

# Failure analyzers
org.springframework.boot.diagnostics.FailureAnalyzer=\
org.springframework.boot.autoconfigure.diagnostics.analyzer.NoSuchBeanDefinitionFailureAnalyzer,\
org.springframework.boot.autoconfigure.flyway.FlywayMigrationScriptMissingFailureAnalyzer,\
org.springframework.boot.autoconfigure.jdbc.DataSourceBeanCreationFailureAnalyzer,\
org.springframework.boot.autoconfigure.jdbc.HikariDriverConfigurationFailureAnalyzer,\
org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryFailureAnalyzer

# Template availability providers
org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider=\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.mustache.MustacheTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafTemplateAvailabilityProvider,\
org.springframework.boot.autoconfigure.web.servlet.JspTemplateAvailabilityProvider

```

这里有很多的全类名，那按照传递的参数EnableAutoConfiguration，就可以得到对应的配制类信息，从21行开始一致到145行。

此配制文件加载后，便被封装到了MultiValueMap<String, String>容器中，再通过传递的参数EnableAutoConfiguration，便获得了一个String类型的集合。

那继续回到getAutoConfigurationEntry方法。

接下来就就来到了重复的过滤了

```java
	protected final <T> List<T> removeDuplicates(List<T> list) {
		return new ArrayList<>(new LinkedHashSet<>(list));
	}
```

可以看出，此通过一个Set集合对象完，完成了对重复数据的过滤。

再回到getAutoConfigurationEntry方法，便来到了

```java
		//4,如果项目中某些自动配制类，我们不希望自动配制，我们可以通过EnableAutoConfiguration的exclude或者excludeName属性进行配制
		//或者也可以在配制文件中通过配制项“spring.autoconfigure.exclude”进行配制。
		// 找到不自动配制的配置类（根据EnableAutoConfiguration的注解的一个exclusions属性）
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
```

此是进行了排除操作了,那到底哪还些是需要被排除的了呢？

```java
	private static final String PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE = "spring.autoconfigure.exclude";

  /**
	 * Return any exclusions that limit the candidate configurations.
	 * @param metadata the source metadata
	 * @param attributes the {@link #getAttributes(AnnotationMetadata) annotation
	 * attributes}
	 * @return exclusions or an empty set
	 */
	protected Set<String> getExclusions(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		Set<String> excluded = new LinkedHashSet<>();
		// 注解上的 exclude 属性
		excluded.addAll(asList(attributes, "exclude"));
		// 注解上的 excludeName 属性
		excluded.addAll(Arrays.asList(attributes.getStringArray("excludeName")));
		// 配置文件的 spring.autoconfigure.exclude 属性
		excluded.addAll(getExcludeAutoConfigurationsProperty());
		return excluded;
	}

	/**
	 * 进行配制文件中的spring.autoconfigure.exclude中的读取操作
	 * @return
	 */
	private List<String> getExcludeAutoConfigurationsProperty() {
		if (getEnvironment() instanceof ConfigurableEnvironment) {
			Binder binder = Binder.get(getEnvironment());
			return binder.bind(PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE, String[].class).map(Arrays::asList)
					.orElse(Collections.emptyList());
		}
		String[] excludes = getEnvironment().getProperty(PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE, String[].class);
		return (excludes != null) ? Arrays.asList(excludes) : Collections.emptyList();
	}

```

经过此处的源码，可以发现，通过EnableAutoConfiguration注解的exclude属性，可以排除，也可通过excludeName进行配制，最后还可以通过spring.autoconfigure.exclude进行配制。

接下再还是回去getAutoConfigurationEntry方法

```
		// 4.1 校验排除类(exclusions必须是自动配制类，否则抛出异常)
		checkExcludedClasses(configurations, exclusions);
```

进行校验排除类的信息

```java
	private void checkExcludedClasses(List<String> configurations, Set<String> exclusions) {
		//获得exclusions 不在invalidExcludes的集合，添加到invalidExcludes中
		List<String> invalidExcludes = new ArrayList<>(exclusions.size());
		for (String exclusion : exclusions) {
			// 在classPath中存在该类，configurations不存在该类，
			if (ClassUtils.isPresent(exclusion, getClass().getClassLoader()) && !configurations.contains(exclusion)) {
				invalidExcludes.add(exclusion);
			}
		}
		//如果invalidExcludes非空，抛出IllegalStateException异常
		//也就是如果当前在排除的类中，存在了非自动配制类，则抛出异常
		if (!invalidExcludes.isEmpty()) {
			handleInvalidExcludes(invalidExcludes);
		}
	}
```

如果发现加载的配制中不包括此配制类，并且在上下文路径中存在，进行抛出异常处理。

当校验完成后，将排除的类，进行移除处理。

接下来再回到getAutoConfigurationEntry方法

```java
		// 对所有候选的自动配置类过时行筛选，根据项目的pom.xml文件中加入的依赖文件筛选出最终符合当前项目运行环境对应的自动配置类
		//常用的注解条件
		//@ConditionalOnClass  ： 某个class位于类路径上，才会实例化这个Bean
		//@ConditionalOnMissingClass ：classpath中不存在该类时生效
		//@ConditionalOnBean : DI容器中存在该类时生效
		//@ConditionalOnMissingBean ： DI容器中不存在该类型Bean时起效
		//@ConditionalOnSingleCandidate : DI容器中该类型Bean中有一个或者@Primary的只有一个时起效
		//@ConditionalOnExpression : Spel表达式结果为true时
		//@ConditionalOnProperty : 参数设置或者值一致时起效
		//@ConditionalOnResource : 参数的文件存在时生效
		//@ConditionalOnJndi : 指定的JNDI起在时起效
		//@ConditionalOnJava : 指定的Java版本存在时起效
		//@ConditionalOnWebApplication : Web应用环境下起效
		//@ConditionalOnNotWebApplication : 非WEB应用环境下起效

		//总结下判断加载某个类的两种方式，
		//1,根据spring-autoconfigure-metadata.properties进行判断
		//2,要判断@Conditional是否满足
		//以如@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })为例
		//表示需要在类路径中存在SqlSessionFactory.class, SqlSessionFactoryBean.class这两个类才能完成自动注册
		configurations = filter(configurations, autoConfigurationMetadata);
```

此将过行过滤操作

```java
	/**
	 * 对所有候选配置类进行筛选，
	 * @param configurations
	 * @param autoConfigurationMetadata
	 * @return
	 */
	private List<String> filter(List<String> configurations, AutoConfigurationMetadata autoConfigurationMetadata) {

		//记录下开始时间，用作统计消耗时间计量
		long startTime = System.nanoTime();
		//配制类数组
		String[] candidates = StringUtils.toStringArray(configurations);
		//声明一个需要忽略的数组，通过下标互相索引
		boolean[] skip = new boolean[candidates.length];
		//是否需要忽略的一个数组声明
		boolean skipped = false;
		//遍历AutoConfigurationImportFilter的数组，逐个匹配。
		for (AutoConfigurationImportFilter filter : getAutoConfigurationImportFilters()) {
			//设置AutoConfigurationImportFilter的属性，
			invokeAwareMethods(filter);
			//执行匹配，并返回结果
			boolean[] match = filter.match(candidates, autoConfigurationMetadata);

			//遍历匹配的结果，判断哪些需要忽略
			for (int i = 0; i < match.length; i++) {
				if (!match[i]) {
					skip[i] = true;
					candidates[i] = null;
					skipped = true;
				}
			}
		}
		//如果没有可以忽略的，则直接返回configurations
		if (!skipped) {
			return configurations;
		}

		//如果存在需要忽略的，则构建新的数组，排除忽略的
		List<String> result = new ArrayList<>(candidates.length);
		for (int i = 0; i < candidates.length; i++) {
			if (!skip[i]) {
				result.add(candidates[i]);
			}
		}
		//打印消耗的时间，和排除的数量
		if (logger.isTraceEnabled()) {
			int numberFiltered = configurations.size() - result.size();
			logger.trace("Filtered " + numberFiltered + " auto configuration class in "
					+ TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");
		}
		return new ArrayList<>(result);
	}
```

由于此处涉及一些配制，先随便打开一个spring.factories文件中一个配制类，来看看

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
public class AopAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(Advice.class)
	static class AspectJAutoProxyingConfiguration {

		@Configuration(proxyBeanMethods = false)
		@EnableAspectJAutoProxy(proxyTargetClass = false)
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false",
				matchIfMissing = false)
		static class JdkDynamicAutoProxyConfiguration {

		}

		@Configuration(proxyBeanMethods = false)
		@EnableAspectJAutoProxy(proxyTargetClass = true)
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
				matchIfMissing = true)
		static class CglibAutoProxyConfiguration {

		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingClass("org.aspectj.weaver.Advice")
	@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
			matchIfMissing = true)
	static class ClassProxyingConfiguration {

		ClassProxyingConfiguration(BeanFactory beanFactory) {
			if (beanFactory instanceof BeanDefinitionRegistry) {
				BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
				AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
				AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
			}
		}

	}

}
```

在类中就可以看到，以@ConditionalOn开头的一个注解。那这些以@ConditionalOn开头的注解就是在进行一个条件的筛选。当执行完成filter方法后，将得到了一个过滤完成后的加载列表。

再回到getAutoConfigurationEntry方法，再看最后一方法的执行。

```java
		//6. 将自动配置导入事件通知监听器
		//当AutoConfigurationImportSelector过滤完成后会自动加载类路径下jar包中META-INF/spring.factories文件中AutoConfigurationImportListener的实现类
		//并触发fireAutoConfigurationImportEvents事件
		fireAutoConfigurationImportEvents(configurations, exclusions);
```

查看此方法的源码

```java
	private void fireAutoConfigurationImportEvents(List<String> configurations, Set<String> exclusions) {
		//加载指定类型AutoConfigurationImportListener对应的，在“META-INF/spring.factories”里类名的数组
		List<AutoConfigurationImportListener> listeners = getAutoConfigurationImportListeners();
		if (!listeners.isEmpty()) {
			// 创建 AutoConfigurationImportEvent 事件
			AutoConfigurationImportEvent event = new AutoConfigurationImportEvent(this, configurations, exclusions);
			//遍历AutoConfigurationImportListener的所有监听，逐个通知
			for (AutoConfigurationImportListener listener : listeners) {
				//设置AutoConfigurationImportListener的属性
				invokeAwareMethods(listener);
				//给AutoConfigurationImportListener监听器发送事件
				listener.onAutoConfigurationImportEvent(event);
			}
		}
	}

	protected List<AutoConfigurationImportListener> getAutoConfigurationImportListeners() {
		return SpringFactoriesLoader.loadFactories(AutoConfigurationImportListener.class, this.beanClassLoader);
	}
```

再次看到SpringFactoriesLoader的loadFactories方法时，此时便明白了，又是从spring.factories文件中去获取。那获取的配制是AutoConfigurationImportListener,经过检查spring.factories配制文件，我们就可以找到

```properties
# Auto Configuration Import Listeners
org.springframework.boot.autoconfigure.AutoConfigurationImportListener=\
org.springframework.boot.autoconfigure.condition.ConditionEvaluationReportAutoConfigurationImportListener
```

那此处的代码也就明了，就是拿到Listener，遍历，然后发送事件给各个监听的处理器。





### 2.6 SpringBoot底层实现自动配置的步骤

>1. SpringBoot启动应用
>2. @SpringBootApplication起作用
>3. @EnableAutoConfiguration注解
>4. @AutoConfigurationPackage这个组件注解是@Import(AutoConfigurationPackage.Register.class),通过将Register类导入到容器中，而Register类作用是扫描主配置类同级目录以及子包，并将相应的组件导入到springboot创建管理的容器中
>5. @Import(AutoConfigurationImportSelector.class)：它通过将AutoConfigurationImportSelector类作用是通过selectImports方法执行的过程中，会使用内部工具类SpringFactoriesLoader,查找classpath上所有jar包中的META-INF/spring.factories进行加载，实现将配置类信息交给SpringFactory加载器进行一系列的容器创建过程。
>
>







# 3. 自定义Starter

Springboot是由众多的Starter组成（一系列的自动化配制的starter插件），SpringBoot之所以流行，也是因为Starter。

Starter是SpringBoot非常重要的一部分，可以理解为一个可插拔的插件，正是这些Starter使得某个功能的开发都者不需要关心各种依赖库的处理，不需要具体的配制信息，由SpringBoot自动通过classpath路径下的某个类发现需要的Bean，并织入相应的Bean。



**为什么要自定义Starter？**

开发过程中，经常会有一些独立于业务之外的配制模块，如果我们将这些可独立于业务代码之后的功能配制模块封装成一个Starter，复用的时候只需要知道其在pom中引用依赖即可，由SpringBoot为我们完成自动装配。



**自定义Starter的命名规则**

SpringBoot提供的Starter以spring-boot-starter-xxx的方式命名，官方建议自定义的Starter使用xxxx-spring-boot-starter命名规则。以区分SpringBoot的生态提供的Starter



**具体的实现**

此可以分为两步。1.定义Starter。2. 使用Starter

## 3.1 首先是定义Starter

添加所需要的依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
            <version>2.2.2.RELEASE</version>
        </dependency>
```

完整配制文件。例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>nullnull-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>


    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
            <version>2.2.2.RELEASE</version>
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

        </plugins>
    </build>

</project>
```



定义配制文件

SimpleBean.java

```java
package com.nullnull.learn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 样例的Bean
 *
 * @EnableConfigurationProperties的作用就是开启ConfigurationProperties注解。
 *
 * @author liujun
 * @since 2023/3/19
 */
@EnableConfigurationProperties(SimpleBean.class)
@ConfigurationProperties(prefix = "simplebean")
public class SimpleBean {

  private int Id;

  private String name;

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SimpleBean{");
    sb.append("Id=").append(Id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

```

定义依赖的条件配制文件

```java
package com.lagou.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass
public class MyAutoConfiguration {
    static {
        System.out.println("MyAutoConfiguration init....");
    }
    @Bean
    public SimpleBean simpleBean() {

        return new SimpleBean();
    }
}

```



添加配制文件

META-INF\spring.factories

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.lagou.config.MyAutoConfiguration
```



将此发布到maven仓库

## 3.2 使用Starter

1.添加依赖

```xml

         <dependency>
            <groupId>com.nullnull.learn</groupId>
            <artifactId>nullnull-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

```

2. 配制相关的属性

```
simplebean.id=111
simplebean.name=nullnull-starter-3
```



编写单元测试

```java
package com.nullnull.learn.boot.controller;

import com.nullnull.learn.config.SimpleBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试自定义Starter
 *
 * @author liujun
 * @since 2023/3/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMyselfStarter {

    /**
     * 加载bean
     */
    @Autowired
    private SimpleBean simpleBean;

    @Test
    public void testDataGet() {
        System.out.println(simpleBean);
    }
}

```

运行单元测试,查看控制台的输出

```
2023-03-19 14:46:55.075  INFO 11924 --- [           main] c.n.l.boot.controller.TestMyselfStarter  : No active profile set, falling back to default profiles: default
MyAutoConfiguration init..... 
2023-03-19 14:47:00.799  INFO 11924 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2023-03-19 14:47:02.085  INFO 11924 --- [           main] c.n.l.boot.controller.TestMyselfStarter  : Started TestMyselfStarter in 7.488 seconds (JVM running for 8.536)
SimpleBean{Id=111, name='nullnull-starter-3'}

```

通过观察，发现此自定义Starter已经成功加载。





# 4. SpringBoot的SpringApplication实例化过程

我们以之前编写的SpringBootDemoApplication为代码，进行剖析

```java
package com.nullnull.learn.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }
}

```

由此提出第一个问题：SpringApplication.run 在启动过程中做了哪些事情？是如何启动SpringBoot项目的呢？

首先打开SpringApplication.java

```java

public class SpringApplication {
......

	/**
	 * Static helper that can be used to run a {@link SpringApplication} from the
	 * specified source using default settings.
	 * <p>
	 * <p>
	 * 调用静态方法，传递对应的参数是SpringBootDemoApplication.class以及main方法的参数
	 *
	 * @param primarySource the primary source to load
	 * @param args          the application arguments (usually passed from a Java main method)
	 * @return the running {@link ApplicationContext}
	 */
	public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
		return run(new Class<?>[]{primarySource}, args);
	}

	/**
	 * Static helper that can be used to run a {@link SpringApplication} from the
	 * specified sources using default settings and user supplied arguments.
	 * <p>
	 * 重载的run方法，
	 *
	 * @param primarySources the primary sources to load
	 * @param args           the application arguments (usually passed from a Java main method)
	 * @return the running {@link ApplicationContext}
	 */
	public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
		//SpringApplication的启动由两部分组件
		//1. 实例化SpringApplication对象
		//2. run(args). 调用run方法
		return new SpringApplication(primarySources).run(args);
	}
	......
}

```

在这个SpringApplication的java文件中，首先调用的是静态的run方法，传递的参数是主启动类SpringBootDemoApplication.class以及main方法的参数，紧接着，就又调用了重载方法run，将参数传递，在重载的run方法中，new SpringApplication(primarySources).run(args);此是非常重要的一个启动方法，将SpringApplication的启动分为两步，第一步，实例化SpringApplication，第二步调用run方法。那首先，我们来开始实例化SpringApplication

## 4.1 实例化SpringApplication对象



```java

public class SpringApplication {
	/**
	 * The class name of application context that will be used by default for non-web
	 * environments.
	 */
	public static final String DEFAULT_CONTEXT_CLASS = "org.springframework.context."
			+ "annotation.AnnotationConfigApplicationContext";

	/**
	 * The class name of application context that will be used by default for web
	 * environments.
	 */
	public static final String DEFAULT_SERVLET_WEB_CONTEXT_CLASS = "org.springframework.boot."
			+ "web.servlet.context.AnnotationConfigServletWebServerApplicationContext";

	/**
	 * The class name of application context that will be used by default for reactive web
	 * environments.
	 */
	public static final String DEFAULT_REACTIVE_WEB_CONTEXT_CLASS = "org.springframework."
			+ "boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext";

	/**
	 * Default banner location.
	 */
	public static final String BANNER_LOCATION_PROPERTY_VALUE = SpringApplicationBannerPrinter.DEFAULT_BANNER_LOCATION;

	/**
	 * Banner location property key.
	 */
	public static final String BANNER_LOCATION_PROPERTY = SpringApplicationBannerPrinter.BANNER_LOCATION_PROPERTY;

	private static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

	private static final Log logger = LogFactory.getLog(SpringApplication.class);

	/**
	 * 主要的JAVA Config类的数组
	 */
	private Set<Class<?>> primarySources;


	private Set<String> sources = new LinkedHashSet<>();

	/**
	 * 启动的Application的类名
	 */
	private Class<?> mainApplicationClass;

	/**
	 * Banner的模式
	 */
	private Banner.Mode bannerMode = Banner.Mode.CONSOLE;

	private boolean logStartupInfo = true;

	/**
	 * 是否添加JVM避动参数
	 */
	private boolean addCommandLineProperties = true;

	/**
	 * 是否添加共享的ConversionService
	 */
	private boolean addConversionService = true;

	/**
	 * Banner对象，用于进行在控制台打印类似图像
	 */
	private Banner banner;

	/**
	 * 资源加载器
	 */
	private ResourceLoader resourceLoader;

	private BeanNameGenerator beanNameGenerator;

	private ConfigurableEnvironment environment;

	private Class<? extends ConfigurableApplicationContext> applicationContextClass;

	/**
	 * WEB的应用类型
	 */
	private WebApplicationType webApplicationType;

	/**
	 * 是否 AWT headless
	 */
	private boolean headless = true;

	/**
	 * 是否注册ShutdownHook的沟子，用于关闭时执行的动作
	 */
	private boolean registerShutdownHook = true;

	/**
	 * ApplicationContextInitializer的初始化数组
	 */
	private List<ApplicationContextInitializer<?>> initializers;

	/**
	 * ApplicationListener数组，用于监听
	 */
	private List<ApplicationListener<?>> listeners;

	/**
	 * 默认属性集合
	 */
	private Map<String, Object> defaultProperties;

	/**
	 * 附加的Profiles的属性
	 */
	private Set<String> additionalProfiles = new HashSet<>();

	private boolean allowBeanDefinitionOverriding;

	/**
	 * 是否自定义 Environment
	 */
	private boolean isCustomEnvironment = false;

	private boolean lazyInitialization = false;
	
	/**
	 * Create a new {@link SpringApplication} instance. The application context will load
	 * beans from the specified primary sources (see {@link SpringApplication class-level}
	 * documentation for details. The instance can be customized before calling
	 * {@link #run(String...)}.
	 *
	 * @param primarySources the primary bean sources
	 * @see #run(Class, String[])
	 * @see #SpringApplication(ResourceLoader, Class...)
	 * @see #setSources(Set)
	 */
	public SpringApplication(Class<?>... primarySources) {
		//调用本类中的另外的一个构建方法，一个类加载器，及主启动类的Class文件。
		this(null, primarySources);
	}
	
	/**
	 * Create a new {@link SpringApplication} instance. The application context will load
	 * beans from the specified primary sources (see {@link SpringApplication class-level}
	 * documentation for details. The instance can be customized before calling
	 * {@link #run(String...)}.
	 *
	 * @param resourceLoader the resource loader to use
	 * @param primarySources the primary bean sources
	 * @see #run(Class, String[])
	 * @see #setSources(Set)
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		this.resourceLoader = resourceLoader;
		Assert.notNull(primarySources, "PrimarySources must not be null");

		//项目启动类，SpringBootDemoApplication.class设置为属性保存起来。
		this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
		//设置应用类型是Servlet应用，（Spring5之前是传统的MVC应用）还是REACTIVE应用，（Spring5之后开始出现的WEBFlux交互式应用）
		this.webApplicationType = WebApplicationType.deduceFromClasspath();

		//设置初始化器（initializers）,最后会实例化这些初始化器
		//所谓的初始化器就是org.springframework.context.ApplicationContextInitializer的实现类，在Spring上下文被刷新之前进行初始化操作。
		//org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,
		//org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
		setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));

		//设置监听器（listeners)
		//org.springframework.boot.autoconfigure.BackgroundPreinitializer
		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

		//初始化mainApplicationClass属性，用于推断并设置项目Main方法启动的主程启动类。
		this.mainApplicationClass = deduceMainApplicationClass();
	}	
......
}

```

首先调用的构造方法SpringApplication(Class<?>... primarySources)，紧接着调用了另外的一构建函数传递了，此Class对象信息。在此构建函数中，将对属性进行一系列的设置。首先设置的是资源加载器，紧接着就设置了主启动类到primarySources中，接下来就是判断应用类型了我们去看一眼源码

```java
public enum WebApplicationType {

	/**
	 * The application should not run as a web application and should not start an
	 * embedded web server.
	 *
	 * 非内嵌的WEB应用
	 */
	NONE,

	/**
	 * The application should run as a servlet-based web application and should start an
	 * embedded servlet web server.
	 *
	 * 内置的Servlet应用，例如SpringMVC
	 */
	SERVLET,

	/**
	 * The application should run as a reactive web application and should start an
	 * embedded reactive web server.
	 *
	 * 内置的Reactive WEB应用，例如Spring Webflux
	 */
	REACTIVE;

	private static final String[] SERVLET_INDICATOR_CLASSES = { "javax.servlet.Servlet",
			"org.springframework.web.context.ConfigurableWebApplicationContext" };

	private static final String WEBMVC_INDICATOR_CLASS = "org.springframework.web.servlet.DispatcherServlet";

	private static final String WEBFLUX_INDICATOR_CLASS = "org.springframework.web.reactive.DispatcherHandler";

	private static final String JERSEY_INDICATOR_CLASS = "org.glassfish.jersey.servlet.ServletContainer";

	static WebApplicationType deduceFromClasspath() {
		//REACTIVE应用，通过类加载器判断Reactive相关的class是否存在，
		if (ClassUtils.isPresent(WEBFLUX_INDICATOR_CLASS, null) && !ClassUtils.isPresent(WEBMVC_INDICATOR_CLASS, null)
				&& !ClassUtils.isPresent(JERSEY_INDICATOR_CLASS, null)) {
			return WebApplicationType.REACTIVE;
		}
		//如果当前不存到Servlet的类，则为非内置的WEB应用
		for (String className : SERVLET_INDICATOR_CLASSES) {
			if (!ClassUtils.isPresent(className, null)) {
				return WebApplicationType.NONE;
			}
		}
		//此时就可以判断当前为Servlet应用，因为引入了Spring MVC时，是内嵌的WEB应用，会引入Servlet，DispatcherServlet就是一个Servlet
		return WebApplicationType.SERVLET;
	}
}
```

通过此处的一个源码，就能明白此处是如何判断是Servlet应用，以及其他类型的应用的。



**ApplicationContextInitializer加载**

那接下来就是setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));这行代码，那这行代码是在干什么呢？

我们首先来查看getSpringFactoriesInstances(ApplicationContextInitializer.class))这是在干什么？

```java
public class SpringApplication {

	/**
	 * 获取指定类对应的对象信息
	 *
	 * @param type
	 * @param <T>
	 * @return
	 */
	private <T> Collection<T> getSpringFactoriesInstances(Class<T> type) {
		return getSpringFactoriesInstances(type, new Class<?>[]{});
	}

	private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
		ClassLoader classLoader = getClassLoader();
		// Use names and ensure unique to protect against duplicates
		// 加载指定类对应的类信息，通过META-INF/spring.factories文件查找对应的
		Set<String> names = new LinkedHashSet<>(SpringFactoriesLoader.loadFactoryNames(type, classLoader));
		//根据名称进行实例化
		List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
		//实例进行排序
		AnnotationAwareOrderComparator.sort(instances);
		return instances;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> createSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes,
													   ClassLoader classLoader, Object[] args, Set<String> names) {
		//声明声明实例的数组的大小
		List<T> instances = new ArrayList<>(names.size());
		for (String name : names) {
			try {
				//获取名称对应的类
				Class<?> instanceClass = ClassUtils.forName(name, classLoader);
				//确认被加载的类，是指定类型的子类
				Assert.isAssignable(type, instanceClass);
				//获得构造方法
				Constructor<?> constructor = instanceClass.getDeclaredConstructor(parameterTypes);
				//创建对象
				T instance = (T) BeanUtils.instantiateClass(constructor, args);
				//加入实例的集合中
				instances.add(instance);
			} catch (Throwable ex) {
				throw new IllegalArgumentException("Cannot instantiate " + type + " : " + name, ex);
			}
		}
		return instances;
	}
}
```

此首先调用了getSpringFactoriesInstances这个方法，此方法仅封装了下参数，又调了了getSpringFactoriesInstances方法。在getSpringFactoriesInstances方法中，首先获得了类加载器，紧接着通过类加载器将调用SpringFactoriesLoader.loadFactoryNames(type, classLoader)，此方法在之前的源码分析时，已经见过，它就是来加载META-INF/spring.factories文件中的内容。此处加载的是ApplicationContextInitializer.class，那在配制文件中配制内容是什么呢？

```properties
# Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer,\
org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
```

当加载到createSpringFactoriesInstances方法后，紧接着就调用了createSpringFactoriesInstances方法，创建实例对象。最后进行实例的排序。此这里分析完成后，我们就回到SpringApplication构建方法。



**ApplicationListener的加载**

此调用方法一致。我们重点看下配制文件吧。

```properties
# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.autoconfigure.BackgroundPreinitializer
```





**deduceMainApplicationClass方法调用**

这时候来到了deduceMainApplicationClass方法调用。此就较为直接了，找到main函数所在的类，进行加载。

```java
	private Class<?> deduceMainApplicationClass() {
		try {
			//获取当前的栈枚举信息
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
			//判断哪个类执行了main方法
			for (StackTraceElement stackTraceElement : stackTrace) {
				if ("main".equals(stackTraceElement.getMethodName())) {
					return Class.forName(stackTraceElement.getClassName());
				}
			}
		} catch (ClassNotFoundException ex) {
			// Swallow and continue
		}
		return null;
	}
```

至此，实例的加载方法，基本已经分析结束。接下来就是分析run方法



## 4.2 run方法

```java
public class SpringApplication {
	/**
	 * Run the Spring application, creating and refreshing a new
	 * {@link ApplicationContext}.
	 *
	 * @param args the application arguments (usually passed from a Java main method)
	 * @return a running {@link ApplicationContext}
	 */
	public ConfigurableApplicationContext run(String... args) {
		//创建StopWatch对象，并启动，主要用于简单统计run启动过程的时长。
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		//初始化应用上下文和异常报告集合
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		//配制headless属性
		configureHeadlessProperty();

		//1. 通过spring.factories文件获得SpringApplicationRunListener并启动监听器。
		//此文件位于spring-boot-2.2.2.RELEASE.jar/META-INF/spring.factories中
		//# Run Listeners
		//org.springframework.boot.SpringApplicationRunListener=org.springframework.boot.context.event.EventPublishingRunListener
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {

			//初始化ApplicationArguments对象。初始化默认应用参数类
			//args是启动Spring的命令行参数。该参数可以Spring应用中被访问。如--server.port=9000
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

			//2. 项目运行环境的预配制
			//项目的运行环境Environment预配制,
			//创建并配制当前SpringBoot应用将要使用的Environment
			//并遍历调用所有的SpringApplicationRunListener的environmentPrepared方法
			ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
			configureIgnoreBeanInfo(environment);

			//banner打印操作,就是在启动Spring Boot的时候打印在console的ASCII艺术字体
			Banner printedBanner = printBanner(environment);


			//3. 创建Spring容器。
			context = createApplicationContext();
			//获取异常报告器SpringBootExceptionReporter数组，
			//这一步与之前的加载是一样的，通过META-INF/spring.factories文件中的配制进行加载
			//org.springframework.boot.diagnostics.FailureAnalyzers
			exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
					new Class[]{ConfigurableApplicationContext.class}, context);

			//4. 创建容器前置处理操作
			//这一步的主要作用是在容器刷新之前的准备动作。包含一个非常关键的操作；将启动类注入容器，为后续开启自动化配置奠定基础。
			prepareContext(context, environment, listeners, applicationArguments, printedBanner);

			//5. 刷新容器
			refreshContext(context);

			//6. Spring容器的后置处理
			afterRefresh(context, applicationArguments);

			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}

			//7. 发出事件监听通知
			listeners.started(context);

			//8. 执行Runner
			callRunners(context, applicationArguments);
		} catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			//9. 发布应用上下文就绪事件。
			listeners.running(context);
		} catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
}
```

通过这里的一个阅读，我们可以将启动大侄分为9步。

**1. 获取所有监听器**

```java
	private SpringApplicationRunListeners getRunListeners(String[] args) {
		Class<?>[] types = new Class<?>[]{SpringApplication.class, String[].class};
		return new SpringApplicationRunListeners(logger,
				getSpringFactoriesInstances(SpringApplicationRunListener.class, types, this, args));
	}
```

此还是在META-INF/spring.factories中的配制。获取到的配制信息是

```properties
# Run Listeners
org.springframework.boot.SpringApplicationRunListener=org.springframework.boot.context.event.EventPublishingRunListener
```

当监听器获取到之后，就开始调用SpringApplicationRunListeners的

```java
class SpringApplicationRunListeners {

	private final Log log;

	private final List<SpringApplicationRunListener> listeners;

	SpringApplicationRunListeners(Log log, Collection<? extends SpringApplicationRunListener> listeners) {
		this.log = log;
		this.listeners = new ArrayList<>(listeners);
	}

	void starting() {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.starting();
		}
	}
......
}
```



**2. 项目运行环境配制**

即调用了prepareEnvironment方法

```java
	/**
	 * 加载外部配制资源到environment，包括命令行参数、servletConfigInitParams、servletContextInitParams、systemProperties、
	 * sytemEnvironment、random、application.yml(.yaml/.xml/.properties)等；初始化日志系统。
	 *
	 * @param listeners
	 * @param applicationArguments
	 * @return
	 */
	private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners,
													   ApplicationArguments applicationArguments) {
		// Create and configure the environment
		//获取或者创建环境
		ConfigurableEnvironment environment = getOrCreateEnvironment();
		//配制环境：配制PropertySource和active Profiles
		configureEnvironment(environment, applicationArguments.getSourceArgs());
		ConfigurationPropertySources.attach(environment);
		//lister环境准备（就是广播ApplicationEnvironmentPreparedEvent事件)。
		listeners.environmentPrepared(environment);
		//将环境绑定到SpringApplication中
		bindToSpringApplication(environment);

		//如果当前非WEB环境，则将环境转换为StandardEnvironment
		if (!this.isCustomEnvironment) {
			environment = new EnvironmentConverter(getClassLoader()).convertEnvironmentIfNecessary(environment,
					deduceEnvironmentClass());
		}
		//配制PropertySources对它自己的递归依赖
		//如果有attach到environment上的MutablePropertySources，则添加到environment的PropertySource中.
		ConfigurationPropertySources.attach(environment);
		return environment;
	}
```

首先是调用了getOrCreateEnvironment，获取或者创建环境对象

```java
	private ConfigurableEnvironment getOrCreateEnvironment() {
		//如果环境对象已经存在，则返回
		if (this.environment != null) {
			return this.environment;
		}
		//不存在，则根据webApplicationType的类型，进行创建
		switch (this.webApplicationType) {
			case SERVLET:
				return new StandardServletEnvironment();
			case REACTIVE:
				return new StandardReactiveWebEnvironment();
			default:
				return new StandardEnvironment();
		}
	}
```

紧接着，就配制环境信息以及进行activeProfile的激活处理

```java
	/**
	 * Template method delegating to
	 * {@link #configurePropertySources(ConfigurableEnvironment, String[])} and
	 * {@link #configureProfiles(ConfigurableEnvironment, String[])} in that order.
	 * Override this method for complete control over Environment customization, or one of
	 * the above for fine-grained control over property sources or profiles, respectively.
	 *
	 * @param environment this application's environment
	 * @param args        arguments passed to the {@code run} method
	 * @see #configureProfiles(ConfigurableEnvironment, String[])
	 * @see #configurePropertySources(ConfigurableEnvironment, String[])
	 */
	protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
		//设置environment的conversionService属性
		if (this.addConversionService) {
			ConversionService conversionService = ApplicationConversionService.getSharedInstance();
			environment.setConversionService((ConfigurableConversionService) conversionService);
		}
		//配制environment的PropertySource属性源
		configurePropertySources(environment, args);
		//配制environment的activeProfiles属性
		configureProfiles(environment, args);
	}
	
		/**
	 * Add, remove or re-order any {@link PropertySource}s in this application's
	 * environment.
	 *
	 * @param environment this application's environment
	 * @param args        arguments passed to the {@code run} method
	 * @see #configureEnvironment(ConfigurableEnvironment, String[])
	 */
	protected void configurePropertySources(ConfigurableEnvironment environment, String[] args) {
		MutablePropertySources sources = environment.getPropertySources();
		//配制默认的defaultProperties
		if (this.defaultProperties != null && !this.defaultProperties.isEmpty()) {
			sources.addLast(new MapPropertySource("defaultProperties", this.defaultProperties));
		}
		//来自启动参数的配制。
		if (this.addCommandLineProperties && args.length > 0) {
			String name = CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME;
			//如果已经存在，则进行替换。
			if (sources.contains(name)) {
				PropertySource<?> source = sources.get(name);
				CompositePropertySource composite = new CompositePropertySource(name);
				composite.addPropertySource(
						new SimpleCommandLinePropertySource("springApplicationCommandLineArgs", args));
				composite.addPropertySource(source);
				sources.replace(name, composite);
			}
			//不存在，则直接添加
			else {
				sources.addFirst(new SimpleCommandLinePropertySource(args));
			}
		}
	}

	/**
	 * Configure which profiles are active (or active by default) for this application
	 * environment. Additional profiles may be activated during configuration file
	 * processing via the {@code spring.profiles.active} property.
	 *
	 * @param environment this application's environment
	 * @param args        arguments passed to the {@code run} method
	 * @see #configureEnvironment(ConfigurableEnvironment, String[])
	 * @see org.springframework.boot.context.config.ConfigFileApplicationListener
	 */
	protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
		//读取profile属性信息
		Set<String> profiles = new LinkedHashSet<>(this.additionalProfiles);
		//添加激活的环境信息
		profiles.addAll(Arrays.asList(environment.getActiveProfiles()));
		//将这些配制设置为激活的Profile信息
		environment.setActiveProfiles(StringUtils.toStringArray(profiles));
	}
```

当基本的配制加载后，就要开始进行监听器的事件发出了，通知相关的类进行处理

```java
	void environmentPrepared(ConfigurableEnvironment environment) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.environmentPrepared(environment);
		}
	}
```

当监听完成后，就将环境配制与SpringApplication绑定。

```java
	/**
	 * Bind the environment to the {@link SpringApplication}.
	 *
	 * @param environment the environment to bind
	 */
	protected void bindToSpringApplication(ConfigurableEnvironment environment) {
		try {
			Binder.get(environment).bind("spring.main", Bindable.ofInstance(this));
		} catch (Exception ex) {
			throw new IllegalStateException("Cannot bind to SpringApplication", ex);
		}
	}
```

到这里，大致的一些流程基本已经看明白了，就是在做环境信息的配制，然后进行监听通知下加载。其他一些细节暂时可忽略。



**3. 创建Spring容器**

```java

	/**
	 * The class name of application context that will be used by default for non-web
	 * environments.
	 */
	public static final String DEFAULT_CONTEXT_CLASS = "org.springframework.context."
			+ "annotation.AnnotationConfigApplicationContext";

  /**
	 * The class name of application context that will be used by default for web
	 * environments.
	 */
	public static final String DEFAULT_SERVLET_WEB_CONTEXT_CLASS = "org.springframework.boot."
			+ "web.servlet.context.AnnotationConfigServletWebServerApplicationContext";

	/**
	 * The class name of application context that will be used by default for reactive web
	 * environments.
	 */
	public static final String DEFAULT_REACTIVE_WEB_CONTEXT_CLASS = "org.springframework."
			+ "boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext";


  /**
	 * Strategy method used to create the {@link ApplicationContext}. By default this
	 * method will respect any explicitly set application context or application context
	 * class before falling back to a suitable default.
	 *
	 * @return the application context (not yet refreshed)
	 * @see #setApplicationContextClass(Class)
	 */
	protected ConfigurableApplicationContext createApplicationContext() {
		//根据webApplicationType类型，获得ApplicationContext类型。
		Class<?> contextClass = this.applicationContextClass;
		if (contextClass == null) {
			try {
				switch (this.webApplicationType) {
					case SERVLET:
						//装载AnnotationConfigServletWebServerApplicationContext
						contextClass = Class.forName(DEFAULT_SERVLET_WEB_CONTEXT_CLASS);
						break;
					case REACTIVE:
						//装载AnnotationConfigReactiveWebServerApplicationContext
						contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
						break;
					default:
						//装载AnnotationConfigApplicationContext
						contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
				}
			} catch (ClassNotFoundException ex) {
				throw new IllegalStateException(
						"Unable create a default ApplicationContext, please specify an ApplicationContextClass", ex);
			}
		}
		//创建ApplicationContext对象
		return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
	}
```



**4. 容器的前置处理**

```java
	private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment,
								SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
		//设置容器的上下文环境
		context.setEnvironment(environment);

		//设置上下文的bean生成器和资源加载器
		postProcessApplicationContext(context);

		//执行容器中的ApplicationContextInitializer（包括 spring.factories和自定义的实例）
		applyInitializers(context);

		//触发所有的SpringApplicationRunListener监听器的contextPrepared事件方法
		listeners.contextPrepared(context);

		//记录日志
		if (this.logStartupInfo) {
			logStartupInfo(context.getParent() == null);
			logStartupProfileInfo(context);
		}

		// Add boot specific singleton beans
		//注册启动参数Bean,这里将容器指定的参数封装成Bean，注入容器
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		beanFactory.registerSingleton("springApplicationArguments", applicationArguments);
		if (printedBanner != null) {
			beanFactory.registerSingleton("springBootBanner", printedBanner);
		}
		if (beanFactory instanceof DefaultListableBeanFactory) {
			((DefaultListableBeanFactory) beanFactory)
					.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
		}
		if (this.lazyInitialization) {
			context.addBeanFactoryPostProcessor(new LazyInitializationBeanFactoryPostProcessor());
		}


		// Load the sources
		//加载所有资源
		Set<Object> sources = getAllSources();
		Assert.notEmpty(sources, "Sources must not be empty");

		//加载启动类，将启动类注入容器，为后续开启自动化奠定基础
		load(context, sources.toArray(new Object[0]));

		//触发所有的SpringApplicationRunListener监听器的contextLoader事件方法
		listeners.contextLoaded(context);

		//这会对整个上下文进行一个预处理，比如触发监听器、加载资源、设置上下文环境。
	}
```

此处的代码，就是容器刷新之前的准备动作， 将包含一个非常关键的操作；将启动类注入容器，为后续开启自动化配置奠定基础。



**5. 刷新容器操作**

```java
	private void refreshContext(ConfigurableApplicationContext context) {
		//注册关闭钩子，
		if (this.registerShutdownHook) {
			try {
				//向JVM运行时，注册一个关机沟子，在JVM关机时关闭这个上下文，除非它已经关闭。
				context.registerShutdownHook();
			} catch (AccessControlException ex) {
				// Not allowed in some environments.
			}
		}
		//开启刷新Spring容器，通过refresh方法对整个IOC容器初始化（包括Bean的资源定位、解析、注册等）
		refresh(context);
	}


	/**
	 * Refresh the underlying {@link ApplicationContext}.
	 *
	 * @param applicationContext the application context to refresh
	 */
	protected void refresh(ApplicationContext applicationContext) {
		Assert.isInstanceOf(AbstractApplicationContext.class, applicationContext);
		//启动刷新，执行AbstractApplicationContext的refresh方法，即Spring原来默认的加载主流程。
		((AbstractApplicationContext) applicationContext).refresh();
	}
```

此处将进行真正的Spring容器的加载操作操作。AbstractApplicationContext.refresh 开始Spring的运行流程。



**6. 刷新后置处理，为扩展预留**

```java
	/**
	 * Called after the context has been refreshed.
	 *
	 * @param context the application context
	 * @param args    the application arguments
	 */
	protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
		//该方法没有实现，可以根据需要做一些定制化的操作
	}

```



**7. 发出监听事件**

```java

	void started(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.started(context);
		}
	}
```



**8. Runner的调用**

```java
private void callRunners(ApplicationContext context, ApplicationArguments args) {
   //用于存储Runner的容器
   List<Object> runners = new ArrayList<>();
   //获取所有实现了ApplicationRunner的实现类
   runners.addAll(context.getBeansOfType(ApplicationRunner.class).values());
   //获取所有实现CommandLineRunner的实现类。
   runners.addAll(context.getBeansOfType(CommandLineRunner.class).values());
   //排序操作
   AnnotationAwareOrderComparator.sort(runners);
   //遍历实例对象，进行调用
   for (Object runner : new LinkedHashSet<>(runners)) {
      if (runner instanceof ApplicationRunner) {
         callRunner((ApplicationRunner) runner, args);
      }
      if (runner instanceof CommandLineRunner) {
         callRunner((CommandLineRunner) runner, args);
      }
   }
}
```

此处可以自定义扩展实现，即在spring加载完成后，就可以以对扩展的进行加载，因为当有业务需要在启动完后后做一些事情，可以实现ApplicationRunner或者CommandLineRunner接口进行。

**9. 发布SpringApplicationRunListener的running事件**

发布应用上下文就绪事件。

```
	void running(ConfigurableApplicationContext context) {
		for (SpringApplicationRunListener listener : this.listeners) {
			listener.running(context);
		}
	}
```



# 5. SpringBoot与其他框架的整合

基础的pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>springboot-merge</artifactId>
    <version>1.0-SNAPSHOT</version>


    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>


    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>


    <dependencies>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.49</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```



Spring-boot主启动类

```java
package com.nullnull.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMerge {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootMerge.class, args);
  }
}

```







## 5.1 SpringBoot整合Mybatis

使用的SQL

```sql
# 创建数据库
CREATE DATABASE springbootdata;
# 选择使用数据库
USE springbootdata;

# 创建表t_article并插入相关数据
DROP TABLE IF EXISTS t_article;
CREATE TABLE t_article (
id int(20) NOT NULL AUTO_INCREMENT COMMENT '文章id',
title varchar(200) DEFAULT NULL COMMENT '文章标题',
content longtext COMMENT '文章内容',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO t_article VALUES ('1', 'Spring Boot基础入门', '从入门到精通讲解...');
INSERT INTO t_article VALUES ('2', 'Spring Cloud基础入门', '从入门到精通讲解...');

# 创建表t_comment并插入相关数据
DROP TABLE IF EXISTS t_comment;
CREATE TABLE t_comment (
id int(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
content longtext COMMENT '评论内容',
author varchar(200) DEFAULT NULL COMMENT '评论作者',
a_id int(20) DEFAULT NULL COMMENT '关联的文章id',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO t_comment VALUES ('1', '很全、很详细', 'luccy', '1');
INSERT INTO t_comment VALUES ('2', '赞一个', 'tom', '1');
INSERT INTO t_comment VALUES ('3', '很详细', 'eric', '1');
INSERT INTO t_comment VALUES ('4', '很好，非常详细', '张三', '1');
INSERT INTO t_comment VALUES ('5', '很不错', '李四', '2');
```



### 使用注解方式进行整合

**1. 添加依赖**

```java
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>
```

**2. 声明实体**

```java
package com.nullnull.learn.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 文章的实体信息
 * @since 2023/3/23
 */
@Getter
@Setter
@ToString
public class Tarticle {

  /** id */
  private Integer id;

  /** 文章标题 */
  private String title;

  /** 文章内容 */
  private String content;
}

```

**3. 编写mapper接口**

```
package com.nullnull.learn.mapper;

import com.nullnull.learn.pojo.Tarticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
/**
 * 文章信息
 *
 * @since 2023/3/23
 */
@Mapper
public interface TarticleMapper {

  @Select("select * from t_article where id = #{id}")
  Tarticle queryById(Integer id);
}

```

**4. 配制文件**

```properties
# mysql数据源配制
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdata?serverTimezone=UTC&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
```

**5. 单元测试**

```java
package com.nullnull.learn.mapper;

import com.nullnull.learn.pojo.Tarticle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author liujun
 * @since 2023/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarticleMapper {

  @Autowired private TarticleMapper tarticleMapper;

  @Test
  public void testRunData() {
    Tarticle tarticle = tarticleMapper.queryById(1);
    System.out.println(tarticle);
    Assert.assertNotNull(tarticle);
  }
}


```

**6. 观察控制台**

```tex
2023-03-23 12:43:41.617  INFO 10340 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-03-23 12:43:42.788  INFO 10340 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
Tarticle(id=1, title=Spring Boot基础入门, content=从入门到精通讲解...)

```





### 使用配制文件的方式

**1. 编写实体**

```java
package com.nullnull.learn.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 评论表信息
 *
 * @author liujun
 * @since 2023/3/23
 */
@Getter
@Setter
@ToString
public class Tcomment {

  /** 主键的id */
  private Integer id;

  /** 评论的内容 */
  private String content;

  /** 作者 */
  private String author;

  /** 关联T_article表的信息 */
  private Integer aId;
}

```





**2. 编写Mapper文件**

```java
package com.nullnull.learn.mapper;

import com.nullnull.learn.pojo.Tcomment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论的数据库映射文件
 *
 * @author liujun
 * @since 2023/3/23
 */
@Mapper
public interface TcommentMapper {

  /**
   * 按ID查询评论
   *
   * @param id
   * @return
   */
  Tcomment queryById(Integer id);
}

```



**3. 编写配制文件 **

```xml
<?xml version="1.0" encoding="UTF-8" ?> <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.nullnull.learn.mapper.TcommentMapper">
    <select id="queryById" parameterType="int" resultType="com.nullnull.learn.pojo.Tcomment">
        select * from t_comment where id = #{id}
    </select>
</mapper>
```



**4. 在SpringBoot配制文件中加入配制文件路径**

```properties
# mysql数据源配制
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdata?serverTimezone=UTC&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456


# 配制Mybatis的Xml配置文件路径
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
```



**5. 编写单元测试**

```java
package com.nullnull.learn.mapper;

import com.nullnull.learn.pojo.Tcomment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 测试评价
 *
 * @author liujun
 * @since 2023/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTcommentMapper {

  @Autowired private TcommentMapper tcommentMapper;

  @Test
  public void queryById() {
    Tcomment comments = tcommentMapper.queryById(1);
    System.out.println(comments);
    Assert.assertNotNull(comments);
  }
}

```



**6. 检查控制台**

```
2023-03-23 22:25:46.093  INFO 18384 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-03-23 22:25:46.527  INFO 18384 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
Tcomment(id=1, content=很全、很详细, author=luccy, aId=null)

```

此时我们发现aId这个没有被填充.那如何解决这个问题呢？

此时我们需要配制下mybatis开启驼峰命名就可以解决这个问题了

```properties
# mysql数据源配制
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdata?serverTimezone=UTC&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456


# 配制Mybatis的Xml配置文件路径
mybatis.mapper-locations=classpath:mapper/*Mapper.xml

mybatis.configuration.map-underscore-to-camel-case=true
```



再次运行单元测试,观察控制台

```
2023-03-23 22:28:17.938  INFO 2316 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-03-23 22:28:18.379  INFO 2316 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
Tcomment(id=1, content=很全、很详细, author=luccy, aId=1)
```



**7. 扫描问题**

在当前的配制中我们是针对每个mapper的接口都添加了@Mapper注解。我们还有另外一种解决方案。

那就是在配制SpringBoot的启动配制文件中加入@MapperScan这个注解扫描。

```
package com.nullnull.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author liujun
 * @since 2023/3/23
 */
@SpringBootApplication
@MapperScan(value = {"com.nullnull.learn.mapper"})
public class SpringBootMerge {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootMerge.class, args);
  }
}

```

此时我们便可以将mapper接口上的注解进行删除，再次运行单元测试，

```properties
2023-03-23 22:31:52.077  INFO 1620 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2023-03-23 22:31:52.521  INFO 1620 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
Tcomment(id=1, content=很全、很详细, author=luccy, aId=1)

```

发现依然可以正常的执行。





## 5.2 SpringBoot整合JPA

**1. 添加依赖**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```



**2. 编写存储的实体类**

```java
package com.nullnull.learn.jpa.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 评论的存储对象
 *
 * @author liujun
 * @since 2023/3/24
 */
@Entity(name = "t_comment")
@Getter
@Setter
@ToString
public class Comment {

  /** 主键 @GeneratedValue(strategy = GenerationType.IDENTITY) 设置主键策略 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 内容 */
  private String content;

  /** 作者 */
  private String author;

  /** 关联的文章的id */
  @Column(name = "a_id")
  private Integer aId;
}
```



**3. 添加存储层的接口**

```java
package com.nullnull.learn.jpa.repository;

import com.nullnull.learn.jpa.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 存储层的接口
 *
 * @author liujun
 * @since 2023/3/24
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {}

```





**4. 编写单元测试**

```java
package com.nullnull.learn.jpa.repository;

import com.nullnull.learn.jpa.po.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
/**
 * 测试JPA的存储
 *
 * @author liujun
 * @since 2023/3/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCommentRepository {

  @Autowired private CommentRepository commentRepository;

  @Test
  public void selectComment() {
    Optional<Comment> dataComment = commentRepository.findById(1);
    if (dataComment.isPresent()) {
      System.out.println(dataComment.get());
    }
    Assert.assertEquals(true, dataComment.isPresent());
  }
}

```



**5. 控制台检查**

```tex
Comment(id=1, content=很全、很详细, author=luccy, aId=1)
```



至此SpringBoot整合JPA已经成功的运行。





## 5.3 SpringBoot整合Redis

**1. 添加依赖**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```



**2. 添加存储的实体信息**

```java
package com.nullnull.learn.redis.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import javax.persistence.Id;
/**
 * 存储的实体信息
 *
 * <p>@RedisHash("persons")
 * 用于指定操作实体对象在Redis数据库中的存储空间，此处表示针对Person实体类的数据操作都存储在Redis数据库中名为persons的存储空间下
 *
 * <p>@Id 用于指定实体主键，在Redis数据库中会默认生成字符串形式的HashKey表示唯一的实体对象，当然也可以在存储时，手动指定Id
 *
 * <p>@Index 用于标识对应属性在Redis数据库中生成二级索引，使用该注解后，会在Redis数据库中生成对应的二级索引，索引名称就是属性名，可以方便的过时行数据条件查找
 *
 * @author liujun
 * @since 2023/3/24
 */
@RedisHash("persons")
@Getter
@Setter
@ToString
public class Person {

  /** 主键的Id */
  @Id private String id;

  /** 姓 */
  @Indexed private String firstName;

  /** 名 */
  @Indexed private String lastName;

  /** 地址信息 */
  private Address address;
}

```

Address.java

```java
package com.nullnull.learn.redis.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 地址信息
 *
 * @author liujun
 * @since 2023/3/24
 */
@Getter
@Setter
@ToString
public class Address {

  /** 城市信息 */
  @Indexed private String city;

  /** 国家 */
  @Indexed private String country;
}

```



**3. 添加Redis存储的接口**

```java
package com.nullnull.learn.redis.repository;

import com.nullnull.learn.redis.po.Person;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * 针对Redis的操作
 *
 * @author liujun
 * @since 2023/3/24
 */
public interface PersonRepository extends CrudRepository<Person, String> {

  /**
   * 按地址的城市查找数据
   *
   * @param city
   * @return
   */
  List<Person> findByAddressCity(String city);
}

```



**4. SpringBoot添加Redis配制**

```
# redis的配制
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=
```



**5. 编写单元测试**

```java
package com.nullnull.learn.redis.repository;

import com.nullnull.learn.redis.po.Address;
import com.nullnull.learn.redis.po.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
/**
 * 测试存储
 *
 * @author liujun
 * @since 2023/3/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPersonRepository {

  @Autowired private PersonRepository personRepository;


  @Test
  public void testPerson()
  {
      Person person = new Person();
      person.setId("11");
      person.setFirstName("liu");
      person.setLastName("jun");
      Address dataAddress = new Address();
      dataAddress.setCity("上海");
      dataAddress.setCountry("中国");
      person.setAddress(dataAddress);

      personRepository.save(person);


      //数据查找
     List<Person> dataPerson =  personRepository.findByAddressCity("上海");
     System.out.println(dataPerson);
     Assert.assertNotNull(dataPerson);
  }
}

```



**6. 运行单元测试**

```java
2023-03-24 09:42:13.643  INFO 5608 --- [           main] io.lettuce.core.EpollProvider            : Starting without optional epoll library
2023-03-24 09:42:13.645  INFO 5608 --- [           main] io.lettuce.core.KqueueProvider           : Starting without optional kqueue library
[Person(id=f1a93c7d-419e-4284-9793-747bab2a89eb, firstName=liu, lastName=jun, address=Address(city=上海, country=中国)), Person(id=11, firstName=liu, lastName=jun, address=Address(city=上海, country=中国))]

```

到此SpringBoot整合Redis成功。



## 5.4 SpringBoot整合MyBatis-Plus

### 5.4.1 基础的方式



**1. 引入依赖**

```xml
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.1</version>
        </dependency>

        <!--mybatis plus generator-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.3.1</version>
        </dependency>
```

**2. 编写数据库实体文件**

```java
package com.nullnull.learn.mybatisplus.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 评论信息
 *
 * @author liujun
 * @since 2023/3/25
 */
@Getter
@Setter
@ToString
@TableName("t_comment")
public class Tcomment {

  /** 主键的id */
  private Integer id;

  /** 评论的内容 */
  private String content;

  /** 作者 */
  private String author;

  /** 关联T_article表的信息 */
  @TableField(value = "a_id")
  private Integer aId;
}

```

**3. 编写数据库映射配制文件**

```xml
package com.nullnull.learn.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nullnull.learn.mybatisplus.po.Tcomment;

/**
 * 标识当前为MybatisPlus的类
 *
 * @author liujun
 * @since 2023/3/25
 */
public interface TcommentMapper extends BaseMapper<Tcomment> {}

```

有了mybatisplus后，基础的一些增删改查的代码已经不用再编写了，通过继承BaseMapper已经可以实现基本功能了。如果不能满足，可以使用mybatis的方式，直接定义接口，然后在mapper.xml文件中去实现即可。

**4. 编写单元测试**

```java
package com.nullnull.learn.mybatisplus.mapper;

import com.nullnull.learn.mybatisplus.po.Tcomment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 测试评论的mappper
 *
 * @author liujun
 * @since 2023/3/25
 */
@SpringBootTest
@MapperScan("com.nullnull.learn.mybatisplus.mapper")
@RunWith(SpringRunner.class)
public class TestTcommentMapper {

  @Autowired private TcommentMapper mapper;

  @Test
  public void testBase() {
    Tcomment data = mapper.selectById(2);
    System.out.println(data);
    Assert.assertNotNull(data);
  }
}

```



**5. 运行单元测试查看控制台**

```tex
Tcomment(id=2, content=赞一个, author=tom, aId=1)
```



### 5.4.2 使用ServiceImpl方式

 **1. 添加存储的实现**

```
package com.nullnull.learn.mybatisplus.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nullnull.learn.mybatisplus.mapper.TcommentMapper;
import com.nullnull.learn.mybatisplus.po.Tcomment;
import org.springframework.stereotype.Repository;

/**
 * @author liujun
 * @since 2023/3/25
 */
@Repository
public class TcommentRepository extends ServiceImpl<TcommentMapper, Tcomment> {}

```





**2. 添加单元测试**

```java
package com.nullnull.learn.mybatisplus.repository;

import com.nullnull.learn.mybatisplus.po.Tcomment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
/**
 * 测试一个高级的接口
 *
 * @author liujun
 * @since 2023/3/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan(value = "com.nullnull.learn.mybatisplus.mapper")
public class TestTcommentRepository {

  @Autowired private TcommentRepository tcommentRepository;

  @Test
  public void batch() {
    List<Tcomment> dataCommentList = new ArrayList<>();

    dataCommentList.add(newComment(11, "高兴啊", "nullnull", 1));
    dataCommentList.add(newComment(12, "蹦极", "nullnull", 1));
    dataCommentList.add(newComment(13, "爬山", "nullnull", 1));

    boolean batchRsp = tcommentRepository.saveBatch(dataCommentList);
    Assert.assertEquals(true, batchRsp);

    List<Tcomment> queryList = tcommentRepository.lambdaQuery().eq(Tcomment::getAId, 1).list();
    System.out.println(queryList);
    Assert.assertNotNull(queryList);
  }

  private Tcomment newComment(Integer id, String content, String auth, Integer aid) {
    Tcomment commentData = new Tcomment();

    commentData.setId(id);
    commentData.setContent(content);
    commentData.setAuthor(auth);
    commentData.setAId(aid);
    return commentData;
  }
}

```



**3. 运行单元测试，检查控制台**

```
[Tcomment(id=1, content=很全、很详细, author=luccy, aId=1), Tcomment(id=2, content=赞一个, author=tom, aId=1), Tcomment(id=3, content=很详细, author=eric, aId=1), Tcomment(id=4, content=很好，非常详细, author=张三, aId=1), Tcomment(id=11, content=高兴啊, author=nullnull, aId=1), Tcomment(id=12, content=蹦极, author=nullnull, aId=1), Tcomment(id=13, content=爬山, author=nullnull, aId=1)]

```



# 6. SpringBoot整合Thymeleaf

>Thymeleaf是一种基于现代服务器端的java模板引擎技术，也是优秀的面向java的XML、XHTML、HTML5页面模板，它有丰富的标签语言、函数和表达式，在SpringBoot框架在进行页面设计时，一般会选择Thymeleaf

**Thymeleaf语法**

在HTML页面上使用thymeleaf标签、thymeleaf标签能够动态的替换掉静态内容，使页面动态展示。以下是一个Thymeleaf样例，这是一个HTML中嵌入了Thymeleaf的页面页面文件

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" media="all" href="../../css/gtvg.css" th:href="@{/css/gtvg.css}" />
	<title>Title</title>
</head>
<body>
	<p th:text="${hello}">欢迎进入Thymeleaf的学习</p>
</body>
</html>
```

代码中"xmlns:th="http://www.thymeleaf.org"用于引入Thymeleaf模板引擎标签，使用关键字“th”标注的标签是Thymeleaf模板提供的标签，其中th:href,用于引入外链样式文件。th:text 用于动态的显示标签文本的内容。

thymeleaf常用的标签表

| TH:标签    | 说明                                   |
| ---------- | -------------------------------------- |
| th:insert  | 布局标签，替换内容到引入文件           |
| th:replate | 页面片段包含（类似JSP中的include标签） |
| th:each    | 元素遍历(类似JSP中的C:froeach标签)     |
| th:if      | 条件判断，如果为真                     |
| th:unless  | 条件判断，如果为假                     |
| th:switch  | 条件判断，进行选择性匹配               |
| th:case    | 条件判断，进行选择性匹配               |
| th:value   | 属性值修改，指定标签属性值             |
| th:href    | 用于设定链接地址                       |
| th:src     | 用于设定链接地址                       |
| th:text    | 用于指定标签显示的文本内容             |

**标准表达式**

thymeleaf模板引擎提供了多种表达式语法

| 说明           | 表达式 |
| -------------- | ------ |
| 变量表达式     | ${...} |
| 选择变量表达式 | *{...} |
| 消息表达式     | #{...} |
| 链接URL表达式  | @{...} |
| 片段表达式     | ~{...} |

**1. 变更表达式**

变量表达式用于获取上下文中的变量值

```html
<p th:text="${title}">这是标题</p>
```

 此使用Themeleaf模板中的变量表达式${...}用来动态获取p标签中的内容，如果当前程序没有启动或者当前上下文中不存在title变量，该片段会显示标签默认值“这是标题”，如果当前上下文中存在title变量并且程序已经启动，当前p标签中的默认文本将会被title变量的的值所替换，从而达到模板引擎页面数据动态替换效果。



**thymeleaf的内置对象**

```
# ctx: 上下文对象
# vars: 上下文变量
# locale: 上下文区域设置
# request:(仅限Web Context)HttpServletRequest对象
# response: (仅限Web Context)HttpServletResponse对象
# session: (仅限制Web Context)HttpSession对象
# servletContext: (仅限制Web Context)ServletContext对象
```

结合上述的内置对象，使用Thymeleaf模板引擎页面动态获取当前国家信息，可以使用#local内置对象，

```html
the locale country is <span th:text="${#locale.country}" >US</span>
```



**2.选择变量表达式 *{...}**

选择变量表达式和变量表达式用法类似，一般用于从被选定对象而不是上下文中获取属性值，如果没有选定对象，则和变量表达式一样

```html
<div th:object="${book}">
	<p>title: <span th:text="*{ttile}"></span></p>
</div>
```

*{ttile}选择变量表达式获取当前指定对象book的title属性值



**3. 消息表达式 #{...}**

消息表达式#{...}主要用于Themeleaf模板页面国际化内容的动态替换和展示，使用消息表达式#{...}进行国际化设置时，还需要提供一些国际化配制文件。



**4. 链接表达式@{...}**

链接表达式@{...}一般用于页面跳转或者资源引入，在WEB开发中占据非常重要的地位，并且使用非常频繁。

```html
	<a th:href="@{http://localhost:8080/order/details(orderId=${o.id})}">view</a>
	<a th:href="@{/order/details(orderId=${o.id})}">view</a>
```

上例中使用链接表达式分别编写了绝对链接地址和相对链接地址。在有参数表达式中，需要按照@{路径(参数名称=参数值，参数名称=参数值....)}的形式编写，同时该参数的值可以使用表达式来传递动态参数值。



**5. 片段表达式**

用来标识一个片段模板，并根据需要移动或者传递给其他模板。最常见的用法是使用th:insert或者th:replace属性插入片段

```html
<div th:insert="`{thymeleafDemo::title}"></div>
```

上述代码中，使用th:insertnt属性将title片段模板引用到该标签中。thymeleafDemo为模板名称，Thymeleaf会自动查找“/resource/templates/”目录下的Thymeleaf模板，title为片段名称。

## 基本使用

**1. 添加依赖**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```



**2. 配制application.properties**

```
# 启用模板缓存,
spring.thymeleaf.cache=false

# 模板编码
spring.thymeleaf.encoding=UTF-8

# 应用于模板的模式
spring.thymeleaf.mode=HTML5

# 指定模板页面存放路径
spring.thymeleaf.prefix=classpath:/templates/

# 指定模板页面名称的后缀
spring.thymeleaf.suffix=.html
```



**3. 编写controller**

```java
package com.nullnull.learn.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
/**
 * @author liujun
 * @since 2023/3/25
 */
@Controller
public class LoginController {

  /**
   * 获取并封装当前年份跳转至登录页login.html
   *
   * @param model
   * @return
   */
  @RequestMapping("/toLoginPage")
  public String toLoginPage(Model model) {
    model.addAttribute("currentYear", LocalDate.now().getYear());
    return "login";
  }
}

```



**4. 编写html文件**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>用户登录界面</title>
    <link th:href="@{/login/css/bootstrap.min.css}" rel="stylesheet">
    <link th:href="@{/login/css/signin.css}" rel="stylesheet">
</head>
<body class="text-center">
<!--用户登录表单-->
<form class="form-signin">
    <img class="mb-4" th:src="@{/login/img/key-g341039857_640.jpg}" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">请登录</h1>
    <input type="text" class="form-control" th:placeholder="用户名" required="" autofocus="">
    <input type="password" class="form-control" th:placeholder="密码" required="" autofocus="">
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> 记住我
        </label>
    </div>

    <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    <p class="mt-5 mb-3 text-muted">© <span th:text="${currentYear}">2019</span>-<span th:text="${currentYear+1}">2020</span></p>
</form>
</body>

</html>
```





**5. 打开页面测试**

在浏览器中输入：http://127.0.0.1:8080/toLoginPage 就可以对页面进行查看。



## 基本国际化页面

**1. 配置国际化资源**

login.properties

```properties
login.tip=请登录
login.username=用户名
login.password=密码
login.rememberme=记住我
login.button=登录
```

login_zh_CN.properties

```properties
login.tip=请登录
login.username=用户名
login.password=密码
login.rememberme=记住我
login.button=登录
```

login_en_US.properties

```properties
login.tip=Please sign in
login.username=Username
login.password=Password
login.rememberme=Remember me
login.button=Login
```



**2. 配制文件配制**

```
# 配制国际化文件的基础目录
spring.messages.basename=i18n.login
```





**3. 修改页面文件**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>用户登录界面</title>
    <link th:href="@{/login/css/bootstrap.min.css}" rel="stylesheet">
    <link th:href="@{/login/css/signin.css}" rel="stylesheet">
</head>
<body class="text-center">
<!--用户登录表单-->
<form class="form-signin">
    <img class="mb-4" th:src="@{/login/img/key-g341039857_640.jpg}" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">请登录</h1>
    <input type="text" class="form-control" th:placeholder="#{login.username}" required="" autofocus="">
    <input type="password" class="form-control" th:placeholder="#{login.password}" required="" autofocus="">
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> [[#{login.rememberme}]]
        </label>
    </div>

    <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.button}">登录</button>
    <p class="mt-5 mb-3 text-muted">© <span th:text="${currentYear}">2019</span>-<span th:text="${currentYear+1}">2020</span></p>
</form>
</body>

</html>
```



**4. 启动服务查看页面**

在浏览器中输入：http://127.0.0.1:8080/toLoginPage 就可以对页面进行查看。





## 动态国际化切换的功能

**1. 添加国际化语言切换接口**

```java
package com.nullnull.learn.thymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
/**
 * 国际化语言切换
 *
 * @author liujun
 * @since 2023/3/26
 */
@Configuration
public class MyLocalResolver implements LocaleResolver {

  @Override
  public Locale resolveLocale(HttpServletRequest httpServletRequest) {

    // 1,按页面传递的参数进行国际化语言切换
      String languageParam = httpServletRequest.getParameter("l");

    // 未传递参数，按默认请求头的类型
    if (StringUtils.isEmpty(languageParam)) {
      // 读取Header头中的参数,信息，例如Accept-Language: en-US,en;q=0.9
      String headerLanguage = httpServletRequest.getHeader("Accept-Language");

      String[] headerCfg = headerLanguage.split(",");
      String[] localParam = headerCfg[0].split("-");
      return new Locale(localParam[0], localParam[1]);
    }
    String[] localParam = languageParam.split("_");
    return new Locale(localParam[0], localParam[1]);
  }

  @Override
  public void setLocale(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Locale locale) {}

  @Bean
  public LocaleResolver localeResolver() {
    return new MyLocalResolver();
  }
}

```



**2. 修改login.html页面**

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>用户登录界面</title>
    <link th:href="@{/login/css/bootstrap.min.css}" rel="stylesheet">
    <link th:href="@{/login/css/signin.css}" rel="stylesheet">
</head>
<body class="text-center">
<!--用户登录表单-->
<form class="form-signin">
    <img class="mb-4" th:src="@{/login/img/key-g341039857_640.jpg}" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">请登录</h1>
    <input type="text" class="form-control" th:placeholder="#{login.username}" required="" autofocus="">
    <input type="password" class="form-control" th:placeholder="#{login.password}" required="" autofocus="">
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> [[#{login.rememberme}]]
        </label>
    </div>

    <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.button}">登录</button>
    <p class="mt-5 mb-3 text-muted">© <span th:text="${currentYear}">2019</span>-<span th:text="${currentYear+1}">2020</span></p>


    <a class="btn btn-sm" th:href="@{/toLoginPage(l='zh_CN')}">中文</a>
    <a class="btn btn-sm" th:href="@{/toLoginPage(l='en_US')}" >English</a>

</form>
</body>

</html>
```



**3. 执行测试**

在浏览器中输入：http://127.0.0.1:8080/toLoginPage 就可以对页面进行查看。

可以点击“中文”、或者English查看动态切换的一个效果。





# 7. SpringBoot缓存管理

Spring框架支持透明的向应用程序添加缓存并对缓存进行管理，其管理缓存的核心是将缓存应用于操作数据的方法，从而减少操作数据的执行次数，同时不会对程序本身造成任何的干扰。

SpringBoot继承了Spring框架的缓存管理功能，通过使用@EnableCaching注解开启基于注解的缓存支持，SpringBoot就可以启动缓存管理的自动化配制。



## 7.1 基础环境搭建

还是使用这两个表

```sql
# 创建数据库
CREATE DATABASE springbootdata;
# 选择使用数据库
USE springbootdata;

# 创建表t_article并插入相关数据
DROP TABLE IF EXISTS t_article;
CREATE TABLE t_article (
id int(20) NOT NULL AUTO_INCREMENT COMMENT '文章id',
title varchar(200) DEFAULT NULL COMMENT '文章标题',
content longtext COMMENT '文章内容',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO t_article VALUES ('1', 'Spring Boot基础入门', '从入门到精通讲解...');
INSERT INTO t_article VALUES ('2', 'Spring Cloud基础入门', '从入门到精通讲解...');

# 创建表t_comment并插入相关数据
DROP TABLE IF EXISTS t_comment;
CREATE TABLE t_comment (
id int(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
content longtext COMMENT '评论内容',
author varchar(200) DEFAULT NULL COMMENT '评论作者',
a_id int(20) DEFAULT NULL COMMENT '关联的文章id',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO t_comment VALUES ('1', '很全、很详细', 'luccy', '1');
INSERT INTO t_comment VALUES ('2', '赞一个', 'tom', '1');
INSERT INTO t_comment VALUES ('3', '很详细', 'eric', '1');
INSERT INTO t_comment VALUES ('4', '很好，非常详细', '张三', '1');
INSERT INTO t_comment VALUES ('5', '很不错', '李四', '2');
```



**1. MAVEN的依赖**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>springboot-cache</artifactId>
    <version>1.0-SNAPSHOT</version>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.49</version>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

**2. 实体编写**

```
package com.nullnull.learn.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 评论信息对应的java实体
 *
 * @author liujun
 * @since 2023/3/26
 */
@Getter
@Setter
@ToString
@Entity(name = "t_comment")
public class Comment {

  /** 主键的ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 内容信息 */
  private String content;

  /** 作都 信息 */
  private String author;

  /** 映射字段对应不上，添加注解 */
  @Column(name = "a_id")
  private Integer aId;
}

```





**3. repository编写**

```java
package com.nullnull.learn.repository;

import com.nullnull.learn.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
/**
 * 评论的存储表
 *
 * @author liujun
 * @since 2023/3/26
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  /**
   * 按评论的id进行更新作者
   *
   * @param author 作者
   * @param id id
   * @return 修改的结果
   */
  @Transactional
  @Modifying
  @Query(value = "update t_comment set author = ?1 where id = ?2", nativeQuery = true)
  public int updateComment(String author, Integer id);
}

```



**4. Service编写**

```java
package com.nullnull.learn.service;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**
 * 评论的服务
 *
 * @author liujun
 * @since 2023/3/26
 */
@Service
public class CommentService {

  @Autowired private CommentRepository commentRepository;

  /**
   * 按id进行查询
   *
   * @param id
   * @return
   */
  public Comment findCommentById(Integer id) {
    Optional<Comment> comment = commentRepository.findById(id);

    if (comment.isPresent()) {
      Comment result = comment.get();
      return result;
    }

    return null;
  }
}

```



**5. controller编写**

```java
package com.nullnull.learn.controller;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论的Controller
 *
 * @author liujun
 * @since 2023/3/26
 */
@RestController
public class CommentController {

  @Autowired private CommentService commentService;

  @RequestMapping(value = "/findCommentById")
  public Comment findCommentById(Integer id) {
    return commentService.findCommentById(id);
  }
}

```



**6. 配制文件编写**

```properties
# Mysql 数据库连接配制
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdata?serverTimezone=UTC&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.show-sql=true
# 解决乱码问题
spring.http.encoding.force-response=true
```



**7. SpringBoot启动函数**

```
package com.nullnull.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 主启动函数 */
@SpringBootApplication
public class SpringBootCache {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootCache.class, args);
  }
}
```



**8. 启动服务进行测试**

当启动服务后，在浏览器中输入：http://127.0.0.1:8080/findCommentById?id=1

就可以看到

```properties
{"id":1,"content":"很全、很详细","author":"luccy","aid":1}
```

控制台输出：

```
Hibernate: select comment0_.id as id1_0_0_, comment0_.a_id as a_id2_0_0_, comment0_.author as author3_0_0_, comment0_.content as content4_0_0_ from t_comment comment0_ where comment0_.id=?
```





## 7.2 使用默认缓存管理

在之前的样例中。如果我们在页面中发送多次的一个请求，就会调用多次的一个数据库的查询，这对数据库来连接来说是一个浪费，我们需要加一个缓存，来对数据进行缓存。

通过控制台就可以发现SQL被调用了多次

```
Hibernate: select comment0_.id as id1_0_0_, comment0_.a_id as a_id2_0_0_, comment0_.author as author3_0_0_, comment0_.content as content4_0_0_ from t_comment comment0_ where comment0_.id=?
Hibernate: select comment0_.id as id1_0_0_, comment0_.a_id as a_id2_0_0_, comment0_.author as author3_0_0_, comment0_.content as content4_0_0_ from t_comment comment0_ where comment0_.id=?
Hibernate: select comment0_.id as id1_0_0_, comment0_.a_id as a_id2_0_0_, comment0_.author as author3_0_0_, comment0_.content as content4_0_0_ from t_comment comment0_ where comment0_.id=?
```



**1. 在启动函数中启用缓存注解**

```java
package com.nullnull.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/** 主启动函数
 *
 * @EnableCaching 开启Spring Boot基于注解的缓存管理支持。
 *
 * */
@EnableCaching
@SpringBootApplication
public class SpringBootCache {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootCache.class, args);
  }
}

```



**2. 在服务层添加缓存注解**

```java
package com.nullnull.learn.service;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**
 * 评论的服务
 *
 * @author liujun
 * @since 2023/3/26
 */
@Service
public class CommentService {

  @Autowired private CommentRepository commentRepository;

  /**
   * 按id进行查询
   *
   * @param id
   * @return
   */
  @Cacheable(cacheNames = "comment")
  public Comment findCommentById(Integer id) {
    Optional<Comment> comment = commentRepository.findById(id);

    if (comment.isPresent()) {
      Comment result = comment.get();
      return result;
    }

    return null;
  }
}

```



**3. 启动应用以检查缓存是否生效**

当我们多次请求：http://127.0.0.1:8080/findCommentById?id=1时，就可以观察到控制台，就只输出了一次	

```sql
Hibernate: select comment0_.id as id1_0_0_, comment0_.a_id as a_id2_0_0_, comment0_.author as author3_0_0_, comment0_.content as content4_0_0_ from t_comment comment0_ where comment0_.id=?
```





**底层结构**

这时候我们来看下SpringBoot的底层结构，在诸多的结存自动配置类中，SpringBoot默认装配的是SimpleCacheConfiguration，它使用CacheManger是ConcurrentMapCacheManager,使用ConcurrentHashMap当底层的数据结构，按照Cache的名称查询出Cache，每一个Cache中存在多个K-V键值对，缓存key：默认认在只有一个参数的情况，Key值默认就是方法的参数值，如果没有参数或者多个参数的的情况，使用simpleKeyGenerate来生成key





SimpleCacheConfiguration.java



```java
package org.springframework.boot.autoconfigure.cache;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Simplest cache configuration, usually used as a fallback.
 *
 * @author Stephane Nicoll
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(CacheManager.class)
@Conditional(CacheCondition.class)
class SimpleCacheConfiguration {

	@Bean
	ConcurrentMapCacheManager cacheManager(CacheProperties cacheProperties,
			CacheManagerCustomizers cacheManagerCustomizers) {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
		List<String> cacheNames = cacheProperties.getCacheNames();
		if (!cacheNames.isEmpty()) {
			cacheManager.setCacheNames(cacheNames);
		}
		return cacheManagerCustomizers.customize(cacheManager);
	}

}

```



ConcurrentMapCacheManager.java

```java

package org.springframework.cache.concurrent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.serializer.support.SerializationDelegate;
import org.springframework.lang.Nullable;

/**
 * {@link CacheManager} implementation that lazily builds {@link ConcurrentMapCache}
 * instances for each {@link #getCache} request. Also supports a 'static' mode where
 * the set of cache names is pre-defined through {@link #setCacheNames}, with no
 * dynamic creation of further cache regions at runtime.
 *
 * <p>Note: This is by no means a sophisticated CacheManager; it comes with no
 * cache configuration options. However, it may be useful for testing or simple
 * caching scenarios. For advanced local caching needs, consider
 * {@link org.springframework.cache.jcache.JCacheCacheManager},
 * {@link org.springframework.cache.ehcache.EhCacheCacheManager},
 * {@link org.springframework.cache.caffeine.CaffeineCacheManager}.
 *
 * @author Juergen Hoeller
 * @since 3.1
 * @see ConcurrentMapCache
 */
public class ConcurrentMapCacheManager implements CacheManager, BeanClassLoaderAware {

	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

	private boolean dynamic = true;

	private boolean allowNullValues = true;

	private boolean storeByValue = false;

	@Nullable
	private SerializationDelegate serialization;


	/**
	 * Construct a dynamic ConcurrentMapCacheManager,
	 * lazily creating cache instances as they are being requested.
	 */
	public ConcurrentMapCacheManager() {
	}

	/**
	 * Construct a static ConcurrentMapCacheManager,
	 * managing caches for the specified cache names only.
	 */
	public ConcurrentMapCacheManager(String... cacheNames) {
		setCacheNames(Arrays.asList(cacheNames));
	}


	/**
	 * Specify the set of cache names for this CacheManager's 'static' mode.
	 * <p>The number of caches and their names will be fixed after a call to this method,
	 * with no creation of further cache regions at runtime.
	 * <p>Calling this with a {@code null} collection argument resets the
	 * mode to 'dynamic', allowing for further creation of caches again.
	 */
	public void setCacheNames(@Nullable Collection<String> cacheNames) {
		if (cacheNames != null) {
			for (String name : cacheNames) {
				this.cacheMap.put(name, createConcurrentMapCache(name));
			}
			this.dynamic = false;
		}
		else {
			this.dynamic = true;
		}
	}

	/**
	 * Specify whether to accept and convert {@code null} values for all caches
	 * in this cache manager.
	 * <p>Default is "true", despite ConcurrentHashMap itself not supporting {@code null}
	 * values. An internal holder object will be used to store user-level {@code null}s.
	 * <p>Note: A change of the null-value setting will reset all existing caches,
	 * if any, to reconfigure them with the new null-value requirement.
	 */
	public void setAllowNullValues(boolean allowNullValues) {
		if (allowNullValues != this.allowNullValues) {
			this.allowNullValues = allowNullValues;
			// Need to recreate all Cache instances with the new null-value configuration...
			recreateCaches();
		}
	}

	/**
	 * Return whether this cache manager accepts and converts {@code null} values
	 * for all of its caches.
	 */
	public boolean isAllowNullValues() {
		return this.allowNullValues;
	}

	/**
	 * Specify whether this cache manager stores a copy of each entry ({@code true}
	 * or the reference ({@code false} for all of its caches.
	 * <p>Default is "false" so that the value itself is stored and no serializable
	 * contract is required on cached values.
	 * <p>Note: A change of the store-by-value setting will reset all existing caches,
	 * if any, to reconfigure them with the new store-by-value requirement.
	 * @since 4.3
	 */
	public void setStoreByValue(boolean storeByValue) {
		if (storeByValue != this.storeByValue) {
			this.storeByValue = storeByValue;
			// Need to recreate all Cache instances with the new store-by-value configuration...
			recreateCaches();
		}
	}

	/**
	 * Return whether this cache manager stores a copy of each entry or
	 * a reference for all its caches. If store by value is enabled, any
	 * cache entry must be serializable.
	 * @since 4.3
	 */
	public boolean isStoreByValue() {
		return this.storeByValue;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.serialization = new SerializationDelegate(classLoader);
		// Need to recreate all Cache instances with new ClassLoader in store-by-value mode...
		if (isStoreByValue()) {
			recreateCaches();
		}
	}


	@Override
	public Collection<String> getCacheNames() {
		return Collections.unmodifiableSet(this.cacheMap.keySet());
	}

	@Override
	@Nullable
	public Cache getCache(String name) {
		Cache cache = this.cacheMap.get(name);
		if (cache == null && this.dynamic) {
			synchronized (this.cacheMap) {
				cache = this.cacheMap.get(name);
				if (cache == null) {
					cache = createConcurrentMapCache(name);
					this.cacheMap.put(name, cache);
				}
			}
		}
		return cache;
	}

	private void recreateCaches() {
		for (Map.Entry<String, Cache> entry : this.cacheMap.entrySet()) {
			entry.setValue(createConcurrentMapCache(entry.getKey()));
		}
	}

	/**
	 * Create a new ConcurrentMapCache instance for the specified cache name.
	 * @param name the name of the cache
	 * @return the ConcurrentMapCache (or a decorator thereof)
	 */
	protected Cache createConcurrentMapCache(String name) {
		SerializationDelegate actualSerialization = (isStoreByValue() ? this.serialization : null);
		return new ConcurrentMapCache(name, new ConcurrentHashMap<>(256),
				isAllowNullValues(), actualSerialization);

	}

}

```

SimpleKeyGenerator.java

```java
public class SimpleKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return generateKey(params);
	}

	/**
	 * Generate a key based on the specified parameters.
	 */
	public static Object generateKey(Object... params) {
		if (params.length == 0) {
			return SimpleKey.EMPTY;
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass().isArray()) {
				return param;
			}
		}
		return new SimpleKey(params);
	}

}
```



## 7.3 缓存的相关介绍

**@EbanleCaching注解**

@EnableCaching是由Spring框架提供的，springboot框架对该注解进行了继承，该注解需要配制在类上（通常配制在项目启动类上），用于开始基于注解的缓存支持



**@Cacheable注解**

@Cacheable注解也是由Spring框架提供的，可以用于类或者方法（通常用于在数据查询方法上），用于对方法结果进行缓存存储。注解的执行顺序是，先进行缓存查询，如果为空进行方法查询，并将结果缓存；如果缓存中有数据，不进行方法查询，而是直接使用缓存的数据。

@cacheable提供了多个属性，用于对缓存存储进行相关配制

| 属性名           | 说明                                                      |
| ---------------- | --------------------------------------------------------- |
| value/cacheNames | 指定缓存空间的名称，必配属性，这两个属性二选一使用        |
| key              | 指定缓存数据的key，默认使用方法参数值，可以使用SpEL表达式 |
| keyGenerator     | 指定缓存数据的key的生成器，与Key属性二选一使用            |
| cacheManager     | 指定缓存管理器                                            |
| cacheResolver    | 指定缓存解析器，与cacheManager属性二选一使用              |
| condition        | 指定在符合条件下，进行数据缓存                            |
| unless           | 指定在符合条件下，不进行数据缓存                          |
| sync             | 指定是否使用异常缓存，默认false                           |



**执行流程&时机**

方法执行之前，先查询Cache（缓存组件），按照cacheNames指定的名字获取，（CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建；

去Cache中查询缓存的内容，使用一个key，默认就是方法的参数，如果多个参数或者没有参数，是按照某种策略生成的，默认使用的是KeyGenerator生成的，使用SimpleKeyGenerator生成key，SimpleKeyGenerator生成key的默认策略

| 参数个数   | Key               |
| ---------- | ----------------- |
| 没有参数   | new SimpleKey()   |
| 有一个参数 | 参数值            |
| 多个参数   | newSimple(params) |

常用SPEL表达式

| 描述                       | 示例                                                |
| -------------------------- | --------------------------------------------------- |
| 当前被调用的方法名         | #root.mathonName                                    |
| 当前被调用的方法           | #root.method                                        |
| 当前被调用的目标对象       | #root.target                                        |
| 当前被调用的目标对象类     | #root.targetClass                                   |
| 当前被调用的方法的参数列表 | #root.args[0]第一个参数，#root.args[1]第二个参数... |
| 根据参数名称取出值         | #参数名，也可以使用#p0 #a0 0是参数的下标索引        |
| 当前方法的返回值           | #result                                             |



**3. @CachePut**

目标方法执行完之后生效，@CachePut被使用于修改操作比较多，哪怕缓存中已经存在目标值了，但是这个注解保证这个方法依然会执行，执行之后的结果被保存在缓存中

@CachePut注解也提供了多个属性，这些属性与@Cahceable注解的属性完全相同。

更新操作，前端会把id+实体传递到后端使用，我们就直接指定方法的返回值从新存进缓存时的key=“#id”，如果前端只是给了实体，我们就使用key=“#实体.id”获取key，同时他的执行时机是目标方法结束后执行，所以也可以使用key=“result.id" 拿出返回值的id



**@CacheEvict注解**

@CacheEvict注解是Spring框架提供的，可以作于用类或者方法（通常作用于数据删除方法上）该注解的作用是删除缓存数据。@CacheEvict注解的默认执行顺序是先过进行方法的调用，然后将缓存进行清除。



在Spring Boot中，数据的缓存管理存储依赖于Spring框架中cache相关的

org.springframework.cache.Cache和org.springframework.cache.CacheManager缓存管理器接口。

如果程序中没有定义类型为CacheManager的Bean组件或者是名为cacheResolver的CacheResolver缓

存解析器，Spring Boot将尝试选择并启用以下缓存组件（按照指定的顺序）：

（1）Generic

（2）JCache (JSR-107) (EhCache 3、Hazelcast、Infifinispan等) 

（3）EhCache 2.x

（4）Hazelcast

（5）Infifinispan

（6）Couchbase

（7）Redis

（8）Caffffeine



## 7.4 基于注解的缓存-Redis

**1. 添加依赖**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

**2. 创建数据库实体**

```java
package com.nullnull.learn.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 评论信息对应的java实体
 *
 * @author liujun
 * @since 2023/3/26
 */
@Getter
@Setter
@ToString
@Entity(name = "t_comment")
public class Comment {

  /** 主键的ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 内容信息 */
  private String content;

  /** 作都 信息 */
  private String author;

  /** 映射字段对应不上，添加注解 */
  @Column(name = "a_id")
  private Integer aId;
}

```

**3. 创建CommentRepository**

```java
package com.nullnull.learn.repository;

import com.nullnull.learn.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
/**
 * 评论的存储表
 *
 * @author liujun
 * @since 2023/3/26
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  /**
   * 按评论的id进行更新作者
   *
   * @param author 作者
   * @param id id
   * @return 修改的结果
   */
  @Transactional
  @Modifying
  @Query(value = "update t_comment set author = ?1 where id = ?2", nativeQuery = true)
  public int updateComment(String author, Integer id);
}

```



**4. 创建CommentService**

```java
package com.nullnull.learn.service;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**
 * 评论的服务
 *
 * @author liujun
 * @since 2023/3/26
 */
@Service
public class CommentService {

  @Autowired private CommentRepository commentRepository;

  /**
   * 按id进行查询 @Cacheable 将该方法查询结果Comment存放在springboot默认缓存中
   *
   * <p>cacheNames： 起一个缓存命名空间，对应缓存唯一标识
   *
   * <p>value： 缓存结果 key：默认认在只有一个参数的情况，Key值默认就是方法的参数值， 如果没有参数或者多个参数的的情况，使用simpleKeyGenerate来生成key
   *
   * <p>unless = "#result == null" 表示如果当前的缓存结果为null，则不进行缓存
   *
   * @param id
   * @return
   */
  @Cacheable(cacheNames = "comment", unless = "#result == null")
  public Comment findCommentById(Integer id) {
    Optional<Comment> comment = commentRepository.findById(id);

    if (comment.isPresent()) {
      Comment result = comment.get();
      return result;
    }
    return null;
  }

  /**
   * 进行数据修改操作,修改完成后，进行缓存的更新
   *
   * @param auth 作者
   * @param id id
   * @return
   */
  @CachePut(cacheNames = "comment", key = "#result.id")
  public Comment updateComment(String auth, Integer id) {

    Optional<Comment> dataComment = commentRepository.findById(id);

    Comment authComment = dataComment.get();
    authComment.setAuthor(auth);
    commentRepository.updateComment(authComment.getAuthor(), authComment.getId());
    return authComment;
  }

  /**
   * 按id进行数据删除
   *
   * @param deleteId
   */
  @CacheEvict(cacheNames = "comment")
  public void deleteComment(Integer deleteId) {
    commentRepository.deleteById(deleteId);
  }
}

```

updateComment方法，配制的注解为  @CachePut，即在执行完后，更新缓存的内容，以方法的返回结果去更新，缓存的KEY为返回结果中的ID属性

deleteComment在执行删除后，也需要更新缓存，未指定key，以默认的一个参数去做key



**5. CommentController**

```java
package com.nullnull.learn.controller;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论的Controller
 *
 * @author liujun
 * @since 2023/3/26
 */
@RestController
public class CommentController {

  @Autowired private CommentService commentService;

  @RequestMapping(value = "/findCommentById")
  public Comment findCommentById(Integer id) {
    return commentService.findCommentById(id);
  }

  /**
   * 按id更新作者
   *
   * @param id id的信息
   * @param author 作者
   * @return
   */
  @RequestMapping(value = "/updateCommentById")
  public Comment updateComment(Integer id, String author) {
    commentService.updateComment(author, id);

    return commentService.findCommentById(id);
  }

  /**
   * 按id删除
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "/deleteById")
  public Comment deleteById(Integer id) {
    commentService.deleteComment(id);
    return commentService.findCommentById(id);
  }
}

```

**6. 对缓存进行配制**

```properties
# Mysql 数据库连接配制
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springbootdata?serverTimezone=UTC&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.show-sql=true
# 解决乱码问题
spring.http.encoding.force-response=true

# redis的配制
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=

# 统一的缓存过期时间配制,有效期为1分钟，单位毫秒
spring.cache.redis.time-to-live=60000
```



**7. 启动服务进行测试**

调用接口：http://127.0.0.1:8080/findCommentById?id=2

此时发生一个错误

```tex
Whitelabel Error Page
This application has no explicit mapping for /error, so you are seeing this as a fallback.

Sun Mar 26 17:20:55 CST 2023
There was an unexpected error (type=Internal Server Error, status=500).
Cannot serialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to serialize object using DefaultSerializer; nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.nullnull.learn.po.Comment]
```

观察控制台发现

```java
2023-03-26 17:20:55.858 ERROR 18608 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.data.redis.serializer.SerializationException: Cannot serialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to serialize object using DefaultSerializer; nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.nullnull.learn.po.Comment]] with root cause

java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.nullnull.learn.po.Comment]
	at org.springframework.core.serializer.DefaultSerializer.serialize(DefaultSerializer.java:43) ~[spring-core-5.2.2.RELEASE.jar:5.2.2.RELEASE]
	at org.springframework.core.serializer.support.SerializingConverter.convert(SerializingConverter.java:63) ~[spring-core-5.2.2.RELEASE.jar:5.2.2.RELEASE]
	at org.springframework.core.serializer.support.SerializingConverter.convert(SerializingConverter.java:35) ~[spring-core-5.2.2.RELEASE.jar:5.2.2.RELEASE]
	at org.springframework.data.redis.serializer.JdkSerializationRedisSerializer.serialize(JdkSerializationRedisSerializer.java:94) ~[spring-data-redis-2.2.3.RELEASE.jar:2.2.3.RELEASE]
	at org.springframework.data.redis.serializer.DefaultRedisElementWriter.write(DefaultRedisElementWriter.java:43) ~[spring-data-redis-2.2.3.RELEASE.jar:2.2.3.RELEASE]
	at org.springframework.data.redis.serializer.RedisSerializationContext$SerializationPair.write(RedisSerializationContext.java:287) ~[spring-data-redis-2.2.3.RELEASE.jar:2.2.3.RELEASE]
	at org.springframework.data.redis.cache.RedisCache.serializeCacheValue(RedisCache.java:244) ~[spring-data-redis-2.2.3.RELEASE.jar:2.2.3.RELEASE]
	at org.springframework.data.redis.cache.RedisCache.put(RedisCache.java:150) ~[spring-data-redis-2.2.3.RELEASE.jar:2.2.3.RELEASE]
	at org.springframework.cache.interceptor.AbstractCacheInvoker.doPut(AbstractCacheInvoker.java:87) ~[spring-context-
......
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-9.0.29.jar:9.0.29]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_291]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_291]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-9.0.29.jar:9.0.29]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_291]
```



**8. 错误解决**

通过观察错误我们发现，存储到Redis中的实体需要实现序列化接口。那我们将存储实体加上序列化

```java
package com.nullnull.learn.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

/**
 * 评论信息对应的java实体
 *
 * @author liujun
 * @since 2023/3/26
 */
@Getter
@Setter
@ToString
@Entity(name = "t_comment")
public class Comment implements Serializable {

  /** 主键的ID */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 内容信息 */
  private String content;

  /** 作都 信息 */
  private String author;

  /** 映射字段对应不上，添加注解 */
  @Column(name = "a_id")
  private Integer aId;
}

```



**9. 再次测试**

再次访问：http://127.0.0.1:8080/findCommentById?id=2

可以发现页面输出了：

```json
{"id":2,"content":"赞一个","author":"tom","aid":1}
```

去缓存中查看下数据

```
127.0.0.1:6379> get comment::2
"\xac\xed\x00\x05sr\x00\x1dcom.nullnull.learn.po.Comment!PY\x8d\x10\xf6,\xc3\x02\x00\x04L\x00\x03aIdt\x00\x13Ljava/lang/Integer;L\x00\x06authort\x00\x12Ljava/lang/String;L\x00\acontentq\x00~\x00\x02L\x00\x02idq\x00~\x00\x01xpsr\x00\x11java.lang.Integer\x12\xe2\xa0\xa4\xf7\x81\x878\x02\x00\x01I\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x00\x01t\x00\x03tomt\x00\t\xe8\xb5\x9e\xe4\xb8\x80\xe4\xb8\xaasq\x00~\x00\x04\x00\x00\x00\x02"
```

数据已经成功的放入了缓存



**10. 更新缓存中的数据**

访问：http://127.0.0.1:8080/updateCommentById?id=2&author=feifei

将id为2的作者更新为feifei,原来是tom

当请求完成后，我们可以看到页面上：

```
{"id":2,"content":"赞一个","author":"feifei","aid":1}
```

控制台输出：

```
Hibernate: select comment0_.id as id1_0_0_, comment0_.a_id as a_id2_0_0_, comment0_.author as author3_0_0_, comment0_.content as content4_0_0_ from t_comment comment0_ where comment0_.id=?
Hibernate: update t_comment set a_id=?, author=?, content=? where id=?
Hibernate: update t_comment set author = ? where id = ?
```

当再次调用http://127.0.0.1:8080/findCommentById?id=2时，发现控制台并未打印SQL





**11. 删除缓存中的数据**

首先检查中的数据

```
127.0.0.1:6379> get comment::2
"\xac\xed\x00\x05sr\x00\x1dcom.nullnull.learn.po.Comment!PY\x8d\x10\xf6,\xc3\x02\x00\x04L\x00\x03aIdt\x00\x13Ljava/lang/Integer;L\x00\x06authort\x00\x12Ljava/lang/String;L\x00\acontentq\x00~\x00\x02L\x00\x02idq\x00~\x00\x01xpsr\x00\x11java.lang.Integer\x12\xe2\xa0\xa4\xf7\x81\x878\x02\x00\x01I\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x00\x01t\x00\x06feifeit\x00\t\xe8\xb5\x9e\xe4\xb8\x80\xe4\xb8\xaasq\x00~\x00\x04\x00\x00\x00\x02"
```



访问：http://127.0.0.1:8080/deleteById?id=2

再次检查数据

```
127.0.0.1:6379> get comment::2
(nil)
```



发现数据已经被删除了



>使用SpringBoot的缓存注解已经基本使用完了，但发现一个问题，那就是缓存的时间不能很灵活的配制，使用了一个固定的缓存时间，针对此问题，就需要使用API的方式来对缓存进行管理。





## 7.5 基于API的Redis的缓存

**1. 添加Service**

```java
package com.nullnull.learn.service;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.repository.CommentRepository;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 评论的服务
 *
 * @author liujun
 * @since 2023/3/26
 */
@Service
public class CommentApiService {

  @Autowired private CommentRepository commentRepository;

  @Autowired private RedisTemplate redisTemplate;

  /**
   * 按id进行查询
   *
   * @param id
   * @return
   */
  public Comment findCommentById(Integer id) {
    Object commentObj = redisTemplate.opsForValue().get(key(id));
    // 如果当前为空，则加载缓存，存储到redis中
    if (null == commentObj) {
      Optional<Comment> comment = commentRepository.findById(id);
      if (comment.isPresent()) {
        Comment result = comment.get();
        redisTemplate.opsForValue().set(key(id), result, 1, TimeUnit.DAYS);
        return result;
      }
    }
    // 如果当前缓存中数据存在
    else {
      return (Comment) commentObj;
    }

    return null;
  }

  private String key(Integer id) {
    return "comment_" + id;
  }

  /**
   * 进行数据修改操作,修改完成后，进行缓存的更新
   *
   * @param auth 作者
   * @param id id
   * @return
   */
  public Comment updateComment(String auth, Integer id) {

    Optional<Comment> dataComment = commentRepository.findById(id);

    Comment authComment = dataComment.get();
    authComment.setAuthor(auth);
    commentRepository.updateComment(authComment.getAuthor(), authComment.getId());

    // 更新缓存中的值
    redisTemplate.opsForValue().set(key(id), authComment);

    return authComment;
  }

  /**
   * 按id进行数据删除
   *
   * @param deleteId
   */
  public void deleteComment(Integer deleteId) {
    commentRepository.deleteById(deleteId);

    // 将缓存中的数据做删除操作
    redisTemplate.delete(key(deleteId));
  }
}

```



**2. 添加Controller**

```
package com.nullnull.learn.controller;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.service.CommentApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论的Controller
 *
 * @author liujun
 * @since 2023/3/26
 */
@RestController
@RequestMapping("/api")
public class CommentApiController {

  @Autowired private CommentApiService commentService;

  @RequestMapping(value = "/findCommentById")
  public Comment findCommentById(Integer id) {
    return commentService.findCommentById(id);
  }

  /**
   * 按id更新作者
   *
   * @param id id的信息
   * @param author 作者
   * @return
   */
  @RequestMapping(value = "/updateCommentById")
  public Comment updateComment(Integer id, String author) {
    return commentService.updateComment(author, id);
  }

  /**
   * 按id删除
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "/deleteById")
  public Comment deleteById(Integer id) {
    commentService.deleteComment(id);
    return commentService.findCommentById(id);
  }
}

```



**3. 测试放入缓存**

请求：http://127.0.0.1:8080/api/findCommentById?id=3

页面返回 ：

```
{"id":3,"content":"很详细","author":"eric","aid":1}
```



检查缓存中的数据

```
127.0.0.1:6379> get "\xac\xed\x00\x05t\x00\tcomment_3"
"\xac\xed\x00\x05sr\x00\x1dcom.nullnull.learn.po.Comment!PY\x8d\x10\xf6,\xc3\x02\x00\x04L\x00\x03aIdt\x00\x13Ljava/lang/Integer;L\x00\x06authort\x00\x12Ljava/lang/String;L\x00\acontentq\x00~\x00\x02L\x00\x02idq\x00~\x00\x01xpsr\x00\x11java.lang.Integer\x12\xe2\xa0\xa4\xf7\x81\x878\x02\x00\x01I\x00\x05valuexr\x00\x10java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp\x00\x00\x00\x01t\x00\x04erict\x00\t\xe5\xbe\x88\xe8\xaf\xa6\xe7\xbb\x86sq\x00~\x00\x04\x00\x00\x00\x03"
```



**4. 更新缓存中的数据**

请求地址： http://127.0.0.1:8080/api/updateCommentById?id=3&author=nullnull

页面响应：

```
{"id":3,"content":"很详细","author":"nullnull","aid":1}
```

由于缓存中都是二进制数据，不能直观的看出数据。因此引入了下一个问题，为Redis数据的存储，序列化为其他格式



## 7.6 自定义Redis缓存序列化机制1-redisTemplate

由于默认的存储数据，不便于直观的查看，因为需要对序列化进行自定义



**1. 查看RedisTemplate的部分源码**

```java
public class RedisTemplate<K, V> extends RedisAccessor implements RedisOperations<K, V>, BeanClassLoaderAware {

	/**
	 * 声明key、value的各种序列化方式及初始值
	 */
  @SuppressWarnings("rawtypes") private @Nullable RedisSerializer keySerializer = null;
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer valueSerializer = null;
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer hashKeySerializer = null;
	@SuppressWarnings("rawtypes") private @Nullable RedisSerializer hashValueSerializer = null;
	......	
	/**
	 * 默认序列化设置，设置为JDK序列化方式。
	 */
	@Override
	public void afterPropertiesSet() {

		super.afterPropertiesSet();
		boolean defaultUsed = false;
		//如果未设置序列化，则默认采用JdkSerializationRedisSerializer这种方式
		if (defaultSerializer == null) {
			defaultSerializer = new JdkSerializationRedisSerializer(
					classLoader != null ? classLoader : this.getClass().getClassLoader());
		}
		if (enableDefaultSerializer) {
			if (keySerializer == null) {
				keySerializer = defaultSerializer;
				defaultUsed = true;
			}
			if (valueSerializer == null) {
				valueSerializer = defaultSerializer;
				defaultUsed = true;
			}
			if (hashKeySerializer == null) {
				hashKeySerializer = defaultSerializer;
				defaultUsed = true;
			}
			if (hashValueSerializer == null) {
				hashValueSerializer = defaultSerializer;
				defaultUsed = true;
			}
		}

		if (enableDefaultSerializer && defaultUsed) {
			Assert.notNull(defaultSerializer, "default serializer null and not all serializers initialized");
		}
		if (scriptExecutor == null) {
			this.scriptExecutor = new DefaultScriptExecutor<>(this);
		}

		initialized = true;
	}
	......
}
```



>通过以上的源码，可以得到两个结论：
>
>1.  使用RedisTemplate进行redis数据缓存操作时，内部默认使用的是JdkSerializationRedisSerializer，所以实体需要实现JDK的序列化接口（Serializable）
>2.   使用RedisTemplate进行redis数据缓存操作时，如果自定义了缓存序列化方式，则使用自定义的缓存序列化方式。



那我们再看看RedisSerializer有哪些实现

```properties
ByteArrayRedisSerializer.java
GenericJackson2JsonRedisSerializer.java
GenericToStringSerializer.java
Jackson2JsonRedisSerializer.java
JdkSerializationRedisSerializer.java
OxmSerializer.java
StringRedisSerializer.java
```

通过这些代码，基本一眼就能知道能使用哪些方式了。



**2. SpringBoot缓存序列化加载**

```java
package org.springframework.boot.autoconfigure.data.redis;

import java.net.UnknownHostException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Redis support.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @author Christian Dupuis
 * @author Christoph Strobl
 * @author Phillip Webb
 * @author Eddú Meléndez
 * @author Stephane Nicoll
 * @author Marco Aust
 * @author Mark Paluch
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
public class RedisAutoConfiguration {

  /**
   * @ConditionalOnMissingBean(name = "redisTemplate") 当外部未配制redisTemplate这个对象时，则加此Bean对象方法
   * 也就是如果我们配制了redisTemplate这个Bean，这个配制将不会被加载。
   */
	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
......

}

```



**3. 自定义序列化配制**

```java
package com.nullnull.learn.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import java.net.UnknownHostException;

@Configuration
public class RedisConfiguration {

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);

    // 使用Json序列化的方式
    Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);

    // 解决查询缓存转换异常的问题
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

    jacksonSerializer.setObjectMapper(om);

    // 设置Redis模板的序列化方式为JSON
    template.setDefaultSerializer(jacksonSerializer);

    return template;
  }
}

```





**4. 测试**

http://127.0.0.1:8080/api/findCommentById?id=3

再次检查缓存中的数据

```
127.0.0.1:6379> get "\"comment_3\""
"[\"com.nullnull.learn.po.Comment\",{\"id\":3,\"content\":\"\xe5\xbe\x88\xe8\xaf\xa6\xe7\xbb\x86\",\"author\":\"nullnull\",\"aId\":1,\"aid\":1}]"
```

发现此时数据已经使用JSON进行了存储 。

使用API方式的RedisTemplate进行自定义序列化方式的改进，从而实现了JSON序列化方式缓存数据，但这种自定义的RedisTemplate对于基于注解的Redis缓存来说，是没有作用的



## 7.7 自定义Redis存序列化机制2-注解

Redis注解的序列化机制

打开SpringBoot整合Redis组件提供的缓存自动配置类。RedisCacheConfifiguration（org.springframework.boot.autoconfigure.cache），查看该类的源码.

```java
package org.springframework.boot.autoconfigure.cache;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * Redis cache configuration.
 *
 * @author Stephane Nicoll
 * @author Mark Paluch
 * @author Ryon Day
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnMissingBean(CacheManager.class)
@Conditional(CacheCondition.class)
class RedisCacheConfiguration {

	@Bean
	RedisCacheManager cacheManager(CacheProperties cacheProperties, CacheManagerCustomizers cacheManagerCustomizers,
			ObjectProvider<org.springframework.data.redis.cache.RedisCacheConfiguration> redisCacheConfiguration,
			ObjectProvider<RedisCacheManagerBuilderCustomizer> redisCacheManagerBuilderCustomizers,
			RedisConnectionFactory redisConnectionFactory, ResourceLoader resourceLoader) {
		RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
				determineConfiguration(cacheProperties, redisCacheConfiguration, resourceLoader.getClassLoader()));
		List<String> cacheNames = cacheProperties.getCacheNames();
		if (!cacheNames.isEmpty()) {
			builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
		}
		redisCacheManagerBuilderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
		return cacheManagerCustomizers.customize(builder.build());
	}

	private org.springframework.data.redis.cache.RedisCacheConfiguration determineConfiguration(
			CacheProperties cacheProperties,
			ObjectProvider<org.springframework.data.redis.cache.RedisCacheConfiguration> redisCacheConfiguration,
			ClassLoader classLoader) {
		return redisCacheConfiguration.getIfAvailable(() -> createConfiguration(cacheProperties, classLoader));
	}

	private org.springframework.data.redis.cache.RedisCacheConfiguration createConfiguration(
			CacheProperties cacheProperties, ClassLoader classLoader) {
		Redis redisProperties = cacheProperties.getRedis();
		org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
				.defaultCacheConfig();
		config = config.serializeValuesWith(
				SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
		if (redisProperties.getTimeToLive() != null) {
			config = config.entryTtl(redisProperties.getTimeToLive());
		}
		if (redisProperties.getKeyPrefix() != null) {
			config = config.prefixKeysWith(redisProperties.getKeyPrefix());
		}
		if (!redisProperties.isCacheNullValues()) {
			config = config.disableCachingNullValues();
		}
		if (!redisProperties.isUseKeyPrefix()) {
			config = config.disableKeyPrefix();
		}
		return config;
	}

}

```

从源码中可以看出，同RedisTemplate类似，RedisCacheConfiguration内部同样通过Redis连接工厂RedisConnectionFactory，同时定制了RedisCacheManager时，也同样使用了JdkSerializationRedisSerializer序列化方式。

若想要使用自定义序列化方式的RedisCacheManager进行数据缓存操作，可以参考上述核心代码，创建一个名为cacheManager的组件，并在组件中设置对应的序列化方式。

>在Spring2.x版本中，RedisCacheManager是单独构建的，因为在SpringBoot 2.X版本中，对RedisTemplate进行了自定义序列化机制后，仍无法对RedisCacheManager的内部默认序列化机制过时行覆盖（这也解释了为什么基于注解的Redis缓存仍然会使用JDK默认序列化机制的原因）。想要基于注解的Redis缓存实现也使用自定义序列化机制，需要自定义RedisCacheManager



**自定义RedisCacheManager**

```java
package com.nullnull.learn.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

/**
 * @author liujun
 * @since 2023/3/27
 */
@Configuration
public class RedisAnnotationConfiguration {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

    // 分别创建JSON和String序列化对象
    RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

    // 查询查询异常缓存转换问题
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    // om.activateDefaultTyping(PolymorphicTypeValidator.Validity.)
    jsonRedisSerializer.setObjectMapper(om);

    // 定制缓存序列化方式及时效
    RedisCacheConfiguration cacheManagerConfig =
        RedisCacheConfiguration.defaultCacheConfig()
            // 过期时间为1天
            .entryTtl(Duration.ofDays(1))
            // 序列化key
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
            // 序列化值
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
            .disableCachingNullValues();
    RedisCacheManager cacheManager =
        RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheManagerConfig).build();

    return cacheManager;
  }
}

```



**测试**

访问：http://127.0.0.1:8080/findCommentById?id=3

查看redis中的数据

```
127.0.0.1:6379> get "comment::3"
"[\"com.nullnull.learn.po.Comment\",{\"id\":3,\"content\":\"\xe5\xbe\x88\xe8\xaf\xa6\xe7\xbb\x86\",\"author\":\"nullnull\",\"aId\":1,\"aid\":1}]"
```

可以发现数据已经以JSON格式进行了存储。





# 8. SpringBoot内嵌的TOMCAT的加载

要了解SpringBoot加载TOMCAT，首先来看看MAVEN什么时候引入的TOMCAT吧.



## 8.1 Tomcat的引入

在SpringBoot的项目中，我们都会引入spring-boot-starter-web

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

跟踪下此引入看下，到底引入了哪些了？

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starters</artifactId>
    <version>2.2.2.RELEASE</version>
  </parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <version>2.2.2.RELEASE</version>
  <name>Spring Boot Web Starter</name>
 
  <dependencies>
    ......
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
      <version>2.2.2.RELEASE</version>
      <scope>compile</scope>
    </dependency>
    ......
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>5.2.2.RELEASE</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.2.2.RELEASE</version>
      <scope>compile</scope>
    </dependency>
  </dependencies>
</project>

```

通过此处我们就发现，在引入spring-boot-starter-web的时候，就引入了spring-boot-starter-tomcat，这样，我们的TOMCAT容器就被引入了SpringBoot了.







## 8.2 Tomcat的启动

打开spring-boot-autoconfigure-2.2.2.RELEASE.jar!\META-INF\spring.factories，找到如下的一行配制，

```properties
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
```

那为什么要找这行呢，因为Tomcat的相关依赖的加载，都在此类中去进行了，点击此类的源码，进行一下查看

```java
package org.springframework.boot.autoconfigure.web.servlet;

@Configuration(proxyBeanMethods = false)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(ServletRequest.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(ServerProperties.class)
//此处通过@Import注解将EmbeddedTomcat、EmbeddedJetty、EmbeddedUndertow等嵌入式容器类加载进来了，springboot默认是启动嵌入式tomcat容器
//如果要改变启动jetty或者Undertow容器，需要在pom.xml中去设置
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class,
		ServletWebServerFactoryConfiguration.EmbeddedTomcat.class,
		ServletWebServerFactoryConfiguration.EmbeddedJetty.class,
		ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })
public class ServletWebServerFactoryAutoConfiguration {
......
}

```



**3.点击ServletWebServerFactoryConfiguration.EmbeddedTomcat.class查看源码**

```java
package org.springframework.boot.autoconfigure.web.servlet;

@Configuration(proxyBeanMethods = false)
class ServletWebServerFactoryConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ Servlet.class, Tomcat.class, UpgradeProtocol.class })
	@ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
	static class EmbeddedTomcat {

		@Bean
		TomcatServletWebServerFactory tomcatServletWebServerFactory(
				ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers,
				ObjectProvider<TomcatContextCustomizer> contextCustomizers,
				ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
      //此处创建了一个TomcatServletWebServerFactory，这是一个Tomcat的工厂对象，它就是用来创建Tomcat
			TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
			factory.getTomcatConnectorCustomizers()
					.addAll(connectorCustomizers.orderedStream().collect(Collectors.toList()));
			factory.getTomcatContextCustomizers()
					.addAll(contextCustomizers.orderedStream().collect(Collectors.toList()));
			factory.getTomcatProtocolHandlerCustomizers()
					.addAll(protocolHandlerCustomizers.orderedStream().collect(Collectors.toList()));
			return factory;
		}

	}
......
}

```

此处创建了一个TomcatServletWebServerFactory对象，这一个名称就知道这是一个工厂对象，它就是用来创建Tomcat，那继续跟踪此源码进行查看



**4. TomcatServletWebServerFactory源码**

```java
package org.springframework.boot.web.embedded.tomcat;

public class TomcatServletWebServerFactory extends AbstractServletWebServerFactory
		implements ConfigurableTomcatWebServerFactory, ResourceLoaderAware {
......

	@Override
	public WebServer getWebServer(ServletContextInitializer... initializers) {
		if (this.disableMBeanRegistry) {
			Registry.disableRegistry();
		}
  	//创建tomcat对象
		Tomcat tomcat = new Tomcat();
  	//为Tomcat设置一些属性。
		File baseDir = (this.baseDirectory != null) ? this.baseDirectory : createTempDir("tomcat");
		tomcat.setBaseDir(baseDir.getAbsolutePath());
		Connector connector = new Connector(this.protocol);
		connector.setThrowOnFailure(true);
		tomcat.getService().addConnector(connector);
		customizeConnector(connector);
		tomcat.setConnector(connector);
		tomcat.getHost().setAutoDeploy(false);
		configureEngine(tomcat.getEngine());
		for (Connector additionalConnector : this.additionalTomcatConnectors) {
			tomcat.getService().addConnector(additionalConnector);
		}
		prepareContext(tomcat.getHost(), initializers);
  	//创建TomcatWebServer对象
		return getTomcatWebServer(tomcat);
	}
......
}

```

此类中有一个非常重要的方法getWebServer方法，记下此方法名，此就是用来创建Tomcat对象，调用了此方法，那内嵌的Tomcat对象就会被创建。既然知道了Tomcat是何时被创建的，那是何时被启动的呢？带着这个疑问，继续来看源码。跳过属性的一些设置，继续来查看getTomcatWebServer方法.

```
	protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
		return new TomcatWebServer(tomcat, getPort() >= 0);
	}
```

此处直接创建了一个TomcatWebServer对象，那我们继续来看下此源码

```java
package org.springframework.boot.web.embedded.tomcat;

public class TomcatWebServer implements WebServer {
......
	/**
	 * Create a new {@link TomcatWebServer} instance.
	 * @param tomcat the underlying Tomcat server
	 * @param autoStart if the server should be started
	 */
	public TomcatWebServer(Tomcat tomcat, boolean autoStart) {
		Assert.notNull(tomcat, "Tomcat Server must not be null");
		this.tomcat = tomcat;
		this.autoStart = autoStart;
  	//初始化方法
		initialize();
	}

	private void initialize() throws WebServerException {
		logger.info("Tomcat initialized with port(s): " + getPortsDescription(false));
		synchronized (this.monitor) {
			try {
				addInstanceIdToEngineName();

				Context context = findContext();
				context.addLifecycleListener((event) -> {
					if (context.equals(event.getSource()) && Lifecycle.START_EVENT.equals(event.getType())) {
						// Remove service connectors so that protocol binding doesn't
						// happen when the service is started.
						removeServiceConnectors();
					}
				});

				// Start the server to trigger initialization listeners
				this.tomcat.start();

				// We can re-throw failure exception directly in the main thread
				rethrowDeferredStartupExceptions();

				try {
					ContextBindings.bindClassLoader(context, context.getNamingToken(), getClass().getClassLoader());
				}
				catch (NamingException ex) {
					// Naming is not enabled. Continue
				}

				// Unlike Jetty, all Tomcat threads are daemon threads. We create a
				// blocking non-daemon to stop immediate shutdown
				startDaemonAwaitThread();
			}
			catch (Exception ex) {
				stopSilently();
				destroySilently();
				throw new WebServerException("Unable to start embedded Tomcat", ex);
			}
		}
	}
......

}
```

在创建的TomcatWebServer容器时，又调用了initialize方法，在initialize方法，我们就看到了非常关键的段代码this.tomcat.start();,此处就启动了Tomcat.



## 8.3 SpringBoot启动Tomcat的时机点

**1. SpringBoot的启动类**

```java
package com.nullnull.learn;


@SpringBootApplication
public class SpringBootCache {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootCache.class, args);
  }
}

```



**2. 跟踪run方法**

```java
public class SpringApplication {
......
	public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
		return run(new Class<?>[] { primarySource }, args);
	}
	public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
		return new SpringApplication(primarySources).run(args);
	}
}	
```



**3. SpringApplication的run方法**

```
public class SpringApplication {
......
	public ConfigurableApplicationContext run(String... args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments, printedBanner);
			//刷新容器的方法
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
	
	private void refreshContext(ConfigurableApplicationContext context) {
		//继续调用刷新方法
		refresh(context);
		if (this.registerShutdownHook) {
			try {
				context.registerShutdownHook();
			}
			catch (AccessControlException ex) {
				// Not allowed in some environments.
			}
		}
	}
	
	protected void refresh(ApplicationContext applicationContext) {
		Assert.isInstanceOf(AbstractApplicationContext.class, applicationContext);
		((AbstractApplicationContext) applicationContext).refresh();
	}
	
......
}
```



**4. 继续跟踪**

```java
public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {
......		
  @Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
        //跟踪此refresh方法
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
  
	protected void onRefresh() throws BeansException {
		// For subclasses: do nothing by default.
	}
......
}
```



经onRefresh需要在子类中扩展去实现，那我们继续观察下子类的实现

```tex
ReactiveWebServerApplicationContext(org.springframework.boot.web.reactive.context)
ServletWebServerApplicationContext(org.springframework.boot.web.servlet.context)
AbstractRefreshableWebApplicationContext(org.springframework.web.context.support)
GenericWebApplicationContext(org.springframework.web.context.support)
StaticWebApplicationContext(org.springframework.web.context.support)
```

此处我们是WEB容器，选择ServletWebServerApplicationContext继续查看



**5. 跟踪onRefresh方法**

```java
public class ServletWebServerApplicationContext extends GenericWebApplicationContext
		implements ConfigurableWebServerApplicationContext {
......
	@Override
	protected void onRefresh() {
		super.onRefresh();
		try {
			createWebServer();
		}
		catch (Throwable ex) {
			throw new ApplicationContextException("Unable to start web server", ex);
		}
	}
	private void createWebServer() {
		WebServer webServer = this.webServer;
		ServletContext servletContext = getServletContext();
		if (webServer == null && servletContext == null) {
			//获取ServletWebServerFactory对象，进行容器的创建操作
			ServletWebServerFactory factory = getWebServerFactory();
			this.webServer = factory.getWebServer(getSelfInitializer());
		}
		else if (servletContext != null) {
			try {
				getSelfInitializer().onStartup(servletContext);
			}
			catch (ServletException ex) {
				throw new ApplicationContextException("Cannot initialize servlet context", ex);
			}
		}
		initPropertySources();
	}
	......
}
```

**6. 查看容器创建接口**

```
@FunctionalInterface
public interface ServletWebServerFactory {

	/**
	 * Gets a new fully configured but paused {@link WebServer} instance. Clients should
	 * not be able to connect to the returned server until {@link WebServer#start()} is
	 * called (which happens when the {@code ApplicationContext} has been fully
	 * refreshed).
	 * @param initializers {@link ServletContextInitializer}s that should be applied as
	 * the server starts
	 * @return a fully configured and started {@link WebServer}
	 * @see WebServer#stop()
	 */
	WebServer getWebServer(ServletContextInitializer... initializers);

}
```

检查实现

```
JettyServletWebServerFactory(org.springframework.boot.web.embedded.jetty)
TomcatServletWebServerFactory(org.springframework.boot.web.embedded.tomcat)
UndertowServletWebServerFactory(org.springframework.boot.web.embedded.undertow)
```



**7. 跟踪TomcatServletWebServerFactory的getWebServer方法**

```java
public class TomcatServletWebServerFactory extends AbstractServletWebServerFactory
		implements ConfigurableTomcatWebServerFactory, ResourceLoaderAware {
......
  @Override
	public WebServer getWebServer(ServletContextInitializer... initializers) {
		if (this.disableMBeanRegistry) {
			Registry.disableRegistry();
		}
		Tomcat tomcat = new Tomcat();
		File baseDir = (this.baseDirectory != null) ? this.baseDirectory : createTempDir("tomcat");
		tomcat.setBaseDir(baseDir.getAbsolutePath());
		Connector connector = new Connector(this.protocol);
		connector.setThrowOnFailure(true);
		tomcat.getService().addConnector(connector);
		customizeConnector(connector);
		tomcat.setConnector(connector);
		tomcat.getHost().setAutoDeploy(false);
		configureEngine(tomcat.getEngine());
		for (Connector additionalConnector : this.additionalTomcatConnectors) {
			tomcat.getService().addConnector(additionalConnector);
		}
		prepareContext(tomcat.getHost(), initializers);
		return getTomcatWebServer(tomcat);
	}
......
}
```

看到此处是不是如此的熟悉。在Tomcat的启动那里，我们就是跟踪到此方法。







# 结束

