CREATE DATABASE `routinelog` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci

##user access log
CREATE TABLE `user_access_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BizTag` varchar(64) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  `AccessText` text,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8