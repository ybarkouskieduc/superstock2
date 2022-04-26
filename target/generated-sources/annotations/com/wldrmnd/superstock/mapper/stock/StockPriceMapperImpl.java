package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockPriceRecord;
import com.wldrmnd.superstock.model.stock.StockPrice;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-26T12:11:31+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class StockPriceMapperImpl implements StockPriceMapper {

    @Override
    public StockPrice toModel(StockPriceRecord record) {
        if ( record == null ) {
            return null;
        }

        StockPrice stockPrice = new StockPrice();

        if ( record.getId() != null ) {
            stockPrice.setId( record.getId() );
        }
        if ( record.getStockId() != null ) {
            stockPrice.setStockId( record.getStockId() );
        }
        if ( record.getPrice() != null ) {
            stockPrice.setPrice( record.getPrice() );
        }
        if ( record.getCreatedAt() != null ) {
            stockPrice.setCreatedAt( record.getCreatedAt() );
        }

        return stockPrice;
    }
}
