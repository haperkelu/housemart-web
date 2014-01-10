DROP DATABASE IF EXISTS admin;
create DATABASE admin;
ALTER DATABASE `admin` CHARACTER SET utf8 COLLATE utf8_general_ci;

##账号
CREATE TABLE `account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LoginName` varchar(64)  DEFAULT NULL,
  `Password` varchar(512) DEFAULT NULL,
  `Name` varchar(64)  DEFAULT NULL,
  `Gender` bit(1) DEFAULT NULL,
  `IdentityID` varchar(128)  DEFAULT NULL,
  `ContactInfo1` varchar(128)  DEFAULT NULL,
  `ContactInfo2` varchar(128)  DEFAULT NULL,
  `WeiXin` varchar(128)  DEFAULT NULL,
  `WeiXinJoined` tinyint(1) NOT NULL DEFAULT 0,
  `EmergencyName` varchar(128)  DEFAULT NULL,
  `EmergencyContact` varchar(128)  DEFAULT NULL,
  `PositionType` varchar(64)  DEFAULT NULL,
  `Company` varchar(64)  DEFAULT NULL,
  `CompanyAddress` varchar(512)  DEFAULT NULL,
  `ManagerID` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `PicURL` varchar(1024) DEFAULT NULL,
  `IDURL` varchar(1024) DEFAULT NULL,
  `CardURL` varchar(1024) DEFAULT NULL,
  `PicLocked` tinyint(1) NOT NULL DEFAULT 0,
  `IDLocked` tinyint(1) NOT NULL DEFAULT 0,
  `CardLocked` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 

##账号分配小区对应关系
CREATE TABLE `account_residence_relationship` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) DEFAULT NULL,
  `ResidenceID` int(11) DEFAULT NULL,
  `ResidenceIDType` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0: 小区, 1: 板块, 2: 区域',
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##注销账号
CREATE TABLE `account_revoke` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##角色
CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(64) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##
CREATE TABLE `account_role_relationship` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) DEFAULT NULL,
  `RoleID` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##权限
CREATE TABLE `privilege` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(64) DEFAULT NULL,
  `Resource` text,
  `Operation` varchar(512) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Memo` text,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##
CREATE TABLE `role_privilege_relationship` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RoleID` int(11) DEFAULT NULL,
  `PrivilegeID` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;