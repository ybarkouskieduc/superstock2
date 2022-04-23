CREATE TABLE `user`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT,
    `username`   varchar(255)      DEFAULT NULL,
    `password`   varchar(255)      DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime          DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 67
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `bank`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` varchar(255)                                                           DEFAULT NULL,
    `country`     varchar(255)                                                           DEFAULT NULL,
    `created_at`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `bank_exchange`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT,
    `bank_id`      bigint                                                        NOT NULL,
    `currency_in`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `rate`         decimal(15, 2)                                                NOT NULL DEFAULT '1.00',
    `created_at`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `currency_out` varchar(255)                                                  NOT NULL,
    `fee`          decimal(15, 2)                                                         DEFAULT '0.00',
    PRIMARY KEY (`id`),
    KEY `bank_id` (`bank_id`),
    CONSTRAINT `bank_exchange_ibfk_1` FOREIGN KEY (`bank_id`) REFERENCES `bank` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `account`
(
    `id`       bigint         NOT NULL AUTO_INCREMENT,
    `user_id`  bigint         NOT NULL,
    `currency` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'USD',
    `amount`   decimal(15, 2) NOT NULL                                       DEFAULT '0.00',
    `default`  tinyint(1)     NOT NULL                                       DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 72
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `bank_exchange_transaction`
(
    `id`           bigint         NOT NULL AUTO_INCREMENT,
    `user_id`      bigint         NOT NULL,
    `bank_id`      bigint         NOT NULL,
    `currency_in`  varchar(255)   NOT NULL,
    `currency_out` varchar(255)   NOT NULL,
    `amount_in`    decimal(15, 2) NOT NULL,
    `amount_out`   decimal(15, 2) NOT NULL,
    `rate`         decimal(15, 2) NOT NULL,
    `created_at`   datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `fee`          decimal(15, 2)          DEFAULT '0.00',
    `reverted_at`  datetime                DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `bank_review`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT,
    `bank_id`    bigint   NOT NULL,
    `user_id`    bigint   NOT NULL,
    `rate`       int      NOT NULL,
    `review`     text,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `stock`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL,
    `sign`        varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `stock_account`
(
    `id`       bigint         NOT NULL AUTO_INCREMENT,
    `user_id`  bigint         NOT NULL,
    `stock_id` bigint         NOT NULL,
    `amount`   decimal(15, 2) NOT NULL DEFAULT '0.00',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `stock_id` (`stock_id`),
    CONSTRAINT `stock_account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `stock_account_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 63
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `stock_price`
(
    `id`         bigint         NOT NULL AUTO_INCREMENT,
    `stock_id`   bigint         NOT NULL,
    `price`      decimal(15, 2) NOT NULL,
    `created_at` datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `stock_id` (`stock_id`),
    CONSTRAINT `stock_price_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 51
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `stock_statistic`
(
    `id`           bigint         NOT NULL AUTO_INCREMENT,
    `stock_id`     bigint         NOT NULL,
    `dividend`     decimal(3, 2)  NOT NULL DEFAULT '0.00',
    `volume`       bigint         NOT NULL DEFAULT '0',
    `market_value` decimal(15, 2) NOT NULL DEFAULT '0.00',
    PRIMARY KEY (`id`),
    KEY `stock_id` (`stock_id`),
    CONSTRAINT `stock_statistic_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `stock_transaction`
(
    `id`             bigint              NOT NULL AUTO_INCREMENT,
    `stock_id`       bigint              NOT NULL,
    `user_id`        bigint              NOT NULL,
    `stock_price_id` bigint              NOT NULL,
    `amount`         decimal(15, 2)      NOT NULL,
    `created_at`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `goal`           enum ('SELL','BUY') NOT NULL,
    `reverted_at`    datetime                     DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `exchange_stock_request`
(
    `id`            bigint         NOT NULL AUTO_INCREMENT,
    `user_id`       bigint         NOT NULL,
    `stock_id`      bigint         NOT NULL,
    `amount`        decimal(15, 2) NOT NULL,
    `desired_price` decimal(15, 2) NOT NULL,
    `created_at`    datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `executed_at`   datetime                DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `stock_id` (`stock_id`),
    CONSTRAINT `exchange_stock_request_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `exchange_stock_request_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE
);