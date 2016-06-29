-- MySQL dump 10.13  Distrib 5.7.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: surveytool
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `anonimousresponse`
--

DROP TABLE IF EXISTS `anonimousresponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anonimousresponse` (
  `idAnonimousUser` int(11) NOT NULL,
  `idResponse` int(11) NOT NULL,
  PRIMARY KEY (`idAnonimousUser`,`idResponse`),
  KEY `fk_AnonimousResponse_Responses1_idx` (`idResponse`),
  CONSTRAINT `fk_AnonimousResponse_AnonimousUser1` FOREIGN KEY (`idAnonimousUser`) REFERENCES `anonimoususer` (`idAnonimousUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_AnonimousResponse_Responses1` FOREIGN KEY (`idResponse`) REFERENCES `responses` (`idResponse`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anonimousresponse`
--

LOCK TABLES `anonimousresponse` WRITE;
/*!40000 ALTER TABLE `anonimousresponse` DISABLE KEYS */;
/*!40000 ALTER TABLE `anonimousresponse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anonimoususer`
--

DROP TABLE IF EXISTS `anonimoususer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anonimoususer` (
  `idAnonimousUser` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idQuestionnaire` int(11) DEFAULT NULL,
  `ipAddres` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idAnonimousUser`),
  KEY `fk_AnonimousUser_Questionnaire1_idx` (`idQuestionnaire`),
  CONSTRAINT `fk_AnonimousUser_Questionnaire1` FOREIGN KEY (`idQuestionnaire`) REFERENCES `questionnaire` (`idQuestionnaire`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anonimoususer`
--

LOCK TABLES `anonimoususer` WRITE;
/*!40000 ALTER TABLE `anonimoususer` DISABLE KEYS */;
/*!40000 ALTER TABLE `anonimoususer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `idCategory` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idCategory`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'generic');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content` (
  `idContent` int(11) NOT NULL,
  `idLanguage` int(11) NOT NULL,
  `idContentType` int(11) NOT NULL,
  `text` varchar(1000) NOT NULL,
  PRIMARY KEY (`idContent`,`idLanguage`,`idContentType`),
  KEY `fk_Content_Language1_idx` (`idLanguage`),
  KEY `fk_Content_ContentType1_idx` (`idContentType`),
  CONSTRAINT `fk_Content_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Content_ContentType1` FOREIGN KEY (`idContentType`) REFERENCES `contenttype` (`idContentType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Content_Language1` FOREIGN KEY (`idLanguage`) REFERENCES `language` (`idLanguage`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contentindex`
--

DROP TABLE IF EXISTS `contentindex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contentindex` (
  `idContent` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idContent`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contentindex`
--

LOCK TABLES `contentindex` WRITE;
/*!40000 ALTER TABLE `contentindex` DISABLE KEYS */;
/*!40000 ALTER TABLE `contentindex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contenttype`
--

DROP TABLE IF EXISTS `contenttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contenttype` (
  `idContentType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idContentType`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contenttype`
--

LOCK TABLES `contenttype` WRITE;
/*!40000 ALTER TABLE `contenttype` DISABLE KEYS */;
INSERT INTO `contenttype` VALUES (1,'title'),(2,'description'),(3,'altText'),(4,'helpText'),(5,'ackText'),(6,'callText'),(7,'label'),(8,'other');
/*!40000 ALTER TABLE `contenttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dependencetype`
--

DROP TABLE IF EXISTS `dependencetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependencetype` (
  `idDependenceType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idDependenceType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependencetype`
--

LOCK TABLES `dependencetype` WRITE;
/*!40000 ALTER TABLE `dependencetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `dependencetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favouritefilter`
--

DROP TABLE IF EXISTS `favouritefilter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favouritefilter` (
  `idCategory` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  PRIMARY KEY (`idCategory`,`idQuestion`),
  KEY `fk_Filter_Question1_idx` (`idQuestion`),
  CONSTRAINT `fk_Filter_Category1` FOREIGN KEY (`idCategory`) REFERENCES `category` (`idCategory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Filter_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favouritefilter`
--

LOCK TABLES `favouritefilter` WRITE;
/*!40000 ALTER TABLE `favouritefilter` DISABLE KEYS */;
/*!40000 ALTER TABLE `favouritefilter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forma`
--

DROP TABLE IF EXISTS `forma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forma` (
  `idForma` int(11) NOT NULL AUTO_INCREMENT,
  `idQuestionnaire` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `ramdom` tinyint(1) NOT NULL DEFAULT '0',
  `numQuestion` tinyint(1) NOT NULL DEFAULT '0',
  `numPage` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idForma`),
  KEY `fk_Forma_Questionnaire1_idx` (`idQuestionnaire`),
  CONSTRAINT `fk_Forma_Questionnaire1` FOREIGN KEY (`idQuestionnaire`) REFERENCES `questionnaire` (`idQuestionnaire`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forma`
--

LOCK TABLES `forma` WRITE;
/*!40000 ALTER TABLE `forma` DISABLE KEYS */;
/*!40000 ALTER TABLE `forma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `idLanguage` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `isoName` varchar(2) NOT NULL,
  PRIMARY KEY (`idLanguage`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'english','en'),(2,'español','es');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option`
--

DROP TABLE IF EXISTS `option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option` (
  `idOption` int(11) NOT NULL AUTO_INCREMENT,
  `idContent` int(11) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idResoruces` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOption`),
  KEY `fk_Option_ContentIndex1_idx` (`idContent`),
  KEY `fk_Option_Resoruces1_idx` (`idResoruces`),
  CONSTRAINT `fk_Option_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Option_Resoruces1` FOREIGN KEY (`idResoruces`) REFERENCES `resoruces` (`idResoruces`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option`
--

LOCK TABLES `option` WRITE;
/*!40000 ALTER TABLE `option` DISABLE KEYS */;
/*!40000 ALTER TABLE `option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optionsbygroup`
--

DROP TABLE IF EXISTS `optionsbygroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optionsbygroup` (
  `idOptionsGroup` int(11) NOT NULL,
  `idOption` int(11) NOT NULL,
  `index` int(11) NOT NULL,
  PRIMARY KEY (`idOptionsGroup`,`idOption`),
  KEY `fk_optionsByGroup_Options_idx` (`idOption`),
  KEY `fk_optionsByGroup_OptionsGroup_idx` (`idOptionsGroup`),
  CONSTRAINT `fk_optionsByGroup_Options` FOREIGN KEY (`idOption`) REFERENCES `option` (`idOption`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_optionsByGroup_OptionsGroup` FOREIGN KEY (`idOptionsGroup`) REFERENCES `optionsgroup` (`idOptionsGroup`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optionsbygroup`
--

LOCK TABLES `optionsbygroup` WRITE;
/*!40000 ALTER TABLE `optionsbygroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `optionsbygroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optionsgroup`
--

DROP TABLE IF EXISTS `optionsgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optionsgroup` (
  `idOptionsGroup` int(11) NOT NULL AUTO_INCREMENT,
  `idQuestion` int(11) NOT NULL,
  `idContent` int(11) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idOptionType` int(11) NOT NULL,
  `random` tinyint(1) NOT NULL DEFAULT '0',
  `index` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOptionsGroup`),
  KEY `fk_OptionsGroup_Question1_idx` (`idQuestion`),
  KEY `fk_OptionsGroup_ContentIndex1_idx` (`idContent`),
  KEY `fk_OptionsGroup_OptionType1_idx` (`idOptionType`),
  CONSTRAINT `fk_OptionsGroup_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_OptionsGroup_OptionType1` FOREIGN KEY (`idOptionType`) REFERENCES `optiontype` (`idOptionType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_OptionsGroup_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optionsgroup`
--

LOCK TABLES `optionsgroup` WRITE;
/*!40000 ALTER TABLE `optionsgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `optionsgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optiontype`
--

DROP TABLE IF EXISTS `optiontype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optiontype` (
  `idOptionType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idOptionType`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optiontype`
--

LOCK TABLES `optiontype` WRITE;
/*!40000 ALTER TABLE `optiontype` DISABLE KEYS */;
INSERT INTO `optiontype` VALUES (1,'checkbox'),(2,'radio');
/*!40000 ALTER TABLE `optiontype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page` (
  `idPage` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `numPage` int(11) NOT NULL DEFAULT '0',
  `idResoruces` int(11) DEFAULT NULL,
  `idSection` int(11) NOT NULL,
  PRIMARY KEY (`idPage`),
  KEY `fk_Page_Resoruces1_idx` (`idResoruces`),
  KEY `fk_Page_Section1_idx` (`idSection`),
  CONSTRAINT `fk_Page_Resoruces1` FOREIGN KEY (`idResoruces`) REFERENCES `resoruces` (`idResoruces`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Page_Section1` FOREIGN KEY (`idSection`) REFERENCES `section` (`idSection`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page`
--

LOCK TABLES `page` WRITE;
/*!40000 ALTER TABLE `page` DISABLE KEYS */;
/*!40000 ALTER TABLE `page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagetype`
--

DROP TABLE IF EXISTS `pagetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagetype` (
  `idpageType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idpageType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagetype`
--

LOCK TABLES `pagetype` WRITE;
/*!40000 ALTER TABLE `pagetype` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameterforquestion`
--

DROP TABLE IF EXISTS `parameterforquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameterforquestion` (
  `idQuestion` int(11) NOT NULL,
  `idPage` int(11) NOT NULL,
  `idParameter` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`idQuestion`,`idPage`,`idParameter`),
  KEY `fk_parameter_idx` (`idParameter`),
  KEY `fk_page_idx` (`idPage`),
  CONSTRAINT `fk_page` FOREIGN KEY (`idPage`) REFERENCES `questionbypage` (`idPage`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_parameter` FOREIGN KEY (`idParameter`) REFERENCES `questionparameter` (`idParameter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_question` FOREIGN KEY (`idQuestion`) REFERENCES `questionbypage` (`idQuestion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameterforquestion`
--

LOCK TABLES `parameterforquestion` WRITE;
/*!40000 ALTER TABLE `parameterforquestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `parameterforquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameterforquestionpoll`
--

DROP TABLE IF EXISTS `parameterforquestionpoll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameterforquestionpoll` (
  `idQuestion` int(11) NOT NULL,
  `idPoll` int(11) NOT NULL,
  `idParameter` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`idQuestion`,`idPoll`,`idParameter`),
  KEY `fk_paramter_idx` (`idParameter`),
  KEY `fk_parameterforquestionpoll_parameter_idx` (`idParameter`),
  KEY `fk_parameterforquestionpoll_idpoll_idx` (`idPoll`),
  CONSTRAINT `fk_parameterforquestionpoll_idpoll` FOREIGN KEY (`idPoll`) REFERENCES `questionbypoll` (`idPoll`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_parameterforquestionpoll_idquestion` FOREIGN KEY (`idQuestion`) REFERENCES `questionbypoll` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_parameterforquestionpoll_parameter` FOREIGN KEY (`idParameter`) REFERENCES `questionparameter` (`idParameter`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameterforquestionpoll`
--

LOCK TABLES `parameterforquestionpoll` WRITE;
/*!40000 ALTER TABLE `parameterforquestionpoll` DISABLE KEYS */;
/*!40000 ALTER TABLE `parameterforquestionpoll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poll`
--

DROP TABLE IF EXISTS `poll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `poll` (
  `idPoll` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `publicId` varchar(45) NOT NULL,
  `author` int(11) NOT NULL,
  `idQuestionnaire` int(11) DEFAULT NULL,
  `idContent` int(11) NOT NULL,
  `idProject` int(11) NOT NULL,
  `deadLineDate` timestamp NULL DEFAULT NULL,
  `callUrl` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idPoll`),
  KEY `fk_Poll_User1_idx` (`author`),
  KEY `fk_Poll_Questionnaire1_idx` (`idQuestionnaire`),
  KEY `fk_Poll_ContentIndex1_idx` (`idContent`),
  KEY `fk_Poll_Project1_idx` (`idProject`),
  CONSTRAINT `fk_Poll_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Poll_Project1` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Poll_Questionnaire1` FOREIGN KEY (`idQuestionnaire`) REFERENCES `questionnaire` (`idQuestionnaire`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Poll_User1` FOREIGN KEY (`author`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poll`
--

LOCK TABLES `poll` WRITE;
/*!40000 ALTER TABLE `poll` DISABLE KEYS */;
/*!40000 ALTER TABLE `poll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `idProject` int(11) NOT NULL AUTO_INCREMENT,
  `projectName` varchar(100) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idProject`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'sdf','2016-04-18 12:30:11'),(2,'Encuesta2','2016-04-18 13:02:21'),(3,'Encuesta3','2016-04-19 07:07:38'),(4,'Encuesta4','2016-04-19 07:08:03'),(5,'Encuesta5','2016-04-19 07:08:33'),(6,'Encuesta6','2016-04-19 07:09:12'),(7,'Encuesta7','2016-04-19 07:09:31'),(8,'Encuesta8','2016-04-19 07:09:58'),(9,'Encuesta9','2016-04-19 07:10:15'),(10,'Encuesta10','2016-04-19 07:10:46'),(11,'Encuesta11','2016-04-19 07:11:02'),(12,'Encuesta12','2016-04-19 07:11:38'),(13,'Encuesta13','2016-04-19 07:11:55'),(14,'EncuestaEjemplo','2016-05-05 11:41:32'),(15,'asd','2016-05-06 10:47:06'),(16,'ss','2016-05-06 11:38:38'),(17,'asdfasdf','2016-05-09 07:11:51'),(18,'','2016-05-11 08:14:56'),(19,'','2016-05-11 08:14:56'),(20,'asdf','2016-05-11 09:06:54'),(21,'proyecto1','2016-05-11 09:07:30'),(22,'LKLKLKLKLKLKLKLKL','2016-05-11 09:38:25'),(23,'a','2016-05-11 11:10:08'),(24,'b','2016-05-12 07:46:02'),(25,'NuevaEncuestaESP','2016-05-24 15:29:35'),(26,'NuevaEncuestaEN','2016-05-24 15:30:11'),(27,'sadf','2016-05-25 07:28:44'),(28,'nuevaEncuestaPruebaESP','2016-05-26 07:26:31'),(29,'EncustaNMNM23','2016-05-26 08:25:35'),(30,'fasdf','2016-05-31 13:51:46'),(31,'aaaa','2016-05-31 14:09:55'),(32,'asdfgggg','2016-05-31 14:15:19'),(33,'gtgtgt','2016-05-31 14:18:21'),(34,'EncuestaDuplicada','2016-05-31 15:01:20'),(35,'EncuestaGuti','2016-05-31 15:17:32'),(36,'EncuestaSecciones','2016-05-31 15:30:27'),(37,'Encuesta1Junio2016v1','2016-06-01 09:50:56');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qdependences`
--

DROP TABLE IF EXISTS `qdependences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qdependences` (
  `idQDependences` int(11) NOT NULL AUTO_INCREMENT,
  `idQuestionnaire` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  `idQuestionObjetive` int(11) NOT NULL,
  `idOptionsGroupObjetive` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`idQDependences`),
  KEY `fk_QDependences_Question1_idx` (`idQuestion`),
  KEY `fk_QDependences_Question2_idx` (`idQuestionObjetive`),
  KEY `fk_QDependences_Questionnaire1_idx` (`idQuestionnaire`),
  KEY `fk_QDependences_OptionsGroup1_idx` (`idOptionsGroupObjetive`),
  CONSTRAINT `fk_QDependences_OptionsGroup1` FOREIGN KEY (`idOptionsGroupObjetive`) REFERENCES `optionsgroup` (`idOptionsGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_QDependences_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_QDependences_Question2` FOREIGN KEY (`idQuestionObjetive`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_QDependences_Questionnaire1` FOREIGN KEY (`idQuestionnaire`) REFERENCES `questionnaire` (`idQuestionnaire`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qdependences`
--

LOCK TABLES `qdependences` WRITE;
/*!40000 ALTER TABLE `qdependences` DISABLE KEYS */;
/*!40000 ALTER TABLE `qdependences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrelationships`
--

DROP TABLE IF EXISTS `qrelationships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrelationships` (
  `idQRelationships` int(11) NOT NULL AUTO_INCREMENT,
  `node1` int(11) NOT NULL,
  `idDependenceType` int(11) NOT NULL,
  `node2` int(11) DEFAULT NULL,
  `idRelationType` int(11) DEFAULT NULL,
  PRIMARY KEY (`idQRelationships`),
  KEY `fk_QRelationships_DependenceType1_idx` (`idDependenceType`),
  KEY `fk_QRelationships_RelationType1_idx` (`idRelationType`),
  CONSTRAINT `fk_QRelationships_DependenceType1` FOREIGN KEY (`idDependenceType`) REFERENCES `dependencetype` (`idDependenceType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_QRelationships_RelationType1` FOREIGN KEY (`idRelationType`) REFERENCES `relationtype` (`idRelationType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrelationships`
--

LOCK TABLES `qrelationships` WRITE;
/*!40000 ALTER TABLE `qrelationships` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrelationships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `idQuestion` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(45) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idQuestionType` int(11) NOT NULL,
  `parentQuestion` int(11) NOT NULL DEFAULT '0',
  `isTemplate` tinyint(1) NOT NULL DEFAULT '0',
  `idContent` int(11) NOT NULL,
  `idCategory` int(11) NOT NULL,
  PRIMARY KEY (`idQuestion`),
  KEY `fk_Question_QuestionType1_idx` (`idQuestionType`),
  KEY `fk_Question_ContentIndex1_idx` (`idContent`),
  KEY `fk_Question_Category1_idx` (`idCategory`),
  CONSTRAINT `fk_Question_Category1` FOREIGN KEY (`idCategory`) REFERENCES `category` (`idCategory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Question_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Question_QuestionType1` FOREIGN KEY (`idQuestionType`) REFERENCES `questiontype` (`idQuestionType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionbypage`
--

DROP TABLE IF EXISTS `questionbypage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionbypage` (
  `idPage` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  `index` int(11) NOT NULL DEFAULT '0',
  `mandatory` tinyint(1) DEFAULT '0',
  `optionalAnswer` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idPage`,`idQuestion`),
  KEY `fk_QuestionByPage_Question1_idx` (`idQuestion`),
  CONSTRAINT `fk_QuestionByPage_Page1` FOREIGN KEY (`idPage`) REFERENCES `page` (`idPage`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_QuestionByPage_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionbypage`
--

LOCK TABLES `questionbypage` WRITE;
/*!40000 ALTER TABLE `questionbypage` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionbypage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionbypoll`
--

DROP TABLE IF EXISTS `questionbypoll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionbypoll` (
  `idPoll` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  `index` int(11) NOT NULL,
  PRIMARY KEY (`idPoll`,`idQuestion`),
  KEY `fk_QuestionByPoll_Question1_idx` (`idQuestion`),
  CONSTRAINT `fk_QuestionByPoll_Poll1` FOREIGN KEY (`idPoll`) REFERENCES `poll` (`idPoll`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_QuestionByPoll_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionbypoll`
--

LOCK TABLES `questionbypoll` WRITE;
/*!40000 ALTER TABLE `questionbypoll` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionbypoll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionnaire`
--

DROP TABLE IF EXISTS `questionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionnaire` (
  `idQuestionnaire` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(45) NOT NULL,
  `startDate` timestamp NULL DEFAULT NULL,
  `deadLineDate` timestamp NULL DEFAULT NULL,
  `idContent` int(11) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idProject` int(11) DEFAULT NULL,
  `publicId` varchar(45) NOT NULL,
  `author` int(11) NOT NULL,
  `defaultLanguage` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idQuestionnaire`),
  KEY `fk_Questionnaire_ContentIndex1_idx` (`idContent`),
  KEY `fk_Questionnaire_Project1_idx` (`idProject`),
  KEY `fk_Questionnaire_User1_idx` (`author`),
  KEY `fk_Questionnarie_DefaultLang_idx` (`defaultLanguage`),
  CONSTRAINT `fk_Questionnaire_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Questionnaire_Project1` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Questionnaire_User1` FOREIGN KEY (`author`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Questionnarie_DefaultLang` FOREIGN KEY (`defaultLanguage`) REFERENCES `language` (`idLanguage`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionnaire`
--

LOCK TABLES `questionnaire` WRITE;
/*!40000 ALTER TABLE `questionnaire` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionparameter`
--

DROP TABLE IF EXISTS `questionparameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionparameter` (
  `idParameter` int(11) NOT NULL,
  `parameterName` varchar(45) NOT NULL,
  PRIMARY KEY (`idParameter`),
  UNIQUE KEY `parameterName_UNIQUE` (`parameterName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionparameter`
--

LOCK TABLES `questionparameter` WRITE;
/*!40000 ALTER TABLE `questionparameter` DISABLE KEYS */;
INSERT INTO `questionparameter` VALUES (7,'decimals'),(8,'formFieldInputMode'),(9,'formFieldType'),(1,'matrixType'),(3,'maxValue'),(2,'minValue'),(4,'textLength'),(5,'textLines'),(6,'textLinesAdjusted');
/*!40000 ALTER TABLE `questionparameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionresource`
--

DROP TABLE IF EXISTS `questionresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionresource` (
  `idQuestion` int(11) NOT NULL,
  `idResoruces` int(11) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idQuestion`,`idResoruces`),
  KEY `fk_QuestionResource_Resoruces1_idx` (`idResoruces`),
  CONSTRAINT `fk_QuestionResource_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_QuestionResource_Resoruces1` FOREIGN KEY (`idResoruces`) REFERENCES `resoruces` (`idResoruces`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionresource`
--

LOCK TABLES `questionresource` WRITE;
/*!40000 ALTER TABLE `questionresource` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questiontype`
--

DROP TABLE IF EXISTS `questiontype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questiontype` (
  `idQuestionType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `iconFile` varchar(45) DEFAULT NULL,
  `templateFile` varchar(100) DEFAULT NULL,
  `formFile` varchar(100) DEFAULT NULL,
  `jsFile` varchar(45) DEFAULT NULL,
  `cssFile` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idQuestionType`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questiontype`
--

LOCK TABLES `questiontype` WRITE;
/*!40000 ALTER TABLE `questiontype` DISABLE KEYS */;
INSERT INTO `questiontype` VALUES (1,'longText',NULL,'jsp/surveyManager/editQuestions/eQuestionLongText.jsp','jsp/surveyEngine/questions/fQuestionLongText.jsp',NULL,NULL),(2,'shortText',NULL,'jsp/surveyManager/editQuestions/eQuestionShortText.jsp','jsp/surveyEngine/questions/fQuestionShortText.jsp',NULL,NULL),(3,'simple',NULL,'jsp/surveyManager/editQuestions/eQuestionSimple.jsp','jsp/surveyEngine/questions/fQuestionSimple.jsp',NULL,NULL),(4,'matrix',NULL,'jsp/surveyManager/editQuestions/eQuestionMatrix.jsp','jsp/surveyEngine/questions/fQuestionMatrix.jsp',NULL,NULL),(5,'multiple',NULL,'jsp/surveyManager/editQuestions/eQuestionMultiple.jsp','jsp/surveyEngine/questions/fQuestionMultiple.jsp',NULL,NULL),(7,'scale',NULL,'jsp/surveyManager/editQuestions/eQuestionScale.jsp','jsp/surveyEngine/questions/fQuestionScale.jsp',NULL,NULL);
/*!40000 ALTER TABLE `questiontype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationtype`
--

DROP TABLE IF EXISTS `relationtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relationtype` (
  `idRelationType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idRelationType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationtype`
--

LOCK TABLES `relationtype` WRITE;
/*!40000 ALTER TABLE `relationtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `relationtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resoruces`
--

DROP TABLE IF EXISTS `resoruces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resoruces` (
  `idResoruces` int(11) NOT NULL AUTO_INCREMENT,
  `idResourceType` int(11) NOT NULL,
  `urlPath` varchar(100) NOT NULL,
  `idContent` int(11) NOT NULL,
  PRIMARY KEY (`idResoruces`),
  KEY `fk_Resoruces_ResourceType1_idx` (`idResourceType`),
  KEY `fk_Resoruces_ContentIndex1_idx` (`idContent`),
  CONSTRAINT `fk_Resoruces_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Resoruces_ResourceType1` FOREIGN KEY (`idResourceType`) REFERENCES `resourcetype` (`idResourceType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resoruces`
--

LOCK TABLES `resoruces` WRITE;
/*!40000 ALTER TABLE `resoruces` DISABLE KEYS */;
/*!40000 ALTER TABLE `resoruces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resourcetype`
--

DROP TABLE IF EXISTS `resourcetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcetype` (
  `idResourceType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idResourceType`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resourcetype`
--

LOCK TABLES `resourcetype` WRITE;
/*!40000 ALTER TABLE `resourcetype` DISABLE KEYS */;
INSERT INTO `resourcetype` VALUES (1,'image');
/*!40000 ALTER TABLE `resourcetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responses`
--

DROP TABLE IF EXISTS `responses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `responses` (
  `idResponse` int(11) NOT NULL AUTO_INCREMENT,
  `idQuestion` int(11) NOT NULL,
  `idOptionsGroup` int(11) DEFAULT NULL,
  `value` varchar(100) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idPoll` int(11) DEFAULT NULL,
  PRIMARY KEY (`idResponse`),
  KEY `fk_Responses_Question1_idx` (`idQuestion`),
  KEY `fk_Responses_OptionsGroup1_idx` (`idOptionsGroup`),
  KEY `fk_Responses_Poll1_idx` (`idPoll`),
  CONSTRAINT `fk_Responses_OptionsGroup1` FOREIGN KEY (`idOptionsGroup`) REFERENCES `optionsgroup` (`idOptionsGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Responses_Poll1` FOREIGN KEY (`idPoll`) REFERENCES `poll` (`idPoll`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_Responses_Question1` FOREIGN KEY (`idQuestion`) REFERENCES `question` (`idQuestion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responses`
--

LOCK TABLES `responses` WRITE;
/*!40000 ALTER TABLE `responses` DISABLE KEYS */;
/*!40000 ALTER TABLE `responses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `rolName` varchar(45) NOT NULL,
  PRIMARY KEY (`idRol`),
  UNIQUE KEY `name_UNIQUE` (`rolName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'admin'),(3,'designer'),(2,'user');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `idSection` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `index` int(11) NOT NULL DEFAULT '0',
  `idForma` int(11) NOT NULL,
  `idContent` int(11) NOT NULL,
  PRIMARY KEY (`idSection`),
  KEY `fk_Section_Forma1_idx` (`idForma`),
  KEY `fk_Section_ContentIndex1_idx` (`idContent`),
  CONSTRAINT `fk_Section_ContentIndex1` FOREIGN KEY (`idContent`) REFERENCES `contentindex` (`idContent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Section_Forma1` FOREIGN KEY (`idForma`) REFERENCES `forma` (`idForma`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `registerDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `idRol` int(11) NOT NULL,
  `idLanguage` int(11) DEFAULT NULL,
  `usercol` varchar(45) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idUser`),
  KEY `fk_User_Rol1_idx` (`idRol`),
  KEY `fk_User_Language_idx` (`idLanguage`),
  CONSTRAINT `fk_User_Rol1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'guti','fasdf111@fasdf.com','12345678','2016-05-19 10:17:16',0,1,1,'1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userquestionnaire`
--

DROP TABLE IF EXISTS `userquestionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userquestionnaire` (
  `idUserQuestionnaire` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `idQuestionnaire` int(11) NOT NULL,
  `state` varchar(45) NOT NULL,
  `idForma` int(11) NOT NULL,
  `progress` int(11) NOT NULL DEFAULT '0',
  `lastCompletedPageId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUserQuestionnaire`),
  KEY `fk_UserQuestionnaire_Questionnaire1_idx` (`idQuestionnaire`),
  KEY `fk_UserQuestionnaire_Forma1_idx` (`idForma`),
  KEY `fk_UserQuestionnaire_Page1_idx` (`lastCompletedPageId`),
  KEY `fk_UserQuestionnaire_User1` (`idUser`),
  CONSTRAINT `fk_UserQuestionnaire_Forma1` FOREIGN KEY (`idForma`) REFERENCES `forma` (`idForma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserQuestionnaire_Page1` FOREIGN KEY (`lastCompletedPageId`) REFERENCES `page` (`idPage`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserQuestionnaire_Questionnaire1` FOREIGN KEY (`idQuestionnaire`) REFERENCES `questionnaire` (`idQuestionnaire`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserQuestionnaire_User1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userquestionnaire`
--

LOCK TABLES `userquestionnaire` WRITE;
/*!40000 ALTER TABLE `userquestionnaire` DISABLE KEYS */;
/*!40000 ALTER TABLE `userquestionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userresponse`
--

DROP TABLE IF EXISTS `userresponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userresponse` (
  `idUserQuestionnaire` int(11) NOT NULL,
  `idResponse` int(11) NOT NULL,
  PRIMARY KEY (`idUserQuestionnaire`,`idResponse`),
  KEY `fk_UserResponse_Responses1_idx` (`idResponse`),
  CONSTRAINT `fk_UserResponse_Responses1` FOREIGN KEY (`idResponse`) REFERENCES `responses` (`idResponse`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserResponse_UserQuestionnaire1` FOREIGN KEY (`idUserQuestionnaire`) REFERENCES `userquestionnaire` (`idUserQuestionnaire`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userresponse`
--

LOCK TABLES `userresponse` WRITE;
/*!40000 ALTER TABLE `userresponse` DISABLE KEYS */;
/*!40000 ALTER TABLE `userresponse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-29 11:41:11
