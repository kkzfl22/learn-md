-- 创建数据库并
CREATE DATABASE nullnulldb CHARACTER SET utf8 COLLATE utf8_bin;

-- 切换数据库
use nullnulldb;

-- 创建表语句
CREATE TABLE data_user (
 userid int(11) NOT NULL AUTO_INCREMENT,
 username varchar(20) COLLATE utf8_bin DEFAULT NULL,
 password varchar(20) COLLATE utf8_bin DEFAULT NULL,
 userroles varchar(2) COLLATE utf8_bin DEFAULT NULL,
 nickname varchar(50) COLLATE utf8_bin DEFAULT NULL,
 PRIMARY KEY (userid)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 插入数据
INSERT INTO data_user (username,PASSWORD,userroles,nickname) VALUES
('admin','1234','04','超级管理员'),
('nullnull','1234','03','管理员');