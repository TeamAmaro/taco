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
INSERT INTO `capiprogetto` VALUES (2),(3),(23);
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
-- Table structure for table `dettagliordini`
--

DROP TABLE IF EXISTS `dettagliordini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dettagliordini` (
  `codice` varchar(128) DEFAULT NULL,
  `id_prodotto` int(11) DEFAULT NULL,
  `quantita` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dettagliordini`
--

LOCK TABLES `dettagliordini` WRITE;
/*!40000 ALTER TABLE `dettagliordini` DISABLE KEYS */;
INSERT INTO `dettagliordini` VALUES ('-1268359798',1,1),('-1268359798',2,1),('-1486330459',9,2),('-1486330459',12,5),('-1486330459',6,2),('1700112824',6,8),('-1486330459',9,2),('-1486330459',12,5),('-1486330459',6,2),('1700112824',6,8),('-2104777059',9,2),('-322712807',8,5),('-322712807',6,5),('-294083656',8,5),('-294083656',6,5),('-236825354',8,5),('-236825354',6,5),('-1623333860',8,5),('-1623333860',6,5),('1160350460',8,5),('1160409980',6,5);
/*!40000 ALTER TABLE `dettagliordini` ENABLE KEYS */;
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
INSERT INTO `dipendenti` VALUES (1,1,'Sede A'),(16,1,'Sede B'),(17,1,'Sede C'),(24,2,'Sede B'),(25,2,'Sede D');
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
  `id_prodotto` int(10) unsigned DEFAULT NULL,
  UNIQUE KEY `nome` (`nome`,`id_prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitori`
--

LOCK TABLES `fornitori` WRITE;
/*!40000 ALTER TABLE `fornitori` DISABLE KEYS */;
INSERT INTO `fornitori` VALUES ('Fornitore A',2),('Fornitore A',3),('Fornitore A',5),('Fornitore A',7),('Fornitore A',9),('Fornitore A',12),('Fornitore A',16),('Fornitore A',17),('Fornitore A',18),('Fornitore A',21),('Fornitore B',1),('Fornitore B',2),('Fornitore B',3),('Fornitore B',4),('Fornitore B',6),('Fornitore B',8),('Fornitore B',9),('Fornitore B',10),('Fornitore B',11),('Fornitore B',12),('Fornitore B',13),('Fornitore B',14),('Fornitore B',16),('Fornitore B',17),('Fornitore B',18),('Fornitore B',19),('Fornitore B',20),('Fornitore B',21),('Fornitore B',22),('Fornitore B',23),('Fornitore C',2),('Fornitore C',4),('Fornitore C',6),('Fornitore C',9),('Fornitore C',10),('Fornitore C',13),('Fornitore C',14),('Fornitore C',16),('Fornitore C',18),('Fornitore C',19),('Fornitore C',20),('Fornitore C',21),('Fornitore C',22),('Fornitore C',23),('Fornitore D',2),('Fornitore D',4),('Fornitore D',5),('Fornitore D',6),('Fornitore D',7),('Fornitore D',8),('Fornitore D',9),('Fornitore D',10),('Fornitore D',11),('Fornitore D',12),('Fornitore D',17),('Fornitore D',18),('Fornitore D',19),('Fornitore D',21),('Fornitore D',23);
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
  `id_magazziniere` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzini`
--

LOCK TABLES `magazzini` WRITE;
/*!40000 ALTER TABLE `magazzini` DISABLE KEYS */;
INSERT INTO `magazzini` VALUES (1,'Magazzino del Sole','Sede A',4),(2,'Magazzino della Luna','Sede B',5),(3,'Magazzino delle Stelle','Sede C',20),(4,'Magazzino del Cielo','Sede D',26);
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
  UNIQUE KEY `id_utente` (`id_utente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzinieri`
--

LOCK TABLES `magazzinieri` WRITE;
/*!40000 ALTER TABLE `magazzinieri` DISABLE KEYS */;
INSERT INTO `magazzinieri` VALUES (4),(5),(20),(26);
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
  `nome_sede_dip` varchar(60) DEFAULT NULL,
  `id_progetto` int(11) DEFAULT NULL,
  `id_magazzino` int(11) DEFAULT NULL,
  `spedito` tinyint(4) DEFAULT '0',
  `data` bigint(20) DEFAULT NULL
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
INSERT INTO `prod_mag` VALUES (1,1,27),(1,2,42),(1,3,52),(1,4,38),(1,5,90),(1,6,19),(1,7,64),(1,9,27),(1,10,26),(1,11,76),(1,12,22),(1,13,94),(1,16,58),(1,17,24),(1,18,40),(1,19,25),(1,20,86),(1,21,80),(1,22,5),(1,23,35),(2,1,66),(2,2,87),(2,3,34),(2,4,85),(2,5,29),(2,6,72),(2,7,47),(2,9,80),(2,10,36),(2,11,51),(2,12,33),(2,13,61),(2,14,4),(2,16,99),(2,17,39),(2,18,68),(2,19,84),(2,20,36),(2,21,11),(2,22,47),(2,23,71),(3,1,64),(3,2,74),(3,3,12),(3,4,38),(3,5,53),(3,6,28),(3,7,71),(3,9,38),(3,10,81),(3,11,79),(3,12,83),(3,13,23),(3,16,99),(3,17,86),(3,18,24),(3,19,13),(3,20,2),(3,21,96),(3,22,53),(3,23,25),(4,1,26),(4,2,71),(4,3,85),(4,4,8),(4,5,35),(4,6,38),(4,7,41),(4,8,90),(4,9,24),(4,10,50),(4,11,17),(4,12,89),(4,13,24),(4,16,70),(4,17,2),(4,18,6),(4,19,68),(4,20,47),(4,21,90),(4,22,98),(4,23,15),(1,8,17),(2,8,18),(3,8,74),(1,14,24),(3,14,97),(4,14,45);
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
  `immagine` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotti`
--

LOCK TABLES `prodotti` WRITE;
/*!40000 ALTER TABLE `prodotti` DISABLE KEYS */;
INSERT INTO `prodotti` VALUES (1,'Stampante MultiJet C460','Stampanti','Stampante con scanner, NFC, WIFI e USB',399.99,'stampantemultijet.jpg'),(2,'Stampante Jackpot','Stampanti','Stampante con porta USB e collegamento ethernet',99.99,'stampantemultijet.jpg'),(3,'Toner Nero','Ricambi','Toner di colore nero per stampanti',9.99,'toner.jpg'),(4,'Toner Rosso','Ricambi','Toner di colore rosso per stampanti',9.99,'toner.jpg'),(5,'Toner Blu','Ricambi','Toner di colore blu per stampanti',9.99,'toner.jpg'),(6,'Toner Giallo','Ricambi','Toner di colore giallo per stampanti',9.99,'toner.jpg'),(7,'Toner Verde','Ricambi','Toner di colore verde per stampanti',9.99,'toner.jpg'),(8,'Matita HB','Cancelleria','Matita HB',0.99,'matita.jpg'),(9,'Penna Stilo Blu','Cancelleria','Penna Stilo Blu',1.49,'penna.jpg'),(10,'Penna Stilo Nera','Cancelleria','Penna Stilo Nera',1.49,'penna.jpg'),(11,'Penna Stilo Rossa','Cancelleria','Penna Stilo Rossa',1.49,'penna.jpg'),(12,'Righello 15 cm','Cancelleria','Righello in plastica lungo 15 cm',1.2,'righello.jpg'),(13,'Righello 1 m','Cancelleria','Righello in plastica lungo 1 m',2.5,'righello.jpg'),(14,'Righello di metallo da 20 cm','Cancelleria','Righello di metallo lungo 20 cm',3,'righello.jpg'),(16,'Hard Disk 500GB 2.5\'\'','Elettronica','Hard Disk da 500GB da 2.5\'\'',39.99,'harddisk.jpg'),(17,'Hard Disk 1TB 2.5\'\'','Elettronica','Hard Disk da 1TB da 2.5\'\'',69.99,'harddisk.jpg'),(18,'SSD 1TB 2.5\'\'','Elettronica','SSD da 1TB da 2.5\'\'',399.99,'harddisk.jpg'),(19,'SSD 500GB 2.5\'\'','Elettronica','SSD da 500GB da 2.5\'\'',299.99,'harddisk.jpg'),(20,'Tastiera Meccanica Pok3r','Elettronica','Tastiera meccanica Pok3r con ingresso micro-USB (cavo incluso)',149.99,'pok3r.jpg'),(21,'Tastiera con numpad','Elettronica','Tastiera per computer con numpad con ingresso USB (cavo incluso)',29.99,'pok3r.jpg'),(22,'Cavo USB-microUSB 50cm','Elettronica','Cavo USB-microUSB lungo mezzo metro',3.99,'cavousb.jpg'),(23,'Cavo USB-typeC 50cm','Elettronica','Cavo USB-typeC lungo mezzo metro',5.99,'cavousb.jpg');
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
INSERT INTO `produttori` VALUES ('Samsung',1),('Kyocera',2),('Bic',9),('Bic',8),('Bic',10),('Bic',11),('Bic',12),('Bic',13),('Bic',14),('Toni',3),('Toni',4),('Toni',5),('Toni',6),('Toni',7),('Samsung',16),('Samsung',17),('Samsung',18),('Samsung',19),('Kyocera',20),('Kyocera',21),('Kyocera',22),('Kyocera',23);
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
INSERT INTO `progetti` VALUES (1,'Progetto Taco',2,6700,6700),(2,'Progetto Pizza',3,4500,4500);
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES (1,'Guga','Rucola','guga.rucola@napoli.na','f67c2bcbfcfa30fccb36f72dca22a817'),(2,'Luke','Skywalker','luke.skywalker@jedi.je','834576ff97df524f60d8690e0519e866'),(3,'Mario','Rossi','mario.rossi@asd.asd','d614bc3109a30c1d30fb502eaade04e3'),(4,'Tizio','Caio','tizio.caio@magazzinodelsole.sol','29ddd676c753140581f97add2b52eadc'),(5,'Ciccio','Brizio','ciccio.brizio@magazzinodellaluna.lun','ffb170398ce21be68c2118d25f29e5d3'),(16,'Jim','Questions','jim.questions@ask.com','e7af1c75730aa1184fa928dae1bdf8e'),(17,'John','Cena','john.cena@wwe.com','e7af1c75730aa1184fa928dae1bdf8e'),(20,'Paolino','Paperino','paolino.paperino@topolino.it','e7af1c75730aa1184fa928dae1bdf8e'),(23,'Alfonso','Verde','alfonso.verde@green.ve','2d91bf040b3919d9f743ee80b12377e5'),(24,'Rocco','Turrocco','rocco.turrocco@california.ca','2d91bf040b3919d9f743ee80b12377e5'),(25,'Perry','Gerry','perry.gerry@curry.cu','5e8192a9cdd479f16706f145154c7b14'),(26,'Luca','Pera','luca.pera@semplici.it','7af22f55b823f1ccc688b64859177d3e');
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

-- Dump completed on 2016-02-16 18:10:34
