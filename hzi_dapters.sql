-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.13-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for sormas_
CREATE DATABASE IF NOT EXISTS `sormas_` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sormas_`;

-- Dumping structure for table sormas_.optionfiles
CREATE TABLE IF NOT EXISTS `optionfiles` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `server` varchar(50) DEFAULT NULL,
  `case_data` char(50) DEFAULT NULL,
  `sample_data` varchar(50) DEFAULT NULL,
  `contact_data` varchar(50) DEFAULT NULL,
  `with_aggregate` varchar(50) DEFAULT NULL,
  `aggregate_only` varchar(50) DEFAULT NULL,
  `r1x` varchar(50) DEFAULT NULL,
  `r1` varchar(50) DEFAULT NULL,
  `installer` varchar(50) DEFAULT NULL,
  `created` varchar(50) DEFAULT NULL,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_.optionfiles: ~0 rows (approximately)
DELETE FROM `optionfiles`;
/*!40000 ALTER TABLE `optionfiles` DISABLE KEYS */;
INSERT INTO `optionfiles` (`uid`, `server`, `case_data`, `sample_data`, `contact_data`, `with_aggregate`, `aggregate_only`, `r1x`, `r1`, `installer`, `created`, `updated`) VALUES
	(1, 'checked', '', '', '', 'checked', 'checked', 'ad_3', 'hr', 'checked', '2020-09-22 18:52:51', '2020-09-22 18:52:51');
/*!40000 ALTER TABLE `optionfiles` ENABLE KEYS */;

-- Dumping structure for table sormas_.raw_
CREATE TABLE IF NOT EXISTS `raw_` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `name` varchar(512) DEFAULT NULL,
  `shortname` varchar(512) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `path_parent` varchar(200) DEFAULT NULL,
  `level` varchar(50) DEFAULT NULL,
  `geopoint_` varchar(100) DEFAULT NULL,
  `fhiruuid` varchar(100) DEFAULT NULL,
  `fhiruuid_history` varchar(100) DEFAULT NULL,
  `updated_last` timestamp NULL DEFAULT NULL,
  `rec_created` datetime DEFAULT NULL,
  `last_edited` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `synced` int(1) DEFAULT '0',
  PRIMARY KEY (`idx`),
  UNIQUE KEY `Index 2` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_.raw_: ~0 rows (approximately)
DELETE FROM `raw_`;
/*!40000 ALTER TABLE `raw_` DISABLE KEYS */;
/*!40000 ALTER TABLE `raw_` ENABLE KEYS */;

-- Dumping structure for table sormas_.sormas_local
CREATE TABLE IF NOT EXISTS `sormas_local` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL DEFAULT '0',
  `uuid` varchar(50) DEFAULT NULL,
  `namex` varchar(512) NOT NULL DEFAULT '0',
  `changedate` datetime DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `externalid` varchar(50) DEFAULT NULL,
  `ext_cdate` datetime DEFAULT NULL,
  `adapter_cdate` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `approved` varchar(10) DEFAULT NULL,
  `duplicate_with` varchar(100) DEFAULT NULL,
  `resolved_by` varchar(50) DEFAULT NULL,
  `synced` varchar(2) NOT NULL DEFAULT '0',
  UNIQUE KEY `Index 2` (`uuid`),
-- UNIQUE KEY `Duplicate DHIS2 UUID` (`externalid`),
  KEY `Index 1` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_.sormas_local: ~0 rows (approximately)
DELETE FROM `sormas_local`;
/*!40000 ALTER TABLE `sormas_local` DISABLE KEYS */;
/*!40000 ALTER TABLE `sormas_local` ENABLE KEYS */;

-- Dumping structure for table sormas_.source_pair
CREATE TABLE IF NOT EXISTS `source_pair` (
  `uuid` varchar(50) NOT NULL,
  `source_id` varchar(50) DEFAULT NULL,
  `destination_id` varchar(50) DEFAULT NULL,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_.source_pair: ~0 rows (approximately)
DELETE FROM `source_pair`;
/*!40000 ALTER TABLE `source_pair` DISABLE KEYS */;
INSERT INTO `source_pair` (`uuid`, `source_id`, `destination_id`, `updated`) VALUES
	('db065fed-b2bc-11ea-b177-b46bfc273d22', '1iemqa99yqxwy', 'urmuflwms2g4', '2020-06-21 21:14:08');
/*!40000 ALTER TABLE `source_pair` ENABLE KEYS */;

-- Dumping structure for table sormas_.sync_tracker
CREATE TABLE IF NOT EXISTS `sync_tracker` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `json_response` text,
  `status` varchar(10) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `datasource` varchar(50) DEFAULT NULL,
  `dataperiod` varchar(50) DEFAULT NULL,
  `case_specific_detail` varchar(50) DEFAULT NULL,
  `lastupdated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `Index 1` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_.sync_tracker: ~0 rows (approximately)
DELETE FROM `sync_tracker`;
/*!40000 ALTER TABLE `sync_tracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `sync_tracker` ENABLE KEYS */;

-- Dumping structure for table sormas_.transition_parameters
CREATE TABLE IF NOT EXISTS `transition_parameters` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `parm1` varchar(150) DEFAULT NULL,
  `parm2` varchar(150) DEFAULT NULL,
  `parm3` varchar(150) DEFAULT NULL,
  `parmtype` varchar(50) DEFAULT NULL,
  KEY `Index 1` (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_.transition_parameters: ~3 rows (approximately)
DELETE FROM `transition_parameters`;
/*!40000 ALTER TABLE `transition_parameters` DISABLE KEYS */;
INSERT INTO `transition_parameters` (`idx`, `parm1`, `parm2`, `parm3`, `parmtype`) VALUES
	(1, 'CORONAVIRUS', 'Qd77B13U9fc', 'MBVAaUTsYi6', 'dis1'),
	(2, 'CORONAVIRUS', 'Qd77B13U9fc', 'TO8IanhHkHA', 'dis2'),
	(3, 'CORONAVIRUS', 'Qd77B13U9fc', 'tEeMGqyVCVx', 'dis3');
/*!40000 ALTER TABLE `transition_parameters` ENABLE KEYS */;

-- Dumping structure for table sormas_._orgunit
CREATE TABLE IF NOT EXISTS `_orgunit` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) DEFAULT NULL,
  `hl_uid` varchar(50) DEFAULT NULL,
  `dhis_uid` varchar(50) DEFAULT NULL,
  `org_level` varchar(50) DEFAULT NULL,
  `dhis_json` text,
  `dhis_last_modification` varchar(50) DEFAULT NULL,
  `hl_last_sync` varchar(50) DEFAULT NULL,
  `hl_last_sync_status` int(11) DEFAULT NULL,
  `hl_last_sync_by` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_._orgunit: ~0 rows (approximately)
DELETE FROM `_orgunit`;
/*!40000 ALTER TABLE `_orgunit` DISABLE KEYS */;
/*!40000 ALTER TABLE `_orgunit` ENABLE KEYS */;

-- Dumping structure for table sormas_._sources
CREATE TABLE IF NOT EXISTS `_sources` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `desc` varchar(200) DEFAULT NULL,
  `source_dest_switch` varchar(200) DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `Index 2` (`url`),
  KEY `Index 3` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_._sources: ~2 rows (approximately)
DELETE FROM `_sources`;
/*!40000 ALTER TABLE `_sources` DISABLE KEYS */;
INSERT INTO `_sources` (`uid`, `uuid`, `url`, `title`, `desc`, `source_dest_switch`, `status`, `created`, `last_update`) VALUES
	(6, '1iemqa99yqxwy', 'http://dhis:8080', 'DHIS2', '', NULL, 'active', '2020-06-20 07:18:18', '2020-06-20 07:18:18'),
	(8, 'urmuflwms2g4', 'https://training.surveillancegh.org', 'SORMAS', '', NULL, 'active', '2020-06-21 21:13:51', '2020-06-21 21:13:51');
/*!40000 ALTER TABLE `_sources` ENABLE KEYS */;

-- Dumping structure for table sormas_._useradmin
CREATE TABLE IF NOT EXISTS `_useradmin` (
  `uid` int(11) DEFAULT NULL,
  `namex` varchar(50) DEFAULT NULL,
  `passw` varchar(50) DEFAULT NULL,
  `fullname` varchar(50) DEFAULT NULL,
  `lastlogin` timestamp NULL DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  `rolex` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table sormas_._useradmin: ~0 rows (approximately)
DELETE FROM `_useradmin`;
/*!40000 ALTER TABLE `_useradmin` DISABLE KEYS */;
/*!40000 ALTER TABLE `_useradmin` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
