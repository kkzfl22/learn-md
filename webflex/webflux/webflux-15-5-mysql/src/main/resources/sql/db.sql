create database mysql_r2dbc;



CREATE TABLE `student` (
  `id` int(11) ,
  `code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` char(1) NOT NULL,
  `birthday` date NOT NULL,
  `address` varchar(300) NULL,
  `remark` varchar(1000) NULL,
  `active` tinyint NOT NULL DEFAULT 1,
  `createdAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `createdBy` varchar(50) NOT NULL,
  `updatedAt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE
CURRENT_TIMESTAMP(0),
  `updatedBy` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_main`(`code`)
);



insert into student(code, name, gender, birthday, address, createdBy, 
updatedBy)
values
  (1,'S0001', 'Tom', 'M', '2001-03-05', null, 'TEST', 'TEST')
 ,(2,'S0002', 'Ted', 'M', '2001-06-12', null, 'TEST', 'TEST')
 ,(3,'S0003', 'Mary', 'F', '2001--9-12', 'Chicago', 'TEST', 'TEST')
;
