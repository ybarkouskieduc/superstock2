/*
 * This file is generated by jOOQ.
 */
package com.wldrmnd.superstock.domain.tables;


import com.wldrmnd.superstock.domain.Keys;
import com.wldrmnd.superstock.domain.Superstock;
import com.wldrmnd.superstock.domain.enums.StockTransactionGoal;
import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StockTransaction extends TableImpl<StockTransactionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>superstock.stock_transaction</code>
     */
    public static final StockTransaction STOCK_TRANSACTION = new StockTransaction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StockTransactionRecord> getRecordType() {
        return StockTransactionRecord.class;
    }

    /**
     * The column <code>superstock.stock_transaction.id</code>.
     */
    public final TableField<StockTransactionRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>superstock.stock_transaction.stock_id</code>.
     */
    public final TableField<StockTransactionRecord, Long> STOCK_ID = createField(DSL.name("stock_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>superstock.stock_transaction.user_id</code>.
     */
    public final TableField<StockTransactionRecord, Long> USER_ID = createField(DSL.name("user_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>superstock.stock_transaction.stock_price_id</code>.
     */
    public final TableField<StockTransactionRecord, Long> STOCK_PRICE_ID = createField(DSL.name("stock_price_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>superstock.stock_transaction.amount</code>.
     */
    public final TableField<StockTransactionRecord, BigDecimal> AMOUNT = createField(DSL.name("amount"), SQLDataType.DECIMAL(15, 2).nullable(false), this, "");

    /**
     * The column <code>superstock.stock_transaction.created_at</code>.
     */
    public final TableField<StockTransactionRecord, Timestamp> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(0).nullable(false), this, "");

    /**
     * The column <code>superstock.stock_transaction.goal</code>.
     */
    public final TableField<StockTransactionRecord, StockTransactionGoal> GOAL = createField(DSL.name("goal"), SQLDataType.VARCHAR(4).nullable(false).asEnumDataType(com.wldrmnd.superstock.domain.enums.StockTransactionGoal.class), this, "");

    /**
     * The column <code>superstock.stock_transaction.reverted_at</code>.
     */
    public final TableField<StockTransactionRecord, Timestamp> REVERTED_AT = createField(DSL.name("reverted_at"), SQLDataType.TIMESTAMP(0), this, "");

    private StockTransaction(Name alias, Table<StockTransactionRecord> aliased) {
        this(alias, aliased, null);
    }

    private StockTransaction(Name alias, Table<StockTransactionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>superstock.stock_transaction</code> table
     * reference
     */
    public StockTransaction(String alias) {
        this(DSL.name(alias), STOCK_TRANSACTION);
    }

    /**
     * Create an aliased <code>superstock.stock_transaction</code> table
     * reference
     */
    public StockTransaction(Name alias) {
        this(alias, STOCK_TRANSACTION);
    }

    /**
     * Create a <code>superstock.stock_transaction</code> table reference
     */
    public StockTransaction() {
        this(DSL.name("stock_transaction"), null);
    }

    public <O extends Record> StockTransaction(Table<O> child, ForeignKey<O, StockTransactionRecord> key) {
        super(child, key, STOCK_TRANSACTION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Superstock.SUPERSTOCK;
    }

    @Override
    public Identity<StockTransactionRecord, Long> getIdentity() {
        return (Identity<StockTransactionRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<StockTransactionRecord> getPrimaryKey() {
        return Keys.KEY_STOCK_TRANSACTION_PRIMARY;
    }

    @Override
    public StockTransaction as(String alias) {
        return new StockTransaction(DSL.name(alias), this);
    }

    @Override
    public StockTransaction as(Name alias) {
        return new StockTransaction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StockTransaction rename(String name) {
        return new StockTransaction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StockTransaction rename(Name name) {
        return new StockTransaction(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, Long, Long, Long, BigDecimal, Timestamp, StockTransactionGoal, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
