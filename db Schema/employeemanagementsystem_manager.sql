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
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `ManagerID` int NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Fname` varchar(255) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `Salary` decimal(10,2) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Education` varchar(255) DEFAULT NULL,
  `PhoneNumber` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `CNIC` varchar(15) DEFAULT NULL,
  `Designation` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ManagerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'Asad Ali','Asad Khan.','1980-05-15',75000.00,'123 Main St',NULL,'123-456-7890','asad@example.com','12345-6789123-0','Senior Manager'),(2,'Anusha Anu','Ashneer.','1985-08-22',65000.50,'456 RWP',NULL,'987-654-3210','anusha@example.com','98765-4321987-1','Project Manager'),(3,'Azam','Azam Khan.','1975-03-10',80000.75,'789 ISB',NULL,'567-890-1234','azam@example.com','56789-0123456-2','Manager of Operations');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-24 21:41:18
