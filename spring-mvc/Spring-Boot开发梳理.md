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



