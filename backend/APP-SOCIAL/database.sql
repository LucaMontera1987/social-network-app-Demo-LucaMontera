-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              8.0.41 - MySQL Community Server - GPL
-- S.O. server:                  Win64
-- HeidiSQL Versione:            12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database app_social
CREATE DATABASE IF NOT EXISTS `app_social` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `app_social`;

-- Dump della struttura di tabella app_social.amicizia
CREATE TABLE IF NOT EXISTS `amicizia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `utente_amicizia_inviata` bigint DEFAULT NULL,
  `utente_amicizia_ricevuta` bigint DEFAULT NULL,
  `stato_amicizia` enum('ACCETTA','RIFIUTATA','SOSPESA') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpx10iayxvrr7o64isg4j6r5dy` (`utente_amicizia_inviata`),
  KEY `FK65qmoouf8ukuc36kpix2jpg4b` (`utente_amicizia_ricevuta`),
  CONSTRAINT `FK65qmoouf8ukuc36kpix2jpg4b` FOREIGN KEY (`utente_amicizia_ricevuta`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpx10iayxvrr7o64isg4j6r5dy` FOREIGN KEY (`utente_amicizia_inviata`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.amicizia: ~37 rows (circa)
INSERT INTO `amicizia` (`id`, `utente_amicizia_inviata`, `utente_amicizia_ricevuta`, `stato_amicizia`) VALUES
	(213, 1, 2, 'ACCETTA'),
	(214, 1, 3, 'ACCETTA'),
	(215, 1, 4, 'SOSPESA'),
	(216, 1, 5, 'ACCETTA'),
	(217, 2, 6, 'SOSPESA'),
	(218, 2, 7, 'SOSPESA'),
	(219, 2, 8, 'ACCETTA'),
	(220, 2, 9, 'SOSPESA'),
	(221, 3, 1, 'SOSPESA'),
	(222, 3, 10, 'ACCETTA'),
	(223, 3, 12, 'SOSPESA'),
	(224, 4, 2, 'SOSPESA'),
	(225, 4, 6, 'SOSPESA'),
	(226, 4, 8, 'SOSPESA'),
	(227, 5, 1, 'ACCETTA'),
	(228, 5, 7, 'SOSPESA'),
	(229, 5, 9, 'SOSPESA'),
	(230, 5, 12, 'SOSPESA'),
	(231, 6, 2, 'SOSPESA'),
	(232, 6, 3, 'SOSPESA'),
	(233, 6, 5, 'SOSPESA'),
	(234, 6, 10, 'ACCETTA'),
	(235, 7, 1, 'ACCETTA'),
	(236, 7, 4, 'SOSPESA'),
	(237, 7, 8, 'SOSPESA'),
	(238, 7, 12, 'SOSPESA'),
	(239, 8, 2, 'ACCETTA'),
	(240, 8, 3, 'SOSPESA'),
	(241, 8, 6, 'SOSPESA'),
	(242, 8, 9, 'SOSPESA'),
	(243, 9, 4, 'SOSPESA'),
	(244, 9, 5, 'SOSPESA'),
	(245, 9, 7, 'SOSPESA'),
	(246, 10, 1, 'ACCETTA'),
	(247, 10, 6, 'ACCETTA'),
	(248, 10, 8, 'SOSPESA'),
	(249, 10, 12, 'ACCETTA');

-- Dump della struttura di tabella app_social.chat_private
CREATE TABLE IF NOT EXISTS `chat_private` (
  `date_chat` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_utente_invia` bigint DEFAULT NULL,
  `id_utente_riceve` bigint DEFAULT NULL,
  `contenuto` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr2qe5qhfa6qsjqxsexc6f9fp8` (`id_utente_invia`),
  KEY `FKt6nqtj95hfvvkopva5vd62umb` (`id_utente_riceve`),
  CONSTRAINT `FKr2qe5qhfa6qsjqxsexc6f9fp8` FOREIGN KEY (`id_utente_invia`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt6nqtj95hfvvkopva5vd62umb` FOREIGN KEY (`id_utente_riceve`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.chat_private: ~0 rows (circa)

-- Dump della struttura di tabella app_social.commento
CREATE TABLE IF NOT EXISTS `commento` (
  `data` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_post` bigint DEFAULT NULL,
  `id_user` bigint DEFAULT NULL,
  `contenuto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK83tkeski8mi777mw9qn9a91bf` (`id_post`),
  KEY `FKkijeiou0jnerhi3tygd0xq7p2` (`id_user`),
  CONSTRAINT `FK83tkeski8mi777mw9qn9a91bf` FOREIGN KEY (`id_post`) REFERENCES `post` (`id`),
  CONSTRAINT `FKkijeiou0jnerhi3tygd0xq7p2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.commento: ~0 rows (circa)

-- Dump della struttura di tabella app_social.foto
CREATE TABLE IF NOT EXISTS `foto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_post` bigint DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `foto_caricate` bigint DEFAULT NULL,
  `foto_lista_foto_profilo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKajbih9wpmfisjpdt0k591fhh6` (`id_post`),
  KEY `FKjtwdtej2ko8xm3b57c2oj5hfo` (`foto_caricate`),
  KEY `FK6tcruqo2og0fecu4qy671nxfg` (`foto_lista_foto_profilo`),
  CONSTRAINT `FK6tcruqo2og0fecu4qy671nxfg` FOREIGN KEY (`foto_lista_foto_profilo`) REFERENCES `profilo` (`id`),
  CONSTRAINT `FKajbih9wpmfisjpdt0k591fhh6` FOREIGN KEY (`id_post`) REFERENCES `post` (`id`),
  CONSTRAINT `FKjtwdtej2ko8xm3b57c2oj5hfo` FOREIGN KEY (`foto_caricate`) REFERENCES `profilo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.foto: ~25 rows (circa)
INSERT INTO `foto` (`id`, `id_post`, `url`, `foto_caricate`, `foto_lista_foto_profilo`) VALUES
	(47, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755823312/social/profilo/ojj4mknnkcnpv33tmq5z.jpg', NULL, 1),
	(48, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755853890/social/profilo/profilo_1.jpg', NULL, 1),
	(49, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755863059/social/profilo/profilo_11.jpg', NULL, 11),
	(50, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755863060/social/profilo/profilo_11.jpg', NULL, 11),
	(51, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755897541/social/profilo/profilo_3.jpg', NULL, 3),
	(52, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755897547/social/profilo/profilo_3.jpg', NULL, 3),
	(53, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755897618/social/profilo/profilo_3.jpg', NULL, 3),
	(54, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755897718/social/profilo/profilo_3.jpg', NULL, 3),
	(55, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755897819/social/profilo/profilo_3.jpg', NULL, 3),
	(56, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755899109/social/profilo/profilo_3.jpg', NULL, 3),
	(57, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755899579/social/profilo/profilo_1.jpg', NULL, 1),
	(58, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755900618/social/profilo/profilo_1.jpg', NULL, 1),
	(59, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755900715/social/profilo/profilo_1.jpg', NULL, 1),
	(60, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755900715/social/profilo/profilo_1.jpg', NULL, 1),
	(61, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755900715/social/profilo/profilo_1.jpg', NULL, 1),
	(62, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755902265/social/profilo/profilo_1.jpg', NULL, 1),
	(63, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755902731/social/profilo/profilo_1.jpg', NULL, 1),
	(64, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755902739/social/profilo/profilo_1.jpg', NULL, 1),
	(65, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755902873/social/profilo/profilo_1.jpg', NULL, 1),
	(66, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755902947/social/profilo/profilo_1.jpg', NULL, 1),
	(67, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755904135/social/profilo/profilo_1.jpg', NULL, 1),
	(68, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755904135/social/profilo/profilo_1.jpg', NULL, 1),
	(69, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755904432/social/profilo/profilo_1.jpg', NULL, 1),
	(70, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755905682/social/profilo/fotoacb15b9c-6d88-4dc9-901c-2d51f576ebb9.jpg', NULL, 1),
	(71, NULL, 'https://res.cloudinary.com/dagumzxln/image/upload/v1755905749/social/profilo/foto47064ad8-5d61-488a-a78f-ab6f39484cb0.jpg', NULL, 3);

-- Dump della struttura di tabella app_social.lavoro
CREATE TABLE IF NOT EXISTS `lavoro` (
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lavoro_profilo` bigint DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpncpwuiejfhc13vet12cgppbb` (`lavoro_profilo`),
  CONSTRAINT `FKpncpwuiejfhc13vet12cgppbb` FOREIGN KEY (`lavoro_profilo`) REFERENCES `profilo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.lavoro: ~0 rows (circa)

-- Dump della struttura di tabella app_social.likes
CREATE TABLE IF NOT EXISTS `likes` (
  `a_chi_piace` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_post` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKry69dahwy1wfxoe0mrkthi8ay` (`a_chi_piace`),
  KEY `FK9e6id0navspjvg5swqwtl3fy6` (`id_post`),
  CONSTRAINT `FK9e6id0navspjvg5swqwtl3fy6` FOREIGN KEY (`id_post`) REFERENCES `post` (`id`),
  CONSTRAINT `FKry69dahwy1wfxoe0mrkthi8ay` FOREIGN KEY (`a_chi_piace`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.likes: ~0 rows (circa)

-- Dump della struttura di tabella app_social.notifica
CREATE TABLE IF NOT EXISTS `notifica` (
  `date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_autore` bigint DEFAULT NULL,
  `id_destinatario` bigint DEFAULT NULL,
  `id_evento` bigint DEFAULT NULL,
  `contenuto` varchar(255) DEFAULT NULL,
  `stato` enum('LETTO','NON_lETTO') DEFAULT NULL,
  `tipo_notifica` enum('AMICIZIA','COMMENTO','LIKE','POST') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtr68ohpamdpg642y55rhx60bx` (`id_autore`),
  CONSTRAINT `FKtr68ohpamdpg642y55rhx60bx` FOREIGN KEY (`id_autore`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.notifica: ~0 rows (circa)

-- Dump della struttura di tabella app_social.post
CREATE TABLE IF NOT EXISTS `post` (
  `autore` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `testo` varchar(255) DEFAULT NULL,
  `profilo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ed2uak6ggygv6qunispod2d8` (`autore`),
  KEY `FKor49dx07i240s2l51ik9pabv` (`profilo`),
  CONSTRAINT `FK6ed2uak6ggygv6qunispod2d8` FOREIGN KEY (`autore`) REFERENCES `user` (`id`),
  CONSTRAINT `FKor49dx07i240s2l51ik9pabv` FOREIGN KEY (`profilo`) REFERENCES `profilo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.post: ~0 rows (circa)

-- Dump della struttura di tabella app_social.profilo
CREATE TABLE IF NOT EXISTS `profilo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `immagine_di_copertina` bigint DEFAULT NULL,
  `immagine_di_profilo` bigint DEFAULT NULL,
  `user_profilo` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpk1am3hwgiw3ggka4bsd3ho02` (`user_profilo`),
  KEY `FKepi6yen5f2bbgkxpgdt304yfe` (`immagine_di_copertina`),
  KEY `FKh9ufeols245uwx26290g9cxau` (`immagine_di_profilo`),
  CONSTRAINT `FK5yqlkqxywxbjgqbojv81powh0` FOREIGN KEY (`user_profilo`) REFERENCES `user` (`id`),
  CONSTRAINT `FKepi6yen5f2bbgkxpgdt304yfe` FOREIGN KEY (`immagine_di_copertina`) REFERENCES `foto` (`id`),
  CONSTRAINT `FKh9ufeols245uwx26290g9cxau` FOREIGN KEY (`immagine_di_profilo`) REFERENCES `foto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.profilo: ~11 rows (circa)
INSERT INTO `profilo` (`id`, `immagine_di_copertina`, `immagine_di_profilo`, `user_profilo`, `username`) VALUES
	(1, 69, 70, 1, NULL),
	(2, 47, 48, NULL, NULL),
	(3, 56, 71, 3, NULL),
	(4, 56, 71, NULL, NULL),
	(5, 56, 71, NULL, NULL),
	(6, 56, 71, NULL, NULL),
	(7, 56, 71, NULL, NULL),
	(8, 56, 71, NULL, NULL),
	(9, 56, 71, NULL, NULL),
	(10, 56, 71, NULL, NULL),
	(11, 49, 50, NULL, NULL);

-- Dump della struttura di tabella app_social.scuola
CREATE TABLE IF NOT EXISTS `scuola` (
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `scuola_profilo` bigint DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmovhnn3dh2j37hlyanyudxcdl` (`scuola_profilo`),
  CONSTRAINT `FKmovhnn3dh2j37hlyanyudxcdl` FOREIGN KEY (`scuola_profilo`) REFERENCES `profilo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.scuola: ~0 rows (circa)

-- Dump della struttura di tabella app_social.user
CREATE TABLE IF NOT EXISTS `user` (
  `data_di_nascita` date NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `id_profilo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkh8i3c3hgctnesntcqet1171i` (`id_profilo`),
  CONSTRAINT `FKfxtnryvc4cdmswqycfgv6lqim` FOREIGN KEY (`id_profilo`) REFERENCES `profilo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump dei dati della tabella app_social.user: ~11 rows (circa)
INSERT INTO `user` (`data_di_nascita`, `id`, `cognome`, `email`, `nome`, `password`, `roles`, `username`, `id_profilo`) VALUES
	('2025-08-20', 1, 'Montera', 'luca@email', 'luca', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'luca.montera', 1),
	('2025-08-22', 2, 'Rossi', 'mario.rossi@example.com', 'Mario', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'mrossi', 11),
	('1992-11-05', 3, 'Sereni', 'anna.sereni@example.com', 'Anna', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'asereni', 3),
	('1988-02-28', 4, 'Verdi', 'giulia.verdi@example.com', 'Giulia', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'gverdi', 4),
	('1983-12-10', 5, 'Di Mauro', 'francesco.dimauro@example.com', 'Francesco', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'MODERATOR', 'fdimauro', 5),
	('1995-05-19', 6, 'Moretti', 'sara.moretti@example.com', 'Sara', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'smoretti', 6),
	('1987-08-09', 7, 'Conti', 'paolo.conti@example.com', 'Paolo', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'ADMIN', 'pconti', 7),
	('1993-09-25', 8, 'Galli', 'elena.galli@example.com', 'Elena', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'egalli', 8),
	('1991-04-14', 9, 'Marini', 'davide.marini@example.com', 'Davide', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'dmarini', 9),
	('1989-01-30', 10, 'Barbieri', 'federica.barbieri@example.com', 'Federica', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'USER', 'fbarbieri', 10),
	('1990-07-22', 12, 'Bianchi', 'luca.bianchi@example.com', 'Luca', '$2a$10$YL8qNSNQNKGaGaXyywMbQufdaQdcH.moowft7tzuGQ4FZNYzwn2r2', 'ADMIN', 'lbianchi', 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
