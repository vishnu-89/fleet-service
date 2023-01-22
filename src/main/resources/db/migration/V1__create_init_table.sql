CREATE TABLE IF NOT EXISTS `fleets` (

    `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20),
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP ,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(65),
    `updated_by` varchar(65),

    UNIQUE KEY `fleet_name_key` (`name`)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `assets` (

    `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20),
    `vin` varchar(20),
    `fleet_id` bigint(20) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP ,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(65),
    `updated_by` varchar(65),

    UNIQUE KEY `asset_vin_key` (`fleet_id`,`vin`)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
