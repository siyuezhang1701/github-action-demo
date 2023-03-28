DROP TABLE IF EXISTS `Employee`;

CREATE TABLE `Employee` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20) NOT NULL,
    `age` int,
    `gender` enum('Female','Male', 'Other'),
    `salary` int
);