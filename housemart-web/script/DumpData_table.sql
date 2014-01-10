CREATE DATABASE `DumpData` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci

CREATE TABLE IF NOT EXISTS `Sheet1` (
  `A` varchar(200) DEFAULT NULL,
  `B` varchar(200) DEFAULT NULL,
  `C` varchar(200) DEFAULT NULL,
  `D` varchar(16) DEFAULT NULL,
  `E` varchar(11) DEFAULT NULL,
  `F` varchar(32) DEFAULT NULL,
  `G` varchar(6) DEFAULT NULL,
  `H` varchar(9) DEFAULT NULL,
  `I` varchar(12) DEFAULT NULL,
  `J` varchar(15) DEFAULT NULL,
  `K` varchar(6) DEFAULT NULL,
  `L` varchar(10) DEFAULT NULL,
  `M` varchar(16) DEFAULT NULL,
  `N` varchar(6) DEFAULT NULL,
  `O` varchar(12) DEFAULT NULL,
  `P` varchar(20) DEFAULT NULL,
  `Q` varchar(24) DEFAULT NULL,
  `R` varchar(12) DEFAULT NULL,
  `S` varchar(12) DEFAULT NULL,
  `T` varchar(18) DEFAULT NULL,
  `U` varchar(131) DEFAULT NULL,
  `V` varchar(15) DEFAULT NULL,
  `W` varchar(9) DEFAULT NULL,
  `X` varchar(12) DEFAULT NULL,
  `Y` varchar(12) DEFAULT NULL,
  `Z` datetime DEFAULT NULL,
  `HouseID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `DumpData`.`Sheet1` 
ADD INDEX `HouseID` (`HouseID` ASC) ;