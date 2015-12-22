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
  `id_utente` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `capiprogetto`
--

LOCK TABLES `capiprogetto` WRITE;
/*!40000 ALTER TABLE `capiprogetto` DISABLE KEYS */;
INSERT INTO `capiprogetto` VALUES (2),(3);
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
INSERT INTO `carrelli` VALUES (1,1,2);
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
  `nome_sede` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dipendenti`
--

LOCK TABLES `dipendenti` WRITE;
/*!40000 ALTER TABLE `dipendenti` DISABLE KEYS */;
INSERT INTO `dipendenti` VALUES (1,1,'Sede A');
/*!40000 ALTER TABLE `dipendenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornitori`
--

DROP TABLE IF EXISTS `fornitori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornitori` (
  `nome` varchar(60) DEFAULT NULL,
  `id_prodotto` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitori`
--

LOCK TABLES `fornitori` WRITE;
/*!40000 ALTER TABLE `fornitori` DISABLE KEYS */;
INSERT INTO `fornitori` VALUES ('Fornitore A',1),('Fornitore B',1),('Fornitore C',NULL),('Fornitore D',NULL);
/*!40000 ALTER TABLE `fornitori` ENABLE KEYS */;
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
  `nome_sede` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzini`
--

LOCK TABLES `magazzini` WRITE;
/*!40000 ALTER TABLE `magazzini` DISABLE KEYS */;
INSERT INTO `magazzini` VALUES (1,'Magazzino del Sole','Sede A'),(2,'Magazzino della Luna','Sede B'),(3,'Magazzino delle Stelle','Sede C'),(4,'Magazzino del Cielo','Sede D');
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
INSERT INTO `magazzinieri` VALUES (4,1),(5,2);
/*!40000 ALTER TABLE `magazzinieri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordini`
--

DROP TABLE IF EXISTS `ordini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordini` (
  `codice` varchar(128) DEFAULT NULL,
  `id_dipendente` int(11) DEFAULT NULL,
  `nome_sede` varchar(60) DEFAULT NULL,
  `id_progetto` int(11) DEFAULT NULL,
  `id_magazzino` int(11) DEFAULT NULL,
  `id_prodotto` int(11) DEFAULT NULL,
  `quantita` int(11) DEFAULT NULL,
  `spedito` tinyint(4) DEFAULT '0',
  `data` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordini`
--

LOCK TABLES `ordini` WRITE;
/*!40000 ALTER TABLE `ordini` DISABLE KEYS */;
INSERT INTO `ordini` VALUES ('-1099431227',1,'Sede A',1,1,1,12,0,1450212300682),('-1099431227',1,'Sede A',1,2,2,1,0,1450212300682);
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
INSERT INTO `prod_mag` VALUES (1,1,2),(2,2,7),(1,2,14);
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotti`
--

LOCK TABLES `prodotti` WRITE;
/*!40000 ALTER TABLE `prodotti` DISABLE KEYS */;
INSERT INTO `prodotti` VALUES (1,'Stampante MultiJet C460','Stampanti','Stampante con scanner, NFC, WIFI e USB',399.99),(2,'Stampante Jackpot','Stampanti','Stampante con porta USB e collegamento ethernet',99.99),(3,'Toner Nero','Ricambi','Toner di colore nero per stampanti',9.99),(4,'Toner Rosso','Ricambi','Toner di colore rosso per stampanti',9.99),(5,'Toner Blu','Ricambi','Toner di colore blu per stampanti',9.99),(6,'Toner Giallo','Ricambi','Toner di colore giallo per stampanti',9.99),(7,'Toner Verde','Ricambi','Toner di colore verde per stampanti',9.99),(8,'Matita HB','Cancelleria','Matita HB',0.99),(9,'Penna Stilo Blu','Cancelleria','Penna Stilo Blu',1.49),(10,'Penna Stilo Nera','Cancelleria','Penna Stilo Nera',1.49),(11,'Penna Stilo Rossa','Cancelleria','Penna Stilo Rossa',1.49),(12,'Righello 15 cm','Cancelleria','Righello in plastica lungo 15 cm',1.2),(13,'Righello 1 m','Cancelleria','Righello in plastica lungo 1 m',2.5),(14,'Righello di metallo da 20 cm','Cancelleria','Righello di metallo lungo 20 cm',3),(16,'Hard Disk 500GB 2.5\'\'','Elettronica','Hard Disk da 500GB da 2.5\'\'',39.99),(17,'Hard Disk 1TB 2.5\'\'','Elettronica','Hard Disk da 1TB da 2.5\'\'',69.99),(18,'SSD 1TB 2.5\'\'','Elettronica','SSD da 1TB da 2.5\'\'',399.99),(19,'SSD 500GB 2.5\'\'','Elettronica','SSD da 500GB da 2.5\'\'',299.99),(20,'Tastiera Meccanica Pok3r','Elettronica','Tastiera meccanica Pok3r con ingresso micro-USB (cavo incluso)',149.99),(21,'Tastiera con numpad','Elettronica','Tastiera per computer con numpad con ingresso USB (cavo incluso)',29.99),(22,'Cavo USB-microUSB 50cm','Elettronica','Cavo USB-microUSB lungo mezzo metro',3.99),(23,'Cavo USB-typeC 50cm','Elettronica','Cavo USB-typeC lungo mezzo metro',5.99);
/*!40000 ALTER TABLE `prodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produttori`
--

DROP TABLE IF EXISTS `produttori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produttori` (
  `nome` varchar(60) DEFAULT NULL,
  `id_prodotto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produttori`
--

LOCK TABLES `produttori` WRITE;
/*!40000 ALTER TABLE `produttori` DISABLE KEYS */;
INSERT INTO `produttori` VALUES ('Produttore A',1),('Produttore B',2),('Produttore C',NULL);
/*!40000 ALTER TABLE `produttori` ENABLE KEYS */;
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
  `id_capoprog` int(10) unsigned DEFAULT NULL,
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
INSERT INTO `progetti` VALUES (1,'Progetto Taco',2,1000,6700),(2,'Progetto Pizza',3,4500,4500);
/*!40000 ALTER TABLE `progetti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sedi`
--

DROP TABLE IF EXISTS `sedi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sedi` (
  `nome` varchar(60) DEFAULT NULL,
  `indirizzo` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sedi`
--

LOCK TABLES `sedi` WRITE;
/*!40000 ALTER TABLE `sedi` DISABLE KEYS */;
INSERT INTO `sedi` VALUES ('Sede A','Parco della Vittoria 5'),('Sede B','Viale dei Giardini 7'),('Sede C','Largo Augusto 10'),('Sede D','Corso Impero 22');
/*!40000 ALTER TABLE `sedi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `superadmin`
--

DROP TABLE IF EXISTS `superadmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `superadmin` (
  `id_utente` int(10) unsigned DEFAULT NULL,
  UNIQUE KEY `id_utente` (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `superadmin`
--

LOCK TABLES `superadmin` WRITE;
/*!40000 ALTER TABLE `superadmin` DISABLE KEYS */;
/*!40000 ALTER TABLE `superadmin` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `email_2` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES (1,'Guga','Rucola','guga.rucola@napoli.na','f67c2bcbfcfa30fccb36f72dca22a817'),(2,'Luke','Mynetti','luke.mynetti@unilento.le','834576ff97df524f60d8690e0519e866'),(3,'Mario','Rossi','mario.rossi@asd.asd','d614bc3109a30c1d30fb502eaade04e3'),(4,'Tizio','Caio','tizio.caio@magazzinodelsole.sol','29ddd676c753140581f97add2b52eadc'),(5,'Ciccio','Brizio','ciccio.brizio@magazzinodellaluna.lun','ffb170398ce21be68c2118d25f29e5d3');
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

-- Dump completed on 2015-12-21 18:09:20
