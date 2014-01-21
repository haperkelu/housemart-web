DROP DATABASE IF EXISTS basedata;
create DATABASE basedata;
ALTER DATABASE `basedata` CHARACTER SET utf8 COLLATE utf8_general_ci;

##region
CREATE TABLE `region` (
  `RegionID` double NOT NULL AUTO_INCREMENT,
  `Name` varchar(192) DEFAULT NULL,
  `CityID` double DEFAULT NULL,
  `ParentID` double DEFAULT NULL,
  `Level` tinyint(4) DEFAULT NULL,
  `Description` blob,
  `AddTime` datetime DEFAULT NULL,
  `URL` varchar(384) DEFAULT NULL,
  `NickName` varchar(384) DEFAULT NULL,
  PRIMARY KEY (`RegionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##residence
CREATE TABLE `residence` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RegionID` double DEFAULT NULL,
  `Name` varchar(384) DEFAULT NULL,
  `AliasName` varchar(384) DEFAULT NULL,
  `PinyinName` varchar(1024) DEFAULT NULL,
  `FirstPinyin` varchar(45) DEFAULT NULL,
  `Avg` decimal(11,0) DEFAULT NULL,
  `Address` varchar(1536) DEFAULT NULL,
  `HeadCount` varchar(384) DEFAULT NULL,
  `Parking` varchar(384) DEFAULT NULL,
  `GreenRate` varchar(384) DEFAULT NULL,
  `PropertyFee` varchar(384) DEFAULT NULL,
  `VolumeRate` varchar(384) DEFAULT NULL,
  `PropertyType` varchar(384) DEFAULT NULL,
  `Developer` varchar(384) DEFAULT NULL,
  `FinishedTime` varchar(384) DEFAULT NULL,
  `AnjuKeID` int(11) DEFAULT NULL,
  `Area` varchar(384) DEFAULT NULL,
  `Status` tinyint(4) DEFAULT '0',
  `RentRevenue` varchar(384) DEFAULT NULL,
  `ForceShow` tinyint(4) DEFAULT '0',
  `Zombie` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##residence building
CREATE TABLE `residence_building` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ResidenceID` int(11) DEFAULT NULL,
  `Prefix` varchar(16) DEFAULT NULL,
  `Suffix` varchar(32) DEFAULT NULL,
  `CodeType` tinyint(4) DEFAULT NULL,
  `CodeBegin` varchar(16) DEFAULT NULL,
  `CodeEnd` varchar(16) DEFAULT NULL,
  `Status` tinyint(4) DEFAULT '1',
  `Period` varchar(64) DEFAULT NULL,
  `Stair` int(11) DEFAULT NULL,
  `HouseHold` int(11) DEFAULT NULL,
  `BuildingType` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_IX` (`ResidenceID`,`Suffix`,`CodeType`,`CodeBegin`,`CodeEnd`,`Status`,`Prefix`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##residence cell
CREATE TABLE `residence_building_cell` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BuildingID` int(11) DEFAULT NULL,
  `CodeType` tinyint(1) DEFAULT NULL,
  `CodeBegin` varchar(16) DEFAULT NULL,
  `CodeEnd` varchar(16) DEFAULT NULL,
  `Status` tinyint(4) DEFAULT '1',
  `FloorBegin` int(11) DEFAULT NULL,
  `FloorEnd` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_IX` (`BuildingID`,`CodeType`,`CodeBegin`,`CodeEnd`,`FloorBegin`,`FloorEnd`,`Status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

##house
CREATE TABLE `house`( 
   `ID` INT NOT NULL AUTO_INCREMENT , 
   `Name` VARCHAR(128) , 
   `Status` TINYINT , 
   `ResidenceID` INT , 
   `CellID` INT , 
   `BuildingNo` VARCHAR(32) , 
   `CellNo` VARCHAR(32) , 
   `DetailName` VARCHAR(128) , 
   `PicID` INT , 
   `RoomTypePicID` INT , 
   `Direction` INT , 
   `PropertyArea` DECIMAL , 
   `OccupiedArea` DECIMAL , 
   `BuildTime` DATETIME , 
   `HouseType` VARCHAR(64) , 
   `RoomType` INT , 
   `Decorating` INT , 
   `Floor` INT , 
   `HasKey` BIT , 
   `HasKeyMemo` TEXT , 
   `ViewHouseType` INT , 
   `SourceType` TINYINT DEFAULT 1, 
   `ClientType` TINYINT DEFAULT 1, 
   `Memo` TEXT , 
   `AddTime` DATETIME , 
   `UpdateTime` DATETIME , 
   `Creator` INT , 
   `LastUpdater` INT , 
   `LastTaskTime` DATETIME , 
   PRIMARY KEY (`ID`)
 )ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 CREATE TABLE `house_sale` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `TagList` varchar(128) DEFAULT NULL, 
  `AvgPrice` decimal(10,0) DEFAULT NULL,  
  `Price` decimal(10,0) DEFAULT NULL,
  `BasePrice` decimal(10,0) DEFAULT NULL,
  `DealType` varchar(64) DEFAULT NULL,
  `SaleStatus` int(11) DEFAULT NULL,
  `SaleRec` int(11) DEFAULT NULL,
  `Source` varchar(64) DEFAULT NULL,  
  `Memo` text,  
  `DealTime` datetime DEFAULT NULL,  
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `Owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_rent` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `TagList` varchar(128) DEFAULT NULL,
  `Price` decimal(10,0) DEFAULT NULL,
  `BasePrice` decimal(10,0) DEFAULT NULL,
  `RentType` int(11) DEFAULT NULL,
  `RentStatus` int(11) DEFAULT NULL,
  `RentRec` int(11) DEFAULT NULL,
  `EquipmentList` varchar(128) DEFAULT NULL,
  `Memo` text,
  `DealTime` datetime DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `Owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `house_tag` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(64) DEFAULT NULL,
  `CategoryName` varchar(64) DEFAULT NULL,
  `Memo` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `house_contact` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `ContactType` int(11) DEFAULT NULL,
  `Name` varchar(64) DEFAULT NULL,
  `Mobile` varchar(64) DEFAULT NULL,
  `Mobile2` varchar(64) DEFAULT NULL,
  `Mobile3` varchar(64) DEFAULT NULL,
  `Memo` varchar(256) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `Committer` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_property` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `PropertyCode` varchar(256) DEFAULT NULL,
  `PropertyOwner` varchar(64) DEFAULT NULL,
  `Loan` text,
  `Version` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `BrokerID` int(11) DEFAULT NULL,
  `Memo` text,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_pic` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ResidenceID` int(11) DEFAULT NULL,
  `HouseID` int(11) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  `CloudURL` varchar(512) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `ShowStatus` int(11) UNSIGNED NULL DEFAULT '1' ,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_favorite` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `house_interaction` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `AgentID` int(11) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `StartDate` datetime DEFAULT NULL,
  `EndDate` datetime DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `house_interaction_transfer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) NOT NULL,
  `FromBrokerID` int(11) NOT NULL,
  `ToBrokerID` int(11) NOT NULL,
  `FinalBrokerID` int(11) NOT NULL,
  `AddTime` datetime NOT NULL,
  `UpdateTime` datetime NOT NULL,
  `Status` tinyint(1) DEFAULT NULL,
  `Notes` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_HouseID_BrokerID_Status` (`HouseID`,`FromBrokerID`,`Status`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `house_audit_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HouseID` int(11) DEFAULT NULL,
  `ManagerID` int(11) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `CommitterID` int(11) DEFAULT NULL,
  `PreContent` varchar(512) DEFAULT NULL,
  `PostContent` varchar(512) DEFAULT NULL,
  `Result` int(11) DEFAULT NULL,
  `Comments` varchar(512) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

## 已售房源的栋座信息
CREATE  TABLE `basedata`.`house_sold_building` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `HouseID` INT NULL ,
  `BuildingInfo` VARCHAR(128) NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE  TABLE `basedata`.`residence_price_history` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `ResidenceID` INT NULL ,
  `AvgPrice` VARCHAR(45) NULL ,
  `AddTime` DATETIME NULL ,
  PRIMARY KEY (`ID`) )
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `house_contact_view` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) NOT NULL,
  `Type` int(11) NOT NULL,
  `FromDate` datetime NOT NULL COMMENT '开始区间',
  `EndDate` datetime NOT NULL COMMENT '结束区间',
  `Base` int(11) NOT NULL COMMENT '基数',
  `ViewCount` int(11) NOT NULL COMMENT '浏览次数',
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查看次数表' AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `house_contact_view`
--


-- --------------------------------------------------------

--
-- 表的结构 `house_contact_view_history`
--

CREATE TABLE IF NOT EXISTS `house_contact_view_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AccountID` int(11) NOT NULL,
  `HouseID` int(11) NOT NULL,
  `ViewCount` int(11) NOT NULL,
  `AddTime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;



CREATE  TABLE IF NOT EXISTS `basedata`.`house_pic_sort` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `HouseID` INT NULL ,
  `Sort` TEXT NULL ,
  `Type` INT NULL ,
  `LastUpdater` INT NULL ,
  `UpdateTime` DATETIME NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `HouseId` (`HouseID` ASC) )
ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


CREATE  TABLE IF NOT EXISTS `basedata`.`house_interaction_notice` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `HouseID` INT NULL ,
  `Type` INT NULL COMMENT '1变更为有交互权，2变更为无交互权' ,
  `Status` INT NULL COMMENT '0未更新，1已更新' ,
  `AddTime` DATE NULL ,
  `UpdateTime` DATE NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE  TABLE `basedata`.`residence_month_data` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `ResidenceId` INT NULL ,
  `ResidenceName` VARCHAR(384) NULL ,
  `Year` INT NULL ,
  `Month` INT NULL ,
  `AnnualPriceIncrement` DECIMAL(8,4)  NULL ,
  `AnnualTurnoverRate` VARCHAR(64)  NULL ,
  `AnnualTurnoverPercent` DECIMAL(8,4)  NULL ,
  `RentRevenue` DECIMAL(8,4)  NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `residence_audit_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ResidenceID` int(11) DEFAULT NULL,
  `ManagerID` int(11) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL,
  `CommitterID` int(11) DEFAULT NULL,
  `PreContent` varchar(512) DEFAULT NULL,
  `PostContent` varchar(512) DEFAULT NULL,
  `Result` int(11) DEFAULT NULL,
  `AddTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ResidenceID` (`ResidenceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE  TABLE `basedata`.`key_value` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `KeyStr` VARCHAR(128) NULL ,
  `ValueStr` TEXT NULL ,
  `AddTime` DATETIME NULL ,
  `UpdateTime` DATETIME NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE  TABLE `basedata`.`app_client_file` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `Version` VARCHAR(45) NOT NULL ,
  `ClientType` CHAR NOT NULL ,
  `OSType` CHAR NOT NULL ,
  `CloudURL` VARCHAR(512) NULL ,
  `Status` INT(11) NULL ,
  `AddTime` DATETIME NULL ,
  `UpdateTime` DATETIME NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

--
-- Table structure for table `residence_name_history`
--

DROP TABLE IF EXISTS `residence_name_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `residence_name_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ResidenceId` int(11) NOT NULL,
  `OldName` varchar(384) DEFAULT NULL,
  `AddTime` datetime NOT NULL,
  `AccountId` int(11) NOT NULL
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

alter table `residence_name_history` add column  `Type` int(11) DEFAULT '0' comment '名称类型';
--update  `residence_name_history` set `Type` = 1; 

CREATE VIEW view_residence_pic_count AS 
SELECT count(1) AS PicCount, ResidenceID as ResidenceId 
FROM house_pic 
WHERE status=1 
GROUP BY ResidenceID;

CREATE VIEW view_residence_pic_under_approve AS 
SELECT count(1) AS PicCount, ResidenceID as ResidenceId 
FROM house_pic
WHERE status=1
AND ShowStatus=0
GROUP BY ResidenceID;


