-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: employeemanagementsystem
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `leaveapplication`
--

DROP TABLE IF EXISTS `leaveapplication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leaveapplication` (
  `EmployeeID` int NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `LeaveType` varchar(50) DEFAULT NULL,
  `LstartDate` date DEFAULT NULL,
  `LendDate` date DEFAULT NULL,
  `Reason` text,
  `Approval` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`EmployeeID`),
  CONSTRAINT `leaveapplication_chk_1` CHECK ((`Approval` in (_utf8mb4'yes',_utf8mb4'no')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leaveapplication`
--

LOCK TABLES `leaveapplication` WRITE;
/*!40000 ALTER TABLE `leaveapplication` DISABLE KEYS */;
INSERT INTO `leaveapplication` VALUES (22,'Azam Khan','Sick Leave','2023-11-11','2023-11-11','I am having some health issues','no'),(77,'ALi','Personal Leave','2023-01-11','2023-05-12','I am taking Leave today, I have lots of work to do.','yes'),(111,'Samir','Vacation','2023-12-12','2023-12-30','I wanna go on Vacation for Few Weeks so Accept my Leave Application','no');
/*!40000 ALTER TABLE `leaveapplication` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-24 21:41:17
