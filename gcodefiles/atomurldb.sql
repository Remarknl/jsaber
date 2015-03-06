-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Machine: 127.0.0.1
-- Gegenereerd op: 11 feb 2015 om 17:02
-- Serverversie: 5.6.21
-- PHP-versie: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `atomurldb`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `weburls`
--

CREATE TABLE IF NOT EXISTS `weburls` (
`urlid` int(11) NOT NULL,
  `urlkey` text NOT NULL,
  `url` text NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `weburls`
--

INSERT INTO `weburls` (`urlid`, `urlkey`, `url`, `creationdate`) VALUES
(1, 'nu', 'http://nu.nl', '2015-02-11 00:00:00'),
(2, 'skof', 'http://skoften.net', '2015-02-11 17:01:52');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `weburls`
--
ALTER TABLE `weburls`
 ADD PRIMARY KEY (`urlid`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `weburls`
--
ALTER TABLE `weburls`
MODIFY `urlid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
