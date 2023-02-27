# Spring Data Jpa 开发梳理

## 1. Spring Data Jpa开发实现

>在正式的开发前。需要先梳理下一般的开发步骤。
>
>1. 创建工程，导入依赖（maven所依赖的jar包）
>2. 配制Spring的配制文件
>3. 编写实体类User，配合JPA的注解配制映射关系
>4. 编写一个符合Spring Data Jpa的Dao层的实现接口
>5. 操作UserDao接口完成DAO的开发。

**准备工作-数据库脚本**

```
DROP TABLE IF EXISTS `user_data_info`;
CREATE TABLE `user_data_info` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `address` varchar(255) DEFAULT NULL,
 `name` varchar(255) DEFAULT NULL,
 `phone` varchar(255) DEFAULT NULL,
 `age`  int(5) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `user_data_info` VALUES (1, '北京', '张三', '13012345678', 170);
INSERT INTO `user_data_info` VALUES (2, '上海', '李四', '13012345671', 160);
INSERT INTO `user_data_info` VALUES (3, '⼴州', '王五', '13012345672', 180);
INSERT INTO `user_data_info` VALUES (5, '上海', '张五', '13012345673', 180);


COMMIT;


```





### 1.1 创建工程导入依赖

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.nullnull.learn</groupId>
    <artifactId>spring-data-jpa-demo</artifactId>
    <version>1.0-SNAPSHOT</version>


    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <!--单元测试jar-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!--spring-data-jpa 需要引⼊的jar,start-->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>2.1.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>javax.el</groupId>
            <artifactId>javax.el-api</artifactId>
            <version>3.0.1-b04</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.el</artifactId>
            <version>2.2.6</version>
        </dependency>
        <!--spring-data-jpa 需要引⼊的jar,end-->
        <!--spring 相关jar,start-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <!--spring对orm框架的⽀持包-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.1.12.RELEASE</version>
        </dependency>
        <!--spring 相关jar,end-->
        <!--hibernate相关jar包,start-->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.4.0.Final</version>
        </dependency>
        <!--hibernate对jpa的实现jar-->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.4.0.Final</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>5.4.0.Final</version>
        </dependency>
        <!--hibernate相关jar包,end-->
        <!--mysql 数据库驱动jar-->
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
        <!--spring-test-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.12.RELEASE</version>
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



### 1.2 创建相关包目录结构

```
com.nullnull.learn.dao
com.nullnull.learn.pojo
```



### 1.3 配制Spring的Dao的配制文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
 http://www.springframework.org/schema/beans
 https://www.springframework.org/schema/beans/spring-beans.xsd
 http://www.springframework.org/schema/context
 https://www.springframework.org/schema/context/spring-context.xsd
 http://www.springframework.org/schema/data/jpa
 https://www.springframework.org/schema/data/jpa/spring-jpa.xsd
">
    <!--对Spring和SpringJpg进行配制-->

    <!--1. 创建数据库连接池-->
    <!--引入外部数据库配制文件-->
    <context:property-placeholder location="classpath*:jdbc.properties"/>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!--
       2. 配制一个JPA中非常重要的对象entityManagerFactory，
       entityManager类似于Mybatis中的SqlSession
       entityManagerFactory则类似于Mybatis中的SqlSessionFactory
    -->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <!--配制数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--配制包扫描,(pojo实体类所在的包)-->
        <property name="packagesToScan" value="com.nullnull.learn.pojo"/>
        <!--指定JPA的具体实现,也就是Hibernate-->
        <property name="persistenceProvider">
            <bean class="org.hibernate.jpa.HibernatePersistenceProvider"/>
        </property>
        <!--JPA的方言配制,不同的jpa实现对于类似于beginTransaction等细节实现起来不一样-->
        <property name="jpaDialect">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaDialect"/>
        </property>
        <!--配制具体的provider,hibernate框架的执行细节-->
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <!--定义hibernate框架的细节-->
                <!--
                配制表是否自动创建
                因为我们会建立pojo与数据表之间的映射关系
                程序启动时，如果数据表还没有创建，是否需要程序给创建一下
                -->
                <property name="generateDdl" value="false"/>
                <!--
                  指定数据库的类型
                  hibernate本身是个dao层的框架，可以支持多种数据库类型，这里指定本次使用什么数据库
                -->
                <property name="database" value="MYSQL"/>
                <!--
                配制数据库方言
                hibernate可以帮我们拼装SQL，但不同的数据库SQL语法是不同的，
                所以需要我们注入具体的数据库方式
                -->
                <property name="databasePlatform" value="org.hibernate.dialect.MySQLDialect"/>
                <!--
                是否显示SQL
                数据库操作时，是否打印SQL
                -->
                <property name="showSql" value="true"/>

            </bean>
        </property>
    </bean>

    <!--
    3, 引用上面的entityManagerFactory
    <jpa:repositories>配制Jpa的dao层的细节。
    base-package: 指定dao层接口所在的包。
    -->
    <jpa:repositories base-package="com.nullnull.learn.dao" entity-manager-factory-ref="entityManagerFactory"
                      transaction-manager-ref="transactionManager"></jpa:repositories>


    <!--
    4. 事务管理器配制
    jdbcTemplate/mybatis使用DataSourceTransactionManager
    jpa规范：JpaTransactionManager
    -->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory" />
    </bean>


    <!--
    5. 声明式事务
    此为演示项目，无需事务，可不配制
    <tx:annotation-driven/>
    -->


    <!--6. 配制Spring包扫描-->
    <context:component-scan base-package="com.nullnull.learn" />
</beans>
```

jdbc.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://127.0.0.1:3306/jpa?characterEncoding=utf8
jdbc.username=root
jdbc.password=123456
```



### 1.4 编写实体类。使用JPA注解配制映射关系

UserDataInfo.java

```java
package com.nullnull.learn.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息 在类中要使用注解建立实体类与数据表之间的映射关系以及属性和映射的关系
 *
 * <p>1. 实体类和映射表的关系 @Entity @Table
 *
 * <p>2. 实体类属性和表字段的映射关系 @Id 标识主键 @GeneratedValue 标识主键的生成策略 @Column 建立属性和字段映射
 *
 * @author nullnull
 * @since 2023/2/24
 */
@Entity
@Table(name = "user_data_info")
public class UserDataInfo {

  /**
   * 用户Id
   *
   * <p>在id的生成策略中经常使用两种：
   *
   * <p>1. GenerationType.IDENTITY 依赖数据库中主键自增功能。 MYSQL
   *
   * <p>2. GenerationType.SEQUENCE 依赖序列来产生主键 ORACLE
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /** 地址信息 */
  @Column(name = "address")
  private String address;

  /** 名称 */
  @Column(name = "name")
  private String name;

  /** 手机号 */
  @Column(name = "phone")
  private String phone;

  /** 年龄 */
  @Column(name = "age")
  private Integer age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserDataInfo{");
    sb.append("id=").append(id);
    sb.append(", address='").append(address).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append(", phone='").append(phone).append('\'');
    sb.append(", age=").append(age);
    sb.append('}');
    return sb.toString();
  }
}
```



### 1.5 编写UserDataInfoDAO接口

#### 1.5.1 基本的CRUD

UserDataInfoDao

```java
package com.nullnull.learn.dao;

import com.nullnull.learn.pojo.UserDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 一个符合SpringDataJpa要求的DAO层接口是需要继承JpaRepository和JpaSpecificationExecutor
 *
 * <p>JpaRepository<操作的实体类型,主键类型> 封装了基本的CRUD操作
 *
 * <p>JpaSpecificationExecutor<操作的实体类型> 封装了复杂的查询（分页、排序等）
 *
 * @author nullnull
 * @since 2023/2/24
 */
public interface UserDataInfoDao
    extends JpaRepository<UserDataInfo, Long>, JpaSpecificationExecutor<UserDataInfo> {}

```

单元测试

```java
package com.nullnull.learn.dao;

import com.nullnull.learn.pojo.UserDataInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * 接口的单元测试
 *
 * @author nullnull
 * @since 2023/2/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDataInfoDaoTest {

  /** 注入需要测试的对象 */
  @Autowired private UserDataInfoDao userDataInfoDao;

  /** 基础的增删改 */
  @Test
  public void crud() {
    UserDataInfo userAdd = new UserDataInfo();
    userAdd.setName("nullnull");
    userAdd.setAddress("上海");
    userAdd.setAge(28);
    userAdd.setPhone("13487654321");

    // 添加
    UserDataInfo saveRsp = userDataInfoDao.save(userAdd);
    Assert.assertNotNull(saveRsp.getId());

    // 修改
    saveRsp.setName("nullnull-new");
    saveRsp.setAge(29);
    UserDataInfo updateRsp = userDataInfoDao.save(saveRsp);
    Assert.assertNotNull(updateRsp.getId());

    //删除
    userDataInfoDao.delete(optId.get());
  }
}

```

控制台结果

```
Hibernate: insert into user_data_info (address, age, name, phone) values (?, ?, ?, ?)
Hibernate: select userdatain0_.id as id1_0_0_, userdatain0_.address as address2_0_0_, userdatain0_.age as age3_0_0_, userdatain0_.name as name4_0_0_, userdatain0_.phone as phone5_0_0_ from user_data_info userdatain0_ where userdatain0_.id=?
Hibernate: update user_data_info set address=?, age=?, name=?, phone=? where id=?
Hibernate: select userdatain0_.id as id1_0_0_, userdatain0_.address as address2_0_0_, userdatain0_.age as age3_0_0_, userdatain0_.name as name4_0_0_, userdatain0_.phone as phone5_0_0_ from user_data_info userdatain0_ where userdatain0_.id=?
Hibernate: select userdatain0_.id as id1_0_0_, userdatain0_.address as address2_0_0_, userdatain0_.age as age3_0_0_, userdatain0_.name as name4_0_0_, userdatain0_.phone as phone5_0_0_ from user_data_info userdatain0_ where userdatain0_.id=?
Hibernate: delete from user_data_info where id=?
```

通过观察发现基本的增删除改查功能已经OK。







#### 1.5.2 查询方式1：调用继承接口中的方法

```java
  @Test
  public void query1() {
    // 查询
    Optional<UserDataInfo> optId = userDataInfoDao.findById(1L);
    System.out.println(optId.get());
    Assert.assertNotNull(optId.get().getAddress());
  }
```

UserDataInfoDao（同上）

运行测试

控制台输出：

```
Hibernate: select userdatain0_.id as id1_0_0_, userdatain0_.address as address2_0_0_, userdatain0_.age as age3_0_0_, userdatain0_.name as name4_0_0_, userdatain0_.phone as phone5_0_0_ from user_data_info userdatain0_ where userdatain0_.id=?
UserDataInfo{id=1, address='北京', name='张三', phone='13012345678', age=170}

```

可以观察发现，已经成功运行。



#### 1.5.3 查询方式2：引入JPQL(jpa查询语言)

>jpql类似于sql，只不过SQL操作的是数据库表和字段，jpql操作的是对象和属性，比如from UserDataInfo where id=?1 

UserDataInfoDao

```sql
public interface UserDataInfoDao
    extends JpaRepository<UserDataInfo, Long>, JpaSpecificationExecutor<UserDataInfo> {

  /**
   * 使用Jqpl过时行查询
   *
   * @param name
   * @return
   */
  @Query("from UserDataInfo where  name like %?1%")
  List<UserDataInfo> queryJqpl(String name);
}

```

单元测试

```java
  /** 使用jqpl进行查询 */
  @Test
  public void queryJqpl() {
    List<UserDataInfo> name = userDataInfoDao.queryJqpl("张");
    System.out.println(name);
    Assert.assertNotNull(name.get(0).getAddress());
  }
```

查看控制台:

```
Hibernate: select userdatain0_.id as id1_0_, userdatain0_.address as address2_0_, userdatain0_.age as age3_0_, userdatain0_.name as name4_0_, userdatain0_.phone as phone5_0_ from user_data_info userdatain0_ where userdatain0_.name like ?
[UserDataInfo{id=1, address='北京', name='张三', phone='13012345678', age=170}, UserDataInfo{id=5, address='上海', name='张五', phone='13012345673', age=180}]

```



#### 1.5.4 查询方式3： 使用原生的SQL

UserDataInfoDao添加查询方法

```java
  /**
   * 使用原生的SQL
   *
   * @param name
   * @return
   */
  @Query(value = "select * from user_data_info where name like %?1%", nativeQuery = true)
  List<UserDataInfo> querySql(String name);
```

单元测试添加测试方法

```
  /** 使用原生的SQL进行查询 */
  @Test
  public void querySql() {
    List<UserDataInfo> name = userDataInfoDao.querySql("张");
    System.out.println(name);
    Assert.assertNotNull(name.get(0).getAddress());
  }
```



控制台输出

```
Hibernate: select * from user_data_info where name like ?
[UserDataInfo{id=1, address='北京', name='张三', phone='13012345678', age=170}, UserDataInfo{id=5, address='上海', name='张五', phone='13012345673', age=180}]
```



#### 1.5.5 查询方式4：接口中自定义方法

>可以在接口中自定义方法，而且不必引入SQL或者JPQL，这种查询叫做方法命名规则查询，也就是说，定义的接口，按照一定的规则形成的，那么框架就能够理解我们的意图。

UserDataInfoDao添加查询方法

```java
  /**
   * 方法命名规则查询
   *
   * <p>按照名称做like查询
   *
   * <p>方法以findBy开头
   *
   * <p>-属性名，首字母大写
   *
   * <p>- 查询方式（模糊查询，等价查询），如果不写查询方式，默认等价查询
   *
   * @param name
   * @return
   */
  List<UserDataInfo> findByNameLikeAndId(String name, Long id);
```

添加测试方法

```java
  @Test
  public void findByNameLikeAndId() {
    List<UserDataInfo> name = userDataInfoDao.findByNameLikeAndId("张%", 1L);
    System.out.println(name);
    Assert.assertNotNull(name.get(0).getAddress());
  }
```

控制台输出

```
Hibernate: select userdatain0_.id as id1_0_, userdatain0_.address as address2_0_, userdatain0_.age as age3_0_, userdatain0_.name as name4_0_, userdatain0_.phone as phone5_0_ from user_data_info userdatain0_ where (userdatain0_.name like ? escape ?) and userdatain0_.id=?
[UserDataInfo{id=1, address='北京', name='张三', phone='13012345678', age=170}]

```



#### 1.5.6 查询方式5：动态查询

>动态查询
>
>service传入DAO层的条件不确定，把service拿到条件封装成一个对象传递给dao层，这个对象叫做Specification(对一个条件的封装)
>
>//根据条件查询单个对象
>
>Optional<T> findOne(@Nullable Specification<T> var1);
>
>//根据条件查询所有
>
>List<T> findAll(@Nullable Specification<T> var1);
>
>//根据条件查询并分页
>
>Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
>
>//根据条件查询并进行排序
>
>List<T> findAll(@Nullable Specification<T> var1, Sort var2);
>
>//根据条件统计
>
>long count(@Nullable Specification<T> var1);
>
>//用来封装查询条件，
>
>// root 根属性，查询所需要的任何属性都可以从根据中获取
>
>// CriteriaQuery 自定义查询方式，一般用不上
>
>//CriteriaBuilder  查询构建器，封装很多查询条件
>
>interface Specification<T> toPredicate(Root<T> var1, CriteriaQuery<?> var2,CriteriaBuilder var3);



Dao无需添加方法

单元测试

```java
/** 测试动态查询 */
@Test
public void testSpecfication() {
  /*
   * 匿名内部类进行封装查询
   * 借助于两个参数，完成条件的封装
   */
  Specification<UserDataInfo> specification =
      new Specification<UserDataInfo>() {
        @Override
        public Predicate toPredicate(
            Root<UserDataInfo> root,
            CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {

          // 使用CriteriaBuilder构造查询条件
          Predicate query = criteriaBuilder.equal(root.get("name"), "张三");

          Predicate query2 = criteriaBuilder.equal(root.get("id"), 1L);

          return criteriaBuilder.and(query, query2);
        }
      };

  Optional<UserDataInfo> resultData = userDataInfoDao.findOne(specification);
  System.out.println(resultData.get());
  Assert.assertNotNull(resultData.get().getAddress());
}
```

检查控制台输出

```
Hibernate: select userdatain0_.id as id1_0_, userdatain0_.address as address2_0_, userdatain0_.age as age3_0_, userdatain0_.name as name4_0_, userdatain0_.phone as phone5_0_ from user_data_info userdatain0_ where userdatain0_.name=? and userdatain0_.id=1
UserDataInfo{id=1, address='北京', name='张三', phone='13012345678', age=170}
```



进行like查询 

```java
  /** 测试动态查询 */
  @Test
  public void testSpecficationLike() {

    Specification<UserDataInfo> specification =
        new Specification<UserDataInfo>() {
          @Override
          public Predicate toPredicate(
              Root<UserDataInfo> root,
              CriteriaQuery<?> criteriaQuery,
              CriteriaBuilder criteriaBuilder) {

            Path<Object> dataName = root.get("name");

            // 使用CriteriaBuilder构造查询条件
            Predicate query = criteriaBuilder.like(dataName.as(String.class), "张%");

            return criteriaBuilder.and(query);
          }
        };

    List<UserDataInfo> resultData = userDataInfoDao.findAll(specification);
    System.out.println(resultData);
    Assert.assertNotNull(resultData.get(0).getAddress());
  }
```

控制台输出

```
Hibernate: select userdatain0_.id as id1_0_, userdatain0_.address as address2_0_, userdatain0_.age as age3_0_, userdatain0_.name as name4_0_, userdatain0_.phone as phone5_0_ from user_data_info userdatain0_ where userdatain0_.name like ?
[UserDataInfo{id=1, address='北京', name='张三', phone='13012345678', age=170}, UserDataInfo{id=5, address='上海', name='张五', phone='13012345673', age=180}]
```

#### 1.5.7 查询条件带排序

DAO无需添加方法

单元测试方法

```java
  /** 带条件的排序查询 */
  @Test
  public void findAndSort() {
    Sort sort = new Sort(Sort.Direction.DESC, "id");

    UserDataInfo optionUser = new UserDataInfo();
    optionUser.setAge(180);

    Example<UserDataInfo> example = Example.of(optionUser);

    List<UserDataInfo> dataList = userDataInfoDao.findAll(example, sort);

    for (int i = 0; i < dataList.size(); i++) {
      UserDataInfo o = dataList.get(i);
      System.out.println(o);
    }

    Assert.assertNotEquals(dataList.size(), 0);
  }
```



控制台输出

```
Hibernate: select userdatain0_.id as id1_0_, userdatain0_.address as address2_0_, userdatain0_.age as age3_0_, userdatain0_.name as name4_0_, userdatain0_.phone as phone5_0_ from user_data_info userdatain0_ where userdatain0_.age=180 order by userdatain0_.id desc
UserDataInfo{id=5, address='上海', name='张五', phone='13012345673', age=180}
UserDataInfo{id=3, address='⼴州', name='王五', phone='13012345672', age=180}

```



#### 1.5.8 分页查询

DAO方法无需添加



添加单元测试

```java
  /** 分页查询*/
  @Test
  public void page() {
    // 第一个参数，当前开始的页，从0开始
    // 第二个参数，每页显示的数量
    Pageable page = PageRequest.of(1, 2);

    Page<UserDataInfo> dataList = userDataInfoDao.findAll(page);

    for (int i = 0; i < dataList.getContent().size(); i++) {
      UserDataInfo o = dataList.getContent().get(i);
      System.out.println(o);
    }

    Assert.assertNotEquals(dataList.getTotalElements(), 0);
  }
```

控制台输出

```
Hibernate: select userdatain0_.id as id1_0_, userdatain0_.address as address2_0_, userdatain0_.age as age3_0_, userdatain0_.name as name4_0_, userdatain0_.phone as phone5_0_ from user_data_info userdatain0_ limit ?, ?
Hibernate: select count(userdatain0_.id) as col_0_0_ from user_data_info userdatain0_
UserDataInfo{id=3, address='⼴州', name='王五', phone='13012345672', age=180}
UserDataInfo{id=5, address='上海', name='张五', phone='13012345673', age=180}
```



## 2. Spring Data JPA 源码分析

>代理对象产生过程追源码很难追，特别特别讲究技巧。
>
>分析解读一下，主要的代理对象产生的过程。
>
>以之单元测试代码作为源码分析的工程。



### 2.1 运行使用继承继承的方法（query1）

![image-20230226092507671](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226092507671.png)

通过初步的一个观察我们发现此userDataInfoDao对象是一个org.springframework.data.jpa.repository.support.SimpleJpaRepository对象。而这个对象是由JdkDynamicAopProxy这个对象产生的，那也就是说这个UserDataInfoDao注入的是一个代理对象，代理对象类型是SimpleJpaRepository。

此时产生了两个疑问？

>1. 为什么会给它指定为一个JpaRepositoryFactoryBean,(getObject方法返回具体的对象）
>
>2. 指定这个FactoryBean是在什么时候发生的？
>
>3. 这个代理对象是怎么产生的，过程怎样？
>
>4. 这个代理对象有什么特别的？进入到该类型的源码去看
>
>   

### 2.2 问题2：指定这个FactoryBean是在什么时候发生的？

以往：如果要给一个对象产生代理对象，我们知道是在AbstractApplicationContext的方法，断点在refreshfinishBeanFactoryInitialization(beanFactory);进行验证。分析过Spring源码就可以知道在这个方法做了什么。

#### 2.2.1 起点AbstractApplicationContext的refresh方法

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
......
}
  
```

那我们就在此此方法打上断点，看能否进入，此为猜测方法，通过debug来进行验证猜想。

断点果然进入，那我们继续跟进

```java
public abstract class AbstractApplicationContext extends DefaultResourceLoader
      implements ConfigurableApplicationContext {
......  
  /**
	 * Finish the initialization of this context's bean factory,
	 * initializing all remaining singleton beans.
	 */
	protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
		// Initialize conversion service for this context.
		if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&
				beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
			beanFactory.setConversionService(
					beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
		}

		// Register a default embedded value resolver if no bean post-processor
		// (such as a PropertyPlaceholderConfigurer bean) registered any before:
		// at this point, primarily for resolution in annotation attribute values.
		if (!beanFactory.hasEmbeddedValueResolver()) {
			beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
		}

		// Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
		String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
		for (String weaverAwareName : weaverAwareNames) {
			getBean(weaverAwareName);
		}

		// Stop using the temporary ClassLoader for type matching.
		beanFactory.setTempClassLoader(null);

		// Allow for caching all bean definition metadata, not expecting further changes.
		beanFactory.freezeConfiguration();

		// Instantiate all remaining (non-lazy-init) singletons.
		beanFactory.preInstantiateSingletons();
	}
......
}
```

略过中间一些步骤，进入beanFactory.preInstantiateSingletons();

#### 2.2.2 进入preInstantiateSingletons()方法

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
......
	@Override
	public void preInstantiateSingletons() throws BeansException {
		if (logger.isTraceEnabled()) {
			logger.trace("Pre-instantiating singletons in " + this);
		}

		// Iterate over a copy to allow for init methods which in turn register new bean definitions.
		// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);

		// Trigger initialization of all non-lazy singleton beans...
		for (String beanName : beanNames) {
			RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
			if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
				if (isFactoryBean(beanName)) {
					Object bean = getBean(FACTORY_BEAN_PREFIX + beanName);
					if (bean instanceof FactoryBean) {
						final FactoryBean<?> factory = (FactoryBean<?>) bean;
						boolean isEagerInit;
						if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
							isEagerInit = AccessController.doPrivileged((PrivilegedAction<Boolean>)
											((SmartFactoryBean<?>) factory)::isEagerInit,
									getAccessControlContext());
						}
						else {
							isEagerInit = (factory instanceof SmartFactoryBean &&
									((SmartFactoryBean<?>) factory).isEagerInit());
						}
						if (isEagerInit) {
							getBean(beanName);
						}
					}
				}
				else {
					getBean(beanName);
				}
			}
		}

		// Trigger post-initialization callback for all applicable beans...
		for (String beanName : beanNames) {
			Object singletonInstance = getSingleton(beanName);
			if (singletonInstance instanceof SmartInitializingSingleton) {
				final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
				if (System.getSecurityManager() != null) {
					AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
						smartSingleton.afterSingletonsInstantiated();
						return null;
					}, getAccessControlContext());
				}
				else {
					smartSingleton.afterSingletonsInstantiated();
				}
			}
		}
	}
......
}
```



![image-20230226095252903](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226095252903.png)



通过观察这个beanNames对象我们可以发现，存在我们需要注入的一个对象userDataInfoDao

通过IDEA给我们debug我们可以在断点上右键，添加断点的条件，beanName.equals("userDataInfoDao")，我们只关心这个对象。

![image-20230226095123902](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226095123902.png)



继续跟踪代码，



![image-20230226140219861](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226140219861.png)



进入了名字为“userDataInfoDao”的对象时。我们发现这个userDataInfoDao是一个FactoryBean,既然是而且这个对象是org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean，而且作为一个FactoryBean，Spring容器肯定会调用该容器的getObject方法，来获取对象。

看到此处，便出来一些疑问？

1. 为什么会给它指定为一个FactoryBean（getObject方法返回具体的对象）?
2. 指定为的这个FactoryBean是在什么时候发生的？



带着这些疑问，我们继续来检查源码，发现在RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);代码结果中获取了一个RootBeanDefinition ，发现此Bean已经被指定了一个FactoryBean,那就继续跟踪getMergedLocalBeanDefinition，看看此拿取对象的方法在干啥？



#### 2.2.3 进入getMergedLocalBeanDefinition获取定义的信息

>由于此处传入了一个userDataInfoDao就返回了一个已经指定class为JpaRepositoryFactoryBean的BeanDefinition对象了。那应该在上图中的get的时候就有了，所以进入了getMergedLocalBeanDefinition方法中。
>
>

那我们在RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);这行打上断点，并添加断点条件，并去掉其他断点

```
beanName.equals("userDataInfoDao")
```

![image-20230226141234454](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226141234454.png)



重新debug单元测试，进入getMergedLocalBeanDefinition方法。

```java
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
......
	/** Map from bean name to merged RootBeanDefinition. */
	private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);


  /**
	 * Return a merged RootBeanDefinition, traversing the parent bean definition
	 * if the specified bean corresponds to a child bean definition.
	 * @param beanName the name of the bean to retrieve the merged definition for
	 * @return a (potentially merged) RootBeanDefinition for the given bean
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @throws BeanDefinitionStoreException in case of an invalid bean definition
	 */
	protected RootBeanDefinition getMergedLocalBeanDefinition(String beanName) throws BeansException {
		// Quick check on the concurrent map first, with minimal locking.
		RootBeanDefinition mbd = this.mergedBeanDefinitions.get(beanName);
		if (mbd != null) {
			return mbd;
		}
		return getMergedBeanDefinition(beanName, getBeanDefinition(beanName));
	}
......
}
```

当我们过进入此方法调试时，发现对象是从一个mergedBeanDefinitions的Map中，获取到的对象，并且此处已经获取到了，就此返回

![image-20230226141924106](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226141924106.png)







#### 2.2.4 定义的userDataInfoDao放入mergedBeanDefinitions跟踪

>小问题又来了。啥时候put到Map中去的？

那此处既然有get,就会有put方法，我们在此代码中去搜索下，看看能不能找到put的方法。通过搜索还真找到了。内容如下：

```java
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
......
  /**
	 * Return a RootBeanDefinition for the given bean, by merging with the
	 * parent if the given bean's definition is a child bean definition.
	 * @param beanName the name of the bean definition
	 * @param bd the original bean definition (Root/ChildBeanDefinition)
	 * @param containingBd the containing bean definition in case of inner bean,
	 * or {@code null} in case of a top-level bean
	 * @return a (potentially merged) RootBeanDefinition for the given bean
	 * @throws BeanDefinitionStoreException in case of an invalid bean definition
	 */
	protected RootBeanDefinition getMergedBeanDefinition(
			String beanName, BeanDefinition bd, @Nullable BeanDefinition containingBd)
			throws BeanDefinitionStoreException {

		synchronized (this.mergedBeanDefinitions) {
			RootBeanDefinition mbd = null;

			// Check with full lock now in order to enforce the same merged instance.
			if (containingBd == null) {
				mbd = this.mergedBeanDefinitions.get(beanName);
			}

			if (mbd == null) {
				if (bd.getParentName() == null) {
					// Use copy of given root bean definition.
					if (bd instanceof RootBeanDefinition) {
						mbd = ((RootBeanDefinition) bd).cloneBeanDefinition();
					}
					else {
						mbd = new RootBeanDefinition(bd);
					}
				}
				else {
					// Child bean definition: needs to be merged with parent.
					BeanDefinition pbd;
					try {
						String parentBeanName = transformedBeanName(bd.getParentName());
						if (!beanName.equals(parentBeanName)) {
							pbd = getMergedBeanDefinition(parentBeanName);
						}
						else {
							BeanFactory parent = getParentBeanFactory();
							if (parent instanceof ConfigurableBeanFactory) {
								pbd = ((ConfigurableBeanFactory) parent).getMergedBeanDefinition(parentBeanName);
							}
							else {
								throw new NoSuchBeanDefinitionException(parentBeanName,
										"Parent name '" + parentBeanName + "' is equal to bean name '" + beanName +
										"': cannot be resolved without an AbstractBeanFactory parent");
							}
						}
					}
					catch (NoSuchBeanDefinitionException ex) {
						throw new BeanDefinitionStoreException(bd.getResourceDescription(), beanName,
								"Could not resolve parent bean definition '" + bd.getParentName() + "'", ex);
					}
					// Deep copy with overridden values.
					mbd = new RootBeanDefinition(pbd);
					mbd.overrideFrom(bd);
				}

				// Set default singleton scope, if not configured before.
				if (!StringUtils.hasLength(mbd.getScope())) {
					mbd.setScope(RootBeanDefinition.SCOPE_SINGLETON);
				}

				// A bean contained in a non-singleton bean cannot be a singleton itself.
				// Let's correct this on the fly here, since this might be the result of
				// parent-child merging for the outer bean, in which case the original inner bean
				// definition will not have inherited the merged outer bean's singleton status.
				if (containingBd != null && !containingBd.isSingleton() && mbd.isSingleton()) {
					mbd.setScope(containingBd.getScope());
				}

				// Cache the merged bean definition for the time being
				// (it might still get re-merged later on in order to pick up metadata changes)
				if (containingBd == null && isCacheBeanMetadata()) {
					this.mergedBeanDefinitions.put(beanName, mbd);
				}
			}

			return mbd;
		}
	}
......
}
```

此时，断点一下getMergedBeanDefinition方法，检查调用栈。

![image-20230226142905044](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226142905044.png)



当进入断点后

![image-20230226143017347](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226143017347.png)

发现，在调用此方法时。BeanDefinition已经被指定了，而且class是[org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean];此时返回跟踪调用栈，就可以看到

调用栈上一个方法

![image-20230226143413770](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226143413770.png)

继续反向跟踪调用栈。

![image-20230226143513510](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226143513510.png)

此处便可发现，传入了名称为userDataInfoDao，通过getBeanDefinition方法获取到了一个BeanDefinition,该方法中的class是被指定为FactoryBean了，此时便跟踪getBeanDefinition方法，但此方法，我们发现getBeanDefinition方法是一个抽象方法。在此处放掉其他的断点，打上断点，然后进行进行观察。

```
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
......
	/**
	 * Return the bean definition for the given bean name.
	 * Subclasses should normally implement caching, as this method is invoked
	 * by this class every time bean definition metadata is needed.
	 * <p>Depending on the nature of the concrete bean factory implementation,
	 * this operation might be expensive (for example, because of directory lookups
	 * in external registries). However, for listable bean factories, this usually
	 * just amounts to a local hash lookup: The operation is therefore part of the
	 * public interface there. The same implementation can serve for both this
	 * template method and the public interface method in that case.
	 * @param beanName the name of the bean to find a definition for
	 * @return the BeanDefinition for this prototype name (never {@code null})
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 * if the bean definition cannot be resolved
	 * @throws BeansException in case of errors
	 * @see RootBeanDefinition
	 * @see ChildBeanDefinition
	 * @see org.springframework.beans.factory.config.ConfigurableListableBeanFactory#getBeanDefinition
	 */
	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
```



![image-20230226144616371](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226144616371.png)

而且通过观察此处对象已经被获取到了,还是按刚刚分析mergedBeanDefinitions的方法，检查放入的位置。



#### 2.2.5 registerBeanDefinition方法跟进

通过搜索，我们发现此代码共同指向了一个方法registerBeanDefinition方法。

```
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
		implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {
......
  @Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			throws BeanDefinitionStoreException {

		Assert.hasText(beanName, "Bean name must not be empty");
		Assert.notNull(beanDefinition, "BeanDefinition must not be null");

		if (beanDefinition instanceof AbstractBeanDefinition) {
			try {
				((AbstractBeanDefinition) beanDefinition).validate();
			}
			catch (BeanDefinitionValidationException ex) {
				throw new BeanDefinitionStoreException(beanDefinition.getResourceDescription(), beanName,
						"Validation of bean definition failed", ex);
			}
		}

		BeanDefinition existingDefinition = this.beanDefinitionMap.get(beanName);
		if (existingDefinition != null) {
			if (!isAllowBeanDefinitionOverriding()) {
				throw new BeanDefinitionOverrideException(beanName, beanDefinition, existingDefinition);
			}
			else if (existingDefinition.getRole() < beanDefinition.getRole()) {
				// e.g. was ROLE_APPLICATION, now overriding with ROLE_SUPPORT or ROLE_INFRASTRUCTURE
				if (logger.isInfoEnabled()) {
					logger.info("Overriding user-defined bean definition for bean '" + beanName +
							"' with a framework-generated bean definition: replacing [" +
							existingDefinition + "] with [" + beanDefinition + "]");
				}
			}
			else if (!beanDefinition.equals(existingDefinition)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Overriding bean definition for bean '" + beanName +
							"' with a different definition: replacing [" + existingDefinition +
							"] with [" + beanDefinition + "]");
				}
			}
			else {
				if (logger.isTraceEnabled()) {
					logger.trace("Overriding bean definition for bean '" + beanName +
							"' with an equivalent definition: replacing [" + existingDefinition +
							"] with [" + beanDefinition + "]");
				}
			}
			this.beanDefinitionMap.put(beanName, beanDefinition);
		}
		else {
			if (hasBeanCreationStarted()) {
				// Cannot modify startup-time collection elements anymore (for stable iteration)
				synchronized (this.beanDefinitionMap) {
					this.beanDefinitionMap.put(beanName, beanDefinition);
					List<String> updatedDefinitions = new ArrayList<>(this.beanDefinitionNames.size() + 1);
					updatedDefinitions.addAll(this.beanDefinitionNames);
					updatedDefinitions.add(beanName);
					this.beanDefinitionNames = updatedDefinitions;
					removeManualSingletonName(beanName);
				}
			}
			else {
				// Still in startup registration phase
				this.beanDefinitionMap.put(beanName, beanDefinition);
				this.beanDefinitionNames.add(beanName);
				removeManualSingletonName(beanName);
			}
			this.frozenBeanDefinitionNames = null;
		}

		if (existingDefinition != null || containsSingleton(beanName)) {
			resetBeanDefinition(beanName);
		}
	}
......
}
```

那继续调试，在registerBeanDefinition开始处打上断点。放掉其他的断点。

![image-20230226145330116](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226145330116.png)

当断点进入后，便可发现

![image-20230226145908788](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226145908788.png)

此对象在传递时，已经被指定为了一个org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean对象，反向跟踪调用栈。

![image-20230226150517669](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226150517669.png)

此处这是一个自定义标签的解析器。

那我们再看看applicationContext.xml文件

```xml
    <!--
    3, 引用上面的entityManagerFactory
    <jpa:repositories>配制Jpa的dao层的细节。
    base-package: 指定dao层接口所在的包。
    -->
    <jpa:repositories base-package="com.nullnull.learn.dao" entity-manager-factory-ref="entityManagerFactory"
                      transaction-manager-ref="transactionManager"></jpa:repositories>
```

此jpa:repositories正是一个自定义标签。那再看看此标签的解析过程

![image-20230226150839218](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226150839218.png)

继续查看调用栈。

![image-20230226151104493](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226151104493.png)

跟踪调用栈的时候发现，此已经被指定为org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean，再观察此对象是如何产生的。

![image-20230226151451560](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226151451560.png)

那我们放过其他的断点，重要追踪这个构建。

![image-20230226151903150](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226151903150.png)

此时发现，当执行完BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(configuration.getRepositoryFactoryBeanClassName());后，就已经构建了org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean，那我们将重点跟踪rootBeanDefinition方法，放过其他的断点，我们继续。

进入getRepositoryFactoryBeanClassName方法

![image-20230226152152572](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226152152572.png)

继续进入getRepositoryFactoryBeanClassName方法，便可发现

![image-20230226152235967](D:\work\myself\learn\learn-md\spring-mvc\img\jpaimage-20230226152235967.png)

此处被固定了一个JpaRepositoryFactoryBean的对象。

后面继继续执行，将被设置为一个JpaRepositoryFactoryBean的对象。

通过上述追踪，我们发现。<jpa:repositories basePackage扫描到的接口，在进行BeanDefintion注册时候，Class会被固定的指定为JpaRepositoryFactoryBean

至此我们便可以回答：

>指定这个FactoryBean是在什么时候发生的？
>
>答：此在进行初始化创建这个BeanDefintion的时候就已经被固定的指定为JpaRepositoryFactoryBean对象了。



### 2.3 问题1：这个代理对象是怎么产生，过程怎样？

由于JpaRepositoryFactoryBean是一个工厂Bean对象，作为一个FactoryBean的bean，我们最关心的是getObject的返回。那就继续跟踪getObject方法。

#### 2.3.1 JpaRepositoryFactoryBean的getObject方法返回具体的对象

 这里使用ctrl+F12快捷键，输入getObj进行快速的查找，



![image-20230226153808658](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230226153808658.png)



通过结果来看，此getObject方法，并不在JpaRepositoryFactoryBean类中，而在父类的父类中，找到了getObject方法

```java
public abstract class RepositoryFactoryBeanSupport<T extends Repository<S, ID>, S, ID>
		implements InitializingBean, RepositoryFactoryInformation<S, ID>, FactoryBean<T>, BeanClassLoaderAware,
		BeanFactoryAware, ApplicationEventPublisherAware {
......
  private Lazy<T> repository;
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Nonnull
	public T getObject() {
		return this.repository.get();
	}
......
}
```

#### 2.3.2 追踪对象被放入

通过此代码，发现是通过Lazy这个对象进行获取的，那自然我们就要去找找何时被放入了？

```java
public abstract class RepositoryFactoryBeanSupport<T extends Repository<S, ID>, S, ID>
		implements InitializingBean, RepositoryFactoryInformation<S, ID>, FactoryBean<T>, BeanClassLoaderAware,
		BeanFactoryAware, ApplicationEventPublisherAware {
......

  /*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		this.factory = createRepositoryFactory();
		this.factory.setQueryLookupStrategyKey(queryLookupStrategyKey);
		this.factory.setNamedQueries(namedQueries);
		this.factory.setEvaluationContextProvider(
				evaluationContextProvider.orElseGet(() -> QueryMethodEvaluationContextProvider.DEFAULT));
		this.factory.setBeanClassLoader(classLoader);
		this.factory.setBeanFactory(beanFactory);

		if (publisher != null) {
			this.factory.addRepositoryProxyPostProcessor(new EventPublishingRepositoryProxyPostProcessor(publisher));
		}

		repositoryBaseClass.ifPresent(this.factory::setRepositoryBaseClass);

		RepositoryFragments customImplementationFragment = customImplementation //
				.map(RepositoryFragments::just) //
				.orElseGet(RepositoryFragments::empty);

		RepositoryFragments repositoryFragmentsToUse = this.repositoryFragments //
				.orElseGet(RepositoryFragments::empty) //
				.append(customImplementationFragment);

		this.repositoryMetadata = this.factory.getRepositoryMetadata(repositoryInterface);

		// Make sure the aggregate root type is present in the MappingContext (e.g. for auditing)
		this.mappingContext.ifPresent(it -> it.getPersistentEntity(repositoryMetadata.getDomainType()));

		this.repository = Lazy.of(() -> this.factory.getRepository(repositoryInterface, repositoryFragmentsToUse));

		if (!lazyInit) {
			this.repository.get();
		}
	}
......
}
```

通过观察我们观察代码可得知，此类实现了InitializingBean接口，那就必然需要实现afterPropertiesSet方法。

```java
		this.repository = Lazy.of(() -> this.factory.getRepository(repositoryInterface, repositoryFragmentsToUse));
```

在JpaRepositoryFactoryBean的父类的父类中初始化方法对repository变量赋值。那就放掉其他断点，将断点放置在此，重启开始调试进入。



#### 2.3.3 追踪代理对象创建getRepository（）方法。

跟踪getRepository方法过进入

```java
@Slf4j
public abstract class RepositoryFactorySupport implements BeanClassLoaderAware, BeanFactoryAware {
......
	/**
	 * Returns a repository instance for the given interface backed by an instance providing implementation logic for
	 * custom logic.
	 *
	 * @param repositoryInterface must not be {@literal null}.
	 * @param fragments must not be {@literal null}.
	 * @return
	 * @since 2.0
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Initializing repository instance for {}…", repositoryInterface.getName());
		}

		Assert.notNull(repositoryInterface, "Repository interface must not be null!");
		Assert.notNull(fragments, "RepositoryFragments must not be null!");

		RepositoryMetadata metadata = getRepositoryMetadata(repositoryInterface);
		RepositoryComposition composition = getRepositoryComposition(metadata, fragments);
		RepositoryInformation information = getRepositoryInformation(metadata, composition);

		validate(information, composition);

		Object target = getTargetRepository(information);

		// Create proxy
		ProxyFactory result = new ProxyFactory();
		result.setTarget(target);
		result.setInterfaces(repositoryInterface, Repository.class, TransactionalProxy.class);

		if (MethodInvocationValidator.supports(repositoryInterface)) {
			result.addAdvice(new MethodInvocationValidator());
		}

		result.addAdvice(SurroundingTransactionDetectorMethodInterceptor.INSTANCE);
		result.addAdvisor(ExposeInvocationInterceptor.ADVISOR);

		postProcessors.forEach(processor -> processor.postProcess(result, information));

		result.addAdvice(new DefaultMethodInvokingMethodInterceptor());

		ProjectionFactory projectionFactory = getProjectionFactory(classLoader, beanFactory);
		result.addAdvice(new QueryExecutorMethodInterceptor(information, projectionFactory));

		composition = composition.append(RepositoryFragment.implemented(target));
		result.addAdvice(new ImplementationMethodExecutionInterceptor(composition));

		T repository = (T) result.getProxy(classLoader);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Finished creation of repository instance for {}.", repositoryInterface.getName());
		}

		return repository;
	}
......
}
```

当我们查看RepositoryMetadata对象时，发现此对象进行了汇总

![image-20230227083749478](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227083749478.png)

继续调试，当我们代码经过了

```java
RepositoryInformation information = getRepositoryInformation(metadata, composition);
```

后

![image-20230227084134526](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227084134526.png)

此代理对象便产生了。

那要查看如何产生的这个SimpleJpaRepository对象，我们便需要在RepositoryInformation information = getRepositoryInformation(metadata, composition);这行打上断点，放过其他断点。重新启动测试。

#### 2.3.4 追踪代理对象获取

那我们继续进入

![image-20230227084659959](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227084659959.png)

经过观察可以发现，当执行过

```java
Class<?> baseClass = repositoryBaseClass.orElse(getRepositoryBaseClass(metadata));
```

便返回SimpleJpaRepository的类型，那我们就在这行打上断点，重新进入调试。

进入getRepositoryBaseClass方法。

```
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleJpaRepository.class;
    }
```

观察代码发现，原来此处代理类型固定的为SimpleJpaRepository



继续回到我们的getRepository()

![image-20230227093338486](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227093338486.png)



#### 2.3.5 代理对象追踪

通过此处发现，将创建一个代理对象工厂。借助代理对象工厂，创建一个代理对象。

![image-20230227093602562](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227093602562.png)

跳过中间的一些步骤，主要是做一些增强，然后我们过时入创建对象的getProxy方法

```java
public class ProxyFactory extends ProxyCreatorSupport {
......
    public Object getProxy(@Nullable ClassLoader classLoader) {
        return this.createAopProxy().getProxy(classLoader);
    }
......
}
```

继续跟踪createAopProxy进入

```
public class ProxyCreatorSupport extends AdvisedSupport {
......
    protected final synchronized AopProxy createAopProxy() {
        if (!this.active) {
            this.activate();
        }

        return this.getAopProxyFactory().createAopProxy(this);
    }
......
}
```

再次过入

```java
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
......
    public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
        if (!config.isOptimize() && !config.isProxyTargetClass() && !this.hasNoUserSuppliedProxyInterfaces(config)) {
            return new JdkDynamicAopProxy(config);
        } else {
            Class<?> targetClass = config.getTargetClass();
            if (targetClass == null) {
                throw new AopConfigException("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
            } else {
                return (AopProxy)(!targetClass.isInterface() && !Proxy.isProxyClass(targetClass) ? new ObjenesisCglibAopProxy(config) : new JdkDynamicAopProxy(config));
            }
        }
    }
......
}
```

![image-20230227094258886](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227094258886.png)

这时候我们回退到getProxy方法。

![image-20230227094821507](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227094821507.png)

再来看看JdkDynamicAopProxy。

```java
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
......

    @Nullable
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object oldProxy = null;
        boolean setProxyContext = false;
        TargetSource targetSource = this.advised.targetSource;
        Object target = null;

        Integer var8;
        try {
            if (!this.equalsDefined && AopUtils.isEqualsMethod(method)) {
                Boolean var18 = this.equals(args[0]);
                return var18;
            }

            if (this.hashCodeDefined || !AopUtils.isHashCodeMethod(method)) {
                if (method.getDeclaringClass() == DecoratingProxy.class) {
                    Class var17 = AopProxyUtils.ultimateTargetClass(this.advised);
                    return var17;
                }

                Object retVal;
                if (!this.advised.opaque && method.getDeclaringClass().isInterface() && method.getDeclaringClass().isAssignableFrom(Advised.class)) {
                    retVal = AopUtils.invokeJoinpointUsingReflection(this.advised, method, args);
                    return retVal;
                }

                if (this.advised.exposeProxy) {
                    oldProxy = AopContext.setCurrentProxy(proxy);
                    setProxyContext = true;
                }

                target = targetSource.getTarget();
                Class<?> targetClass = target != null ? target.getClass() : null;
                List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
                if (chain.isEmpty()) {
                    Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, args);
                    retVal = AopUtils.invokeJoinpointUsingReflection(target, method, argsToUse);
                } else {
                    MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
                    retVal = invocation.proceed();
                }

                Class<?> returnType = method.getReturnType();
                if (retVal != null && retVal == target && returnType != Object.class && returnType.isInstance(proxy) && !RawTargetAccess.class.isAssignableFrom(method.getDeclaringClass())) {
                    retVal = proxy;
                } else if (retVal == null && returnType != Void.TYPE && returnType.isPrimitive()) {
                    throw new AopInvocationException("Null return value from advice does not match primitive return type for: " + method);
                }

                Object var12 = retVal;
                return var12;
            }

            var8 = this.hashCode();
        } finally {
            if (target != null && !targetSource.isStatic()) {
                targetSource.releaseTarget(target);
            }

            if (setProxyContext) {
                AopContext.setCurrentProxy(oldProxy);
            }

        }

        return var8;
    }
......
}
```

可以发现这个JdkDynamicAopProxy，实现了InvocationHandler接口，并且实现了invoke方法。

由此可见，JDK动态代理会生成一个代理对象类型为SimpleJpaRepository,而该对象的的增强就在JdkDynamicAopProxy类的invoke方法中



### 2.4 这个代理对象有什么特别的？进入该类型的源码去看。



```
@Repository
@Transactional(
    readOnly = true
)
public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {
}

@NoRepositoryBean
public interface JpaRepositoryImplementation<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
```

>原来SimpleJpaRepository类实现了JpaRepository接口和JpaSpecificationExecutor接口。

那继续查找一个方法查看下

```
@Repository
@Transactional(
    readOnly = true
)
public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {
......
    private final EntityManager em;

    public Optional<T> findById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        Class<T> domainType = this.getDomainClass();
        if (this.metadata == null) {
            return Optional.ofNullable(this.em.find(domainType, id));
        } else {
            LockModeType type = this.metadata.getLockModeType();
            Map<String, Object> hints = this.getQueryHints().withFetchGraphs(this.em).asMap();
            return Optional.ofNullable(type == null ? this.em.find(domainType, id, hints) : this.em.find(domainType, id, type, hints));
        }
    }
......
}
```

通过这个em.find调用了jpa原本的API，Spring Data JPA封装到此结束。

这时候我们将直接使用单元测试，来跟踪下我们刚刚代码的调用，是否最终进入了findById方法

那首先释放所有断点。重新在单元测试的入口打上断点，也就是UserDataInfoDaoTest的query1方法。

```
 Optional<UserDataInfo> optId = userDataInfoDao.findById(1L);
```

这时候我们单步进入

![image-20230227101330532](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227101330532.png)

发现当前就是JdkDynamicAopProxy的invoke方法，那继续进入，将是很多动态代理的增强的一些调用，直接在SimpleJpaRepository的findById打上断点，查看进入。
![image-20230227101650008](D:\work\myself\learn\learn-md\spring-mvc\img\jpa-image-20230227101650008.png)

通过跟踪代码就进入了findById方法。

至此JPA的一个源码分析，至此完成。
