CREATE DATABASE  IF NOT EXISTS `leaguemanager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `leaguemanager`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: leaguemanager
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `competicion`
--

DROP TABLE IF EXISTS `competicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competicion` (
  `nombre` varchar(100) NOT NULL,
  `numero_equipos` int DEFAULT NULL,
  `temporada` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competicion`
--

LOCK TABLES `competicion` WRITE;
/*!40000 ALTER TABLE `competicion` DISABLE KEYS */;
INSERT INTO `competicion` VALUES ('Champions',10,'25/26'),('Liga',20,'25/26'),('Ligue A',20,'25/26'),('Marta',2,'25/26');
/*!40000 ALTER TABLE `competicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrena`
--

DROP TABLE IF EXISTS `entrena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrena` (
  `entrenador_dni` varchar(20) NOT NULL,
  `equipo_nombre` varchar(100) NOT NULL,
  `temporada` varchar(50) NOT NULL,
  PRIMARY KEY (`entrenador_dni`,`equipo_nombre`,`temporada`),
  KEY `equipo_nombre` (`equipo_nombre`),
  CONSTRAINT `entrena_ibfk_1` FOREIGN KEY (`entrenador_dni`) REFERENCES `entrenador` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `entrena_ibfk_2` FOREIGN KEY (`equipo_nombre`) REFERENCES `equipo` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrena`
--

LOCK TABLES `entrena` WRITE;
/*!40000 ALTER TABLE `entrena` DISABLE KEYS */;
INSERT INTO `entrena` VALUES ('11122233E','Athletic Club','25/26'),('11122233A','Atlético de Madrid','25/26'),('11122233J','CA Osasuna','25/26'),('11122233S','CD Leganés','25/26'),('11122233Q','Deportivo Alavés','25/26'),('11122233C','FC Barcelona','25/26'),('11122233K','Getafe CF','25/26'),('11122233F','Girona FC','25/26'),('11122233N','Rayo Vallecano','25/26'),('11122233L','RC Celta','25/26'),('11122233T','RCD Espanyol','25/26'),('11122233O','RCD Mallorca','25/26'),('11122233G','Real Betis','25/26'),('26163181Q','Real Madrid','25/26'),('11122233D','Real Sociedad','25/26'),('11122233R','Real Valladolid','25/26'),('1234657893Q','Sevilla FC','25/26'),('11122233P','UD Las Palmas','25/26'),('11122233H','Valencia CF','25/26'),('11122233B','Villarreal CF','25/26');
/*!40000 ALTER TABLE `entrena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrenador`
--

DROP TABLE IF EXISTS `entrenador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrenador` (
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrenador`
--

LOCK TABLES `entrenador` WRITE;
/*!40000 ALTER TABLE `entrenador` DISABLE KEYS */;
INSERT INTO `entrenador` VALUES ('11122233A','Diego Simeone',56),('11122233B','Marcelino García Toral',60),('11122233C','Hansi Flick',61),('11122233D','Imanol Alguacil',54),('11122233E','Ernesto Valverde',62),('11122233F','Míchel Sánchez',50),('11122233G','Manuel Pellegrini',72),('11122233H','Rubén Baraja',50),('11122233J','Vicente Moreno',51),('11122233K','José Bordalás',62),('11122233L','Claudio Giráldez',38),('11122233N','Íñigo Pérez',38),('11122233O','Jagoba Arrasate',48),('11122233P','Luis Carrión',47),('11122233Q','Luis García Plaza',53),('11122233R','Paulo Pezzolano',43),('11122233S','Borja Jiménez',41),('11122233T','Manolo González',47),('12345697P','Chavo',57),('1234657893Q','Unai Emery',50),('26163181Q','Mourinho',55);
/*!40000 ALTER TABLE `entrenador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipo` (
  `nombre` varchar(100) NOT NULL,
  `ciudad` varchar(100) DEFAULT NULL,
  `estadio` varchar(100) DEFAULT NULL,
  `fecha_fundacion` date DEFAULT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES ('Athletic Club','Bilbao','San Mamés','1898-01-01'),('Atlético de Madrid','Madrid','Cívitas Metropolitano','1903-04-26'),('CA Osasuna','Pamplona','El Sadar','1920-10-24'),('CD Leganés','Leganés','Butarque','1928-06-23'),('Cordoba Fc','Cordoba','El Arcangel','2026-05-02'),('Deportivo Alavés','Vitoria','Mendizorroza','1921-01-23'),('FC Barcelona','Barcelona','Spotify Camp Nou','1899-11-29'),('Getafe CF','Getafe','Coliseum','1983-07-08'),('Girona FC','Girona','Montilivi','1930-07-23'),('Malaga','Malaga','Rosalada','2026-05-05'),('Rayo Vallecano','Madrid','Vallecas','1924-05-29'),('RC Celta','Vigo','Abanca-Balaídos','1923-08-23'),('RCD Espanyol','Barcelona','Stage Front Stadium','1900-10-28'),('RCD Mallorca','Palma','Visit Mallorca Estadi','1916-03-05'),('Real Betis','Sevilla','Benito Villamarín','1907-09-12'),('Real Madrid','Madrid','Santiago Bernabéu','1902-03-06'),('Real Sociedad','San Sebastián','Reale Arena','1909-09-07'),('Real Valladolid','Valladolid','José Zorrilla','1928-06-20'),('Sevilla FC','Sevilla','Ramón Sánchez-Pizjuán','1890-01-25'),('UD Las Palmas','Las Palmas','Estadio de Gran Canaria','1949-08-22'),('Valencia CF','Valencia','Mestalla','1919-03-18'),('Villarreal CF','Villarreal','Estadio de la Cerámica','1923-03-10');
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juega`
--

DROP TABLE IF EXISTS `juega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `juega` (
  `equipo_nombre` varchar(100) NOT NULL,
  `id_partido` int NOT NULL,
  PRIMARY KEY (`equipo_nombre`,`id_partido`),
  KEY `id_partido` (`id_partido`),
  CONSTRAINT `juega_ibfk_1` FOREIGN KEY (`equipo_nombre`) REFERENCES `equipo` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `juega_ibfk_2` FOREIGN KEY (`id_partido`) REFERENCES `partido` (`id_partido`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juega`
--

LOCK TABLES `juega` WRITE;
/*!40000 ALTER TABLE `juega` DISABLE KEYS */;
INSERT INTO `juega` VALUES ('Athletic Club',191),('Villarreal CF',191),('Atlético de Madrid',192),('Real Madrid',192),('FC Barcelona',193),('Real Betis',193),('Atlético de Madrid',194),('Villarreal CF',194),('Athletic Club',195),('FC Barcelona',195),('Real Betis',196),('Real Madrid',196),('FC Barcelona',197),('Villarreal CF',197),('Atlético de Madrid',198),('Real Betis',198),('Athletic Club',199),('Real Madrid',199),('Real Betis',200),('Villarreal CF',200),('FC Barcelona',201),('Real Madrid',201),('Athletic Club',202),('Atlético de Madrid',202),('Real Madrid',203),('Villarreal CF',203),('Athletic Club',204),('Real Betis',204),('Atlético de Madrid',205),('FC Barcelona',205),('Athletic Club',206),('Villarreal CF',206),('Atlético de Madrid',207),('Real Madrid',207),('FC Barcelona',208),('Real Betis',208),('Atlético de Madrid',209),('Villarreal CF',209),('Athletic Club',210),('FC Barcelona',210),('Real Betis',211),('Real Madrid',211),('FC Barcelona',212),('Villarreal CF',212),('Atlético de Madrid',213),('Real Betis',213),('Athletic Club',214),('Real Madrid',214),('FC Barcelona',215),('Malaga',215),('FC Barcelona',216),('Malaga',216);
/*!40000 ALTER TABLE `juega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador`
--

DROP TABLE IF EXISTS `jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jugador` (
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  `posicion` varchar(50) DEFAULT NULL,
  `dorsal` int DEFAULT NULL,
  `equipo_nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`dni`),
  KEY `equipo_nombre` (`equipo_nombre`),
  CONSTRAINT `jugador_ibfk_1` FOREIGN KEY (`equipo_nombre`) REFERENCES `equipo` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES ('12345678A','Moleiro',21,'Centrocampista',20,'Villarreal CF'),('23456791P','Joan García',24,'Portero',1,'FC Barcelona'),('87654321A','Dani',19,'Delantero',21,'FC Barcelona'),('ALA01','Sivera',29,'Portero',1,'Deportivo Alavés'),('ALA02','Tenaglia',30,'Defensa',14,'Deportivo Alavés'),('ALA03','Abqar',27,'Defensa',5,'Deportivo Alavés'),('ALA04','Sedlar',34,'Defensa',4,'Deportivo Alavés'),('ALA05','Manu Sánchez',25,'Defensa',3,'Deportivo Alavés'),('ALA06','Guevara',29,'Centrocampista',6,'Deportivo Alavés'),('ALA07','Blanco',26,'Centrocampista',8,'Deportivo Alavés'),('ALA08','Guridi',31,'Centrocampista',18,'Deportivo Alavés'),('ALA09','Carlos Vicente',27,'Delantero',17,'Deportivo Alavés'),('ALA10','Stoichkov',32,'Delantero',19,'Deportivo Alavés'),('ALA11','Toni Martínez',28,'Delantero',11,'Deportivo Alavés'),('ATH01','Unai Simón',28,'Portero',1,'Athletic Club'),('ATH02','De Marcos',37,'Defensa',18,'Athletic Club'),('ATH03','Vivian',26,'Defensa',3,'Athletic Club'),('ATH04','Yeray',31,'Defensa',5,'Athletic Club'),('ATH05','Yuri',36,'Defensa',17,'Athletic Club'),('ATH06','Galarreta',32,'Centrocampista',16,'Athletic Club'),('ATH07','Sancet',26,'Centrocampista',8,'Athletic Club'),('ATH08','Prados',25,'Centrocampista',24,'Athletic Club'),('ATH09','Iñaki Williams',31,'Delantero',9,'Athletic Club'),('ATH10','Guruzeta',29,'Delantero',12,'Athletic Club'),('ATH11','Nico Williams',23,'Delantero',11,'Athletic Club'),('ATM01','Oblak',33,'Portero',13,'Atlético de Madrid'),('ATM02','Nahuel Molina',28,'Defensa',16,'Atlético de Madrid'),('ATM03','Le Normand',29,'Defensa',24,'Atlético de Madrid'),('ATM04','Giménez',31,'Defensa',2,'Atlético de Madrid'),('ATM05','Lino',26,'Defensa',12,'Atlético de Madrid'),('ATM06','De Paul',32,'Centrocampista',5,'Atlético de Madrid'),('ATM07','Koke',34,'Centrocampista',6,'Atlético de Madrid'),('ATM08','Gallagher',26,'Centrocampista',4,'Atlético de Madrid'),('ATM09','Llorente',31,'Centrocampista',14,'Atlético de Madrid'),('ATM11','Julián Álvarez',26,'Delantero',19,'Atlético de Madrid'),('BET01','Rui Silva',32,'Portero',13,'Real Betis'),('BET02','Sabaly',33,'Defensa',23,'Real Betis'),('BET03','Bartra',35,'Defensa',15,'Real Betis'),('BET04','Natan',25,'Defensa',6,'Real Betis'),('BET05','Ricardo Rodríguez',33,'Defensa',12,'Real Betis'),('BET06','Marc Roca',29,'Centrocampista',21,'Real Betis'),('BET07','Johnny Cardoso',24,'Centrocampista',4,'Real Betis'),('BET08','Isco',34,'Centrocampista',22,'Real Betis'),('BET09','Fornals',30,'Centrocampista',18,'Real Betis'),('BET10','Vitor Roque',21,'Delantero',8,'Real Betis'),('BET11','Chimy Ávila',32,'Delantero',9,'Real Betis'),('CEL01','Guaita',39,'Portero',13,'RC Celta'),('CEL02','Mingueza',27,'Defensa',3,'RC Celta'),('CEL03','Starfelt',31,'Defensa',2,'RC Celta'),('CEL04','Marcos Alonso',35,'Defensa',20,'RC Celta'),('CEL05','Hugo Álvarez',22,'Defensa',33,'RC Celta'),('CEL06','Fran Beltrán',27,'Centrocampista',8,'RC Celta'),('CEL07','Ilaix Moriba',23,'Centrocampista',14,'RC Celta'),('CEL08','Williot Swedberg',22,'Centrocampista',19,'RC Celta'),('CEL09','Iago Aspas',38,'Delantero',10,'RC Celta'),('CEL10','Borja Iglesias',33,'Delantero',7,'RC Celta'),('CEL11','Bamba',30,'Delantero',17,'RC Celta'),('ESP01','Joan García',25,'Portero',1,'RCD Espanyol'),('ESP02','Omar El Hilali',22,'Defensa',23,'RCD Espanyol'),('ESP03','Kumbulla',26,'Defensa',4,'RCD Espanyol'),('ESP04','Cabrera',34,'Defensa',6,'RCD Espanyol'),('ESP05','Carlos Romero',24,'Defensa',14,'RCD Espanyol'),('ESP06','Alex Král',28,'Centrocampista',20,'RCD Espanyol'),('ESP07','Gragera',26,'Centrocampista',15,'RCD Espanyol'),('ESP08','Edu Expósito',29,'Centrocampista',10,'RCD Espanyol'),('ESP09','Jofre Carreras',24,'Delantero',17,'RCD Espanyol'),('ESP10','Puado',28,'Delantero',7,'RCD Espanyol'),('ESP11','Veliz',22,'Delantero',9,'RCD Espanyol'),('FCB02','Koundé',27,'Defensa',23,'FC Barcelona'),('FCB03','Araújo',27,'Defensa',4,'FC Barcelona'),('FCB04','Cubarsí',19,'Defensa',2,'FC Barcelona'),('FCB05','Balde',22,'Defensa',3,'FC Barcelona'),('FCB06','Gavi',21,'Centrocampista',6,'FC Barcelona'),('FCB07','Pedri',23,'Centrocampista',8,'FC Barcelona'),('FCB08','Fermín López',23,'Centrocampista',16,'FC Barcelona'),('FCB09','Lamine Yamal',18,'Delantero',19,'FC Barcelona'),('FCB10','Lewandowski',37,'Delantero',9,'FC Barcelona'),('FCB11','Raphinha',29,'Delantero',11,'FC Barcelona'),('GET01','David Soria',33,'Portero',13,'Getafe CF'),('GET02','Djené',34,'Defensa',2,'Getafe CF'),('GET03','Alderete',29,'Defensa',15,'Getafe CF'),('GET04','Diego Rico',33,'Defensa',16,'Getafe CF'),('GET05','Juan Iglesias',27,'Defensa',21,'Getafe CF'),('GET06','Milla',31,'Centrocampista',5,'Getafe CF'),('GET07','Mauro Arambarri',30,'Centrocampista',8,'Getafe CF'),('GET08','Uche',23,'Centrocampista',6,'Getafe CF'),('GET09','Carles Pérez',28,'Delantero',17,'Getafe CF'),('GET10','Álex Sola',27,'Delantero',20,'Getafe CF'),('GET11','Borja Mayoral',29,'Delantero',19,'Getafe CF'),('GIR01','Gazzaniga',34,'Portero',13,'Girona FC'),('GIR02','Arnau Martínez',23,'Defensa',4,'Girona FC'),('GIR03','David López',36,'Defensa',5,'Girona FC'),('GIR04','Blind',36,'Defensa',17,'Girona FC'),('GIR05','Miguel Gutiérrez',24,'Defensa',3,'Girona FC'),('GIR06','Yangel Herrera',28,'Centrocampista',21,'Girona FC'),('GIR07','Oriol Romeu',34,'Centrocampista',14,'Girona FC'),('GIR08','Iván Martín',27,'Centrocampista',23,'Girona FC'),('GIR09','Tsygankov',28,'Delantero',8,'Girona FC'),('GIR10','Abel Ruiz',26,'Delantero',9,'Girona FC'),('GIR11','Bryan Gil',25,'Delantero',20,'Girona FC'),('LEG01','Dmitrovic',34,'Portero',13,'CD Leganés'),('LEG02','Rosier',29,'Defensa',2,'CD Leganés'),('LEG03','Jorge Sáenz',29,'Defensa',4,'CD Leganés'),('LEG04','Sergio González',34,'Defensa',6,'CD Leganés'),('LEG05','Javi Hernández',28,'Defensa',20,'CD Leganés'),('LEG06','Renato Tapia',30,'Centrocampista',5,'CD Leganés'),('LEG07','Neyou',29,'Centrocampista',17,'CD Leganés'),('LEG08','Munir',30,'Centrocampista',23,'CD Leganés'),('LEG09','Dani Raba',30,'Delantero',10,'CD Leganés'),('LEG10','Juan Cruz',26,'Delantero',11,'CD Leganés'),('LEG11','Haller',31,'Delantero',18,'CD Leganés'),('LPA01','Cillessen',37,'Portero',1,'UD Las Palmas'),('LPA02','Viti Rozada',28,'Defensa',18,'UD Las Palmas'),('LPA03','Alex Suárez',32,'Defensa',4,'UD Las Palmas'),('LPA04','McKenna',29,'Defensa',15,'UD Las Palmas'),('LPA05','Álex Muñoz',31,'Defensa',23,'UD Las Palmas'),('LPA06','Kirian Rodríguez',30,'Centrocampista',20,'UD Las Palmas'),('LPA07','Essugo',21,'Centrocampista',29,'UD Las Palmas'),('LPA08','Moleiro',22,'Centrocampista',10,'UD Las Palmas'),('LPA09','Sandro',30,'Delantero',9,'UD Las Palmas'),('LPA10','Fabio Silva',23,'Delantero',37,'UD Las Palmas'),('LPA11','McBurnie',29,'Delantero',16,'UD Las Palmas'),('MAL01','Dominik Greif',29,'Portero',1,'RCD Mallorca'),('MAL02','Maffeo',28,'Defensa',23,'RCD Mallorca'),('MAL03','Raíllo',34,'Defensa',21,'RCD Mallorca'),('MAL04','Valjent',30,'Defensa',24,'RCD Mallorca'),('MAL05','Mojica',33,'Defensa',22,'RCD Mallorca'),('MAL06','Samú Costa',25,'Centrocampista',12,'RCD Mallorca'),('MAL07','Mascarell',33,'Centrocampista',5,'RCD Mallorca'),('MAL08','Darder',32,'Centrocampista',10,'RCD Mallorca'),('MAL09','Asano',31,'Delantero',11,'RCD Mallorca'),('MAL10','Dani Rodríguez',38,'Delantero',14,'RCD Mallorca'),('MAL11','Muriqi',32,'Delantero',7,'RCD Mallorca'),('OSA01','Sergio Herrera',32,'Portero',1,'CA Osasuna'),('OSA02','Areso',26,'Defensa',12,'CA Osasuna'),('OSA03','Catena',31,'Defensa',24,'CA Osasuna'),('OSA04','Boyomo',24,'Defensa',22,'CA Osasuna'),('OSA05','Abel Bretones',25,'Defensa',23,'CA Osasuna'),('OSA06','Torró',31,'Centrocampista',6,'CA Osasuna'),('OSA07','Moncayola',28,'Centrocampista',7,'CA Osasuna'),('OSA08','Aimar Oroz',24,'Centrocampista',10,'CA Osasuna'),('OSA09','Rubén García',32,'Delantero',14,'CA Osasuna'),('OSA10','Bryan Zaragoza',24,'Delantero',19,'CA Osasuna'),('OSA11','Budimir',34,'Delantero',17,'CA Osasuna'),('RAY01','Batalla',30,'Portero',13,'Rayo Vallecano'),('RAY02','Ratiu',27,'Defensa',2,'Rayo Vallecano'),('RAY03','Lejeune',35,'Defensa',24,'Rayo Vallecano'),('RAY04','Mumin',27,'Defensa',16,'Rayo Vallecano'),('RAY05','Chavarría',28,'Defensa',3,'Rayo Vallecano'),('RAY06','Óscar Valentín',32,'Centrocampista',23,'Rayo Vallecano'),('RAY07','Unai López',30,'Centrocampista',17,'Rayo Vallecano'),('RAY08','James Rodríguez',34,'Centrocampista',10,'Rayo Vallecano'),('RAY09','Isi Palazón',31,'Delantero',7,'Rayo Vallecano'),('RAY10','Álvaro García',33,'Delantero',18,'Rayo Vallecano'),('RAY11','Camello',25,'Delantero',14,'Rayo Vallecano'),('RM01','Courtois',33,'Portero',1,'Real Madrid'),('RM02','Carvajal',34,'Defensa',2,'Real Madrid'),('RM03','Militao',28,'Defensa',3,'Real Madrid'),('RM04','Rüdiger',33,'Defensa',22,'Real Madrid'),('RM05','Mendy',30,'Defensa',23,'Real Madrid'),('RM06','Valverde',27,'Centrocampista',8,'Real Madrid'),('RM07','Tchouaméni',26,'Centrocampista',14,'Real Madrid'),('RM08','Bellingham',22,'Centrocampista',5,'Real Madrid'),('RM09','Rodrygo',25,'Delantero',11,'Real Madrid'),('RM10','Mbappé',27,'Delantero',9,'Real Madrid'),('RM11','Vinícius Jr',25,'Delantero',7,'Real Madrid'),('RSO01','Remiro',31,'Portero',1,'Real Sociedad'),('RSO02','Traoré',34,'Defensa',18,'Real Sociedad'),('RSO03','Zubeldia',29,'Defensa',5,'Real Sociedad'),('RSO04','Aguerd',30,'Defensa',21,'Real Sociedad'),('RSO05','Javi López',24,'Defensa',23,'Real Sociedad'),('RSO06','Zubimendi',27,'Centrocampista',4,'Real Sociedad'),('RSO07','Brais Méndez',29,'Centrocampista',23,'Real Sociedad'),('RSO08','Sergio Gómez',25,'Centrocampista',17,'Real Sociedad'),('RSO09','Kubo',24,'Delantero',14,'Real Sociedad'),('RSO10','Oyarzabal',29,'Delantero',10,'Real Sociedad'),('RSO11','Becker',31,'Delantero',11,'Real Sociedad'),('SFC01','Nyland',35,'Portero',13,'Sevilla FC'),('SFC02','Carmona',23,'Defensa',32,'Sevilla FC'),('SFC03','Badé',26,'Defensa',22,'Sevilla FC'),('SFC04','Marcao',30,'Defensa',23,'Sevilla FC'),('SFC05','Barco',21,'Defensa',19,'Sevilla FC'),('SFC06','Gudelj',34,'Centrocampista',6,'Sevilla FC'),('SFC07','Sow',29,'Centrocampista',20,'Sevilla FC'),('SFC08','Lokonga',26,'Centrocampista',12,'Sevilla FC'),('SFC09','Lukebakio',28,'Delantero',11,'Sevilla FC'),('SFC10','Isaac Romero',26,'Delantero',20,'Sevilla FC'),('SFC11','Ejuke',28,'Delantero',21,'Sevilla FC'),('VAD01','Karl Hein',24,'Portero',1,'Real Valladolid'),('VAD02','Luis Pérez',31,'Defensa',2,'Real Valladolid'),('VAD03','Juma Bah',20,'Defensa',15,'Real Valladolid'),('VAD04','David Torres',23,'Defensa',5,'Real Valladolid'),('VAD05','Lucas Rosa',26,'Defensa',22,'Real Valladolid'),('VAD06','Stanko Juric',29,'Centrocampista',8,'Real Valladolid'),('VAD07','Kike Pérez',29,'Centrocampista',4,'Real Valladolid'),('VAD08','Amallah',29,'Centrocampista',21,'Real Valladolid'),('VAD09','Raúl Moro',23,'Delantero',11,'Real Valladolid'),('VAD10','Amath',29,'Delantero',23,'Real Valladolid'),('VAD11','Sylla',32,'Delantero',7,'Real Valladolid'),('VCF01','Mamardashvili',25,'Portero',25,'Valencia CF'),('VCF02','Thierry Correia',27,'Defensa',12,'Valencia CF'),('VCF03','Mosquera',21,'Defensa',3,'Valencia CF'),('VCF04','Tárrega',24,'Defensa',15,'Valencia CF'),('VCF05','Gayà',31,'Defensa',14,'Valencia CF'),('VCF06','Pepelu',27,'Centrocampista',18,'Valencia CF'),('VCF07','Javi Guerra',23,'Centrocampista',10,'Valencia CF'),('VCF08','Almeida',26,'Centrocampista',10,'Valencia CF'),('VCF09','Diego López',24,'Delantero',16,'Valencia CF'),('VCF10','Hugo Duro',26,'Delantero',9,'Valencia CF'),('VCF11','Luis Rioja',32,'Delantero',22,'Valencia CF'),('VIL01','Diego Conde',27,'Portero',13,'Villarreal CF'),('VIL02','Kiko Femenía',35,'Defensa',17,'Villarreal CF'),('VIL03','Albiol',40,'Defensa',3,'Villarreal CF'),('VIL05','Cardona',25,'Defensa',23,'Villarreal CF'),('VIL06','Parejo',37,'Centrocampista',10,'Villarreal CF'),('VIL07','Comesaña',29,'Centrocampista',14,'Villarreal CF'),('VIL08','Baena',24,'Centrocampista',16,'Villarreal CF'),('VIL09','Yeremy Pino',23,'Delantero',21,'Villarreal CF'),('VIL10','Ayoze Pérez',32,'Delantero',22,'Villarreal CF'),('VIL11','Gerard Moreno',34,'Delantero',7,'Villarreal CF');
/*!40000 ALTER TABLE `jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participa`
--

DROP TABLE IF EXISTS `participa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participa` (
  `equipo_nombre` varchar(100) NOT NULL,
  `competicion_nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`equipo_nombre`,`competicion_nombre`),
  KEY `competicion_nombre` (`competicion_nombre`),
  CONSTRAINT `participa_ibfk_1` FOREIGN KEY (`equipo_nombre`) REFERENCES `equipo` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `participa_ibfk_2` FOREIGN KEY (`competicion_nombre`) REFERENCES `competicion` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participa`
--

LOCK TABLES `participa` WRITE;
/*!40000 ALTER TABLE `participa` DISABLE KEYS */;
INSERT INTO `participa` VALUES ('Athletic Club','Champions'),('Atlético de Madrid','Champions'),('FC Barcelona','Champions'),('Real Betis','Champions'),('Real Madrid','Champions'),('Villarreal CF','Champions'),('FC Barcelona','Ligue A'),('Getafe CF','Ligue A'),('FC Barcelona','Marta'),('Malaga','Marta');
/*!40000 ALTER TABLE `participa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partido` (
  `id_partido` int NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `goles_local` int DEFAULT NULL,
  `goles_visitante` int DEFAULT NULL,
  `competicion_nombre` varchar(100) NOT NULL,
  `equipo_local` varchar(100) DEFAULT NULL,
  `equipo_visitante` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_partido`),
  KEY `competicion_nombre` (`competicion_nombre`),
  KEY `fk_equipo_local` (`equipo_local`),
  KEY `fk_equipo_visitante` (`equipo_visitante`),
  CONSTRAINT `fk_equipo_local` FOREIGN KEY (`equipo_local`) REFERENCES `equipo` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_equipo_visitante` FOREIGN KEY (`equipo_visitante`) REFERENCES `equipo` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `partido_ibfk_1` FOREIGN KEY (`competicion_nombre`) REFERENCES `competicion` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (191,'2026-05-21',0,2,'Champions',NULL,NULL),(192,'2026-05-21',3,3,'Champions',NULL,NULL),(193,'2026-05-21',0,2,'Champions',NULL,NULL),(194,'2026-05-21',2,0,'Champions',NULL,NULL),(195,'2026-05-21',1,2,'Champions',NULL,NULL),(196,'2026-05-21',3,0,'Champions',NULL,NULL),(197,'2026-05-21',3,1,'Champions',NULL,NULL),(198,'2026-05-21',2,3,'Champions',NULL,NULL),(199,'2026-05-21',0,0,'Champions',NULL,NULL),(200,'2026-05-21',3,0,'Champions',NULL,NULL),(201,'2026-05-21',1,2,'Champions',NULL,NULL),(202,'2026-05-21',2,1,'Champions',NULL,NULL),(203,'2026-05-21',3,4,'Champions',NULL,NULL),(204,'2026-05-21',4,3,'Champions',NULL,NULL),(205,'2026-05-21',0,0,'Champions',NULL,NULL),(206,'2026-05-21',1,4,'Champions',NULL,NULL),(207,'2026-05-21',2,2,'Champions',NULL,NULL),(208,'2026-05-21',3,1,'Champions',NULL,NULL),(209,'2026-05-21',1,3,'Champions',NULL,NULL),(210,'2026-05-21',2,1,'Champions',NULL,NULL),(211,'2026-05-21',0,2,'Champions',NULL,NULL),(212,'2026-05-21',0,2,'Champions',NULL,NULL),(213,'2026-05-21',3,4,'Champions',NULL,NULL),(214,'2026-05-21',3,4,'Champions',NULL,NULL),(215,'2026-05-21',3,0,'Marta',NULL,NULL),(216,'2026-05-21',1,3,'Marta',NULL,NULL);
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-22 12:59:12
