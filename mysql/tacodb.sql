-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: localhost    Database: tacodb
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `capiprogetto`
--

DROP TABLE IF EXISTS `capiprogetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `capiprogetto` (
  `id_utente` int(10) unsigned DEFAULT NULL,
  `id_progetto` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `capiprogetto`
--

LOCK TABLES `capiprogetto` WRITE;
/*!40000 ALTER TABLE `capiprogetto` DISABLE KEYS */;
INSERT INTO `capiprogetto` VALUES (2,1),(3,2);
/*!40000 ALTER TABLE `capiprogetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrelli`
--

DROP TABLE IF EXISTS `carrelli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrelli` (
  `id_dipendente` int(11) DEFAULT NULL,
  `id_prodotto` int(11) DEFAULT NULL,
  `quantita` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrelli`
--

LOCK TABLES `carrelli` WRITE;
/*!40000 ALTER TABLE `carrelli` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrelli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dipendenti`
--

DROP TABLE IF EXISTS `dipendenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dipendenti` (
  `id_utente` int(11) DEFAULT NULL,
  `id_progetto` int(11) DEFAULT NULL,
  `id_sede` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dipendenti`
--

LOCK TABLES `dipendenti` WRITE;
/*!40000 ALTER TABLE `dipendenti` DISABLE KEYS */;
INSERT INTO `dipendenti` VALUES (1,1,NULL);
/*!40000 ALTER TABLE `dipendenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazzini`
--

DROP TABLE IF EXISTS `magazzini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazzini` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) DEFAULT NULL,
  `id_sede` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzini`
--

LOCK TABLES `magazzini` WRITE;
/*!40000 ALTER TABLE `magazzini` DISABLE KEYS */;
/*!40000 ALTER TABLE `magazzini` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazzinieri`
--

DROP TABLE IF EXISTS `magazzinieri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazzinieri` (
  `id_utente` int(11) DEFAULT NULL,
  `id_magazzino` int(11) DEFAULT NULL,
  UNIQUE KEY `id_utente` (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzinieri`
--

LOCK TABLES `magazzinieri` WRITE;
/*!40000 ALTER TABLE `magazzinieri` DISABLE KEYS */;
/*!40000 ALTER TABLE `magazzinieri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordini`
--

DROP TABLE IF EXISTS `ordini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordini` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_dipendente` int(11) DEFAULT NULL,
  `id_sede` int(11) DEFAULT NULL,
  `id_progetto` int(11) DEFAULT NULL,
  `id_magazzino` int(11) DEFAULT NULL,
  `id_prodotto` int(11) DEFAULT NULL,
  `quantita` int(11) DEFAULT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `spedito` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordini`
--

LOCK TABLES `ordini` WRITE;
/*!40000 ALTER TABLE `ordini` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordini` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prod_mag`
--

DROP TABLE IF EXISTS `prod_mag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prod_mag` (
  `id_magazzino` int(11) DEFAULT NULL,
  `id_prodotto` int(11) DEFAULT NULL,
  `quantita` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prod_mag`
--

LOCK TABLES `prod_mag` WRITE;
/*!40000 ALTER TABLE `prod_mag` DISABLE KEYS */;
/*!40000 ALTER TABLE `prod_mag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotti`
--

DROP TABLE IF EXISTS `prodotti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) DEFAULT NULL,
  `categoria` varchar(60) DEFAULT NULL,
  `descrizione` text,
  `prezzo` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotti`
--

LOCK TABLES `prodotti` WRITE;
/*!40000 ALTER TABLE `prodotti` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produttore`
--

DROP TABLE IF EXISTS `produttore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produttore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_prodotto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produttore`
--

LOCK TABLES `produttore` WRITE;
/*!40000 ALTER TABLE `produttore` DISABLE KEYS */;
/*!40000 ALTER TABLE `produttore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progetti`
--

DROP TABLE IF EXISTS `progetti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `progetti` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `budget` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progetti`
--

LOCK TABLES `progetti` WRITE;
/*!40000 ALTER TABLE `progetti` DISABLE KEYS */;
INSERT INTO `progetti` VALUES (1,'Progetto Taco',10.34,6700),(2,'Progetto Pizza',4500,4500);
/*!40000 ALTER TABLE `progetti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sedi`
--

DROP TABLE IF EXISTS `sedi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sedi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) DEFAULT NULL,
  `indirizzo` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sedi`
--

LOCK TABLES `sedi` WRITE;
/*!40000 ALTER TABLE `sedi` DISABLE KEYS */;
/*!40000 ALTER TABLE `sedi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utenti` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `cognome` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(128) NOT NULL,
  `data_reg` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES (1,'Guga','Rucola','guga.rucola@napoli.na','asd123','2015-12-03 12:37:21'),(2,'Luke','Mynetti','luke.mynetti@unilento.le','asd123','2015-12-05 11:06:15'),(3,'Mario','Rossi','mario.rossi@asd.asd','asd123','2015-12-07 12:44:15');
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-09 18:14:57
