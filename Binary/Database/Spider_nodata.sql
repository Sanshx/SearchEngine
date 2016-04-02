-- MySQL dump 10.13  Distrib 5.5.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Spider
-- ------------------------------------------------------
-- Server version	5.5.41-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allpages_table`
--

DROP TABLE IF EXISTS `allpages_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `allpages_table` (
  `page_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seed_id` int(10) unsigned NOT NULL,
  `page_url` varchar(200) NOT NULL,
  `depth` tinyint(3) unsigned DEFAULT NULL,
  `incoming_link` smallint(5) unsigned DEFAULT NULL,
  `outgoing_link` smallint(5) unsigned DEFAULT NULL,
  `added` tinyint(3) unsigned DEFAULT NULL,
  `fetched` tinyint(3) unsigned DEFAULT NULL,
  `page_title` text,
  `page_info` text,
  PRIMARY KEY (`page_id`),
  KEY `seed_id` (`seed_id`),
  CONSTRAINT `allpages_table_ibfk_1` FOREIGN KEY (`seed_id`) REFERENCES `seeds_table` (`seed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49263 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allpages_table`
--

LOCK TABLES `allpages_table` WRITE;
/*!40000 ALTER TABLE `allpages_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `allpages_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brokenlink_table`
--

DROP TABLE IF EXISTS `brokenlink_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brokenlink_table` (
  `seed_id` int(10) unsigned DEFAULT NULL,
  `page_url` varchar(200) DEFAULT NULL,
  KEY `seed_id` (`seed_id`),
  CONSTRAINT `brokenlink_table_ibfk_1` FOREIGN KEY (`seed_id`) REFERENCES `seeds_table` (`seed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brokenlink_table`
--

LOCK TABLES `brokenlink_table` WRITE;
/*!40000 ALTER TABLE `brokenlink_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `brokenlink_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fetchdate_table`
--

DROP TABLE IF EXISTS `fetchdate_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fetchdate_table` (
  `seed_id` int(10) unsigned NOT NULL,
  `fetch_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `seed_id` (`seed_id`),
  CONSTRAINT `fetchdate_table_ibfk_1` FOREIGN KEY (`seed_id`) REFERENCES `seeds_table` (`seed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fetchdate_table`
--

LOCK TABLES `fetchdate_table` WRITE;
/*!40000 ALTER TABLE `fetchdate_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `fetchdate_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_mapping`
--

DROP TABLE IF EXISTS `location_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_mapping` (
  `treatment_id` int(10) unsigned DEFAULT NULL,
  `location_id` int(10) unsigned DEFAULT NULL,
  KEY `treatment_id` (`treatment_id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `location_mapping_ibfk_1` FOREIGN KEY (`treatment_id`) REFERENCES `treatment_table` (`treatment_id`),
  CONSTRAINT `location_mapping_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `maps_list` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_mapping`
--

LOCK TABLES `location_mapping` WRITE;
/*!40000 ALTER TABLE `location_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maps_admin`
--

DROP TABLE IF EXISTS `maps_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps_admin` (
  `username` varchar(32) NOT NULL,
  `password` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maps_admin`
--

LOCK TABLES `maps_admin` WRITE;
/*!40000 ALTER TABLE `maps_admin` DISABLE KEYS */;
INSERT INTO `maps_admin` VALUES ('admin','admin');
/*!40000 ALTER TABLE `maps_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maps_list`
--

DROP TABLE IF EXISTS `maps_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maps_list` (
  `location_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` tinyint(3) unsigned DEFAULT NULL,
  `city` varchar(30) NOT NULL,
  `address` text,
  PRIMARY KEY (`location_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `maps_list_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_table` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maps_list`
--

LOCK TABLES `maps_list` WRITE;
/*!40000 ALTER TABLE `maps_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `maps_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seeds_table`
--

DROP TABLE IF EXISTS `seeds_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seeds_table` (
  `seed_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seed_url` varchar(100) NOT NULL,
  `type_id` tinyint(3) unsigned DEFAULT NULL,
  `seed_updated` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`seed_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `seeds_table_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_table` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10048 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seeds_table`
--

LOCK TABLES `seeds_table` WRITE;
/*!40000 ALTER TABLE `seeds_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `seeds_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_table`
--

DROP TABLE IF EXISTS `treatment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `treatment_table` (
  `treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `treatment_name` varchar(100) NOT NULL,
  PRIMARY KEY (`treatment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10042 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_table`
--

LOCK TABLES `treatment_table` WRITE;
/*!40000 ALTER TABLE `treatment_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `treatment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_table`
--

DROP TABLE IF EXISTS `type_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_table` (
  `type_id` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `type_name` varchar(20) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_table`
--

LOCK TABLES `type_table` WRITE;
/*!40000 ALTER TABLE `type_table` DISABLE KEYS */;
INSERT INTO `type_table` VALUES (1,'Information'),(2,'NGO'),(3,'Hospital');
/*!40000 ALTER TABLE `type_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words_detail`
--

DROP TABLE IF EXISTS `words_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `words_detail` (
  `word_id` int(10) unsigned NOT NULL,
  `page_id` int(10) unsigned DEFAULT NULL,
  `frequency` int(10) unsigned DEFAULT NULL,
  `degree` tinyint(3) unsigned DEFAULT NULL,
  `type_id` tinyint(3) unsigned DEFAULT '1',
  KEY `word_id` (`word_id`),
  CONSTRAINT `words_detail_ibfk_1` FOREIGN KEY (`word_id`) REFERENCES `words_table` (`word_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words_detail`
--

LOCK TABLES `words_detail` WRITE;
/*!40000 ALTER TABLE `words_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `words_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words_table`
--

DROP TABLE IF EXISTS `words_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `words_table` (
  `word_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `word_name` varchar(100) DEFAULT NULL,
  `frequency` int(10) unsigned DEFAULT '1',
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56622 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words_table`
--

LOCK TABLES `words_table` WRITE;
/*!40000 ALTER TABLE `words_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `words_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-19 21:32:37
