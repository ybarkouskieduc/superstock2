package com.wldrmnd.superstock.mapper.bank;

import com.wldrmnd.superstock.domain.tables.records.BankExchangeTransactionRecord;
import com.wldrmnd.superstock.model.bank.BankExchangeTransaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class BankExchangeTransactionMapperImpl implements BankExchangeTransactionMapper {

    @Override
    public BankExchangeTransaction toModel(BankExchangeTransactionRecord record) {
        if ( record == null ) {
            return null;
        }

        BankExchangeTransaction bankExchangeTransaction = new BankExchangeTransaction();

        if ( record.getId() != null ) {
            bankExchangeTransaction.setId( record.getId() );
        }
        if ( record.getUserId() != null ) {
            bankExchangeTransaction.setUserId( record.getUserId() );
        }
        if ( record.getBankId() != null ) {
            bankExchangeTransaction.setBankId( record.getBankId() );
        }
        if ( record.getCurrencyIn() != null ) {
            bankExchangeTransaction.setCurrencyIn( record.getCurrencyIn() );
        }
        if ( record.getCurrencyOut() != null ) {
            bankExchangeTransaction.setCurrencyOut( record.getCurrencyOut() );
        }
        if ( record.getAmountIn() != null ) {
            bankExchangeTransaction.setAmountIn( record.getAmountIn() );
        }
        if ( record.getAmountOut() != null ) {
            bankExchangeTransaction.setAmountOut( record.getAmountOut() );
        }
        if ( record.getRate() != null ) {
            bankExchangeTransaction.setRate( record.getRate() );
        }
        if ( record.getFee() != null ) {
            bankExchangeTransaction.setFee( record.getFee() );
        }
        if ( record.getCreatedAt() != null ) {
            bankExchangeTransaction.setCreatedAt( record.getCreatedAt() );
        }

        return bankExchangeTransaction;
    }
}
