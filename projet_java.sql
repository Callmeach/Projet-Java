-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 01 fév. 2022 à 21:19
-- Version du serveur :  10.4.19-MariaDB
-- Version de PHP : 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projet_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

CREATE TABLE `articles` (
  `codeArticle` varchar(10) NOT NULL,
  `libelleArticle` varchar(25) NOT NULL,
  `prix` int(11) NOT NULL,
  `qteStock` int(11) NOT NULL,
  `dateCreation` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`codeArticle`, `libelleArticle`, `prix`, `qteStock`, `dateCreation`) VALUES
('aze', 'Mni', 1234, 0, '2022-02-01'),
('Cd10', 'Blue-rays', 2500, 100, '2022-01-13'),
('Ch0', 'Chaises', 3000, 100, '2022-01-31'),
('IPl', 'iPhone13', 600000, 70, '2022-01-25'),
('Liv', 'Livres', 1500, 100, '2022-01-28'),
('Pts', 'Hamburger', 1000, 30, '2021-12-24'),
('Riv', 'Arme a feu', 120000, 400, '2022-01-13'),
('S2S', 'Ecran', 120000, 0, '2022-02-01'),
('Srv', 'Sacs a main', 10000, 100, '2022-01-29'),
('SSM', 'Bugatti', 98000000, 32, '2022-01-01'),
('Tab', 'Tableaux', 12000, 34, '2022-01-29'),
('Vt7', 'Luis Vuitton', 110000, 66, '2022-01-31'),
('wer', 'Chats', 6000, 42, '2022-01-13'),
('Xrt6', 'Stylos', 125, 1300, '2022-01-29');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `username` varchar(5) NOT NULL DEFAULT 'admin',
  `password` varchar(5) NOT NULL DEFAULT 'admin'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`username`, `password`) VALUES
('admin', 'admin');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`codeArticle`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
