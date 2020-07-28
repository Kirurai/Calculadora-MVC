/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.18 : Database - db_programacion2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_programacion2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_programacion2`;

/*Table structure for table `alumno` */

DROP TABLE IF EXISTS `alumno`;

CREATE TABLE `alumno` (
  `alu_dni` int(10) unsigned NOT NULL,
  `alu_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `alu_apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alu_fec_nac` date NOT NULL,
  `alu_domicilio` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `alu_telefono` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `alu_insc_cod` int(10) NOT NULL,
  PRIMARY KEY (`alu_dni`),
  KEY `alu_insc_cod` (`alu_insc_cod`),
  CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`alu_insc_cod`) REFERENCES `inscripcion` (`insc_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `alumno` */

insert  into `alumno`(`alu_dni`,`alu_nombre`,`alu_apellido`,`alu_fec_nac`,`alu_domicilio`,`alu_telefono`,`alu_insc_cod`) values 
(41769024,'Maurizio','Miño','1992-12-23','','',103),
(789564689,'jfsiofje','yopjao','2020-06-17','','',103);

/*Table structure for table `carrera` */

DROP TABLE IF EXISTS `carrera`;

CREATE TABLE `carrera` (
  `car_cod` int(10) NOT NULL,
  `car_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `car_duracion` int(5) NOT NULL,
  PRIMARY KEY (`car_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `carrera` */

insert  into `carrera`(`car_cod`,`car_nombre`,`car_duracion`) values 
(4,'120',2),
(6,'1',40),
(7,'54145651',727),
(1010,'Como vivir como un hobbit',50);

/*Table structure for table `cursado` */

DROP TABLE IF EXISTS `cursado`;

CREATE TABLE `cursado` (
  `cur_alu_dni` int(10) unsigned NOT NULL,
  `cur_mat_cod` int(10) unsigned NOT NULL,
  `cur_nota` int(2) unsigned NOT NULL,
  PRIMARY KEY (`cur_alu_dni`,`cur_mat_cod`),
  KEY `cur_mat_cod` (`cur_mat_cod`),
  CONSTRAINT `cursado_ibfk_2` FOREIGN KEY (`cur_alu_dni`) REFERENCES `alumno` (`alu_dni`),
  CONSTRAINT `cursado_ibfk_3` FOREIGN KEY (`cur_mat_cod`) REFERENCES `materia` (`mat_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `cursado` */

insert  into `cursado`(`cur_alu_dni`,`cur_mat_cod`,`cur_nota`) values 
(41769024,1,10);

/*Table structure for table `inscripcion` */

DROP TABLE IF EXISTS `inscripcion`;

CREATE TABLE `inscripcion` (
  `insc_cod` int(10) NOT NULL,
  `insc_nombre` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `insc_fecha` date NOT NULL,
  `insc_car_cod` int(10) NOT NULL,
  PRIMARY KEY (`insc_cod`),
  KEY `insc_car_cod` (`insc_car_cod`),
  CONSTRAINT `inscripcion_ibfk_1` FOREIGN KEY (`insc_car_cod`) REFERENCES `carrera` (`car_cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `inscripcion` */

insert  into `inscripcion`(`insc_cod`,`insc_nombre`,`insc_fecha`,`insc_car_cod`) values 
(0,'Mecatrónica','2020-06-17',4),
(103,'as Mecatrónica','2020-06-17',4);

/*Table structure for table `materia` */

DROP TABLE IF EXISTS `materia`;

CREATE TABLE `materia` (
  `mat_cod` int(10) unsigned NOT NULL,
  `mat_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `mat_profe_dni` int(10) unsigned NOT NULL,
  PRIMARY KEY (`mat_cod`),
  KEY `mat_profe_dni` (`mat_profe_dni`),
  CONSTRAINT `materia_ibfk_1` FOREIGN KEY (`mat_profe_dni`) REFERENCES `profesor` (`prof_dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `materia` */

insert  into `materia`(`mat_cod`,`mat_nombre`,`mat_profe_dni`) values 
(1,'Programación 2',5298),
(502,'Uso de la fuerza',5298);

/*Table structure for table `profesor` */

DROP TABLE IF EXISTS `profesor`;

CREATE TABLE `profesor` (
  `prof_dni` int(10) unsigned NOT NULL,
  `prof_nombre` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `prof_apellido` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `prof_fec_nac` date NOT NULL,
  `prof_domicilio` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `prof_telefono` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`prof_dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `profesor` */

insert  into `profesor`(`prof_dni`,`prof_nombre`,`prof_apellido`,`prof_fec_nac`,`prof_domicilio`,`prof_telefono`) values 
(5298,'Mace','Windu','2020-06-17','',''),
(4176904,'gssdgsdfzg','dzgzdsgreas','2020-06-06','','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
