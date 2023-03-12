-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 02, 2020 at 09:34 AM
-- Server version: 5.7.30
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vose`
--

-- --------------------------------------------------------

--
-- Table structure for table `ar_admin`
--

CREATE TABLE `ar_admin` (
  `id` int(11) NOT NULL,
  `isim_soyisim` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `sifre` varchar(32) NOT NULL,
  `telefon` varchar(20) NOT NULL,
  `adres` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_admin`
--

INSERT INTO `ar_admin` (`id`, `isim_soyisim`, `email`, `sifre`, `telefon`, `adres`) VALUES
(1, 'John Doe', 'johndoe@gmail.com', 'john', '+(90) (596) 888 6666', 'Kocaeli, Türkiye');

-- --------------------------------------------------------

--
-- Table structure for table `ar_adresler`
--

CREATE TABLE `ar_adresler` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) NOT NULL,
  `baslik` varchar(155) NOT NULL,
  `alici_adi` varchar(100) NOT NULL,
  `alici_tel` varchar(20) NOT NULL,
  `adres` text NOT NULL,
  `posta_kodu` varchar(50) NOT NULL,
  `ilce` varchar(50) NOT NULL,
  `il` varchar(50) NOT NULL,
  `ulke` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ar_adresler`
--

INSERT INTO `ar_adresler` (`id`, `bayi_id`, `baslik`, `alici_adi`, `alici_tel`, `adres`, `posta_kodu`, `ilce`, `il`, `ulke`) VALUES
(1, 1, 'Ev', 'Kadir Tokmak', '05555555555', 'Yakuplu Mah. No:305/855', '34000', 'Beylikdüzü', 'İstanbul', 'Türkiye'),
(2, 1, 'iş', 'Kadir Tokmak', '5556787654', 'deneme bir kii deneme bir kii', '340098', 'Sultangazi', 'İstanbul', 'Türkiye'),
(3, 8, 'Deneme', 'Esra Şahin', '05556545454', 'deneme sok. tes apt. no:1/2', '21341', 'Elmalı', 'Kırıkkale', 'Türkiye'),
(4, 7, 'Ev', 'Esma Şahin', '05556545454', 'sadfgdsg', '34543', 'ewewr', 'Kırıkkale', 'Türkiye'),
(5, 13, 'Ev', 'Veli Alili', '055543232221', 'deneme mah. test sok.', '213124', 'asdasd', 'Denizli', 'Türkiye');

-- --------------------------------------------------------

--
-- Table structure for table `ar_aktiflik`
--

CREATE TABLE `ar_aktiflik` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) NOT NULL,
  `ay` varchar(4) NOT NULL,
  `urun_sayisi` int(11) NOT NULL,
  `guncelleme` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_aktiflik`
--

INSERT INTO `ar_aktiflik` (`id`, `bayi_id`, `ay`, `urun_sayisi`, `guncelleme`) VALUES
(6, 2, '0720', 5, '2020-07-09 12:37:29'),
(7, 5, '0720', 2, '2020-07-09 12:39:46'),
(8, 4, '0720', 5, '2020-07-09 13:09:34'),
(9, 1, '0720', 8, '2020-07-26 17:03:16'),
(10, 8, '0720', 7, '2020-07-26 02:02:48'),
(11, 7, '0720', 16, '2020-07-26 17:59:49'),
(12, 13, '0720', 2, '2020-07-26 18:27:27');

-- --------------------------------------------------------

--
-- Table structure for table `ar_app_settings`
--

CREATE TABLE `ar_app_settings` (
  `name` varchar(30) NOT NULL,
  `value` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_app_settings`
--

INSERT INTO `ar_app_settings` (`name`, `value`) VALUES
('bankaAdi', 'Garanti BBVA'),
('bankaAlici', 'Vose Ticaret ve Pazarlama'),
('bankaIban', 'TR11 2222 3333 4444 5555 6666 77'),
('bankaNotlar', 'Gönder gelsin :)'),
('cvMatchAmount', '10'),
('kargoFirma', 'Yurtiçi Kargo 2'),
('kargoUcret', '23.56'),
('kargoUstLimit', '300'),
('pvMaxSinglePercentage', '40');

-- --------------------------------------------------------

--
-- Table structure for table `ar_bayiler`
--

CREATE TABLE `ar_bayiler` (
  `id` int(11) NOT NULL,
  `isim` varchar(50) NOT NULL,
  `soyisim` varchar(50) NOT NULL,
  `tckn` varchar(11) NOT NULL,
  `dogum_tarihi` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefon1` varchar(20) NOT NULL,
  `telefon2` varchar(20) DEFAULT NULL,
  `ulke` varchar(50) NOT NULL,
  `sehir` varchar(50) NOT NULL,
  `sifre` varchar(32) NOT NULL,
  `tarih` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `kaydeden` varchar(50) DEFAULT NULL,
  `kayit_durum` tinyint(11) NOT NULL DEFAULT '0',
  `sponsor_id` int(11) DEFAULT NULL,
  `kariyer_id` int(11) NOT NULL,
  `aybasi_kariyer_id` int(11) DEFAULT NULL,
  `derinlik` int(11) NOT NULL DEFAULT '1',
  `kol_sira` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_bayiler`
--

INSERT INTO `ar_bayiler` (`id`, `isim`, `soyisim`, `tckn`, `dogum_tarihi`, `email`, `telefon1`, `telefon2`, `ulke`, `sehir`, `sifre`, `tarih`, `kaydeden`, `kayit_durum`, `sponsor_id`, `kariyer_id`, `aybasi_kariyer_id`, `derinlik`, `kol_sira`) VALUES
(1, 'Kadir', 'Tokmak', '12345678900', '1996-01-03', 'kadir@gmail.com', '05435434343', NULL, 'Türkiye', 'İstanbul', '123456', '2020-07-04 11:09:52', NULL, 1, NULL, 11, 11, 1, 1),
(2, 'Ahmet Can', 'Talay', '12345679800', '1990-08-10', 'ahmettly38@gmail.com', '05435434343', '+905056788989', 'Türkiye', 'Kayseri', '123456', '2020-07-04 17:03:48', 'kadir', 1, 1, 2, 2, 2, 1),
(4, 'Ahmet', 'Şahin', '22345678900', '1978-10-10', 'ahmet@mail.com', '05556755555', NULL, 'Türkiye', 'Kayseri', '123456', '2020-07-05 11:31:39', 'kadir', 1, 2, 2, 2, 3, 1),
(5, 'Emre', 'Şahin', '32332132132', '1998-10-10', 'emrererero@hotmail.com', '05435434343', NULL, 'Türkiye', 'İstanbul', '123456', '2020-07-06 07:48:57', 'Ahmet Can', 1, 2, 2, 2, 3, 2),
(6, 'Mustafa', 'Şahin', '24324233434', '1990-11-11', 'mustafallb@gmail.com', '05544323221', NULL, 'Türkiye', 'İstanbul', '123456', '2020-07-09 14:26:19', NULL, 1, 1, 5, 5, 2, 2),
(7, 'Esma', 'Şahin', '12312312333', '1994-06-05', 'esma@mail.com', '05556545454', NULL, 'Türkiye', 'Kırıkkale', '123456', '2020-07-09 14:30:12', NULL, 1, 6, 3, 3, 3, 2),
(8, 'Esra', 'Şahin', '12312312332', '1977-10-05', 'esraa@mail.com', '05556545454', NULL, 'Türkiye', 'Kırıkkale', '123456', '2020-07-09 14:31:22', NULL, 0, 6, 3, NULL, 3, 3),
(9, 'Rumeysa', 'Şahin', '12412312332', '1999-01-14', 'rumisa@mail.com', '05556545454', NULL, 'Türkiye', 'Kırıkkale', '123456', '2020-07-09 14:31:22', NULL, 0, 6, 3, NULL, 3, 4),
(11, 'Fatih', 'Mehmet', '11122234566', '2000-08-10', 'fatihlgz@icloud.com', '05556546565', NULL, 'Türkiye', 'Osmaniye', '654321', '2020-07-19 10:46:07', NULL, 0, 1, 2, NULL, 2, 3),
(13, 'Veli', 'Alili', '00000000001', '1990-01-01', 'veli@mail.com', '055543232221', NULL, 'Türkiye', 'Denizli', '123456', '2020-07-26 15:20:39', NULL, 1, 11, 21, 21, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ar_bayi_puan`
--

CREATE TABLE `ar_bayi_puan` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) NOT NULL,
  `alt_bayi_id` int(11) NOT NULL,
  `pv` double NOT NULL,
  `cv` double NOT NULL,
  `tarih` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_bayi_puan`
--

INSERT INTO `ar_bayi_puan` (`id`, `bayi_id`, `alt_bayi_id`, `pv`, `cv`, `tarih`) VALUES
(6, 1, 2, 122.50007, 124.04, '2020-07-09 13:09:36'),
(7, 2, 5, 62.4, 29.200000000000003, '2020-07-09 13:12:01'),
(8, 2, 4, 40.349999999999994, 3.6799999999999997, '2020-07-09 13:12:38'),
(9, 6, 8, 22.85, 32.55, '2020-07-26 02:01:02'),
(10, 1, 6, 109.55000000000001, 146.85000000000002, '2020-07-26 02:01:02'),
(11, 6, 7, 86.7, 114.30000000000001, '2020-07-26 17:50:23'),
(12, 11, 13, 15.6, 19.8, '2020-07-26 18:27:27'),
(13, 1, 11, 15.6, 19.8, '2020-07-26 18:27:27');

-- --------------------------------------------------------

--
-- Table structure for table `ar_cuzdan`
--

CREATE TABLE `ar_cuzdan` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) NOT NULL,
  `miktar` double NOT NULL,
  `yon` tinyint(2) NOT NULL,
  `aciklama` varchar(150) DEFAULT NULL,
  `cuzdan_toplam` double NOT NULL,
  `tarih` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_cuzdan`
--

INSERT INTO `ar_cuzdan` (`id`, `bayi_id`, `miktar`, `yon`, `aciklama`, `cuzdan_toplam`, `tarih`) VALUES
(9, 2, 0, 1, 'Temmuz 2020 toplam kazancınız.', 0, '2020-07-11 13:57:22'),
(10, 1, 148.46, 1, 'Temmuz 2020 toplam kazancınız.', 148.46, '2020-07-11 13:57:22'),
(11, 2, 1, 1, 'Temmuz 2020 kazancınız.', 1, '2020-07-11 14:07:28'),
(12, 1, 0.05, 1, 'Temmuz 2020 kazancınız.', 148.51, '2020-07-11 14:07:28'),
(13, 1, 1550, 1, 'Temmuz 2020 kazancınız.', 1698.51, '2020-07-11 14:12:24');

-- --------------------------------------------------------

--
-- Table structure for table `ar_jobs`
--

CREATE TABLE `ar_jobs` (
  `id` int(11) NOT NULL,
  `root_id` int(11) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `args` varchar(100) NOT NULL,
  `generation` varchar(50) DEFAULT NULL,
  `processed` tinyint(4) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `process_date` datetime DEFAULT NULL,
  `success` tinyint(1) NOT NULL DEFAULT '0',
  `fail_log_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_jobs`
--

INSERT INTO `ar_jobs` (`id`, `root_id`, `type`, `args`, `generation`, `processed`, `create_date`, `process_date`, `success`, `fail_log_id`) VALUES
(64, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":1}', NULL, 1, '2020-07-09 12:06:53', '2020-07-11 13:50:34', 1, NULL),
(65, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":2}', NULL, 1, '2020-07-09 12:12:06', '2020-07-11 13:50:35', 1, NULL),
(66, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":3}', NULL, 1, '2020-07-09 12:15:47', '2020-07-11 13:50:39', 1, NULL),
(183, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":4}', NULL, 1, '2020-07-09 12:15:47', '2020-07-11 13:50:39', 1, NULL),
(184, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":5}', NULL, 1, '2020-07-09 12:15:47', '2020-07-11 13:57:19', 1, NULL),
(185, 64, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"pv\":3.45,\"cv\":5.32}', '64', 1, '2020-07-11 13:50:34', '2020-07-11 13:50:35', 1, NULL),
(186, 64, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"tutar\":9.04,\"kazancTuru\":\"BAYI_FARK_KAZANCI\"}', '64', 1, '2020-07-11 13:50:34', '2020-07-11 13:50:35', 1, NULL),
(187, 64, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"tutar\":2.79,\"kazancTuru\":\"EKIP_ALISVERIS_PRIMI\"}', '64', 1, '2020-07-11 13:50:34', '2020-07-11 13:50:35', 1, NULL),
(188, 65, 'PUAN_EKLE', '{\"bayiId\":2,\"altBayiId\":5,\"pv\":15.6,\"cv\":19.8}', '65', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:35', 1, NULL),
(189, 65, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"pv\":15.6,\"cv\":19.8}', '65', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:35', 1, NULL),
(190, 65, 'KAZANC_EKLE', '{\"bayiId\":2,\"altBayiId\":5,\"tutar\":0.0,\"kazancTuru\":\"BAYI_FARK_KAZANCI\"}', '65', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:35', 1, NULL),
(191, 65, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":5,\"tutar\":17.94,\"kazancTuru\":\"EKIP_ALISVERIS_PRIMI\"}', '65', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:36', 1, NULL),
(192, 64, 'KARIYER_CHECK', '{\"bayiId\":1}', '64,185', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:35', 1, NULL),
(193, 65, 'KARIYER_CHECK', '{\"bayiId\":2}', '65,188', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:36', 1, NULL),
(194, 65, 'KARIYER_CHECK', '{\"bayiId\":1}', '65,189', 1, '2020-07-11 13:50:35', '2020-07-11 13:50:36', 1, NULL),
(195, 66, 'PUAN_EKLE', '{\"bayiId\":2,\"altBayiId\":4,\"pv\":13.45,\"cv\":14.56}', '66', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:39', 1, NULL),
(196, 66, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"pv\":13.45,\"cv\":14.56}', '66', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(197, 66, 'KAZANC_EKLE', '{\"bayiId\":2,\"altBayiId\":4,\"tutar\":0.0,\"kazancTuru\":\"BAYI_FARK_KAZANCI\"}', '66', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(198, 66, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":4,\"tutar\":16.44,\"kazancTuru\":\"EKIP_ALISVERIS_PRIMI\"}', '66', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(199, 183, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"pv\":25.0,\"cv\":25.0}', '183', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(200, 183, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"tutar\":77.25,\"kazancTuru\":\"BAYI_FARK_KAZANCI\"}', '183', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(201, 183, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"tutar\":25.0,\"kazancTuru\":\"EKIP_ALISVERIS_PRIMI\"}', '183', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(202, 66, 'KARIYER_CHECK', '{\"bayiId\":2}', '66,195', 1, '2020-07-11 13:50:39', '2020-07-11 13:50:40', 1, NULL),
(203, 66, 'KARIYER_CHECK', '{\"bayiId\":1}', '66,196', 1, '2020-07-11 13:50:40', '2020-07-11 13:50:40', 1, NULL),
(204, 183, 'KARIYER_CHECK', '{\"bayiId\":1}', '183,199', 1, '2020-07-11 13:50:40', '2020-07-11 13:50:41', 1, NULL),
(205, NULL, 'AY_SONU', '{}', NULL, 1, '2020-07-09 12:15:47', '2020-07-11 13:57:20', 1, NULL),
(206, NULL, 'CUZDANA_ISLE', '{}', NULL, 1, '2020-07-09 12:15:47', '2020-07-11 13:57:20', 1, NULL),
(207, 205, 'AY_SONU_BAYI', '{\"bayiId\":4,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 14:07:26', 1, NULL),
(208, 205, 'AY_SONU_BAYI', '{\"bayiId\":5,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 14:07:26', 1, NULL),
(209, 205, 'AY_SONU_BAYI', '{\"bayiId\":7,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:20', 1, NULL),
(210, 205, 'AY_SONU_BAYI', '{\"bayiId\":8,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:20', 1, NULL),
(211, 205, 'AY_SONU_BAYI', '{\"bayiId\":9,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:20', 1, NULL),
(212, 205, 'AY_SONU_BAYI', '{\"bayiId\":2,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 14:07:26', 1, NULL),
(213, 205, 'AY_SONU_BAYI', '{\"bayiId\":6,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:21', 1, NULL),
(214, 205, 'AY_SONU_BAYI', '{\"bayiId\":1,\"monthKey\":\"0720\"}', '205', 1, '2020-07-11 13:57:20', '2020-07-11 14:12:23', 1, NULL),
(215, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":4,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:21', 1, NULL),
(216, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":5,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:21', 1, NULL),
(217, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":7,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:21', 1, NULL),
(218, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":8,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:21', 1, NULL),
(219, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":9,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:21', 1, NULL),
(220, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":2,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:22', 1, NULL),
(221, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":6,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:22', 1, NULL),
(222, 206, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":1,\"monthKey\":\"0720\"}', '206', 1, '2020-07-11 13:57:20', '2020-07-11 13:57:22', 1, NULL),
(224, NULL, 'CUZDANA_ISLE', '{}', NULL, 1, '2020-07-11 14:06:52', '2020-07-11 14:07:26', 1, NULL),
(225, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":4,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(226, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":5,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(227, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":7,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(228, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":8,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(229, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":9,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(230, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":2,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:28', 1, NULL),
(231, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":6,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:28', 1, NULL),
(232, 224, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":1,\"monthKey\":\"0720\"}', '224', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:28', 1, NULL),
(233, 205, 'KAZANC_EKLE', '{\"bayiId\":2,\"tutar\":1.0,\"kazancTuru\":\"BINARY_KAZANCI\"}', '205,212', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(234, 205, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":2,\"tutar\":0.05,\"kazancTuru\":\"EKIP_KAZANC_PRIMI\"}', '205,212', 1, '2020-07-11 14:07:26', '2020-07-11 14:07:27', 1, NULL),
(236, NULL, 'CUZDANA_ISLE', '{}', NULL, 1, '2020-07-11 14:11:51', '2020-07-11 14:12:23', 1, NULL),
(237, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":4,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:23', 1, NULL),
(238, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":5,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(239, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":7,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(240, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":8,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(241, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":9,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(242, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":2,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(243, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":6,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(244, 236, 'CUZDANA_ISLE_BAYI', '{\"bayiId\":1,\"monthKey\":\"0720\"}', '236', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:24', 1, NULL),
(245, 205, 'KAZANC_EKLE', '{\"bayiId\":1,\"tutar\":1550.0,\"kazancTuru\":\"LIDER_DESTEK_PRIMI\"}', '205,214', 1, '2020-07-11 14:12:23', '2020-07-11 14:12:23', 1, NULL),
(247, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":25}', NULL, 1, '2020-07-26 02:01:01', '2020-07-26 02:01:02', 1, NULL),
(248, 247, 'PUAN_EKLE', '{\"bayiId\":6,\"altBayiId\":8,\"pv\":13.6,\"cv\":20.1}', '247', 1, '2020-07-26 02:01:02', '2020-07-26 02:01:02', 1, NULL),
(249, 247, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":6,\"pv\":13.6,\"cv\":20.1}', '247', 1, '2020-07-26 02:01:02', '2020-07-26 02:01:02', 1, NULL),
(250, 247, 'KARIYER_CHECK', '{\"bayiId\":6}', '247,248', 1, '2020-07-26 02:01:02', '2020-07-26 02:01:02', 1, NULL),
(251, 247, 'KARIYER_CHECK', '{\"bayiId\":1}', '247,249', 1, '2020-07-26 02:01:02', '2020-07-26 02:01:02', 1, NULL),
(252, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":26}', NULL, 1, '2020-07-26 02:02:45', '2020-07-26 02:02:48', 1, NULL),
(253, 252, 'PUAN_EKLE', '{\"bayiId\":6,\"altBayiId\":8,\"pv\":9.25,\"cv\":12.45}', '252', 1, '2020-07-26 02:02:48', '2020-07-26 02:02:48', 1, NULL),
(254, 252, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":6,\"pv\":9.25,\"cv\":12.45}', '252', 1, '2020-07-26 02:02:48', '2020-07-26 02:02:48', 1, NULL),
(255, 252, 'KARIYER_CHECK', '{\"bayiId\":6}', '252,253', 1, '2020-07-26 02:02:48', '2020-07-26 02:02:48', 1, NULL),
(256, 252, 'KARIYER_CHECK', '{\"bayiId\":1}', '252,254', 1, '2020-07-26 02:02:48', '2020-07-26 02:02:48', 1, NULL),
(257, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":22}', NULL, 1, '2020-07-26 11:15:55', '2020-07-26 11:15:55', 1, NULL),
(258, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":28}', NULL, 1, '2020-07-26 17:03:15', '2020-07-26 17:03:16', 1, NULL),
(259, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":29}', NULL, 1, '2020-07-26 17:50:21', '2020-07-26 17:50:22', 1, NULL),
(260, 259, 'PUAN_EKLE', '{\"bayiId\":6,\"altBayiId\":7,\"pv\":24.3,\"cv\":35.1}', '259', 1, '2020-07-26 17:50:22', '2020-07-26 17:50:23', 1, NULL),
(261, 259, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":6,\"pv\":24.3,\"cv\":35.1}', '259', 1, '2020-07-26 17:50:22', '2020-07-26 17:50:23', 1, NULL),
(262, 259, 'KARIYER_CHECK', '{\"bayiId\":6}', '259,260', 1, '2020-07-26 17:50:23', '2020-07-26 17:50:23', 1, NULL),
(263, 259, 'KARIYER_CHECK', '{\"bayiId\":1}', '259,261', 1, '2020-07-26 17:50:23', '2020-07-26 17:50:23', 1, NULL),
(264, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":30}', NULL, 1, '2020-07-26 17:59:49', '2020-07-26 17:59:49', 1, NULL),
(265, 264, 'KAYIT_TAMAMLA', '{\"bayiId\":7}', '264', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:49', 1, NULL),
(266, 264, 'PUAN_EKLE', '{\"bayiId\":6,\"altBayiId\":7,\"pv\":62.4,\"cv\":79.2}', '264', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:49', 1, NULL),
(267, 264, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":6,\"pv\":62.4,\"cv\":79.2}', '264', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:50', 1, NULL),
(268, 264, 'KAZANC_EKLE', '{\"bayiId\":6,\"altBayiId\":7,\"tutar\":150.0,\"kazancTuru\":\"SPONSOR_FARK_KAZANCI\"}', '264,265', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:50', 1, NULL),
(269, 264, 'KAZANC_EKLE', '{\"bayiId\":6,\"altBayiId\":7,\"tutar\":300.0,\"kazancTuru\":\"SPONSOR_PRIMI\"}', '264,265', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:50', 1, NULL),
(270, 264, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":7,\"tutar\":75.0,\"kazancTuru\":\"SPONSOR_PRIMI\"}', '264,265', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:50', 1, NULL),
(271, 264, 'KARIYER_CHECK', '{\"bayiId\":6}', '264,266', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:50', 1, NULL),
(272, 264, 'KARIYER_CHECK', '{\"bayiId\":1}', '264,267', 1, '2020-07-26 17:59:49', '2020-07-26 17:59:50', 1, NULL),
(273, NULL, 'ODEME_ONAY_ISLE', '{\"siparisId\":31}', NULL, 1, '2020-07-26 18:27:27', '2020-07-26 18:27:27', 1, NULL),
(274, 273, 'KAYIT_TAMAMLA', '{\"bayiId\":13}', '273', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:27', 1, NULL),
(275, 273, 'PUAN_EKLE', '{\"bayiId\":11,\"altBayiId\":13,\"pv\":15.6,\"cv\":19.8}', '273', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:27', 1, NULL),
(276, 273, 'PUAN_EKLE', '{\"bayiId\":1,\"altBayiId\":11,\"pv\":15.6,\"cv\":19.8}', '273', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:27', 1, NULL),
(277, 273, 'KAZANC_EKLE', '{\"bayiId\":11,\"altBayiId\":13,\"tutar\":15.0,\"kazancTuru\":\"SPONSOR_FARK_KAZANCI\"}', '273,274', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:27', 1, NULL),
(278, 273, 'KAZANC_EKLE', '{\"bayiId\":1,\"altBayiId\":13,\"tutar\":15.0,\"kazancTuru\":\"SPONSOR_PRIMI\"}', '273,274', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:28', 1, NULL),
(279, 273, 'KARIYER_CHECK', '{\"bayiId\":11}', '273,275', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:28', 1, NULL),
(280, 273, 'KARIYER_CHECK', '{\"bayiId\":1}', '273,276', 1, '2020-07-26 18:27:27', '2020-07-26 18:27:28', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ar_job_fail_logs`
--

CREATE TABLE `ar_job_fail_logs` (
  `id` int(11) NOT NULL,
  `message` text NOT NULL,
  `fail_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_job_fail_log
--

INSERT INTO `ar_job_fail_logs` (`id`, `message`, `fail_date`) VALUES
(16, 'java.lang.NullPointerException\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor.executeBayiFarkKazanc(OdemeOnayProcessor.java:152)\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor.process(OdemeOnayProcessor.java:62)\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor$$FastClassBySpringCGLIB$$4be13c30.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor$$EnhancerBySpringCGLIB$$688f1b7.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.GeneratedMethodAccessor71.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\n', '2020-07-11 13:50:40'),
(17, 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:281)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:255)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:528)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:153)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:178)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212)\n	at com.sun.proxy.$Proxy120.findById(Unknown Source)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:205)\n	at com.sun.proxy.$Proxy81.findById(Unknown Source)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.getCvMatchAmount(AySonuBayiProcessor.java:202)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.executeBinaryKazanci(AySonuBayiProcessor.java:152)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.handleAktifBayi(AySonuBayiProcessor.java:89)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.process(AySonuBayiProcessor.java:55)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$FastClassBySpringCGLIB$$b190d98b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$EnhancerBySpringCGLIB$$17aaf08.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:63)\n	at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:67)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.getResultSet(AbstractLoadPlanBasedLoader.java:390)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeQueryStatement(AbstractLoadPlanBasedLoader.java:163)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeLoad(AbstractLoadPlanBasedLoader.java:104)\n	at org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader.load(AbstractLoadPlanBasedEntityLoader.java:223)\n	at org.hibernate.persister.entity.AbstractEntityPersister.doLoad(AbstractEntityPersister.java:4415)\n	at org.hibernate.persister.entity.AbstractEntityPersister.load(AbstractEntityPersister.java:4405)\n	at org.hibernate.event.internal.DefaultLoadEventListener.loadFromDatasource(DefaultLoadEventListener.java:569)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doLoad(DefaultLoadEventListener.java:537)\n	at org.hibernate.event.internal.DefaultLoadEventListener.load(DefaultLoadEventListener.java:208)\n	at org.hibernate.event.internal.DefaultLoadEventListener.proxyOrLoad(DefaultLoadEventListener.java:332)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doOnLoad(DefaultLoadEventListener.java:108)\n	at org.hibernate.event.internal.DefaultLoadEventListener.onLoad(DefaultLoadEventListener.java:74)\n	at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:113)\n	at org.hibernate.internal.SessionImpl.fireLoadNoChecks(SessionImpl.java:1184)\n	at org.hibernate.internal.SessionImpl.fireLoad(SessionImpl.java:1173)\n	at org.hibernate.internal.SessionImpl.access$2100(SessionImpl.java:194)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.doLoad(SessionImpl.java:2784)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.lambda$load$1(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.perform(SessionImpl.java:2721)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.load(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3320)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3287)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:314)\n	at com.sun.proxy.$Proxy114.find(Unknown Source)\n	at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById(SimpleJpaRepository.java:281)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.data.repository.core.support.ImplementationInvocationMetadata.invoke(ImplementationInvocationMetadata.java:72)\n	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:382)\n	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:205)\n	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:549)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:155)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:130)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:139)\n	... 44 more\nCaused by: java.sql.SQLSyntaxErrorException: Unknown column \'appsetting0_.value_num\' in \'field list\'\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003)\n	at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeQuery(ProxyPreparedStatement.java:52)\n	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeQuery(HikariProxyPreparedStatement.java)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:57)\n	... 89 more\n', '2020-07-11 13:57:20'),
(18, 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:281)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:255)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:528)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:153)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:178)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212)\n	at com.sun.proxy.$Proxy120.findById(Unknown Source)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:205)\n	at com.sun.proxy.$Proxy81.findById(Unknown Source)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.getCvMatchAmount(AySonuBayiProcessor.java:202)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.executeBinaryKazanci(AySonuBayiProcessor.java:152)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.handleAktifBayi(AySonuBayiProcessor.java:89)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.process(AySonuBayiProcessor.java:55)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$FastClassBySpringCGLIB$$b190d98b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$EnhancerBySpringCGLIB$$17aaf08.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:63)\n	at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:67)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.getResultSet(AbstractLoadPlanBasedLoader.java:390)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeQueryStatement(AbstractLoadPlanBasedLoader.java:163)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeLoad(AbstractLoadPlanBasedLoader.java:104)\n	at org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader.load(AbstractLoadPlanBasedEntityLoader.java:223)\n	at org.hibernate.persister.entity.AbstractEntityPersister.doLoad(AbstractEntityPersister.java:4415)\n	at org.hibernate.persister.entity.AbstractEntityPersister.load(AbstractEntityPersister.java:4405)\n	at org.hibernate.event.internal.DefaultLoadEventListener.loadFromDatasource(DefaultLoadEventListener.java:569)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doLoad(DefaultLoadEventListener.java:537)\n	at org.hibernate.event.internal.DefaultLoadEventListener.load(DefaultLoadEventListener.java:208)\n	at org.hibernate.event.internal.DefaultLoadEventListener.proxyOrLoad(DefaultLoadEventListener.java:332)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doOnLoad(DefaultLoadEventListener.java:108)\n	at org.hibernate.event.internal.DefaultLoadEventListener.onLoad(DefaultLoadEventListener.java:74)\n	at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:113)\n	at org.hibernate.internal.SessionImpl.fireLoadNoChecks(SessionImpl.java:1184)\n	at org.hibernate.internal.SessionImpl.fireLoad(SessionImpl.java:1173)\n	at org.hibernate.internal.SessionImpl.access$2100(SessionImpl.java:194)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.doLoad(SessionImpl.java:2784)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.lambda$load$1(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.perform(SessionImpl.java:2721)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.load(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3320)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3287)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:314)\n	at com.sun.proxy.$Proxy114.find(Unknown Source)\n	at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById(SimpleJpaRepository.java:281)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.data.repository.core.support.ImplementationInvocationMetadata.invoke(ImplementationInvocationMetadata.java:72)\n	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:382)\n	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:205)\n	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:549)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:155)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:130)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:139)\n	... 44 more\nCaused by: java.sql.SQLSyntaxErrorException: Unknown column \'appsetting0_.value_num\' in \'field list\'\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003)\n	at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeQuery(ProxyPreparedStatement.java:52)\n	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeQuery(HikariProxyPreparedStatement.java)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:57)\n	... 89 more\n', '2020-07-11 13:57:20'),
(19, 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:281)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:255)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:528)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:153)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:178)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212)\n	at com.sun.proxy.$Proxy120.findById(Unknown Source)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:205)\n	at com.sun.proxy.$Proxy81.findById(Unknown Source)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.getCvMatchAmount(AySonuBayiProcessor.java:202)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.executeBinaryKazanci(AySonuBayiProcessor.java:152)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.handleAktifBayi(AySonuBayiProcessor.java:89)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.process(AySonuBayiProcessor.java:55)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$FastClassBySpringCGLIB$$b190d98b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$EnhancerBySpringCGLIB$$17aaf08.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:63)\n	at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:67)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.getResultSet(AbstractLoadPlanBasedLoader.java:390)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeQueryStatement(AbstractLoadPlanBasedLoader.java:163)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeLoad(AbstractLoadPlanBasedLoader.java:104)\n	at org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader.load(AbstractLoadPlanBasedEntityLoader.java:223)\n	at org.hibernate.persister.entity.AbstractEntityPersister.doLoad(AbstractEntityPersister.java:4415)\n	at org.hibernate.persister.entity.AbstractEntityPersister.load(AbstractEntityPersister.java:4405)\n	at org.hibernate.event.internal.DefaultLoadEventListener.loadFromDatasource(DefaultLoadEventListener.java:569)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doLoad(DefaultLoadEventListener.java:537)\n	at org.hibernate.event.internal.DefaultLoadEventListener.load(DefaultLoadEventListener.java:208)\n	at org.hibernate.event.internal.DefaultLoadEventListener.proxyOrLoad(DefaultLoadEventListener.java:332)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doOnLoad(DefaultLoadEventListener.java:108)\n	at org.hibernate.event.internal.DefaultLoadEventListener.onLoad(DefaultLoadEventListener.java:74)\n	at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:113)\n	at org.hibernate.internal.SessionImpl.fireLoadNoChecks(SessionImpl.java:1184)\n	at org.hibernate.internal.SessionImpl.fireLoad(SessionImpl.java:1173)\n	at org.hibernate.internal.SessionImpl.access$2100(SessionImpl.java:194)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.doLoad(SessionImpl.java:2784)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.lambda$load$1(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.perform(SessionImpl.java:2721)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.load(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3320)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3287)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:314)\n	at com.sun.proxy.$Proxy114.find(Unknown Source)\n	at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById(SimpleJpaRepository.java:281)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.data.repository.core.support.ImplementationInvocationMetadata.invoke(ImplementationInvocationMetadata.java:72)\n	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:382)\n	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:205)\n	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:549)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:155)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:130)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:139)\n	... 44 more\nCaused by: java.sql.SQLSyntaxErrorException: Unknown column \'appsetting0_.value_num\' in \'field list\'\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003)\n	at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeQuery(ProxyPreparedStatement.java:52)\n	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeQuery(HikariProxyPreparedStatement.java)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:57)\n	... 89 more\n', '2020-07-11 13:57:21'),
(20, 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.convertHibernateAccessException(HibernateJpaDialect.java:281)\n	at org.springframework.orm.jpa.vendor.HibernateJpaDialect.translateExceptionIfPossible(HibernateJpaDialect.java:255)\n	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.translateExceptionIfPossible(AbstractEntityManagerFactoryBean.java:528)\n	at org.springframework.dao.support.ChainedPersistenceExceptionTranslator.translateExceptionIfPossible(ChainedPersistenceExceptionTranslator.java:61)\n	at org.springframework.dao.support.DataAccessUtils.translateIfNecessary(DataAccessUtils.java:242)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:153)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:178)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212)\n	at com.sun.proxy.$Proxy120.findById(Unknown Source)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:205)\n	at com.sun.proxy.$Proxy81.findById(Unknown Source)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.getCvMatchAmount(AySonuBayiProcessor.java:202)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.executeBinaryKazanci(AySonuBayiProcessor.java:152)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.handleAktifBayi(AySonuBayiProcessor.java:89)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.process(AySonuBayiProcessor.java:55)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$FastClassBySpringCGLIB$$b190d98b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$EnhancerBySpringCGLIB$$17aaf08.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: org.hibernate.exception.SQLGrammarException: could not extract ResultSet\n	at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:63)\n	at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:42)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)\n	at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:67)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.getResultSet(AbstractLoadPlanBasedLoader.java:390)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeQueryStatement(AbstractLoadPlanBasedLoader.java:163)\n	at org.hibernate.loader.plan.exec.internal.AbstractLoadPlanBasedLoader.executeLoad(AbstractLoadPlanBasedLoader.java:104)\n	at org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader.load(AbstractLoadPlanBasedEntityLoader.java:223)\n	at org.hibernate.persister.entity.AbstractEntityPersister.doLoad(AbstractEntityPersister.java:4415)\n	at org.hibernate.persister.entity.AbstractEntityPersister.load(AbstractEntityPersister.java:4405)\n	at org.hibernate.event.internal.DefaultLoadEventListener.loadFromDatasource(DefaultLoadEventListener.java:569)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doLoad(DefaultLoadEventListener.java:537)\n	at org.hibernate.event.internal.DefaultLoadEventListener.load(DefaultLoadEventListener.java:208)\n	at org.hibernate.event.internal.DefaultLoadEventListener.proxyOrLoad(DefaultLoadEventListener.java:332)\n	at org.hibernate.event.internal.DefaultLoadEventListener.doOnLoad(DefaultLoadEventListener.java:108)\n	at org.hibernate.event.internal.DefaultLoadEventListener.onLoad(DefaultLoadEventListener.java:74)\n	at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:113)\n	at org.hibernate.internal.SessionImpl.fireLoadNoChecks(SessionImpl.java:1184)\n	at org.hibernate.internal.SessionImpl.fireLoad(SessionImpl.java:1173)\n	at org.hibernate.internal.SessionImpl.access$2100(SessionImpl.java:194)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.doLoad(SessionImpl.java:2784)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.lambda$load$1(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.perform(SessionImpl.java:2721)\n	at org.hibernate.internal.SessionImpl$IdentifierLoadAccessImpl.load(SessionImpl.java:2765)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3320)\n	at org.hibernate.internal.SessionImpl.find(SessionImpl.java:3287)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:314)\n	at com.sun.proxy.$Proxy114.find(Unknown Source)\n	at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById(SimpleJpaRepository.java:281)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.data.repository.core.support.ImplementationInvocationMetadata.invoke(ImplementationInvocationMetadata.java:72)\n	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:382)\n	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:205)\n	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:549)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:155)\n	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:130)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:139)\n	... 44 more\nCaused by: java.sql.SQLSyntaxErrorException: Unknown column \'appsetting0_.value_num\' in \'field list\'\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeQuery(ClientPreparedStatement.java:1003)\n	at com.zaxxer.hikari.pool.ProxyPreparedStatement.executeQuery(ProxyPreparedStatement.java:52)\n	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.executeQuery(HikariProxyPreparedStatement.java)\n	at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.extract(ResultSetReturnImpl.java:57)\n	... 89 more\n', '2020-07-11 13:57:21');
INSERT INTO `ar_job_fail_logs` (`id`, `message`, `fail_date`) VALUES
(21, 'java.lang.NullPointerException\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.executeBinaryKazanci(AySonuBayiProcessor.java:158)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.handleAktifBayi(AySonuBayiProcessor.java:89)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor.process(AySonuBayiProcessor.java:55)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$FastClassBySpringCGLIB$$b190d98b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.AySonuBayiProcessor$$EnhancerBySpringCGLIB$$81dd2dca.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\n', '2020-07-11 14:07:27'),
(22, 'java.lang.RuntimeException: Sipariş odemeOnayTarih null!\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor.executeAktiflikHesapla(OdemeOnayProcessor.java:89)\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor.process(OdemeOnayProcessor.java:49)\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor$$FastClassBySpringCGLIB$$4be13c30.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:367)\n	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:118)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691)\n	at com.vesonet.voseengine.job.processor.OdemeOnayProcessor$$EnhancerBySpringCGLIB$$a4a79a36.process(<generated>)\n	at com.vesonet.voseengine.job.JobManagerImpl.consume(JobManagerImpl.java:155)\n	at com.vesonet.voseengine.job.JobManagerImpl.run(JobManagerImpl.java:49)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.scheduling.support.ScheduledMethodRunnable.run(ScheduledMethodRunnable.java:84)\n	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54)\n	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)\n	at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)\n	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at java.lang.Thread.run(Thread.java:748)\n', '2020-07-26 01:55:54');

-- --------------------------------------------------------

--
-- Table structure for table `ar_kariyerler`
--

CREATE TABLE `ar_kariyerler` (
  `id` int(11) NOT NULL,
  `kariyer_adi` varchar(50) NOT NULL,
  `sira_no` int(11) NOT NULL,
  `baslangic_paket` tinyint(1) NOT NULL,
  `baslangic_paket_tutar` int(11) DEFAULT NULL,
  `kariyer_puan` int(11) DEFAULT NULL,
  `aktiflik_sarti` int(11) NOT NULL,
  `alisveris_indirimi` int(11) NOT NULL,
  `binary_eslesme` int(11) NOT NULL,
  `sponsor_primi` varchar(50) DEFAULT NULL,
  `ekip_alisveris` varchar(50) DEFAULT NULL,
  `ekip_kazanc` varchar(50) DEFAULT NULL,
  `bayi_aktiflik_puan` int(11) DEFAULT NULL,
  `bayi_aktiflik_kazanc` int(11) DEFAULT NULL,
  `lider_destek` int(11) NOT NULL,
  `lider_cikarma` int(11) NOT NULL,
  `tazminat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_kariyerler`
--

INSERT INTO `ar_kariyerler` (`id`, `kariyer_adi`, `sira_no`, `baslangic_paket`, `baslangic_paket_tutar`, `kariyer_puan`, `aktiflik_sarti`, `alisveris_indirimi`, `binary_eslesme`, `sponsor_primi`, `ekip_alisveris`, `ekip_kazanc`, `bayi_aktiflik_puan`, `bayi_aktiflik_kazanc`, `lider_destek`, `lider_cikarma`, `tazminat`) VALUES
(2, 'Kullanıcı', 2, 1, 500, NULL, 2, 10, 5, NULL, NULL, NULL, NULL, NULL, 0, 0, 0),
(3, 'Başlangıç', 3, 1, 1500, NULL, 3, 15, 10, NULL, NULL, NULL, NULL, NULL, 0, 0, 0),
(5, 'Girişimci', 5, 1, 5000, NULL, 3, 25, 20, '20,5,4', NULL, NULL, NULL, NULL, 0, 0, 0),
(6, 'Danışman', 6, 0, NULL, 2500, 3, 25, 20, '21,5,4,3', NULL, NULL, NULL, NULL, 0, 0, 0),
(7, 'Temsilci', 7, 0, NULL, 5000, 3, 25, 20, '22,5,4,3,2', '5,2', NULL, NULL, NULL, 0, 0, 0),
(8, 'Yardımcı Asistan', 8, 0, NULL, 10000, 3, 25, 20, '23,15,4,3,2,1', '5', '5', NULL, NULL, 125, 500, 2500),
(9, 'Asistan', 9, 0, NULL, 30000, 3, 25, 20, '24,5,4,3,2,2,1', '5,4', '5,4', NULL, NULL, 500, 1000, 6000),
(10, 'Öncü Lider', 10, 0, NULL, 75000, 3, 25, 20, '25,5,4,3,2,2,2,1', '5,4,3', '5,4,3', NULL, NULL, 1050, 1500, 12000),
(11, 'Lider', 11, 0, NULL, 150000, 3, 25, 20, '26,5,4,3,2,2,2,2', '5,4,3,2', '5,4,3,2', NULL, NULL, 1550, 3000, 18000),
(12, 'Müdür Yardımcısı', 12, 0, NULL, 250000, 3, 25, 20, '26,5,4,3,2,2,2,2', '5,4,3,2,1', '5,4,3,2,1', NULL, NULL, 2050, 3500, 23000),
(13, 'Müdür', 13, 0, NULL, 500000, 3, 25, 20, '26,5,4,3,2,2,2,2', '5,4,3,2,1,1', '5,4,3,2,1,1', NULL, NULL, 2500, 5000, 30000),
(15, 'Genel Müdür Yardımcısı', 14, 0, NULL, 750000, 3, 25, 20, '26,5,4,3,2,2,2,2', '5,4,3,2,1,1,1', '5,4,3,2,1,1,1', NULL, NULL, 5000, 10000, 35000),
(16, 'Genel Müdür', 15, 0, NULL, 1000000, 3, 25, 20, '26,5,4,3,2,2,2,2', '5,4,3,2,1,1,1,1', '5,4,3,2,1,1,1,1', NULL, NULL, 10000, 30000, 50000),
(18, 'Girişimci Adayı', 4, 1, 2500, NULL, 3, 20, 15, '15,5', NULL, NULL, NULL, NULL, 0, 0, 0),
(21, 'Müşteri', 1, 1, 300, NULL, 1, 5, 3, NULL, NULL, NULL, NULL, NULL, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ar_kategoriler`
--

CREATE TABLE `ar_kategoriler` (
  `id` int(11) NOT NULL,
  `kategori_adi` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `ar_kategoriler`
--

INSERT INTO `ar_kategoriler` (`id`, `kategori_adi`) VALUES
(1, 'saglikurunleri1'),
(2, 'saglikurunleri2'),
(4, 'saglikurunleri4'),
(6, 'saglikurunleri3');

-- --------------------------------------------------------

--
-- Table structure for table `ar_kazanclar`
--

CREATE TABLE `ar_kazanclar` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) NOT NULL,
  `alt_bayi_id` int(11) DEFAULT NULL,
  `miktar` double NOT NULL,
  `kazanc_turu` varchar(30) NOT NULL,
  `tarih` datetime NOT NULL,
  `cuzdana_islendi` tinyint(1) NOT NULL DEFAULT '0',
  `silindi` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_kazanclar`
--

INSERT INTO `ar_kazanclar` (`id`, `bayi_id`, `alt_bayi_id`, `miktar`, `kazanc_turu`, `tarih`, `cuzdana_islendi`, `silindi`) VALUES
(40, 1, 2, 9.04, 'BAYI_FARK_KAZANCI', '2020-07-11 13:50:35', 1, 0),
(41, 1, 2, 2.79, 'EKIP_ALISVERIS_PRIMI', '2020-07-11 13:50:35', 1, 0),
(43, 1, 5, 17.94, 'EKIP_ALISVERIS_PRIMI', '2020-07-11 13:50:36', 1, 0),
(45, 1, 4, 16.44, 'EKIP_ALISVERIS_PRIMI', '2020-07-11 13:50:40', 1, 0),
(46, 1, 2, 77.25, 'BAYI_FARK_KAZANCI', '2020-07-11 13:50:40', 1, 0),
(47, 1, 2, 25, 'EKIP_ALISVERIS_PRIMI', '2020-07-11 13:50:40', 1, 0),
(48, 2, NULL, 1, 'BINARY_KAZANCI', '2020-07-11 14:07:27', 1, 0),
(49, 1, 2, 0.05, 'EKIP_KAZANC_PRIMI', '2020-07-11 14:07:27', 1, 0),
(50, 1, NULL, 1550, 'LIDER_DESTEK_PRIMI', '2020-07-11 14:12:23', 1, 0),
(51, 6, 7, 150, 'SPONSOR_FARK_KAZANCI', '2020-07-26 17:59:50', 0, 0),
(52, 6, 7, 300, 'SPONSOR_PRIMI', '2020-07-26 17:59:50', 0, 0),
(53, 1, 7, 75, 'SPONSOR_PRIMI', '2020-07-26 17:59:50', 0, 0),
(54, 11, 13, 15, 'SPONSOR_FARK_KAZANCI', '2020-07-26 18:27:27', 0, 0),
(55, 1, 13, 15, 'SPONSOR_PRIMI', '2020-07-26 18:27:28', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `ar_siparisler`
--

CREATE TABLE `ar_siparisler` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) DEFAULT NULL,
  `siparis_tarihi` datetime NOT NULL,
  `urun_sayisi` int(11) NOT NULL,
  `odeme_yontemi` varchar(20) CHARACTER SET utf8 NOT NULL,
  `indirim` int(11) NOT NULL,
  `urun_tutar` double NOT NULL,
  `odenen_tutar` double NOT NULL,
  `toplam_pv` double NOT NULL,
  `toplam_cv` double NOT NULL,
  `siparis_veren` varchar(100) NOT NULL,
  `adres` text NOT NULL,
  `odeme_onay` tinyint(1) NOT NULL DEFAULT '0',
  `odeme_onay_tarih` datetime DEFAULT NULL,
  `odeme_onaylayan` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `siparis_durum` varchar(20) CHARACTER SET utf8 NOT NULL,
  `son_guncelleme` datetime DEFAULT NULL,
  `son_guncelleyen` varchar(50) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ar_siparisler`
--

INSERT INTO `ar_siparisler` (`id`, `bayi_id`, `siparis_tarihi`, `urun_sayisi`, `odeme_yontemi`, `indirim`, `urun_tutar`, `odenen_tutar`, `toplam_pv`, `toplam_cv`, `siparis_veren`, `adres`, `odeme_onay`, `odeme_onay_tarih`, `odeme_onaylayan`, `siparis_durum`, `son_guncelleme`, `son_guncelleyen`) VALUES
(1, 2, '2020-07-04 20:16:49', 4, 'EFT', 0, 55.78, 0, 3.45, 5.32, 'Ahmet Can Talay', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-09 05:33:43', NULL, 'ODEME_ONAYLANDI', '2020-07-04 20:16:49', 'John Doe'),
(2, 5, '2020-07-06 11:55:35', 2, 'EFT', 0, 448.4, 0, 15.6, 19.8, 'Emre Şahin', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-09 08:33:47', NULL, 'ODEME_ONAYLANDI', '2020-07-06 11:55:35', NULL),
(3, 4, '2020-07-07 12:53:53', 5, 'EFT', 0, 411, 0, 13.45, 14.56, 'Ahmet Şahin', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-09 09:33:54', NULL, 'ODEME_ONAYLANDI', '2020-07-07 12:53:53', NULL),
(4, 2, '2020-07-07 12:57:44', 1, 'EFT', 0, 500, 0, 25, 25, 'Ahmet Can Talay', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-09 12:34:03', NULL, 'ODEME_ONAYLANDI', '2020-07-07 12:57:44', NULL),
(5, 1, '2020-07-07 12:57:44', 5, 'EFT', 0, 500, 0, 25, 25, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-09 12:34:03', NULL, 'ODEME_ONAYLANDI', '2020-07-07 12:57:44', NULL),
(7, 1, '2020-07-23 22:20:03', 3, 'banka', 25, 373.3875, 373.3875, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:20:03', 'system.SepetCalculator'),
(8, 1, '2020-07-23 22:24:37', 3, 'banka', 25, 373.3875, 373.3875, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:24:37', 'system.SepetCalculator'),
(9, 1, '2020-07-23 22:25:05', 3, 'banka', 25, 373.3875, 373.3875, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:25:05', 'system.SepetCalculator'),
(10, 1, '2020-07-23 22:25:47', 3, 'banka', 25, 373.3875, 373.3875, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:25:47', 'system.SepetCalculator'),
(11, 1, '2020-07-23 22:30:15', 3, 'banka', 25, 373.39, 373.39, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:30:15', 'system.SepetCalculator'),
(12, 1, '2020-07-23 22:38:35', 3, 'banka', 25, 373.39, 373.39, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:38:35', 'system.SepetCalculator'),
(13, 1, '2020-07-23 22:41:23', 3, 'banka', 25, 373.39, 373.39, 17.05, 22.35, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:41:23', 'system.SepetCalculator'),
(14, 1, '2020-07-23 22:45:09', 5, 'banka', 25, 229, 229, 11.95, 16.049999999999997, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:45:09', 'system.SepetCalculator'),
(15, 1, '2020-07-23 22:55:01', 3, 'banka', 25, 198.74, 198.74, 9.6, 12.3, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:55:01', 'system.SepetCalculator'),
(16, 1, '2020-07-23 22:56:41', 3, 'banka', 25, 301.69, 301.69, 22.65, 34.349999999999994, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 22:56:41', 'system.SepetCalculator'),
(17, 1, '2020-07-23 23:13:02', 1, 'banka', 25, 177, 177, 7.8, 9.9, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 23:13:02', 'system.SepetCalculator'),
(18, 1, '2020-07-23 23:17:46', 2, 'banka', 25, 282.3, 282.3, 21.2, 31.799999999999997, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 23:17:46', 'system.SepetCalculator'),
(19, 1, '2020-07-23 23:26:13', 4, 'banka', 25, 384.26, 384.26, 17.95, 23.55, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-23 23:26:13', 'system.SepetCalculator'),
(20, 1, '2020-07-24 00:57:26', 3, 'banka', 25, 45.92, 69.48, 25.299999999999997, 26, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-24 00:57:26', 'system.SepetCalculator'),
(21, 1, '2020-07-24 11:16:29', 2, 'banka', 25, 354, 354, 15.6, 19.8, 'Kadir Tokmak', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-24 11:16:29', 'system.SepetCalculator'),
(22, NULL, '2020-07-24 17:51:03', 2, 'banka', 0, 376.4, 376.4, 21.2, 31.8, 'Abuzer Çonçon', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 11:15:55', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 11:15:55', '_unknown_'),
(23, 1, '2020-07-24 22:02:56', 1, 'banka', 25, 177, 177, 7.8, 9.9, 'Kadir Tokmak', '{\"id\":1,\"bayiId\":1,\"baslik\":\"Ev\",\"aliciAdi\":\"Kadir Tokmak\",\"aliciTel\":\"05555555555\",\"adres\":\"Yakuplu Mah. No:3/9\",\"postaKodu\":\"34000\",\"ilce\":\"Beylikdüzü\",\"il\":\"İstanbul\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-24 22:02:56', 'system.SepetCalculator'),
(24, 1, '2020-07-24 22:08:17', 1, 'banka', 25, 177, 177, 7.8, 9.9, 'Kadir Tokmak', '{\"id\":1,\"bayiId\":1,\"baslik\":\"Ev\",\"aliciAdi\":\"Kadir Tokmak\",\"aliciTel\":\"05555555555\",\"adres\":\"Yakuplu Mah. No:3/9\",\"postaKodu\":\"34000\",\"ilce\":\"Beylikdüzü\",\"il\":\"İstanbul\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-24 22:08:17', 'system.SepetCalculator'),
(25, 8, '2020-07-25 11:35:52', 5, 'kart', 15, 286.36, 286.36, 13.6, 20.1, 'Esra Şahin', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 02:01:01', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 02:01:02', '_unknown_'),
(26, 8, '2020-07-25 11:37:11', 2, 'kart', 15, 222.04, 222.04, 9.25, 12.45, 'Esra Şahin', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 02:02:45', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 02:02:48', '_unknown_'),
(27, NULL, '2020-07-25 11:47:20', 2, 'banka', 0, 472, 472, 15.6, 19.8, 'Hasan Polat', '{\"id\":3,\"bayiId\":8,\"baslik\":\"Deneme\",\"aliciAdi\":\"Esra Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"deneme sok. tes apt. no:1/2\",\"postaKodu\":\"21341\",\"ilce\":\"Elmalı\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 0, NULL, NULL, 'ODEME_BEKLENIYOR', '2020-07-25 11:47:20', 'system.SepetCalculator'),
(28, 1, '2020-07-26 17:00:35', 3, 'kart', 25, 212.4, 212.4, 32.2, 34.7, 'Kadir Tokmak', '{\"id\":1,\"bayiId\":1,\"baslik\":\"Ev\",\"aliciAdi\":\"Kadir Tokmak\",\"aliciTel\":\"05555555555\",\"adres\":\"Yakuplu Mah. No:3/9\",\"postaKodu\":\"34000\",\"ilce\":\"Beylikdüzü\",\"il\":\"İstanbul\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 17:03:15', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 17:03:16', '_unknown_'),
(29, 7, '2020-07-26 17:49:44', 8, 'banka', 15, 529.84, 529.84, 24.3, 35.1, 'Esma Şahin', '{\"id\":4,\"bayiId\":7,\"baslik\":\"Ev\",\"aliciAdi\":\"Esma Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"sadfgdsg\",\"postaKodu\":\"34543\",\"ilce\":\"ewewr\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 17:50:21', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 17:50:22', '_unknown_'),
(30, 7, '2020-07-26 17:59:24', 8, 'banka', 15, 1604.8, 1604.8, 62.4, 79.2, 'Esma Şahin', '{\"id\":4,\"bayiId\":7,\"baslik\":\"Ev\",\"aliciAdi\":\"Esma Şahin\",\"aliciTel\":\"05556545454\",\"adres\":\"sadfgdsg\",\"postaKodu\":\"34543\",\"ilce\":\"ewewr\",\"il\":\"Kırıkkale\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 17:59:49', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 17:59:49', '_unknown_'),
(31, 13, '2020-07-26 18:26:36', 2, 'banka', 5, 448.4, 448.4, 15.6, 19.8, 'Veli Dereli', '{\"id\":5,\"bayiId\":13,\"baslik\":\"Ev\",\"aliciAdi\":\"Veli Dereli\",\"aliciTel\":\"055543232221\",\"adres\":\"deneme mah. test sok.\",\"postaKodu\":\"213124\",\"ilce\":\"asdasd\",\"il\":\"Denizli\",\"ulke\":\"Türkiye\"}', 1, '2020-07-26 18:27:27', '_unknown_', 'ODEME_ONAYLANDI', '2020-07-26 18:27:27', '_unknown_');

-- --------------------------------------------------------

--
-- Table structure for table `ar_siparis_detay`
--

CREATE TABLE `ar_siparis_detay` (
  `id` int(11) NOT NULL,
  `siparis_id` int(11) NOT NULL,
  `urun_id` int(11) NOT NULL,
  `miktar` int(11) NOT NULL,
  `katalog_birim_fiyat` double NOT NULL,
  `indirimli_birim_fiyat` double NOT NULL,
  `tutar` double NOT NULL,
  `birim_pv` double NOT NULL,
  `birim_cv` double NOT NULL,
  `toplam_pv` double NOT NULL,
  `toplam_cv` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_siparis_detay`
--

INSERT INTO `ar_siparis_detay` (`id`, `siparis_id`, `urun_id`, `miktar`, `katalog_birim_fiyat`, `indirimli_birim_fiyat`, `tutar`, `birim_pv`, `birim_cv`, `toplam_pv`, `toplam_cv`) VALUES
(1, 1, 1, 1, 23, 21.5, 21.5, 0, 0, 0, 0),
(2, 1, 2, 3, 12.5, 11.3, 33.9, 0, 0, 0, 0),
(3, 2, 3, 2, 236, 224.2, 448.4, 0, 0, 0, 0),
(4, 7, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(5, 7, 1, 1, 25.85, 19.3875, 19.3875, 1.45, 2.55, 1.45, 2.55),
(6, 8, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(7, 8, 1, 1, 25.85, 19.3875, 19.3875, 1.45, 2.55, 1.45, 2.55),
(8, 9, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(9, 9, 1, 1, 25.85, 19.3875, 19.3875, 1.45, 2.55, 1.45, 2.55),
(10, 10, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(11, 10, 1, 1, 25.85, 19.3875, 19.3875, 1.45, 2.55, 1.45, 2.55),
(12, 11, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(13, 11, 1, 1, 25.85, 19.39, 19.39, 1.45, 2.55, 1.45, 2.55),
(14, 12, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(15, 12, 1, 1, 25.85, 19.39, 19.39, 1.45, 2.55, 1.45, 2.55),
(16, 13, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(17, 13, 1, 1, 25.85, 19.39, 19.39, 1.45, 2.55, 1.45, 2.55),
(18, 14, 1, 1, 25.85, 19.39, 19.39, 1.45, 2.55, 1.45, 2.55),
(19, 14, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(20, 14, 2, 3, 14.49, 10.87, 32.61, 0.9, 1.2, 2.7, 3.5999999999999996),
(21, 15, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(22, 15, 2, 2, 14.49, 10.87, 21.74, 0.9, 1.2, 1.8, 2.4),
(23, 16, 1, 1, 25.85, 19.39, 19.39, 1.45, 2.55, 1.45, 2.55),
(24, 16, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(25, 16, 5, 1, 140.4, 105.3, 105.3, 13.4, 21.9, 13.4, 21.9),
(26, 17, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(27, 18, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(28, 18, 5, 1, 140.4, 105.3, 105.3, 13.4, 21.9, 13.4, 21.9),
(29, 19, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(30, 19, 1, 1, 25.85, 19.39, 19.39, 1.45, 2.55, 1.45, 2.55),
(31, 19, 2, 1, 14.49, 10.87, 10.87, 0.9, 1.2, 0.9, 1.2),
(32, 20, 9, 2, 23.6, 17.7, 35.4, 12.2, 12.4, 24.4, 24.8),
(33, 20, 2, 1, 14.03, 10.52, 10.52, 0.9, 1.2, 0.9, 1.2),
(34, 21, 3, 2, 236, 177, 354, 7.8, 9.9, 15.6, 19.8),
(35, 22, 3, 1, 236, 236, 236, 7.8, 9.9, 7.8, 9.9),
(36, 22, 5, 1, 140.4, 140.4, 140.4, 13.4, 21.9, 13.4, 21.9),
(37, 23, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(38, 24, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(39, 25, 3, 1, 236, 200.6, 200.6, 7.8, 9.9, 7.8, 9.9),
(40, 25, 1, 4, 25.22, 21.44, 85.76, 1.45, 2.55, 5.8, 10.2),
(41, 26, 1, 1, 25.22, 21.44, 21.44, 1.45, 2.55, 1.45, 2.55),
(42, 26, 3, 1, 236, 200.6, 200.6, 7.8, 9.9, 7.8, 9.9),
(43, 27, 3, 2, 236, 236, 472, 7.8, 9.9, 15.6, 19.8),
(44, 28, 3, 1, 236, 177, 177, 7.8, 9.9, 7.8, 9.9),
(45, 28, 9, 2, 23.6, 17.7, 35.4, 12.2, 12.4, 24.4, 24.8),
(46, 29, 3, 2, 236, 200.6, 401.2, 7.8, 9.9, 15.6, 19.8),
(47, 29, 1, 6, 25.22, 21.44, 128.64, 1.45, 2.55, 8.7, 15.299999999999999),
(48, 30, 3, 8, 236, 200.6, 1604.8, 7.8, 9.9, 62.4, 79.2),
(49, 31, 3, 2, 236, 224.2, 448.4, 7.8, 9.9, 15.6, 19.8);

-- --------------------------------------------------------

--
-- Table structure for table `ar_urunler`
--

CREATE TABLE `ar_urunler` (
  `id` int(11) NOT NULL,
  `kategori_id` int(11) NOT NULL,
  `urun_adi` varchar(150) NOT NULL,
  `urun_kodu` varchar(50) NOT NULL,
  `img` varchar(100) DEFAULT NULL,
  `net_fiyat` double NOT NULL,
  `kdv` tinyint(4) NOT NULL,
  `satis_fiyat` double NOT NULL,
  `pv` double NOT NULL,
  `cv` double NOT NULL,
  `urun_stok_id` int(11) DEFAULT NULL,
  `aciklama` text NOT NULL,
  `eklenme_tarih` datetime NOT NULL,
  `guncelleme_tarih` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_urunler`
--

INSERT INTO `ar_urunler` (`id`, `kategori_id`, `urun_adi`, `urun_kodu`, `img`, `net_fiyat`, `kdv`, `satis_fiyat`, `pv`, `cv`, `urun_stok_id`, `aciklama`, `eklenme_tarih`, `guncelleme_tarih`) VALUES
(1, 1, 'Deneme Ürün 1', 'DNM01', NULL, 23.35, 8, 25.22, 1.45, 2.55, 1, 'Ürünümüz çok güzel bir üründür. Şiddetle tavsiye edilir', '2020-07-04 20:13:11', '2020-07-04 21:14:25'),
(2, 2, 'Yanılma Ürün 2', 'DNM02', 'turkey-sky-cloud-1201295-wallhere.com-nwmxnikict.jpg', 12.99, 8, 14.03, 0.9, 1.2, 2, 'Çok satılan, ufak ama etkili bir üründür. Çok kıymetlidir. Her alan memnun kalır. Siz de mutlaka almalısınız.', '2020-07-04 20:13:11', '2020-07-22 00:45:49'),
(3, 4, 'Pahalı Ürün', 'DNM03', 'content-249-eaf1592d-e223-406f-a214-ba7f4a993ea8_1920x1080-lqmxwqafup.jpg', 200, 18, 236, 7.8, 9.9, 3, 'Paketinizi doldurmak için bir kaç tane bu pahalı üründen alabilirsiniz.', '2020-07-06 11:53:34', '2020-07-21 15:42:30'),
(5, 1, 'Panel Deneme', 'PNL001', 'screen_shot_2020-07-15_at_20.31.20-erxwicgqlr.png', 130, 8, 140.4, 13.4, 21.9, 4, 'Panelden test için edit yaptık . Stok update oalyından vazgeçtik', '2020-07-21 11:23:34', '2020-07-22 00:45:33'),
(9, 1, 'Resim Deneme', 'PNL003', 'thumb2-istanbul-4k-blue-mosque-sunset-panorama-ntleayiieg.jpg', 20, 18, 23.6, 12.2, 12.4, 8, 'resim yükleme denemesi 456', '2020-07-21 15:03:03', '2020-07-22 00:45:42');

-- --------------------------------------------------------

--
-- Table structure for table `ar_urun_stok`
--

CREATE TABLE `ar_urun_stok` (
  `id` int(11) NOT NULL,
  `urun_id` int(11) DEFAULT NULL,
  `miktar` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ar_urun_stok`
--

INSERT INTO `ar_urun_stok` (`id`, `urun_id`, `miktar`) VALUES
(1, 1, 20),
(2, 2, 50),
(3, 3, 10),
(4, 5, 0),
(5, 6, 100),
(6, 7, 200),
(7, 8, 150),
(8, 9, 150);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ar_admin`
--
ALTER TABLE `ar_admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `ar_adresler`
--
ALTER TABLE `ar_adresler`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uye_id` (`bayi_id`);

--
-- Indexes for table `ar_aktiflik`
--
ALTER TABLE `ar_aktiflik`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `bayi_id_ay_unique` (`bayi_id`,`ay`) USING BTREE;

--
-- Indexes for table `ar_app_settings`
--
ALTER TABLE `ar_app_settings`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `ar_bayiler`
--
ALTER TABLE `ar_bayiler`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `tckn` (`tckn`),
  ADD KEY `sponsor_id` (`sponsor_id`),
  ADD KEY `kariyer_id` (`kariyer_id`);

--
-- Indexes for table `ar_bayi_puan`
--
ALTER TABLE `ar_bayi_puan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `bayi_id` (`bayi_id`,`alt_bayi_id`);

--
-- Indexes for table `ar_cuzdan`
--
ALTER TABLE `ar_cuzdan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bayi_id` (`bayi_id`);

--
-- Indexes for table `ar_jobs`
--
ALTER TABLE `ar_jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `processed` (`processed`,`root_id`);

--
-- Indexes for table `ar_job_fail_logs`
--
ALTER TABLE `ar_job_fail_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ar_kariyerler`
--
ALTER TABLE `ar_kariyerler`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sira_no` (`sira_no`) USING BTREE;

--
-- Indexes for table `ar_kategoriler`
--
ALTER TABLE `ar_kategoriler`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ar_kazanclar`
--
ALTER TABLE `ar_kazanclar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bayi_id` (`bayi_id`),
  ADD KEY `tarih` (`tarih`);

--
-- Indexes for table `ar_siparisler`
--
ALTER TABLE `ar_siparisler`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ar_siparisler_ibfk_2` (`bayi_id`),
  ADD KEY `odeme_onay_tarih` (`odeme_onay_tarih`),
  ADD KEY `siparis_durum` (`siparis_durum`);

--
-- Indexes for table `ar_siparis_detay`
--
ALTER TABLE `ar_siparis_detay`
  ADD PRIMARY KEY (`id`),
  ADD KEY `siparis_id` (`siparis_id`),
  ADD KEY `urun_id` (`urun_id`);

--
-- Indexes for table `ar_urunler`
--
ALTER TABLE `ar_urunler`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `urun_kodu` (`urun_kodu`),
  ADD KEY `kategori_id` (`kategori_id`),
  ADD KEY `urun_stok_id` (`urun_stok_id`);

--
-- Indexes for table `ar_urun_stok`
--
ALTER TABLE `ar_urun_stok`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ar_admin`
--
ALTER TABLE `ar_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `ar_adresler`
--
ALTER TABLE `ar_adresler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `ar_aktiflik`
--
ALTER TABLE `ar_aktiflik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `ar_bayiler`
--
ALTER TABLE `ar_bayiler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `ar_bayi_puan`
--
ALTER TABLE `ar_bayi_puan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `ar_cuzdan`
--
ALTER TABLE `ar_cuzdan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `ar_jobs`
--
ALTER TABLE `ar_jobs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=281;

--
-- AUTO_INCREMENT for table `ar_job_fail_logs`
--
ALTER TABLE `ar_job_fail_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `ar_kariyerler`
--
ALTER TABLE `ar_kariyerler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `ar_kategoriler`
--
ALTER TABLE `ar_kategoriler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `ar_kazanclar`
--
ALTER TABLE `ar_kazanclar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `ar_siparisler`
--
ALTER TABLE `ar_siparisler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `ar_siparis_detay`
--
ALTER TABLE `ar_siparis_detay`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `ar_urunler`
--
ALTER TABLE `ar_urunler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `ar_urun_stok`
--
ALTER TABLE `ar_urun_stok`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ar_adresler`
--
ALTER TABLE `ar_adresler`
  ADD CONSTRAINT `ar_adresler_ibfk_1` FOREIGN KEY (`bayi_id`) REFERENCES `ar_bayiler` (`id`);

--
-- Constraints for table `ar_aktiflik`
--
ALTER TABLE `ar_aktiflik`
  ADD CONSTRAINT `ar_aktiflik_ibfk_1` FOREIGN KEY (`bayi_id`) REFERENCES `ar_bayiler` (`id`);

--
-- Constraints for table `ar_bayiler`
--
ALTER TABLE `ar_bayiler`
  ADD CONSTRAINT `ar_bayiler_ibfk_1` FOREIGN KEY (`sponsor_id`) REFERENCES `ar_bayiler` (`id`),
  ADD CONSTRAINT `ar_bayiler_ibfk_2` FOREIGN KEY (`kariyer_id`) REFERENCES `ar_kariyerler` (`id`);

--
-- Constraints for table `ar_cuzdan`
--
ALTER TABLE `ar_cuzdan`
  ADD CONSTRAINT `ar_cuzdan_ibfk_1` FOREIGN KEY (`bayi_id`) REFERENCES `ar_bayiler` (`id`);

--
-- Constraints for table `ar_kazanclar`
--
ALTER TABLE `ar_kazanclar`
  ADD CONSTRAINT `ar_kazanclar_ibfk_1` FOREIGN KEY (`bayi_id`) REFERENCES `ar_bayiler` (`id`);

--
-- Constraints for table `ar_siparisler`
--
ALTER TABLE `ar_siparisler`
  ADD CONSTRAINT `ar_siparisler_ibfk_2` FOREIGN KEY (`bayi_id`) REFERENCES `ar_bayiler` (`id`);

--
-- Constraints for table `ar_siparis_detay`
--
ALTER TABLE `ar_siparis_detay`
  ADD CONSTRAINT `ar_siparis_detay_ibfk_1` FOREIGN KEY (`siparis_id`) REFERENCES `ar_siparisler` (`id`),
  ADD CONSTRAINT `ar_siparis_detay_ibfk_2` FOREIGN KEY (`urun_id`) REFERENCES `ar_urunler` (`id`);

--
-- Constraints for table `ar_urunler`
--
ALTER TABLE `ar_urunler`
  ADD CONSTRAINT `ar_urunler_ibfk_1` FOREIGN KEY (`kategori_id`) REFERENCES `ar_kategoriler` (`id`),
  ADD CONSTRAINT `ar_urunler_ibfk_2` FOREIGN KEY (`urun_stok_id`) REFERENCES `ar_urun_stok` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
