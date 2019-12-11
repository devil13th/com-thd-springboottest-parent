-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `my_user`
--

DROP TABLE IF EXISTS `my_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_user` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `user_age` int(11) DEFAULT NULL,
  `user_birthday` date DEFAULT NULL,
  `user_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_user`
--

LOCK TABLES `my_user` WRITE;
/*!40000 ALTER TABLE `my_user` DISABLE KEYS */;
INSERT INTO `my_user` VALUES ('1','ZHANGSAN',23,'2019-10-24','2019-10-24 16:13:26'),('2','ZHANGSAN1',43,'2019-10-27','2019-10-24 16:13:25'),('3','ZHANGSAN1',0,'2019-10-27','2019-10-24 16:13:25'),('4','ZHANGSAN1',2,'2019-10-27','2019-10-24 16:13:26'),('5','dev',32,'2019-10-24','2019-10-24 17:21:48'),('6','ZHANGSAN',49,'2019-10-25','2019-10-24 16:13:25'),('7','ZHANGSAN',25,'2019-10-23','2019-10-24 16:13:24'),('8','ZHANGSAN',22,'2019-10-24','2019-10-24 16:47:03'),('9','ZHANGSAN',7,'2019-10-23','2019-10-24 16:13:24'),('bd955259-97c9-45ba-ba28-fed7f76ed43a','ZHANGSAN',49,'2019-10-21','2019-10-24 16:13:24'),('dae71dde-d0f3-4e94-b36a-f7cc10bd437a','ZHANGSAN',0,'2019-10-27','2019-10-24 16:13:27');
/*!40000 ALTER TABLE `my_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_sex` int(11) DEFAULT NULL,
  `user_mail` varchar(100) DEFAULT NULL,
  `user_tel` varchar(50) DEFAULT NULL,
  `user_birthday` date DEFAULT NULL,
  `user_status` varchar(10) DEFAULT NULL,
  `org_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','n_1075622b-ae13-45c7-a17f-c85aff73ff30',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('2','n_2eb95be2-091e-402b-8f0c-6abe65a89a20',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('3','n_45ff73a0-d108-4c4f-9917-f210d3e3fe3b',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('4','n_6a5152cc-eba3-4bbf-a702-0f5a4aaf9d10',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('5','n_a0f86994-d7b3-4221-ba89-f2fcbbf02268',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('8d9640b2-7b8e-46b6-99ec-0cb3cf5c0392','n_8d9640b2-7b8e-46b6-99ec-0cb3cf5c0392',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('c5c3afdb-c668-474a-b3fe-7a977e494906','n_c5c3afdb-c668-474a-b3fe-7a977e494906',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('e1a13f36-5bcb-42f8-9a67-087151ff97f5','ttt1',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL),('f3366067-6d98-4575-9e4f-c1251ee60f36','n_f3366067-6d98-4575-9e4f-c1251ee60f36',1,'tt@163.com',NULL,'2019-09-18',NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-28  9:51:08
