-- MySQL dump 10.13  Distrib 8.0.16, for Linux (x86_64)
--
-- Host: localhost    Database: db_delivery
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--


/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER'),(5,'ROLE_USER'),(6,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `users_roles` (
                               `user_id` bigint(20) NOT NULL,
                               `role_id` bigint(20) NOT NULL,
                               KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
                               KEY `FKgd3iendaoyh04b95ykqise6qh` (`user_id`),
                               CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--


/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,3),(5,6);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `created_at` datetime DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `price` float DEFAULT NULL,
                           `quantity` int(11) DEFAULT NULL,
                           `updated_at` datetime DEFAULT NULL,
                           `imagem` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'2019-06-08 10:13:16','Feijoada',100,2,'2019-06-08 14:35:35','Maksoud-Plaza-Aniversario-da-Cidade.jpg'),(2,'2019-06-08 14:13:21','Cuscuz',10,1,'2019-06-08 14:35:48','320761.webp'),(3,'2019-06-23 11:25:30','Lasanha à bolonhesa',12,1,'2019-06-23 11:25:30','receitas-lasanha.jpg'),(4,'2019-06-23 11:28:25','Tapioca de Carne de Sol',10,1,'2019-06-23 11:28:25','receita-tapioca-carne-de-sol.jpg'),(5,'2019-06-23 11:29:34','X-Burguer',7,1,'2019-06-23 14:18:00','xburguer.png'),(6,'2019-06-23 13:29:17','Pastel de Carne',4,1,'2019-06-23 13:29:17','pastel-de-carne-moida.jpg'),(12,'2019-06-25 10:11:33','Macarronada',6,2,'2019-06-25 10:15:49','macarronada.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--


/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@gmail.com','admin','$2a$04$jJXpmfs2Gxdu4dWfYLN6xujR03Ixj3BbnMjWqZuU8EhcpcUWGFaz2',NULL,NULL,NULL,NULL),
                          (2,'teste@mail.com','teste','$2a$10$qg9PchWqgX8jlkbp5gXQCuNg6UKGebGxCSqS4TlQOe8geFJ9ld.C2',NULL,NULL,NULL,NULL),
                          (3,'mensones@mail.com','mensones','$2a$10$5VxCNyGtXUs99RmYAdg8ju.S9F8qG8A1yTTR8b6Tg5NbamkOJfP8y','00000000000','Emerson Vieira','1991-07-27 00:00:00','Rua Dr. Rui Maia, 450 apto C');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-26  8:22:54