-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.13-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for parking
CREATE DATABASE IF NOT EXISTS `parking` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `parking`;

-- Dumping structure for table parking.company
CREATE TABLE IF NOT EXISTS `company` (
  `companyid` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyname` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `phonenumber` varchar(20) NOT NULL,
  `fee_motorcycle` bigint(20) NOT NULL DEFAULT 0,
  `fee_others` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`companyid`),
  UNIQUE KEY `companyname` (`companyname`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.contract
CREATE TABLE IF NOT EXISTS `contract` (
  `contractid` bigint(20) NOT NULL AUTO_INCREMENT,
  `time_created` text NOT NULL,
  `time_end` text NOT NULL,
  `cardid` int(11) NOT NULL,
  `licenseplate` varchar(25) NOT NULL,
  `customerid` bigint(20) NOT NULL,
  PRIMARY KEY (`contractid`),
  KEY `Contract_fk0` (`cardid`),
  KEY `Contract_fk1` (`licenseplate`),
  KEY `Contract_fk2` (`customerid`),
  CONSTRAINT `Contract_fk1` FOREIGN KEY (`licenseplate`) REFERENCES `vehicle` (`licenseplate`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `customerid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `phonenumber` varchar(25) DEFAULT NULL,
  `email` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `time_created` text NOT NULL,
  PRIMARY KEY (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `employeeid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `gender` varchar(10) NOT NULL DEFAULT 'Male',
  `birthdate` varchar(30) NOT NULL,
  `phonenumber` varchar(25) NOT NULL,
  `email` text NOT NULL,
  `address` text DEFAULT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `loginstatus` tinyint(4) NOT NULL DEFAULT 0,
  `companyid` bigint(20) NOT NULL,
  `parkingid` bigint(20) NOT NULL,
  `roleid` int(11) NOT NULL,
  PRIMARY KEY (`employeeid`),
  UNIQUE KEY `username` (`username`) USING HASH,
  KEY `FK_employee_role` (`roleid`),
  KEY `FK_employee_company` (`companyid`),
  KEY `FK_employee_parking` (`parkingid`),
  CONSTRAINT `FK_employee_company` FOREIGN KEY (`companyid`) REFERENCES `company` (`companyid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_employee_parking` FOREIGN KEY (`parkingid`) REFERENCES `parking` (`parkingid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_employee_role` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.history
CREATE TABLE IF NOT EXISTS `history` (
  `historyid` bigint(20) NOT NULL AUTO_INCREMENT,
  `img_in` text DEFAULT NULL,
  `img_out` text DEFAULT NULL,
  `img_lp_in` text DEFAULT NULL,
  `img_lp_out` text DEFAULT NULL,
  `plate_in` text DEFAULT NULL,
  `plate_out` text DEFAULT NULL,
  `time_in` text DEFAULT NULL,
  `time_out` text DEFAULT NULL,
  `visit_status` tinyint(4) NOT NULL,
  `fee` double DEFAULT 0,
  `doubt` tinyint(4) DEFAULT 0,
  `cardid` varchar(20) NOT NULL,
  PRIMARY KEY (`historyid`),
  KEY `FK_history_guest_parkingcard` (`cardid`),
  CONSTRAINT `FK_history_guest_parkingcard` FOREIGN KEY (`cardid`) REFERENCES `parkingcard` (`cardid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.parking
CREATE TABLE IF NOT EXISTS `parking` (
  `parkingid` bigint(20) NOT NULL AUTO_INCREMENT,
  `parkname` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `slot` int(11) NOT NULL,
  `no_current_vehicle` int(11) NOT NULL DEFAULT 0,
  `companyid` bigint(20) NOT NULL,
  PRIMARY KEY (`parkingid`),
  UNIQUE KEY `parkname` (`parkname`),
  KEY `FK_parking_company` (`companyid`),
  CONSTRAINT `FK_parking_company` FOREIGN KEY (`companyid`) REFERENCES `company` (`companyid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.parkingcard
CREATE TABLE IF NOT EXISTS `parkingcard` (
  `cardid` varchar(20) NOT NULL,
  `companyid` bigint(20) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`cardid`),
  KEY `FK_parkingcard_parking` (`companyid`) USING BTREE,
  CONSTRAINT `FK_parkingcard_company` FOREIGN KEY (`companyid`) REFERENCES `company` (`companyid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.role
CREATE TABLE IF NOT EXISTS `role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `roleno` int(11) NOT NULL,
  `rolename` varchar(15) NOT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`roleid`),
  UNIQUE KEY `roleno` (`roleno`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table parking.vehicle
CREATE TABLE IF NOT EXISTS `vehicle` (
  `licenseplate` varchar(25) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `model` varchar(25) DEFAULT NULL,
  `color` varchar(15) DEFAULT NULL,
  `customerid` bigint(20) NOT NULL,
  PRIMARY KEY (`licenseplate`),
  KEY `FK_vehicle_customer` (`customerid`),
  CONSTRAINT `FK_vehicle_customer` FOREIGN KEY (`customerid`) REFERENCES `customer` (`customerid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
