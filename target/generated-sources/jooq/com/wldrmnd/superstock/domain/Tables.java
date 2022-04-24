/*
 * This file is generated by jOOQ.
 */
package com.wldrmnd.superstock.domain;


import com.wldrmnd.superstock.domain.tables.Account;
import com.wldrmnd.superstock.domain.tables.Bank;
import com.wldrmnd.superstock.domain.tables.BankExchange;
import com.wldrmnd.superstock.domain.tables.BankExchangeTransaction;
import com.wldrmnd.superstock.domain.tables.BankReview;
import com.wldrmnd.superstock.domain.tables.ExchangeStockRequest;
import com.wldrmnd.superstock.domain.tables.FlywaySchemaHistory;
import com.wldrmnd.superstock.domain.tables.Stock;
import com.wldrmnd.superstock.domain.tables.StockAccount;
import com.wldrmnd.superstock.domain.tables.StockPrice;
import com.wldrmnd.superstock.domain.tables.StockStatistic;
import com.wldrmnd.superstock.domain.tables.StockTransaction;
import com.wldrmnd.superstock.domain.tables.User;


/**
 * Convenience access to all tables in superstock.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>superstock.account</code>.
     */
    public static final Account ACCOUNT = Account.ACCOUNT;

    /**
     * The table <code>superstock.bank</code>.
     */
    public static final Bank BANK = Bank.BANK;

    /**
     * The table <code>superstock.bank_exchange</code>.
     */
    public static final BankExchange BANK_EXCHANGE = BankExchange.BANK_EXCHANGE;

    /**
     * The table <code>superstock.bank_exchange_transaction</code>.
     */
    public static final BankExchangeTransaction BANK_EXCHANGE_TRANSACTION = BankExchangeTransaction.BANK_EXCHANGE_TRANSACTION;

    /**
     * The table <code>superstock.bank_review</code>.
     */
    public static final BankReview BANK_REVIEW = BankReview.BANK_REVIEW;

    /**
     * The table <code>superstock.exchange_stock_request</code>.
     */
    public static final ExchangeStockRequest EXCHANGE_STOCK_REQUEST = ExchangeStockRequest.EXCHANGE_STOCK_REQUEST;

    /**
     * The table <code>superstock.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>superstock.stock</code>.
     */
    public static final Stock STOCK = Stock.STOCK;

    /**
     * The table <code>superstock.stock_account</code>.
     */
    public static final StockAccount STOCK_ACCOUNT = StockAccount.STOCK_ACCOUNT;

    /**
     * The table <code>superstock.stock_price</code>.
     */
    public static final StockPrice STOCK_PRICE = StockPrice.STOCK_PRICE;

    /**
     * The table <code>superstock.stock_statistic</code>.
     */
    public static final StockStatistic STOCK_STATISTIC = StockStatistic.STOCK_STATISTIC;

    /**
     * The table <code>superstock.stock_transaction</code>.
     */
    public static final StockTransaction STOCK_TRANSACTION = StockTransaction.STOCK_TRANSACTION;

    /**
     * The table <code>superstock.user</code>.
     */
    public static final User USER = User.USER;
}