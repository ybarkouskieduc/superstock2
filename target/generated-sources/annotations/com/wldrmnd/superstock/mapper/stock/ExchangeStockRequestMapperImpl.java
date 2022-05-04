package com.wldrmnd.superstock.mapper.stock;

import com.wldrmnd.superstock.domain.tables.records.ExchangeStockRequestRecord;
import com.wldrmnd.superstock.model.stock.ExchangeStockRequestModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class ExchangeStockRequestMapperImpl implements ExchangeStockRequestMapper {

    @Override
    public ExchangeStockRequestModel toModel(ExchangeStockRequestRecord record) {
        if ( record == null ) {
            return null;
        }

        ExchangeStockRequestModel exchangeStockRequestModel = new ExchangeStockRequestModel();

        if ( record.getId() != null ) {
            exchangeStockRequestModel.setId( record.getId() );
        }
        if ( record.getUserId() != null ) {
            exchangeStockRequestModel.setUserId( record.getUserId() );
        }
        if ( record.getStockId() != null ) {
            exchangeStockRequestModel.setStockId( record.getStockId() );
        }
        if ( record.getAmount() != null ) {
            exchangeStockRequestModel.setAmount( record.getAmount() );
        }
        if ( record.getDesiredPrice() != null ) {
            exchangeStockRequestModel.setDesiredPrice( record.getDesiredPrice() );
        }
        if ( record.getCreatedAt() != null ) {
            exchangeStockRequestModel.setCreatedAt( record.getCreatedAt() );
        }
        if ( record.getExecutedAt() != null ) {
            exchangeStockRequestModel.setExecutedAt( record.getExecutedAt() );
        }

        return exchangeStockRequestModel;
    }
}
