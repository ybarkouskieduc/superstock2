package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockRecord;
import com.wldrmnd.superstock.model.stock.Stock;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public Stock toModel(StockRecord record) {
        if ( record == null ) {
            return null;
        }

        Stock stock = new Stock();

        if ( record.getId() != null ) {
            stock.setId( record.getId() );
        }
        if ( record.getName() != null ) {
            stock.setName( record.getName() );
        }
        if ( record.getSign() != null ) {
            stock.setSign( record.getSign() );
        }
        if ( record.getDescription() != null ) {
            stock.setDescription( record.getDescription() );
        }

        return stock;
    }
}
