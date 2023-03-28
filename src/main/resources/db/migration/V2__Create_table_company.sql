drop table IF EXISTS `Employee`;

create TABLE `Company` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20) NOT NULL
);