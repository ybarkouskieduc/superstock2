INSERT INTO `user` (`id`, `username`, `password`, `created_at`, `updated_at`) VALUES
    (67, 'user', 'awesomesecurity', '2022-04-24 14:41:35', NULL),
    (68, 'seconduser', 'awesomesecurity', '2022-04-24 14:41:35', NULL);

INSERT INTO `account` (`id`, `user_id`, `currency`, `amount`, `default`) VALUES
    (72, 67, 'USD', 100.00, 1),
    (73, 67, 'BYN', 1500.00, 0),
    (74, 68, 'USD', 1340.20, 1),
    (75, 68, 'BYN', 15040.00, 0),
    (76, 68, 'EUR', 4200.99, 0);

INSERT INTO `bank` (`id`, `name`, `description`, `country`, `created_at`) VALUES
    (19, 'Супербанк', 'Хороший банк для настоящик ребят, отличные курсы и сервис', 'Швейцария', '2022-04-24 14:41:35'),
    (20, 'Банкнеочень', 'Не очень хороший банк, но все еще пойдет. Валюты мало, зато RUB - полно!', 'Россия', '2022-04-24 14:41:35'),
    (21, 'Ужасбанк', 'Комментарии излишни', 'Беларусь', '2022-04-24 14:41:35');

INSERT INTO `bank_exchange` (`id`, `bank_id`, `currency_in`, `rate`, `created_at`, `currency_out`, `fee`) VALUES
    (31, 19, 'USD', 2.90, '2022-04-24 14:41:35', 'BYN', 0.00),
    (32, 19, 'BYN', 0.38, '2022-04-24 14:41:35', 'USD', 0.00),
    (33, 20, 'USD', 2.70, '2022-04-24 14:41:35', 'BYN', 0.00),
    (34, 20, 'BYN', 0.28, '2022-04-24 14:41:35', 'USD', 0.00),
    (35, 21, 'USD', 0.10, '2022-04-24 14:41:35', 'BYN', 0.00),
    (36, 21, 'BYN', 0.15, '2022-04-24 14:41:35', 'USD', 0.00);

INSERT INTO `bank_review` (`id`, `bank_id`, `user_id`, `rate`, `review`, `created_at`) VALUES
    (1, 19, 67, 5, 'Крутой банк!', '2022-04-24 14:43:11');

INSERT INTO `stock` (`id`, `name`, `sign`, `description`) VALUES
    (1, 'Apple', 'AAPL', 'Apple stock description'),
    (2, 'Amazon', 'AMZN', NULL),
    (3, 'Alphabet', 'GOOG', NULL);

INSERT INTO `stock_account` (`id`, `user_id`, `stock_id`, `amount`) VALUES
    (1, 67, 1, 100.00),
    (2, 68, 1, 5555.00);

INSERT INTO `stock_price` (`id`, `stock_id`, `price`, `created_at`) VALUES
    (1, 1, 100.00, '2022-04-24 14:43:58'),
    (2, 1, 110.00, '2022-04-24 14:44:06'),
    (3, 2, 1233.00, '2022-04-24 14:44:43'),
    (4, 3, 12.00, '2022-04-24 14:45:29');

INSERT INTO `stock_statistic` (`id`, `stock_id`, `dividend`, `volume`, `market_value`) VALUES
    (1, 1, 3.00, 21313123, 123123123.00);

INSERT INTO `stock` (`id`, `name`, `sign`, `description`) VALUES
    (4, 'Tesla', 'TSLA', NULL),
    (5, 'Meta', 'FB', NULL),
    (6, 'Netflix', 'NFLX', NULL);
