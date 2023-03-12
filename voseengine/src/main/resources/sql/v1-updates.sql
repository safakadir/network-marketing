ALTER TABLE `ar_bayiler` AUTO_INCREMENT=10101

ALTER TABLE `ar_kariyerler` ADD `fark_kazanci` VARCHAR(50) NULL AFTER `binary_eslesme`

ALTER TABLE `ar_bayiler` CHANGE `email` `email` VARCHAR(50) NULL


CREATE TABLE `ar_odeme_talepler` (
  `id` int(11) NOT NULL,
  `bayi_id` int(11) NOT NULL,
  `miktar` double NOT NULL,
  `cuzdan_miktar` double NOT NULL,
  `talep_tarih` datetime NOT NULL,
  `durum` int(11) NOT NULL DEFAULT '0',
  `durum_tarih` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `ar_odeme_talepler`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bayi_id` (`bayi_id`),
  ADD KEY `talep_tarih` (`talep_tarih`);

ALTER TABLE `ar_odeme_talepler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ar_odeme_talepler`
  ADD CONSTRAINT `ar_odeme_talepler_ibfk_1` FOREIGN KEY (`bayi_id`) REFERENCES `ar_bayiler` (`id`);

ALTER TABLE `ar_odeme_talepler` AUTO_INCREMENT=101

ALTER TABLE `ar_siparisler` AUTO_INCREMENT=100101

ALTER TABLE `ar_siparisler` ADD `paket_alisveris` BOOLEAN NOT NULL AFTER `adres`;

ALTER TABLE `ar_urunler` ADD `starred` BOOLEAN NOT NULL AFTER `aciklama`;

ALTER TABLE `ar_kazanclar` ADD `aciklama` VARCHAR(150) NULL AFTER `kazanc_turu`;
