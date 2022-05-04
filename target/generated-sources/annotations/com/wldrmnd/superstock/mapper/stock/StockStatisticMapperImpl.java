package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.StockStatisticRecord;
import com.wldrmnd.superstock.model.stock.StockStatistic;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class StockStatisticMapperImpl implements StockStatisticMapper {

    @Override
    public StockStatistic toModel(StockStatisticRecord record) {
        if ( record == null ) {
            return null;
        }

        StockStatistic stockStatistic = new StockStatistic();

        if ( record.getId() != null ) {
            stockStatistic.setId( record.getId() );
        }
        if ( record.getStockId() != null ) {
            stockStatistic.setStockId( record.getStockId() );
        }
        if ( record.getVolume() != null ) {
            stockStatistic.setVolume( record.getVolume() );
        }
        if ( record.getDividend() != null ) {
            stockStatistic.setDividend( record.getDividend() );
        }
        if ( record.getMarketValue() != null ) {
            stockStatistic.setMarketValue( record.getMarketValue() );
        }

        return stockStatistic;
    }
}
