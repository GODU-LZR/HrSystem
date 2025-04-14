/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.39 : Database - systemadministrationmodule
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`systemadministrationmodule` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `systemadministrationmodule`;

/*Table structure for table `checksalarystd` */

DROP TABLE IF EXISTS `checksalarystd`;

CREATE TABLE `checksalarystd` (
  `checkid` int NOT NULL AUTO_INCREMENT,
  `sid` int DEFAULT NULL,
  `sname` varchar(16) DEFAULT NULL,
  `total` int DEFAULT NULL,
  `maker` varchar(16) DEFAULT NULL,
  `register` varchar(16) DEFAULT NULL,
  `stime` timestamp NULL DEFAULT NULL,
  `opinion` varchar(255) DEFAULT NULL,
  `variety` int DEFAULT NULL,
  `checked` int DEFAULT NULL,
  PRIMARY KEY (`checkid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `checksalarystd` */

insert  into `checksalarystd`(`checkid`,`sid`,`sname`,`total`,`maker`,`register`,`stime`,`opinion`,`variety`,`checked`) values (20,27,'派遣',5000,'张三','admin','2024-12-17 00:00:00','新增派遣',1,1),(21,28,'外包',5000,'张三','admin','2024-12-17 00:00:00','新增外包',1,1),(22,29,'合同',6000,'张三','admin','2024-12-17 00:00:00','新增合同',1,1),(23,30,'编制',10000,'张三','admin','2024-12-17 00:00:00','新增编制',1,1);

/*Table structure for table `departmentpayroll` */

DROP TABLE IF EXISTS `departmentpayroll`;

CREATE TABLE `departmentpayroll` (
  `dpid` int NOT NULL AUTO_INCREMENT,
  `onedepartment` varchar(16) DEFAULT NULL,
  `twodepartment` varchar(16) DEFAULT NULL,
  `threedepartment` varchar(16) DEFAULT NULL,
  `number` int DEFAULT NULL,
  `departmentsalary` int DEFAULT NULL,
  `dtime` timestamp NULL DEFAULT NULL,
  `register` varchar(16) DEFAULT NULL,
  `checked` int DEFAULT NULL,
  PRIMARY KEY (`dpid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `departmentpayroll` */

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `archiveId` int NOT NULL,
  `orgLevel1` varchar(255) DEFAULT NULL COMMENT 'I级机构',
  `orgLevel2` varchar(255) DEFAULT NULL COMMENT 'II级机构',
  `orgLevel3` varchar(255) DEFAULT NULL COMMENT 'III级机构',
  `positionCategory` varchar(255) DEFAULT NULL COMMENT '职位分类',
  `positionName` varchar(255) DEFAULT NULL COMMENT '职位名称',
  `salaryStander` varchar(255) DEFAULT NULL COMMENT '薪酬标准',
  `employeePhotoUrl` varchar(255) DEFAULT NULL COMMENT '员工照片 URL',
  `NAME` varchar(255) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别 (男/女)',
  `email` varchar(255) DEFAULT NULL COMMENT 'Email',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  `postalCode` varchar(20) DEFAULT NULL COMMENT '邮编',
  `nationality` varchar(255) DEFAULT NULL COMMENT '国籍',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `ethnicity` varchar(50) DEFAULT NULL COMMENT '民族',
  `religion` varchar(255) DEFAULT NULL COMMENT '宗教信仰',
  `politicalAffiliation` varchar(255) DEFAULT NULL COMMENT '政治面貌',
  `idNumber` varchar(50) DEFAULT NULL COMMENT '身份证号码',
  `socialSecurityNumber` varchar(50) DEFAULT NULL COMMENT '社会保障号码',
  `age` int DEFAULT NULL COMMENT '年龄',
  `education` varchar(50) DEFAULT NULL COMMENT '学历',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `registerTime` datetime DEFAULT NULL COMMENT '建档时间',
  `RESUME` text COMMENT '个人履历',
  `familyInfo` text COMMENT '家庭关系信息',
  `remarks` text COMMENT '备注',
  `reviewId` int DEFAULT NULL COMMENT '复核状态 0 待复核 1 已复核',
  `isdeleted` int DEFAULT NULL COMMENT '删除状态 0 未删除 1已删除',
  `registrant` varchar(255) DEFAULT NULL COMMENT '登记人名称',
  `changePerson` varchar(255) DEFAULT NULL COMMENT '变更人名称',
  `reviewer` varchar(255) DEFAULT NULL COMMENT '复核人名称',
  PRIMARY KEY (`archiveId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee` */

insert  into `employee`(`archiveId`,`orgLevel1`,`orgLevel2`,`orgLevel3`,`positionCategory`,`positionName`,`salaryStander`,`employeePhotoUrl`,`NAME`,`gender`,`email`,`phone`,`qq`,`mobile`,`address`,`postalCode`,`nationality`,`birthday`,`ethnicity`,`religion`,`politicalAffiliation`,`idNumber`,`socialSecurityNumber`,`age`,`education`,`account`,`registerTime`,`RESUME`,`familyInfo`,`remarks`,`reviewId`,`isdeleted`,`registrant`,`changePerson`,`reviewer`) values (401020501,'总公司','华东分公司','上海办事处','技术类','Java工程师','合同',NULL,'零','female','1','1','1','1','1','1','1','2024-12-16 08:00:00','','1','1','1','1',1,'','1','2024-12-17 14:26:17','1','1','1',1,0,'admin',NULL,'admin'),(401020502,'总公司','华东分公司','上海办事处','管理类','技术总监','合同',NULL,'4','male','4','4','4','4','4','4','4','2024-12-19 08:00:00','','4','4','4','4',4,'','4','2024-12-17 14:28:56','4','4','4',1,0,'admin',NULL,'admin'),(401020503,'总公司','华东分公司','上海办事处','管理类','部门主管','合同',NULL,'4','male','4','4','4','4','4','4','4','2024-12-12 08:00:00','','4','4','4','4',4,'','4','2024-12-17 14:34:16','4','4','4',1,0,'admin',NULL,'admin'),(401020504,'总公司','华东分公司','上海办事处','管理类','技术总监','合同',NULL,'5','male','5','5','5','5','5','5','5','2024-12-13 08:00:00','','5','5','5','5',5,'','5','2024-12-17 14:34:52','5','5','5',1,0,'admin',NULL,'admin'),(401020601,'总公司','华东分公司','南京办事处','技术类','前端工程师','合同',NULL,'3','','3','3','3','3','3','3','3','2024-12-28 08:00:00','','3','3','3','3',3,'','3','2024-12-17 14:32:07','3','3','3',1,0,'admin',NULL,'admin'),(401030701,'总公司','华南分公司','广州办事处','管理类','项目经理','合同',NULL,'老板','male','1','1','1','1','1','1','1','2024-12-20 08:00:00','','1','1','1','1',1,'','1','2024-12-17 14:27:29','1','1','1',1,0,'admin',NULL,'admin'),(401030702,'总公司','华南分公司','广州办事处','技术类','前端工程师','外包',NULL,'我','female','3','3','3','3','3','3','3','2024-12-20 08:00:00','','3','3','3','3',3,'','3','2024-12-17 14:33:05','3','3','3',1,0,'admin',NULL,'admin'),(401030703,'总公司','华南分公司','广州办事处','管理类','部门主管','合同',NULL,'2','female','2','2','2','2','2','2','2','2024-12-13 08:00:00','','2','2','2','2',2,'','2','2024-12-17 14:35:32','2','2','2',0,0,'admin',NULL,NULL),(401030801,'总公司','华南分公司','深圳办事处','管理类','技术总监','合同',NULL,'q','','1','2','1','1','1','1','1','2024-12-19 08:00:00','','1','1','1','1',1,'','1','2024-12-17 14:31:36','1','1','1',0,0,'admin',NULL,NULL),(401040901,'总公司','华北分公司','北京办事处','管理类','技术总监','编制',NULL,'3','male','3','3','3','3','3','3','3','2024-12-18 08:00:00','','3','3','3','3',3,'','3','2024-12-17 14:29:34','3','3','3',0,0,'admin',NULL,NULL),(411121301,'海外总公司','加拿大分公司','温哥华办事处','技术类','前端工程师','合同',NULL,'2','male','2','2','2','2','2','2','2','2024-12-25 08:00:00','','2','2','2','2',2,'','2','2024-12-17 14:28:15','2','2','2',0,0,'admin',NULL,NULL),(411121302,'海外总公司','加拿大分公司','温哥华办事处','技术类','Java工程师','合同',NULL,'去','','1','1','1','1','1','1','1','2024-12-12 08:00:00','','1','1','1','1',1,'','1','2024-12-17 14:30:19','1','1','1',0,0,'admin',NULL,NULL),(411121303,'海外总公司','加拿大分公司','温哥华办事处','技术类','运维工程师','合同',NULL,'我','male','2','2','2','2','2','2','2','2024-12-18 08:00:00','','2','2','2','2',2,'','2','2024-12-17 14:30:53','2','2','2',0,0,'admin',NULL,NULL),(411121304,'海外总公司','加拿大分公司','温哥华办事处','技术类','Java工程师','外包',NULL,'5','','4','2','3','3','3','3','3','2024-12-26 08:00:00','','3','3','3','3',3,'','3','2024-12-17 14:33:46','3','3','3',0,0,'admin',NULL,NULL);

/*Table structure for table `employeepayroll` */

DROP TABLE IF EXISTS `employeepayroll`;

CREATE TABLE `employeepayroll` (
  `epid` int NOT NULL AUTO_INCREMENT,
  `dpid` int DEFAULT NULL,
  `ename` varchar(16) DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `checked` int DEFAULT NULL,
  PRIMARY KEY (`epid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employeepayroll` */

/*Table structure for table `organization` */

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `orgId` int NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  `orgName` varchar(100) NOT NULL COMMENT '机构名称',
  `orgLevel` int NOT NULL COMMENT '机构层级:1-一级,2-二级,3-三级',
  `parentId` int DEFAULT NULL COMMENT '父级机构ID',
  `orgSort` int DEFAULT NULL COMMENT '排序号',
  `STATUS` tinyint DEFAULT '1' COMMENT '状态:1-启用,0-禁用',
  PRIMARY KEY (`orgId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `organization` */

insert  into `organization`(`orgId`,`orgName`,`orgLevel`,`parentId`,`orgSort`,`STATUS`) values (1,'总公司',1,0,1,1),(2,'华东分公司',2,1,1,1),(3,'华南分公司',2,1,2,1),(4,'华北分公司',2,1,3,1),(5,'上海办事处',3,2,1,1),(6,'南京办事处',3,2,2,1),(7,'广州办事处',3,3,1,1),(8,'深圳办事处',3,3,2,1),(9,'北京办事处',3,4,1,1),(10,'天津办事处',3,4,2,1),(11,'海外总公司',1,0,2,1),(12,'加拿大分公司',2,11,1,1),(13,'温哥华办事处',3,12,1,1);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permissionId` int NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `permission` */

insert  into `permission`(`permissionId`,`permissionName`,`description`) values (7,'普通用户',NULL),(11,'人力资源档案登记',NULL),(12,'人力资源档案变更',NULL),(13,'人力资源档案登记复核',NULL),(14,'人力资源档案查询',NULL),(15,'人力资源档案删除',NULL),(16,'薪酬标准管理',NULL),(17,'薪酬发放管理',NULL),(18,'系统管理员',NULL),(20,'薪酬标准复核管理',NULL);

/*Table structure for table `positioninfo` */

DROP TABLE IF EXISTS `positioninfo`;

CREATE TABLE `positioninfo` (
  `positionId` int NOT NULL AUTO_INCREMENT COMMENT '职位ID',
  `positionType` varchar(50) DEFAULT NULL COMMENT '职位类型',
  `positionName` varchar(50) DEFAULT NULL COMMENT '职位名称',
  `parentId` int DEFAULT '0' COMMENT '父级ID(0表示职位类型,其他值关联职位类型的positionId)',
  `positionSort` int DEFAULT NULL COMMENT '排序号',
  `isEnabled` tinyint DEFAULT '1' COMMENT '状态:1-启用,0-禁用',
  PRIMARY KEY (`positionId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `positioninfo` */

insert  into `positioninfo`(`positionId`,`positionType`,`positionName`,`parentId`,`positionSort`,`isEnabled`) values (1,'技术类','技术类',0,1,1),(2,'管理类','管理类',0,2,1),(3,'营销类','营销类',0,3,1),(4,'财务类','财务类',0,4,1),(5,'技术类','Java工程师',1,1,1),(6,'技术类','前端工程师',1,2,1),(7,'技术类','测试工程师',1,3,1),(8,'技术类','运维工程师',1,4,1),(9,'管理类','项目经理',2,1,1),(10,'管理类','部门主管',2,2,1),(11,'管理类','技术总监',2,3,1),(12,'营销类','销售经理',3,1,1),(13,'营销类','销售代表',3,2,1),(14,'营销类','市场专员',3,3,1),(15,'财务类','财务总监',4,1,1),(16,'财务类','会计',4,2,1),(17,'财务类','出纳',4,3,1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `roleId` int NOT NULL AUTO_INCREMENT,
  `roleName` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`roleId`,`roleName`,`description`) values (3,'人事专员',NULL),(4,'人事经理',NULL),(5,'薪酬专员',NULL),(6,'薪酬经理',NULL),(7,'普通用户',NULL),(8,'系统管理员',NULL);

/*Table structure for table `salarystd` */

DROP TABLE IF EXISTS `salarystd`;

CREATE TABLE `salarystd` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `sname` varchar(16) DEFAULT NULL,
  `total` int DEFAULT NULL,
  `maker` varchar(16) DEFAULT NULL,
  `register` varchar(16) DEFAULT NULL,
  `stime` timestamp NULL DEFAULT NULL,
  `checker` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `salarystd` */

insert  into `salarystd`(`sid`,`sname`,`total`,`maker`,`register`,`stime`,`checker`) values (27,'派遣',5000,'张三','admin','2024-12-17 00:00:00','admin'),(28,'外包',5000,'张三','admin','2024-12-17 00:00:00','admin'),(29,'合同',6000,'张三','admin','2024-12-17 00:00:00','admin'),(30,'编制',10000,'张三','admin','2024-12-17 00:00:00','admin');

/*Table structure for table `spring_session` */

DROP TABLE IF EXISTS `spring_session`;

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `spring_session` */

insert  into `spring_session`(`PRIMARY_ID`,`SESSION_ID`,`CREATION_TIME`,`LAST_ACCESS_TIME`,`MAX_INACTIVE_INTERVAL`,`EXPIRY_TIME`,`PRINCIPAL_NAME`) values ('0f4f1b8b-cb72-408f-8441-b04e3b597614','e271734c-6880-4dec-9ca2-da452e09399b',1734425250699,1734425250700,1800,1734427050700,NULL),('18ea6332-d496-425a-a7b1-84a7e6b19251','dbc979aa-e2ed-4074-906d-1cf4bbc1e16b',1734425870433,1734425870433,1800,1734427670433,NULL),('4b3641e7-469b-4c4c-b557-b2e004096320','244c4f7e-f974-4332-9d25-73d554ebdd12',1734424171579,1734424574474,1800,1734426374474,NULL),('7105277d-79c0-4e69-9642-1318aa745095','c6941355-6626-46b7-ab3e-d97526212169',1734425464456,1734425545761,1800,1734427345761,NULL),('741e3b7e-80a4-47f6-8e2d-85a48e6652e3','2fba027f-a3b2-4ca5-b83d-ff12d040969b',1734424788987,1734424929777,1800,1734426729777,NULL),('7cff43d8-b23b-4a74-a293-de108b108af7','8638d2ce-e5f7-4ee2-9079-b5f9e46a73cc',1734424115152,1734424165586,1800,1734425965586,NULL),('eace89a6-c798-4167-9fa3-4625f673a71e','d86fbeb0-50a5-4fb9-9e28-5f00027b3b4c',1734425319394,1734425323159,1800,1734427123159,NULL);

/*Table structure for table `spring_session_attributes` */

DROP TABLE IF EXISTS `spring_session_attributes`;

CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `spring_session_attributes` */

insert  into `spring_session_attributes`(`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`,`ATTRIBUTE_BYTES`) values ('0f4f1b8b-cb72-408f-8441-b04e3b597614','user','��\0t\0admin'),('4b3641e7-469b-4c4c-b557-b2e004096320','user','��\0t\0admin'),('7105277d-79c0-4e69-9642-1318aa745095','user','��\0t\0admin'),('741e3b7e-80a4-47f6-8e2d-85a48e6652e3','user','��\0t\0admin'),('7cff43d8-b23b-4a74-a293-de108b108af7','user','��\0t\0admin'),('eace89a6-c798-4167-9fa3-4625f673a71e','user','��\0t\0admin');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `roleId` int DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`userId`,`username`,`password`,`email`,`roleId`) values (1,'admin','admin','admin@qq.com',8),(2,'蔡徐坤','12345678','cxk@qq.com',4),(3,'小黑子','12345678','xhz@qq.com',3),(4,'范家发','12345678','fjf@qq.com',6),(5,'范小勤','12345678','fxq@qq.com',5),(6,'丽丽','12345678','ll@qq.com',7),(7,'abc','abc','asdhwidhqwoiu',7);

/*Table structure for table `userrolepermission` */

DROP TABLE IF EXISTS `userrolepermission`;

CREATE TABLE `userrolepermission` (
  `userId` int NOT NULL,
  `roleId` int NOT NULL,
  `permissionId` int NOT NULL,
  PRIMARY KEY (`userId`,`roleId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `userrolepermission` */

insert  into `userrolepermission`(`userId`,`roleId`,`permissionId`) values (0,3,11),(0,3,12),(0,3,14),(0,4,13),(0,4,14),(0,4,15),(0,5,16),(0,5,17),(0,6,16),(0,6,17),(0,6,20),(0,7,7),(0,8,11),(0,8,12),(0,8,13),(0,8,14),(0,8,15),(0,8,16),(0,8,17),(0,8,18),(0,8,20),(1,8,11),(1,8,12),(1,8,13),(1,8,14),(1,8,15),(1,8,16),(1,8,17),(1,8,18),(1,8,20),(2,4,13),(2,4,14),(2,4,15),(3,3,11),(3,3,12),(3,3,14),(4,6,16),(4,6,17),(5,5,16),(5,5,17),(6,7,7),(7,7,7);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
