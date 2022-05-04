package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockTransactionRecord;
import com.wldrmnd.superstock.model.stock.StockTransaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class StockTransactionMapperImpl implements StockTransactionMapper {

    @Override
    public StockTransaction toModel(StockTransactionRecord record) {
        if ( record == null ) {
            return null;
        }

        StockTransaction stockTransaction = new StockTransaction();

        if ( record.getId() != null ) {
            stockTransaction.setId( record.getId() );
        }
        if ( record.getStockId() != null ) {
            stockTransaction.setStockId( record.getStockId() );
        }
        if ( record.getUserId() != null ) {
            stockTransaction.setUserId( record.getUserId() );
        }
        if ( record.getStockPriceId() != null ) {
            stockTransaction.setStockPriceId( record.getStockPriceId() );
        }
        if ( record.getAmount() != null ) {
            stockTransaction.setAmount( record.getAmount() );
        }
        if ( record.getCreatedAt() != null ) {
            stockTransaction.setCreatedAt( record.getCreatedAt() );
        }
        if ( record.getRevertedAt() != null ) {
            stockTransaction.setRevertedAt( record.getRevertedAt() );
        }
        if ( record.getGoal() != null ) {
            stockTransaction.setGoal( record.getGoal() );
        }

        return stockTransaction;
    }
}
