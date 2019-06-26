CREATE TABLE IF NOT EXISTS `category` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `category_name` varchar(255) NOT NULL,
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS`product` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `created_at` datetime DEFAULT NULL,
                                        `name` varchar(255) DEFAULT NULL,
                                        `price` float DEFAULT NULL,
                                        `quantity` int(11) DEFAULT NULL,
                                        `updated_at` datetime DEFAULT NULL,
                                        `imagem` varchar(255) DEFAULT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user` (

                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) DEFAULT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `cpf` varchar(255) DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `nascimento` datetime DEFAULT NULL,
                        `endereco` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users_roles` (
                               `user_id` bigint(20) NOT NULL,
                               `role_id` bigint(20) NOT NULL,
                               KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
                               KEY `FKgd3iendaoyh04b95ykqise6qh` (`user_id`),
                               CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
