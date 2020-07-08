DROP TABLE IF EXISTS `task_tracker`.`user`;

CREATE TABLE `task_tracker`.`user` (
`id` int(11) NOT NULL AUTO_INCREMENT, 
`first_name` varchar(255) DEFAULT NULL, 
`last_name` varchar(255) DEFAULT NULL, 
`email` varchar(255) DEFAULT NULL,
`status` varchar(255) DEFAULT NULL, 
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `task_tracker`.`task`;

CREATE TABLE `task_tracker`.`task` (
`id` int(11) NOT NULL AUTO_INCREMENT, 
`title` varchar(255) DEFAULT NULL, 
`description` varchar(255) DEFAULT NULL,
`status` varchar(255) DEFAULT NULL,
`user_id` int(11) DEFAULT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;









