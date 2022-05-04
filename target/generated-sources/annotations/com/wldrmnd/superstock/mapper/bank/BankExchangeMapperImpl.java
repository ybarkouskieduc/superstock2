package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeRecord;
import com.wldrmnd.superstock.model.bank.BankExchange;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class BankExchangeMapperImpl implements BankExchangeMapper {

    @Override
    public BankExchange toModel(BankExchangeRecord record) {
        if ( record == null ) {
            return null;
        }

        BankExchange bankExchange = new BankExchange();

        if ( record.getId() != null ) {
            bankExchange.setId( record.getId() );
        }
        if ( record.getBankId() != null ) {
            bankExchange.setBankId( record.getBankId() );
        }
        if ( record.getCurrencyIn() != null ) {
            bankExchange.setCurrencyIn( record.getCurrencyIn() );
        }
        if ( record.getCurrencyOut() != null ) {
            bankExchange.setCurrencyOut( record.getCurrencyOut() );
        }
        if ( record.getRate() != null ) {
            bankExchange.setRate( record.getRate() );
        }
        if ( record.getFee() != null ) {
            bankExchange.setFee( record.getFee() );
        }

        return bankExchange;
    }
}
