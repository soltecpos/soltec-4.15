--    SmartPOS - Punto de Venta Inteligente
--    Copyright (c) 2009-2018 SmartPos
--    http://smartpos.com.co
--
--    This file is part of SmartPOS.
--
--    SmartPOS is free software: you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation, either version 3 of the License, or
--    (at your option) any later version.
--
--    SmartPOS is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with SmartPOS.  If not, see <http://www.gnu.org/licenses/>.

/*
 * Script created by Jack, SmartPos 23/01/2018 08:00:00
 *
 * Creating for version smartpos45. Jan 2018
*/

/* Header line. Object: applications. Script date: 23/01/2018 08:00:00 */
SET foreign_key_checks = 0;
CREATE TABLE `applications` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`version` varchar(255) NOT NULL,
	`instdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attribute. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `attribute` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributeinstance. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `attributeinstance` (
	`id` varchar(255) NOT NULL,
	`attributesetinstance_id` varchar(255) NOT NULL,
	`attribute_id` varchar(255) NOT NULL,
	`value` varchar(255) default NULL,
	KEY `attinst_att` ( `attribute_id` ),
	KEY `attinst_set` ( `attributesetinstance_id` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributeset. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `attributeset` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributesetinstance. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `attributesetinstance` (
	`id` varchar(255) NOT NULL,
	`attributeset_id` varchar(255) NOT NULL,
	`description` varchar(255) default NULL,
	KEY `attsetinst_set` ( `attributeset_id` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributeuse. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `attributeuse` (
	`id` varchar(255) NOT NULL,
	`attributeset_id` varchar(255) NOT NULL,
	`attribute_id` varchar(255) NOT NULL,
	`lineno` int(11) default NULL,
	KEY `attuse_att` ( `attribute_id` ),
	UNIQUE INDEX `attuse_line` ( `attributeset_id`, `lineno` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributevalue. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `attributevalue` (
	`id` varchar(255) NOT NULL,
	`attribute_id` varchar(255) NOT NULL,
	`value` varchar(255) default NULL,
	KEY `attval_att` ( `attribute_id` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: breaks. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `breaks` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`visible` tinyint(1) NOT NULL default '1',
	`notes` varchar(255) default NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: categories. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `categories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`parentid` varchar(255) default NULL,
	`image` mediumblob default NULL,
	`texttip` varchar(255) default NULL,
	`catshowname` smallint(6) NOT NULL default '1',
	`catorder` varchar(255) default NULL,
	KEY `categories_fk_1` ( `parentid` ),
	UNIQUE INDEX `categories_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: closedcash. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `closedcash` (
	`money` varchar(255) NOT NULL,
	`host` varchar(255) NOT NULL,
	`hostsequence` int(11) NOT NULL,
	`datestart` datetime NOT NULL,
	`dateend` datetime default NULL,
	`nosales` int(11) NOT NULL default '0',
	KEY `closedcash_inx_1` ( `datestart` ),
	UNIQUE INDEX `closedcash_inx_seq` ( `host`, `hostsequence` ),
	PRIMARY KEY  ( `money` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: csvimport. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `csvimport` (
	`id` varchar(255) NOT NULL,
	`rownumber` varchar(255) default NULL,
	`csverror` varchar(255) default NULL,
	`reference` varchar(255) default NULL,
	`code` varchar(255) default NULL,
	`name` varchar(255) default NULL,
	`pricebuy` double default NULL,
	`pricesell` double default NULL,
	`previousbuy` double default NULL,
	`previoussell` double default NULL,
	`category` varchar(255) default NULL,
	`tax` varchar(255) default NULL,
	`searchkey` varchar(255) default NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: customers. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `customers` (
	`id` varchar(255) NOT NULL,
	`searchkey` varchar(255) NOT NULL,
	`taxid` varchar(255) default NULL,
	`name` varchar(255) NOT NULL,
	`taxcategory` varchar(255) default NULL,
	`card` varchar(255) default NULL,
	`maxdebt` double NOT NULL default '0',
	`address` varchar(255) NOT NULL,
	`address2` varchar(255) default NULL,
	`postal` varchar(255) default NULL,
	`city` varchar(255) default NULL,
	`region` varchar(255) default NULL,
	`country` varchar(255) default NULL,
	`firstname` varchar(255) default NULL,
	`lastname` varchar(255) default NULL,
	`email` varchar(255) NOT NULL,
	`phone` varchar(255) default NULL,
	`phone2` varchar(255) default NULL,
	`fax` varchar(255) default NULL,
	`notes` varchar(255) default NULL,
	`visible` bit(1) NOT NULL default b'1',
	`curdate` datetime default NULL,
	`curdebt` double default '0',
	`image` mediumblob default NULL,
	`isvip` bit(1) NOT NULL default b'0',
	`discount` double default '0',
	`memodate` datetime default '2020-01-01 00:00:01',
	KEY `customers_card_inx` ( `card` ),
	KEY `customers_name_inx` ( `name` ),
	UNIQUE INDEX `customers_skey_inx` ( `searchkey` ),
	KEY `customers_taxcat` ( `taxcategory` ),
	KEY `customers_taxid_inx` ( `taxid` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: draweropened. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `draweropened` (
	`opendate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`name` varchar(255) default NULL,
	`ticketid` varchar(255) default NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: floors. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `floors` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`image` mediumblob default NULL,
	UNIQUE INDEX `floors_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: leaves. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `leaves` (
	`id` varchar(255) NOT NULL,
	`pplid` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`startdate` datetime NOT NULL,
	`enddate` datetime NOT NULL,
	`notes` varchar(255) default NULL,
	KEY `leaves_pplid` ( `pplid` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: lineremoved. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `lineremoved` (
	`removeddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`name` varchar(255) default NULL,
	`ticketid` varchar(255) default NULL,
	`productid` varchar(255) default NULL,
	`productname` varchar(255) default NULL,
	`units` double NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: locations. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `locations` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`address` varchar(255) default NULL,
	UNIQUE INDEX `locations_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: moorers. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `moorers` (
	`vesselname` varchar(255) default NULL,
	`size` int(11) default NULL,
	`days` int(11) default NULL,
	`power` bit(1) NOT NULL default b'0'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

CREATE TABLE IF NOT EXISTS `orders` (
    `id` MEDIUMINT NOT NULL AUTO_INCREMENT,
    `orderid` varchar(50) DEFAULT NULL,
    `qty` int(11) DEFAULT '1',
    `details` varchar(255) DEFAULT NULL,
    `attributes` varchar(255) DEFAULT NULL,
    `notes` varchar(255) DEFAULT NULL,
    `ticketid` varchar(50) DEFAULT NULL,
    `ordertime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `displayid` int(11) DEFAULT '1',
    `auxiliary` int(11) DEFAULT NULL,
    `completetime` timestamp NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: payments. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `payments` (
	`id` varchar(255) NOT NULL,
	`receipt` varchar(255) NOT NULL,
	`payment` varchar(255) NOT NULL,
	`total` double NOT NULL default '0',
	`tip` double default '0',
	`transid` varchar(255) default NULL,
	`isprocessed` bit(1) default b'0',
	`returnmsg` mediumblob default NULL,
	`notes` varchar(255) default NULL,
	`tendered` double default NULL,
	`cardname` varchar(255) default NULL,
        `voucher` varchar(255) default NULL,
	KEY `payments_fk_receipt` ( `receipt` ),
	KEY `payments_inx_1` ( `payment` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: people. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `people` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`apppassword` varchar(255) default NULL,
	`card` MEDIUMTEXT default NULL,
	`role` varchar(255) NOT NULL,
	`visible` bit(1) NOT NULL,
	`image` mediumblob default NULL,
	KEY `people_fk_1` ( `role` ),
	UNIQUE INDEX `people_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: pickup_number. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `pickup_number` (
	`id` int(11) NOT NULL default '0'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: places. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `places` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`seats` varchar(6) NOT NULL DEFAULT '1',
	`x` int(11) NOT NULL,
	`y` int(11) NOT NULL,
	`floor` varchar(255) NOT NULL,
	`customer` varchar(255) default NULL,
	`waiter` varchar(255) default NULL,
	`ticketid` varchar(255) default NULL,
	`tablemoved` smallint(6) NOT NULL default '0',
	`width` int(11) NOT NULL,
	`height` int(11) NOT NULL,
        `guests` int(11) DEFAULT 0,
        `occupied` datetime DEFAULT NULL,
	UNIQUE INDEX `places_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products. Script date: 02/04/2016 10:53:00. */
CREATE TABLE `products` (
	`id` varchar(255) NOT NULL,
	`reference` varchar(255) NOT NULL,
	`code` varchar(255) NOT NULL,
	`codetype` varchar(255) default NULL,
	`name` varchar(255) NOT NULL,
	`pricebuy` double NOT NULL default '0',
	`pricesell` double NOT NULL default '0',
	`category` varchar(255) NOT NULL,
	`taxcat` varchar(255) NOT NULL,
	`attributeset_id` varchar(255) default NULL,
	`stockcost` double NOT NULL default '0',
	`stockvolume` double NOT NULL default '0',
	`image` mediumblob default NULL,
	`iscom` bit(1) NOT NULL default b'0',
	`isscale` bit(1) NOT NULL default b'0',
	`isconstant` bit(1) NOT NULL default b'0',
	`printkb` bit(1) NOT NULL default b'0',
	`sendstatus` bit(1) NOT NULL default b'0',
	`isservice` bit(1) NOT NULL default b'0',
	`attributes` mediumblob default NULL,
	`display` varchar(255) default NULL,
	`isvprice` smallint(6) NOT NULL default '0',
	`isverpatrib` smallint(6) NOT NULL default '0',
	`texttip` varchar(255) default NULL,
	`warranty` smallint(6) NOT NULL default '0',
	`stockunits` double NOT NULL default '0',
	`printto` varchar(255) default '1',
	`supplier` varchar(255) default NULL,
    `uom` varchar(255) default '0',
	`pricesellaux` double DEFAULT '0',
		`pricesellaux2` double DEFAULT '0',
	`memodate` datetime default '2018-01-01 00:00:01',

	PRIMARY KEY  ( `id` ),
	KEY `products_attrset_fx` ( `attributeset_id` ),
	KEY `products_fk_1` ( `category` ),
	UNIQUE INDEX `products_inx_0` ( `reference` ),
	UNIQUE INDEX `products_inx_1` ( `code` ),
	INDEX `products_name_inx` ( `name` ),
	KEY `products_taxcat_fk` ( `taxcat` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products_cat. Script date: 11/05/2016 05:25:00. */
CREATE TABLE `products_bundle` (
    `id` varchar(255) NOT NULL,
    `product` VARCHAR(255) NOT NULL,
    `product_bundle` VARCHAR(255) NOT NULL,
    `quantity` DOUBLE NOT NULL,
    PRIMARY KEY ( `id` ),
    UNIQUE INDEX `pbundle_inx_prod` ( `product` , `product_bundle` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products_cat. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `products_cat` (
	`product` varchar(255) NOT NULL,
	`catorder` int(11) default NULL,
	PRIMARY KEY  ( `product` ),
	KEY `products_cat_inx_1` ( `catorder` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products_com. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `products_com` (
	`id` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`product2` varchar(255) NOT NULL,
	UNIQUE INDEX `pcom_inx_prod` ( `product`, `product2` ),
	PRIMARY KEY  ( `id` ),
	KEY `products_com_fk_2` ( `product2` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: receipts. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `receipts` (
	`id` varchar(255) NOT NULL,
	`money` varchar(255) NOT NULL,
	`datenew` datetime NOT NULL,
	`attributes` mediumblob default NULL,
	`person` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `receipts_fk_money` ( `money` ),
	KEY `receipts_inx_1` ( `datenew` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: reservation_customers. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `reservation_customers` (
	`id` varchar(255) NOT NULL,
	`customer` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` ),
	KEY `res_cust_fk_2` ( `customer` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: reservations. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `reservations` (
	`id` varchar(255) NOT NULL,
	`created` datetime NOT NULL,
	`datenew` datetime NOT NULL default '2018-01-01 00:00:00',
	`title` varchar(255) NOT NULL,
	`chairs` int(11) NOT NULL,
	`isdone` bit(1) NOT NULL,
	`description` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `reservations_inx_1` ( `datenew` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: resources. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `resources` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`restype` int(11) NOT NULL,
	`content` mediumblob default NULL,
        `version` varchar(10) default NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `resources_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: roles. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `roles` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`permissions` mediumblob default NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `roles_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: sharedtickets. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `sharedtickets` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`content` mediumblob default NULL,
	`appuser` varchar(255) default NULL,
	`pickupid` int(11) NOT NULL default '0',
	`locked` varchar(20) default NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: shift_breaks. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `shift_breaks` (
	`id` varchar(255) NOT NULL,
	`shiftid` varchar(255) NOT NULL,
	`breakid` varchar(255) NOT NULL,
	`starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`endtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY  ( `id` ),
	KEY `shift_breaks_breakid` ( `breakid` ),
	KEY `shift_breaks_shiftid` ( `shiftid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: shifts. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `shifts` (
	`id` varchar(255) NOT NULL,
	`startshift` datetime NOT NULL,
	`endshift` datetime default NULL,
	`pplid` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: stockcurrent. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `stockcurrent` (
	`location` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`attributesetinstance_id` varchar(255) default NULL,
	`units` double NOT NULL,
	KEY `stockcurrent_attsetinst` ( `attributesetinstance_id` ),
	KEY `stockcurrent_fk_1` ( `product` ),
	UNIQUE INDEX `stockcurrent_inx` ( `location`, `product`, `attributesetinstance_id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: stockdiary. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `stockdiary` (
	`id` varchar(255) NOT NULL,
	`datenew` datetime NOT NULL,
	`reason` int(11) NOT NULL,
	`location` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`attributesetinstance_id` varchar(255) default NULL,
	`units` double NOT NULL,
	`price` double NOT NULL,
	`appuser` varchar(255) default NULL,
	`supplier` varchar(255) default NULL,
	`supplierdoc` varchar(255) default NULL,
	`brand` varchar(255) DEFAULT NULL,
	`presentation` varchar(255) DEFAULT NULL,
	`registry` varchar(255) DEFAULT NULL,
	`lot` varchar(255) DEFAULT NULL,
	`due` varchar(255) DEFAULT NULL,
	PRIMARY KEY  ( `id` ),
	KEY `stockdiary_attsetinst` ( `attributesetinstance_id` ),
	KEY `stockdiary_fk_1` ( `product` ),
	KEY `stockdiary_fk_2` ( `location` ),
	KEY `stockdiary_inx_1` ( `datenew` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: stocklevel. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `stocklevel` (
	`id` varchar(255) NOT NULL,
	`location` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`stocksecurity` double default NULL,
	`stockmaximum` double default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `stocklevel_location` ( `location` ),
	KEY `stocklevel_product` ( `product` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: suppliers. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `suppliers` (
	`id` varchar(255) NOT NULL,
	`searchkey` varchar(255) NOT NULL,
	`taxid` varchar(255) default NULL,
	`name` varchar(255) NOT NULL,
	`maxdebt` double NOT NULL default '0',
	`address` varchar(255) default NULL,
	`address2` varchar(255) default NULL,
	`postal` varchar(255) default NULL,
	`city` varchar(255) default NULL,
	`region` varchar(255) default NULL,
	`country` varchar(255) default NULL,
	`firstname` varchar(255) default NULL,
	`lastname` varchar(255) default NULL,
	`email` varchar(255) default NULL,
	`phone` varchar(255) default NULL,
	`phone2` varchar(255) default NULL,
	`fax` varchar(255) default NULL,
	`notes` varchar(255) default NULL,
	`visible` bit(1) NOT NULL default b'1',
	`curdate` datetime default NULL,
	`curdebt` double default '0',
	`vatid` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `suppliers_name_inx` ( `name` ),
	UNIQUE INDEX `suppliers_skey_inx` ( `searchkey` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxcategories. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `taxcategories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `taxcat_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxcustcategories. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `taxcustcategories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `taxcustcat_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxes. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `taxes` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`category` varchar(255) NOT NULL,
	`custcategory` varchar(255) default NULL,
	`parentid` varchar(255) default NULL,
	`rate` double NOT NULL default '0',
	`ratecascade` bit(1) NOT NULL default b'0',
	`rateorder` int(11) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `taxes_cat_fk` ( `category` ),
	KEY `taxes_custcat_fk` ( `custcategory` ),
	UNIQUE INDEX `taxes_name_inx` ( `name` ),
	KEY `taxes_taxes_fk` ( `parentid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxlines. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `taxlines` (
	`id` varchar(255) NOT NULL,
	`receipt` varchar(255) NOT NULL,
	`taxid` varchar(255) NOT NULL,
	`base` double NOT NULL default '0',
	`amount` double NOT NULL default '0',
	PRIMARY KEY  ( `id` ),
	KEY `taxlines_receipt` ( `receipt` ),
	KEY `taxlines_tax` ( `taxid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxsuppcategories. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `taxsuppcategories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: thirdparties. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `thirdparties` (
	`id` varchar(255) NOT NULL,
	`cif` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`address` varchar(255) default NULL,
	`contactcomm` varchar(255) default NULL,
	`contactfact` varchar(255) default NULL,
	`payrule` varchar(255) default NULL,
	`faxnumber` varchar(255) default NULL,
	`phonenumber` varchar(255) default NULL,
	`mobilenumber` varchar(255) default NULL,
	`email` varchar(255) default NULL,
	`webpage` varchar(255) default NULL,
	`notes` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `thirdparties_cif_inx` ( `cif` ),
	UNIQUE INDEX `thirdparties_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketlines. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `ticketlines` (
	`ticket` varchar(255) NOT NULL,
	`line` int(11) NOT NULL,
	`product` varchar(255) default NULL,
	`attributesetinstance_id` varchar(255) default NULL,
	`units` double NOT NULL,
	`price` double NOT NULL,
	`taxid` varchar(255) NOT NULL,
	`attributes` mediumblob default NULL,
	PRIMARY KEY  ( `ticket`, `line` ),
	KEY `ticketlines_attsetinst` ( `attributesetinstance_id` ),
	KEY `ticketlines_fk_2` ( `product` ),
	KEY `ticketlines_fk_3` ( `taxid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: tickets. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `tickets` (
	`id` varchar(255) NOT NULL,
	`tickettype` int(11) NOT NULL default '0',
	`ticketid` int(11) NOT NULL,
	`person` varchar(255) NOT NULL,
	`customer` varchar(255) default NULL,
	`status` int(11) NOT NULL default '0',
	PRIMARY KEY  ( `id` ),
	KEY `tickets_customers_fk` ( `customer` ),
	KEY `tickets_fk_2` ( `person` ),
	KEY `tickets_ticketid` ( `tickettype`, `ticketid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `ticketsnum` (
	`id` int(11) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum_payment. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `ticketsnum_payment` (
	`id` int(11) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum_fe. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `ticketsnum_fe` (
	`id` int(11) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum_refund. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `ticketsnum_refund` (
	`id` int(11) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: uom. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `uom` (
    `id` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: vouchers. Script date: 23/01/2018 08:00:00 */
CREATE TABLE `vouchers` (
   `id` VARCHAR(100) NOT NULL,
   `voucher_number` VARCHAR(100) DEFAULT NULL,
   `customer` VARCHAR(100) DEFAULT NULL,
   `amount` DOUBLE DEFAULT NULL,
   `status` CHAR(1) DEFAULT 'A',
  PRIMARY KEY ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

-- Update foreign keys of attributeinstance
ALTER TABLE `attributeinstance` ADD CONSTRAINT `attinst_att`
	FOREIGN KEY ( `attribute_id` ) REFERENCES `attribute` ( `id` );

ALTER TABLE `attributeinstance` ADD CONSTRAINT `attinst_set`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of attributesetinstance
ALTER TABLE `attributesetinstance` ADD CONSTRAINT `attsetinst_set`
	FOREIGN KEY ( `attributeset_id` ) REFERENCES `attributeset` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of attributeuse
ALTER TABLE `attributeuse` ADD CONSTRAINT `attuse_att`
	FOREIGN KEY ( `attribute_id` ) REFERENCES `attribute` ( `id` );

ALTER TABLE `attributeuse` ADD CONSTRAINT `attuse_set`
	FOREIGN KEY ( `attributeset_id` ) REFERENCES `attributeset` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of attributevalue
ALTER TABLE `attributevalue` ADD CONSTRAINT `attval_att`
	FOREIGN KEY ( `attribute_id` ) REFERENCES `attribute` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of categories
ALTER TABLE `categories` ADD CONSTRAINT `categories_fk_1`
	FOREIGN KEY ( `parentid` ) REFERENCES `categories` ( `id` );

-- Update foreign keys of customers
 ALTER TABLE `customers` ADD CONSTRAINT `customers_taxcat`
	FOREIGN KEY ( `taxcategory` ) REFERENCES `taxcustcategories` ( `id` );

-- Update foreign keys of leaves
ALTER TABLE `leaves` ADD CONSTRAINT `leaves_pplid`
	FOREIGN KEY ( `pplid` ) REFERENCES `people` ( `id` );

-- Update foreign keys of payments
ALTER TABLE `payments` ADD CONSTRAINT `payments_fk_receipt`
	FOREIGN KEY ( `receipt` ) REFERENCES `receipts` ( `id` );

-- Update foreign keys of people
ALTER TABLE `people` ADD CONSTRAINT `people_fk_1`
	FOREIGN KEY ( `role` ) REFERENCES `roles` ( `id` );

-- Update foreign keys of products
ALTER TABLE `products` ADD CONSTRAINT `products_attrset_fk`
	FOREIGN KEY ( `attributeset_id` ) REFERENCES `attributeset` ( `id` );

ALTER TABLE `products` ADD CONSTRAINT `products_fk_1`
	FOREIGN KEY ( `category` ) REFERENCES `categories` ( `id` );

ALTER TABLE `products` ADD CONSTRAINT `products_taxcat_fk`
	FOREIGN KEY ( `taxcat` ) REFERENCES `taxcategories` ( `id` );

-- Update foreign keys of product_bundle
ALTER TABLE `products_bundle` ADD CONSTRAINT `products_bundle_fk_1` 
        FOREIGN KEY ( `product` ) REFERENCES `products`( `id` );

ALTER TABLE `products_bundle` ADD CONSTRAINT `products_bundle_fk_2`     
        FOREIGN KEY ( `product_bundle` ) REFERENCES `products`( `id` );

-- Update foreign keys of products_cat
ALTER TABLE `products_cat` ADD CONSTRAINT `products_cat_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

-- Update foreign keys of products_com
ALTER TABLE `products_com` ADD CONSTRAINT `products_com_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `products_com` ADD CONSTRAINT `products_com_fk_2`
	FOREIGN KEY ( `product2` ) REFERENCES `products` ( `id` );

-- Update foreign keys of receipts
ALTER TABLE `receipts` ADD CONSTRAINT `receipts_fk_money`
	FOREIGN KEY ( `money` ) REFERENCES `closedcash` ( `money` );

-- Update foreign keys of reservation_customers
ALTER TABLE `reservation_customers` ADD CONSTRAINT `res_cust_fk_1`
	FOREIGN KEY ( `id` ) REFERENCES `reservations` ( `id` );

ALTER TABLE `reservation_customers` ADD CONSTRAINT `res_cust_fk_2`
	FOREIGN KEY ( `customer` ) REFERENCES `customers` ( `id` );

-- Update foreign keys of shift_breaks
ALTER TABLE `shift_breaks` ADD CONSTRAINT `shift_breaks_breakid`
	FOREIGN KEY ( `breakid` ) REFERENCES `breaks` ( `id` );

ALTER TABLE `shift_breaks` ADD CONSTRAINT `shift_breaks_shiftid`
	FOREIGN KEY ( `shiftid` ) REFERENCES `shifts` ( `id` );

-- Update foreign keys of stockcurrent
ALTER TABLE `stockcurrent` ADD CONSTRAINT `stockcurrent_attsetinst`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` );

ALTER TABLE `stockcurrent` ADD CONSTRAINT `stockcurrent_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `stockcurrent` ADD CONSTRAINT `stockcurrent_fk_2`
	FOREIGN KEY ( `location` ) REFERENCES `locations` ( `id` );

-- Update foreign keys of stockdiary
ALTER TABLE `stockdiary` ADD CONSTRAINT `stockdiary_attsetinst`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` );

ALTER TABLE `stockdiary` ADD CONSTRAINT `stockdiary_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `stockdiary` ADD CONSTRAINT `stockdiary_fk_2`
	FOREIGN KEY ( `location` ) REFERENCES `locations` ( `id` );

-- Update foreign keys of stocklevel
ALTER TABLE `stocklevel` ADD CONSTRAINT `stocklevel_location`
	FOREIGN KEY ( `location` ) REFERENCES `locations` ( `id` );

ALTER TABLE `stocklevel` ADD CONSTRAINT `stocklevel_product`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

-- Update foreign keys of taxes
ALTER TABLE `taxes` ADD CONSTRAINT `taxes_cat_fk`
	FOREIGN KEY ( `category` ) REFERENCES `taxcategories` ( `id` );

ALTER TABLE `taxes` ADD CONSTRAINT `taxes_custcat_fk`
	FOREIGN KEY ( `custcategory` ) REFERENCES `taxcustcategories` ( `id` );

ALTER TABLE `taxes` ADD CONSTRAINT `taxes_taxes_fk`
	FOREIGN KEY ( `parentid` ) REFERENCES `taxes` ( `id` );

-- Update foreign keys of taxlines
ALTER TABLE `taxlines` ADD CONSTRAINT `taxlines_receipt`
	FOREIGN KEY ( `receipt` ) REFERENCES `receipts` ( `id` );

ALTER TABLE `taxlines` ADD CONSTRAINT `taxlines_tax`
	FOREIGN KEY ( `taxid` ) REFERENCES `taxes` ( `id` );

-- Update foreign keys of ticketlines
ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_attsetinst`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` );

ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_fk_2`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_fk_3`
	FOREIGN KEY ( `taxid` ) REFERENCES `taxes` ( `id` );

ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_fk_ticket`
	FOREIGN KEY ( `ticket` ) REFERENCES `tickets` ( `id` );

-- Update foreign keys of tickets
ALTER TABLE `tickets` ADD CONSTRAINT `tickets_customers_fk`
	FOREIGN KEY ( `customer` ) REFERENCES `customers` ( `id` );

ALTER TABLE `tickets` ADD CONSTRAINT `tickets_fk_2`
	FOREIGN KEY ( `person` ) REFERENCES `people` ( `id` );

ALTER TABLE `tickets` ADD CONSTRAINT `tickets_fk_id`
	FOREIGN KEY ( `id` ) REFERENCES `receipts` ( `id` );

-- *****************************************************************************

-- ADD roles
INSERT INTO ROLES(ID, NAME, PERMISSIONS) VALUES('0', 'Rol de Administrador', $FILE{/com/openbravo/pos/templates/Role.Administrator.xml} );
INSERT INTO ROLES(ID, NAME, PERMISSIONS) VALUES('1', 'Rol de Gerente', $FILE{/com/openbravo/pos/templates/Role.Manager.xml} );
INSERT INTO ROLES(ID, NAME, PERMISSIONS) VALUES('2', 'Rol de Funcionario', $FILE{/com/openbravo/pos/templates/Role.Employee.xml} );
INSERT INTO ROLES(ID, NAME, PERMISSIONS) VALUES('3', 'Rol de Visitante', $FILE{/com/openbravo/pos/templates/Role.Guest.xml} );

-- ADD people
INSERT INTO PEOPLE(ID, NAME, APPPASSWORD, ROLE, VISIBLE, IMAGE) VALUES ('0', 'Administrador', NULL, '0', TRUE, NULL);
INSERT INTO PEOPLE(ID, NAME, APPPASSWORD, ROLE, VISIBLE, IMAGE) VALUES ('1', 'Gerente', NULL, '1', TRUE, NULL);
INSERT INTO PEOPLE(ID, NAME, APPPASSWORD, ROLE, VISIBLE, IMAGE) VALUES ('2', 'Funcionario', NULL, '2', TRUE, NULL);
INSERT INTO PEOPLE(ID, NAME, APPPASSWORD, ROLE, VISIBLE, IMAGE) VALUES ('3', 'Visitante', NULL, '3', TRUE, NULL);

-- ADD resources --
-- SYSTEM
INSERT INTO resources(id, name, restype, content) VALUES('00', 'Menu.Root', 0, $FILE{/com/openbravo/pos/templates/Menu.Root.txt});
INSERT INTO resources(ID, name, restype, content) VALUES('01', 'Application.Started', 0, $FILE{/com/openbravo/pos/templates/application.started.xml});
INSERT INTO resources(id, name, restype, content) VALUES('02', 'Cash.Close', 0, $FILE{/com/openbravo/pos/templates/Cash.Close.xml});
INSERT INTO resources(ID, name, restype, content) VALUES('03', 'Customer.Created', 0, $FILE{/com/openbravo/pos/templates/customer.created.xml});
INSERT INTO resources(ID, name, restype, content) VALUES('04', 'Customer.Deleted', 0, $FILE{/com/openbravo/pos/templates/customer.deleted.xml});
INSERT INTO resources(ID, name, restype, content) VALUES('05', 'Customer.Updated', 0, $FILE{/com/openbravo/pos/templates/customer.updated.xml});
INSERT INTO resources(id, name, restype, content) VALUES('06', 'payment.cash', 0, $FILE{/com/openbravo/pos/templates/payment.cash.txt});
INSERT INTO resources(id, name, restype, content) VALUES('07', 'Ticket.Buttons', 0, $FILE{/com/openbravo/pos/templates/Ticket.Buttons.xml});
INSERT INTO resources(id, name, restype, content) VALUES('08', 'Ticket.Close', 0, $FILE{/com/openbravo/pos/templates/Ticket.Close.xml});
INSERT INTO resources(id, name, restype, content) VALUES('09', 'Ticket.Line', 0, $FILE{/com/openbravo/pos/templates/Ticket.Line.xml});
INSERT INTO resources(id, name, restype, content) VALUES('10', 'Window.Title', 0, $FILE{/com/openbravo/pos/templates/Window.Title.txt});

-- IMAGES

INSERT INTO resources(id, name, restype, content) VALUES('11', 'coin.01', 1, $FILE{/com/openbravo/pos/templates/coin.01.png});
INSERT INTO resources(id, name, restype, content) VALUES('12', 'coin.02', 1, $FILE{/com/openbravo/pos/templates/coin.02.png});
INSERT INTO resources(id, name, restype, content) VALUES('13', 'coin.05', 1, $FILE{/com/openbravo/pos/templates/coin.05.png});
INSERT INTO resources(id, name, restype, content) VALUES('14', 'coin.1', 1, $FILE{/com/openbravo/pos/templates/coin.1.png});
INSERT INTO resources(id, name, restype, content) VALUES('15', 'coin.10', 1, $FILE{/com/openbravo/pos/templates/coin.10.png});
INSERT INTO resources(id, name, restype, content) VALUES('16', 'coin.2', 1, $FILE{/com/openbravo/pos/templates/coin.2.png});
INSERT INTO resources(id, name, restype, content) VALUES('17', 'coin.20', 1, $FILE{/com/openbravo/pos/templates/coin.20.png});
INSERT INTO resources(id, name, restype, content) VALUES('18', 'coin.50', 1, $FILE{/com/openbravo/pos/templates/coin.50.png});
INSERT INTO resources(id, name, restype, content) VALUES('19', 'note.20', 1, $FILE{/com/openbravo/pos/templates/note.20.png});
INSERT INTO resources(id, name, restype, content) VALUES('20', 'note.10', 1, $FILE{/com/openbravo/pos/templates/note.10.png});
INSERT INTO resources(id, name, restype, content) VALUES('21', 'note.5', 1, $FILE{/com/openbravo/pos/templates/note.5.png});
INSERT INTO resources(id, name, restype, content) VALUES('22', 'note.50', 1, $FILE{/com/openbravo/pos/templates/note.50.png});
INSERT INTO resources(id, name, restype, content) VALUES('23', 'img.50', 1, $FILE{/com/openbravo/images/50.00.png});
INSERT INTO resources(id, name, restype, content) VALUES('24', 'img.100', 1, $FILE{/com/openbravo/images/100.00.png});
INSERT INTO resources(id, name, restype, content) VALUES('25', 'img.200', 1, $FILE{/com/openbravo/images/200.00.png});
INSERT INTO resources(id, name, restype, content) VALUES('26', 'img.500', 1, $FILE{/com/openbravo/images/500.00.png});
INSERT INTO resources(id, name, restype, content) VALUES('27', 'img.1000', 1, $FILE{/com/openbravo/images/1000.00.png});
INSERT INTO resources(id, name, restype, content) VALUES('28', 'img.cash', 1, $FILE{/com/openbravo/images/cash.png});
INSERT INTO resources(id, name, restype, content) VALUES('29', 'img.cashdrawer', 1, $FILE{/com/openbravo/images/cashdrawer.png});
INSERT INTO resources(id, name, restype, content) VALUES('30', 'img.discount', 1, $FILE{/com/openbravo/images/discount.png});
INSERT INTO resources(id, name, restype, content) VALUES('31', 'img.discount_b', 1, $FILE{/com/openbravo/images/discount_b.png});
INSERT INTO resources(id, name, restype, content) VALUES('32', 'img.heart', 1, $FILE{/com/openbravo/images/heart.png});
INSERT INTO resources(id, name, restype, content) VALUES('33', 'img.keyboard_48', 1, $FILE{/com/openbravo/images/keyboard_48.png});
INSERT INTO resources(id, name, restype, content) VALUES('34', 'img.kit_print', 1, $FILE{/com/openbravo/images/kit_print.png});
INSERT INTO resources(id, name, restype, content) VALUES('35', 'img.no_photo', 1, $FILE{/com/openbravo/images/no_photo.png});
INSERT INTO resources(id, name, restype, content) VALUES('36', 'img.refundit', 1, $FILE{/com/openbravo/images/refundit.png});
INSERT INTO resources(id, name, restype, content) VALUES('37', 'img.run_script', 1, $FILE{/com/openbravo/images/run_script.png});
INSERT INTO resources(id, name, restype, content) VALUES('38', 'img.ticket_print', 1, $FILE{/com/openbravo/images/ticket_print.png});
INSERT INTO resources(id, name, restype, content) VALUES('39', 'img.posapps', 1, $FILE{/com/openbravo/images/img.posapps.png});
INSERT INTO resources(id, name, restype, content) VALUES('40', 'Printer.Ticket.Logo', 1, $FILE{/com/openbravo/images/printer.ticket.logo.jpg});

-- PRINTER
INSERT INTO resources(id, name, restype, content) VALUES('41', 'Printer.CloseCash.Preview', 0, $FILE{/com/openbravo/pos/templates/Printer.CloseCash.Preview.xml});
INSERT INTO resources(id, name, restype, content) VALUES('42', 'Printer.CloseCash', 0, $FILE{/com/openbravo/pos/templates/Printer.CloseCash.xml});
INSERT INTO resources(id, name, restype, content) VALUES('43', 'Printer.CustomerPaid', 0, $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid.xml});
INSERT INTO resources(id, name, restype, content) VALUES('44', 'Printer.CustomerPaid2', 0, $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid2.xml});
INSERT INTO resources(id, name, restype, content) VALUES('45', 'Printer.FiscalTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.FiscalTicket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('46', 'Printer.Inventory', 0, $FILE{/com/openbravo/pos/templates/Printer.Inventory.xml});
INSERT INTO resources(id, name, restype, content) VALUES('47', 'Printer.OpenDrawer', 0, $FILE{/com/openbravo/pos/templates/Printer.OpenDrawer.xml});
INSERT INTO resources(id, name, restype, content) VALUES('48', 'Printer.PartialCash', 0, $FILE{/com/openbravo/pos/templates/Printer.PartialCash.xml});
INSERT INTO resources(id, name, restype, content) VALUES('49', 'Printer.PrintLastTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.PrintLastTicket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('50', 'Printer.Product', 0, $FILE{/com/openbravo/pos/templates/Printer.Product.xml});
INSERT INTO resources(id, name, restype, content) VALUES('52', 'Printer.ReprintTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.ReprintTicket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('53', 'Printer.Start', 0, $FILE{/com/openbravo/pos/templates/Printer.Start.xml});
INSERT INTO resources(id, name, restype, content) VALUES('54', 'Printer.Ticket.P1', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P1.xml});
INSERT INTO resources(id, name, restype, content) VALUES('55', 'Printer.Ticket.P2', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P2.xml});
INSERT INTO resources(id, name, restype, content) VALUES('56', 'Printer.Ticket.P3', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P3.xml});
INSERT INTO resources(id, name, restype, content) VALUES('57', 'Printer.Ticket.P4', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P4.xml});
INSERT INTO resources(id, name, restype, content) VALUES('58', 'Printer.Ticket.P5', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P5.xml});
INSERT INTO resources(id, name, restype, content) VALUES('59', 'Printer.Ticket.P6', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P6.xml});
INSERT INTO resources(id, name, restype, content) VALUES('60', 'Printer.Ticket', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('61', 'Printer.Ticket2', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket2.xml});
INSERT INTO resources(id, name, restype, content) VALUES('62', 'Printer.TicketClose', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketClose.xml});
INSERT INTO resources(id, name, restype, content) VALUES('63', 'Printer.TicketLine', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketLine.xml});
INSERT INTO resources(id, name, restype, content) VALUES('64', 'Printer.TicketNew', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketLine.xml});
INSERT INTO resources(id, name, restype, content) VALUES('65', 'Printer.TicketPreview', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketPreview.xml});
INSERT INTO resources(id, name, restype, content) VALUES('66', 'Printer.Ticket_A4', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket_A4.xml});
INSERT INTO resources(id, name, restype, content) VALUES('67', 'Printer.TicketPreview_A4', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketPreview_A4.xml});
INSERT INTO resources(id, name, restype, content) VALUES('68', 'Printer.TicketRemote', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketRemote.xml});
INSERT INTO resources(id, name, restype, content) VALUES('69', 'Printer.TicketTotal', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketTotal.xml});

-- SCRIPTS
INSERT INTO resources(id, name, restype, content) VALUES('70', 'script.Keyboard', 0, $FILE{/com/openbravo/pos/templates/script.Keyboard.txt});
INSERT INTO resources(id, name, restype, content) VALUES('71', 'script.Linediscount', 0, $FILE{/com/openbravo/pos/templates/script.Linediscount.txt});
INSERT INTO resources(id, name, restype, content) VALUES('72', 'script.posapps', 0, $FILE{/com/openbravo/pos/templates/script.posapps.txt});
INSERT INTO resources(id, name, restype, content) VALUES('73', 'script.SendOrder', 0, $FILE{/com/openbravo/pos/templates/script.SendOrder.txt});
INSERT INTO resources(id, name, restype, content) VALUES('74', 'script.Totaldiscount', 0, $FILE{/com/openbravo/pos/templates/script.Totaldiscount.txt});
INSERT INTO resources(id, name, restype, content) VALUES('75', 'script.ServiceCharge', 0, $FILE{/com/openbravo/pos/templates/script.ServiceCharge.txt});
INSERT INTO resources(id, name, restype, content) VALUES('76', 'Printer.CashMovement', 0, $FILE{/com/openbravo/pos/templates/Printer.CashMovement.xml});

-- ADD CATEGORIES
INSERT INTO categories(id, name) VALUES ('000', 'Categoria Estandar');

-- ADD TAXCATEGORIES
/* 002 added 31/01/2017 00:00:00. */
INSERT INTO taxcategories(id, name) VALUES ('00', 'Exento');
INSERT INTO taxcategories(id, name) VALUES ('01', 'Impuesto');
-- ADD TAXES
/* 002 added 31/01/2017 00:00:00. */
INSERT INTO taxes(id, name, category, custcategory, parentid, rate, ratecascade, rateorder) VALUES ('00', 'Imp Exento', '00', NULL, NULL, 0, FALSE, NULL);
INSERT INTO taxes(id, name, category, custcategory, parentid, rate, ratecascade, rateorder) VALUES ('01', 'IVA', '01', NULL, NULL, 0.19, FALSE, NULL);

-- ADD PRODUCTS
INSERT INTO products(id, reference, code, name, category, taxcat, isservice, display, printto) 
VALUES ('xxx999_999xxx_x9x9x9', 'xxx999', 'xxx999', 'Linea en Blanco', '000', '00', 1, '<html><center>Linea en Blanco', '1');

-- ADD PRODUCTS_CAT
INSERT INTO products_cat(product) VALUES ('xxx999_999xxx_x9x9x9');

-- ADD LOCATION
INSERT INTO locations(id, name, address) VALUES ('0','Almacen 1','Local');

-- ADD SUPPLIERS
INSERT INTO suppliers(id, searchkey, name) VALUES ('0','SmartPos','SmartPos');

-- ADD UOM
INSERT INTO uom(id, name) VALUES ('0','Unidad');

-- ADD FLOORS
INSERT INTO floors(id, name, image) VALUES ('0', '1 Restaurante', $FILE{/com/openbravo/images/paperboard960_600.png});
INSERT INTO floors(id, name, image) VALUES ('c335e0ee-a977-4028-9fbe-9daa75939591', '2 Domicilios', $FILE{/com/openbravo/images/domicilios.png});
INSERT INTO floors(id, name, image) VALUES ('978faa5f-24ba-4c8f-b7b8-89c17ce17a15', '3 Para Llevar', $FILE{/com/openbravo/images/mostrador.png});


-- ADD PLACES

INSERT INTO `places` (`id`, `name`, `seats`, `x`, `y`, `floor`, `customer`, `waiter`, `ticketid`, `tablemoved`, `width`, `height`, `guests`, `occupied`) VALUES
	('1645d1b8-a948-46c8-a213-a3007d79f0b3', 'Domicilio 1', '1', 318, 117, 'c335e0ee-a977-4028-9fbe-9daa75939591', NULL, NULL, NULL, 0, 300, 100, 0, NULL),
	('4a518701-bfeb-47ae-9986-ef744f35a0ea', 'Domicilio 3', '1', 320, 442, 'c335e0ee-a977-4028-9fbe-9daa75939591', NULL, NULL, NULL, 0, 300, 100, 0, NULL),
	('5f42db8c-3804-4ea5-aac0-3bb34a3405b4', 'Para llevar', '', 307, 237, '978faa5f-24ba-4c8f-b7b8-89c17ce17a15', NULL, NULL, NULL, 0, 300, 500, 0, NULL),
	('726e8e52-8881-4d6a-ac77-7f16dfa76e59', 'Domicilios.com', '1', 731, 445, 'c335e0ee-a977-4028-9fbe-9daa75939591', NULL, NULL, NULL, 0, 300, 100, 0, NULL),
	('8c19ca45-e99e-46a3-8331-77f8387f163a', 'Web y App', '1', 712, 114, 'c335e0ee-a977-4028-9fbe-9daa75939591', NULL, NULL, NULL, 0, 300, 100, 0, NULL),
	('9a95467e-1e69-43c7-93df-5a81c84867f6', 'Rappi', '1', 711, 276, 'c335e0ee-a977-4028-9fbe-9daa75939591', NULL, NULL, NULL, 0, 300, 100, 0, NULL),
	('c17816d5-81c2-4b96-a438-eb7f42589386', 'Domicilio 2', '1', 320, 278, 'c335e0ee-a977-4028-9fbe-9daa75939591', NULL, NULL, NULL, 0, 300, 100, 0, NULL);

INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('1', 'Mesa  1', 417, 540, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('2', 'Mesa  2', 575, 540, '0', '2', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('3', 'Mesa  3', 414, 400, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('4', 'Mesa  4', 582, 385, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('5', 'Mesa  5', 423, 257, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('6', 'Mesa  6', 583, 253, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('7', 'Mesa  7', 428, 111, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('8', 'Mesa  8', 589, 109, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('9', 'Mesa  9', 749, 110, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('10', 'Mesa  10', 751, 251, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('11', 'Mesa  11', 940, 106, '0', '4', 90, 45);
INSERT INTO places(id, name, x, y, floor, seats, width, height) VALUES ('12', 'Mesa  12', 935, 322, '0', '4', 90, 45);


-- ADD SHIFTS
INSERT INTO shifts(id, startshift, endshift, pplid) VALUES ('0', '2018-01-01 00:00:00.001', '2018-01-01 00:00:00.002','0');

-- ADD BREAKS
INSERT INTO BREAKS(ID, NAME, VISIBLE, NOTES) VALUES ('0', 'Pausa Desayuno', TRUE, NULL);
INSERT INTO BREAKS(ID, NAME, VISIBLE, NOTES) VALUES ('1', 'Pausa Almuerzo', TRUE, NULL);
INSERT INTO BREAKS(ID, NAME, VISIBLE, NOTES) VALUES ('2', 'Pausa Cena', TRUE, NULL);

-- ADD SHIFT_BREAKS
INSERT INTO shift_breaks(id, shiftid, breakid, starttime, endtime) VALUES ('0', '0', '0', '2018-01-01 00:00:00.003', '2018-01-01 00:00:00.004');

-- ADD SEQUENCES
INSERT INTO pickup_number VALUES(1);
INSERT INTO ticketsnum VALUES(1);
INSERT INTO ticketsnum_refund VALUES(1);
INSERT INTO ticketsnum_payment VALUES(1);
INSERT INTO ticketsnum_fe VALUES(1);

-- ADD APPLICATION VERSION
INSERT INTO applications(id, name, version) VALUES($APP_ID{}, $APP_NAME{}, $APP_VERSION{});

SET foreign_key_checks = 1;