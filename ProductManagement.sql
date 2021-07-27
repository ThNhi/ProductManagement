CREATE DATABASE ProductManagement
GO
--------------------------------------
USE ProductManagement
GO
---------------------------------------------
CREATE TABLE TblUsers(
userID   varchar(10) not null,
fullName nvarchar(50),
password varchar(50),
status   bit,
constraint pm_users primary key(userID)
)
---------------------------------------------
CREATE TABLE TblCategories(
categoryID   varchar(10) not null,
categoryName nvarchar(50),
description  nvarchar(200),
constraint pm_categories primary key(categoryID)
)
---------------------------------------------
CREATE TABLE TblProducts(
productID   varchar(10) not null,
productName nvarchar(50),
unit        varchar(50),
price       float,
quantity    integer,
categoryid  varchar(10),
constraint pm_products primary key(productID)
)
---------------------------------------------
IF(OBJECT_ID('fk_Products_Categories','F') IS NOT NULL)
	ALTER TABLE TblProducts DROP CONSTRAINT fk_Products_Categories
GO
ALTER TABLE TblProducts ADD CONSTRAINT fk_Products_Categories FOREIGN KEY (categoryID) REFERENCES TblCategories(categoryID)
---------------------------------------------







