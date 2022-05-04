CREATE TABLE `stock_account_statistic`
(
    `id`         bigint         NOT NULL AUTO_INCREMENT,
    `user_id`    bigint         NOT NULL,
    `stock_id`   bigint         NOT NULL,
    `created_at` datetime       NOT NULL,
    `profit`     decimal(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
)