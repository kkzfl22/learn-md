CREATE DATABASE `spring_r2dbc`;

use spring_r2dbc;

CREATE TABLE `student` (
  `id` int(11) COMMENT '编号',
  `code` varchar(50) NOT NULL COMMENT '学号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` char(1) NOT NULL COMMENT '姓名:M男，F女',
  `birthday` date NOT NULL COMMENT '生日',
  `address` varchar(300) NULL COMMENT '家庭住址',
  `remark` varchar(1000) NULL COMMENT '备注',
  `active` tinyint NOT NULL DEFAULT 1 COMMENT '有效标识，默认为1',
  `createdAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `createdBy` varchar(50) NOT NULL COMMENT '创建人',
  `updatedAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `updatedBy` varchar(50) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `idx_main`(`code`)
);



insert into student(id,code, name, gender, birthday, address, createdBy,updatedBy)
values
  (1,'S0001', 'Tom', 'M', '2001-03-05', null, 'TEST', 'TEST'),
  (2,'S0002', 'Ted', 'M', '2001-06-12', null, 'TEST', 'TEST'),
  (3,'S0003', 'Mary', 'F', '2001--9-12', 'Chicago', 'TEST', 'TEST')
;
