## PostgreSQL  使用

显示所有表

```
SELECT tablename FROM pg_tables;  
```



切换数据库

```
postgres=# c demo
postgres-#
```



显示所有数据库

```
postgres=# \l
                                                                        数据库列表
   名称    |  拥有者  | 字元编码 |            校对规则            |             Ctype              | ICU Locale | Locale Provider |       存取权限
-----------+----------+----------+--------------------------------+--------------------------------+------------+-----------------+-----------------------
 demo      | postgres | UTF8     | Chinese (Simplified)_China.936 | Chinese (Simplified)_China.936 |            | libc            |
 postgres  | postgres | UTF8     | Chinese (Simplified)_China.936 | Chinese (Simplified)_China.936 |            | libc            |
 template0 | postgres | UTF8     | Chinese (Simplified)_China.936 | Chinese (Simplified)_China.936 |            | libc            | =c/postgres          +
           |          |          |                                |                                |            |                 | postgres=CTc/postgres
 template1 | postgres | UTF8     | Chinese (Simplified)_China.936 | Chinese (Simplified)_China.936 |            | libc            | =c/postgres          +
           |          |          |                                |                                |            |                 | postgres=CTc/postgres
```



当前数据库及当前用户

```
postgres=# select current_database()
postgres-# ;
 current_database
------------------
 postgres
(1 行记录)


postgres=# select current_user;
 current_user
--------------
 postgres
(1 行记录)
```

使用 \c + 数据库名 来进入数据库：

```
postgres=# \c + demo
用户 demo 的口令：
连接到"localhost" (::1)上的服务器，端口5432失败：致命错误:  用户 "demo" Password 认证失败
保留上一次连接
```



所有表

```
```







创建用户

```
CREATE USER liujun WITH PASSWORD 'liujun';
```





创建数据库

```
drop database maxwelldemo;

create database maxwelldemo;

CREATE DATABASE maxwelldemo OWNER liujun;
```



创建表

```cpp
CREATE TABLE user_demo(
   user_ID INT PRIMARY KEY     NOT NULL,
   user_NAME           TEXT    NOT NULL,
   create_time         timestamp 
);
```





```
\c + liujun
```





插入数据

```
insert into user_demo(user_id,user_name,create_time)
values(1,'liujun','2023-02-03 11:05:00');


insert into user_demo(user_id,user_name,create_time)
values(2,'liujun2','2023-02-02 11:05:00');


insert into user_demo(user_id,user_name,create_time)
values(3,'liujun3','2023-02-01 11:05:00');
```





查询数据

```
maxwelldemo=# select * from user_demo;
 user_id | user_name |     create_time
---------+-----------+---------------------
       1 | liujun    | 2023-02-03 11:05:00
       2 | liujun2   | 2023-02-02 11:05:00
       3 | liujun3   | 2023-02-01 11:05:00
```









```
grant USAGE on SCHEMA public to liujun ;


grant ALL on SCHEMA public to liujun ;

GRANT ALL ON maxwelldemo TO liujun;



GRANT ALL ON DATABASE maxwelldemo TO liujun;




GRANT ALL PRIVILEGES on maxwelldemo to admin

```





要使用无密码输入，使用环境变量

PGPASSWORD=liujun













