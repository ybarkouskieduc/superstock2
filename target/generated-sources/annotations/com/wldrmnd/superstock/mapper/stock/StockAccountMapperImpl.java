package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockAccountRecord;
import com.wldrmnd.superstock.model.stock.StockAccount;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class StockAccountMapperImpl implements StockAccountMapper {

    @Override
    public StockAccount toModel(StockAccountRecord record) {
        if ( record == null ) {
            return null;
        }

        StockAccount stockAccount = new StockAccount();

        if ( record.getId() != null ) {
            stockAccount.setId( record.getId() );
        }
        if ( record.getStockId() != null ) {
            stockAccount.setStockId( record.getStockId() );
        }
        if ( record.getUserId() != null ) {
            stockAccount.setUserId( record.getUserId() );
        }
        if ( record.getAmount() != null ) {
            stockAccount.setAmount( record.getAmount() );
        }

        return stockAccount;
    }
}
